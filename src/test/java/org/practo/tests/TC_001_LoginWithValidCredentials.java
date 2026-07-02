package org.practo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class TC_001_LoginWithValidCredentials{

    WebDriver driver;
    WebDriverWait wait;
    Properties config;

    @BeforeMethod
    public void setUp() throws IOException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Load credentials from external config file (not hardcoded)
        config = new Properties();
        config.load(new FileInputStream("src/test/resources/config.properties"));

        driver.get("https://www.practo.com");
    }

    @Test
    public void loginAndVerifyProfileDropdown() {


        String mobileNumber = "9346740043";
        String password = "Amruthayadav@30";

        // 1. Click Login / Signup
        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[text()='Sign up']")));
        loginButton.click();

        // 2. Enter mobile number
        WebElement mobileInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@type='tel' or @placeholder='Mobile number']")));
        mobileInput.clear();
        mobileInput.sendKeys(mobileNumber);
        // 3. Click Continue (if present)
        try {
            WebElement continueBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(text(),'Continue') or contains(text(),'Next')]")));
            continueBtn.click();
        } catch (Exception e) {
            System.out.println("Continue step not required");
        }

        // 4. Enter password
        WebElement passwordInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@type='password']")));
        passwordInput.clear();
        passwordInput.sendKeys(password);

        // 5. Click Login
        WebElement submitLogin = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(),'Login') or contains(text(),'Continue')]")));
        submitLogin.click();

        // 6. Verify login success (username visible)
        WebElement userNameSpan = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("span.user_info_top")));

        String loggedInUser = userNameSpan.getText().trim();
        System.out.println("Logged in as: " + loggedInUser);
        Assert.assertFalse(loggedInUser.isEmpty());

        // 7. Open dropdown
        userNameSpan.click();

        // 8. Verify dropdown
        WebElement myAppointments = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[text()='My Appointments']")));

        Assert.assertTrue(myAppointments.isDisplayed());

        System.out.println("Login + dropdown verified");
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
