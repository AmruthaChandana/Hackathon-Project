package org.practo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    // =====================================================
    // TC_018 - Video Consult Invalid Mobile Flow Elements
    // =====================================================

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

    // =====================================================
    // Existing TC_016 - FAQ Dynamic Locators
    // Do not remove these, TC_016 depends on these methods.
    // =====================================================

    private static final String FAQ_SECTION_XPATH =
            "//*[@id='FaqSection' or @data-testid='faq-section' or contains(@class,'faq-section')]";

    private static final String FAQ_XPATH =
            "//*[@id='FaqSection']//*[self::h3 or self::div or self::p or self::span][contains(normalize-space(),'?')] " +
                    "| //*[@data-testid='faq-section']//*[self::h3 or self::div or self::p or self::span][contains(normalize-space(),'?')] " +
                    "| //*[contains(@class,'faq-section')]//*[self::h3 or self::div or self::p or self::span][contains(normalize-space(),'?')]";

    // =====================================================
    // TC_018 - Consultation Flow Actions
    // =====================================================

    public boolean isConsultNowDisplayed() {
        try {
            return consultNowButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickConsultNow() {
        consultNowButton.click();
    }

    public void clickConsultNowUsingJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", consultNowButton);
    }

    public boolean isSymptomFieldDisplayed() {
        try {
            return symptomField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterSymptom(String symptom) {
        symptomField.clear();
        symptomField.sendKeys(symptom);
    }

    public boolean isFirstSpecialistDisplayed() {
        try {
            return firstSpecialistOption.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectFirstSpecialist() {
        firstSpecialistOption.click();
    }

    public void selectFirstSpecialistUsingJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", firstSpecialistOption);
    }

    public boolean isMobileNumberFieldDisplayed() {
        try {
            return mobileNumberField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterMobileNumber(String mobileNumber) {
        mobileNumberField.clear();
        mobileNumberField.sendKeys(mobileNumber);
    }

    public boolean isContinueButtonDisplayed() {
        try {
            return continueButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickContinue() {
        continueButton.click();
    }

    public void clickContinueUsingJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", continueButton);
    }

    // =====================================================
    // TC_018 - Iframe / Invalid Mobile Popup Methods
    // =====================================================

    public WebElement getLoginIframe() {
        return loginIframe;
    }

    public void switchToLoginIframe() {
        driver.switchTo().frame(loginIframe);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public boolean isInvalidMobileMessageDisplayedInIframe() {
        try {
            return invalidMobileMessageInIframe.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getInvalidMobileMessageInIframe() {
        try {
            return invalidMobileMessageInIframe.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public void closeOtpPopup() {
        closeOtpPopupButton.click();
    }

    public void closeOtpPopupUsingJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", closeOtpPopupButton);
    }

    public boolean isBackToVideoConsultPageLinkDisplayed() {
        try {
            return backToVideoConsultPageLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickBackToVideoConsultPage() {
        backToVideoConsultPageLink.click();
    }

    public void clickBackToVideoConsultPageUsingJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", backToVideoConsultPageLink);
    }

    // =====================================================
    // Existing TC_016 - FAQ Methods
    // Keep these exactly so TC_016 compiles and runs.
    // =====================================================

    public boolean isFaqSectionPresent() {
        try {
            return driver.findElements(By.xpath(FAQ_SECTION_XPATH)).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFaqSectionDisplayed() {
        try {
            List<WebElement> faqSections =
                    driver.findElements(By.xpath(FAQ_SECTION_XPATH));

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
            WebElement faqSection =
                    driver.findElement(By.xpath(FAQ_SECTION_XPATH));

            JavascriptExecutor js = (JavascriptExecutor) driver;

            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    faqSection
            );

        } catch (Exception ignored) {
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

                if (!faqText.isEmpty()
                        && faqText.contains("?")
                        && faqText.length() <= 180) {

                    faqList.add(faqText);
                }

            } catch (Exception ignored) {
                // Handles stale element safely
            }
        }

        return faqList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getTopFiveFaqQuestions() {
        return getAllFaqQuestions()
                .stream()
                .limit(5)
                .collect(Collectors.toList());
    }

    // =====================================================
    // Optional Business Method
    // =====================================================

    public void startConsultation(String symptom, String mobileNumber) {
        clickConsultNow();
        enterSymptom(symptom);
        selectFirstSpecialist();
        enterMobileNumber(mobileNumber);
    }
}