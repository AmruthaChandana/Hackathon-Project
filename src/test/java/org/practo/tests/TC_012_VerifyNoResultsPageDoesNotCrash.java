package org.practo.tests;

import base.BaseTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebElement;

import org.practo.pages.HomePage;
import org.practo.pages.HospitalPage;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utilities.ExcelUtils;

import java.util.HashMap;
import java.util.Map;

public class TC_012_VerifyNoResultsPageDoesNotCrash extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(
                    TC_012_VerifyNoResultsPageDoesNotCrash.class);

    private HomePage homePage;
    private HospitalPage hospitalPage;

    @DataProvider(name = "tc012Data")
    public Object[][] getTC012Data() {

        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("hospitalSheetName")
        );

        Map<String, String> rowData =
                new HashMap<>();

        rowData.put(
                "Location",
                ExcelUtils.getCellData(
                        "TC_012",
                        "Location")
        );

        rowData.put(
                "SearchKeyword",
                ExcelUtils.getCellData(
                        "TC_012",
                        "SearchKeyword")
        );

        return new Object[][]{
                {rowData}
        };
    }

    @Test(dataProvider = "tc012Data")
    public void verifySiteDoesNotCrashWhenResultListIsEmpty(
            Map<String, String> data) {

        logger.info(
                "Starting TC_012 - Verify No Results Page Does Not Crash");

        homePage =
                new HomePage(driver);

        hospitalPage =
                new HospitalPage(driver);

        String location =
                data.get("Location");

        String searchKeyword =
                data.get("SearchKeyword");

        logger.info(
                "Location: {}",
                location);

        logger.info(
                "Search Keyword: {}",
                searchKeyword);

        // Step 1: Search with invalid / no-result keyword

        logger.info(
                "Searching hospital with invalid keyword");

        commonCode.searchHospitalUsingContains(
                homePage,
                location,
                searchKeyword
        );

        // Step 2: Verify no-results message

        WebElement noResultsElement =
                commonCode.waitForVisible(
                        hospitalPage.getNoResultsMessage());

        Assert.assertTrue(
                noResultsElement.isDisplayed(),
                "No results message is not displayed."
        );

        String errorMessage =
                commonCode.getVisibleTextWithJsFallback(
                        noResultsElement);

        Assert.assertNotNull(
                errorMessage,
                "No results message text is null."
        );

        Assert.assertFalse(
                errorMessage.trim().isEmpty(),
                "No results message text is empty."
        );

        logger.info(
                "No Results Message: {}",
                errorMessage);

        // Step 3: Verify page did not crash

        Assert.assertFalse(
                errorMessage.toLowerCase().contains("error")
                        || errorMessage.toLowerCase().contains("something went wrong")
                        || errorMessage.toLowerCase().contains("server")
                        || errorMessage.toLowerCase().contains("crash"),
                "Error/crash message displayed instead of no-results message. Actual message: "
                        + errorMessage
        );

        logger.info(
                "Verified that the application displayed a valid no-results message");

        logger.info(
                "TC_012 Passed: Site did not crash when result list was empty.");
    }
}