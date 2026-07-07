package org.practo.tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.practo.pages.HomePage;
import org.practo.pages.HospitalPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import java.util.HashMap;
import java.util.Map;

public class TC_008_VerifySearchAndLocationBox extends BaseTest {
    private static final Logger logger = LogManager.getLogger(TC_008_VerifySearchAndLocationBox.class);
    private HomePage homePage;
    private HospitalPage hospitalPage;

    @DataProvider(name = "tc008Data")
    public Object[][] getTC008Data() {
        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("hospitalSheetName")
        );

        Map<String, String> rowData = new HashMap<>();

        rowData.put(
                "Location",
                ExcelUtils.getCellData(
                        "TC_008",
                        "Location"
                )
        );

        rowData.put(
                "SearchKeyword",
                ExcelUtils.getCellData(
                        "TC_008",
                        "SearchKeyword"
                )
        );

        return new Object[][]{
                {rowData}
        };
    }

    @Test(dataProvider = "tc008Data")
    public void verifyLocationAndSearchBoxesAreWorking(Map<String, String> data) {
        logger.info("Starting TC_008 - Verify Search And Location Box");

        homePage = new HomePage(driver);
        hospitalPage = new HospitalPage(driver);

        String location = data.get("Location");
        String searchKeyword = data.get("SearchKeyword");
        String previousPageTitle = commonCode.getPageTitle();

        logger.info("Location: {}", location);
        logger.info("Search Keyword: {}", searchKeyword);
        logger.info("Previous Page Title: {}", previousPageTitle);

        // Step 1: Search Hospital
        commonCode.searchHospital(
                homePage,
                location,
                searchKeyword
        );

        // Step 2: Wait For Search Results
        commonCode.waitForHospitalSearchResults(hospitalPage);

        String currentPageTitle = commonCode.getPageTitle();

        // Step 3: Verify Page Title Changed
        Assert.assertNotEquals(
                currentPageTitle,
                previousPageTitle,
                "Page title did not change after entering location and search keyword."
        );

        logger.info("Current Page Title: {}", currentPageTitle);
        logger.info("TC_008 Passed: Location and search boxes are working successfully");
    }
}