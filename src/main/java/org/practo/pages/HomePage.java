package org.practo.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private WebDriver driver;

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

    @FindBy(xpath = "//a[contains(text(),'Medicines') or contains(text(),'medicine')]")
    private WebElement medicinesLink;

    @FindBy(xpath = "//*[contains(text(),'Corporate Wellness') or contains(text(),'For Corporates')]")
    private WebElement corporateWellnessLink;

    // ==========================
    // Actions
    // ==========================

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

    public void clickMedicines() {
        medicinesLink.click();
    }

    public void clickCorporateWellness() {
        corporateWellnessLink.click();
    }

    // ==========================
    // Getters (for waits)
    // ==========================

    public WebElement getVideoConsultLink() {
        return videoConsultLink;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    public WebElement getCorporateWellnessLink() {
        return corporateWellnessLink;
    }
}