package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import utilities.ConfigReader;
import utilities.ExcelUtils;
import utilities.ScreenshotUtil;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;

public class BaseTest {
    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Properties prop;

    @BeforeClass
    public void loadConfiguration() {
        prop = ConfigReader.initProperties();
        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("sheetName")
        );
    }

    @BeforeMethod
    public void setup() {
        String browser = prop.getProperty("browser");
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new RuntimeException(
                    "Invalid browser name in config.properties: " + browser);
        }
        driver.manage().window().maximize();
        int implicitWait =
                Integer.parseInt(prop.getProperty("implicitWait"));
        int explicitWait =
                Integer.parseInt(prop.getProperty("explicitWait"));
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(implicitWait));
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(explicitWait)
        );
    }

    public void openApplication() {
        driver.get(prop.getProperty("url"));
    }

    // Medicine Sheet Loader
    public void loadMedicineSheet() {
        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("medicineSheetName")
        );
    }

    // Hospital Sheet Loader
       public void loadHospitalSheet() {
        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("hospitalSheetName")
        );
    }

    public WebElement waitForVisible(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(locator));
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

    public void scrollDown() {
        JavascriptExecutor js =
                (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,700)");
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js =
                (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].scrollIntoView(true);",
                element
        );
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

    public void switchToNewWindow(String parentWindow) {
        Set<String> windowHandles = driver.getWindowHandles();
        for (String window : windowHandles) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @DataProvider(name = "excelData")
    public Object[][] getExcelData() {
        return ExcelUtils.getAllTestData();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.captureScreenshot(
                    driver,
                    result.getName()
            );
        }
        if (driver != null) {
            driver.quit();
        }
    }
}