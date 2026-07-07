package org.practo.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class LabTestsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LabTestsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
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
    @FindBy(xpath = "//input[@placeholder='Search for city']")
    private WebElement citySearchField;

    @FindBy(xpath = "//div[contains(@class,'suggestion')]")
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

    private WebElement waitForVisible(WebElement element) {
        return wait.until(
                ExpectedConditions.visibilityOf(element)
        );
    }

    private WebElement waitForClickable(WebElement element) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(element)
        );
    }

    private void safeClick(WebElement element) {
        try {
            waitForClickable(element).click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }
    }

    private boolean isElementDisplayed(WebElement element) {
        try {
            return waitForVisible(element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLabTestsMenu() {
        safeClick(labTestsMenu);
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

    public void searchCity(String cityName) {
        waitForVisible(citySearchField);
        citySearchField.click();
        citySearchField.clear();
        citySearchField.sendKeys(cityName);
    }

    public void selectCity(String cityName) {
        if (cityName.equalsIgnoreCase("Bangalore")) {
            safeClick(bangaloreCity);
        }
    }

    public void clickDiabetesHealthConcern() {
        safeClick(diabetesLink);
    }

    public void clickThyroidAddToCart() {
        safeClick(thyroidAddToCart);
    }

    public boolean isAddedToCart() {
        return isElementDisplayed(removeButton);
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
        waitForVisible(topCitiesSection);
        return topCities.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(city -> !city.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }
}