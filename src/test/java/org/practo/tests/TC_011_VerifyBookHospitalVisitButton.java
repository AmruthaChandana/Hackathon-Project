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

public class TC_011_VerifyBookHospitalVisitButton extends BaseTest {
    private static final Logger logger = LogManager.getLogger(TC_011_VerifyBookHospitalVisitButton.class);
    private HomePage homePage;
    private HospitalPage hospitalPage;

    @DataProvider(name = "tc011Data")
    public Object[][] getTC011Data() {
        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("hospitalSheetName")
        );

        Map<String, String> rowData = new HashMap<>();

        rowData.put(
                "Location",
                ExcelUtils.getCellData(
                        "TC_011",
                        "Location"
                )
        );

        rowData.put(
                "SearchKeyword",
                ExcelUtils.getCellData(
                        "TC_011",
                        "SearchKeyword"
                )
        );

        return new Object[][]{
                {rowData}
        };
    }

    @Test(dataProvider = "tc011Data")
    public void verifyBookHospitalVisitButtonIsFunctional(Map<String, String> data) {
        logger.info("Starting TC_011 - Verify Book Hospital Visit Button");

        homePage = new HomePage(driver);
        hospitalPage = new HospitalPage(driver);

        String location = data.get("Location");
        String searchKeyword = data.get("SearchKeyword");

        logger.info("Location: {}", location);
        logger.info("Search Keyword: {}", searchKeyword);

        // Step 1: Search Hospital
        commonCode.searchHospital(
                homePage,
                location,
                searchKeyword
        );

        commonCode.waitForHospitalSearchResults(hospitalPage);

        logger.info("Hospital search results loaded successfully");

        // Step 2: Verify Button Displayed and Enabled
        WebElement bookHospitalButton = commonCode.waitForClickable(
                hospitalPage.getBookHospitalVisitButton()
        );

        Assert.assertTrue(
                bookHospitalButton.isDisplayed(),
                "Book Hospital Visit button is not displayed."
        );

        logger.info("Book Hospital Visit button is displayed");

        Assert.assertTrue(
                bookHospitalButton.isEnabled(),
                "Book Hospital Visit button is not enabled."
        );

        logger.info("Book Hospital Visit button is enabled");

        String previousPageTitle = commonCode.getPageTitle();
        String previousPageUrl = commonCode.getCurrentUrl();
        String parentWindow = driver.getWindowHandle();

        Assert.assertFalse(
                previousPageTitle.trim().isEmpty(),
                "Previous page title is empty before clicking Book Hospital Visit button."
        );

        logger.info("Previous Page Title: {}", previousPageTitle);
        logger.info("Previous Page URL: {}", previousPageUrl);

        // Step 3: Click Button
        logger.info("Clicking Book Hospital Visit button");

        bookHospitalButton.click();

        boolean newWindowOpened = commonCode.switchToNewWindowIfAvailable(parentWindow);

        if (newWindowOpened) {
            logger.info("New window opened and switched successfully.");
        } else {
            logger.info("No new window opened. Continuing in the same window.");
            commonCode.waitForUrlToBeChanged(previousPageUrl);
        }

        String currentPageTitle = commonCode.getPageTitle();
        String currentPageUrl = commonCode.getCurrentUrl();

        // Step 4: Verify Navigation
        Assert.assertNotEquals(
                currentPageUrl,
                previousPageUrl,
                "Page URL did not change after clicking Book Hospital Visit button."
        );

        Assert.assertNotEquals(
                currentPageTitle,
                previousPageTitle,
                "Page title did not change after clicking Book Hospital Visit button."
        );

        logger.info("Current Page Title: {}", currentPageTitle);
        logger.info("Current Page URL: {}", currentPageUrl);
        logger.info("TC_011 Passed: Book Hospital Visit button is functional.");
    }
}