package org.practo.pages;

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
    // Lab Tests Navigation
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

    @FindBy(xpath = "//*[contains(text(),'Bangalore') or contains(text(),'Mumbai') or contains(text(),'Delhi') or contains(text(),'Hyderabad') or contains(text(),'Pune')]")
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
    // Navigation Methods
    // ==========================

    public void clickLabTestsMenu() {
        labTestsMenu.click();
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
    // Existing Actions
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

    // ==========================
    // Data Retrieval Methods
    // ==========================

    public int getTopCitiesCount() {
        return topCityNames.size();
    }

    public List<String> getTopCityNames() {

        return topCityNames.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(city -> !city.isEmpty())
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