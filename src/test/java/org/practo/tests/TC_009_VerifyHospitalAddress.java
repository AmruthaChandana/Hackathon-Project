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

public class TC_009_VerifyHospitalAddress extends BaseTest {
    private HomePage homePage;
    private HospitalPage hospitalPage;

    @DataProvider(name = "tc009Data")
    public Object[][] getTC009Data() {
        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("hospitalSheetName")
        );
        Map<String, String> rowData = new HashMap<>();
        rowData.put("Location", ExcelUtils.getCellData("TC_009", "Location"));
        rowData.put("SearchKeyword", ExcelUtils.getCellData("TC_009", "SearchKeyword"));
        return new Object[][]{{rowData}};
    }

    @Test(dataProvider = "tc009Data")
    public void verifyAddressIsVisibleAndCanBeExtracted(Map<String, String> data) {
        homePage = new HomePage(driver);
        hospitalPage = new HospitalPage(driver);
        commonCode.openApplication();

        String location = data.get("Location");
        String searchKeyword = data.get("SearchKeyword");

        // Step 1: Search hospital using location and keyword
        commonCode.searchHospital(homePage, location, searchKeyword);
        commonCode.waitForHospitalSearchResults(hospitalPage);

        String parentWindow = driver.getWindowHandle();
        int windowCountBeforeClick = driver.getWindowHandles().size();
        String previousUrl = driver.getCurrentUrl();

        // Step 2: Click first hospital result
        WebElement firstHospitalResult = WaitUtils.waitForClickable(driver, hospitalPage.getFirstHospitalResultLink());
        String hospitalName = firstHospitalResult.getText().trim();

        Assert.assertFalse(
                hospitalName.isEmpty(),
                "First hospital name is empty before clicking."
        );

        firstHospitalResult.click();

        try {
            WaitUtils.waitUntil(driver, driver ->
                    driver.getWindowHandles().size() > windowCountBeforeClick
                            || !driver.getCurrentUrl().equals(previousUrl)
            );
        } catch (Exception e) {
            System.out.println("No new window or URL change detected immediately after clicking first hospital.");
        }

        // Step 3: Switch to new window if hospital details opened in new window
        if (driver.getWindowHandles().size() > windowCountBeforeClick) {
            commonCode.switchToNewWindow(parentWindow);
            System.out.println("New hospital details window opened and switched successfully.");
        } else {
            System.out.println("Hospital details opened in the same window.");
        }

        hospitalPage = new HospitalPage(driver);

        // Step 4: Verify address is visible and extract address
        WebElement addressElement = WaitUtils.waitForVisible(driver, hospitalPage.getAddressBodyElement());

        Assert.assertTrue(
                addressElement.isDisplayed(),
                "Hospital address is not displayed on details page."
        );

        String addressText = commonCode.getVisibleTextWithJsFallback(addressElement);
        String finalAddress = hospitalPage.cleanAddressText(addressText);

        Assert.assertFalse(
                finalAddress.trim().isEmpty(),
                "Hospital address text is empty."
        );

        System.out.println("TC_009 Passed: Hospital address is visible and extracted successfully.");
        System.out.println("Hospital Name: " + hospitalName);
        System.out.println("Hospital Address:");
        System.out.println(finalAddress);
    }
}