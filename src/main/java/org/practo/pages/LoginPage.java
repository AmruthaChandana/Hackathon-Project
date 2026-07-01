package org.practo.pages;

import org.openqa.selenium.By;

public class LoginPage {

    public By mobileNumberField = By.xpath(
            "//input[contains(@placeholder,'Mobile') or contains(@name,'mobile')]"
    );

    public By passwordField = By.xpath(
            "//input[@type='password']"
    );

    public By loginSubmitButton = By.xpath(
            "//button[contains(text(),'Login') or contains(text(),'Continue')]"
    );

    // Header username
    public By headerUserName = By.xpath(
            "//span[contains(@class,'user_info_top')]"
    );

    // ✅ FINAL DOWN ARROW LOCATOR (your XPath)
    public By profileDownArrow = By.xpath(
            "//span[contains(@class,'user_info_top')]/following-sibling::span[contains(@class,'downarrow')]"
    );

    // Dropdown panel (after click)
    public By profileDropdownPanel = By.xpath(
            "//div[contains(@class,'nav-dropdown')]"
    );

    public By errorMessage = By.xpath(
            "//*[contains(text(),'Invalid') or contains(text(),'incorrect') or contains(text(),'wrong') or contains(text(),'Try again')]"
    );

}