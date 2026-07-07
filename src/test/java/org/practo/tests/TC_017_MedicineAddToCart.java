package org.practo.tests;

import base.BaseTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.practo.pages.MedicinesPage;

import org.testng.Assert;
import org.testng.annotations.Test;

import utilities.ExcelUtils;

public class TC_017_MedicineAddToCart extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(
                    TC_017_MedicineAddToCart.class);

    @Test
    public void verifyMedicineAddToCart() {

        logger.info(
                "Starting TC_017 - Medicine Add To Cart");

        commonCode.loadMedicineSheet();

        String medicineName =
                ExcelUtils.getCellData(
                        "TC_017",
                        "SearchMedicine");

        Assert.assertFalse(
                medicineName.isEmpty(),
                "Medicine name is empty in Excel"
        );

        logger.info(
                "Medicine Search : {}",
                medicineName);

        MedicinesPage medicinesPage =
                new MedicinesPage(driver);

        // Step 1: Open Medicines Page

        logger.info(
                "Opening Medicines page");

        driver.get(
                prop.getProperty("url2"));

        // Step 2: Validate Medicines Page Opened

        commonCode.waitUntil(
                driver ->
                        commonCode.getCurrentUrl()
                                .toLowerCase()
                                .contains("order")
                                ||
                                commonCode.getCurrentUrl()
                                        .toLowerCase()
                                        .contains("medicine")
                                ||
                                commonCode.getCurrentUrl()
                                        .toLowerCase()
                                        .contains("medicines")
        );

        logger.info(
                "Navigated to Medicines page");

        logger.info(
                "Current URL: {}",
                commonCode.getCurrentUrl());

        // Step 3: Search Medicine

        logger.info(
                "Searching medicine: {}",
                medicineName);

        medicinesPage.searchMedicine(
                medicineName);

        // Step 4: Validate Medicines Found

        int medicineCount =
                medicinesPage.getMedicineCount();

        Assert.assertTrue(
                medicineCount > 0,
                "No medicines found for search: "
                        + medicineName
        );

        logger.info(
                "Medicines found: {}",
                medicineCount);

        // Step 5: Click First Medicine

        medicinesPage.clickFirstMedicineFromResults();

        logger.info(
                "Clicked first medicine from search results");

        // Step 6: Wait For Product Page

        commonCode.waitUntil(
                driver ->
                        commonCode.getCurrentUrl()
                                .toLowerCase()
                                .contains("order")
                                ||
                                commonCode.getCurrentUrl()
                                        .toLowerCase()
                                        .contains("medicine")
                                ||
                                commonCode.getCurrentUrl()
                                        .toLowerCase()
                                        .contains("product")
        );

        logger.info(
                "Medicine product page opened");

        // Step 7: Click Add To Cart

        medicinesPage.clickAddToCart();

        logger.info(
                "Clicked Add To Cart button");

        // Step 8: Validate Cart

        Assert.assertTrue(
                medicinesPage.isCartDisplayed(),
                "Cart is not displayed after adding medicine"
        );

        logger.info(
                "Cart displayed successfully");

        logger.info(
                "TC_017 Passed: Add To Cart button is functioning properly");
    }
}