package org.practo.tests;

import base.CommonCode;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.practo.pages.HomePage;
import org.practo.pages.VideoConsultPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import utilities.ScreenshotUtil;

public class TC_018_InvalidMobileVideoConsult extends CommonCode {
    private HomePage homePage;
    private VideoConsultPage videoConsultPage;

    @Test
    public void verifyInvalidMobileNumberInVideoConsultFlow() {
        homePage = new HomePage(driver);
        videoConsultPage = new VideoConsultPage(driver);
        String symptom = ExcelUtils.getCellData("TC_018", "Symptom");
        String invalidMobile = ExcelUtils.getCellData("TC_018", "InvalidMobile");
        openApplication();

        //Step 1: Click Video Consult from Home Page
        try {
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            homePage.getVideoConsultHomeButton()
                    )
            );
            homePage.clickVideoConsultHomeButton();
        } catch (Exception e) {
            homePage.clickVideoConsultHomeButtonUsingJS();
        }

        wait.until(driver ->
                getCurrentUrl().contains("/consult")
        );
        System.out.println("Navigated to Video Consult page");
        System.out.println("Current URL: " + getCurrentUrl());

        //Step 2: Click Consult Now
        wait.until(driver ->
                videoConsultPage.isConsultNowDisplayed()
        );

        try {
            videoConsultPage.clickConsultNow();
        } catch (Exception e) {
            videoConsultPage.clickConsultNowUsingJS();
        }

        //Step 3: Wait for consultation form
        wait.until(driver ->
                getCurrentUrl().contains("new_consultation")
                        || videoConsultPage.isSymptomFieldDisplayed()
        );

        System.out.println("Consultation form opened");

        //Step 4: Enter symptom
        wait.until(driver ->
                videoConsultPage.isSymptomFieldDisplayed()
        );

        videoConsultPage.enterSymptom(symptom);
        System.out.println("Entered symptom: " + symptom);

        //Step 5: Select relevant specialist
        wait.until(driver ->
                videoConsultPage.isFirstSpecialistDisplayed()
        );

        try {
            videoConsultPage.selectFirstSpecialist();
        } catch (Exception e) {
            videoConsultPage.selectFirstSpecialistUsingJS();
        }

        System.out.println("Specialist selected");

        //Step 6: Enter invalid mobile number
        wait.until(driver ->
                videoConsultPage.isMobileNumberFieldDisplayed()
        );

        videoConsultPage.enterMobileNumber(invalidMobile);
        System.out.println("Entered invalid mobile number: " + invalidMobile);

        //Step 7: Click Continue
        wait.until(driver ->
                videoConsultPage.isContinueButtonDisplayed()
        );

        try {
            videoConsultPage.clickContinue();
        } catch (Exception e) {
            videoConsultPage.clickContinueUsingJS();
        }

        //Step 8: Wait for iframe popup
        wait.until(
                ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                        videoConsultPage.getLoginIframe()
                )
        );

        //Step 9: Validate invalid mobile message inside iframe
        wait.until(driver ->
                videoConsultPage.isInvalidMobileMessageDisplayedInIframe()
        );

        String invalidMessage = videoConsultPage.getInvalidMobileMessageInIframe();
        System.out.println("Invalid mobile validation message: " + invalidMessage);

        Assert.assertTrue(
                invalidMessage.contains("Not a valid mobile number"),
                "Expected invalid mobile number message is not displayed. Actual: " + invalidMessage
        );

        //Step 10: Capture screenshot while iframe popup is visible
        ScreenshotUtil.captureScreenshot(
                driver,
                "TC_018_Invalid_Mobile_Number"
        );

        //Step 11: Close OTP/Login popup inside iframe
        try {
            videoConsultPage.closeOtpPopup();
        } catch (Exception e) {
            videoConsultPage.closeOtpPopupUsingJS();
        }

        //Step 12: Switch back to main page
        videoConsultPage.switchToDefaultContent();

        //Step 13: Come back using top consultation page link
        wait.until(driver ->
                videoConsultPage.isBackToVideoConsultPageLinkDisplayed()
        );

        try {
            videoConsultPage.clickBackToVideoConsultPage();
        } catch (Exception e) {
            videoConsultPage.clickBackToVideoConsultPageUsingJS();
        }

        System.out.println("TC_018 Passed: Invalid mobile number validation message captured successfully");
    }
}