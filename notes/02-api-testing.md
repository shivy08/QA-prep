# API Testing — Notes

---

## HTTP Methods

| Method | Purpose |
|---|---|
| GET | Fetch data |
| POST | Create new data |
| PUT | Update full existing data |
| PATCH | Update partial data |
| DELETE | Delete data |

**PUT vs PATCH:**
- PUT — send full object even if one field changed
- PATCH — send only the changed fields

---

## Status Codes

| Code | Meaning |
|---|---|
| 200 | OK — success |
| 201 | Created — POST success |
| 400 | Bad Request — invalid input |
| 401 | Unauthorized — not logged in / no token |
| 403 | Forbidden — logged in but no permission |
| 404 | Not Found |
| 500 | Internal Server Error — backend issue |

**401 vs 403:**
- 401 — not in the building yet (not authenticated)
- 403 — in the building but room is locked (no permission)

---

## What to Validate in API Tests

1. **Status code** — 200, 201, 404 etc. as expected
2. **Response body** — correct data, correct field values
3. **Response time** — under acceptable threshold (e.g. 2s)
4. **Headers** — correct content-type, authorization

---

## API vs UI Testing

| | API Testing | UI Testing |
|---|---|---|
| What | Backend, business logic | Frontend, user experience |
| Speed | Fast | Slow |
| Stability | Stable | Brittle (locators break) |
| When | Earlier in SDLC | After UI is built |

---

## Interview Questions

**Q: What do you validate in an API test?**
A: Status code, response body fields and values, response time, and headers like content-type.

**Q: Difference between 401 and 403?**
A: 401 — not authenticated (not logged in, expired token). 403 — authenticated but no permission to access that resource.

**Q: Difference between PUT and PATCH?**
A: PUT updates the full resource — you send the complete object. PATCH updates partially — you send only the changed fields.

**Q: How is API testing different from UI testing?**
A: API tests the backend directly — faster, more stable, catches issues earlier. UI testing validates the full user experience but is slower and brittle. API suite that takes 2 hours on UI can run in minutes.

**Q: What tool do you use for API testing?**
A: Postman for manual API testing — collections, environments, assertions in Tests tab. For automation, REST Assured (Java) can be used to write API tests in code.
