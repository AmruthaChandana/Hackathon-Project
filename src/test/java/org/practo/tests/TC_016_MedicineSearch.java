package org.practo.tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.practo.pages.MedicinesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;

public class TC_016_MedicineSearch extends BaseTest {
    private static final Logger logger =
            LogManager.getLogger(
                    TC_016_MedicineSearch.class);

    private MedicinesPage medicinesPage;

    @Test
    public void verifyMedicineSearch() {
        logger.info("Starting TC_016 - Medicine Search");
        commonCode.loadMedicineSheet();
        String medicineName =
                ExcelUtils.getCellData(
                        "TC_016",
                        "SearchMedicine");

        Assert.assertFalse(
                medicineName.isEmpty(),
                "Medicine name is empty in Excel"
        );

        logger.info("Medicine Search : {}", medicineName);
        medicinesPage = new MedicinesPage(driver);

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

        Assert.assertTrue(
                commonCode.getCurrentUrl().toLowerCase().contains("order") ||
                        commonCode.getCurrentUrl().toLowerCase().contains("medicine") ||
                        commonCode.getCurrentUrl().toLowerCase().contains("medicines"),
                "User is not navigated to Medicines page"
        );

        logger.info("Navigated to Medicines page");
        logger.info("Current URL: {}", commonCode.getCurrentUrl());

        // Step 3: Search Medicine
        logger.info("Searching medicine: {}", medicineName);
        medicinesPage.searchMedicine(medicineName);

        // Step 4: Validate Search Result
        Assert.assertTrue(
                medicinesPage.isMedicinePresentInResults(medicineName),
                "Medicine is not present in search results: " + medicineName
        );

        String matchedMedicine = medicinesPage.getMatchedMedicineName(medicineName);

        Assert.assertFalse(
                matchedMedicine.isEmpty(),
                "Matched medicine name is empty"
        );

        logger.info("Medicine found in results: {}", matchedMedicine);

        // Step 5: Validate Medicine Count
        int medicineCount = medicinesPage.getMedicineCount();
        Assert.assertTrue(
                medicineCount > 0,
                "No medicines found for search: "
                        + medicineName
        );

        logger.info("Total medicines found: {}", medicineCount);

        // Step 6: Print First 5 Medicines
        logger.info("Printing first five medicines");
        medicinesPage.printFirstFiveMedicines();
        logger.info("TC_016 Passed: Medicine search completed successfully");
    }
}