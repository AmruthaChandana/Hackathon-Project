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
import java.util.stream.Collectors;

public class SurgeryPage {
    private WebDriver driver;
    private WebDriverWait wait;
    public SurgeryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        PageFactory.initElements(driver, this);
    }

    // TC_013 - Treatments Offered heading
    @FindBy(xpath = "//h2[contains(text(),'Treatments Offered')]")
    private WebElement treatmentsOfferedHeading;

    // TC_013 - Popular grid
    @FindBy(xpath = "//div[@id='surgery-list']//div[contains(@class,'SurgeryList-module_grid')]")
    private WebElement popularGrid;

    // TC_013 - Treatment names
    @FindBy(xpath = "//div[@id='surgery-list']//span[contains(@class,'tileName')]")
    private List<WebElement> popularTreatmentCards;

    // Helper methods
    private WebElement waitForVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    private boolean isElementDisplayed(WebElement element) {
        try {
            return waitForVisible(element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    // Page actions
    public boolean isTreatmentsOfferedDisplayed() {
        return isElementDisplayed(treatmentsOfferedHeading);
    }

    public void scrollToTreatmentsOffered() {
        try {
            scrollToElement(treatmentsOfferedHeading);
        } catch (Exception ignored) {
        }
    }

    public boolean isPopularGridDisplayed() {
        return isElementDisplayed(popularGrid);
    }

    public int getPopularTreatmentsCount() {
        try {
            return popularTreatmentCards.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<String> getPopularTreatments() {
        List<String> treatments = new ArrayList<>();
        waitForVisible(popularGrid);
        for (WebElement treatment : popularTreatmentCards) {
            try {
                String text = treatment.getText().trim();
                if (!text.isEmpty()) {
                    treatments.add(text);
                }
            } catch (Exception ignored) {
            }
        }
        return treatments.stream().distinct().collect(Collectors.toList());
    }
}