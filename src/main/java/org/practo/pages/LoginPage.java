package org.practo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {

        this.driver = driver;

        this.wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(40));

        PageFactory.initElements(
                driver,
                this);
    }

    // Login form elements

    @FindBy(xpath = "//input[contains(@placeholder,'Mobile') or contains(@name,'mobile')]")
    private WebElement mobileNumberField;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(text(),'Login') or contains(text(),'Continue')]")
    private WebElement loginSubmitButton;

    // Profile elements

    @FindBy(xpath = "//span[contains(@class,'user_info_top')]")
    private WebElement headerUserName;

    @FindBy(xpath = "//span[contains(@class,'user_info_top')]/following-sibling::span[contains(@class,'downarrow')]")
    private WebElement profileDownArrow;

    @FindBy(xpath = "//div[contains(@class,'nav-dropdown')]")
    private WebElement profileDropdownPanel;

    // Error message

    @FindBy(xpath = "//*[contains(text(),'Invalid') or contains(text(),'incorrect') or contains(text(),'wrong') or contains(text(),'Try again')]")
    private WebElement errorMessage;

    private By profileNameLocator(
            String expectedProfileName) {

        return By.xpath(
                "//*[normalize-space()='"
                        + expectedProfileName
                        + "' or contains(normalize-space(),'"
                        + expectedProfileName
                        + "')]");
    }


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
                    element)
                    .click();

        } catch (Exception e) {

            JavascriptExecutor js =
                    (JavascriptExecutor) driver;

            js.executeScript(
                    "arguments[0].click();",
                    element);
        }
    }

    private boolean isElementDisplayed(
            WebElement element) {

        try {

            return waitForVisible(
                    element)
                    .isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    // =====================================================
    // Actions
    // =====================================================

    public void enterMobile(
            String mobile) {

        waitForVisible(
                mobileNumberField);

        mobileNumberField.clear();

        mobileNumberField.sendKeys(
                mobile);
    }

    public void enterPassword(
            String password) {

        waitForVisible(
                passwordField);

        passwordField.clear();

        passwordField.sendKeys(
                password);
    }

    public void clickLogin() {

        safeClick(
                loginSubmitButton);
    }

    public void login(
            String mobile,
            String password) {

        enterMobile(mobile);

        enterPassword(password);

        clickLogin();
    }

    public boolean isUserLoggedIn() {

        return isElementDisplayed(
                headerUserName);
    }

    public boolean isProfileNameDisplayed(
            String expectedProfileName) {

        try {

            List<WebElement> elements =
                    driver.findElements(
                            profileNameLocator(
                                    expectedProfileName));

            for (WebElement element : elements) {

                try {

                    String text =
                            element.getText()
                                    .trim();

                    if (element.isDisplayed()
                            && !text.isEmpty()
                            && text.contains(
                            expectedProfileName)) {

                        return true;
                    }

                } catch (Exception ignored) {
                }
            }

            JavascriptExecutor js =
                    (JavascriptExecutor) driver;

            String bodyText =
                    js.executeScript(
                                    "return document.body.innerText;")
                            .toString();

            return bodyText.contains(
                    expectedProfileName);

        } catch (Exception e) {

            return false;
        }
    }

    public String getDisplayedProfileName(
            String expectedProfileName) {

        try {

            List<WebElement> elements =
                    driver.findElements(
                            profileNameLocator(
                                    expectedProfileName));

            for (WebElement element : elements) {

                try {

                    String text =
                            element.getText()
                                    .trim();

                    if (element.isDisplayed()
                            && !text.isEmpty()
                            && text.contains(
                            expectedProfileName)) {

                        return text;
                    }

                } catch (Exception ignored) {
                }
            }

            JavascriptExecutor js =
                    (JavascriptExecutor) driver;

            String bodyText =
                    js.executeScript(
                                    "return document.body.innerText;")
                            .toString();

            if (bodyText.contains(
                    expectedProfileName)) {

                return expectedProfileName;
            }

        } catch (Exception e) {

            return "";
        }

        return "";
    }

}