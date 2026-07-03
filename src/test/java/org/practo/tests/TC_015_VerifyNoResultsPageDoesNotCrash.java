package org.practo.tests;
import org.openqa.selenium.Keys;
import base.BaseTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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

public class TC_015_VerifyNoResultsPageDoesNotCrash extends BaseTest {
    HomePage homePage = new HomePage();
    HospitalPage hospitalPage = new HospitalPage();
    @DataProvider(name = "tc15Data")
    public Object[][] getTC15Data() {
        Properties hospitalProperties = ConfigReader.initProperties();
        ExcelUtils.loadExcel(
                hospitalProperties.getProperty("excelPath"),
                hospitalProperties.getProperty("hospitalSheetName")
        );
        Map<String, String> rowData = new HashMap<>();
        rowData.put("TestCaseID", "TC15");
        rowData.put("Location", ExcelUtils.getCellData("TC15", "Location"));
        rowData.put("SearchKeyword", ExcelUtils.getCellData("TC15", "SearchKeyword"));
        return new Object[][] {
                { rowData }
        };
    }

    @Test(dataProvider = "tc15Data")
    public void verifySiteDoesNotCrashWhenResultListIsEmpty(Map<String, String> data) {
        openApplication();
        String location = data.get("Location");
        String searchKeyword = data.get("SearchKeyword");
        type(homePage.hospitalLocationBox, location);
        waitForVisible(homePage.hospitalLocationBox).sendKeys(Keys.BACK_SPACE);
        waitForVisible(homePage.hospitalLocationBox).sendKeys(location.substring(location.length() - 1));
        click(homePage.locationOption(location));
        type(homePage.hospitalSearchBox, searchKeyword);
        click(homePage.searchOptionContains(searchKeyword));
        WebElement noResultsElement = waitForVisible(hospitalPage.noResultsMessage);
        Assert.assertTrue(
                noResultsElement.isDisplayed(),
                "No results message is not displayed."
        );
        String errorMessage = noResultsElement.getText();
        if (errorMessage == null || errorMessage.trim().isEmpty()) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            errorMessage = (String) js.executeScript(
                    "return arguments[0].innerText;",
                    noResultsElement
            );
        }
        Assert.assertFalse(
                errorMessage.trim().isEmpty(),
                "No results message text is empty."
        );
        System.out.println("TC15 Passed: Site did not crash when result list was empty.");
        System.out.println("No Results Message:");
        System.out.println(errorMessage);
    }
}