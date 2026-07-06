package org.practo.tests;

import base.BaseTest;
import org.openqa.selenium.TimeoutException;
import org.practo.pages.HomePage;
import org.practo.pages.VideoConsultPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.WaitUtils;
import java.util.List;

public class TC_015_ExtractTop5FAQs extends BaseTest {
    private HomePage homePage;
    private VideoConsultPage videoConsultPage;

    @Test
    public void verifyTop5FAQsExtraction() {
        homePage = new HomePage(driver);
        videoConsultPage = new VideoConsultPage(driver);
        commonCode.openApplication();

        // Step 1: Navigate to Video Consult page
        commonCode.scrollDown();
        try {
            WaitUtils.waitForClickable(driver, homePage.getVideoConsultLink());
            homePage.clickVideoConsult();
        } catch (Exception e) {
            commonCode.scrollDown();
            homePage.clickVideoConsultUsingJS();
        }

        WaitUtils.waitUntil(driver, driver -> commonCode.getCurrentUrl().contains("/consult"));
        System.out.println("Navigated to Video Consult page");
        System.out.println("Current URL: " + commonCode.getCurrentUrl());

        // Step 2: Scroll dynamically until FAQ section/questions are found
        boolean faqFound = false;
        for (int i = 0; i < 20; i++) {
            if (videoConsultPage.isFaqSectionPresent() || videoConsultPage.getFaqCount() > 0) {
                faqFound = true;
                break;
            }
            commonCode.scrollDown();
        }

        Assert.assertTrue(
                faqFound,
                "FAQ section not found on Video Consult page"
        );

        videoConsultPage.scrollToFaqSection();

        Assert.assertTrue(
                videoConsultPage.isFaqSectionDisplayed() || videoConsultPage.getFaqCount() > 0,
                "FAQ section not displayed"
        );

        System.out.println("FAQ section found");

        // Step 3: Wait until at least one FAQ is loaded
        WaitUtils.waitUntil(driver, driver -> videoConsultPage.getFaqCount() > 0);

        // Step 4: Scroll dynamically until all FAQs are loaded
        int previousCount;
        int currentCount;
        do {
            previousCount = videoConsultPage.getFaqCount();
            commonCode.scrollDown();
            int finalPreviousCount = previousCount;
            try {
                WaitUtils.waitUntil(driver, driver -> videoConsultPage.getFaqCount() > finalPreviousCount);
            } catch (TimeoutException ignored) {
            }
            currentCount = videoConsultPage.getFaqCount();
        } while (currentCount > previousCount);

        // Step 5: Extract top 5 FAQs
        List<String> topFaqs = videoConsultPage.getTopFiveFaqQuestions();

        // Step 6: Print FAQs
        System.out.println("\nTop 5 FAQs:");
        for (int i = 0; i < topFaqs.size(); i++) {
            System.out.println((i + 1) + ". " + topFaqs.get(i));
        }

        // Step 7: Validate FAQ count
        Assert.assertTrue(
                topFaqs.size() >= 5,
                "Less than 5 FAQs found"
        );

        // Step 8: Navigate back
        driver.navigate().back();
        System.out.println("TC_015 Passed: Extracted 5 FAQs successfully");
    }
}