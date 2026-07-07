package org.practo.tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.practo.pages.HomePage;
import org.practo.pages.VideoConsultPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class TC_015_ExtractTop5FAQs extends BaseTest {
    private static final Logger logger =
            LogManager.getLogger(TC_015_ExtractTop5FAQs.class);
    private HomePage homePage;
    private VideoConsultPage videoConsultPage;

    @Test
    public void verifyTop5FAQsExtraction() {
        logger.info("Starting TC_015 - Extract Top 5 FAQs");

        homePage = new HomePage(driver);
        videoConsultPage = new VideoConsultPage(driver);

        // Step 1: Navigate to Video Consult Page
        commonCode.scrollDown();
        try {
            logger.info("Clicking Video Consult link");
            commonCode.waitForClickable(homePage.getVideoConsultLink());
            homePage.clickVideoConsult();
        } catch (Exception e) {
            logger.warn("Normal click failed. Using JavaScript click.");
            commonCode.scrollDown();
            homePage.clickVideoConsultUsingJS();
        }

        commonCode.waitUntil(
                driver ->
                        commonCode.getCurrentUrl()
                                .contains("/consult"));

        logger.info("Navigated to Video Consult page");
        logger.info("Current URL: {}", commonCode.getCurrentUrl());

        // Step 2: Scroll Until FAQ Section Found
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
                "FAQ section not found on Video Consult page");
        videoConsultPage.scrollToFaqSection();

        Assert.assertTrue(
                videoConsultPage.isFaqSectionDisplayed()
                        || videoConsultPage.getFaqCount() > 0,
                "FAQ section not displayed");

        logger.info("FAQ section found successfully");

        // Step 3: Wait Until FAQs Load
        commonCode.waitUntil(
                driver ->
                        videoConsultPage.getFaqCount() > 0);

        logger.info("FAQs loaded successfully");

        // Step 4: Scroll Until All FAQs Load
        int previousCount;
        int currentCount;

        do {
            previousCount = videoConsultPage.getFaqCount();
            commonCode.scrollDown();
            int finalPreviousCount = previousCount;
            try {
                commonCode.waitUntil(
                        driver ->
                                videoConsultPage.getFaqCount()
                                        > finalPreviousCount);
            } catch (TimeoutException ignored) {
                logger.debug(
                        "No additional FAQs loaded after scrolling.");
            }
            currentCount = videoConsultPage.getFaqCount();
        } while (currentCount > previousCount);

        // Step 5: Extract Top 5 FAQs
        List<String> topFaqs =
                videoConsultPage.getTopFiveFaqQuestions();

        // Step 6: Print FAQs
        logger.info("Top 5 FAQs:");

        for (int i = 0; i < topFaqs.size(); i++) {
            logger.info(
                    "{}. {}",
                    (i + 1),
                    topFaqs.get(i));
        }

        // Step 7: Validate FAQ Count
        Assert.assertTrue(
                topFaqs.size() >= 5,
                "Less than 5 FAQs found");

        logger.info("Total FAQs Extracted: {}", topFaqs.size());

        // Step 8: Navigate Back
        driver.navigate().back();

        logger.info("Returned from Video Consult page");
        logger.info("TC_015 Passed: Extracted 5 FAQs successfully");
    }
}