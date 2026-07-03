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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TC_012_VerifyFirstNHospitals extends BaseTest {
    HomePage homePage = new HomePage();
    HospitalPage hospitalPage = new HospitalPage();
    @DataProvider(name = "tc12Data")
    public Object[][] getTC12Data() {
        Properties hospitalProperties = ConfigReader.initProperties();
        ExcelUtils.loadExcel(
                hospitalProperties.getProperty("excelPath"),
                hospitalProperties.getProperty("hospitalSheetName")
        );
        Map<String, String> rowData = new HashMap<>();
        rowData.put("TestCaseID", "TC12");
        rowData.put("Location", ExcelUtils.getCellData("TC12", "Location"));
        rowData.put("SearchKeyword", ExcelUtils.getCellData("TC12", "SearchKeyword"));
        rowData.put("RequiredMatchCount", ExcelUtils.getCellData("TC12", "RequiredMatchCount"));
        rowData.put("MinimumRating", ExcelUtils.getCellData("TC12", "MinimumRating"));
        return new Object[][]{
                {rowData}
        };
    }

    @Test(dataProvider = "tc12Data")
    public void verifyFirstNHospitalsAreCheckedAndMatchingHospitalsAreAddedToList(Map<String, String> data) throws InterruptedException {
        openApplication();
        String location = data.get("Location");
        String searchKeyword = data.get("SearchKeyword");
        int requiredMatchCount = Integer.parseInt(data.get("RequiredMatchCount"));
        double minimumRating = Double.parseDouble(data.get("MinimumRating"));
        type(homePage.hospitalLocationBox, location);
        waitForVisible(homePage.hospitalLocationBox).sendKeys(Keys.BACK_SPACE);
        waitForVisible(homePage.hospitalLocationBox).sendKeys(location.substring(location.length() - 1));
        click(homePage.locationOption(location));
        type(homePage.hospitalSearchBox, searchKeyword);
        click(homePage.searchOption(searchKeyword));
        waitForVisible(hospitalPage.hospitalNamesForSearchResults);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        ArrayList<WebElement> matchingHospitals = new ArrayList<>();
        int checkedCount = 0;
        while (matchingHospitals.size() < requiredMatchCount) {
            List<WebElement> hospitalNames = driver.findElements(hospitalPage.hospitalNamesForSearchResults);
            for (int i = checkedCount; i < hospitalNames.size(); i++) {
                WebElement hospitalName = hospitalNames.get(i);
                try {
                    WebElement hospitalCard = hospitalName.findElement(hospitalPage.hospitalCardFromName);
                    List<WebElement> open24x7 = hospitalCard.findElements(hospitalPage.open24x7Text);
                    if (open24x7.size() == 0) {
                        continue;
                    }
                    List<WebElement> ratingElement = hospitalCard.findElements(hospitalPage.ratingText);
                    if (ratingElement.size() == 0) {
                        continue;
                    }
                    String ratingText = ratingElement.get(0).getText().trim();
                    double rating = Double.parseDouble(ratingText);
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
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000);
            List<WebElement> newHospitalNames = driver.findElements(hospitalPage.hospitalNamesForSearchResults);
            if (newHospitalNames.size() == checkedCount) {
                break;
            }
        }
        System.out.println("Total hospitals checked: " + checkedCount);
        System.out.println("Matching hospitals stored in list: " + matchingHospitals.size());
        System.out.println("Matching hospital details:");
        for (int i = 0; i < matchingHospitals.size(); i++) {
            WebElement hospitalName = matchingHospitals.get(i);
            WebElement hospitalCard = hospitalName.findElement(hospitalPage.hospitalCardFromName);
            String name = hospitalName.getText();
            String rating = hospitalCard.findElement(hospitalPage.ratingText).getText();
            System.out.println((i + 1) + ". " + name + " - Rating: " + rating);
        }
        Assert.assertTrue(
                checkedCount > 0,
                "No hospitals were checked from the search results."
        );
        Assert.assertTrue(
                matchingHospitals.size() > 0,
                "No matching hospitals found with Open 24x7 and rating greater than " + minimumRating
        );
        System.out.println("TC12 Passed: Hospitals were checked and matching hospitals were added to list.");
    }
}