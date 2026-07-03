package org.practo.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class LabTestsPage {
    private WebDriver driver;
    public LabTestsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ==========================
    // TC_009 - Navigation
    // ==========================

    @FindBy(xpath = "//a[@title='tests']")
    private WebElement labTestsMenu;

    // ==========================
    // Existing Elements
    // ==========================

    @FindBy(xpath = "//*[contains(text(),'Select City') or contains(@class,'city')]")
    private WebElement cityDropdown;

    @FindBy(xpath = "//*[contains(text(),'Top Cities') or contains(text(),'Popular Cities')]")
    private WebElement topCitiesSection;

    @FindBy(xpath = "//li[contains(@class,'u-text--center')]//div[contains(@class,'o-f-color--primary')]")
    private List<WebElement> topCityNames;

    @FindBy(xpath = "//input[contains(@placeholder,'city') or contains(@placeholder,'City')]")
    private WebElement citySearchField;

    @FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'test')]")
    private WebElement labSearchField;

    @FindBy(xpath = "//*[contains(text(),'Thyroid') or contains(@class,'test')]")
    private List<WebElement> labSearchResults;

    @FindBy(xpath = "//*[contains(text(),'Health') or contains(text(),'package') or contains(text(),'Package')]")
    private List<WebElement> healthPackages;

    // ==========================
    // TC_010 - Top Cities Icons
    // ==========================

    @FindBy(xpath = "//img[contains(@src,'topcities')]")
    private List<WebElement> topCityIcons;

    // ==========================
    // TC_012 - City Suggestions
    // ==========================

    @FindBy(xpath = "//*[contains(@class,'suggestion') or contains(@class,'result')]")
    private List<WebElement> citySuggestions;

    // ==========================
    // TC_013 - Delhi & Thyroid Search
    // ==========================

    @FindBy(xpath = "//img[contains(@src,'Delhi.svg')]")
    private WebElement delhiCity;

    @FindBy(xpath = "//*[contains(@class,'suggestion-container')]//*[contains(text(),'Thyroid')]")
    private List<WebElement> thyroidSearchResults;

    // ==========================
    // TC_009 Methods
    // ==========================

    public void clickLabTestsMenu() {

        try {
            labTestsMenu.click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", labTestsMenu);
        }
    }

    public boolean isLabTestsPageOpened() {

        try {

            String currentUrl = driver.getCurrentUrl().toLowerCase();
            String pageTitle = driver.getTitle().toLowerCase();
            return currentUrl.contains("tests")
                    || currentUrl.contains("lab")
                    || pageTitle.contains("lab");
        } catch (Exception e) {
            return false;
        }
    }

    // ==========================
    // Actions
    // ==========================

    public void clickCityDropdown() {
        cityDropdown.click();
    }

    public void enterCity(String city) {
        citySearchField.clear();
        citySearchField.sendKeys(city);
    }

    public void searchLabTest(String testName) {
        labSearchField.clear();
        labSearchField.sendKeys(testName);
    }

    public void searchCity(String cityName) {
        citySearchField.clear();
        citySearchField.sendKeys(cityName);
    }

    public void selectDelhiCity() {

        try {
            delhiCity.click();
        } catch (Exception e) {

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", delhiCity);
        }
    }

    // ==========================
    // Validation Methods
    // ==========================

    public boolean isTopCitiesDisplayed() {
        try {
            return topCitiesSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCityDropdownDisplayed() {
        try {
            return cityDropdown.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasLabResults() {
        return labSearchResults.size() > 0;
    }

    public boolean hasHealthPackages() {
        return healthPackages.size() > 0;
    }

    public boolean isTopCitiesSectionVisible() {

        try {
            return topCityIcons.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public int getTopCityIconsCount() {

        try {
            return topCityIcons.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isCitySuggestionAvailable() {

        try {
            return citySuggestions.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasThyroidResults() {

        try {
            return thyroidSearchResults.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // ==========================
    // Data Retrieval Methods
    // ==========================

    public List<String> getCitySuggestions() {

        return citySuggestions.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(city -> !city.isEmpty())
                .collect(Collectors.toList());
    }

    public List<String> getThyroidResults() {

        return thyroidSearchResults.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(result -> !result.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    public int getTopCitiesCount() {
        return getTopCityNames().size();
    }

    public List<String> getTopCityNames() {

        return topCityNames.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(city -> !city.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    public int getLabResultsCount() {
        return labSearchResults.size();
    }

    public int getHealthPackageCount() {
        return healthPackages.size();
    }

    public List<String> getLabResultNames() {
        return labSearchResults.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(result -> !result.isEmpty())
                .collect(Collectors.toList());
    }
}