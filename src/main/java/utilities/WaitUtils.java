package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WaitUtils {

    private static int getExplicitWaitTime() {
        try {
            return Integer.parseInt(ConfigReader.getProperty("explicitWait"));
        } catch (Exception e) {
            return 30;
        }
    }

    private static WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(getExplicitWaitTime()));
    }

    // By locator wait methods
    public static WebElement waitForVisible(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForPresence(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static boolean waitForInvisibility(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // WebElement wait methods
    public static WebElement waitForVisible(WebDriver driver, WebElement element) {
        return getWait(driver).until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForClickable(WebDriver driver, WebElement element) {
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForRefreshedClickable(WebDriver driver, By locator) {
        return getWait(driver).until(
                ExpectedConditions.refreshed(
                        ExpectedConditions.elementToBeClickable(locator)
                )
        );
    }

    // URL, window and frame wait methods
    public static boolean waitForUrlContains(WebDriver driver, String partialUrl) {
        return getWait(driver).until(ExpectedConditions.urlContains(partialUrl));
    }

    public static boolean waitForUrlToBeChanged(WebDriver driver, String oldUrl) {
        return getWait(driver).until(
                ExpectedConditions.not(
                        ExpectedConditions.urlToBe(oldUrl)
                )
        );
    }

    public static boolean waitForNumberOfWindows(WebDriver driver, int numberOfWindows) {
        return getWait(driver).until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
    }

    public static void waitForFrameAndSwitchToIt(WebDriver driver, WebElement iframeElement) {
        getWait(driver).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeElement));
    }

    // Custom wait method
    public static <T> T waitUntil(WebDriver driver, ExpectedCondition<T> condition) {
        return getWait(driver).until(condition);
    }

    // Page load wait method
    public static void waitForPageLoad(WebDriver driver) {
        getWait(driver).until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    // Click helper methods
    public static void clickWhenReady(WebDriver driver, WebElement element) {
        waitForClickable(driver, element).click();
    }

    public static void clickWhenReady(WebDriver driver, By locator) {
        waitForClickable(driver, locator).click();
    }

    public static void clickUsingJS(WebDriver driver, WebElement element) {
        waitForVisible(driver, element);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public static void clickUsingJS(WebDriver driver, By locator) {
        WebElement element = waitForVisible(driver, locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public static void safeClick(WebDriver driver, WebElement element) {
        try {
            waitForClickable(driver, element).click();
        } catch (Exception e) {
            clickUsingJS(driver, element);
        }
    }

    public static void safeClick(WebDriver driver, By locator) {
        try {
            waitForRefreshedClickable(driver, locator).click();
        } catch (StaleElementReferenceException e) {
            waitForRefreshedClickable(driver, locator).click();
        } catch (Exception e) {
            clickUsingJS(driver, locator);
        }
    }

    // Scroll helper methods
    public static void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    public static void scrollToTop(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,0)");
    }

    public static void scrollToBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void scrollDown(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,700)");
    }

    public static void scrollUp(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,-700)");
    }

    // Boolean helper methods
    public static boolean isElementDisplayed(WebDriver driver, WebElement element) {
        try {
            return waitForVisible(driver, element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElementDisplayed(WebDriver driver, By locator) {
        try {
            return waitForVisible(driver, locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElementEnabled(WebDriver driver, WebElement element) {
        try {
            return waitForVisible(driver, element).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElementEnabled(WebDriver driver, By locator) {
        try {
            return waitForVisible(driver, locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElementPresent(WebDriver driver, By locator) {
        try {
            return driver.findElements(locator).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}