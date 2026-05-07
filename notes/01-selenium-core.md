# Selenium Core — Notes

---

## Project Setup

**Dependencies in pom.xml:**
- `selenium-java` — core Selenium
- `webdrivermanager` — auto-manages chromedriver, no manual download needed
- `testng` — test framework (annotations, assertions, suite config)

**Project structure:**
```
src/test/java/
├── tests/       ← test classes
├── pages/       ← POM classes (later)
├── utils/       ← DriverFactory, helpers
└── resources/
    └── testng.xml
```

---

## TestNG Annotations

| Annotation | When it runs |
|---|---|
| `@BeforeMethod` | Before every `@Test` method |
| `@AfterMethod` | After every `@Test` method (pass or fail) |
| `@BeforeClass` | Once before all tests in the class |
| `@AfterClass` | Once after all tests in the class |
| `@Test` | Marks a method as a test case |

**Flow:**
```
setUp() → test1 → tearDown()
setUp() → test2 → tearDown()
```

**Rule:** Always quit driver in `@AfterMethod`, never inside the test itself.
If the test fails, `@AfterMethod` still runs — browser won't be left open.

---

## DriverFactory

```java
WebDriverManager.chromedriver().setup(); // auto-downloads correct chromedriver
driver = new ChromeDriver();
driver.manage().window().maximize();
```

`driver = null` check ensures only one browser instance is created.

---

## Common WebDriver Commands

| Command | What it does |
|---|---|
| `driver.get(url)` | Navigate to URL |
| `driver.getTitle()` | Returns page title as String |
| `driver.getCurrentUrl()` | Returns current URL |
| `driver.close()` | Closes current tab |
| `driver.quit()` | Closes all tabs + kills browser process |

**`close()` vs `quit()` — interview question:**
- `close()` — closes only the current window/tab
- `quit()` — closes all windows and ends the WebDriver session
- Always use `quit()` in teardown

---

## Locators

**Priority order (use top ones when available):**
```java
By.id("username")
By.name("username")
By.cssSelector("#username")
By.xpath("//input[@id='username']")
```

**Why this order?**
- `id` and `name` are unique, fast, and stable
- CSS is fast but can't traverse upward in DOM
- XPath is most powerful but slowest and brittle

**XPath patterns:**
```java
//input[@id='username']                         // by attribute
//button[text()='Login']                        // exact text
//div[contains(@class,'error')]                 // partial class
//div[contains(text(),'Welcome')]               // partial text
//label[text()='Email']/following-sibling::input // traverse to sibling
```

**CSS patterns:**
```java
"#username"              // id
".error-msg"             // class
"input[type='text']"     // attribute
"div.card > button"      // direct child
```

**When to use XPath over CSS?**
- When you need to traverse UP the DOM (parent/ancestor)
- When you need to find element by text content
- CSS cannot do either of these

---

## Element Interaction

```java
driver.findElement(By.id("username")).sendKeys("tomsmith");  // type into input
driver.findElement(By.id("loginBtn")).click();               // click button/link
driver.findElement(By.id("username")).clear();               // clear input field
```

**`click()` vs `sendKeys()`:**
- `click()` — buttons, checkboxes, radio buttons, links
- `sendKeys()` — input fields, text areas

---

## Waits

### 3 types — know all of them

**1. Implicit Wait** — set once, applies globally
```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```
Waits up to 10s for every `findElement` call. If element appears earlier, moves on.

**2. Explicit Wait** ← most important
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".radius"))).click();
```
Waits for a specific condition on a specific element. Declare at class level, initialize in `@BeforeMethod`.

**Common ExpectedConditions:**
```java
ExpectedConditions.elementToBeClickable(locator)       // visible + enabled
ExpectedConditions.visibilityOfElementLocated(locator)  // visible in DOM
ExpectedConditions.presenceOfElementLocated(locator)    // in DOM, may not be visible
ExpectedConditions.urlContains("dashboard")
```

**3. Fluent Wait** — explicit wait with custom polling
```java
Wait<WebDriver> wait = new FluentWait<>(driver)
    .withTimeout(Duration.ofSeconds(30))
    .pollingEvery(Duration.ofSeconds(2))
    .ignoring(NoSuchElementException.class);
