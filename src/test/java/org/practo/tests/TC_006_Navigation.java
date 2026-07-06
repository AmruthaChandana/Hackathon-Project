package org.practo.tests;

import base.BaseTest;
import org.practo.pages.LabTestsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.WaitUtils;

public class TC_006_Navigation extends BaseTest {

    @Test
    public void verifyNavigationToLabTestsPage() {
        LabTestsPage labTestsPage = new LabTestsPage(driver);

        commonCode.openApplication();

        // Step 1: Click Lab Tests menu
        labTestsPage.clickLabTestsMenu();
        WaitUtils.waitForUrlContains(driver, "tests");

        // Step 2: Verify Lab Tests page opened
        Assert.assertTrue(
                labTestsPage.isLabTestsPageOpened(),
                "Lab Tests/Diagnostics page was not opened successfully"
        );

        System.out.println("TC_006 Passed: Successfully navigated to Lab Tests/Diagnostics page");
        System.out.println("Current URL : " + driver.getCurrentUrl());
        System.out.println("Page Title  : " + driver.getTitle());
    }
}