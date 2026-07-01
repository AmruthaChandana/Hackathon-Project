package org.practo.tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.practo.pages.HomePage;
import org.practo.pages.VideoConsultPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TC_016_ExtractTop5FAQs extends BaseTest {

    HomePage homePage;
    VideoConsultPage videoConsultPage;

    @Test
    public void verifyTop5FAQsExtraction() {

        homePage = new HomePage();
        videoConsultPage = new VideoConsultPage();

        openApplication();

        /*
         * Step 1: Navigate to Video Consult
         */
        scrollDown();

        try {
            wait.until(ExpectedConditions.elementToBeClickable(homePage.videoConsultLink));
            click(homePage.videoConsultLink);
        } catch (Exception e) {
            scrollDown();
            clickUsingJS(homePage.videoConsultLink);
        }

        System.out.println("Navigated to Video Consult page");

        /*
         * Step 2: Wait for FAQ section
         */
        wait.until(ExpectedConditions.presenceOfElementLocated(videoConsultPage.faqSection));

        // bring section into view
        for (int i = 0; i < 3; i++) {
            scrollDown();
        }

        System.out.println("FAQ section found");

        /*
         * Step 3: DYNAMIC SCROLL (MOST IMPORTANT FIX )
         */

        wait.until(driver ->
                driver.findElements(videoConsultPage.faqQuestions).size() > 0
        );

        int previousCount = 0;
        int currentCount = 0;

        do {
            previousCount = driver.findElements(videoConsultPage.faqQuestions).size();

            scrollDown();

            try {
                Thread.sleep(800);
            } catch (Exception ignored) {}

            currentCount = driver.findElements(videoConsultPage.faqQuestions).size();

        } while (currentCount > previousCount);

        /*
         * Step 4: Extract FAQs
         */
        List<String> faqList = new ArrayList<>();

        List<WebElement> elements =
                driver.findElements(videoConsultPage.faqQuestions);

        for (WebElement element : elements) {
            try {
                String text = element.getText().trim();

                if (!text.isEmpty()) {
                    faqList.add(text);
                }

            } catch (Exception ignored) {
                // handles stale safely
            }
        }

        /*
         * Step 5: Print Top 5 FAQs
         */
        System.out.println("\nTop 5 FAQs:");

        int count = Math.min(5, faqList.size());

        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + faqList.get(i));
        }

        /*
         * Step 6: Validation (STRICT )
         */
        Assert.assertTrue(
                faqList.size() >= 5,
                "Less than 5 FAQs found"
        );

        /*
         * Step 7: Navigate back
         */
        driver.navigate().back();

        System.out.println("\nTC_016 Passed: Extracted 5 FAQs successfully");
    }
}
