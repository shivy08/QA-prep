# QA Interview Prep

Selenium automation framework built for QA interview preparation. Covers core Selenium concepts, Page Object Model, TestNG, and common interview topics.

---

## Tech Stack

- Java 11
- Selenium 4.18
- TestNG 7.9
- WebDriverManager 5.7
- Maven

---

## Project Structure

```
src/test/java/
├── tests/
│   ├── SampleTest.java        ← locators, waits, alerts, frames, dropdowns
│   └── LoginTest.java         ← POM-based login test
├── pages/
│   └── LoginPage.java         ← Page Object Model example
├── utils/
│   └── DriverFactory.java     ← WebDriverManager setup
└── resources/
    └── testng.xml             ← test suite config

notes/
├── 01-selenium-core.md        ← locators, waits, POM, exceptions, dynamic elements
├── 02-api-testing.md          ← HTTP methods, status codes, Postman
├── 03-agile-sql-behavioral.md ← agile, SQL queries, behavioral answers
└── 04-manual-testing.md       ← testing types, severity/priority, automation vs manual
```

---

## Topics Covered

### Selenium
- Locators — id, name, CSS, XPath, linkText
- Waits — implicit, explicit, fluent
- Page Object Model (POM)
- TestNG annotations — @BeforeMethod, @AfterMethod, @Test
- Dropdowns — Select class
- Alerts — JS Alert, Confirm, Prompt
- Frames — switchTo().frame(), defaultContent()
- Multiple Windows
- Dynamic Elements
- Common Exceptions

### API Testing
- HTTP methods — GET, POST, PUT, PATCH, DELETE
- Status codes
- What to validate in API tests
- API vs UI testing

### Manual Testing
- Smoke, Sanity, Regression testing
- Severity vs Priority
- Functional vs Non-functional testing
- Test case format
- Risk-based testing

### SQL
- SELECT, WHERE, COUNT, ORDER BY
- INNER JOIN, LEFT JOIN

---

## Running Tests

```bash
mvn test
```

Or run individual tests from IntelliJ by right-clicking the test class.

---

## Practice Site

Tests are written against [The Internet](https://the-internet.herokuapp.com) — a free Selenium practice site.
