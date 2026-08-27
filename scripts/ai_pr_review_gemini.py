from google import genai

client = genai.Client()

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
response = client.models.generate_content(
    # model="gemini-2.5-pro", #costly #Complex structural refactoring / deep logic debugging
    model="gemini-2.5-flash",  # High-speed, lower-cost option
    # model="gemini-2.5-flash-lite", # Maximum savings on routine syntax reviews
    contents=prompt
)

print("\n===== AI PR REVIEW =====\n")
print(response.text)