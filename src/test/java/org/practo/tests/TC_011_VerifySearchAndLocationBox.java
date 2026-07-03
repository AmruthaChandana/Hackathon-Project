package org.practo.tests;
import org.openqa.selenium.Keys;
import base.BaseTest;
import org.practo.pages.HomePage;
import org.practo.pages.HospitalPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import utilities.ExcelUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TC_011_VerifySearchAndLocationBox extends BaseTest {
    HomePage homePage = new HomePage();
    HospitalPage hospitalPage = new HospitalPage();
    @DataProvider(name = "tc11Data")
    public Object[][] getTC11Data() {
        Properties hospitalProperties = ConfigReader.initProperties();
        ExcelUtils.loadExcel(
                hospitalProperties.getProperty("excelPath"),
                hospitalProperties.getProperty("hospitalSheetName")
        );
        Map<String, String> rowData = new HashMap<>();
        rowData.put("TestCaseID", "TC11");
        rowData.put("Location", ExcelUtils.getCellData("TC11", "Location"));
        rowData.put("SearchKeyword", ExcelUtils.getCellData("TC11", "SearchKeyword"));
        return new Object[][] {
                { rowData }
        };
    }

    @Test(dataProvider = "tc11Data")
    public void verifyLocationAndSearchBoxesAreWorking(Map<String, String> data) {
        openApplication();
        String location = data.get("Location");
        String searchKeyword = data.get("SearchKeyword");
        String previousPageTitle = getPageTitle();
        type(homePage.hospitalLocationBox, location);
        waitForVisible(homePage.hospitalLocationBox).sendKeys(Keys.BACK_SPACE);
        waitForVisible(homePage.hospitalLocationBox).sendKeys(location.substring(location.length() - 1));
        click(homePage.locationOption(location));
        type(homePage.hospitalSearchBox, searchKeyword);
        click(homePage.searchOption(searchKeyword));
        waitForVisible(hospitalPage.hospitalNamesForSearchResults);
        String currentPageTitle = getPageTitle();
        Assert.assertNotEquals(
                currentPageTitle,
                previousPageTitle,
                "Page title did not change after entering location and search keyword."
        );
        System.out.println("TC11 Passed: Location and search boxes are working.");
        System.out.println("Previous Page Title: " + previousPageTitle);
        System.out.println("Current Page Title: " + currentPageTitle);
    }
}