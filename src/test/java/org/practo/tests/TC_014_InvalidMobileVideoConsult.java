package org.practo.tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.practo.pages.HomePage;
import org.practo.pages.VideoConsultPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import utilities.ScreenshotUtil;

public class TC_014_InvalidMobileVideoConsult extends BaseTest {
    private static final Logger logger = LogManager.getLogger(TC_014_InvalidMobileVideoConsult.class);

    private HomePage homePage;
    private VideoConsultPage videoConsultPage;

    @Test
    public void verifyInvalidMobileNumberInVideoConsultFlow() {
        logger.info("Starting TC_014 - Invalid Mobile Number Video Consult");

        homePage = new HomePage(driver);
        videoConsultPage = new VideoConsultPage(driver);

        String symptom = ExcelUtils.getCellData("TC_014", "Symptom");
        String invalidMobile = ExcelUtils.getCellData("TC_014", "InvalidMobile");

        logger.info("Symptom: {}", symptom);
        logger.info("Invalid Mobile Number: {}", invalidMobile);

        // Step 1: Click Video Consult
        try {
            logger.info("Clicking Video Consult link");

            commonCode.waitForClickable(
                    homePage.getVideoConsultLink()
            );

            homePage.clickVideoConsult();
        } catch (Exception e) {
            logger.warn("Normal click failed. Using JavaScript click.");
            homePage.clickVideoConsultUsingJS();
        }

        commonCode.waitUntil(driver ->
                commonCode.getCurrentUrl().contains("/consult")
        );

        logger.info("Navigated to Video Consult page");
        logger.info("Current URL: {}", commonCode.getCurrentUrl());

        // Step 2: Click Consult Now
        commonCode.waitUntil(driver ->
                videoConsultPage.isConsultNowDisplayed()
        );

        try {
            logger.info("Clicking Consult Now button");
            videoConsultPage.clickConsultNow();
        } catch (Exception e) {
            logger.warn("Normal click failed. Using JavaScript click.");
            videoConsultPage.clickConsultNowUsingJS();
        }

        // Step 3: Wait for Consultation Form
        commonCode.waitUntil(driver ->
                commonCode.getCurrentUrl().contains("new_consultation")
                        || videoConsultPage.isSymptomFieldDisplayed()
        );

        logger.info("Consultation form opened");

        // Step 4: Enter Symptom
        commonCode.waitUntil(driver ->
                videoConsultPage.isSymptomFieldDisplayed()
        );

        videoConsultPage.enterSymptom(symptom);

        logger.info("Entered symptom: {}", symptom);

        // Step 5: Select Specialist
        commonCode.waitUntil(driver ->
                videoConsultPage.isFirstSpecialistDisplayed()
        );

        try {
            videoConsultPage.selectFirstSpecialist();
        } catch (Exception e) {
            logger.warn("Specialist selection click failed. Using JavaScript click.");
            videoConsultPage.selectFirstSpecialistUsingJS();
        }

        logger.info("Specialist selected successfully");

        // Step 6: Enter Invalid Mobile
        commonCode.waitUntil(driver ->
                videoConsultPage.isMobileNumberFieldDisplayed()
        );

        videoConsultPage.enterMobileNumber(invalidMobile);

        logger.info("Entered invalid mobile number: {}", invalidMobile);

        // Step 7: Click Continue
        commonCode.waitUntil(driver ->
                videoConsultPage.isContinueButtonDisplayed()
        );

        try {
            videoConsultPage.clickContinue();
        } catch (Exception e) {
            logger.warn("Continue button click failed. Using JavaScript click.");
            videoConsultPage.clickContinueUsingJS();
        }

        // Step 8: Switch to Iframe
        logger.info("Switching to login iframe");

        commonCode.waitForFrameAndSwitchToIt(
                videoConsultPage.getLoginIframe()
        );

        // Step 9: Verify Validation Message
        commonCode.waitUntil(driver ->
                videoConsultPage.isInvalidMobileMessageDisplayedInIframe()
        );

        String invalidMessage =
                videoConsultPage.getInvalidMobileMessageInIframe();

        logger.info(
                "Invalid mobile validation message: {}",
                invalidMessage
        );

        Assert.assertTrue(
                invalidMessage.contains("Not a valid mobile number"),
                "Expected invalid mobile number message is not displayed. Actual: "
                        + invalidMessage
        );

        // Step 10: Capture Screenshot
        logger.info("Capturing validation screenshot");

        ScreenshotUtil.captureScreenshot(
                driver,
                "TC_014_Invalid_Mobile_Number"
        );

        // Step 11: Close OTP Popup
        try {
            videoConsultPage.closeOtpPopup();
        } catch (Exception e) {
            logger.warn("OTP popup close click failed. Using JavaScript click.");
            videoConsultPage.closeOtpPopupUsingJS();
        }

        logger.info("OTP/Login popup closed");

        // Step 12: Switch Back
        videoConsultPage.switchToDefaultContent();

        logger.info("Switched back to default content");

        // Step 13: Navigate Back
        commonCode.waitUntil(driver ->
                videoConsultPage.isBackToVideoConsultPageLinkDisplayed()
        );

        try {
            videoConsultPage.clickBackToVideoConsultPage();
        } catch (Exception e) {
            logger.warn("Back navigation click failed. Using JavaScript click.");
            videoConsultPage.clickBackToVideoConsultPageUsingJS();
        }

        logger.info("TC_014 Passed: Invalid mobile number validation message captured successfully");
    }
}