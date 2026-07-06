package org.practo.tests;

import base.BaseTest;
import org.practo.pages.MedicinesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import utilities.WaitUtils;

public class TC_017_MedicineAddToCart extends BaseTest {

    @Test
    public void verifyMedicineAddToCart() {
        commonCode.loadMedicineSheet();
        String medicineName = ExcelUtils.getCellData("TC_017", "SearchMedicine");

        Assert.assertFalse(
                medicineName.isEmpty(),
                "Medicine name is empty in Excel"
        );

        System.out.println("Medicine Search : " + medicineName);
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

        // Step 3: Search medicine
        medicinesPage.searchMedicine(medicineName);

        // Step 4: Validate medicines are found
        int medicineCount = medicinesPage.getMedicineCount();

        Assert.assertTrue(
                medicineCount > 0,
                "No medicines found for search: " + medicineName
        );

        // Step 5: Click first medicine from search results
        medicinesPage.clickFirstMedicineFromResults();
        System.out.println("Clicked first medicine from search results");

        // Step 6: Wait after selecting first medicine
        WaitUtils.waitUntil(driver, driver ->
                commonCode.getCurrentUrl().toLowerCase().contains("order")
                        || commonCode.getCurrentUrl().toLowerCase().contains("medicine")
                        || commonCode.getCurrentUrl().toLowerCase().contains("product")
        );

        // Step 7: Click Add To Cart
        medicinesPage.clickAddToCart();

        // Step 8: Validate cart
        Assert.assertTrue(
                medicinesPage.isCartDisplayed(),
                "Cart is not displayed after adding medicine"
        );

        System.out.println("TC_017 Passed: Add To Cart button is functioning properly");
    }
}