package org.practo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ExcelUtils;
import utilities.WaitUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CommonCode {
    private final WebDriver driver;
    private final Properties prop;

    public CommonCode(WebDriver driver, WebDriverWait wait, Properties prop) {
        this.driver = driver;
        this.prop = prop;
    }

    // Application methods
    public void openApplication() {
        driver.get(prop.getProperty("url"));
    }

    // Excel sheet loader methods
    private void loadSheet(String sheetNameProperty) {
        ExcelUtils.loadExcel(prop.getProperty("excelPath"), prop.getProperty(sheetNameProperty));
    }

    public void loadMedicineSheet() {
        loadSheet("medicineSheetName");
    }

    public void loadHospitalSheet() {
        loadSheet("hospitalSheetName");
    }

    public void loadCorporateSheet() {
        loadSheet("corporateSheetName");
    }

    public Map<String, String> getHospitalTestData(String testCaseId, String... columnNames) {
        loadHospitalSheet();
        Map<String, String> rowData = new HashMap<>();
        rowData.put("TestCaseID", testCaseId);
        for (String columnName : columnNames) {
            rowData.put(columnName, ExcelUtils.getCellData(testCaseId, columnName));
        }
        return rowData;
    }

    // By locator based methods
    public WebElement waitForVisible(By locator) {
        return WaitUtils.waitForVisible(driver, locator);
    }

    public WebElement waitForClickable(By locator) {
        return WaitUtils.waitForClickable(driver, locator);
    }

    public void click(By locator) {
        WaitUtils.safeClick(driver, locator);
    }

    public void type(By locator, String value) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(value);
    }

    public String getText(By locator) {
        return waitForVisible(locator).getText();
    }

    public boolean isDisplayed(By locator) {
        return WaitUtils.isElementDisplayed(driver, locator);
    }

    public boolean isElementEnabled(By locator) {
        return WaitUtils.isElementEnabled(driver, locator);
    }

    public void pressEnter(By locator) {
        waitForVisible(locator).sendKeys(Keys.ENTER);
    }

    public void clickUsingJS(By locator) {
        WaitUtils.clickUsingJS(driver, locator);
    }

    // WebElement based methods
    public WebElement waitForVisible(WebElement element) {
        return WaitUtils.waitForVisible(driver, element);
    }

    public WebElement waitForClickable(WebElement element) {
        return WaitUtils.waitForClickable(driver, element);
    }

    public void click(WebElement element) {
        WaitUtils.safeClick(driver, element);
    }

    public void type(WebElement element, String value) {
        waitForVisible(element);
        element.clear();
        element.sendKeys(value);
    }

    public String getText(WebElement element) {
        return waitForVisible(element).getText();
    }

    public boolean isDisplayed(WebElement element) {
        return WaitUtils.isElementDisplayed(driver, element);
    }

    public boolean isElementEnabled(WebElement element) {
        return WaitUtils.isElementEnabled(driver, element);
    }

    public void pressEnter(WebElement element) {
        waitForVisible(element).sendKeys(Keys.ENTER);
    }

    public void clickUsingJS(WebElement element) {
        WaitUtils.clickUsingJS(driver, element);
    }

    public String getTextUsingJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].innerText;", element);
    }

    public String getVisibleTextWithJsFallback(WebElement element) {
        String text;
        try {
            text = waitForVisible(element).getText();
        } catch (Exception e) {
            text = "";
        }
        if (text == null || text.trim().isEmpty()) {
            text = getTextUsingJS(element);
        }
        return text;
    }

    // Scroll methods
    public void scrollDown() {
        WaitUtils.scrollDown(driver);
    }

    public void scrollUp() {
        WaitUtils.scrollUp(driver);
    }

    public void scrollToElement(WebElement element) {
        WaitUtils.scrollToElement(driver, element);
    }

    public void scrollToTop() {
        WaitUtils.scrollToTop(driver);
    }

    public void scrollToBottom() {
        WaitUtils.scrollToBottom(driver);
    }

    // Common hospital search methods
    public void searchHospital(HomePage homePage, String location, String searchKeyword) {
        homePage.enterHospitalLocation(location);
        homePage.triggerHospitalLocationSuggestion(location);
        click(homePage.locationOption(location));
        homePage.enterHospitalSearchKeyword(searchKeyword);
        click(homePage.searchOption(searchKeyword));
    }

    public void searchHospitalUsingContains(HomePage homePage, String location, String searchKeyword) {
        homePage.enterHospitalLocation(location);
        homePage.triggerHospitalLocationSuggestion(location);
        click(homePage.locationOption(location));
        homePage.enterHospitalSearchKeyword(searchKeyword);
        click(homePage.searchOptionContains(searchKeyword));
    }

    public void waitForHospitalSearchResults(HospitalPage hospitalPage) {
        WaitUtils.waitUntil(driver, driver ->
                hospitalPage.getHospitalNamesForSearchResultsElements() != null
                        && hospitalPage.getHospitalNamesForSearchResultsElements().size() > 0
        );
    }

    public boolean waitForHospitalResultCountToIncrease(HospitalPage hospitalPage, int previousCount) {
        try {
            return WaitUtils.waitUntil(driver,
                    driver -> hospitalPage.getHospitalNamesForSearchResultsElements().size() > previousCount
            );
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Window methods
    public void switchToNewWindow(String parentWindow) {
        Set<String> windowHandles = driver.getWindowHandles();
        for (String window : windowHandles) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }

    public boolean switchToNewWindowIfAvailable(String parentWindow) {
        try {
            WaitUtils.waitForNumberOfWindows(driver, 2);
            switchToNewWindow(parentWindow);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForUrlToChange(String previousPageUrl) {
        WaitUtils.waitForUrlToBeChanged(driver, previousPageUrl);
    }

    // Browser info methods
    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}