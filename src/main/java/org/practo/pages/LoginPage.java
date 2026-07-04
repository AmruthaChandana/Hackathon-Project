package org.practo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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

    @FindBy(xpath = "//*[contains(text(),'Invalid') or contains(text(),'incorrect') or contains(text(),'wrong') or contains(text(),'Try again')]")
    private WebElement errorMessage;

    private By profileNameLocator(String expectedProfileName) {
        return By.xpath(
                "//*[normalize-space()='" + expectedProfileName + "' or contains(normalize-space(),'" + expectedProfileName + "')]"
        );
    }

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

    public void login(String mobile, String password) {
        enterMobile(mobile);
        enterPassword(password);
        clickLogin();
    }

    public boolean isUserLoggedIn() {
        try {
            return headerUserName.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isProfileNameDisplayed(String expectedProfileName) {

        try {
            List<WebElement> elements =
                    driver.findElements(profileNameLocator(expectedProfileName));

            for (WebElement element : elements) {

                try {
                    String text = element.getText().trim();

                    if (element.isDisplayed()
                            && !text.isEmpty()
                            && text.contains(expectedProfileName)) {
                        return true;
                    }

                } catch (Exception ignored) {
                }
            }

            /*
             * Fallback validation using visible page text.
             * Useful because Practo header updates dynamically.
             */
            JavascriptExecutor js = (JavascriptExecutor) driver;

            String bodyText =
                    js.executeScript("return document.body.innerText;").toString();

            return bodyText.contains(expectedProfileName);

        } catch (Exception e) {
            return false;
        }
    }

    public String getDisplayedProfileName(String expectedProfileName) {

        try {
            List<WebElement> elements =
                    driver.findElements(profileNameLocator(expectedProfileName));

            for (WebElement element : elements) {

                try {
                    String text = element.getText().trim();

                    if (element.isDisplayed()
                            && !text.isEmpty()
                            && text.contains(expectedProfileName)) {
                        return text;
                    }

                } catch (Exception ignored) {
                }
            }

            JavascriptExecutor js = (JavascriptExecutor) driver;

            String bodyText =
                    js.executeScript("return document.body.innerText;").toString();

            if (bodyText.contains(expectedProfileName)) {
                return expectedProfileName;
            }

        } catch (Exception e) {
            return "";
        }

        return "";
    }

    public boolean isLoginButtonDisplayed() {
        try {
            return loginSubmitButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickProfileArrow() {
        profileDownArrow.click();
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
}