```
Use for slow-loading elements that appear unpredictably.

**Declare wait at class level:**
```java
public class SampleTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
}
```

**Chain vs store:**
```java
// Use once → chain directly
wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".radius"))).click();

// Use more than once → store in variable
WebElement flash = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
Assert.assertTrue(flash.getText().contains("success"));
```

---

## Assertions

```java
Assert.assertTrue(condition);                  // pass if true
Assert.assertFalse(condition);                 // pass if false
Assert.assertEquals(actual, expected);         // pass if equal
Assert.assertNotEquals(actual, expected);      // pass if not equal
```

---

## getText() vs getAttribute("value")

```java
element.getText()                  // visible text: <p>Hello</p> → "Hello"
element.getAttribute("value")     // attribute value: <input value="tomsmith">
element.getAttribute("href")      // works for any HTML attribute
element.getAttribute("class")
```

**Rule:** For input fields, always use `getAttribute("value")` to read typed text.
`getText()` returns empty string for input fields.

---

## Page Object Model (POM)

**Concept:** Each page = one class. Class has locators + actions. Test has data + assertions only.

```java
// LoginPage.java — locators + actions
public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    By usernameField = By.id("username");
    By passwordField = By.id("password");
    By loginButton   = By.cssSelector(".radius");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
}
```

```java
// LoginTest.java — test data + assertions only
public class LoginTest {
    WebDriver driver;
    WebDriverWait wait;
    LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        loginPage = new LoginPage(driver, wait);
    }

    @Test
    public void validLoginTest() {
        driver.get("https://the-internet.herokuapp.com/login");
        loginPage.login("tomsmith", "SuperSecretPassword!");
        WebElement flash = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash-messages")));
        Assert.assertTrue(flash.getText().contains("You logged into a secure area!"));
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
```

**Why POM?**
- Locator changes → fix in one page class, all tests work
- Tests are readable — `loginPage.login()` is self-explanatory
- No duplicate locators across test files

---

## Dropdowns

```java
// Only works with <select> tag
Select dropdown = new Select(driver.findElement(By.id("dropdown")));

dropdown.selectByVisibleText("Option 1");       // by text user sees
dropdown.selectByValue("1");                    // by value attribute in HTML
dropdown.selectByIndex(1);                      // by position, 0-based

dropdown.getFirstSelectedOption().getText();    // read currently selected option
```

**Never click `<option>` directly — always use `Select` class for `<select>` tags.**

---

## Alerts

```java
// Switch to alert
Alert alert = driver.switchTo().alert();

alert.accept();           // click OK
alert.dismiss();          // click Cancel
alert.getText();          // read alert message
alert.sendKeys("text");   // type into prompt (JS Prompt only)

// Always switch back after
driver.switchTo().defaultContent();
```

**3 alert types:**
- JS Alert — OK only → `alert.accept()`
- JS Confirm — OK + Cancel → `accept()` or `dismiss()`
- JS Prompt — text input + OK/Cancel → `sendKeys()` then `accept()`

---

## Frames / iFrames

```java
// Switch to frame
driver.switchTo().frame("frameName");     // by name attribute
driver.switchTo().frame(0);              // by index
driver.switchTo().frame(webElement);     // by WebElement

// Interact with elements inside frame
driver.findElement(By.id("someElement")).click();

// Switch back to main page
driver.switchTo().defaultContent();
```

**Common mistake:** Forgetting `defaultContent()` — after switching to a frame, all `findElement` calls look inside that frame only.

**Two iframes — how to switch between them:**
```java
driver.switchTo().frame(0);           // switch to iframe 1
// do stuff
driver.switchTo().defaultContent();   // back to main page
driver.switchTo().frame(1);           // switch to iframe 2
```

---

## Multiple Windows

```java
String mainWindow = driver.getWindowHandle();         // save current window
driver.findElement(By.linkText("Click Here")).click(); // opens new tab

Set<String> allWindows = driver.getWindowHandles();   // get all windows

for (String window : allWindows) {
    if (!window.equals(mainWindow)) {
        driver.switchTo().window(window);              // switch to new window
        break;
    }
}

// do stuff in new window
driver.switchTo().window(mainWindow);                  // switch back
```

---

## Common Exceptions

| Exception | When | Fix |
|---|---|---|
| `NoSuchElementException` | Element not found | Wrong locator or missing wait |
| `StaleElementReferenceException` | DOM changed after element found | Re-find element or use explicit wait |
| `ElementNotInteractableException` | Element exists but not clickable | Hidden/disabled — use wait |
| `TimeoutException` | Explicit wait condition never met | Wrong locator or element never appeared |
| `NoSuchWindowException` | Switching to closed window | Verify handle before switching |

**StaleElementReferenceException answer:**
> "Element was found but DOM changed before interaction. Fix: re-find just before action, or use `elementToBeClickable` which handles stale references. Also check if AJAX or page reload is happening between find and click."

---

## Dynamic Elements

Dynamic elements change their id, class, or position on every page load.

**How to handle:**

```java
// 1. Use contains() for partial match
driver.findElement(By.xpath("//div[contains(@id,'username')]"));
driver.findElement(By.xpath("//div[contains(@class,'btn')]"));

// 2. Use text content instead of id
driver.findElement(By.xpath("//button[contains(.,'Login')]"));

// 3. Use explicit wait — wait for element to appear
wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id,'user')]")));

