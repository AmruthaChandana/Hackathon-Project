package org.practo.tests;

import base.BaseTest;
import org.practo.pages.LabTestsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.WaitUtils;

public class TC_004_AddToCart extends BaseTest {

    @Test
    public void verifyThyroidProfileAddedToCart() {
        LabTestsPage page = new LabTestsPage(driver);
        String cityName = prop.getProperty("cityName");

        commonCode.openApplication();

        // Step 1: Navigate to Lab Tests page
        page.clickLabTestsMenu();

        // Step 2: Select city
        page.selectCity(cityName);
        WaitUtils.waitForUrlContains(driver, cityName.toLowerCase());

        // Step 3: Add Thyroid Profile to cart
        page.clickThyroidAddToCart();
        WaitUtils.waitUntil(driver, driver -> page.isAddedToCart());

        // Step 4: Verify Thyroid Profile is added to cart
        Assert.assertTrue(
                page.isAddedToCart(),
                "Thyroid Profile was not added to cart"
        );

        System.out.println("TC_004 Passed: Thyroid Profile added to cart successfully in " + cityName);
    }
}