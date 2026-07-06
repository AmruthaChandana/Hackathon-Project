package org.practo.tests;

import base.BaseTest;
import org.practo.pages.LabTestsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import utilities.WaitUtils;

public class TC_005_CitySearch extends BaseTest {

    @Test
    public void verifyCitySearchFromDropdown() {
        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("citySheetName")
        );

        String cityName = ExcelUtils.getCellData("TC_005", "CityName");
        LabTestsPage labTestsPage = new LabTestsPage(driver);

        commonCode.openApplication();

        // Step 1: Navigate to Lab Tests page
        labTestsPage.clickLabTestsMenu();
        WaitUtils.waitForUrlContains(driver, "tests");

        // Step 2: Search city from dropdown
        labTestsPage.searchCity(cityName);

        // Step 3: Verify city suggestion is available
        Assert.assertTrue(
                labTestsPage.isCitySuggestionAvailable(),
                "City search is not working"
        );

        System.out.println("TC_005 Passed: City search is working successfully for: " + cityName);
    }
}