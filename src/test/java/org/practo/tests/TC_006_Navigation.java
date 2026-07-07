package org.practo.tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.practo.pages.LabTestsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_006_Navigation extends BaseTest {
    private static final Logger logger = LogManager.getLogger(TC_006_Navigation.class);

    @Test
    public void verifyNavigationToLabTestsPage() {
        logger.info("Starting TC_006 - Verify Navigation To Lab Tests Page");

        LabTestsPage labTestsPage = new LabTestsPage(driver);

        // Step 1: Click Lab Tests Menu
        logger.info("Clicking Lab Tests menu");
        labTestsPage.clickLabTestsMenu();

        commonCode.waitForUrlContains("tests");

        logger.info("Successfully navigated to Lab Tests page");

        // Step 2: Verify Lab Tests Page Opened
        Assert.assertTrue(
                labTestsPage.isLabTestsPageOpened(),
                "Lab Tests/Diagnostics page was not opened successfully"
        );

        logger.info("TC_006 Passed: Successfully navigated to Lab Tests/Diagnostics page");
        logger.info("Current URL : {}", driver.getCurrentUrl());
        logger.info("Page Title : {}", driver.getTitle());
    }
}