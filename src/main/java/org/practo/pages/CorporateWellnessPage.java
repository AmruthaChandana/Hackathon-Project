package org.practo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class CorporateWellnessPage {

    WebDriver driver;
    private WebDriverWait wait;

    public CorporateWellnessPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[contains(@placeholder,'Name')]")
    private WebElement nameField;

    @FindBy(xpath = "//input[contains(@placeholder,'Organization') or contains(@placeholder,'Company')]")
    private WebElement organizationField;

    @FindBy(xpath = "//input[contains(@placeholder,'Email')]")
    private WebElement emailField;

    @FindBy(id = "contactNumber")
    private WebElement mobileField;

    @FindBy(id = "organizationSize")
    private WebElement organizationSizeDropdown;

    @FindBy(id = "interestedIn")
    private WebElement interestedInDropdown;

    @FindBy(xpath = "//button[contains(text(),'Schedule') or contains(text(),'Submit')]")
    private WebElement submitButton;

    @FindBy(xpath = "//*[contains(text(),'valid') or contains(text(),'Invalid') or contains(text(),'required')]")
    private WebElement validationMessage;

    @FindBy(xpath = "//li[contains(text(),'Our Services')]")
    private WebElement ourServices;

    @FindBy(xpath = "//li[contains(text(),'Practo Ecosystem')]")
    private WebElement practoEcosystem;

    @FindBy(xpath = "//li[contains(text(),'Product Capabilities')]")
    private WebElement productCapabilities;

    @FindBy(xpath = "//li[contains(text(),'Testimonials')]")
    private WebElement testimonials;

    @FindBy(xpath = "//li[contains(text(),'FAQs')]")
    private WebElement faqs;

    // Actions

    public void enterName(String name) {
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void enterOrganization(String organization) {
        organizationField.clear();
        organizationField.sendKeys(organization);
    }

    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterMobile(String mobile) {
        mobileField.clear();
        mobileField.sendKeys(mobile);
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public String getValidationMessage() {
        return validationMessage.getText();
    }

    public void selectOrganizationSize(String organizationSize) {

        Select select =
                new Select(organizationSizeDropdown);

        select.selectByVisibleText(organizationSize);
    }

    public void selectInterestedIn(String interestedIn) {

        Select select =
                new Select(interestedInDropdown);

        for (WebElement option : select.getOptions()) {

            if (option.getText().trim()
                    .equalsIgnoreCase(interestedIn.trim())) {

                option.click();
                break;
            }
        }
    }

    public void fillCorporateWellnessForm(
            String name,
            String organization,
            String email,
            String mobile,
            String organizationSize,
            String interestedIn) {

        enterName(name);
        enterOrganization(organization);
        enterEmail(email);
        enterMobile(mobile);

        selectOrganizationSize(organizationSize);
        selectInterestedIn(interestedIn);
    }

    public boolean isSubmitButtonEnabled() {
        return submitButton.isEnabled();
    }

    public void scrollDown() {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "window.scrollBy(0,500)");
    }

    public void clickOurServices() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        ourServices));

        ourServices.click();
    }

    public void clickPractoEcosystem() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        practoEcosystem));

        practoEcosystem.click();
    }

    public void clickProductCapabilities() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        productCapabilities));

        productCapabilities.click();
    }

    public void clickTestimonials() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        testimonials));

        testimonials.click();
    }

    public void clickFAQs() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        faqs));

        faqs.click();
    }
}