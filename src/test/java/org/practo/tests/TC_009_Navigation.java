package org.practo.tests;

import java.time.Duration;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.practo.pages.LabTestsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_009_Navigation extends BaseTest {

    @Test
    public void verifyNavigationToLabTestsPage() {

        driver.get("https://www.practo.com/");

        LabTestsPage labTestsPage = new LabTestsPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        labTestsPage.clickLabTestsMenu();

        wait.until(ExpectedConditions.urlContains("tests"));

        Assert.assertTrue(
                labTestsPage.isLabTestsPageOpened(),
                "Lab Tests/Diagnostics page was not opened successfully");

        System.out.println("Successfully navigated to Lab Tests/Diagnostics page");
        System.out.println("Current URL : " + driver.getCurrentUrl());
        System.out.println("Page Title  : " + driver.getTitle());
    }
}