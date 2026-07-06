package org.practo.tests;

import base.BaseTest;
import org.practo.pages.HomePage;
import org.practo.pages.SurgeryPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.WaitUtils;
import java.util.List;

public class TC_013_NavigateToSurgeryPage extends BaseTest {
    private HomePage homePage;
    private SurgeryPage surgeryPage;

    @Test
    public void verifySurgeryPageAndPrintPopularTreatments() {
        homePage = new HomePage(driver);
        surgeryPage = new SurgeryPage(driver);
        commonCode.openApplication();

        // Step 1: Click Surgeries button from Home Page
        try {
            WaitUtils.waitForClickable(driver, homePage.getSurgeriesButton());
            homePage.clickSurgeriesButton();
        } catch (Exception e) {
            homePage.clickSurgeriesButtonUsingJS();
        }

        // Step 2: Validate redirected to Surgery/Care page
        WaitUtils.waitUntil(driver, driver -> commonCode.getCurrentUrl().contains("/care"));

        Assert.assertTrue(
                commonCode.getCurrentUrl().contains("/care"),
                "User is not navigated to Surgery page"
        );

        System.out.println("Navigated to Surgery page");
        System.out.println("Current URL: " + commonCode.getCurrentUrl());

        // Step 3: Scroll to Treatments Offered section
        boolean treatmentsSectionFound = false;

        for (int i = 0; i < 15; i++) {
            if (surgeryPage.isTreatmentsOfferedDisplayed()) {
                treatmentsSectionFound = true;
                break;
            }
            commonCode.scrollDown();
        }

        Assert.assertTrue(
                treatmentsSectionFound,
                "Treatments Offered section not found"
        );

        surgeryPage.scrollToTreatmentsOffered();
        System.out.println("Treatments Offered section found");

        // Step 4: Wait until Popular grid is displayed
        WaitUtils.waitUntil(driver, driver -> surgeryPage.isPopularGridDisplayed());

        // Step 5: Wait until popular treatments are loaded
        WaitUtils.waitUntil(driver, driver -> surgeryPage.getPopularTreatmentsCount() > 0);

        // Step 6: Extract and print all popular treatments
        List<String> popularTreatments = surgeryPage.getPopularTreatments();

        System.out.println("\nPopular Treatments Offered:");

        for (int i = 0; i < popularTreatments.size(); i++) {
            System.out.println((i + 1) + ". " + popularTreatments.get(i));
        }

        // Step 7: Validate popular treatments extracted
        Assert.assertTrue(
                popularTreatments.size() > 0,
                "No popular treatments were extracted"
        );

        // Step 8: Redirect back to Home Page
        driver.navigate().back();

        WaitUtils.waitUntil(driver, driver ->
                commonCode.getCurrentUrl().equalsIgnoreCase(prop.getProperty("url"))
                        || commonCode.getCurrentUrl().contains("practo.com")
        );

        System.out.println("TC_013 Passed: Surgery page opened, popular treatments printed, and redirected back");
    }
}
