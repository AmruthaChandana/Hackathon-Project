package org.practo.tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.practo.pages.MedicinesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;

public class TC_018_MedicineSearchInvalid extends BaseTest {
    private static final Logger logger = LogManager.getLogger(TC_018_MedicineSearchInvalid.class);

    @Test
    public void verifyInvalidMedicineSearch() {
        logger.info("Starting TC_018 - Invalid Medicine Search");
        commonCode.loadMedicineSheet();
        String medicineName = ExcelUtils.getCellData("TC_018", "SearchMedicine");

        Assert.assertFalse(
                medicineName.isEmpty(),
                "Invalid medicine name is empty in Excel"
        );

        logger.info("Searching Invalid Medicine : {}", medicineName);
        MedicinesPage medicinesPage = new MedicinesPage(driver);

        // Step 1: Open Medicines Page
        logger.info("Opening Medicines page");
        driver.get(prop.getProperty("url2"));

        // Step 2: Validate Medicines Page Opened
        commonCode.waitUntil(
                driver ->
                        commonCode.getCurrentUrl().toLowerCase().contains("order") ||
                                commonCode.getCurrentUrl().toLowerCase().contains("medicine") ||
                                commonCode.getCurrentUrl().toLowerCase().contains("medicines")
        );

        logger.info("Navigated to Medicines page");
        logger.info("Current URL: {}", commonCode.getCurrentUrl());

        // Step 3: Search Invalid Medicine
        logger.info("Searching invalid medicine: {}", medicineName);
        medicinesPage.searchInvalidMedicine(medicineName);

        // Step 4: Validate No Results Found
        Assert.assertFalse(
                medicinesPage.isMedicinePresentInResults(
                        medicineName),
                "Invalid medicine is displayed in search results."
        );

        logger.info("No matching medicine found for invalid search keyword");
        logger.info("TC_018 Passed: No matching medicine found for invalid medicine.");
    }
}