package org.practo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class HospitalPage {

    private WebDriver driver;

    public HospitalPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(@class,'listing') or contains(@class,'card')]")
    private List<WebElement> hospitalCards;

    @FindBy(xpath = "//h2 | //h3 | //div[contains(@class,'name')]")
    private List<WebElement> hospitalNames;

    @FindBy(xpath = "//*[contains(@class,'rating')]")
    private List<WebElement> hospitalRatings;

    @FindBy(xpath = "//*[contains(text(),'Open 24') or contains(text(),'24/7') or contains(text(),'Open now')]")
    private WebElement open247Filter;

    @FindBy(xpath = "(//div[contains(@class,'listing') or contains(@class,'card')])[1]")
    private WebElement firstHospitalCard;

    @FindBy(xpath = "//*[contains(text(),'Address')]/following::*[1] | //*[contains(@class,'address')]")
    private WebElement addressSection;

    // ==========================
    // Actions
    // ==========================

    public void clickOpen24x7Filter() {
        open247Filter.click();
    }

    public void clickFirstHospital() {
        firstHospitalCard.click();
    }

    // ==========================
    // Data Retrieval Methods
    // ==========================

    public int getHospitalCount() {
        return hospitalCards.size();
    }

    public List<String> getHospitalNames() {

        return hospitalNames.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .collect(Collectors.toList());
    }

    public List<String> getHospitalRatings() {

        return hospitalRatings.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(rating -> !rating.isEmpty())
                .collect(Collectors.toList());
    }

    public String getHospitalAddress() {

        try {
            return addressSection.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    // ==========================
    // Validation Methods
    // ==========================

    public boolean isOpen24x7FilterDisplayed() {

        try {
            return open247Filter.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAddressDisplayed() {

        try {
            return addressSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasHospitals() {
        return getHospitalCount() > 0;
    }
}