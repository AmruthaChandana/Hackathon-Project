package org.practo.tests;

import java.time.Duration;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.practo.pages.LabTestsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;

public class TC_008_CitySearch extends BaseTest {

    @Test
    public void verifyCitySearchFromDropdown() {

        String cityName = "Bangalore";

        driver.get(ConfigReader.getProperty("url"));

        LabTestsPage labTestsPage = new LabTestsPage(driver);

        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        Integer.parseInt(
                                ConfigReader.getProperty("explicitWait"))
                )
        );

        // Navigate to Lab Tests page
        labTestsPage.clickLabTestsMenu();

        wait.until(ExpectedConditions.urlContains("tests"));

        // Search city
        labTestsPage.clickCityDropdown();
        labTestsPage.searchCity(cityName);

        // Verify search suggestions are displayed
        Assert.assertTrue(
                labTestsPage.isCitySuggestionAvailable(),
                "City search is not working"
        );

        System.out.println("City search is working successfully for: " + cityName);
    }
}