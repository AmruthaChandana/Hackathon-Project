package org.practo.tests;

import base.CommonCode;
import org.practo.pages.MedicinesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import org.practo.pages.HomePage;

public class TC_019_MedicineSearchInvalid extends CommonCode {

    @Test
    public void verifyInvalidMedicineSearch() {

        loadMedicineSheet();

        String medicineName =
                ExcelUtils.getCellData(
                        "TC_019",
                        "SearchMedicine");

        System.out.println(
                "Searching Invalid Medicine : "
                        + medicineName);

        HomePage homePage =
                new HomePage(driver);

        homePage.clickSurgeriesButton();

        homePage.clickMedicines();

        MedicinesPage medicinesPage =
                new MedicinesPage(driver);

        medicinesPage.searchMedicine(medicineName);

        Assert.assertFalse(
                medicinesPage.isMedicinePresentInResults(
                        medicineName),
                "Invalid medicine is displayed in search results."
        );

        System.out.println(
                "No matching medicine found for invalid medicine. Test Passed."
        );
    }
}