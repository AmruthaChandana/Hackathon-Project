package org.practo.tests;

import base.CommonCode;
import org.openqa.selenium.WebElement;
import org.practo.pages.HomePage;
import org.practo.pages.HospitalPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class TC_013_VerifyBookHospitalVisitButton extends CommonCode {

    HomePage homePage;
    HospitalPage hospitalPage;

    @DataProvider(name = "tc13Data")
    public Object[][] getTC13Data() {
        Map<String, String> rowData = getHospitalTestData(
                "TC13",
                "Location",
                "SearchKeyword"
        );

        return new Object[][]{
                {rowData}
        };
    }

    @Test(dataProvider = "tc13Data")
    public void verifyBookHospitalVisitButtonIsFunctional(Map<String, String> data) {
        homePage = new HomePage(driver);
        hospitalPage = new HospitalPage(driver);

        openApplication();

        String location = data.get("Location");
        String searchKeyword = data.get("SearchKeyword");

        searchHospital(homePage, location, searchKeyword);

        waitForHospitalSearchResults(hospitalPage);

        WebElement bookHospitalButton =
                waitForClickable(hospitalPage.getBookHospitalVisitButton());

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

        boolean newWindowOpened =
                switchToNewWindowIfAvailable(parentWindow);

        if (newWindowOpened) {
            System.out.println("New window opened and switched successfully.");
        } else {
            System.out.println("No new window opened. Continuing in the same window.");
            waitForUrlToChange(previousPageUrl);
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