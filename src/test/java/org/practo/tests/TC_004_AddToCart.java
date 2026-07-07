package org.practo.tests;

import base.BaseTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.practo.pages.LabTestsPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_004_AddToCart extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(
                    TC_004_AddToCart.class);

    @Test
    public void verifyThyroidProfileAddedToCart() {

        logger.info(
                "Starting TC_004 - Verify Thyroid Profile Add To Cart");

        LabTestsPage page =
                new LabTestsPage(driver);

        String cityName =
                prop.getProperty("cityName");

        // Step 1: Navigate to Lab Tests Page

        logger.info(
                "Clicking Lab Tests menu");

        page.clickLabTestsMenu();

        // Step 2: Select City

        logger.info(
                "Selecting city: {}",
                cityName);

        page.selectCity(
                cityName);

        commonCode.waitForUrlContains(
                cityName.toLowerCase()
        );

        logger.info(
                "Successfully navigated to city page: {}",
                cityName);

        // Step 3: Add Thyroid Profile To Cart

        logger.info(
                "Adding Thyroid Profile to cart");

        page.clickThyroidAddToCart();

        commonCode.waitUntil(
                driver ->
                        page.isAddedToCart()
        );

        // Step 4: Verify Thyroid Profile Added

        Assert.assertTrue(
                page.isAddedToCart(),
                "Thyroid Profile was not added to cart"
        );

        logger.info(
                "Thyroid Profile added successfully");

        logger.info(
                "Current URL: {}",
                driver.getCurrentUrl()
        );

        logger.info(
                "TC_004 Passed: Thyroid Profile added to cart successfully in {}",
                cityName
        );
    }
}