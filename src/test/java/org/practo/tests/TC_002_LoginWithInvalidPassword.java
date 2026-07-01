package org.practo.tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

        homePage = new HomePage();
        loginPage = new LoginPage();

        String mobileNumber = ExcelUtils.getCellData("TC_002", "Mobile");
        String invalidPassword = ExcelUtils.getCellData("TC_002", "InvalidPassword");

        openApplication();

        click(homePage.loginButton);

        type(loginPage.mobileNumberField, mobileNumber);

        type(loginPage.passwordField, invalidPassword);

        click(loginPage.loginSubmitButton);

        wait.until(driver ->
                isDisplayed(loginPage.headerUserName) ||
                        isDisplayed(loginPage.loginSubmitButton)
        );

        boolean isUserLoggedIn = isDisplayed(loginPage.headerUserName);

        Assert.assertFalse(
                isUserLoggedIn,
                "Login should NOT succeed with invalid password"
        );

        System.out.println("TC_002 Passed: Login unsuccessful as expected (invalid password)");
    }
}