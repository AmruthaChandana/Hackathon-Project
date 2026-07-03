package org.practo.tests;

import base.CommonCode;
import org.practo.pages.HomePage;
import org.practo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;

public class TC_001_LoginWithValidCredentials extends CommonCode {

    private HomePage homePage;
    private LoginPage loginPage;

    @Test
    public void verifyLoginWithValidCredentials() {

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        String mobileNumber = ExcelUtils.getCellData("TC_001", "Mobile");
        String password = ExcelUtils.getCellData("TC_001", "Password");
        String expectedProfileName = ExcelUtils.getCellData("TC_001", "ExpectedProfileName");
        openApplication();
        homePage.clickLogin();
        loginPage.login(mobileNumber, password);

        wait.until(driver ->
                loginPage.isProfileNameDisplayed(expectedProfileName)
                        || loginPage.isUserLoggedIn()
        );

        Assert.assertTrue(
                loginPage.isProfileNameDisplayed(expectedProfileName)
                        || loginPage.isUserLoggedIn(),
                "Login failed. User profile was not visible after login."
        );

        String actualProfileName = loginPage.getDisplayedProfileName(expectedProfileName);
        Assert.assertFalse(
                actualProfileName.isEmpty(),
                "Login failed. Profile name is empty after login."
        );
        Assert.assertTrue(
                actualProfileName.contains(expectedProfileName),
                "Profile name mismatch. Expected: "
                        + expectedProfileName
                        + ", Actual: "
                        + actualProfileName
        );

        System.out.println("TC_001 Passed: User logged in successfully as: " + actualProfileName);
    }
}