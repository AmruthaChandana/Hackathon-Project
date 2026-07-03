package org.practo.tests;

import base.BaseTest;
import base.CommonCode;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.practo.pages.HomePage;
import org.practo.pages.VideoConsultPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TC_016_ExtractTop5FAQs extends CommonCode {
    private HomePage homePage;
    private VideoConsultPage videoConsultPage;

    @Test
    public void verifyTop5FAQsExtraction() {
        homePage = new HomePage(driver);
        videoConsultPage = new VideoConsultPage(driver);
        openApplication();
        //Step 1: Navigate to Video Consult Page
        scrollDown();
        try {
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            homePage.getVideoConsultLink()
                    )
            );
            homePage.clickVideoConsult();
        } catch (Exception e) {
            scrollDown();
            homePage.clickVideoConsultUsingJS();
        }
        System.out.println("Navigated to Video Consult page");
        //Step 2: Scroll to FAQ Section
        for (int i = 0; i < 5; i++) {
            scrollDown();
        }
        Assert.assertTrue(
                videoConsultPage.isFaqSectionDisplayed(),
                "FAQ section not displayed"
        );
        System.out.println("FAQ section found");
        //Step 3: Wait until at least one FAQ is loaded
        wait.until(driver ->
                videoConsultPage.getFaqCount() > 0
        );
        //Step 4: Scroll dynamically until all FAQs are loaded
        int previousCount;
        int currentCount;
        do {
            previousCount = videoConsultPage.getFaqCount();
            scrollDown();
            try {
                Thread.sleep(800);
            } catch (Exception ignored) {
            }
            currentCount = videoConsultPage.getFaqCount();
        } while (currentCount > previousCount);
        //Step 5: Extract Top 5 FAQs
        List<String> topFaqs =
                videoConsultPage.getTopFiveFaqQuestions();
        //Step 6: Print FAQs
        System.out.println("\nTop 5 FAQs:");
        for (int i = 0; i < topFaqs.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + topFaqs.get(i)
            );
        }
        //Step 7: Validation
        Assert.assertTrue(
                topFaqs.size() >= 5,
                "Less than 5 FAQs found"
        );
        // Step 8: Navigate Back
        driver.navigate().back();
        System.out.println(
                "\nTC_016 Passed: Extracted 5 FAQs successfully"
        );
    }
}