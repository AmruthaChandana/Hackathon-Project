package org.practo.tests;

import base.CommonCode;
import org.practo.pages.MedicinesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import org.practo.pages.HomePage;

public class TC_018_MedicineAddToCart extends CommonCode {

    @Test
    public void verifyMedicineAddToCart() {

        loadMedicineSheet();

        String medicineName =
                ExcelUtils.getCellData(
                        "TC_018",
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

        driver.findElements(
                        org.openqa.selenium.By.cssSelector(
                                ".search-bar__results a"))
                .get(0)
                .click();

        medicinesPage.clickAddToCart();

        System.out.println(
                "Add To Cart button is functioning properly");
    }
}