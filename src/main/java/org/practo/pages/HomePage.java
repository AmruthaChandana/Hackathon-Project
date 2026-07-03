package org.practo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;
    public HomePage() {
    }
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
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

    public By hospitalLocationBox =
            By.xpath("//input[@data-qa-id='omni-searchbox-locality']");

    public By hospitalSearchBox =
            By.xpath("//input[@data-qa-id='omni-searchbox-keyword']");

    public By locationOption(String location) {
        return By.xpath("//div[contains(text(),'" + location + "')]");
    }

    public By searchOption(String searchKeyword) {
        return By.xpath(
                "//div[@data-qa-id='omni-suggestion-main' and text()='"
                        + searchKeyword + "']");
    }

    public By searchOptionContains(String searchKeyword) {
        return By.xpath(
                "//div[@data-qa-id='omni-suggestion-main' and contains(text(),'"
                        + searchKeyword + "')]");
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void enterLocation(String location) {
        locationBox.clear();
        locationBox.sendKeys(location);
    }

    public void enterSearchKeyword(String keyword) {
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }

    public void clickVideoConsult() {
        videoConsultLink.click();
    }

    public void clickVideoConsultUsingJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", videoConsultLink);
    }

    public void clickLabTests() {
        labTestsLink.click();
    }

    public void clickCorporateWellness() {
        corporateWellnessLink.click();
    }

    public void enterHospitalLocation(String location) {
        hospitalLocationBoxElement.click();
        hospitalLocationBoxElement.clear();
        hospitalLocationBoxElement.sendKeys(location);
    }

    public void enterHospitalSearchKeyword(String searchKeyword) {
        hospitalSearchBoxElement.click();
        hospitalSearchBoxElement.clear();
        hospitalSearchBoxElement.sendKeys(searchKeyword);
    }

    public void triggerHospitalLocationSuggestion(String location) {
        hospitalLocationBoxElement.sendKeys(Keys.BACK_SPACE);
        hospitalLocationBoxElement.sendKeys(
                location.substring(location.length() - 1));
    }

    public void clickLocationOption(String location) {
        driver.findElement(locationOption(location)).click();
    }

    public void clickSearchOption(String searchKeyword) {
        driver.findElement(searchOption(searchKeyword)).click();
    }

    public void clickSearchOptionContains(String searchKeyword) {
        driver.findElement(searchOptionContains(searchKeyword)).click();
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    public WebElement getVideoConsultLink() {
        return videoConsultLink;
    }

    public WebElement getCorporateWellnessLink() {
        return corporateWellnessLink;
    }

    public WebElement getHospitalLocationBoxElement() {
        return hospitalLocationBoxElement;
    }

    public WebElement getHospitalSearchBoxElement() {
        return hospitalSearchBoxElement;
    }
}