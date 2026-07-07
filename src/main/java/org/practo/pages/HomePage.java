package org.practo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage() {
    }

    public HomePage(WebDriver driver) {

        this.driver = driver;

        this.wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(40));

        PageFactory.initElements(
                driver,
                this);
    }

    @FindBy(xpath = "//a[contains(text(),'Login') or contains(text(),'login')]")
    private WebElement loginButton;

    @FindBy(xpath = "//input[contains(@placeholder,'location') or contains(@placeholder,'Location')]")
    private WebElement locationBox;

    @FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'doctor') or contains(@placeholder,'clinic')]")
    private WebElement searchBox;

    @FindBy(xpath = "//div[contains(text(),'Video Consult')] | //a[contains(text(),'Video Consult')]")
    private WebElement videoConsultLink;

    @FindBy(xpath = "//a[contains(text(),'Lab Tests') or contains(text(),'Diagnostics')]")
    private WebElement labTestsLink;

    @FindBy(xpath = "//*[contains(text(),'Corporate Wellness') or contains(text(),'For Corporates')]")
    private WebElement corporateWellnessLink;

    @FindBy(xpath = "//input[@data-qa-id='omni-searchbox-locality']")
    private WebElement hospitalLocationBoxElement;

    @FindBy(xpath = "//input[@data-qa-id='omni-searchbox-keyword']")
    private WebElement hospitalSearchBoxElement;

    @FindBy(xpath = "//a[@title='surgery']")
    private WebElement surgeriesButton;

    @FindBy(xpath = "//div[text()='Medicines']")
    private WebElement medicinesButton;

    @FindBy(xpath = "(//a[contains(@href,'medicines') or contains(@href,'medicine') or contains(normalize-space(),'Medicines')])[1]")
    private WebElement medicinesLink;

    @FindBy(xpath = "//span[contains(text(),'For Corporates')]")
    private WebElement forCorporates;

    @FindBy(xpath = "//a[contains(text(),'Health & Wellness Plans')]")
    private WebElement healthAndWellnessPlans;

    public By hospitalLocationBox =
            By.xpath("//input[@data-qa-id='omni-searchbox-locality']");

    public By hospitalSearchBox =
            By.xpath("//input[@data-qa-id='omni-searchbox-keyword']");

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

    public By locationOption(
            String location) {

        return By.xpath(
                "//div[contains(text(),'"
                        + location
                        + "')]");
    }

    public By searchOption(
            String searchKeyword) {

        return By.xpath(
                "//div[@data-qa-id='omni-suggestion-main' and text()='"
                        + searchKeyword
                        + "']");
    }

    public By searchOptionContains(
            String searchKeyword) {

        return By.xpath(
                "//div[@data-qa-id='omni-suggestion-main' and contains(text(),'"
                        + searchKeyword
                        + "')]");
    }

    public void clickLogin() {
        safeClick(loginButton);
    }

    public void clickVideoConsult() {
        safeClick(videoConsultLink);
    }

    public void clickVideoConsultUsingJS() {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                videoConsultLink);
    }

    public WebElement getVideoConsultLink() {
        return videoConsultLink;
    }

    public WebElement getLabTestsLink() {
        return labTestsLink;
    }

    public WebElement getCorporateWellnessLink() {
        return corporateWellnessLink;
    }

    public WebElement getForCorporates() {
        return forCorporates;
    }

    public void clickForCorporates() {
        safeClick(forCorporates);
    }

    public void clickForCorporatesUsingJS() {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                forCorporates);
    }

    public WebElement getHealthAndWellnessPlans() {
        return healthAndWellnessPlans;
    }

    public void clickHealthAndWellnessPlans() {
        safeClick(healthAndWellnessPlans);
    }

    public void clickHealthAndWellnessPlansUsingJS() {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                healthAndWellnessPlans);
    }

    public void enterHospitalLocation(
            String location) {

        waitForVisible(
                hospitalLocationBoxElement);

        hospitalLocationBoxElement.click();

        hospitalLocationBoxElement.clear();

        hospitalLocationBoxElement.sendKeys(
                location);
    }

    public void enterHospitalSearchKeyword(
            String searchKeyword) {

        waitForVisible(
                hospitalSearchBoxElement);

        hospitalSearchBoxElement.click();

        hospitalSearchBoxElement.clear();

        hospitalSearchBoxElement.sendKeys(
                searchKeyword);
    }

    public void triggerHospitalLocationSuggestion(
            String location) {

        hospitalLocationBoxElement.sendKeys(
                Keys.BACK_SPACE);

        hospitalLocationBoxElement.sendKeys(
                location.substring(
                        location.length() - 1));
    }

    public WebElement getHospitalLocationBoxElement() {
        return hospitalLocationBoxElement;
    }

    public WebElement getHospitalSearchBoxElement() {
        return hospitalSearchBoxElement;
    }

    public WebElement getSurgeriesButton() {
        return surgeriesButton;
    }

    public void clickSurgeriesButton() {
        safeClick(surgeriesButton);
    }

    public void clickSurgeriesButtonUsingJS() {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                surgeriesButton);
    }
}