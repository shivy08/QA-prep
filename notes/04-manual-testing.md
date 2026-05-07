# Manual Testing — Notes

---

## Severity vs Priority

- **Severity** — how badly bug impacts functionality
- **Priority** — how urgently it needs to be fixed (business impact)

| Example | Severity | Priority |
|---|---|---|
| Crash in rarely used admin feature | High | Low |
| Blurred logo on homepage | Low | High |
| Spelling mistake on login page | Low | High |

---

## Testing Types

**Smoke Testing**
- High-level check of core features on every new build
- Answers: "Is this build stable enough to test?"
- Examples: login, navigation, home page load

**Sanity Testing**
- Narrow, focused check after a bug fix or new feature
- Answers: "Does this specific change work?"
- Does not test entire application

**Regression Testing**
- Ensures new changes haven't broken existing functionality
- Performed: after bug fixes, after new features, before every release

---

## Functional vs Non-Functional Testing

**Functional** — what the system does
- Features, business logic, inputs/outputs
- Examples: login works, form submits correctly

**Non-Functional** — how the system performs
- Performance, usability, security, compatibility
- Examples: page loads under 2s, 1000 users simultaneously, works on mobile

---

## Test Case Components

1. Test Case ID
2. Title
3. Objective
4. Preconditions
5. Test Steps + Actions
6. Expected Result
7. Actual Result ← filled during execution
8. Pass/Fail Status ← filled during execution

---

## Automation vs Manual

**Automate when:**
- Repetitive tests run every sprint
- Same flow with many data inputs
- Cross-browser testing
- Stable UI

**Manual when:**
- Exploratory testing
- UI changes frequently
- One-time tests
- Usability testing

---

## Automation Testing

**Advantages:**
- Faster execution — minutes vs hours
- Reusable — write once, run every sprint
- Reliable — no human error
- Parallel execution — multiple browsers simultaneously
- Great for regression

**Disadvantages:**
- High initial investment — time to write scripts
- Maintenance cost — locators break when UI changes
- Not for everything — exploratory, usability can't be automated
- Requires technical skills

**Best suited for automation:**
- Repetitive tests
- Regression tests
- Data-driven tests (same flow, many inputs)
- Smoke tests (every build)
- Cross-browser tests

**Not suited:**
- Exploratory testing
- One-time tests
- Frequently changing UI
- Usability, CAPTCHA

---

## Interview Questions

**Q: Severity vs Priority — example of high severity low priority?**
A: Crash in a rarely used admin feature — functionally severe but low business urgency.

**Q: Smoke vs Sanity testing?**
A: Smoke — is the build stable enough to test (core features, every build). Sanity — does this specific fix/feature work (narrow, after a change).

**Q: What is regression testing?**
A: Ensures new changes haven't broken existing working functionality. Done after fixes, new features, and before releases.

**Q: Functional vs non-functional?**
A: Functional = what the system does (features, logic). Non-functional = how it performs (speed, load, usability, security).

**Q: What does a test case contain?**
A: ID, title, objective, preconditions, test steps, expected result, actual result, pass/fail status.

**Q: Not enough time — how do you prioritize?**
A: Risk-based — core critical features first, then new features, then high-traffic features, then regression. Document untested areas and communicate risk to stakeholders.
