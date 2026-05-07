package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.DriverFactory;

import java.time.Duration;

public class LoginTest  {
    WebDriver driver;
    WebDriverWait wait;
    LoginPage loginPage;

    @BeforeMethod
    public void setup()
    {
        driver = driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        loginPage = new LoginPage(driver, wait);

    }
    @Test
 public void loginTest()
    {
        driver.get("https://the-internet.herokuapp.com/login");
        loginPage.login("tomsmith","SuperSecretPassword!");
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash-messages"))).getText().contains("You logged into a secure area!"));

    }
    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
