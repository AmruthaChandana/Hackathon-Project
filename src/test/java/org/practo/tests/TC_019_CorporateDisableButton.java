package org.practo.tests;

import base.BaseTest;
import org.practo.pages.CorporateWellnessPage;
import org.practo.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import utilities.WaitUtils;

public class TC_019_CorporateDisableButton extends BaseTest {
    private HomePage homePage;
    private CorporateWellnessPage corporatePage;

    @Test
    public void verifyCorporateDisableButton() {
        commonCode.loadCorporateSheet();
        String name = ExcelUtils.getCellData("TC_019", "Name");
        String organization = ExcelUtils.getCellData("TC_019", "Organization");
        String email = ExcelUtils.getCellData("TC_019", "Email");
        String mobile = ExcelUtils.getCellData("TC_019", "Mobile");
        String organizationSize = ExcelUtils.getCellData("TC_019", "OrganizationSize");
        String interestedIn = ExcelUtils.getCellData("TC_019", "InterestedIn");

        Assert.assertFalse(name.isEmpty(), "Name is empty in Excel");
        Assert.assertFalse(organization.isEmpty(), "Organization is empty in Excel");
        Assert.assertFalse(email.isEmpty(), "Email is empty in Excel");
        Assert.assertFalse(mobile.isEmpty(), "Mobile is empty in Excel");
        Assert.assertFalse(organizationSize.isEmpty(), "Organization size is empty in Excel");
        Assert.assertFalse(interestedIn.isEmpty(), "Interested In is empty in Excel");

        System.out.println("Name : " + name);
        System.out.println("Organization : " + organization);
        System.out.println("Email : " + email);
        System.out.println("Mobile : " + mobile);
        System.out.println("Organization Size : " + organizationSize);
        System.out.println("Interested In : " + interestedIn);

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
                "Corporate wellness page is not opened"
        );

        System.out.println("Navigated to Corporate Wellness page");
        System.out.println("Current URL : " + commonCode.getCurrentUrl());

        corporatePage = new CorporateWellnessPage(driver);

        // Step 4: Validate form visible
        Assert.assertTrue(
                corporatePage.isCorporateFormDisplayed(),
                "Corporate wellness form is not displayed"
        );

        corporatePage.scrollToForm();

        // Step 5: Fill Corporate Wellness form
        corporatePage.fillCorporateWellnessForm(
                name,
                organization,
                email,
                mobile,
                organizationSize,
                interestedIn
        );

        // Step 6: Validate Schedule Demo button remains disabled
        Assert.assertFalse(
                corporatePage.isSubmitButtonEnabled(),
                "Schedule Demo button should remain disabled for invalid data."
        );

        System.out.println("TC_019 Passed: Schedule Demo button remains disabled for invalid data.");
    }
}