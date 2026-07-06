package org.practo.tests;

import base.BaseTest;
import org.practo.pages.LabTestsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.WaitUtils;

public class TC_003_FindTest extends BaseTest {

    @Test
    public void verifyDiabetesHealthConcernNavigation() {
        LabTestsPage page = new LabTestsPage(driver);
        commonCode.openApplication();

        // Step 1: Navigate to Lab Tests page
        page.clickLabTestsMenu();

        // Step 2: Select city
        page.selectCity(prop.getProperty("cityName"));
        WaitUtils.waitForUrlContains(driver, "bangalore");

        // Step 3: Click Diabetes health concern
        page.clickDiabetesHealthConcern();

        // Step 4: Validate navigation to Diabetes Checkup page
        Assert.assertTrue(
                driver.getCurrentUrl().contains("diabetes-checkup"),
                "User is not navigated to Diabetes Checkup page"
        );

        System.out.println("TC_003 Passed: User navigated to Diabetes Checkup page successfully");
    }
}