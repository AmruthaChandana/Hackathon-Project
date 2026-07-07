package org.practo.tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.practo.pages.HomePage;
import org.practo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;

public class TC_001_LoginWithValidCredentials extends BaseTest {
    private static final Logger logger = LogManager.getLogger(TC_001_LoginWithValidCredentials.class);
    private HomePage homePage;
    private LoginPage loginPage;

    @Test
    public void verifyLoginWithValidCredentials() {
        logger.info("Starting TC_001 - Verify Login With Valid Credentials");

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);

        String mobileNumber = ExcelUtils.getCellData("TC_001", "Mobile");
        String password = ExcelUtils.getCellData("TC_001", "Password");
        String expectedProfileName = ExcelUtils.getCellData("TC_001", "ExpectedProfileName");

        logger.info("Clicking Login button");
        homePage.clickLogin();

        logger.info("Logging in with valid credentials");
        loginPage.login(mobileNumber, password);

        commonCode.waitUntil(driver ->
                loginPage.isProfileNameDisplayed(expectedProfileName) || loginPage.isUserLoggedIn()
        );

        Assert.assertTrue(
                loginPage.isProfileNameDisplayed(expectedProfileName) || loginPage.isUserLoggedIn(),
                "Login failed. User profile was not visible after login."
        );

        String actualProfileName = loginPage.getDisplayedProfileName(expectedProfileName);

        Assert.assertFalse(
                actualProfileName.isEmpty(),
                "Login failed. Profile name is empty after login."
        );

        Assert.assertTrue(
                actualProfileName.contains(expectedProfileName),
                "Profile name mismatch. Expected: " + expectedProfileName + ", Actual: " + actualProfileName
        );

        logger.info("TC_001 Passed: User logged in successfully as: {}", actualProfileName);
    }
}