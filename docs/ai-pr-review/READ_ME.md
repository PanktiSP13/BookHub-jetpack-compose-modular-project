# AI PR Review

AI-assisted GitHub PR reviewer using Gemini.

## Versions

### V1 — Basic AI Review

Flow:

GitHub PR
→ GitHub Actions
→ Gemini
→ AI review
→ PR comment

Features:
- Gemini API integration
- PR diff extraction
- Android/Kotlin review
- Consolidated PR comment

Location:
`v1-basic-review/`

---

### V2 — Inline AI Review

Flow:

GitHub PR
→ GitHub Actions
→ Gemini
→ Structured findings
→ Changed-line validation
→ Inline PR comments

Features:
- Structured JSON output
- Severity
- File + line
- Inline GitHub comments
- Changed-line validation

Location:
`v2-inline-comments/`

---

### V3 — Production Quality

Planned:

- Duplicate comment prevention
- Better false-positive handling
- Large PR handling
- Android-specific rules
- Quality gates

---


.github/
└── workflows/
└── ai-pr-review_gemini.yml       ← ONLY ACTIVE WORKFLOW


docs/
└── ai-pr-review/
├── READ_ME.md
│
└── versions/
├── ai-pr-review_openai.yml
├── v1-gemini-integration.yml
├── v2-basic-review.yml
├── v3-inline-comments.yml
├── v4-production-quality.yml
└── v5-quality-gate.yml

