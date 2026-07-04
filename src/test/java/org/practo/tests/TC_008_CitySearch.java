package org.practo.tests;

import java.time.Duration;
import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.practo.pages.LabTestsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import utilities.ExcelUtils;

public class TC_008_CitySearch extends BaseTest {
    @Test
    public void verifyCitySearchFromDropdown() {
        ExcelUtils.loadExcel(
                ConfigReader.getProperty("excelPath"),
                ConfigReader.getProperty("citySheetName")
        );
        String cityName = ExcelUtils.getCellData("TC_008", "CityName");
        driver.get(ConfigReader.getProperty("url"));
        LabTestsPage labTestsPage = new LabTestsPage(driver);
        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        Integer.parseInt(
                                ConfigReader.getProperty("explicitWait"))
                )
        );
        labTestsPage.clickLabTestsMenu();
        wait.until(ExpectedConditions.urlContains("tests"));
        labTestsPage.searchCity(cityName);
        Assert.assertTrue(
                labTestsPage.isCitySuggestionAvailable(),
                "City search is not working"
        );
        System.out.println("City search is working successfully for: " + cityName);
    }
}