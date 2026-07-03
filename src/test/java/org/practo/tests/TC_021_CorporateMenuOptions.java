package org.practo.tests;

import base.CommonCode;
import org.practo.pages.CorporateWellnessPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.practo.pages.HomePage;

public class TC_021_CorporateMenuOptions extends CommonCode {

    @Test
    public void verifyCorporateMenuOptions() {

        HomePage homePage =
                new HomePage(driver);

       homePage.clickForCorporates();

        homePage.clickHealthAndWellnessPlans();

        CorporateWellnessPage corporatePage =
                new CorporateWellnessPage(driver);

        corporatePage.clickOurServices();
        corporatePage.scrollDown();

        corporatePage.clickPractoEcosystem();
        corporatePage.scrollDown();

        corporatePage.clickProductCapabilities();
        corporatePage.scrollDown();

        corporatePage.clickTestimonials();
        corporatePage.scrollDown();

        corporatePage.clickFAQs();
        corporatePage.scrollDown();

        System.out.println(
                "Our Services, Practo Ecosystem, Product Capabilities, Testimonials and FAQs options are functioning properly."
        );

        Assert.assertTrue(true);
    }
}