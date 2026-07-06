package org.practo.tests;

import base.BaseTest;
import org.practo.pages.CorporateWellnessPage;
import org.practo.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.WaitUtils;

public class TC_020_CorporateMenuOptions extends BaseTest {
    private HomePage homePage;
    private CorporateWellnessPage corporatePage;

    @Test
    public void verifyCorporateMenuOptions() {
        homePage = new HomePage(driver);
        commonCode.openApplication();

        // Step 1: Click For Corporates
        try {
            WaitUtils.waitForClickable(driver, homePage.getForCorporates());
            homePage.clickForCorporates();
        } catch (Exception e) {
            homePage.clickForCorporatesUsingJS();
        }

        // Step 2: Click Health & Wellness Plans
        try {
            WaitUtils.waitForClickable(driver, homePage.getHealthAndWellnessPlans());
            homePage.clickHealthAndWellnessPlans();
        } catch (Exception e) {
            homePage.clickHealthAndWellnessPlansUsingJS();
        }

        // Step 3: Validate Corporate Wellness page opened
        WaitUtils.waitUntil(driver, driver -> commonCode.getCurrentUrl().contains("/plus/corporate"));

        Assert.assertTrue(
                commonCode.getCurrentUrl().contains("/plus/corporate"),
                "Corporate Wellness page is not opened"
        );

        System.out.println("Navigated to Corporate Wellness page");
        System.out.println("Current URL : " + commonCode.getCurrentUrl());

        corporatePage = new CorporateWellnessPage(driver);

        // Step 4: Click Our Services
        corporatePage.scrollToTop();
        corporatePage.clickOurServices();
        corporatePage.scrollDown();
        System.out.println("Clicked Our Services");

        // Step 5: Click Practo Ecosystem
        corporatePage.scrollToTop();
        corporatePage.clickPractoEcosystem();
        corporatePage.scrollDown();
        System.out.println("Clicked Practo Ecosystem");

        // Step 6: Click Product Capabilities
        corporatePage.scrollToTop();
        corporatePage.clickProductCapabilities();
        corporatePage.scrollDown();
        System.out.println("Clicked Product Capabilities");

        // Step 7: Click Testimonials
        corporatePage.scrollToTop();
        corporatePage.clickTestimonials();
        corporatePage.scrollDown();
        System.out.println("Clicked Testimonials");

        // Step 8: Click FAQs
        corporatePage.scrollToTop();
        corporatePage.clickFAQs();
        corporatePage.scrollDown();
        System.out.println("Clicked FAQs");

        // Step 9: Validate user remains on Corporate Wellness page
        Assert.assertTrue(
                commonCode.getCurrentUrl().contains("/plus/corporate"),
                "User moved away from Corporate Wellness page"
        );

        System.out.println("TC_020 Passed: Corporate menu options are functioning properly.");
    }
}