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

public class TC_009_VerifyHospitalAddress extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(
                    TC_009_VerifyHospitalAddress.class);

    private HomePage homePage;
    private HospitalPage hospitalPage;

    @DataProvider(name = "tc009Data")
    public Object[][] getTC009Data() {

        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("hospitalSheetName")
        );

        Map<String, String> rowData =
                new HashMap<>();

        rowData.put(
                "Location",
                ExcelUtils.getCellData(
                        "TC_009",
                        "Location")
        );

        rowData.put(
                "SearchKeyword",
                ExcelUtils.getCellData(
                        "TC_009",
                        "SearchKeyword")
        );

        return new Object[][]{
                {rowData}
        };
    }

    @Test(dataProvider = "tc009Data")
    public void verifyAddressIsVisibleAndCanBeExtracted(
            Map<String, String> data) {

        logger.info(
                "Starting TC_009 - Verify Hospital Address");

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

        // Step 1: Search Hospital

        commonCode.searchHospital(
                homePage,
                location,
                searchKeyword
        );

        commonCode.waitForHospitalSearchResults(
                hospitalPage);

        logger.info(
                "Hospital search results loaded successfully");

        String parentWindow =
                driver.getWindowHandle();

        int windowCountBeforeClick =
                driver.getWindowHandles()
                        .size();

        String previousUrl =
                driver.getCurrentUrl();

        // Step 2: Click First Hospital

        WebElement firstHospitalResult =
                commonCode.waitForClickable(
                        hospitalPage.getFirstHospitalResultLink());

        String hospitalName =
                firstHospitalResult
                        .getText()
                        .trim();

        Assert.assertFalse(
                hospitalName.isEmpty(),
                "First hospital name is empty before clicking."
        );

        logger.info(
                "Selected Hospital: {}",
                hospitalName);

        firstHospitalResult.click();

        try {

            commonCode.waitUntil(
                    driver ->
                            driver.getWindowHandles().size()
                                    > windowCountBeforeClick
                                    ||
                                    !driver.getCurrentUrl()
                                            .equals(previousUrl)
            );

        } catch (Exception e) {

            logger.warn(
                    "No new window or URL change detected immediately after clicking first hospital.");
        }

        // Step 3: Switch Window

        if (driver.getWindowHandles().size()
                > windowCountBeforeClick) {

            commonCode.switchToNewWindow(
                    parentWindow);

            logger.info(
                    "New hospital details window opened and switched successfully.");

        } else {

            logger.info(
                    "Hospital details opened in the same window.");
        }

        hospitalPage =
                new HospitalPage(driver);

        // Step 4: Verify Address

        WebElement addressElement =
                commonCode.waitForVisible(
                        hospitalPage.getAddressBodyElement());

        Assert.assertTrue(
                addressElement.isDisplayed(),
                "Hospital address is not displayed on details page."
        );

        String addressText =
                commonCode.getVisibleTextWithJsFallback(
                        addressElement);

        String finalAddress =
                hospitalPage.cleanAddressText(
                        addressText);

        Assert.assertFalse(
                finalAddress.trim().isEmpty(),
                "Hospital address text is empty."
        );

        logger.info(
                "Hospital Address Extracted Successfully");

        logger.info(
                "Hospital Name: {}",
                hospitalName);

        logger.info(
                "Hospital Address:\n{}",
                finalAddress);

        logger.info(
                "TC_009 Passed: Hospital address is visible and extracted successfully.");
    }
}