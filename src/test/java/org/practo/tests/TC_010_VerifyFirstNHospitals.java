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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TC_010_VerifyFirstNHospitals extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(
                    TC_010_VerifyFirstNHospitals.class);

    private HomePage homePage;
    private HospitalPage hospitalPage;

    @DataProvider(name = "tc010Data")
    public Object[][] getTC010Data() {

        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("hospitalSheetName")
        );

        Map<String, String> rowData =
                new HashMap<>();

        rowData.put(
                "Location",
                ExcelUtils.getCellData(
                        "TC_010",
                        "Location"));

        rowData.put(
                "SearchKeyword",
                ExcelUtils.getCellData(
                        "TC_010",
                        "SearchKeyword"));

        rowData.put(
                "RequiredMatchCount",
                ExcelUtils.getCellData(
                        "TC_010",
                        "RequiredMatchCount"));

        rowData.put(
                "MinimumRating",
                ExcelUtils.getCellData(
                        "TC_010",
                        "MinimumRating"));

        return new Object[][]{
                {rowData}
        };
    }

    @Test(dataProvider = "tc010Data")
    public void verifyFirstNHospitalsAreCheckedAndMatchingHospitalsAreAddedToList(
            Map<String, String> data) {

        logger.info(
                "Starting TC_010 - Verify First N Hospitals");

        homePage =
                new HomePage(driver);

        hospitalPage =
                new HospitalPage(driver);

        String location =
                data.get("Location");

        String searchKeyword =
                data.get("SearchKeyword");

        int requiredMatchCount =
                Integer.parseInt(
                        data.get("RequiredMatchCount"));

        double minimumRating =
                Double.parseDouble(
                        data.get("MinimumRating"));

        logger.info(
                "Location: {}",
                location);

        logger.info(
                "Search Keyword: {}",
                searchKeyword);

        logger.info(
                "Required Match Count: {}",
                requiredMatchCount);

        logger.info(
                "Minimum Rating: {}",
                minimumRating);

        // Step 1: Search Hospital

        commonCode.searchHospital(
                homePage,
                location,
                searchKeyword);

        commonCode.waitForHospitalSearchResults(
                hospitalPage);

        logger.info(
                "Hospital search results loaded successfully");

        // Step 2: Check Hospitals

        ArrayList<WebElement> matchingHospitals =
                new ArrayList<>();

        int checkedCount = 0;

        while (matchingHospitals.size() < requiredMatchCount) {

            List<WebElement> hospitalNames =
                    hospitalPage.getHospitalNamesForSearchResultsElements();

            for (int i = checkedCount;
                 i < hospitalNames.size();
                 i++) {

                WebElement hospitalName =
                        hospitalNames.get(i);

                try {

                    WebElement hospitalCard =
                            hospitalPage.getHospitalCardFromHospitalName(
                                    hospitalName);

                    if (!hospitalPage.isHospitalOpen24x7(
                            hospitalCard)) {

                        continue;
                    }

                    if (!hospitalPage.hasHospitalRating(
                            hospitalCard)) {

                        continue;
                    }

                    double rating =
                            hospitalPage.getHospitalRatingValue(
                                    hospitalCard);

                    if (rating > minimumRating) {

                        matchingHospitals.add(
                                hospitalName);

                        logger.info(
                                "Matching Hospital Found: {} | Rating: {}",
                                hospitalName.getText(),
                                rating);
                    }

                    if (matchingHospitals.size()
                            == requiredMatchCount) {

                        break;
                    }

                } catch (Exception e) {

                    logger.warn(
                            "Skipped one hospital because Open 24x7 or rating details were missing.");
                }
            }

            checkedCount =
                    hospitalNames.size();

            if (matchingHospitals.size()
                    == requiredMatchCount) {

                break;
            }

            commonCode.scrollToBottom();

            boolean moreResultsLoaded =
                    commonCode.waitForHospitalResultCountToIncrease(
                            hospitalPage,
                            checkedCount
                    );

            if (!moreResultsLoaded) {

                logger.info(
                        "No more hospital results available.");

                break;
            }
        }

        // Step 3: Print Matching Hospitals

        logger.info(
                "Total hospitals checked: {}",
                checkedCount);

        logger.info(
                "Matching hospitals stored in list: {}",
                matchingHospitals.size());

        logger.info(
                "Matching Hospital Details:");

        for (int i = 0;
             i < matchingHospitals.size();
             i++) {

            WebElement hospitalName =
                    matchingHospitals.get(i);

            WebElement hospitalCard =
                    hospitalPage.getHospitalCardFromHospitalName(
                            hospitalName);

            String name =
                    hospitalName.getText();

            String rating =
                    hospitalPage.getHospitalRatingText(
                            hospitalCard);

            logger.info(
                    "{}. {} - Rating: {}",
                    (i + 1),
                    name,
                    rating);
        }

        // Step 4: Validate Results

        Assert.assertTrue(
                checkedCount > 0,
                "No hospitals were checked from the search results."
        );

        Assert.assertTrue(
                matchingHospitals.size() > 0,
                "No matching hospitals found with Open 24x7 and rating greater than "
                        + minimumRating
        );

        logger.info(
                "TC_010 Passed: Hospitals were checked and matching hospitals were added to list.");
    }
}