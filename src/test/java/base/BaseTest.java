package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utilities.ConfigReader;
import utilities.ExcelUtils;
import utilities.ScreenshotUtil;

import java.time.Duration;
import java.util.Properties;

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
                    "Invalid browser name in config.properties: " + browser
            );
        }

        driver.manage().window().maximize();
        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
        int explicitWait = Integer.parseInt(prop.getProperty("explicitWait"));
        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(implicitWait));
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(explicitWait)
        );
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