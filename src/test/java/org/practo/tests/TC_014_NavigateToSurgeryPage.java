package org.practo.tests;

import base.CommonCode;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.practo.pages.HomePage;
import org.practo.pages.SurgeryPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class TC_014_NavigateToSurgeryPage extends CommonCode {
    private HomePage homePage;
    private SurgeryPage surgeryPage;

    @Test
    public void verifySurgeryPageAndPrintPopularTreatments() {
        homePage = new HomePage(driver);
        surgeryPage = new SurgeryPage(driver);
        //Step 1: Open Home Page
        openApplication();
        //Step 2: Click Surgeries button from Home Page
        try {
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            homePage.getSurgeriesButton()
                    )
            );
            homePage.clickSurgeriesButton();
        } catch (Exception e) {
            homePage.clickSurgeriesButtonUsingJS();
        }
        //Step 3: Validate redirected to Surgery/Care page
        wait.until(driver ->
                getCurrentUrl().contains("/care")
        );
        Assert.assertTrue(
                getCurrentUrl().contains("/care"),
                "User is not navigated to Surgery page"
        );
        System.out.println("Navigated to Surgery page");
        System.out.println("Current URL: " + getCurrentUrl());
        //Step 4: Scroll to Treatments Offered section
        boolean treatmentsSectionFound = false;
        for (int i = 0; i < 15; i++) {
            if (surgeryPage.isTreatmentsOfferedDisplayed()) {
                treatmentsSectionFound = true;
                break;
            }
            scrollDown();
        }
        Assert.assertTrue(
                treatmentsSectionFound,
                "Treatments Offered section not found"
        );
        surgeryPage.scrollToTreatmentsOffered();
        System.out.println("Treatments Offered section found");
        //Step 5: Wait until Popular grid is displayed
        wait.until(driver ->
                surgeryPage.isPopularGridDisplayed()
        );
        //Step 6: Wait until popular treatments are loaded
        wait.until(driver ->
                surgeryPage.getPopularTreatmentsCount() > 0
        );
        //Step 7: Extract and print all popular treatments
        List<String> popularTreatments = surgeryPage.getPopularTreatments();
        System.out.println("\nPopular Treatments Offered:");
        for (int i = 0; i < popularTreatments.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + popularTreatments.get(i)
            );
        }
        //Step 8: Validation
        Assert.assertTrue(
                popularTreatments.size() > 0,
                "No popular treatments were extracted"
        );
        //Step 9: Redirect back to Home Page
        driver.navigate().back();
        wait.until(driver ->
                getCurrentUrl().equalsIgnoreCase("https://www.practo.com/")
                        || getCurrentUrl().contains("practo.com")
        );
        System.out.println(
                "\nTC_014 Passed: Surgery page opened, popular treatments printed, and redirected back"
        );
    }
}