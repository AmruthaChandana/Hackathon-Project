package org.practo.tests;

import base.BaseTest;
import org.practo.pages.HomePage;
import org.practo.pages.VideoConsultPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import utilities.ScreenshotUtil;
import utilities.WaitUtils;

public class TC_014_InvalidMobileVideoConsult extends BaseTest {
    private HomePage homePage;
    private VideoConsultPage videoConsultPage;

    @Test
    public void verifyInvalidMobileNumberInVideoConsultFlow() {
        homePage = new HomePage(driver);
        videoConsultPage = new VideoConsultPage(driver);
        String symptom = ExcelUtils.getCellData("TC_014", "Symptom");
        String invalidMobile = ExcelUtils.getCellData("TC_014", "InvalidMobile");
        commonCode.openApplication();

        // Step 1: Click Video Consult from Home Page
        try {
            WaitUtils.waitForClickable(driver, homePage.getVideoConsultLink());
            homePage.clickVideoConsult();
        } catch (Exception e) {
            homePage.clickVideoConsultUsingJS();
        }

        WaitUtils.waitUntil(driver, driver -> commonCode.getCurrentUrl().contains("/consult"));
        System.out.println("Navigated to Video Consult page");
        System.out.println("Current URL: " + commonCode.getCurrentUrl());

        // Step 2: Click Consult Now
        WaitUtils.waitUntil(driver, driver -> videoConsultPage.isConsultNowDisplayed());
        try {
            videoConsultPage.clickConsultNow();
        } catch (Exception e) {
            videoConsultPage.clickConsultNowUsingJS();
        }

        // Step 3: Wait for consultation form
        WaitUtils.waitUntil(driver, driver ->
                commonCode.getCurrentUrl().contains("new_consultation") || videoConsultPage.isSymptomFieldDisplayed()
        );
        System.out.println("Consultation form opened");

        // Step 4: Enter symptom
        WaitUtils.waitUntil(driver, driver -> videoConsultPage.isSymptomFieldDisplayed());
        videoConsultPage.enterSymptom(symptom);
        System.out.println("Entered symptom: " + symptom);

        // Step 5: Select relevant specialist
        WaitUtils.waitUntil(driver, driver -> videoConsultPage.isFirstSpecialistDisplayed());
        try {
            videoConsultPage.selectFirstSpecialist();
        } catch (Exception e) {
            videoConsultPage.selectFirstSpecialistUsingJS();
        }
        System.out.println("Specialist selected");

        // Step 6: Enter invalid mobile number
        WaitUtils.waitUntil(driver, driver -> videoConsultPage.isMobileNumberFieldDisplayed());
        videoConsultPage.enterMobileNumber(invalidMobile);
        System.out.println("Entered invalid mobile number: " + invalidMobile);

        // Step 7: Click Continue
        WaitUtils.waitUntil(driver, driver -> videoConsultPage.isContinueButtonDisplayed());
        try {
            videoConsultPage.clickContinue();
        } catch (Exception e) {
            videoConsultPage.clickContinueUsingJS();
        }

        // Step 8: Wait for iframe popup
        WaitUtils.waitForFrameAndSwitchToIt(driver, videoConsultPage.getLoginIframe());

        // Step 9: Validate invalid mobile message inside iframe
        WaitUtils.waitUntil(driver, driver -> videoConsultPage.isInvalidMobileMessageDisplayedInIframe());
        String invalidMessage = videoConsultPage.getInvalidMobileMessageInIframe();
        System.out.println("Invalid mobile validation message: " + invalidMessage);

        Assert.assertTrue(
                invalidMessage.contains("Not a valid mobile number"),
                "Expected invalid mobile number message is not displayed. Actual: " + invalidMessage
        );

        // Step 10: Capture screenshot while iframe popup is visible
        ScreenshotUtil.captureScreenshot(driver, "TC_014_Invalid_Mobile_Number");

        // Step 11: Close OTP/Login popup inside iframe
        try {
            videoConsultPage.closeOtpPopup();
        } catch (Exception e) {
            videoConsultPage.closeOtpPopupUsingJS();
        }

        // Step 12: Switch back to main page
        videoConsultPage.switchToDefaultContent();

        // Step 13: Come back using top consultation page link
        WaitUtils.waitUntil(driver, driver -> videoConsultPage.isBackToVideoConsultPageLinkDisplayed());
        try {
            videoConsultPage.clickBackToVideoConsultPage();
        } catch (Exception e) {
            videoConsultPage.clickBackToVideoConsultPageUsingJS();
        }

        System.out.println("TC_014 Passed: Invalid mobile number validation message captured successfully");
    }
}