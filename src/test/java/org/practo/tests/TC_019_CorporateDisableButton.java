package org.practo.tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.practo.pages.CorporateWellnessPage;
import org.practo.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;

public class TC_019_CorporateDisableButton extends BaseTest {
    private static final Logger logger = LogManager.getLogger(TC_019_CorporateDisableButton.class);
    private HomePage homePage;
    private CorporateWellnessPage corporatePage;

    @Test
    public void verifyCorporateDisableButton() {
        logger.info("Starting TC_019 - Verify Corporate Disable Button");
        commonCode.loadCorporateSheet();
        String name = ExcelUtils.getCellData("TC_019", "Name");
        String organization = ExcelUtils.getCellData("TC_019", "Organization");
        String email = ExcelUtils.getCellData("TC_019", "Email");
        String mobile = ExcelUtils.getCellData("TC_019", "Mobile");
        String organizationSize = ExcelUtils.getCellData("TC_019", "OrganizationSize");
        String interestedIn = ExcelUtils.getCellData("TC_019", "InterestedIn");

        Assert.assertFalse(
                name.isEmpty(),
                "Name is empty in Excel"
        );

        Assert.assertFalse(
                organization.isEmpty(),
                "Organization is empty in Excel"
        );

        Assert.assertFalse(
                email.isEmpty(),
                "Email is empty in Excel"
        );

        Assert.assertFalse(
                mobile.isEmpty(),
                "Mobile is empty in Excel"
        );

        Assert.assertFalse(
                organizationSize.isEmpty(),
                "Organization size is empty in Excel"
        );

        Assert.assertFalse(
                interestedIn.isEmpty(),
                "Interested In is empty in Excel"
        );

        logger.info("Name : {}", name);
        logger.info("Organization : {}", organization);
        logger.info("Email : {}", email);
        logger.info("Mobile : {}", mobile);
        logger.info("Organization Size : {}", organizationSize);
        logger.info("Interested In : {}", interestedIn);

        homePage = new HomePage(driver);

        // Step 1: Click For Corporates
        try {
            logger.info("Clicking For Corporates option");
            commonCode.waitForClickable(homePage.getForCorporates());
            homePage.clickForCorporates();
        } catch (Exception e) {
            logger.warn("Normal click failed. Using JavaScript click.");
            homePage.clickForCorporatesUsingJS();
        }

        // Step 2: Click Health & Wellness Plans
        try {
            logger.info("Clicking Health & Wellness Plans");
            commonCode.waitForClickable(homePage.getHealthAndWellnessPlans());
            homePage.clickHealthAndWellnessPlans();
        } catch (Exception e) {
            logger.warn("Normal click failed. Using JavaScript click.");
            homePage.clickHealthAndWellnessPlansUsingJS();
        }

        // Step 3: Validate Corporate Wellness Page Opened
        commonCode.waitUntil(driver ->
                        commonCode.getCurrentUrl()
                                .contains("/plus/corporate")
        );

        Assert.assertTrue(
                commonCode.getCurrentUrl()
                        .contains("/plus/corporate"),
                "Corporate wellness page is not opened"
        );

        logger.info("Navigated to Corporate Wellness page");
        logger.info("Current URL : {}", commonCode.getCurrentUrl());
        corporatePage = new CorporateWellnessPage(driver);

        // Step 4: Validate Form Visible
        Assert.assertTrue(
                corporatePage.isCorporateFormDisplayed(),
                "Corporate wellness form is not displayed"
        );

        logger.info("Corporate wellness form is displayed");
        corporatePage.scrollToForm();

        // Step 5: Fill Corporate Wellness Form
        logger.info("Filling Corporate Wellness form");

        corporatePage.fillCorporateWellnessForm(
                name,
                organization,
                email,
                mobile,
                organizationSize,
                interestedIn
        );

        // Step 6: Validate Schedule Demo Button Disabled
        Assert.assertFalse(
                corporatePage.isSubmitButtonEnabled(),
                "Schedule Demo button should remain disabled for invalid data."
        );

        logger.info("Verified Schedule Demo button remains disabled");

        logger.info("TC_019 Passed: Schedule Demo button remains disabled for invalid data.");
    }
}