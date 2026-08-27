import json
import os
import urllib.request

with open("ai-review.json", "r", encoding="utf-8") as file:
    review = json.load(file)

findings = review.get("findings", [])

if not findings:
    print("No findings to post.")
    exit(0)

token = os.environ["GH_TOKEN"]
repository = os.environ["REPOSITORY"]
pr_number = os.environ["PR_NUMBER"]
commit_id = os.environ["PR_HEAD_SHA"]

# Read the PR diff.
with open("pr.diff", "r", encoding="utf-8") as file:
    diff = file.read()


def get_changed_lines(diff_text):
    """
    Returns:
        {
            "file/path.kt": {20, 21, 25, ...}
        }

    Only lines added/changed in the PR are included.
    """
    changed_lines = {}

    current_file = None
    new_line_number = None

    for line in diff_text.splitlines():

        if line.startswith("+++ b/"):
            current_file = line[6:]
            changed_lines.setdefault(current_file, set())
            continue

        if line.startswith("@@"):
            # Example:
            # @@ -10,5 +10,8 @@
            import re

            match = re.search(r"\+(\d+)(?:,\d+)?", line)

            if match:
                new_line_number = int(match.group(1))

            continue

        if current_file is None or new_line_number is None:
            continue

        # Added line
        if line.startswith("+") and not line.startswith("+++"):
            changed_lines[current_file].add(new_line_number)
            new_line_number += 1

        # Deleted line
        elif line.startswith("-") and not line.startswith("---"):
            continue

        # Context line
        else:
            new_line_number += 1

    return changed_lines


changed_lines = get_changed_lines(diff)

comments = []

for finding in findings:

    file_path = finding["file"]
    line_value = finding["line"]

    try:
        line_number = int(line_value)
    except (ValueError, TypeError):
        print(f"Skipping finding with invalid line: {line_value}")
        continue

    # Only comment if the line was actually changed in this PR.
    if file_path not in changed_lines:
        print(f"Skipping {file_path}:{line_number} - file not in diff")
        continue

    if line_number not in changed_lines[file_path]:
        print(
            f"Skipping {file_path}:{line_number} - "
            "line is not an added/changed line in the PR"
        )
        continue

    severity = finding["severity"]
    category = finding["category"]
    problem = finding["problem"]
    why = finding["why_it_matters"]
    fix = finding["suggested_fix"]

    icon = {
        "CRITICAL": "🚨",
        "HIGH": "🔴",
        "MEDIUM": "🟡",
        "LOW": "🔵",
    }.get(severity, "⚪")

    body = f"""### {icon} {severity} — {category}

**Problem:**  
{problem}

**Why it matters:**  
{why}

**Suggested fix:**  
{fix}

_🤖 Gemini AI Code Review_
"""

    comments.append({
        "path": file_path,
        "line": line_number,
        "side": "RIGHT",
        "body": body
    })


if not comments:
    print("No valid inline comments to post.")
    exit(0)


url = (
    f"https://api.github.com/repos/"
    f"{repository}/pulls/{pr_number}/reviews"
)

payload = {
    "commit_id": commit_id,
    "body": "🤖 **AI PR Review**",
    "event": "COMMENT",
    "comments": comments,
}

data = json.dumps(payload).encode("utf-8")

request = urllib.request.Request(
    url,
    data=data,
    method="POST",
    headers={
        "Authorization": f"Bearer {token}",
        "Accept": "application/vnd.github+json",
        "X-GitHub-Api-Version": "2026-03-10",
        "Content-Type": "application/json",
    },
)

with urllib.request.urlopen(request) as response:
    print("Inline AI review created.")
    print("HTTP status:", response.status)