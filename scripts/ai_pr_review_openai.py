import os
from openai import OpenAI

api_key = os.environ["OPENAI_API_KEY"].strip()
client = OpenAI(
    api_key= api_key
)

with open("pr.diff", "r", encoding="utf-8") as file:
    diff = file.read()

prompt = f"""
You are a senior Android engineer reviewing a GitHub pull request.

Review the following PR diff.

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

Only report actionable issues introduced or affected by this PR.

Do NOT report:
- Formatting preferences
- Subjective style opinions
- Unrelated existing issues
- Issues without a reasonable technical basis

For every issue provide:
- Severity: CRITICAL, HIGH, MEDIUM, or LOW
- File
- Line if identifiable
- Category
- Problem
- Why it matters
- Suggested fix

PR DIFF:

{diff}
"""

response = client.responses.create(
    model="gpt-5.6-luna",
    input=prompt
)

print("\n===== AI PR REVIEW =====\n")
print(response.output_text)