package org.practo.tests;

import base.BaseTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.practo.pages.HomePage;
import org.practo.pages.LoginPage;

import org.testng.Assert;
import org.testng.annotations.Test;

import utilities.ExcelUtils;

public class TC_002_LoginWithInvalidPassword extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(
                    TC_002_LoginWithInvalidPassword.class);

    private HomePage homePage;
    private LoginPage loginPage;

    @Test
    public void verifyLoginWithInvalidPassword() {

        logger.info(
                "Starting TC_002 - Login With Invalid Password");

        homePage =
                new HomePage(driver);

        loginPage =
                new LoginPage(driver);

        String mobileNumber =
                ExcelUtils.getCellData(
                        "TC_002",
                        "Mobile");

        String invalidPassword =
                ExcelUtils.getCellData(
                        "TC_002",
                        "InvalidPassword");

        logger.info(
                "Clicking Login button");

        // Step 1: Click Login

        homePage.clickLogin();

        logger.info(
                "Entering invalid login credentials");

        // Step 2: Enter Invalid Credentials

        loginPage.enterMobile(
                mobileNumber);

        loginPage.enterPassword(
                invalidPassword);

        logger.info(
                "Clicking Login button with invalid password");

        // Step 3: Click Login Button

        loginPage.clickLogin();

        commonCode.waitUntil(
                driver ->
                        !loginPage.isUserLoggedIn()
        );

        // Step 4: Verify Login Failed

        Assert.assertFalse(
                loginPage.isUserLoggedIn(),
                "Login should NOT succeed with invalid password"
        );

        logger.info(
                "TC_002 Passed: Login unsuccessful as expected with invalid password");
    }
}
