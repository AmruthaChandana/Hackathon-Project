package org.practo.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger logger =
            LogManager.getLogger(LoginPage.class);

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

    // Login Form Elements

    @FindBy(xpath = "//input[contains(@placeholder,'Mobile') or contains(@name,'mobile')]")
    private WebElement mobileNumberField;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(text(),'Login') or contains(text(),'Continue')]")
    private WebElement loginSubmitButton;

    // Profile Elements

    @FindBy(xpath = "//span[contains(@class,'user_info_top')]")
    private WebElement headerUserName;

    @FindBy(xpath = "//span[contains(@class,'user_info_top')]/following-sibling::span[contains(@class,'downarrow')]")
    private WebElement profileDownArrow;

    @FindBy(xpath = "//div[contains(@class,'nav-dropdown')]")
    private WebElement profileDropdownPanel;

    // Error Message

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
                    element).click();

        } catch (Exception e) {

            logger.warn(
                    "Regular click failed. Using JavaScript click.");

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

            logger.debug(
                    "Element not displayed.");

            return false;
        }
    }

    // ===========================================
    // Login Methods
    // ===========================================

    public void enterMobile(
            String mobile) {

        logger.info(
                "Entering mobile number");

        waitForVisible(
                mobileNumberField);

        mobileNumberField.clear();

        mobileNumberField.sendKeys(
                mobile);
    }

    public void enterPassword(
            String password) {

        logger.info(
                "Entering password");

        waitForVisible(
                passwordField);

        passwordField.clear();

        passwordField.sendKeys(
                password);
    }

    public void clickLogin() {

        logger.info(
                "Clicking Login button");

        safeClick(
                loginSubmitButton);
    }

    public void login(
            String mobile,
            String password) {

        enterMobile(
                mobile);

        enterPassword(
                password);

        clickLogin();
    }

    // ===========================================
    // Verification Methods
    // ===========================================

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

            logger.error(
                    "Error while validating profile name.",
                    e);

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

                        logger.info(
                                "Profile name found: {}",
                                text);

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

                logger.info(
                        "Profile name found in page body: {}",
                        expectedProfileName);

                return expectedProfileName;
            }

        } catch (Exception e) {

            logger.error(
                    "Exception while retrieving profile name.",
                    e);

            return "PROFILE_NAME_NOT_FOUND";
        }

        logger.warn(
                "Profile name not found: {}",
                expectedProfileName);

        return "PROFILE_NAME_NOT_FOUND";
    }

    public String getErrorMessage() {

        try {

            if (isElementDisplayed(
                    errorMessage)) {

                return errorMessage
                        .getText()
                        .trim();
            }

        } catch (Exception e) {

            logger.error(
                    "Unable to read login error message.",
                    e);
        }

        return "ERROR_MESSAGE_NOT_FOUND";
    }
}