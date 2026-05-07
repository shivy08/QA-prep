# Agile + SQL + Behavioral — Notes

---

## QA Role in Agile Sprint

> "In sprint planning I review user stories and raise questions about edge cases and acceptance criteria. During the sprint I write test cases and test features as they're developed. I participate in daily standups to flag blockers. At end of sprint I do regression and sign off. I work with developers early — shift-left approach — so bugs are caught before they reach testing."

**Key terms:** sprint planning, acceptance criteria, shift-left, regression, sign off, daily standup

---

## SQL

**SELECT with WHERE**
```sql
SELECT * FROM employees WHERE department = 'QA';
```

**COUNT**
```sql
SELECT COUNT(*) FROM employees WHERE salary > 50000;
```

**ORDER BY**
```sql
SELECT * FROM employees WHERE salary > 50000 ORDER BY salary DESC;
```

**INNER JOIN**
```sql
SELECT e.name, d.name 
FROM employees e 
INNER JOIN departments d ON e.dept_id = d.id;
```

**LEFT JOIN** — all from left, matching from right (NULL if no match)
```sql
SELECT e.name, d.name 
FROM employees e 
LEFT JOIN departments d ON e.dept_id = d.id;
```

**Why QA needs SQL:**
> "After automation test runs, I verify data in DB. After registration test, I query users table to confirm record inserted with correct values."

---

## Behavioral Questions

**Q: Bug you found that others missed?**
> "While testing a dropdown filter, I noticed a specific value didn't retain its selection after page refresh — all other values worked fine. I logged it with steps to reproduce, the specific value, screenshots, and comparison with working values. If shipped, users would have lost filter selection unexpectedly."

**Q: Developer says your bug is not a bug?**
> "I re-verify first. Then provide evidence — screenshots, recording, steps, environment details. If still disagreed, I check acceptance criteria. If not documented, I involve the product owner for a decision. Goal is quality software, not winning an argument."

**Q: Not enough time to test everything?**
> "Risk-based testing — first core critical features, then newly delivered features (highest bug risk), then high-traffic features, then regression on stable areas. Whatever is not tested, I document and communicate the risk to stakeholders so the release decision is informed."
