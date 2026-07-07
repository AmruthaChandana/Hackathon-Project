package org.practo.tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.practo.pages.LabTestsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;

public class TC_005_CitySearch extends BaseTest {
    private static final Logger logger = LogManager.getLogger(TC_005_CitySearch.class);

    @Test
    public void verifyCitySearchFromDropdown() {
        logger.info("Starting TC_005 - Verify City Search");

        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("citySheetName")
        );

        String cityName = ExcelUtils.getCellData("TC_005", "CityName");

        logger.info("Searching city: {}", cityName);

        LabTestsPage labTestsPage = new LabTestsPage(driver);

        // Step 1: Navigate to Lab Tests Page
        logger.info("Navigating to Lab Tests page");
        labTestsPage.clickLabTestsMenu();

        commonCode.waitForUrlContains("tests");

        logger.info("Successfully navigated to Lab Tests page");

        // Step 2: Search City
        logger.info("Searching city from dropdown: {}", cityName);
        labTestsPage.searchCity(cityName);

        // Step 3: Verify Suggestion Available
        Assert.assertTrue(
                labTestsPage.isCitySuggestionAvailable(),
                "City search is not working"
        );

        logger.info("City suggestion displayed successfully for {}", cityName);
        logger.info("TC_005 Passed: City search is working successfully for {}", cityName);
    }
}