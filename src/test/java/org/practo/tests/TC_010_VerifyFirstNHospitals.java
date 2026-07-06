package org.practo.tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.practo.pages.HomePage;
import org.practo.pages.HospitalPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TC_010_VerifyFirstNHospitals extends BaseTest {
    private HomePage homePage;
    private HospitalPage hospitalPage;

    @DataProvider(name = "tc010Data")
    public Object[][] getTC010Data() {
        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("hospitalSheetName")
        );
        Map<String, String> rowData = new HashMap<>();
        rowData.put("Location", ExcelUtils.getCellData("TC_010", "Location"));
        rowData.put("SearchKeyword", ExcelUtils.getCellData("TC_010", "SearchKeyword"));
        rowData.put("RequiredMatchCount", ExcelUtils.getCellData("TC_010", "RequiredMatchCount"));
        rowData.put("MinimumRating", ExcelUtils.getCellData("TC_010", "MinimumRating"));
        return new Object[][]{{rowData}};
    }

    @Test(dataProvider = "tc010Data")
    public void verifyFirstNHospitalsAreCheckedAndMatchingHospitalsAreAddedToList(Map<String, String> data) {
        homePage = new HomePage(driver);
        hospitalPage = new HospitalPage(driver);
        commonCode.openApplication();

        String location = data.get("Location");
        String searchKeyword = data.get("SearchKeyword");
        int requiredMatchCount = Integer.parseInt(data.get("RequiredMatchCount"));
        double minimumRating = Double.parseDouble(data.get("MinimumRating"));

        // Step 1: Search hospital using location and keyword
        commonCode.searchHospital(homePage, location, searchKeyword);
        commonCode.waitForHospitalSearchResults(hospitalPage);

        // Step 2: Check hospitals and store matching hospitals
        ArrayList<WebElement> matchingHospitals = new ArrayList<>();
        int checkedCount = 0;

        while (matchingHospitals.size() < requiredMatchCount) {
            List<WebElement> hospitalNames = hospitalPage.getHospitalNamesForSearchResultsElements();

            for (int i = checkedCount; i < hospitalNames.size(); i++) {
                WebElement hospitalName = hospitalNames.get(i);
                try {
                    WebElement hospitalCard = hospitalPage.getHospitalCardFromHospitalName(hospitalName);

                    if (!hospitalPage.isHospitalOpen24x7(hospitalCard)) {
                        continue;
                    }

                    if (!hospitalPage.hasHospitalRating(hospitalCard)) {
                        continue;
                    }

                    double rating = hospitalPage.getHospitalRatingValue(hospitalCard);

                    if (rating > minimumRating) {
                        matchingHospitals.add(hospitalName);
                    }

                    if (matchingHospitals.size() == requiredMatchCount) {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Skipped one hospital because Open 24x7 or rating details were missing.");
                }
            }

            checkedCount = hospitalNames.size();

            if (matchingHospitals.size() == requiredMatchCount) {
                break;
            }

            commonCode.scrollToBottom();

            boolean moreResultsLoaded = commonCode.waitForHospitalResultCountToIncrease(
                    hospitalPage,
                    checkedCount
            );

            if (!moreResultsLoaded) {
                break;
            }
        }

        // Step 3: Print matching hospital details
        System.out.println("Total hospitals checked: " + checkedCount);
        System.out.println("Matching hospitals stored in list: " + matchingHospitals.size());
        System.out.println("Matching hospital details:");

        for (int i = 0; i < matchingHospitals.size(); i++) {
            WebElement hospitalName = matchingHospitals.get(i);
            WebElement hospitalCard = hospitalPage.getHospitalCardFromHospitalName(hospitalName);
            String name = hospitalName.getText();
            String rating = hospitalPage.getHospitalRatingText(hospitalCard);
            System.out.println((i + 1) + ". " + name + " - Rating: " + rating);
        }

        // Step 4: Validate hospitals were checked and matching hospitals found
        Assert.assertTrue(
                checkedCount > 0,
                "No hospitals were checked from the search results."
        );

        Assert.assertTrue(
                matchingHospitals.size() > 0,
                "No matching hospitals found with Open 24x7 and rating greater than " + minimumRating
        );

        System.out.println("TC_010 Passed: Hospitals were checked and matching hospitals were added to list.");
    }
}