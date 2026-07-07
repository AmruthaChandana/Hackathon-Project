package org.practo.tests;

import base.BaseTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.practo.pages.LabTestsPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_003_FindTest extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(
                    TC_003_FindTest.class);

    @Test
    public void verifyDiabetesHealthConcernNavigation() {

        logger.info(
                "Starting TC_003 - Verify Diabetes Health Concern Navigation");

        LabTestsPage page =
                new LabTestsPage(driver);

        // Step 1: Navigate to Lab Tests Page

        logger.info(
                "Clicking Lab Tests menu");

        page.clickLabTestsMenu();

        // Step 2: Select City

        String cityName =
                prop.getProperty("cityName");

        logger.info(
                "Selecting city: {}",
                cityName);

        page.selectCity(
                cityName);

        commonCode.waitForUrlContains(
                "bangalore");

        // Step 3: Click Diabetes Health Concern

        logger.info(
                "Clicking Diabetes Health Concern");

        page.clickDiabetesHealthConcern();

        // Step 4: Validate Navigation

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains("diabetes-checkup"),
                "User is not navigated to Diabetes Checkup page"
        );

        logger.info(
                "Successfully navigated to Diabetes Checkup page");

        logger.info(
                "Current URL: {}",
                driver.getCurrentUrl());

        logger.info(
                "TC_003 Passed: User navigated to Diabetes Checkup page successfully");
    }
}