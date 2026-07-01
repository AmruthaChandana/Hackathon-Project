package org.practo.pages;

import org.openqa.selenium.By;

public class VideoConsultPage {

    public By consultNowButton = By.xpath("//*[contains(text(),'Consult Now') or contains(text(),'Start consultation')]");
    public By symptomField = By.xpath("//input[contains(@placeholder,'symptom') or contains(@placeholder,'health')]");
    public By specialistOption = By.xpath("(//*[contains(text(),'General Physician') or contains(text(),'Dermatologist') or contains(text(),'Specialist')])[1]");
    public By mobileNumberField = By.xpath("//input[contains(@placeholder,'Mobile') or contains(@placeholder,'phone')]");
    public By invalidMobileMessage = By.xpath("//*[contains(text(),'valid mobile') or contains(text(),'Invalid') or contains(text(),'10 digit')]");

    public By faqSection = By.xpath("//div[@id='FaqSection']");
    public By faqQuestions = By.xpath(
            "//div[starts-with(@data-testid,'faq_')]//h3"
    );


}