package org.practo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;
import java.util.List;
import java.util.stream.Collectors;

public class LabTestsPage {
    private WebDriver driver;

    public LabTestsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // TC_006 - Navigation to Lab Tests Page
    @FindBy(xpath = "//a[@title='tests']")
    private WebElement labTestsMenu;

    // TC_007 - Top Cities
    @FindBy(xpath = "//div[text()='TOP CITIES']")
    private WebElement topCitiesSection;

    @FindBy(xpath = "//div[text()='TOP CITIES']/following::div[contains(@class,'o-f-color--primary')]")
    private List<WebElement> topCities;

    // TC_003 / TC_005 - Lab Test and City Search
    @FindBy(xpath = "//input[@type='text' and @placeholder='Search for city']")
    private WebElement citySearchField;

    @FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'test')]")
    private WebElement labSearchField;

    @FindBy(xpath = "//*[contains(@class,'suggestion') or contains(@class,'result')]")
    private List<WebElement> citySuggestions;

    @FindBy(xpath = "//div[text()='Bangalore']")
    private WebElement bangaloreCity;

    // TC_004 - Add To Cart
    @FindBy(xpath = "//a[contains(@href,'diabetes-checkup')]")
    private WebElement diabetesLink;

    @FindBy(xpath = "//a[contains(@href,'thyroid-profile-total-blood')]/following::div[contains(text(),'ADD TO CART')][1]")
    private WebElement thyroidAddToCart;

    @FindBy(xpath = "//div[contains(text(),'REMOVE')]")
    private WebElement removeButton;

    public void clickLabTestsMenu() {
        WaitUtils.safeClick(driver, labTestsMenu);
    }

    public boolean isLabTestsPageOpened() {
        try {
            String currentUrl = driver.getCurrentUrl().toLowerCase();
            String pageTitle = driver.getTitle().toLowerCase();
            return currentUrl.contains("tests") || currentUrl.contains("lab") || pageTitle.contains("lab");
        } catch (Exception e) {
            return false;
        }
    }

    public void enterCity(String city) {
        WaitUtils.waitForVisible(driver, citySearchField);
        citySearchField.clear();
        citySearchField.sendKeys(city);
    }

    public void searchLabTest(String testName) {
        WaitUtils.waitForVisible(driver, labSearchField);
        labSearchField.clear();
        labSearchField.sendKeys(testName);
    }

    public void searchCity(String cityName) {
        WaitUtils.waitForVisible(driver, citySearchField);
        citySearchField.click();
        citySearchField.clear();
        citySearchField.sendKeys(cityName);
    }

    public void selectCity(String cityName) {
        if (cityName.equalsIgnoreCase("Bangalore")) {
            WaitUtils.safeClick(driver, bangaloreCity);
        }
    }

    public void clickDiabetesHealthConcern() {
        WaitUtils.safeClick(driver, diabetesLink);
    }

    public void clickThyroidAddToCart() {
        WaitUtils.safeClick(driver, thyroidAddToCart);
    }

    public void selectBangaloreCity() {
        WaitUtils.safeClick(driver, bangaloreCity);
    }

    public boolean isAddedToCart() {
        return WaitUtils.isElementDisplayed(driver, removeButton);
    }

    public boolean isCitySuggestionAvailable() {
        try {
            return citySuggestions.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public int getTopCitiesCount() {
        return getTopCityNames().size();
    }

    public List<String> getTopCityNames() {
        WaitUtils.waitForVisible(driver, topCitiesSection);
        return topCities.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(city -> !city.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }
}