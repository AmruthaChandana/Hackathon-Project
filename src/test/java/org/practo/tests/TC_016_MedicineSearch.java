package org.practo.tests;

import base.BaseTest;
import org.practo.pages.MedicinesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import utilities.WaitUtils;

public class TC_016_MedicineSearch extends BaseTest {
    private MedicinesPage medicinesPage;

    @Test
    public void verifyMedicineSearch() {
        commonCode.loadMedicineSheet();
        String medicineName = ExcelUtils.getCellData("TC_016", "SearchMedicine");

        Assert.assertFalse(
                medicineName.isEmpty(),
                "Medicine name is empty in Excel"
        );

        System.out.println("Medicine Search : " + medicineName);
        medicinesPage = new MedicinesPage(driver);

        // Step 1: Open Medicines page using url2 from config.properties
        driver.get(prop.getProperty("url2"));

        // Step 2: Validate Medicines page opened
        WaitUtils.waitUntil(driver, driver ->
                commonCode.getCurrentUrl().toLowerCase().contains("order")
                        || commonCode.getCurrentUrl().toLowerCase().contains("medicine")
                        || commonCode.getCurrentUrl().toLowerCase().contains("medicines")
        );

        Assert.assertTrue(
                commonCode.getCurrentUrl().toLowerCase().contains("order")
                        || commonCode.getCurrentUrl().toLowerCase().contains("medicine")
                        || commonCode.getCurrentUrl().toLowerCase().contains("medicines"),
                "User is not navigated to Medicines page"
        );

        System.out.println("Navigated to Medicines page");
        System.out.println("Current URL: " + commonCode.getCurrentUrl());

        // Step 3: Search medicine
        medicinesPage.searchMedicine(medicineName);

        // Step 4: Validate searched medicine is present in results
        Assert.assertTrue(
                medicinesPage.isMedicinePresentInResults(medicineName),
                "Medicine is not present in search results: " + medicineName
        );

        String matchedMedicine = medicinesPage.getMatchedMedicineName(medicineName);

        Assert.assertFalse(
                matchedMedicine.isEmpty(),
                "Matched medicine name is empty"
        );

        System.out.println("Medicine found in results: " + matchedMedicine);

        // Step 5: Validate medicine count
        int medicineCount = medicinesPage.getMedicineCount();

        Assert.assertTrue(
                medicineCount > 0,
                "No medicines found for search: " + medicineName
        );

        // Step 6: Print first 5 medicines
        medicinesPage.printFirstFiveMedicines();

        System.out.println("TC_016 Passed: Medicine search completed successfully");
    }
}