// 4. Use parent-child relationship
driver.findElement(By.xpath("//div[@class='stable-parent']/button"));
```

**Interview answer:**
> "Dynamic elements change attributes on every load. I handle them using partial match with `contains()` in XPath, locating by text content, using stable parent elements, or wrapping with explicit wait to handle timing issues."

---

## Locators — Complete List

```java
driver.findElement(By.id("username"));                          // by id
driver.findElement(By.name("username"));                        // by name
driver.findElement(By.className("error-msg"));                  // by class
driver.findElement(By.cssSelector("#username"));                // CSS by id
driver.findElement(By.cssSelector(".error-msg"));               // CSS by class
driver.findElement(By.cssSelector("input[type='text']"));       // CSS by attribute
driver.findElement(By.cssSelector("div.card > button"));        // CSS child
driver.findElement(By.xpath("//input[@id='username']"));        // XPath by attribute
driver.findElement(By.xpath("//button[contains(.,'Login')]"));  // XPath by text
driver.findElement(By.linkText("Click Here"));                  // exact link text
driver.findElement(By.partialLinkText("Click"));                // partial link text
```

**linkText / partialLinkText** — only works on `<a>` anchor tags.

**Priority:** id → name → CSS → XPath → linkText

---

## CSS Selectors — Patterns

```java
"#username"                // id
".error-msg"               // class
"input[type='text']"       // attribute
"input[type='text'][name='username']"  // multiple attributes
"div.card > button"        // direct child
"div button"               // any descendant
"input:first-child"        // first child
```

---

## What is WebDriver — How it Works

```
Your Java Code
     ↓
WebDriver API (selenium-java)
     ↓
ChromeDriver
     ↓
