package org.practo.pages;

import org.openqa.selenium.By;

public class LabTestsPage {

    public By cityDropdown = By.xpath("//*[contains(text(),'Select City') or contains(@class,'city')]");
    public By topCitiesSection = By.xpath("//*[contains(text(),'Top Cities') or contains(text(),'Popular Cities')]");
    public By topCityNames = By.xpath("//*[contains(text(),'Bangalore') or contains(text(),'Mumbai') or contains(text(),'Delhi') or contains(text(),'Hyderabad') or contains(text(),'Pune')]");
    public By citySearchField = By.xpath("//input[contains(@placeholder,'city') or contains(@placeholder,'City')]");
    public By labSearchField = By.xpath("//input[contains(@placeholder,'Search') or contains(@placeholder,'test')]");
    public By labSearchResults = By.xpath("//*[contains(text(),'Thyroid') or contains(@class,'test')]");
    public By healthPackages = By.xpath("//*[contains(text(),'Health') or contains(text(),'package') or contains(text(),'Package')]");
}