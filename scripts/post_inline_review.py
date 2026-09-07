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


# ---------------------------------------------------------
# GitHub API helper
# ---------------------------------------------------------

def github_request(url, method="GET", data=None):
    headers = {
        "Authorization": f"Bearer {token}",
        "Accept": "application/vnd.github+json",
        "X-GitHub-Api-Version": "2026-03-10",
        "Content-Type": "application/json",
    }

    request_data = None

    if data is not None:
        request_data = json.dumps(data).encode("utf-8")

    request = urllib.request.Request(
        url,
        data=request_data,
        method=method,
        headers=headers,
    )

    with urllib.request.urlopen(request) as response:
        response_body = response.read().decode("utf-8")

        if response_body:
            return json.loads(response_body)

        return None


# ---------------------------------------------------------
# Get existing AI review comments
# ---------------------------------------------------------

comments_url = (
    f"https://api.github.com/repos/"
    f"{repository}/issues/{pr_number}/comments"
)

existing_comments = github_request(comments_url)

existing_ai_findings = set()

for comment in existing_comments:

    body = comment.get("body", "")

    # Only consider comments created by our AI reviewer.
    if "_🤖 Gemini AI Code Review_" not in body:
        continue

    path = comment.get("path")

    # Old consolidated comments won't have a path.
    if not path:
        continue

    line = comment.get("line")

    if line is None:
        continue

    # Extract the problem from the existing comment.
    problem_marker = "**Problem:**"

    if problem_marker not in body:
        continue

    problem = body.split(problem_marker, 1)[1]

    if "**Why it matters:**" in problem:
        problem = problem.split(
            "**Why it matters:",
            1
        )[0]

    problem = problem.strip()

    existing_ai_findings.add(
        (
            path,
            int(line),
            problem
        )
    )


print(
    f"Found {len(existing_ai_findings)} existing "
    "AI inline findings."
)


# ---------------------------------------------------------
# Read PR diff
# ---------------------------------------------------------

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

            import re

            match = re.search(
                r"\+(\d+)(?:,\d+)?",
                line
            )

            if match:
                new_line_number = int(match.group(1))

            continue

        if current_file is None or new_line_number is None:
            continue

        # Added line
        if line.startswith("+") and not line.startswith("+++"):
            changed_lines[current_file].add(
                new_line_number
            )
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


# ---------------------------------------------------------
# Process AI findings
# ---------------------------------------------------------

for finding in findings:

    file_path = finding["file"]
    line_value = finding["line"]

    try:
        line_number = int(line_value)

    except (ValueError, TypeError):
        print(
            f"Skipping finding with invalid line: "
            f"{line_value}"
        )
        continue


    # -----------------------------------------------------
    # Validate changed file
    # -----------------------------------------------------

    if file_path not in changed_lines:

        print(
            f"Skipping {file_path}:{line_number} "
            "- file not in diff"
        )

        continue


    # -----------------------------------------------------
    # Validate changed line
    # -----------------------------------------------------

    if line_number not in changed_lines[file_path]:

        print(
            f"Skipping "
            f"{file_path}:{line_number} "
            "- line is not an added/changed line "
            "in the PR"
        )

        continue


    severity = finding["severity"]
    category = finding["category"]
    problem = finding["problem"]
    why = finding["why_it_matters"]
    fix = finding["suggested_fix"]


    # -----------------------------------------------------
    # Duplicate detection
    # -----------------------------------------------------

    finding_key = (
        file_path,
        line_number,
        problem.strip()
    )

    if finding_key in existing_ai_findings:

        print(
            f"Skipping duplicate finding: "
            f"{file_path}:{line_number}"
        )

        continue


    # -----------------------------------------------------
    # Create GitHub comment
    # -----------------------------------------------------

    icon = {
        "CRITICAL": "🚨",
        "HIGH": "🔴",
        "MEDIUM": "🟡",
        "LOW": "🔵",
    }.get(
        severity,
        "⚪"
    )


    body = f"""### {icon} {severity} — {category}

**Problem:**  
{problem}

**Why it matters:**  
{why}

**Suggested fix:**  
{fix}

_🤖 Gemini AI Code Review_
"""


    comments.append(
        {
            "path": file_path,
            "line": line_number,
            "side": "RIGHT",
            "body": body,
        }
    )


# ---------------------------------------------------------
# Nothing new to post
# ---------------------------------------------------------

if not comments:

    print(
        "No new AI findings to post."
    )

    exit(0)


# ---------------------------------------------------------
# Create GitHub Pull Request review
# ---------------------------------------------------------

review_url = (
    f"https://api.github.com/repos/"
    f"{repository}/pulls/{pr_number}/reviews"
)


payload = {
    "commit_id": commit_id,
    "body": "🤖 **AI PR Review**",
    "event": "COMMENT",
    "comments": comments,
}


github_request(
    review_url,
    method="POST",
    data=payload
)


print(
    f"Inline AI review created with "
    f"{len(comments)} new comment(s)."
)