package org.practo.pages;

import org.openqa.selenium.By;

public class HospitalPage {

    public By hospitalCards = By.xpath("//div[contains(@class,'listing') or contains(@class,'card')]");
    public By hospitalNames = By.xpath("//h2 | //h3 | //div[contains(@class,'name')]");
    public By hospitalRatings = By.xpath("//*[contains(@class,'rating')]");
    public By open247Filter = By.xpath("//*[contains(text(),'Open 24') or contains(text(),'24/7') or contains(text(),'Open now')]");
    public By firstHospitalCard = By.xpath("(//div[contains(@class,'listing') or contains(@class,'card')])[1]");
    public By addressSection = By.xpath("//*[contains(text(),'Address')]/following::*[1] | //*[contains(@class,'address')]");

    //From this position onwards TC11 to TC15 variables are included
    public By hospitalNamesForSearchResults = By.xpath("//h2[contains(@class,'line-1')]");

    public By hospitalCardFromName = By.xpath("./ancestor::li");

    public By open24x7Text = By.xpath(".//span[normalize-space()='Open 24x7']");

    public By ratingText = By.xpath(".//div[contains(@class,'c-feedback')]//span[contains(@class,'u-bold')]");

    public By bookHospitalVisitButton = By.xpath("//button[@class='c-book-cta' and text()='Book Hospital Visit']");

    public By noResultsMessage = By.xpath("//div[@data-qa-id='no_results']");
}