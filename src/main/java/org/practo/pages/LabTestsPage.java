package org.practo.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

    @FindBy(xpath = "//a[@title='tests']")
    private WebElement labTestsMenu;

    @FindBy(className = "icon-ic_dropdown")
    private WebElement cityDropDown;

    @FindBy(xpath = "//div[text()='TOP CITIES']")
    private WebElement topCitiesSection;

    @FindBy(xpath = "//div[text()='TOP CITIES']/following::div[contains(@class,'o-f-color--primary')]")
    private List<WebElement> topCities;

    @FindBy(xpath = "//input[@type='text' and @placeholder='Search for city']")
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
// TC_009 - Add to Cart
// ==========================

    @FindBy(xpath = "//a[contains(@href,'liver-function-tests-blood')]//div[contains(text(),'Add to Cart')]")
    private WebElement liverFunctionAddToCart;

//    @FindBy(className = "c-order-cart")
//    private WebElement cartSection;

    @FindBy(xpath = "//*[contains(text(),'Liver Function Test')]")
    private WebElement liverFunctionTestInCart;
    // City Search Box
    @FindBy(xpath = "//input[@placeholder='Search for city']")
    private WebElement citySearchBox;

    // First city suggestion
    @FindBy(xpath = "(//div[contains(@class,'suggestion')])[1]")
    private WebElement firstCitySuggestion;


    @FindBy(xpath = "//div[text()='Bangalore']")
    private WebElement bangaloreCity;

    // Thyroid Profile Add To Cart
    @FindBy(xpath = "//div[contains(text(),'ADD TO CART')]")
    private WebElement addToCartButton;

    @FindBy(xpath =
            "//a[contains(@href,'thyroid-profile-total-blood')]/following::div[contains(text(),'ADD TO CART')][1]")
    private WebElement thyroidAddToCart;
    // Cart
    @FindBy(xpath = "//*[contains(text(),'Your Cart')]")
    private WebElement cartSection;

    @FindBy(xpath = "//div[contains(text(),'REMOVE')]")
    private WebElement removeButton;

    @FindBy(xpath = "//a[contains(@href,'diabetes-checkup')]")
    private WebElement diabetesLink;

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

    public void enterCity(String city) {
        citySearchField.clear();
        citySearchField.sendKeys(city);
    }

    public void searchLabTest(String testName) {
        labSearchField.clear();
        labSearchField.sendKeys(testName);
    }

    public void searchCity(String cityName) {
        citySearchField.click();
        citySearchField.clear();
        citySearchField.sendKeys(cityName);
    }

    public void selectCity(String cityName) {
        if(cityName.equalsIgnoreCase("Bangalore")) {
            bangaloreCity.click();
        }
    }

    public void clickDiabetesHealthConcern() {
        diabetesLink.click();
    }

    public boolean isAddedToCart() {
        try {
            return removeButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickThyroidAddToCart() {
        thyroidAddToCart.click();
    }

    public void selectBangaloreCity() {
        bangaloreCity.click();
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
        return topCities.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(city -> !city.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }
}