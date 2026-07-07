package org.practo.tests;

import base.BaseTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.practo.pages.CorporateWellnessPage;
import org.practo.pages.HomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_020_CorporateMenuOptions extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(
                    TC_020_CorporateMenuOptions.class);

    private HomePage homePage;
    private CorporateWellnessPage corporatePage;

    @Test
    public void verifyCorporateMenuOptions() {

        logger.info(
                "Starting TC_020 - Verify Corporate Menu Options");

        homePage =
                new HomePage(driver);

        // Step 1: Click For Corporates

        try {

            logger.info(
                    "Clicking For Corporates menu");

            commonCode.waitForClickable(
                    homePage.getForCorporates());

            homePage.clickForCorporates();

        } catch (Exception e) {

            logger.warn(
                    "Normal click failed. Using JavaScript click.");

            homePage.clickForCorporatesUsingJS();
        }

        // Step 2: Click Health & Wellness Plans

        try {

            logger.info(
                    "Clicking Health & Wellness Plans");

            commonCode.waitForClickable(
                    homePage.getHealthAndWellnessPlans());

            homePage.clickHealthAndWellnessPlans();

        } catch (Exception e) {

            logger.warn(
                    "Normal click failed. Using JavaScript click.");

            homePage.clickHealthAndWellnessPlansUsingJS();
        }

        // Step 3: Validate Corporate Wellness Page Opened

        commonCode.waitUntil(
                driver ->
                        commonCode.getCurrentUrl()
                                .contains("/plus/corporate")
        );

        Assert.assertTrue(
                commonCode.getCurrentUrl()
                        .contains("/plus/corporate"),
                "Corporate Wellness page is not opened"
        );

        logger.info(
                "Navigated to Corporate Wellness page");

        logger.info(
                "Current URL : {}",
                commonCode.getCurrentUrl());

        corporatePage =
                new CorporateWellnessPage(driver);

        // Step 4: Click Our Services

        corporatePage.scrollToTop();
        corporatePage.clickOurServices();
        corporatePage.scrollDown();

        logger.info(
                "Clicked Our Services");

        // Step 5: Click Practo Ecosystem

        corporatePage.scrollToTop();
        corporatePage.clickPractoEcosystem();
        corporatePage.scrollDown();

        logger.info(
                "Clicked Practo Ecosystem");

        // Step 6: Click Product Capabilities

        corporatePage.scrollToTop();
        corporatePage.clickProductCapabilities();
        corporatePage.scrollDown();

        logger.info(
                "Clicked Product Capabilities");

        // Step 7: Click Testimonials

        corporatePage.scrollToTop();
        corporatePage.clickTestimonials();
        corporatePage.scrollDown();

        logger.info(
                "Clicked Testimonials");

        // Step 8: Click FAQs

        corporatePage.scrollToTop();
        corporatePage.clickFAQs();
        corporatePage.scrollDown();

        logger.info(
                "Clicked FAQs");

        // Step 9: Verify User Remains On Corporate Wellness Page

        Assert.assertTrue(
                commonCode.getCurrentUrl()
                        .contains("/plus/corporate"),
                "User moved away from Corporate Wellness page"
        );

        logger.info(
                "Verified user remains on Corporate Wellness page");

        logger.info(
                "TC_020 Passed: Corporate menu options are functioning properly.");
    }
}