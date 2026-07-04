package org.practo.tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.practo.pages.LabTestsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import java.time.Duration;

public class TC_006_FindTest extends BaseTest {

    @Test
    public void verifyDiabetesHealthConcernNavigation() {
        driver.get(ConfigReader.getProperty("url"));
        LabTestsPage page = new LabTestsPage(driver);
        WebDriverWait wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(
                                Integer.parseInt(
                                        ConfigReader.getProperty("explicitWait")
                                )
                        )
                );
        page.clickLabTestsMenu();
        page.selectCity(
                ConfigReader.getProperty("cityName")
        );
        wait.until(
                ExpectedConditions.urlContains("bangalore")
        );
        page.clickDiabetesHealthConcern();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("diabetes-checkup"),
                "User is not navigated to Diabetes Checkup page"
        );
        System.out.println(
                "TC_005 Passed - User navigated to Diabetes Checkup page successfully"
        );
    }
}