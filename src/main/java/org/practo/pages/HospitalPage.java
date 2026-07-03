package org.practo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class HospitalPage {

    private WebDriver driver;

    public HospitalPage() {
    }

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

    // TC11 to TC15 PageFactory Elements
    // Added for hospital search test cases

    @FindBy(xpath = "//h2[contains(@class,'line-1')]")
    private List<WebElement> hospitalNamesForSearchResultsElements;

    @FindBy(xpath = "//button[@class='c-book-cta' and text()='Book Hospital Visit']")
    private WebElement bookHospitalVisitButtonElement;

    @FindBy(xpath = "//div[@data-qa-id='no_results']")
    private WebElement noResultsMessageElement;

    // TC11 to TC15 By Locators
    // Kept so existing TC11-TC15 test classes do not break

    public By hospitalNamesForSearchResults = By.xpath("//h2[contains(@class,'line-1')]");
    public By hospitalCardFromName = By.xpath("./ancestor::li");
    public By open24x7Text = By.xpath(".//span[normalize-space()='Open 24x7']");
    public By ratingText = By.xpath(".//div[contains(@class,'c-feedback')]//span[contains(@class,'u-bold')]");
    public By bookHospitalVisitButton = By.xpath("//button[@class='c-book-cta' and text()='Book Hospital Visit']");
    public By noResultsMessage = By.xpath("//div[@data-qa-id='no_results']");

    // Existing Actions

    public void clickOpen24x7Filter() {
        open247Filter.click();
    }

    public void clickFirstHospital() {
        firstHospitalCard.click();
    }

    // TC11 to TC15 PageFactory Actions

    public void clickBookHospitalVisitButton() {
        bookHospitalVisitButtonElement.click();
    }

    // Existing Data Retrieval Methods

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

    // TC11 to TC15 PageFactory Data Retrieval Methods

    public List<WebElement> getHospitalNamesForSearchResultsElements() {
        return hospitalNamesForSearchResultsElements;
    }

    public String getNoResultsMessageText() {

        try {
            return noResultsMessageElement.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    // Existing Validation Methods

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

    // TC11 to TC15 PageFactory Validation Methods

    public boolean isBookHospitalVisitButtonDisplayed() {

        try {
            return bookHospitalVisitButtonElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBookHospitalVisitButtonEnabled() {

        try {
            return bookHospitalVisitButtonElement.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNoResultsMessageDisplayed() {

        try {
            return noResultsMessageElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}