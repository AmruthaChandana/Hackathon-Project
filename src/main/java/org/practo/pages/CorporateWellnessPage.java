package org.practo.pages;

import org.openqa.selenium.By;

public class CorporateWellnessPage {

    public By nameField = By.xpath("//input[contains(@placeholder,'Name')]");
    public By organizationField = By.xpath("//input[contains(@placeholder,'Organization') or contains(@placeholder,'Company')]");
    public By emailField = By.xpath("//input[contains(@placeholder,'Email')]");
    public By mobileField = By.xpath("//input[contains(@placeholder,'Mobile') or contains(@placeholder,'Phone')]");
    public By submitButton = By.xpath("//button[contains(text(),'Schedule') or contains(text(),'Submit') or contains(text(),'Login')]");
    public By validationMessage = By.xpath("//*[contains(text(),'valid') or contains(text(),'Invalid') or contains(text(),'required')]");
}