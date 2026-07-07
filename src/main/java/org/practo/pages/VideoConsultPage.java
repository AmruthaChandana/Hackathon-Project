package org.practo.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VideoConsultPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public VideoConsultPage(WebDriver driver) {

        this.driver = driver;

        this.wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(40));

        PageFactory.initElements(
                driver,
                this);
    }

    // TC_014

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

    // TC_015

    @FindBy(xpath = "//*[@id='FaqSection' or @data-testid='faq-section' or contains(@class,'faq-section')]")
    private List<WebElement> faqSections;

    @FindBy(xpath = "//*[@id='FaqSection']//*[self::h3 or self::div or self::p or self::span][contains(normalize-space(),'?')] | //*[@data-testid='faq-section']//*[self::h3 or self::div or self::p or self::span][contains(normalize-space(),'?')] | //*[contains(@class,'faq-section')]//*[self::h3 or self::div or self::p or self::span][contains(normalize-space(),'?')]")
    private List<WebElement> faqElements;

    // =====================================================
    // Helper Methods
    // =====================================================

    private WebElement waitForVisible(
            WebElement element) {

        return wait.until(
                ExpectedConditions.visibilityOf(
                        element));
    }

    private WebElement waitForClickable(
            WebElement element) {

        return wait.until(
                ExpectedConditions.elementToBeClickable(
                        element));
    }

    private void safeClick(
            WebElement element) {

        try {

            waitForClickable(
                    element).click();

        } catch (Exception e) {

            JavascriptExecutor js =
                    (JavascriptExecutor) driver;

            js.executeScript(
                    "arguments[0].click();",
                    element);
        }
    }

    private void clickUsingJS(
            WebElement element) {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                element);
    }

    private boolean isElementDisplayed(
            WebElement element) {

        try {

            return waitForVisible(
                    element).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    private void scrollToElement(
            WebElement element) {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element);
    }

    // =====================================================
    // TC_014 Methods
    // =====================================================

    public boolean isConsultNowDisplayed() {

        return isElementDisplayed(
                consultNowButton);
    }

    public void clickConsultNow() {

        safeClick(
                consultNowButton);
    }

    public void clickConsultNowUsingJS() {

        clickUsingJS(
                consultNowButton);
    }

    public boolean isSymptomFieldDisplayed() {

        return isElementDisplayed(
                symptomField);
    }

    public void enterSymptom(
            String symptom) {

        waitForVisible(
                symptomField);

        symptomField.clear();

        symptomField.sendKeys(
                symptom);
    }

    public boolean isFirstSpecialistDisplayed() {

        return isElementDisplayed(
                firstSpecialistOption);
    }

    public void selectFirstSpecialist() {

        safeClick(
                firstSpecialistOption);
    }

    public void selectFirstSpecialistUsingJS() {

        clickUsingJS(
                firstSpecialistOption);
    }

    public boolean isMobileNumberFieldDisplayed() {

        return isElementDisplayed(
                mobileNumberField);
    }

    public void enterMobileNumber(
            String mobileNumber) {

        waitForVisible(
                mobileNumberField);

        mobileNumberField.clear();

        mobileNumberField.sendKeys(
                mobileNumber);
    }

    public boolean isContinueButtonDisplayed() {

        return isElementDisplayed(
                continueButton);
    }

    public void clickContinue() {

        safeClick(
                continueButton);
    }

    public void clickContinueUsingJS() {

        clickUsingJS(
                continueButton);
    }

    public WebElement getLoginIframe() {

        return loginIframe;
    }

    public void switchToDefaultContent() {

        driver.switchTo()
                .defaultContent();
    }

    public boolean isInvalidMobileMessageDisplayedInIframe() {

        return isElementDisplayed(
                invalidMobileMessageInIframe);
    }

    public String getInvalidMobileMessageInIframe() {

        try {

            return waitForVisible(
                    invalidMobileMessageInIframe)
                    .getText()
                    .trim();

        } catch (Exception e) {

            return "";
        }
    }

    public void closeOtpPopup() {

        safeClick(
                closeOtpPopupButton);
    }

    public void closeOtpPopupUsingJS() {

        clickUsingJS(
                closeOtpPopupButton);
    }

    public boolean isBackToVideoConsultPageLinkDisplayed() {

        return isElementDisplayed(
                backToVideoConsultPageLink);
    }

    public void clickBackToVideoConsultPage() {

        safeClick(
                backToVideoConsultPageLink);
    }

    public void clickBackToVideoConsultPageUsingJS() {

        clickUsingJS(
                backToVideoConsultPageLink);
    }

    // =====================================================
    // TC_015 FAQ Methods
    // =====================================================

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

                scrollToElement(
                        faqSections.get(0));
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

        List<String> faqList =
                new ArrayList<>();

        for (WebElement faqElement : faqElements) {

            try {

                String faqText =
                        faqElement.getText()
                                .trim();

                if (!faqText.isEmpty()
                        && faqText.contains("?")
                        && faqText.length() <= 180) {

                    faqList.add(
                            faqText);
                }

            } catch (Exception ignored) {
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
}