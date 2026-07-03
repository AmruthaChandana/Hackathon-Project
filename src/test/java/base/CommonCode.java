package base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.DataProvider;
import utilities.ExcelUtils;

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
        JavascriptExecutor js =
                (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].click();",
                element
        );
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

    public void loadCorporateSheet() {
        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("corporateSheetName")
        );
    }
}