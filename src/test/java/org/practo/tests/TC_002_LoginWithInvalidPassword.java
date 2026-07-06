package org.practo.tests;

import base.BaseTest;
import org.practo.pages.HomePage;
import org.practo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import utilities.WaitUtils;

public class TC_002_LoginWithInvalidPassword extends BaseTest {
    private HomePage homePage;
    private LoginPage loginPage;

    @Test
    public void verifyLoginWithInvalidPassword() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        String mobileNumber = ExcelUtils.getCellData("TC_002", "Mobile");
        String invalidPassword = ExcelUtils.getCellData("TC_002", "InvalidPassword");
        commonCode.openApplication();

        // Step 1: Click Login
        homePage.clickLogin();

        // Step 2: Enter invalid credentials
        loginPage.enterMobile(mobileNumber);
        loginPage.enterPassword(invalidPassword);

        // Step 3: Click Login button
        loginPage.clickLogin();
        WaitUtils.waitUntil(driver, driver -> !loginPage.isUserLoggedIn());

        // Step 4: Verify login failed
        Assert.assertFalse(
                loginPage.isUserLoggedIn(),
                "Login should NOT succeed with invalid password"
        );

        System.out.println("TC_002 Passed: Login unsuccessful as expected with invalid password");
    }
}