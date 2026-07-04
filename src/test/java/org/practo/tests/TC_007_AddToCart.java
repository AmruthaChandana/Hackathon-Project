package org.practo.tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.practo.pages.LabTestsPage;
import utilities.ConfigReader;
import java.time.Duration;

public class TC_007_AddToCart extends BaseTest {

    @Test
    public void verifyThyroidProfileAddedToCart() throws InterruptedException {
        driver.get(ConfigReader.getProperty("url"));
        LabTestsPage page = new LabTestsPage(driver);
        WebDriverWait wait = new WebDriverWait(driver,
                        Duration.ofSeconds(
                                Integer.parseInt(
                                        ConfigReader.getProperty("explicitWait")
                                )
                        )
                );
        page.clickLabTestsMenu();
        page.selectCity(
                ConfigReader.getProperty("cityName")
        );
        wait.until(
                ExpectedConditions.urlContains(
                        ConfigReader.getProperty("cityName").toLowerCase()
                )
        );
        page.clickThyroidAddToCart();
        wait.until(driver -> page.isAddedToCart());
        Assert.assertTrue(
                page.isAddedToCart(),
                "Thyroid Profile was not added to cart"
        );
        System.out.println(
                "PASS : Thyroid Profile added to cart successfully in "
                        + ConfigReader.getProperty("cityName")
        );
    }
}