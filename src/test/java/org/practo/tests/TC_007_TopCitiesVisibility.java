package org.practo.tests;

import base.BaseTest;
import org.practo.pages.LabTestsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.WaitUtils;
import java.util.List;

public class TC_007_TopCitiesVisibility extends BaseTest {

    @Test
    public void verifyTopCitiesSectionVisible() {
        LabTestsPage labTestsPage = new LabTestsPage(driver);
        commonCode.openApplication();

        // Step 1: Navigate to Lab Tests page
        labTestsPage.clickLabTestsMenu();
        WaitUtils.waitForUrlContains(driver, "tests");

        // Step 2: Verify Top Cities section is visible
        Assert.assertTrue(
                labTestsPage.getTopCitiesCount() > 0,
                "Top Cities section is not visible"
        );

        // Step 3: Print Top Cities
        List<String> cities = labTestsPage.getTopCityNames();
        System.out.println("Top Cities:");
        for (String city : cities) {
            System.out.println(city);
        }
        System.out.println("Total Cities Found: " + cities.size());
        System.out.println("TC_007 Passed: Top Cities section is visible");
    }
}