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
}