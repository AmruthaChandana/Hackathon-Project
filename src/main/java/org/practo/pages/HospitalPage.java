package org.practo.pages;

import org.openqa.selenium.By;

public class HospitalPage {

    public By hospitalCards = By.xpath("//div[contains(@class,'listing') or contains(@class,'card')]");
    public By hospitalNames = By.xpath("//h2 | //h3 | //div[contains(@class,'name')]");
    public By hospitalRatings = By.xpath("//*[contains(@class,'rating')]");
    public By open247Filter = By.xpath("//*[contains(text(),'Open 24') or contains(text(),'24/7') or contains(text(),'Open now')]");
    public By firstHospitalCard = By.xpath("(//div[contains(@class,'listing') or contains(@class,'card')])[1]");
    public By addressSection = By.xpath("//*[contains(text(),'Address')]/following::*[1] | //*[contains(@class,'address')]");
}