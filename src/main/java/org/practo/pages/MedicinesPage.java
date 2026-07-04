package org.practo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MedicinesPage {
    private WebDriver driver;
    private WebDriverWait wait;
    public MedicinesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "input[placeholder*='Search']")
    private WebElement searchBox;

    @FindBy(className = "search-bar__results")
    private WebElement resultsBox;

    @FindBy(css = "input[placeholder*='Search']")
    private WebElement searchBox;

    @FindBy(className = "search-bar__results")
    private WebElement resultsBox;

    @FindBy(xpath = "//span[contains(text(),'ADD TO CART')]")
    private WebElement addToCartButton;

    public void searchMedicine(String medicineName) {
        wait.until(
                ExpectedConditions.visibilityOf(searchBox)
        );
        searchBox.clear();
        searchBox.sendKeys(medicineName);
        searchBox.click();
        wait.until(
                ExpectedConditions.visibilityOf(resultsBox)
        );
    }

    public int getMedicineCount() {
        wait.until(
                ExpectedConditions.visibilityOf(resultsBox)
        );
        List<WebElement> suggestions =
                driver.findElements(
                        By.cssSelector(".search-bar__results a"));
        System.out.println(
                "Total Medicines Found : "
                        + suggestions.size());
        return suggestions.size();
    }

    public void printFirstFiveMedicines() {
        wait.until(
                ExpectedConditions.visibilityOf(resultsBox)
        );
        JavascriptExecutor js =
                (JavascriptExecutor) driver;
        List<WebElement> suggestions =
                driver.findElements(
                        By.cssSelector(".search-bar__results a"));
        try {
            while (suggestions.size() < 10) {
                int currentSize = suggestions.size();
                js.executeScript(
                        "arguments[0].scrollTop = arguments[0].scrollTop + 300;",
                        resultsBox
                );
                wait.until(driver ->
                        driver.findElements(
                                        By.cssSelector(".search-bar__results a"))
                                .size() >= currentSize
                );
                suggestions =
                        driver.findElements(
                                By.cssSelector(".search-bar__results a"));
                if (suggestions.size() >= 10) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(
                "\n========== FIRST 5 MEDICINES ==========");
        int printedCount = 0;
        for (WebElement suggestion : suggestions) {
            if (printedCount == 5) {
                break;
            }
            String text = suggestion.getText();
            String[] lines = text.split("\\R");
            String name = "";
            String price = "";
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()
                        || line.equalsIgnoreCase("ADD")) {
                    continue;
                }
                if (name.isEmpty()) {
                    name = line;
                }
                if (line.contains("₹")) {
                    price = line;
                }
            }
            if (price.isEmpty()) {
                continue;
            }
            printedCount++;
            System.out.println("------------------------------------");
            System.out.println("Medicine Name : " + name);
            System.out.println("Price         : " + price);
        }
    }
    public void clickAddToCart() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        addToCartButton)
        );

        addToCartButton.click();
    }

    @FindBy(css = ".search-bar__results a")
    private List<WebElement> medicineSuggestions;

    public boolean isMedicinePresentInResults(String medicineName) {

        wait.until(
                ExpectedConditions.visibilityOf(resultsBox)
        );

        for (WebElement suggestion : medicineSuggestions) {

            if (suggestion.getText()
                    .toLowerCase()
                    .contains(medicineName.toLowerCase())) {

                return true;
            }
        }

        return false;
    }

}