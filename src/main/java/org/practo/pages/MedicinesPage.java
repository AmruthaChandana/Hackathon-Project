package org.practo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class MedicinesPage {

    private WebDriver driver;

    public MedicinesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'medicine')]")
    private WebElement medicineSearchBox;

    @FindBy(xpath = "//*[contains(@class,'medicine') or contains(@class,'product') or contains(@class,'card')]")
    private List<WebElement> medicineList;

    @FindBy(xpath = "//*[contains(@class,'name') or contains(@class,'title')]")
    private List<WebElement> medicineNames;

    @FindBy(xpath = "(//button[contains(text(),'Add') or contains(text(),'Cart')])[1]")
    private WebElement addToCartButton;

    @FindBy(xpath = "//*[contains(text(),'Cart') or contains(@class,'cart')]")
    private WebElement cartIcon;

    @FindBy(xpath = "//*[contains(text(),'Out of Stock') or contains(text(),'Unavailable')]")
    private WebElement outOfStockText;

    @FindBy(xpath = "//button[contains(text(),'Add') and @disabled]")
    private WebElement disabledAddToCart;

    // ==========================
    // Actions
    // ==========================

    public void searchMedicine(String medicineName) {

        medicineSearchBox.clear();
        medicineSearchBox.sendKeys(medicineName);
    }

    public void clickAddToCart() {
        addToCartButton.click();
    }

    public void clickCart() {
        cartIcon.click();
    }

    // ==========================
    // Validation Methods
    // ==========================

    public boolean isCartDisplayed() {

        try {
            return cartIcon.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOutOfStockMessageDisplayed() {

        try {
            return outOfStockText.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAddToCartDisabled() {

        try {
            return !disabledAddToCart.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasMedicines() {
        return medicineList.size() > 0;
    }

    // ==========================
    // Data Retrieval Methods
    // ==========================

    public int getMedicineCount() {
        return medicineList.size();
    }

    public List<String> getMedicineNames() {

        return medicineNames.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .collect(Collectors.toList());
    }

    public String getOutOfStockMessage() {

        try {
            return outOfStockText.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    // ==========================
    // Business Method
    // ==========================

    public void searchAndAddMedicine(String medicineName) {

        searchMedicine(medicineName);

        if (hasMedicines()) {
            clickAddToCart();
        }
    }
}