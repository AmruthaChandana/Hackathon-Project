package org.practo.tests;

import base.CommonCode;
import org.practo.pages.CorporateWellnessPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExcelUtils;
import org.practo.pages.HomePage;

public class TC_020_CorporateDisableButton extends CommonCode {

    @Test
    public void verifyCorporateDisableButton() {

        loadCorporateSheet();

        String name =
                ExcelUtils.getCellData(
                        "TC_020",
                        "Name");

        String organization =
                ExcelUtils.getCellData(
                        "TC_020",
                        "Organization");

        String email =
                ExcelUtils.getCellData(
                        "TC_020",
                        "Email");

        String mobile =
                ExcelUtils.getCellData(
                        "TC_020",
                        "Mobile");

        String organizationSize =
                ExcelUtils.getCellData(
                        "TC_020",
                        "OrganizationSize");

        String interestedIn =
                ExcelUtils.getCellData(
                        "TC_020",
                        "InterestedIn");

        System.out.println("Name : " + name);
        System.out.println("Organization : " + organization);
        System.out.println("Email : " + email);
        System.out.println("Mobile : " + mobile);
        System.out.println("Organization Size : " + organizationSize);
        System.out.println("Interested In : " + interestedIn);

        HomePage homePage =
                new HomePage(driver);

        homePage.clickForCorporates();

        homePage.clickHealthAndWellnessPlans();

        CorporateWellnessPage corporatePage =
                new CorporateWellnessPage(driver);

        corporatePage.fillCorporateWellnessForm(
                name,
                organization,
                email,
                mobile,
                organizationSize,
                interestedIn
        );

        Assert.assertFalse(
                corporatePage.isSubmitButtonEnabled(),
                "Schedule Demo button should remain disabled for invalid data."
        );

        System.out.println(
                "Schedule Demo button remains disabled for invalid data. Test Passed."
        );
    }
}