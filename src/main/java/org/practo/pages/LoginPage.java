package org.practo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[contains(@placeholder,'Mobile') or contains(@name,'mobile')]")
    private WebElement mobileNumberField;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(text(),'Login') or contains(text(),'Continue')]")
    private WebElement loginSubmitButton;

    @FindBy(xpath = "//span[contains(@class,'user_info_top')]")
    private WebElement headerUserName;

    @FindBy(xpath = "//span[contains(@class,'user_info_top')]/following-sibling::span[contains(@class,'downarrow')]")
    private WebElement profileDownArrow;

    @FindBy(xpath = "//div[contains(@class,'nav-dropdown')]")
    private WebElement profileDropdownPanel;

    public void enterMobile(String mobile) {
        mobileNumberField.clear();
        mobileNumberField.sendKeys(mobile);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginSubmitButton.click();
    }

    public void clickProfileArrow() {
        profileDownArrow.click();
    }

    public boolean isUserLoggedIn() {
        try {
            return headerUserName.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isProfileDropdownDisplayed() {
        try {
            return profileDropdownPanel.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoggedInUserName() {
        try {
            return headerUserName.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public void login(String mobile, String password) {
        enterMobile(mobile);
        enterPassword(password);
        clickLogin();
    }
}