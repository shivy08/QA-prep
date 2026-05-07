package tests;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.DriverFactory;

import java.time.Duration;
import java.util.Objects;

public class SampleTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {

        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    @Test
    public void verifyGoogleTitle() {
        // driver.get("https://the-internet.herokuapp.com/dropdown");
        // Step 1 — wrap the <select> element in Select class
        //Select dropdown = new Select(driver.findElement(By.id("dropdown")));
        // Step 2 — three ways to select
        // dropdown.selectByVisibleText("Option 1");  // selects by text the user sees on screen
        // dropdown.selectByValue("1");               // selects by the value attribute in HTML
        //dropdown.selectByIndex(1);                 // selects by position, 0-based (0 = first option)
        // dropdown.getFirstSelectedOption().getText();  // returns currently selected option text
        // driver.findElement(By.id("username")).sendKeys("tomsmith");
        //  Assert.assertEquals(driver.findElement(By.id("username")).getAttribute("value"), "tomsmith");
        // driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //  wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".radius"))).click();

        //  Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash-messages"))).getText().contains("You logged into a secure area!"));
        //WebElement flash = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash-messages")));
        // Assert.assertTrue(flash.getText().contains("You logged into a secure area!"));
        //Assert.assertTrue(driver.findElement(By.id("flash-messages")).getText().contains("You logged into a secure area!"));
        //Assert.assertTrue(driver.getTitle().contains("Google"));
        //driver.findElement(By.name("username")).click();
        //driver.findElement(By.id("username")).sendKeys("tomsmith");
        //driver.findElement(By.cssSelector(".radius")).click();
        // driver.findElement(By.xpath("//button[contains(.,'Login')]")).click();

       /* driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'Click for JS Alert')]"))).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result"))).getText(), "You successfully clicked an alert");
        //@AfterMethod
        //  public void tearDown() {
        // DriverFactory.quitDriver();
        //}
        */
        driver.get("https://the-internet.herokuapp.com/iframe");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".tox-notification__dismiss"))).click();
        driver.switchTo().frame("mce_0_ifr");
        driver.findElement(By.tagName("body")).sendKeys("Hello");
        driver.switchTo().defaultContent();
    }
}
