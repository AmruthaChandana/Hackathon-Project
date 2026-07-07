package org.practo.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CorporateWellnessPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CorporateWellnessPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id='header']/div[2]/div | //div[contains(@class,'corporate-form')]")
    private WebElement corporateForm;

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
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }
    }

    public boolean isCorporateFormDisplayed() {
        try {
            return waitForVisible(corporateForm).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForCorporateForm() {
        waitForVisible(corporateForm);
    }

    public void enterName(String name) {
        waitForVisible(nameField);
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void enterOrganization(String organization) {
        waitForVisible(organizationField);
        organizationField.clear();
        organizationField.sendKeys(organization);
    }

    public void enterMobile(String mobile) {
        waitForVisible(mobileField);
        mobileField.clear();
        mobileField.sendKeys(mobile);
    }

    public void enterEmail(String email) {
        waitForVisible(emailField);
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void selectOrganizationSize(String organizationSize) {
        safeClick(organizationSizeDropdown);
        selectDropdownOption(organizationSize);
    }

    public void selectInterestedIn(String interestedIn) {
        safeClick(interestedInDropdown);
        selectDropdownOption(interestedIn);
    }

    private void selectDropdownOption(String expectedOption) {
        boolean optionClicked = false;
        for (WebElement option : dropdownOptions) {
            try {
                String optionText = option.getText().trim();
                if (option.isDisplayed()
                        && !optionText.isEmpty()
                        && optionText.equalsIgnoreCase(expectedOption.trim())) {
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
                    if (option.isDisplayed()
                            && !optionText.isEmpty()
                            && optionText.toLowerCase().contains(expectedOption.trim().toLowerCase())) {
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

    public void fillCorporateWellnessForm(
            String name,
            String organization,
            String email,
            String mobile,
            String organizationSize,
            String interestedIn) {
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
            waitForVisible(scheduleDemoButton);
            String disabled = scheduleDemoButton.getAttribute("disabled");
            String ariaDisabled = scheduleDemoButton.getAttribute("aria-disabled");
            String classValue = scheduleDemoButton.getAttribute("class");

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

    private void clickMenuOption(WebElement element) {
        try {
            scrollToElement(element);
            safeClick(element);
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
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,700)");
    }

    public void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,0)");
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );
    }

    public void scrollToForm() {
        try {
            waitForVisible(corporateForm);
            scrollToElement(corporateForm);
        } catch (Exception ignored) {
        }
    }
}