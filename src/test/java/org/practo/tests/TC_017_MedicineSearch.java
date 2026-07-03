package org.practo.tests;

import base.BaseTest;
import org.practo.pages.MedicinesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;

public class TC_017_MedicineSearch extends BaseTest {
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
        driver.get("https://www.practo.com/order");
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