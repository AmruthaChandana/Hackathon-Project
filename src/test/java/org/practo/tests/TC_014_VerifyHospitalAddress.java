package org.practo.tests;

import base.CommonCode;
import org.openqa.selenium.WebElement;
import org.practo.pages.HomePage;
import org.practo.pages.HospitalDetailsPage;
import org.practo.pages.HospitalPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Set;

public class TC_014_VerifyHospitalAddress extends CommonCode {

    HomePage homePage;
    HospitalPage hospitalPage;
    HospitalDetailsPage hospitalDetailsPage;

    @DataProvider(name = "tc14Data")
    public Object[][] getTC14Data() {
        Map<String, String> rowData = getHospitalTestData(
                "TC14",
                "Location",
                "SearchKeyword"
        );

        return new Object[][]{
                {rowData}
        };
    }

    @Test(dataProvider = "tc14Data")
    public void verifyAddressIsVisibleAndCanBeExtracted(Map<String, String> data) {
        homePage = new HomePage(driver);
        hospitalPage = new HospitalPage(driver);

        openApplication();

        String location = data.get("Location");
        String searchKeyword = data.get("SearchKeyword");

        searchHospital(homePage, location, searchKeyword);

        waitForHospitalSearchResults(hospitalPage);

        String parentWindow = driver.getWindowHandle();
        int windowCountBeforeClick = driver.getWindowHandles().size();
        String previousUrl = getCurrentUrl();

        WebElement firstHospitalResult =
                waitForClickable(hospitalPage.getFirstHospitalResultLink());

        String hospitalName = firstHospitalResult.getText().trim();

        Assert.assertFalse(
                hospitalName.isEmpty(),
                "First hospital name is empty before clicking."
        );

        firstHospitalResult.click();

        try {
            wait.until(driver ->
                    driver.getWindowHandles().size() > windowCountBeforeClick ||
                            !driver.getCurrentUrl().equals(previousUrl)
            );
        } catch (Exception e) {
            System.out.println("No new window or URL change detected immediately after clicking first hospital.");
        }

        if (driver.getWindowHandles().size() > windowCountBeforeClick) {
            Set<String> allWindows = driver.getWindowHandles();

            for (String window : allWindows) {
                if (!window.equals(parentWindow)) {
                    driver.switchTo().window(window);
                    break;
                }
            }

            System.out.println("New hospital details window opened and switched successfully.");
        } else {
            System.out.println("Hospital details opened in the same window.");
        }

        hospitalDetailsPage = new HospitalDetailsPage(driver);

        WebElement addressElement =
                waitForVisible(hospitalDetailsPage.getAddressBodyElement());

        Assert.assertTrue(
                addressElement.isDisplayed(),
                "Hospital address is not displayed on details page."
        );

        String addressText =
                getVisibleTextWithJsFallback(addressElement);

        String finalAddress =
                hospitalDetailsPage.cleanAddressText(addressText);

        Assert.assertFalse(
                finalAddress.trim().isEmpty(),
                "Hospital address text is empty."
        );

        System.out.println("TC14 Passed: Hospital address is visible and extracted successfully.");
        System.out.println("Hospital Name: " + hospitalName);
        System.out.println("Hospital Address:");
        System.out.println(finalAddress);
    }
}