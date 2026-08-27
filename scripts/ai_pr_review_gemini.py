import json
import os

from google import genai

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
    "type": "object",
    "properties": {
        "findings": {
            "type": "array",
            "items": {
                "type": "object",
                "properties": {
                    "severity": {
                        "type": "string",
                        "enum": ["CRITICAL", "HIGH", "MEDIUM", "LOW"]
                    },
                    "file": {
                        "type": "string"
                    },
                    "line": {
                        "type": ["integer", "null"]
                    },
                    "category": {
                        "type": "string"
                    },
                    "problem": {
                        "type": "string"
                    },
                    "why_it_matters": {
                        "type": "string"
                    },
                    "suggested_fix": {
                        "type": "string"
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
                ],
                "additionalProperties": False
            }
        }
    },
    "required": ["findings"],
    "additionalProperties": False
}

response = client.models.generate_content(
    model="gemini-3.6-flash",
    contents=prompt,
    config={
        "response_mime_type": "application/json",
        "response_schema": schema
    }
)

review = json.loads(response.text)

with open("ai-review.json", "w", encoding="utf-8") as file:
    json.dump(review, file, indent=2)

print("\n===== AI PR REVIEW =====\n")
print(json.dumps(review, indent=2))