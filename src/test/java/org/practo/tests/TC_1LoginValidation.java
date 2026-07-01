package org.practo.tests;

import org.practo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;

public class TC_1LoginValidation extends BaseTest {

    @Test
    public void loginTest() throws InterruptedException {

        // Step 1: Open Practo
        driver.get("https://www.practo.com/");

        LoginPage loginPage = new LoginPage(driver);

        // Step 2: Click Login
        loginPage.clickSignIn();
        Thread.sleep(2000);

        // Step 3: Enter Credentials
        loginPage.enterMobile("9346740043");
        loginPage.enterPassword("Amruthayadav@30");

        // Step 4: Click Login
        loginPage.clickLogin();
        Thread.sleep(3000);

        // Step 5: Validation
        boolean status = loginPage.isLoginSuccessful();

        if (status) {
            System.out.println("Login Successful");
        } else {
            System.out.println("Login Failed");
        }

        // Assertion (Final result)
        Assert.assertTrue(status, "Login test failed!");
    }
}