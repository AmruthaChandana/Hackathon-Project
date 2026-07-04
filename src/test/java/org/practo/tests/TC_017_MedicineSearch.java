package org.practo.tests;

import base.BaseTest;
import base.CommonCode;
import org.practo.pages.MedicinesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import org.practo.pages.HomePage;

public class TC_017_MedicineSearch extends CommonCode {
    @Test
    public void verifyMedicineSearch() {
        loadMedicineSheet();
        String medicineName =
                ExcelUtils.getCellData(
                        "TC_017",
                        "SearchMedicine");
        System.out.println(
                "Medicine Search : "
                        + medicineName);
        HomePage homePage =
                new HomePage(driver);

        homePage.clickSurgeriesButton();

        homePage.clickMedicines();

        MedicinesPage medicinesPage =
                new MedicinesPage(driver);
        medicinesPage.searchMedicine(medicineName);
        Assert.assertTrue(
                medicinesPage.getMedicineCount() > 0,
                "No medicines found."
        );
        medicinesPage.printFirstFiveMedicines();
    }
}