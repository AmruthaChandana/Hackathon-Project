package org.practo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CorporateWellnessPage {
    WebDriver driver;
    public CorporateWellnessPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//input[contains(@placeholder,'Name')]")
    private WebElement nameField;

    @FindBy(xpath = "//input[contains(@placeholder,'Organization') or contains(@placeholder,'Company')]")
    private WebElement organizationField;

    @FindBy(xpath = "//input[contains(@placeholder,'Email')]")
    private WebElement emailField;

    @FindBy(xpath = "//input[contains(@placeholder,'Mobile') or contains(@placeholder,'Phone')]")
    private WebElement mobileField;

    @FindBy(xpath = "//button[contains(text(),'Schedule') or contains(text(),'Submit')]")
    private WebElement submitButton;

    @FindBy(xpath = "//*[contains(text(),'valid') or contains(text(),'Invalid') or contains(text(),'required')]")
    private WebElement validationMessage;

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

    public void fillCorporateWellnessForm(
            String name,
            String organization,
            String email,
            String mobile) {
        enterName(name);
        enterOrganization(organization);
        enterEmail(email);
        enterMobile(mobile);
    }
}