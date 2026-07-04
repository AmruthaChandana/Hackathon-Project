package org.practo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;

public class HomePage {
    private WebDriver driver;

    public HomePage() {
    }

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Common Home Page elements
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

    // Hospital search elements
    @FindBy(xpath = "//input[@data-qa-id='omni-searchbox-locality']")
    private WebElement hospitalLocationBoxElement;

    @FindBy(xpath = "//input[@data-qa-id='omni-searchbox-keyword']")
    private WebElement hospitalSearchBoxElement;

    // TC_013 - Surgery navigation
    @FindBy(xpath = "//*[@id='root']/div/div/div[1]/div[1]/div[2]/div/div[2]/div[4]/a/div[1]")
    private WebElement surgeriesButton;

    // Medicines elements
    @FindBy(xpath = "//div[text()='Medicines']")
    private WebElement medicinesButton;

    @FindBy(xpath = "(//a[contains(@href,'medicines') or contains(@href,'medicine') or contains(normalize-space(),'Medicines')])[1]")
    private WebElement medicinesLink;

    // TC_019 / TC_020 - Corporate Wellness elements
    @FindBy(xpath = "//*[@id='root']/div/div/div[1]/div[1]/div[2]/div/div[3]/div[1]/span/span[2]")
    private WebElement forCorporates;

    @FindBy(xpath = "//*[@id='root']/div/div/div[1]/div[1]/div[2]/div/div[3]/div[1]/span/div/div[1]/a")
    private WebElement healthAndWellnessPlans;

    public By hospitalLocationBox = By.xpath("//input[@data-qa-id='omni-searchbox-locality']");

    public By hospitalSearchBox = By.xpath("//input[@data-qa-id='omni-searchbox-keyword']");

    public By locationOption(String location) {
        return By.xpath("//div[contains(text(),'" + location + "')]");
    }

    public By searchOption(String searchKeyword) {
        return By.xpath("//div[@data-qa-id='omni-suggestion-main' and text()='" + searchKeyword + "']");
    }

    public By searchOptionContains(String searchKeyword) {
        return By.xpath("//div[@data-qa-id='omni-suggestion-main' and contains(text(),'" + searchKeyword + "')]");
    }

    public void clickLogin() {
        WaitUtils.safeClick(driver, loginButton);
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    public void enterLocation(String location) {
        WaitUtils.waitForVisible(driver, locationBox);
        locationBox.clear();
        locationBox.sendKeys(location);
    }

    public void enterSearchKeyword(String keyword) {
        WaitUtils.waitForVisible(driver, searchBox);
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }

    public void clickVideoConsult() {
        WaitUtils.safeClick(driver, videoConsultLink);
    }

    public void clickVideoConsultUsingJS() {
        WaitUtils.clickUsingJS(driver, videoConsultLink);
    }

    public WebElement getVideoConsultLink() {
        return videoConsultLink;
    }

    public void clickLabTests() {
        WaitUtils.safeClick(driver, labTestsLink);
    }

    public void clickLabTestsUsingJS() {
        WaitUtils.clickUsingJS(driver, labTestsLink);
    }

    public WebElement getLabTestsLink() {
        return labTestsLink;
    }

    public void clickCorporateWellness() {
        WaitUtils.safeClick(driver, corporateWellnessLink);
    }

    public WebElement getCorporateWellnessLink() {
        return corporateWellnessLink;
    }

    public WebElement getForCorporates() {
        return forCorporates;
    }

    public void clickForCorporates() {
        WaitUtils.safeClick(driver, forCorporates);
    }

    public void clickForCorporatesUsingJS() {
        WaitUtils.clickUsingJS(driver, forCorporates);
    }

    public WebElement getHealthAndWellnessPlans() {
        return healthAndWellnessPlans;
    }

    public void clickHealthAndWellnessPlans() {
        WaitUtils.safeClick(driver, healthAndWellnessPlans);
    }

    public void clickHealthAndWellnessPlansUsingJS() {
        WaitUtils.clickUsingJS(driver, healthAndWellnessPlans);
    }

    public void enterHospitalLocation(String location) {
        WaitUtils.waitForVisible(driver, hospitalLocationBoxElement);
        hospitalLocationBoxElement.click();
        hospitalLocationBoxElement.clear();
        hospitalLocationBoxElement.sendKeys(location);
    }

    public void enterHospitalSearchKeyword(String searchKeyword) {
        WaitUtils.waitForVisible(driver, hospitalSearchBoxElement);
        hospitalSearchBoxElement.click();
        hospitalSearchBoxElement.clear();
        hospitalSearchBoxElement.sendKeys(searchKeyword);
    }

    public void triggerHospitalLocationSuggestion(String location) {
        hospitalLocationBoxElement.sendKeys(Keys.BACK_SPACE);
        hospitalLocationBoxElement.sendKeys(location.substring(location.length() - 1));
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
        WaitUtils.safeClick(driver, surgeriesButton);
    }

    public void clickSurgeriesButtonUsingJS() {
        WaitUtils.clickUsingJS(driver, surgeriesButton);
    }

    public WebElement getMedicinesButton() {
        return medicinesButton;
    }

    public void clickMedicinesButton() {
        WaitUtils.safeClick(driver, medicinesButton);
    }

    public void clickMedicinesButtonUsingJS() {
        WaitUtils.clickUsingJS(driver, medicinesButton);
    }

    public WebElement getMedicinesLink() {
        return medicinesLink;
    }

    public void clickMedicines() {
        WaitUtils.safeClick(driver, medicinesLink);
    }

    public void clickMedicinesUsingJS() {
        WaitUtils.clickUsingJS(driver, medicinesLink);
    }
}