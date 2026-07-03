package org.practo.tests;

import base.CommonCode;
import org.practo.pages.HomePage;
import org.practo.pages.HospitalPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class TC_011_VerifySearchAndLocationBox extends CommonCode {

    HomePage homePage;
    HospitalPage hospitalPage;

    @DataProvider(name = "tc11Data")
    public Object[][] getTC11Data() {
        Map<String, String> rowData = getHospitalTestData(
                "TC11",
                "Location",
                "SearchKeyword"
        );

        return new Object[][]{
                {rowData}
        };
    }

    @Test(dataProvider = "tc11Data")
    public void verifyLocationAndSearchBoxesAreWorking(Map<String, String> data) {
        homePage = new HomePage(driver);
        hospitalPage = new HospitalPage(driver);

        openApplication();

        String location = data.get("Location");
        String searchKeyword = data.get("SearchKeyword");
        String previousPageTitle = getPageTitle();

        searchHospital(homePage, location, searchKeyword);

        waitForHospitalSearchResults(hospitalPage);

        String currentPageTitle = getPageTitle();

        Assert.assertNotEquals(
                currentPageTitle,
                previousPageTitle,
                "Page title did not change after entering location and search keyword."
        );

        System.out.println("TC11 Passed: Location and search boxes are working.");
        System.out.println("Previous Page Title: " + previousPageTitle);
        System.out.println("Current Page Title: " + currentPageTitle);
    }
}