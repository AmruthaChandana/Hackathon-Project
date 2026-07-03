package org.practo.tests;
import org.openqa.selenium.Keys;
import base.CommonCode;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import java.util.Set;

public class TC_013_VerifyBookHospitalVisitButton extends CommonCode {
    HomePage homePage = new HomePage();
    HospitalPage hospitalPage = new HospitalPage();
    @DataProvider(name = "tc13Data")
    public Object[][] getTC13Data() {
        Properties hospitalProperties = ConfigReader.initProperties();
        ExcelUtils.loadExcel(
                hospitalProperties.getProperty("excelPath"),
                hospitalProperties.getProperty("hospitalSheetName")
        );
        Map<String, String> rowData = new HashMap<>();
        rowData.put("TestCaseID", "TC13");
        rowData.put("Location", ExcelUtils.getCellData("TC13", "Location"));
        rowData.put("SearchKeyword", ExcelUtils.getCellData("TC13", "SearchKeyword"));
        return new Object[][] {
                { rowData }
        };
    }

    @Test(dataProvider = "tc13Data")
    public void verifyBookHospitalVisitButtonIsFunctional(Map<String, String> data) {
        openApplication();
        String location = data.get("Location");
        String searchKeyword = data.get("SearchKeyword");
        type(homePage.hospitalLocationBox, location);
        waitForVisible(homePage.hospitalLocationBox).sendKeys(Keys.BACK_SPACE);
        waitForVisible(homePage.hospitalLocationBox).sendKeys(location.substring(location.length() - 1));
        click(homePage.locationOption(location));
        type(homePage.hospitalSearchBox, searchKeyword);
        click(homePage.searchOption(searchKeyword));
        waitForVisible(hospitalPage.hospitalNamesForSearchResults);
        WebElement bookHospitalButton = waitForClickable(hospitalPage.bookHospitalVisitButton);
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
        String previousPageTitle = getPageTitle();
        String previousPageUrl = getCurrentUrl();
        String parentWindow = driver.getWindowHandle();
        Assert.assertFalse(
                previousPageTitle.trim().isEmpty(),
                "Previous page title is empty before clicking Book Hospital Visit button."
        );
        System.out.println("Previous Page Title: " + previousPageTitle);
        System.out.println("Previous Page URL: " + previousPageUrl);
        bookHospitalButton.click();
        try {
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            Set<String> allWindows = driver.getWindowHandles();
            for (String window : allWindows) {
                if (!window.equals(parentWindow)) {
                    driver.switchTo().window(window);
                    break;
                }
            }
            System.out.println("New window opened and switched successfully.");
        } catch (Exception e) {
            System.out.println("No new window opened. Continuing in the same window.");
            wait.until(ExpectedConditions.not(
                    ExpectedConditions.urlToBe(previousPageUrl)
            ));
        }
        String currentPageTitle = getPageTitle();
        String currentPageUrl = getCurrentUrl();
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
        System.out.println("TC13 Passed: Book Hospital Visit button is functional.");
        System.out.println("Current Page Title: " + currentPageTitle);
        System.out.println("Current Page URL: " + currentPageUrl);
    }
}