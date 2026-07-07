package base;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import utilities.CommonCode;
import utilities.ConfigReader;
import utilities.ExcelUtils;
import utilities.ScreenshotUtil;

import java.time.Duration;
import java.util.Properties;

public class BaseTest {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Properties prop;

    protected CommonCode commonCode;

    private static final Logger logger =
            LogManager.getLogger(BaseTest.class);

    @BeforeClass
    public void loadConfiguration() {

        prop = ConfigReader.initProperties();

        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty("sheetName")
        );

        logger.info("Configuration loaded successfully");
    }

    @BeforeMethod
    public void setup() {

        String browser = prop.getProperty("browser");

        logger.info("Launching browser: " + browser);

        if (browser.equalsIgnoreCase("chrome")) {

            WebDriverManager
                    .chromedriver()
                    .setup();

            driver =
                    new ChromeDriver();

        } else if (browser.equalsIgnoreCase("edge")) {

            WebDriverManager
                    .edgedriver()
                    .setup();

            driver =
                    new EdgeDriver();

        } else {

            logger.error(
                    "Invalid browser name in config.properties: "
                            + browser);

            throw new RuntimeException(
                    "Invalid browser name in config.properties: "
                            + browser);
        }

        driver.manage()
                .window()
                .maximize();

        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));

        int explicitWait = Integer.parseInt(prop.getProperty("explicitWait"));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                                implicitWait));

        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));

        // Open URL from BaseTest
        driver.get(prop.getProperty("url"));

        commonCode = new CommonCode(driver, wait, prop);

        logger.info("Browser setup completed successfully");
    }

    @AfterMethod
    public void tearDown(
            ITestResult result) {

        if (result.getStatus() == ITestResult.SUCCESS) {

            logger.info("Test passed: " + result.getName());
        }

        if (result.getStatus()
                == ITestResult.FAILURE) {

            logger.error("Test failed: "
                            + result.getName());

            logger.error("Failure reason: "
                            + result.getThrowable());

            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }

        if (result.getStatus() == ITestResult.SKIP) {

            logger.warn("Test skipped: " + result.getName());
        }

        if (driver != null) {

            driver.quit();

            logger.info("Browser closed successfully");
        }
    }
}