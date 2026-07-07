package org.practo.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MedicinesPage {
    private static final Logger logger = LogManager.getLogger(MedicinesPage.class);
    private WebDriver driver;
    private WebDriverWait wait;
    public MedicinesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        PageFactory.initElements(driver, this);
    }

    // Medicine Search Elements
    @FindBy(css = "input[placeholder*='Search']")
    private WebElement medicineSearchBox;

    @FindBy(className = "search-bar__results")
    private WebElement searchResultsBox;

    @FindBy(css = ".search-bar__results a")
    private List<WebElement> medicineSuggestions;

    // Add To Cart Elements
    @FindBy(xpath = "(//*[self::button or self::div or self::span or self::a][contains(normalize-space(),'ADD') or contains(normalize-space(),'Add') or contains(normalize-space(),'ADD TO CART') or contains(normalize-space(),'Add to Cart')])[1]")
    private WebElement firstAddToCartButton;

    @FindBy(xpath = "//*[contains(normalize-space(),'Cart') or contains(normalize-space(),'Your Cart') or contains(@class,'cart') or contains(@class,'Cart')]")
    private WebElement cartIcon;

    // Helper Methods
    private WebElement waitForVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    private WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void safeClick(WebElement element) {
        try {
            waitForClickable(element).click();
        } catch (Exception e) {
            logger.warn("Regular click failed. Using JavaScript click.");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }
    }

    private boolean isElementDisplayed(WebElement element) {
        try {
            return waitForVisible(element).isDisplayed();
        } catch (Exception e) {
            logger.debug("Element is not displayed.");
            return false;
        }
    }

    private <T> T waitUntil(java.util.function.Function<WebDriver, T> condition) {
        return wait.until(condition);
    }

    // Medicine Search Methods
    public void searchMedicine(String medicineName) {
        logger.info("Searching medicine: {}", medicineName);
        waitForVisible(medicineSearchBox);
        medicineSearchBox.clear();
        medicineSearchBox.sendKeys(medicineName);
        medicineSearchBox.click();
        waitForVisible(searchResultsBox);
        waitUntil(driver -> medicineSuggestions.size() > 0);
    }

    public void searchInvalidMedicine(String medicineName) {
        logger.info("Searching invalid medicine: {}", medicineName);
        waitForVisible(medicineSearchBox);
        medicineSearchBox.clear();
        medicineSearchBox.sendKeys(medicineName);
        medicineSearchBox.click();
    }

    public int getMedicineCount() {
        waitForVisible(searchResultsBox);
        waitUntil(driver -> medicineSuggestions.size() > 0);
        logger.info("Total Medicines Found : {}", medicineSuggestions.size());
        return medicineSuggestions.size();
    }

    public void printFirstFiveMedicines() {
        waitForVisible(searchResultsBox);
        waitUntil(driver -> {
            List<String[]> medicines = getPricedMedicineDetails();
            if (medicines.size() >= 5) {
                return true;
            }
            scrollMedicineResultsDropdown();
            return false;
        });

        List<String[]> medicines = getPricedMedicineDetails();
        logger.info("========== FIRST 5 MEDICINES ==========");
        int count = Math.min(5, medicines.size());
        for (int i = 0; i < count; i++) {
            String[] medicine = medicines.get(i);
            logger.info("------------------------------------");
            logger.info("Medicine Name : {}", medicine[0]);
            logger.info("Price : {}", medicine[1]);
        }
    }

    private List<String[]> getPricedMedicineDetails() {
        List<String[]> medicineDetails = new ArrayList<>();
        for (WebElement suggestion : medicineSuggestions) {
            try {
                String text = suggestion.getText();
                String[] lines = text.split("\\R");
                String name = "";
                String price = "";
                for (String line : lines) {
                    line = line.trim();
                    if (line.isEmpty() || line.equalsIgnoreCase("ADD")) {
                        continue;
                    }
                    if (name.isEmpty()) {
                        name = line;
                    }
                    if (line.contains("₹")) {
                        price = line;
                    }
                }
                if (!name.isEmpty() && !price.isEmpty()) {
                    medicineDetails.add(new String[]{name, price});
                }
            } catch (Exception e) {
                logger.debug("Unable to parse medicine details.", e);
            }
        }
        return medicineDetails;
    }

    // Result Verification Methods
    public boolean isMedicinePresentInResults(String expectedMedicineName) {
        try {
            waitForVisible(searchResultsBox);
            waitUntil(
                    driver ->
                            medicineSuggestions.size() > 0);
            for (WebElement suggestion : medicineSuggestions) {
                try {
                    String text = suggestion.getText().trim();
                    if (!text.isEmpty() && text.toLowerCase().contains(expectedMedicineName.toLowerCase())) {
                        return true;
                    }
                } catch (Exception e) {
                    logger.debug("Error reading medicine suggestion.", e);
                }
            }
        } catch (Exception e) {
            logger.error("Error while verifying medicine search result.", e);
            return false;
        }
        return false;
    }

    public String getMatchedMedicineName(String expectedMedicineName) {
        try {
            waitForVisible(searchResultsBox);
            waitUntil(driver -> medicineSuggestions.size() > 0);
            for (WebElement suggestion : medicineSuggestions) {
                try {
                    String text = suggestion.getText().trim();
                    if (!text.isEmpty() && text.toLowerCase().contains(expectedMedicineName.toLowerCase())) {
                        String[] lines = text.split("\\R");
                        for (String line : lines) {
                            line = line.trim();
                            if (!line.isEmpty() && !line.equalsIgnoreCase("ADD")) {
                                return line;
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.debug("Unable to process medicine suggestion.", e);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to find matched medicine.", e);
            return "MEDICINE_NOT_FOUND";
        }
        logger.warn("No matching medicine found for: {}", expectedMedicineName);
        return "MEDICINE_NOT_FOUND";
    }

    public void clickFirstMedicineFromResults() {
        waitUntil(driver -> medicineSuggestions.size() > 0);
        medicineSuggestions.get(0).click();
        logger.info("Clicked first medicine from search results");
    }

    public void clickAddToCart() {
        try {
            waitForVisible(firstAddToCartButton);
            scrollToElement(firstAddToCartButton);
            safeClick(firstAddToCartButton);
            logger.info("Clicked Add To Cart button");
        } catch (Exception e) {
            logger.error("Unable to click Add To Cart button", e);
            throw new RuntimeException("Unable to click Add To Cart button", e);
        }
    }

    public boolean isCartDisplayed() {
        return isElementDisplayed(cartIcon);
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    private void scrollMedicineResultsDropdown() {
        try {
            waitForVisible(searchResultsBox);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + 300;", searchResultsBox);
        } catch (Exception e) {
            logger.debug("Unable to scroll medicine results dropdown.", e);
        }
    }
}