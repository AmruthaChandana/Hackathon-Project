package org.practo.pages;

import org.openqa.selenium.By;

public class MedicinesPage {

    public By medicineSearchBox = By.xpath("//input[contains(@placeholder,'Search') or contains(@placeholder,'medicine')]");
    public By medicineList = By.xpath("//*[contains(@class,'medicine') or contains(@class,'product') or contains(@class,'card')]");
    public By medicineNames = By.xpath("//*[contains(@class,'name') or contains(@class,'title')]");
    public By addToCartButton = By.xpath("(//button[contains(text(),'Add') or contains(text(),'Cart')])[1]");
    public By cartIcon = By.xpath("//*[contains(text(),'Cart') or contains(@class,'cart')]");
    public By outOfStockText = By.xpath("//*[contains(text(),'Out of Stock') or contains(text(),'Unavailable')]");
    public By disabledAddToCart = By.xpath("//button[contains(text(),'Add') and @disabled]");
}
