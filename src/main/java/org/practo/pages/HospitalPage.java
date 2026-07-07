package org.practo.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class HospitalPage {
    private static final Logger logger = LogManager.getLogger(HospitalPage.class);
    private WebDriver driver;
    public HospitalPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Hospital listing elements
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

    // Hospital search result elements
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

    // Hospital details page address element
    @FindBy(xpath = "//p[@data-qa-id='address_body']")
    private WebElement addressBodyElement;

    // Search Result Getters
    public List<WebElement> getHospitalNamesForSearchResultsElements() {
        return hospitalNamesForSearchResultsElements;
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

    // Address Methods
    public WebElement getAddressBodyElement() {
        return addressBodyElement;
    }

    public String cleanAddressText(String addressText) {
        if (addressText == null) {
            return "ADDRESS_NOT_AVAILABLE";
        }
        return addressText
                .replace("Get Directions", "")
                .replace("&amp;amp;amp;", "&")
                .replace("&amp;amp;", "&")
                .trim();
    }

    // Hospital Utility Methods
    public WebElement getHospitalCardFromHospitalName(WebElement hospitalName) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (WebElement) js.executeScript("return arguments[0].closest('li');", hospitalName);
    }

    public boolean isHospitalOpen24x7(WebElement hospitalCard) {
        String cardText = hospitalCard.getText();
        return cardText != null && cardText.contains("Open 24x7");
    }

    public boolean hasHospitalRating(WebElement hospitalCard) {
        return !getHospitalRatingText(hospitalCard).equals("RATING_NOT_AVAILABLE");
    }

    public String getHospitalRatingText(WebElement hospitalCard) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Object ratingText = js.executeScript(
                            "var rating = arguments[0].querySelector('.c-feedback .u-bold');"
                                    + "return rating ? rating.textContent.trim() : null;",
                            hospitalCard
                    );

            if (ratingText == null) {
                logger.warn("Rating not available for hospital card.");
                return "RATING_NOT_AVAILABLE";
            }
            return ratingText.toString().trim();
        } catch (Exception e) {
            logger.error("Exception while fetching hospital rating.", e);
            return "RATING_NOT_AVAILABLE";
        }
    }

    public double getHospitalRatingValue(WebElement hospitalCard) {
        try {
            String ratingText = getHospitalRatingText(hospitalCard);
            return Double.parseDouble(ratingText);
        } catch (Exception e) {
            logger.warn("Unable to convert hospital rating to numeric value.");
            return -1.0;
        }
    }
}