package org.practo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SurgeryPage {
    private WebDriver driver;

    public SurgeryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // TC_013 - Treatments Offered heading
    @FindBy(xpath = "//*[@id='surgery-list']/section/h2")
    private WebElement treatmentsOfferedHeading;

    // TC_013 - Popular grid
    @FindBy(xpath = "//*[@id='surgery-list']/section/div/div[1]")
    private WebElement popularGrid;

    // TC_013 - Popular treatment cards
    @FindBy(xpath = "//*[@id='surgery-list']/section/div/div[1]/div[@role='button' or @tabindex='0']")
    private List<WebElement> popularTreatmentCards;

    public boolean isTreatmentsOfferedDisplayed() {
        return WaitUtils.isElementDisplayed(driver, treatmentsOfferedHeading);
    }

    public void scrollToTreatmentsOffered() {
        try {
            WaitUtils.scrollToElement(driver, treatmentsOfferedHeading);
        } catch (Exception ignored) {
        }
    }

    public boolean isPopularGridDisplayed() {
        return WaitUtils.isElementDisplayed(driver, popularGrid);
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
        WaitUtils.waitForVisible(driver, popularGrid);
        for (WebElement card : popularTreatmentCards) {
            try {
                String text = card.getText().trim();
                if (!text.isEmpty()) {
                    treatments.add(text);
                }
            } catch (Exception ignored) {
            }
        }
        return treatments.stream().distinct().collect(Collectors.toList());
    }
}