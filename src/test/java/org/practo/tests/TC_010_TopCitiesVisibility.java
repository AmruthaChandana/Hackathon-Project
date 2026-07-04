package org.practo.tests;

import java.time.Duration;
import java.util.List;
import base.CommonCode;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.practo.pages.LabTestsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;

public class TC_010_TopCitiesVisibility extends CommonCode {

    @Test
    public void verifyTopCitiesSectionVisible() {
        driver.get(ConfigReader.getProperty("url"));
        LabTestsPage labTestsPage = new LabTestsPage(driver);
        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        Integer.parseInt(ConfigReader.getProperty("explicitWait"))
                )
        );
        labTestsPage.clickLabTestsMenu();
        wait.until(ExpectedConditions.urlContains("tests"));
        Assert.assertTrue(
                labTestsPage.getTopCitiesCount() > 0,
                "Top Cities section is not visible"
        );
        List<String> cities = labTestsPage.getTopCityNames();
        System.out.println("Top Cities:");
        for (String city : cities) {
            System.out.println(city);
        }
        System.out.println("Total Cities Found: " + cities.size());
    }
}
