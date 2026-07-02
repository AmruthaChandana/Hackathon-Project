package org.practo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VideoConsultPage {

    private WebDriver driver;

    public VideoConsultPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[contains(text(),'Consult Now') or contains(text(),'Start consultation')]")
    private WebElement consultNowButton;

    @FindBy(xpath = "//input[contains(@placeholder,'symptom') or contains(@placeholder,'health')]")
    private WebElement symptomField;

    @FindBy(xpath = "(//*[contains(text(),'General Physician') or contains(text(),'Dermatologist') or contains(text(),'Specialist')])[1]")
    private WebElement specialistOption;

    @FindBy(xpath = "//input[contains(@placeholder,'Mobile') or contains(@placeholder,'phone')]")
    private WebElement mobileNumberField;

    @FindBy(xpath = "//*[contains(text(),'valid mobile') or contains(text(),'Invalid') or contains(text(),'10 digit')]")
    private WebElement invalidMobileMessage;

    @FindBy(xpath = "//div[@id='FaqSection']")
    private WebElement faqSection;

    private static final String FAQ_XPATH = "//div[starts-with(@data-testid,'faq_')]//h3";

    public void clickConsultNow() {
        consultNowButton.click();
    }

    public void enterSymptom(String symptom) {
        symptomField.clear();
        symptomField.sendKeys(symptom);
    }

    public void selectSpecialist() {
        specialistOption.click();
    }

    public void enterMobileNumber(String mobileNumber) {
        mobileNumberField.clear();
        mobileNumberField.sendKeys(mobileNumber);
    }

    public String getInvalidMobileMessage() {
        try {
            return invalidMobileMessage.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isFaqSectionDisplayed() {
        try {
            return faqSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getFaqCount() {
        try {
            return driver.findElements(By.xpath(FAQ_XPATH)).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<String> getAllFaqQuestions() {
        List<String> faqList = new ArrayList<>();
        List<WebElement> faqElements =
                driver.findElements(By.xpath(FAQ_XPATH));
        for (WebElement faqElement : faqElements) {
            try {
                String faqText = faqElement.getText().trim();
                if (!faqText.isEmpty()) {
                    faqList.add(faqText);
                }
            } catch (Exception ignored) {
                // Handles StaleElementReferenceException safely
            }
        }
        return faqList;
    }

    public List<String> getTopFiveFaqQuestions() {
        return getAllFaqQuestions()
                .stream()
                .distinct()
                .limit(5)
                .collect(Collectors.toList());
    }

    public void startConsultation(String symptom, String mobileNumber) {
        clickConsultNow();
        enterSymptom(symptom);
        selectSpecialist();
        enterMobileNumber(mobileNumber);
    }
}