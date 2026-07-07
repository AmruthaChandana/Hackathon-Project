package org.practo.tests;

import base.BaseTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.practo.pages.LabTestsPage;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TC_007_TopCitiesVisibility extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(
                    TC_007_TopCitiesVisibility.class);

    @Test
    public void verifyTopCitiesSectionVisible() {

        logger.info(
                "Starting TC_007 - Verify Top Cities Visibility");

        LabTestsPage labTestsPage =
                new LabTestsPage(driver);

        // Step 1: Navigate to Lab Tests Page

        logger.info(
                "Navigating to Lab Tests page");

        labTestsPage.clickLabTestsMenu();

        commonCode.waitForUrlContains(
                "tests");

        // Step 2: Verify Top Cities Section

        Assert.assertTrue(
                labTestsPage.getTopCitiesCount() > 0,
                "Top Cities section is not visible"
        );

        logger.info(
                "Top Cities section is visible"
        );

        // Step 3: Print Top Cities

        List<String> cities =
                labTestsPage.getTopCityNames();

        logger.info("Top Cities:");

        for (String city : cities) {

            logger.info(city);
        }

        logger.info(
                "Total Cities Found: {}",
                cities.size()
        );

        logger.info(
                "TC_007 Passed: Top Cities section is visible");
    }
}