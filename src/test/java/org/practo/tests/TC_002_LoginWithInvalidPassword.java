package org.practo.tests;

import base.BaseTest;
import org.practo.pages.HomePage;
import org.practo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;

public class TC_002_LoginWithInvalidPassword extends BaseTest {

    HomePage homePage;
    LoginPage loginPage;

    @Test
    public void verifyLoginWithInvalidPassword() {

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);

        String mobileNumber =
                ExcelUtils.getCellData("TC_002", "Mobile");

        String invalidPassword =
                ExcelUtils.getCellData("TC_002", "InvalidPassword");

        openApplication();

        // Step 1: Click Login
        homePage.clickLogin();

        // Step 2: Enter Credentials
        loginPage.enterMobile(mobileNumber);
        loginPage.enterPassword(invalidPassword);

        //Step 3: Click Login Button
        loginPage.clickLogin();
        wait.until(driver ->
                !loginPage.isUserLoggedIn()
        );

        //Step 4: Verify Login Failed
        Assert.assertFalse(
                loginPage.isUserLoggedIn(),
                "Login should NOT succeed with invalid password"
        );

        System.out.println(
                "TC_002 Passed: Login unsuccessful as expected with invalid password"
        );
    }
}