package org.practo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HospitalDetailsPage {

    private WebDriver driver;

    public HospitalDetailsPage() {
    }

    public HospitalDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//p[@data-qa-id='address_body']")
    private WebElement addressBodyElement;

    public WebElement getAddressBodyElement() {
        return addressBodyElement;
    }

    public String cleanAddressText(String addressText) {
        if (addressText == null) {
            return "";
        }

        return addressText
                .replace("Get Directions", "")
                .replace("&amp;", "&")
                .trim();
    }
}