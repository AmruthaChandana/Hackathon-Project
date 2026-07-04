package org.practo.tests;

import base.BaseTest;
import org.practo.pages.HomePage;
import org.practo.pages.HospitalPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import java.util.HashMap;
import java.util.Map;

public class TC_008_VerifySearchAndLocationBox extends BaseTest {
    private HomePage homePage;
    private HospitalPage hospitalPage;

    @DataProvider(name = "tc008Data")
    public Object[][] getTC008Data() {
        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("hospitalSheetName")
        );
        Map<String, String> rowData = new HashMap<>();
        rowData.put("Location", ExcelUtils.getCellData("TC_008", "Location"));
        rowData.put("SearchKeyword", ExcelUtils.getCellData("TC_008", "SearchKeyword"));
        return new Object[][]{{rowData}};
    }

    @Test(dataProvider = "tc008Data")
    public void verifyLocationAndSearchBoxesAreWorking(Map<String, String> data) {
        homePage = new HomePage(driver);
        hospitalPage = new HospitalPage(driver);
        commonCode.openApplication();

        String location = data.get("Location");
        String searchKeyword = data.get("SearchKeyword");
        String previousPageTitle = commonCode.getPageTitle();

        // Step 1: Search hospital using location and keyword
        commonCode.searchHospital(homePage, location, searchKeyword);

        // Step 2: Wait for hospital search results
        commonCode.waitForHospitalSearchResults(hospitalPage);

        String currentPageTitle = commonCode.getPageTitle();

        // Step 3: Verify page title changed after search
        Assert.assertNotEquals(
                currentPageTitle,
                previousPageTitle,
                "Page title did not change after entering location and search keyword."
        );

        System.out.println("TC_008 Passed: Location and search boxes are working.");
        System.out.println("Previous Page Title: " + previousPageTitle);
        System.out.println("Current Page Title: " + currentPageTitle);
    }
}