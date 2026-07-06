package org.practo.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VideoConsultPage {
    private WebDriver driver;

    public VideoConsultPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // TC_014 - Invalid mobile consultation flow elements
    @FindBy(xpath = "//*[@id='FirstFold']/div/section/div[1]/a")
    private WebElement consultNowButton;

    @FindBy(xpath = "//*[@id='detailed-description']")
    private WebElement symptomField;

    @FindBy(xpath = "//*[@id='container']/div/div/div/section/section/div/div/div/div/form/problem-form/div/div/div[2]/div/div/div[1]/label")
    private WebElement firstSpecialistOption;

    @FindBy(xpath = "//input[contains(@placeholder,'Mobile') or contains(@placeholder,'mobile') or contains(@placeholder,'phone') or @type='tel']")
    private WebElement mobileNumberField;

    @FindBy(xpath = "//*[@id='container']/div/div/div/section/section/div/div/div/div/form/div/div/div[2]/button")
    private WebElement continueButton;

    @FindBy(xpath = "//*[@id='login-iframe-form']")
    private WebElement loginIframe;

    @FindBy(xpath = "//*[contains(normalize-space(),'Not a valid mobile number')]")
    private WebElement invalidMobileMessageInIframe;

    @FindBy(xpath = "//*[@id='close']/span")
    private WebElement closeOtpPopupButton;

    @FindBy(xpath = "//*[@id='new-consultation-top-element']/div/div/a")
    private WebElement backToVideoConsultPageLink;

    // TC_015 - FAQ elements
    @FindBy(xpath = "//*[@id='FaqSection' or @data-testid='faq-section' or contains(@class,'faq-section')]")
    private List<WebElement> faqSections;

    @FindBy(xpath = "//*[@id='FaqSection']//*[self::h3 or self::div or self::p or self::span][contains(normalize-space(),'?')] | //*[@data-testid='faq-section']//*[self::h3 or self::div or self::p or self::span][contains(normalize-space(),'?')] | //*[contains(@class,'faq-section')]//*[self::h3 or self::div or self::p or self::span][contains(normalize-space(),'?')]")
    private List<WebElement> faqElements;

    public boolean isConsultNowDisplayed() {
        return WaitUtils.isElementDisplayed(driver, consultNowButton);
    }

    public void clickConsultNow() {
        WaitUtils.safeClick(driver, consultNowButton);
    }

    public void clickConsultNowUsingJS() {
        WaitUtils.clickUsingJS(driver, consultNowButton);
    }

    public boolean isSymptomFieldDisplayed() {
        return WaitUtils.isElementDisplayed(driver, symptomField);
    }

    public void enterSymptom(String symptom) {
        WaitUtils.waitForVisible(driver, symptomField);
        symptomField.clear();
        symptomField.sendKeys(symptom);
    }

    public boolean isFirstSpecialistDisplayed() {
        return WaitUtils.isElementDisplayed(driver, firstSpecialistOption);
    }

    public void selectFirstSpecialist() {
        WaitUtils.safeClick(driver, firstSpecialistOption);
    }

    public void selectFirstSpecialistUsingJS() {
        WaitUtils.clickUsingJS(driver, firstSpecialistOption);
    }

    public boolean isMobileNumberFieldDisplayed() {
        return WaitUtils.isElementDisplayed(driver, mobileNumberField);
    }

    public void enterMobileNumber(String mobileNumber) {
        WaitUtils.waitForVisible(driver, mobileNumberField);
        mobileNumberField.clear();
        mobileNumberField.sendKeys(mobileNumber);
    }

    public boolean isContinueButtonDisplayed() {
        return WaitUtils.isElementDisplayed(driver, continueButton);
    }

    public void clickContinue() {
        WaitUtils.safeClick(driver, continueButton);
    }

    public void clickContinueUsingJS() {
        WaitUtils.clickUsingJS(driver, continueButton);
    }

    public WebElement getLoginIframe() {
        return loginIframe;
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public boolean isInvalidMobileMessageDisplayedInIframe() {
        return WaitUtils.isElementDisplayed(driver, invalidMobileMessageInIframe);
    }

    public String getInvalidMobileMessageInIframe() {
        try {
            return WaitUtils.waitForVisible(driver, invalidMobileMessageInIframe).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public void closeOtpPopup() {
        WaitUtils.safeClick(driver, closeOtpPopupButton);
    }

    public void closeOtpPopupUsingJS() {
        WaitUtils.clickUsingJS(driver, closeOtpPopupButton);
    }

    public boolean isBackToVideoConsultPageLinkDisplayed() {
        return WaitUtils.isElementDisplayed(driver, backToVideoConsultPageLink);
    }

    public void clickBackToVideoConsultPage() {
        WaitUtils.safeClick(driver, backToVideoConsultPageLink);
    }

    public void clickBackToVideoConsultPageUsingJS() {
        WaitUtils.clickUsingJS(driver, backToVideoConsultPageLink);
    }

    public boolean isFaqSectionPresent() {
        try {
            return !faqSections.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFaqSectionDisplayed() {
        try {
            for (WebElement faqSection : faqSections) {
                try {
                    if (faqSection.isDisplayed()) {
                        return true;
                    }
                } catch (Exception ignored) {
                }
            }
            return getFaqCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void scrollToFaqSection() {
        try {
            if (!faqSections.isEmpty()) {
                WaitUtils.scrollToElement(driver, faqSections.get(0));
            }
        } catch (Exception ignored) {
        }
    }

    public int getFaqCount() {
        try {
            return faqElements.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<String> getAllFaqQuestions() {
        List<String> faqList = new ArrayList<>();
        for (WebElement faqElement : faqElements) {
            try {
                String faqText = faqElement.getText().trim();
                if (!faqText.isEmpty() && faqText.contains("?") && faqText.length() <= 180) {
                    faqList.add(faqText);
                }
            } catch (Exception ignored) {
            }
        }
        return faqList.stream().distinct().collect(Collectors.toList());
    }

    public List<String> getTopFiveFaqQuestions() {
        return getAllFaqQuestions().stream().limit(5).collect(Collectors.toList());
    }
}