Chrome Browser
```

1. Code calls `driver.findElement()` or `driver.click()`
2. Selenium converts to HTTP commands (W3C WebDriver Protocol)
3. ChromeDriver receives commands
4. ChromeDriver talks to Chrome
5. Result sent back to code

**Interview answer:**
> "WebDriver is an interface that controls the browser programmatically via the W3C WebDriver protocol through a browser-specific driver like ChromeDriver. WebDriverManager handles driver version automatically."

---

## Interview Questions

**Q: How do you handle multiple browser windows?**
A: Save current window with `getWindowHandle()`, trigger the new window, loop through `getWindowHandles()`, switch to the one that doesn't match the saved handle. Switch back with `switchTo().window(mainWindow)`.

**Q: What causes StaleElementReferenceException and how do you fix it?**
A: DOM changed after element was found — reference became invalid. Fix: re-find just before action, or use explicit wait with `elementToBeClickable`. Check for AJAX calls or page reloads between find and interact.

**Q: What is the difference between NoSuchElementException and TimeoutException?**
A: `NoSuchElementException` — element not in DOM at all. `TimeoutException` — explicit wait ran out of time waiting for a condition. Usually means wrong locator or element never appeared.

**Q: How do you handle a dropdown in Selenium?**
A: Use the `Select` class — `selectByVisibleText()`, `selectByValue()`, or `selectByIndex()`. Never click options directly — it's unreliable across browsers.

**Q: What are the 3 types of JavaScript alerts and how do you handle them?**
A: JS Alert (OK only) — `alert.accept()`. JS Confirm (OK + Cancel) — `accept()` or `dismiss()`. JS Prompt (text input) — `sendKeys()` then `accept()`. Always use `driver.switchTo().alert()` first.

**Q: How do you switch between two iframes?**
A: Switch to first frame with `driver.switchTo().frame(0)`, do the work, then `driver.switchTo().defaultContent()` to go back to main page, then `driver.switchTo().frame(1)` for the second.

**Q: What is POM and why use it?**
A: Page Object Model — each page has a class with locators and actions. Tests only have data and assertions. Benefit: locator changes in one place, all tests work. Tests are readable and maintainable.

**Q: Write a POM class for a login page** *(coding round)*
```java
public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    By usernameField = By.id("username");
    By passwordField = By.id("password");
    By loginButton   = By.cssSelector(".radius");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
}
```

**Q: What is WebDriverManager and why use it?**
A: It automatically downloads and manages the correct version of browser drivers (chromedriver, geckodriver). Without it, you'd manually download chromedriver matching your Chrome version — breaks every time Chrome updates.

**Q: Difference between `close()` and `quit()`?**
A: `close()` closes current tab only. `quit()` closes all browser windows and ends the session. Always use `quit()` in teardown.

**Q: Which locator do you prefer and why?**
A: `id` when available — unique, fast, stable. Fall back to CSS selector. Use XPath only when CSS can't do the job (text-based or upward traversal).

**Q: When would you use XPath over CSS Selector?**
A: When I need to find an element by its text content, or traverse to a parent/ancestor element. CSS can't do either.

**Q: What's the difference between `getText()` and `getAttribute("value")`?**
A: `getText()` returns visible text content of elements like div/span/p. `getAttribute("value")` reads the HTML attribute — needed for input fields since typed values are stored as the `value` attribute, not as text content.

**Q: Why put driver setup in `@BeforeMethod` and not in the test itself?**
A: Separation of concerns. If setup is in the test, every test duplicates that code. `@BeforeMethod` ensures a clean browser for each test automatically, and `@AfterMethod` always cleans up even if the test fails.

**Q: What are the 3 types of waits in Selenium?**
A: Implicit (global, applies to all findElement calls), Explicit (specific condition on specific element, most preferred), Fluent (like explicit but with custom polling interval and exception handling).

**Q: Why should you not mix implicit and explicit waits?**
A: They stack — combined timeout becomes unpredictable. Browser behavior varies. Tests become flaky and slow. Always use explicit wait only.

**Q: What is StaleElementReferenceException and how do you fix it?**
A: The element was found but the DOM changed before the action was performed — reference became invalid. Fix: re-find the element just before interacting, or use explicit wait with `elementToBeClickable` which handles it automatically.

**Q: Difference between `elementToBeClickable` and `visibilityOfElementLocated`?**
A: `visibilityOfElementLocated` — element is visible in DOM. `elementToBeClickable` — element is visible AND enabled (ready to interact). Use clickable for buttons, visible for reading text.
