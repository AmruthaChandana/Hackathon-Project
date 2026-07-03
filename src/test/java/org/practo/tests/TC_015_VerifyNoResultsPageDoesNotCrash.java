package org.practo.tests;

import base.CommonCode;
import org.openqa.selenium.WebElement;
import org.practo.pages.HomePage;
import org.practo.pages.HospitalPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class TC_015_VerifyNoResultsPageDoesNotCrash extends CommonCode {

    HomePage homePage;
    HospitalPage hospitalPage;

    @DataProvider(name = "tc15Data")
    public Object[][] getTC15Data() {
        Map<String, String> rowData = getHospitalTestData(
                "TC15",
                "Location",
                "SearchKeyword"
        );

        return new Object[][]{
                {rowData}
        };
    }

    @Test(dataProvider = "tc15Data")
    public void verifySiteDoesNotCrashWhenResultListIsEmpty(Map<String, String> data) {
        homePage = new HomePage(driver);
        hospitalPage = new HospitalPage(driver);

        openApplication();

        String location = data.get("Location");
        String searchKeyword = data.get("SearchKeyword");

        searchHospitalUsingContains(homePage, location, searchKeyword);

        WebElement noResultsElement =
                waitForVisible(hospitalPage.getNoResultsMessage());

        Assert.assertTrue(
                noResultsElement.isDisplayed(),
                "No results message is not displayed."
        );

        String errorMessage =
                getVisibleTextWithJsFallback(noResultsElement);

        Assert.assertNotNull(
                errorMessage,
                "No results message text is null."
        );

        Assert.assertFalse(
                errorMessage.trim().isEmpty(),
                "No results message text is empty."
        );

        Assert.assertFalse(
                errorMessage.toLowerCase().contains("error") ||
                        errorMessage.toLowerCase().contains("something went wrong") ||
                        errorMessage.toLowerCase().contains("server") ||
                        errorMessage.toLowerCase().contains("crash"),
                "Error/crash message displayed instead of no-results message. Actual message: " + errorMessage
        );

        System.out.println("TC15 Passed: Site did not crash when result list was empty.");
        System.out.println("No Results Message:");
        System.out.println(errorMessage);
    }
}