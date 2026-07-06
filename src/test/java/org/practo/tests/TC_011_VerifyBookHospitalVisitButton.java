package org.practo.tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.practo.pages.HomePage;
import org.practo.pages.HospitalPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import utilities.WaitUtils;
import java.util.HashMap;
import java.util.Map;

public class TC_011_VerifyBookHospitalVisitButton extends BaseTest {
    private HomePage homePage;
    private HospitalPage hospitalPage;

    @DataProvider(name = "tc011Data")
    public Object[][] getTC011Data() {
        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("hospitalSheetName")
        );
        Map<String, String> rowData = new HashMap<>();
        rowData.put("Location", ExcelUtils.getCellData("TC_011", "Location"));
        rowData.put("SearchKeyword", ExcelUtils.getCellData("TC_011", "SearchKeyword"));
        return new Object[][]{{rowData}};
    }

    @Test(dataProvider = "tc011Data")
    public void verifyBookHospitalVisitButtonIsFunctional(Map<String, String> data) {
        homePage = new HomePage(driver);
        hospitalPage = new HospitalPage(driver);
        commonCode.openApplication();

        String location = data.get("Location");
        String searchKeyword = data.get("SearchKeyword");

        // Step 1: Search hospital using location and keyword
        commonCode.searchHospital(homePage, location, searchKeyword);
        commonCode.waitForHospitalSearchResults(hospitalPage);

        // Step 2: Verify Book Hospital Visit button is displayed and enabled
        WebElement bookHospitalButton = WaitUtils.waitForClickable(driver, hospitalPage.getBookHospitalVisitButton());

        Assert.assertTrue(
                bookHospitalButton.isDisplayed(),
                "Book Hospital Visit button is not displayed."
        );

        System.out.println("Book Hospital Visit button is displayed.");

        Assert.assertTrue(
                bookHospitalButton.isEnabled(),
                "Book Hospital Visit button is not enabled."
        );

        System.out.println("Book Hospital Visit button is enabled.");

        String previousPageTitle = commonCode.getPageTitle();
        String previousPageUrl = commonCode.getCurrentUrl();
        String parentWindow = driver.getWindowHandle();

        Assert.assertFalse(
                previousPageTitle.trim().isEmpty(),
                "Previous page title is empty before clicking Book Hospital Visit button."
        );

        System.out.println("Previous Page Title: " + previousPageTitle);
        System.out.println("Previous Page URL: " + previousPageUrl);

        // Step 3: Click Book Hospital Visit button
        bookHospitalButton.click();

        boolean newWindowOpened = commonCode.switchToNewWindowIfAvailable(parentWindow);

        if (newWindowOpened) {
            System.out.println("New window opened and switched successfully.");
        } else {
            System.out.println("No new window opened. Continuing in the same window.");
            commonCode.waitForUrlToChange(previousPageUrl);
        }

        String currentPageTitle = commonCode.getPageTitle();
        String currentPageUrl = commonCode.getCurrentUrl();

        // Step 4: Verify page changed after clicking Book Hospital Visit button
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

        System.out.println("TC_011 Passed: Book Hospital Visit button is functional.");
        System.out.println("Current Page Title: " + currentPageTitle);
        System.out.println("Current Page URL: " + currentPageUrl);
    }
}