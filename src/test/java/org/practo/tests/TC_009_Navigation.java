package org.practo.tests;

import java.time.Duration;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.practo.pages.LabTestsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;

public class TC_009_Navigation extends BaseTest {

    @Test
    public void verifyNavigationToLabTestsPage() {
        // Read URL and wait time from config.properties
        String url = ConfigReader.getProperty("url");
        int explicitWait = Integer.parseInt(
                ConfigReader.getProperty("explicitWait"));
        driver.get(url);
        LabTestsPage labTestsPage = new LabTestsPage(driver);
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        // Click Lab Tests menu
        labTestsPage.clickLabTestsMenu();
        // Wait for navigation
        wait.until(ExpectedConditions.urlContains("tests"));
        // Validation
        Assert.assertTrue(
                labTestsPage.isLabTestsPageOpened(),
                "Lab Tests/Diagnostics page was not opened successfully");
        System.out.println("Successfully navigated to Lab Tests/Diagnostics page");
        System.out.println("Current URL : " + driver.getCurrentUrl());
        System.out.println("Page Title  : " + driver.getTitle());
    }
}