package org.practo.tests;

import base.BaseTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.practo.pages.HomePage;
import org.practo.pages.SurgeryPage;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TC_013_NavigateToSurgeryPage extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(
                    TC_013_NavigateToSurgeryPage.class);

    private HomePage homePage;
    private SurgeryPage surgeryPage;

    @Test
    public void verifySurgeryPageAndPrintPopularTreatments() {

        logger.info(
                "Starting TC_013 - Navigate To Surgery Page");

        homePage =
                new HomePage(driver);

        surgeryPage =
                new SurgeryPage(driver);

        // Step 1: Click Surgeries Button

        try {

            logger.info(
                    "Clicking Surgeries button");

            commonCode.waitForClickable(
                    homePage.getSurgeriesButton());

            homePage.clickSurgeriesButton();

        } catch (Exception e) {

            logger.warn(
                    "Normal click failed. Using JavaScript click.");

            homePage.clickSurgeriesButtonUsingJS();
        }

        // Step 2: Validate Navigation

        commonCode.waitUntil(
                driver ->
                        commonCode.getCurrentUrl()
                                .contains("/care")
        );

        Assert.assertTrue(
                commonCode.getCurrentUrl()
                        .contains("/care"),
                "User is not navigated to Surgery page"
        );

        logger.info(
                "Navigated to Surgery page");

        logger.info(
                "Current URL: {}",
                commonCode.getCurrentUrl());

        // Step 3: Scroll to Treatments Offered Section

        boolean treatmentsSectionFound =
                false;

        for (int i = 0; i < 15; i++) {

            if (surgeryPage.isTreatmentsOfferedDisplayed()) {

                treatmentsSectionFound = true;

                break;
            }

            commonCode.scrollDown();
        }

        Assert.assertTrue(
                treatmentsSectionFound,
                "Treatments Offered section not found"
        );

        surgeryPage.scrollToTreatmentsOffered();

        logger.info(
                "Treatments Offered section found");

        // Step 4: Wait for Popular Grid

        commonCode.waitUntil(
                driver ->
                        surgeryPage.isPopularGridDisplayed()
        );

        // Step 5: Wait for Treatments

        commonCode.waitUntil(
                driver ->
                        surgeryPage.getPopularTreatmentsCount() > 0
        );

        // Step 6: Extract Treatments

        List<String> popularTreatments =
                surgeryPage.getPopularTreatments();

        logger.info(
                "Popular Treatments Offered:");

        for (int i = 0; i < popularTreatments.size(); i++) {

            logger.info(
                    "{}. {}",
                    (i + 1),
                    popularTreatments.get(i));
        }

        // Step 7: Validate Treatments

        Assert.assertTrue(
                popularTreatments.size() > 0,
                "No popular treatments were extracted"
        );

        logger.info(
                "Total Popular Treatments Found: {}",
                popularTreatments.size());

        // Step 8: Navigate Back

        driver.navigate().back();

        commonCode.waitUntil(
                driver ->
                        commonCode.getCurrentUrl()
                                .equalsIgnoreCase(
                                        prop.getProperty("url"))
                                ||
                                commonCode.getCurrentUrl()
                                        .contains("practo.com")
        );

        logger.info(
                "Returned back from Surgery page");

        logger.info(
                "TC_013 Passed: Surgery page opened, popular treatments printed, and redirected back");
    }
}