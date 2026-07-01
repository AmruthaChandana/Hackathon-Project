package org.practo.pages;

import org.openqa.selenium.By;

public class HomePage {

    public By loginButton = By.xpath("//a[contains(text(),'Login') or contains(text(),'login')]");
    public By locationBox = By.xpath("//input[contains(@placeholder,'location') or contains(@placeholder,'Location')]");
    public By searchBox = By.xpath("//input[contains(@placeholder,'Search') or contains(@placeholder,'doctor') or contains(@placeholder,'clinic')]");

    public By videoConsultLink = By.xpath("//div[contains(text(),'Video Consult')] | //a[contains(text(),'Video Consult')]");
    public By labTestsLink = By.xpath("//a[contains(text(),'Lab Tests') or contains(text(),'Diagnostics')]");
    public By medicinesLink = By.xpath("//a[contains(text(),'Medicines') or contains(text(),'medicine')]");

    public By corporateWellnessLink = By.xpath("//*[contains(text(),'Corporate Wellness') or contains(text(),'For Corporates')]");

    //From this position onwards TC11 to TC15 variables are included
    public By hospitalLocationBox = By.xpath("//input[@data-qa-id='omni-searchbox-locality']");

    public By hospitalSearchBox = By.xpath("//input[@data-qa-id='omni-searchbox-keyword']");



    public By locationOption(String location) {
        return By.xpath("//div[contains(text(),'" + location + "')]");
    }

    public By searchOption(String searchKeyword) {
        return By.xpath("//div[@data-qa-id='omni-suggestion-main' and text()='" + searchKeyword + "']");
    }

    public By searchOptionContains(String searchKeyword) {
        return By.xpath("//div[@data-qa-id='omni-suggestion-main' and contains(text(),'" + searchKeyword + "')]");
    }
}