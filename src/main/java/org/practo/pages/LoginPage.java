package org.practo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    WebDriver driver;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Login Elements
    @FindBy(xpath = "//a[text()='Login / Signup']")
    WebElement signInBtn;

    @FindBy(id = "username")
    WebElement mobileField;

    @FindBy(id = "password")
    WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement loginBtn;

    // Success indicator (after login)
    @FindBy(xpath = "//div[contains(@class,'nav-bar')]")
    WebElement homePageHeader;

    // Actions
    public void clickSignIn() {
        signInBtn.click();
    }

    public void enterMobile(String mobile) {
        mobileField.sendKeys(mobile);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginBtn.click();
    }

    // Validation
    public boolean isLoginSuccessful() {
        return homePageHeader.isDisplayed();
    }
}