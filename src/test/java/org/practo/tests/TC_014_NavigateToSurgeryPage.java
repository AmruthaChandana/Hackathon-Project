package org.practo.tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.practo.pages.HomePage;
import org.practo.pages.SurgeryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TC_014_NavigateToSurgeryPage extends BaseTest {

    private HomePage homePage;
    private SurgeryPage surgeryPage;

    @Test
    public void verifyUserCanNavigateToSurgeryPageAndPrintPopularTreatments() {

        homePage = new HomePage(driver);
        surgeryPage = new SurgeryPage(driver);

        /*
         * Step 1: Open Practo Home Page
         */
        openApplication();

        /*
         * Step 2: Click Surgeries from Home Page
         */
        try {
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            homePage.getSurgeriesLink()
                    )
            );

            homePage.clickSurgeries();

        } catch (Exception e) {
            homePage.clickSurgeriesUsingJS();
        }

        System.out.println("Navigated to Surgery page");

        /*
         * Step 3: Validate user is on Surgery/Care page
         */
        wait.until(driver ->
                getCurrentUrl().contains("/care")
        );

        Assert.assertTrue(
                getCurrentUrl().contains("/care"),
                "User is not navigated to Surgery page"
        );

        /*
         * Step 4: Scroll to Treatments Offered section
         */
        for (int i = 0; i < 6; i++) {

            scrollDown();

            if (surgeryPage.isSurgeryListSectionDisplayed()) {
                break;
            }
        }

        surgeryPage.scrollToSurgeryListSection();

        Assert.assertTrue(
                surgeryPage.isTreatmentsOfferedDisplayed(),
                "Treatments offered section is not displayed"
        );

        System.out.println("Treatments offered section found");

        /*
         * Step 5: Click Popular tab
         */
        surgeryPage.clickPopularTab();

        /*
         * Step 6: Wait until popular treatments are loaded
         */
        wait.until(driver ->
                surgeryPage.getPopularTreatmentCount() > 0
        );

        /*
         * Step 7: Extract and print popular treatments
         */
        List<String> popularTreatments =
                surgeryPage.getPopularTreatments();

        System.out.println("\nPopular Treatments Offered:");

        for (int i = 0; i < popularTreatments.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + popularTreatments.get(i)
            );
        }

        /*
         * Step 8: Validation
         */
        Assert.assertTrue(
                popularTreatments.size() > 0,
                "No popular treatments were extracted"
        );

        /*
         * Step 9: Navigate back to Home Page
         */
        driver.navigate().back();

        System.out.println(
                "\nTC_014 Passed: Surgery page opened, popular treatments printed, and navigated back"
        );
    }
}