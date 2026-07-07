package org.practo.pages;

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

    private <T> T waitUntil(java.util.function.Function<WebDriver, T> condition) {
        return wait.until(condition);
    }

    // Medicine Search Methods
    public void searchMedicine(String medicineName) {
        waitForVisible(medicineSearchBox);
        medicineSearchBox.clear();
        medicineSearchBox.sendKeys(medicineName);
        medicineSearchBox.click();
        waitForVisible(searchResultsBox);
        waitUntil(driver -> medicineSuggestions.size() > 0);
    }

    public void searchInvalidMedicine(String medicineName) {
        waitForVisible(medicineSearchBox);
        medicineSearchBox.clear();
        medicineSearchBox.sendKeys(medicineName);
        medicineSearchBox.click();
    }

    public int getMedicineCount() {
        waitForVisible(searchResultsBox);
        waitUntil(driver -> medicineSuggestions.size() > 0);
        System.out.println("Total Medicines Found : " + medicineSuggestions.size());
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
        System.out.println("\n========== FIRST 5 MEDICINES ==========");
        int count = Math.min(5, medicines.size());

        for (int i = 0; i < count; i++) {
            String[] medicine = medicines.get(i);
            System.out.println("------------------------------------");
            System.out.println("Medicine Name : " + medicine[0]);
            System.out.println("Price         : " + medicine[1]);
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
                    medicineDetails.add(
                            new String[]{
                                    name,
                                    price
                            }
                    );
                }
            } catch (Exception ignored) {
            }
        }

        return medicineDetails;
    }

    // Result Verification Methods
    public boolean isMedicinePresentInResults(String expectedMedicineName) {
        try {
            waitForVisible(searchResultsBox);
            waitUntil(driver -> medicineSuggestions.size() > 0);

            for (WebElement suggestion : medicineSuggestions) {
                try {
                    String text = suggestion.getText().trim();

                    if (!text.isEmpty() && text.toLowerCase().contains(expectedMedicineName.toLowerCase())) {
                        return true;
                    }
                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
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
                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
            return "";
        }

        return "";
    }

    public void clickFirstMedicineFromResults() {
        waitUntil(driver -> medicineSuggestions.size() > 0);
        medicineSuggestions.get(0).click();
    }

    public void clickAddToCart() {
        try {
            waitForVisible(firstAddToCartButton);
            scrollToElement(firstAddToCartButton);
            safeClick(firstAddToCartButton);
            System.out.println("Clicked Add To Cart button");
        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to click Add To Cart button",
                    e
            );
        }
    }

    public boolean isCartDisplayed() {
        return isElementDisplayed(cartIcon);
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );
    }

    private void scrollMedicineResultsDropdown() {
        try {
            waitForVisible(searchResultsBox);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].scrollTop = arguments[0].scrollTop + 300;",
                    searchResultsBox
            );
        } catch (Exception ignored) {
        }
    }
}