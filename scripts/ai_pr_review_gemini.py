import json
import os
import time

from google import genai
from google.genai import errors


client = genai.Client()

with open("pr.diff", "r", encoding="utf-8") as file:
    diff = file.read()

prompt = f"""
You are a senior Android engineer reviewing a GitHub pull request.

Review ONLY the changes present in the PR DIFF below.

Focus on:
- Kotlin correctness
- Android best practices
- Coroutines
- Flow / StateFlow
- Lifecycle issues
- Jetpack Compose
- Memory leaks
- Threading
- Performance
- Error handling
- Security
- Architecture
- Testability

Rules:
- Only report actionable issues.
- Only report issues caused by or directly related to the PR changes.
- Do not report formatting preferences.
- Do not report subjective style opinions.
- Do not report unrelated existing problems.
- Do not invent issues.
- If there are no issues, return an empty findings array.
- Use the exact file path from the diff.
- Use the changed line number when it can be identified.

PR DIFF:

{diff}
"""

schema = {
    "type": "OBJECT",
    "properties": {
        "findings": {
            "type": "ARRAY",
            "items": {
                "type": "OBJECT",
                "properties": {
                    "severity": {
                        "type": "STRING",
                        "enum": ["CRITICAL", "HIGH", "MEDIUM", "LOW"]
                    },
                    "file": {
                        "type": "STRING"
                    },
                    "line": {
                        "type": "STRING"
                    },
                    "category": {
                        "type": "STRING"
                    },
                    "problem": {
                        "type": "STRING"
                    },
                    "why_it_matters": {
                        "type": "STRING"
                    },
                    "suggested_fix": {
                        "type": "STRING"
                    }
                },
                "required": [
                    "severity",
                    "file",
                    "line",
                    "category",
                    "problem",
                    "why_it_matters",
                    "suggested_fix"
                ]
            }
        }
    },
    "required": ["findings"]
}


MAX_RETRIES = 3

response = None

for attempt in range(1, MAX_RETRIES + 1):

    try:

        print(
            f"Calling Gemini "
            f"(attempt {attempt}/{MAX_RETRIES})..."
        )

        response = client.models.generate_content(
            model="gemini-3.6-flash",
            contents=prompt,
            config={
                "response_mime_type": "application/json",
                "response_schema": schema,
            },
        )

        break

    except errors.ServerError as error:

        if error.code != 503:
            raise

        if attempt == MAX_RETRIES:
            print(
                "Gemini is still unavailable after "
                f"{MAX_RETRIES} attempts."
            )
            raise

        wait_seconds = 2 ** attempt

        print(
            "Gemini returned 503 (temporarily unavailable). "
            f"Retrying in {wait_seconds} seconds..."
        )

        time.sleep(wait_seconds)


if response is None:
    raise RuntimeError(
        "Gemini did not return a response."
    )

review = json.loads(response.text)

with open("ai-review.json", "w", encoding="utf-8") as file:
    json.dump(review, file, indent=2)

print("\n===== AI PR REVIEW =====\n")
print(json.dumps(review, indent=2))