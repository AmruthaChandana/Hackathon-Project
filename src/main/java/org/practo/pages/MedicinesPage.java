package org.practo.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;
import java.util.ArrayList;
import java.util.List;

public class MedicinesPage {
    private WebDriver driver;

    public MedicinesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // TC_016 / TC_017 / TC_018 - Medicine search elements
    @FindBy(css = "input[placeholder*='Search']")
    private WebElement medicineSearchBox;

    @FindBy(className = "search-bar__results")
    private WebElement searchResultsBox;

    @FindBy(css = ".search-bar__results a")
    private List<WebElement> medicineSuggestions;

    // TC_017 - Add to cart elements
    @FindBy(xpath = "(//*[self::button or self::div or self::span or self::a][contains(normalize-space(),'ADD') or contains(normalize-space(),'Add') or contains(normalize-space(),'ADD TO CART') or contains(normalize-space(),'Add to Cart')])[1]")
    private WebElement firstAddToCartButton;

    @FindBy(xpath = "//*[contains(normalize-space(),'Cart') or contains(normalize-space(),'Your Cart') or contains(@class,'cart') or contains(@class,'Cart')]")
    private WebElement cartIcon;

    public void searchMedicine(String medicineName) {
        WaitUtils.waitForVisible(driver, medicineSearchBox);
        medicineSearchBox.clear();
        medicineSearchBox.sendKeys(medicineName);
        medicineSearchBox.click();
        WaitUtils.waitForVisible(driver, searchResultsBox);
        WaitUtils.waitUntil(driver, driver -> medicineSuggestions.size() > 0);
    }

    public int getMedicineCount() {
        WaitUtils.waitForVisible(driver, searchResultsBox);
        WaitUtils.waitUntil(driver, driver -> medicineSuggestions.size() > 0);
        System.out.println("Total Medicines Found : " + medicineSuggestions.size());
        return medicineSuggestions.size();
    }

    public void printFirstFiveMedicines() {
        WaitUtils.waitForVisible(driver, searchResultsBox);
        WaitUtils.waitUntil(driver, driver -> {
            List<String[]> pricedMedicines = getPricedMedicineDetails();
            if (pricedMedicines.size() >= 5) {
                return true;
            }
            scrollMedicineResultsDropdown();
            return false;
        });
        List<String[]> pricedMedicines = getPricedMedicineDetails();
        System.out.println("\n========== FIRST 5 MEDICINES ==========");
        int count = Math.min(5, pricedMedicines.size());
        for (int i = 0; i < count; i++) {
            String[] medicine = pricedMedicines.get(i);
            System.out.println("------------------------------------");
            System.out.println("Medicine Name : " + medicine[0]);
            System.out.println("Price         : " + medicine[1]);
        }
    }

    public void clickAddToCart() {
        try {
            WaitUtils.waitForVisible(driver, firstAddToCartButton);
            WaitUtils.scrollToElement(driver, firstAddToCartButton);
            WaitUtils.safeClick(driver, firstAddToCartButton);
            System.out.println("Clicked Add To Cart button");
        } catch (Exception e) {
            throw new RuntimeException("Unable to click Add To Cart button", e);
        }
    }

    public boolean isCartDisplayed() {
        return WaitUtils.isElementDisplayed(driver, cartIcon);
    }

    public void searchAndAddMedicine(String medicineName) {
        searchMedicine(medicineName);
        WaitUtils.waitUntil(driver, driver -> getMedicineCount() > 0);
        clickAddToCart();
    }

    private void scrollMedicineResultsDropdown() {
        try {
            WaitUtils.waitForVisible(driver, searchResultsBox);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + 300;", searchResultsBox);
        } catch (Exception ignored) {
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
            } catch (Exception ignored) {
            }
        }
        return medicineDetails;
    }

    public boolean hasMedicines() {
        try {
            return getMedicineCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isMedicinePresentInResults(String expectedMedicineName) {
        try {
            WaitUtils.waitForVisible(driver, searchResultsBox);
            WaitUtils.waitUntil(driver, driver -> medicineSuggestions.size() > 0);
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
            WaitUtils.waitForVisible(driver, searchResultsBox);
            WaitUtils.waitUntil(driver, driver -> medicineSuggestions.size() > 0);
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

    public void searchInvalidMedicine(String medicineName) {
        WaitUtils.waitForVisible(driver, medicineSearchBox);
        medicineSearchBox.clear();
        medicineSearchBox.sendKeys(medicineName);
        medicineSearchBox.click();
    }

    public boolean hasNoMedicineResults() {
        try {
            return medicineSuggestions.size() == 0;
        } catch (Exception e) {
            return true;
        }
    }

    public void clickFirstMedicineFromResults() {
        WaitUtils.waitUntil(driver, driver -> medicineSuggestions.size() > 0);
        medicineSuggestions.get(0).click();
    }
}