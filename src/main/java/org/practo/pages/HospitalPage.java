package org.practo.pages;

import org.openqa.selenium.JavascriptExecutor;
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

    // TC11 to TC15 FindBy Elements
    @FindBy(xpath = "//h2[contains(@class,'line-1')]")
    private List<WebElement> hospitalNamesForSearchResultsElements;

    @FindBy(xpath = "//h2[contains(@class,'line-1')]/ancestor::li")
    private List<WebElement> hospitalCardsForSearchResultsElements;

    @FindBy(xpath = "(//h2[contains(@class,'line-1')]/parent::a)[1]")
    private WebElement firstHospitalResultLinkElement;

    @FindBy(xpath = "//button[@class='c-book-cta' and text()='Book Hospital Visit']")
    private WebElement bookHospitalVisitButtonElement;

    @FindBy(xpath = "//div[@data-qa-id='no_results']")
    private WebElement noResultsMessageElement;

    // Existing Actions
    public void clickOpen24x7Filter() {
        open247Filter.click();
    }

    public void clickFirstHospital() {
        firstHospitalCard.click();
    }

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

    // TC11 to TC15 Data Retrieval Methods
    public List<WebElement> getHospitalNamesForSearchResultsElements() {
        return hospitalNamesForSearchResultsElements;
    }

    public List<WebElement> getHospitalCardsForSearchResultsElements() {
        return hospitalCardsForSearchResultsElements;
    }

    public WebElement getFirstHospitalResultLink() {
        return firstHospitalResultLinkElement;
    }

    public WebElement getBookHospitalVisitButton() {
        return bookHospitalVisitButtonElement;
    }

    public WebElement getNoResultsMessage() {
        return noResultsMessageElement;
    }

    public WebElement getHospitalCardFromHospitalName(WebElement hospitalName) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (WebElement) js.executeScript(
                "return arguments[0].closest('li');",
                hospitalName
        );
    }

    public boolean isHospitalOpen24x7(WebElement hospitalCard) {
        String cardText = hospitalCard.getText();
        return cardText != null && cardText.contains("Open 24x7");
    }

    public boolean hasHospitalRating(WebElement hospitalCard) {
        String ratingText = getHospitalRatingText(hospitalCard);
        return ratingText != null && !ratingText.trim().isEmpty();
    }

    public String getHospitalRatingText(WebElement hospitalCard) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object ratingText = js.executeScript(
                    "var rating = arguments[0].querySelector('.c-feedback .u-bold');" +
                            "return rating ? rating.textContent.trim() : '';",
                    hospitalCard
            );

            if (ratingText == null) {
                return "";
            }

            return ratingText.toString().trim();

        } catch (Exception e) {
            return "";
        }
    }

    public double getHospitalRatingValue(WebElement hospitalCard) {
        return Double.parseDouble(getHospitalRatingText(hospitalCard));
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