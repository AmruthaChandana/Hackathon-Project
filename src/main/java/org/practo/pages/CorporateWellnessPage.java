package org.practo.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;
import java.util.List;

public class CorporateWellnessPage {
    private WebDriver driver;

    public CorporateWellnessPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Corporate form container
    @FindBy(xpath = "//*[@id='header']/div[2]/div | //div[contains(@class,'corporate-form')]")
    private WebElement corporateForm;

    // Corporate form fields
    @FindBy(xpath = "//input[@placeholder='Name' or contains(@placeholder,'Name')]")
    private WebElement nameField;

    @FindBy(xpath = "//input[@placeholder='Organization Name' or contains(@placeholder,'Organization')]")
    private WebElement organizationField;

    @FindBy(xpath = "//input[@placeholder='Contact Number' or contains(@placeholder,'Contact')]")
    private WebElement mobileField;

    @FindBy(xpath = "//input[@placeholder='Official Email ID' or contains(@placeholder,'Email')]")
    private WebElement emailField;

    @FindBy(xpath = "//*[contains(normalize-space(),'Organization Size')]")
    private WebElement organizationSizeDropdown;

    @FindBy(xpath = "//*[contains(normalize-space(),'Interested In')]")
    private WebElement interestedInDropdown;

    @FindBy(xpath = "//button[contains(normalize-space(),'Schedule a demo')]")
    private WebElement scheduleDemoButton;

    @FindBy(xpath = "//*[contains(normalize-space(),'valid') or contains(normalize-space(),'Invalid') or contains(normalize-space(),'required') or contains(normalize-space(),'Please')]")
    private WebElement validationMessage;

    @FindBy(xpath = "//*[self::div or self::li or self::span][normalize-space()!='']")
    private List<WebElement> dropdownOptions;

    // TC_020 corporate menu options
    @FindBy(xpath = "//*[contains(normalize-space(),'Our Services')]")
    private WebElement ourServices;

    @FindBy(xpath = "//*[contains(normalize-space(),'Practo Ecosystem')]")
    private WebElement practoEcosystem;

    @FindBy(xpath = "//*[contains(normalize-space(),'Product Capabilities')]")
    private WebElement productCapabilities;

    @FindBy(xpath = "//*[contains(normalize-space(),'Testimonials')]")
    private WebElement testimonials;

    @FindBy(xpath = "//*[contains(normalize-space(),'FAQs')]")
    private WebElement faqs;

    public boolean isCorporateFormDisplayed() {
        try {
            return WaitUtils.waitForVisible(driver, corporateForm).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForCorporateForm() {
        WaitUtils.waitForVisible(driver, corporateForm);
    }

    public void enterName(String name) {
        WaitUtils.waitForVisible(driver, nameField);
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void enterOrganization(String organization) {
        WaitUtils.waitForVisible(driver, organizationField);
        organizationField.clear();
        organizationField.sendKeys(organization);
    }

    public void enterMobile(String mobile) {
        WaitUtils.waitForVisible(driver, mobileField);
        mobileField.clear();
        mobileField.sendKeys(mobile);
    }

    public void enterEmail(String email) {
        WaitUtils.waitForVisible(driver, emailField);
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void selectOrganizationSize(String organizationSize) {
        WaitUtils.safeClick(driver, organizationSizeDropdown);
        selectDropdownOption(organizationSize);
    }

    public void selectInterestedIn(String interestedIn) {
        WaitUtils.safeClick(driver, interestedInDropdown);
        selectDropdownOption(interestedIn);
    }

    private void selectDropdownOption(String expectedOption) {
        boolean optionClicked = false;
        for (WebElement option : dropdownOptions) {
            try {
                String optionText = option.getText().trim();
                if (option.isDisplayed() && !optionText.isEmpty() && optionText.equalsIgnoreCase(expectedOption.trim())) {
                    option.click();
                    optionClicked = true;
                    break;
                }
            } catch (Exception ignored) {
            }
        }
        if (!optionClicked) {
            for (WebElement option : dropdownOptions) {
                try {
                    String optionText = option.getText().trim();
                    if (option.isDisplayed() && !optionText.isEmpty() && optionText.toLowerCase().contains(expectedOption.trim().toLowerCase())) {
                        option.click();
                        optionClicked = true;
                        break;
                    }
                } catch (Exception ignored) {
                }
            }
        }
        if (!optionClicked) {
            throw new RuntimeException("Dropdown option not found: " + expectedOption);
        }
    }

    public void fillCorporateWellnessForm(String name, String organization, String email, String mobile, String organizationSize, String interestedIn) {
        waitForCorporateForm();
        enterName(name);
        enterOrganization(organization);
        enterMobile(mobile);
        enterEmail(email);
        selectOrganizationSize(organizationSize);
        selectInterestedIn(interestedIn);
    }

    public boolean isSubmitButtonEnabled() {
        try {
            WaitUtils.waitForVisible(driver, scheduleDemoButton);
            String disabled = scheduleDemoButton.getAttribute("disabled");
            String ariaDisabled = scheduleDemoButton.getAttribute("aria-disabled");
            String classValue = scheduleDemoButton.getAttribute("class");
            System.out.println("Submit Button Text : " + scheduleDemoButton.getText().trim());
            System.out.println("disabled attribute : " + disabled);
            System.out.println("aria-disabled      : " + ariaDisabled);
            System.out.println("class attribute    : " + classValue);
            System.out.println("isEnabled()        : " + scheduleDemoButton.isEnabled());
            if (disabled != null) {
                return false;
            }
            if (ariaDisabled != null && ariaDisabled.equalsIgnoreCase("true")) {
                return false;
            }
            if (classValue != null && classValue.toLowerCase().contains("disabled")) {
                return false;
            }
            return scheduleDemoButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickScheduleDemoButton() {
        try {
            WaitUtils.safeClick(driver, scheduleDemoButton);
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", scheduleDemoButton);
        }
    }

    public String getSubmitButtonText() {
        try {
            return WaitUtils.waitForVisible(driver, scheduleDemoButton).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isValidationMessageDisplayed() {
        try {
            return WaitUtils.waitForVisible(driver, validationMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getValidationMessage() {
        try {
            return WaitUtils.waitForVisible(driver, validationMessage).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    private void clickMenuOption(WebElement element) {
        try {
            WaitUtils.waitForVisible(driver, element);
            WaitUtils.scrollToElement(driver, element);
            WaitUtils.safeClick(driver, element);
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }
    }

    public void clickOurServices() {
        clickMenuOption(ourServices);
    }

    public void clickPractoEcosystem() {
        clickMenuOption(practoEcosystem);
    }

    public void clickProductCapabilities() {
        clickMenuOption(productCapabilities);
    }

    public void clickTestimonials() {
        clickMenuOption(testimonials);
    }

    public void clickFAQs() {
        clickMenuOption(faqs);
    }

    public void scrollDown() {
        WaitUtils.scrollDown(driver);
    }

    public void scrollToTop() {
        WaitUtils.scrollToTop(driver);
    }

    public void scrollToForm() {
        try {
            WaitUtils.waitForVisible(driver, corporateForm);
            WaitUtils.scrollToElement(driver, corporateForm);
        } catch (Exception ignored) {
        }
    }

    public void scrollToSubmitButton() {
        try {
            WaitUtils.waitForVisible(driver, scheduleDemoButton);
            WaitUtils.scrollToElement(driver, scheduleDemoButton);
        } catch (Exception ignored) {
        }
    }
}