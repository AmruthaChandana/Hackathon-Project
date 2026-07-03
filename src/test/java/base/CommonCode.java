package base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.practo.pages.HomePage;
import org.practo.pages.HospitalPage;
import org.testng.annotations.DataProvider;
import utilities.ExcelUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommonCode extends BaseTest {

    // Application Methods
    public void openApplication() {
        driver.get(prop.getProperty("url"));
    }

    // Excel Sheet Loaders
    public void loadMedicineSheet() {
        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("medicineSheetName")
        );
    }

    public void loadHospitalSheet() {
        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("hospitalSheetName")
        );
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

    // By Locator Based Methods
    public WebElement waitForVisible(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
    }

    public void click(By locator) {
        waitForClickable(locator).click();
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
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementEnabled(By locator) {
        try {
            return waitForVisible(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public void pressEnter(By locator) {
        waitForVisible(locator).sendKeys(Keys.ENTER);
    }

    public void clickUsingJS(By locator) {
        WebElement element = waitForVisible(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    // WebElement Based Methods
    public WebElement waitForVisible(WebElement element) {
        return wait.until(
                ExpectedConditions.visibilityOf(element)
        );
    }

    public WebElement waitForClickable(WebElement element) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(element)
        );
    }

    public void click(WebElement element) {
        waitForClickable(element).click();
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
        try {
            return waitForVisible(element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementEnabled(WebElement element) {
        try {
            return waitForVisible(element).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public void pressEnter(WebElement element) {
        waitForVisible(element).sendKeys(Keys.ENTER);
    }

    public void clickUsingJS(WebElement element) {
        waitForVisible(element);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public String getTextUsingJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript(
                "return arguments[0].innerText;",
                element
        );
    }

    public String getVisibleTextWithJsFallback(WebElement element) {
        String text = "";

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

    // Scroll Methods
    public void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,700)");
    }

    public void scrollUp() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,-700)");
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    public void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,0)");
    }

    public void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    // Common Hospital Search Actions
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
        wait.until(driver ->
                hospitalPage.getHospitalNamesForSearchResultsElements() != null &&
                        hospitalPage.getHospitalNamesForSearchResultsElements().size() > 0
        );
    }

    public boolean waitForHospitalResultCountToIncrease(HospitalPage hospitalPage, int previousCount) {
        try {
            return wait.until(driver ->
                    hospitalPage.getHospitalNamesForSearchResultsElements().size() > previousCount
            );
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Window Methods
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
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            switchToNewWindow(parentWindow);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForUrlToChange(String previousPageUrl) {
        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlToBe(previousPageUrl)
        ));
    }

    // Browser Info Methods
    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // Excel Data Provider
    @DataProvider(name = "excelData")
    public Object[][] getExcelData() {
        return ExcelUtils.getAllTestData();
    }
}