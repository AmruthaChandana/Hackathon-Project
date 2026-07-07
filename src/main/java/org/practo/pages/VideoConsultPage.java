package org.practo.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(VideoConsultPage.class);
    private WebDriver driver;
    private WebDriverWait wait;

    public VideoConsultPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        PageFactory.initElements(driver, this);
    }

    // TC_014
    @FindBy(xpath = "//a[contains(@class,'cta')]")
    private WebElement consultNowButton;

    @FindBy(name = "detailedDescription")
    private WebElement symptomField;

    @FindBy(xpath = "//label[contains(@class,'tag-label')]")
    private WebElement firstSpecialistOption;

    @FindBy(id = "mobile")
    private WebElement mobileNumberField;

    @FindBy(xpath = "//button[contains(text(),'Continue')]")
    private WebElement continueButton;

    @FindBy(id = "login-iframe-form")
    private WebElement loginIframe;

    @FindBy(id = "otpSentMsg")
    private WebElement invalidMobileMessageInIframe;

    @FindBy(id = "close")
    private WebElement closeOtpPopupButton;

    // TC_015
    @FindBy(id = "FaqSection")
    private WebElement faqSection;

    @FindBy(xpath = "//div[@id='FaqSection']//h2[contains(@class,'faq-section-heading')]")
    private WebElement faqHeading;

    @FindBy(xpath = "//div[@id='FaqSection']//h3[contains(@class,'accordion-text')]")
    private List<WebElement> faqElements;

    // Helper Methods
    private WebElement waitForVisible(WebElement element) {
        return wait.until(
                ExpectedConditions.visibilityOf(element)
        );
    }

    private WebElement waitForClickable(WebElement element) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(element)
        );
    }

    private void safeClick(WebElement element) {
        try {
            waitForClickable(element).click();
        } catch (Exception e) {
            logger.warn("Regular click failed. Using JavaScript click.");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }
    }

    private void clickUsingJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    private boolean isElementDisplayed(WebElement element) {
        try {
            return waitForVisible(element).isDisplayed();
        } catch (Exception e) {
            logger.debug("Element not displayed.");
            return false;
        }
    }

    private void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    // TC_014 Methods
    public boolean isConsultNowDisplayed() {
        return isElementDisplayed(consultNowButton);
    }

    public void clickConsultNow() {
        logger.info("Clicking Consult Now");
        safeClick(consultNowButton);
    }

    public void clickConsultNowUsingJS() {
        logger.info("Clicking Consult Now using JavaScript");
        clickUsingJS(consultNowButton);
    }

    public boolean isSymptomFieldDisplayed() {
        return isElementDisplayed(symptomField);
    }

    public void enterSymptom(String symptom) {
        logger.info("Entering symptom: {}", symptom);
        waitForVisible(symptomField);
        symptomField.clear();
        symptomField.sendKeys(symptom);
    }

    public boolean isFirstSpecialistDisplayed() {
        return isElementDisplayed(firstSpecialistOption);
    }

    public void selectFirstSpecialist() {
        logger.info("Selecting first specialist");
        safeClick(firstSpecialistOption);
    }

    public void selectFirstSpecialistUsingJS() {
        clickUsingJS(firstSpecialistOption);
    }

    public boolean isMobileNumberFieldDisplayed() {
        return isElementDisplayed(mobileNumberField);
    }

    public void enterMobileNumber(String mobileNumber) {
        logger.info("Entering mobile number");
        waitForVisible(mobileNumberField);
        mobileNumberField.clear();
        mobileNumberField.sendKeys(mobileNumber);
    }

    public boolean isContinueButtonDisplayed() {
        return isElementDisplayed(continueButton);
    }

    public void clickContinue() {
        logger.info("Clicking Continue button");
        safeClick(continueButton);
    }

    public void clickContinueUsingJS() {
        clickUsingJS(continueButton);
    }

    public WebElement getLoginIframe() {
        return loginIframe;
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public boolean isInvalidMobileMessageDisplayedInIframe() {
        return isElementDisplayed(invalidMobileMessageInIframe);
    }

    public String getInvalidMobileMessageInIframe() {
        try {
            return waitForVisible(invalidMobileMessageInIframe).getText().trim();
        } catch (Exception e) {
            logger.error("Unable to retrieve invalid mobile validation message.", e);
            return "VALIDATION_MESSAGE_NOT_FOUND";
        }
    }

    public void closeOtpPopup() {
        logger.info("Closing OTP popup");
        safeClick(closeOtpPopupButton);
    }

    public void closeOtpPopupUsingJS() {
        clickUsingJS(closeOtpPopupButton);
    }

    // FAQ Methods
    public boolean isFaqSectionPresent() {
        try {
            return faqSection.isDisplayed();
        } catch (Exception e) {
            logger.error("Unable to verify FAQ section presence.", e);
            return false;
        }
    }

    public boolean isFaqSectionDisplayed() {
        return isElementDisplayed(faqHeading);
    }

    public void scrollToFaqSection() {
        try {
            scrollToElement(faqHeading);
            logger.info("Scrolled to FAQ section");
        } catch (Exception e) {
            logger.error("Unable to scroll to FAQ section.", e);
        }
    }

    public int getFaqCount() {
        try {
            return faqElements.size();
        } catch (Exception e) {
            logger.error("Unable to retrieve FAQ count.", e);
            return 0;
        }
    }

    public List<String> getAllFaqQuestions() {
        List<String> faqList = new ArrayList<>();
        for (WebElement faqElement : faqElements) {
            try {
                String faqText = faqElement.getText().trim();
                if (!faqText.isEmpty()) {
                    faqList.add(faqText);
                }
            } catch (Exception e) {
                logger.debug("Unable to process FAQ element.", e);
            }
        }
        return faqList.stream().distinct().collect(Collectors.toList());
    }

    public List<String> getTopFiveFaqQuestions() {
        return getAllFaqQuestions()
                .stream()
                .limit(5)
                .collect(Collectors.toList());
    }
}