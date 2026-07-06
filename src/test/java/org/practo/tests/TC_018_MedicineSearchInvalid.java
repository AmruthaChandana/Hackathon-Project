package org.practo.tests;

import base.BaseTest;
import org.practo.pages.MedicinesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import utilities.WaitUtils;

public class TC_018_MedicineSearchInvalid extends BaseTest {

    @Test
    public void verifyInvalidMedicineSearch() {
        commonCode.loadMedicineSheet();
        String medicineName = ExcelUtils.getCellData("TC_018", "SearchMedicine");

        Assert.assertFalse(
                medicineName.isEmpty(),
                "Invalid medicine name is empty in Excel"
        );

        System.out.println("Searching Invalid Medicine : " + medicineName);
        MedicinesPage medicinesPage = new MedicinesPage(driver);

        // Step 1: Open Medicines page using url2 from config.properties
        driver.get(prop.getProperty("url2"));

        // Step 2: Validate Medicines page opened
        WaitUtils.waitUntil(driver, driver ->
                commonCode.getCurrentUrl().toLowerCase().contains("order")
                        || commonCode.getCurrentUrl().toLowerCase().contains("medicine")
                        || commonCode.getCurrentUrl().toLowerCase().contains("medicines")
        );

        System.out.println("Navigated to Medicines page");
        System.out.println("Current URL: " + commonCode.getCurrentUrl());

        // Step 3: Search invalid medicine
        medicinesPage.searchInvalidMedicine(medicineName);

        // Step 4: Validate invalid medicine is not present
        Assert.assertFalse(
                medicinesPage.isMedicinePresentInResults(medicineName),
                "Invalid medicine is displayed in search results."
        );

        System.out.println("TC_018 Passed: No matching medicine found for invalid medicine.");
    }
}