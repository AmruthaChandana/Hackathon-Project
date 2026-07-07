package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.practo.pages.HomePage;
import org.practo.pages.HospitalPage;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CommonCode {

    private final WebDriver driver;
    private final Properties prop;

    public CommonCode(
            WebDriver driver,
            WebDriverWait wait,
            Properties prop) {

        this.driver = driver;
        this.prop = prop;
    }

    private void loadSheet(
            String sheetNameProperty) {

        ExcelUtils.loadExcel(
                prop.getProperty("excelPath"),
                prop.getProperty(sheetNameProperty));
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


    private int getExplicitWaitTime() {

        try {

            return Integer.parseInt(
                    ConfigReader.getProperty(
                            "explicitWait"));

        } catch (Exception e) {

            return 30;
        }
    }

    private WebDriverWait getWait() {

        return new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        getExplicitWaitTime()));
    }

    // =====================================================
    // BY LOCATOR WAIT METHODS
    // =====================================================

    public WebElement waitForVisible(
            By locator) {

        return getWait().until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                locator));
    }

    public WebElement waitForClickable(
            By locator) {

        return getWait().until(
                ExpectedConditions
                        .elementToBeClickable(
                                locator));
    }


    public WebElement waitForRefreshedClickable(
            By locator) {

        return getWait().until(
                ExpectedConditions.refreshed(
                        ExpectedConditions
                                .elementToBeClickable(
                                        locator)));
    }


    public WebElement waitForVisible(
            WebElement element) {

        return getWait().until(
                ExpectedConditions
                        .visibilityOf(
                                element));
    }

    public WebElement waitForClickable(
            WebElement element) {

        return getWait().until(
                ExpectedConditions
                        .elementToBeClickable(
                                element));
    }

    // =====================================================
    // URL / WINDOW / FRAME METHODS
    // =====================================================

    public boolean waitForUrlContains(
            String partialUrl) {

        return getWait().until(
                ExpectedConditions
                        .urlContains(
                                partialUrl));
    }

    public boolean waitForUrlToBeChanged(
            String oldUrl) {

        return getWait().until(
                ExpectedConditions.not(
                        ExpectedConditions
                                .urlToBe(
                                        oldUrl)));
    }

    public boolean waitForNumberOfWindows(
            int numberOfWindows) {

        return getWait().until(
                ExpectedConditions
                        .numberOfWindowsToBe(
                                numberOfWindows));
    }

    public void waitForFrameAndSwitchToIt(
            WebElement iframeElement) {

        getWait().until(
                ExpectedConditions
                        .frameToBeAvailableAndSwitchToIt(
                                iframeElement));
    }

    public <T> T waitUntil(
            ExpectedCondition<T> condition) {

        return getWait().until(
                condition);
    }

    // =====================================================
    // CLICK METHODS
    // =====================================================

    public void clickUsingJS(
            WebElement element) {

        waitForVisible(element);

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                element);
    }

    public void clickUsingJS(
            By locator) {

        WebElement element =
                waitForVisible(locator);

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                element);
    }

    public void safeClick(
            WebElement element) {

        try {

            waitForClickable(
                    element)
                    .click();

        } catch (Exception e) {

            clickUsingJS(element);
        }
    }

    public void safeClick(
            By locator) {

        try {

            waitForRefreshedClickable(
                    locator)
                    .click();

        } catch (
                StaleElementReferenceException e) {

            waitForRefreshedClickable(
                    locator)
                    .click();

        } catch (Exception e) {

            clickUsingJS(locator);
        }
    }

    public void click(
            By locator) {

        safeClick(locator);
    }

    public void click(
            WebElement element) {

        safeClick(element);
    }

    // =====================================================
    // TEXT METHODS
    // =====================================================

    public String getTextUsingJS(
            WebElement element) {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        return (String) js.executeScript(
                "return arguments[0].innerText;",
                element);
    }

    public String getVisibleTextWithJsFallback(
            WebElement element) {

        String text;

        try {

            text = waitForVisible(
                    element)
                    .getText();

        } catch (Exception e) {

            text = "";
        }

        if (text == null
                || text.trim().isEmpty()) {

            text = getTextUsingJS(
                    element);
        }

        return text;
    }


    public void scrollDown() {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "window.scrollBy(0,700)");
    }

    public void scrollToBottom() {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "window.scrollTo(0, document.body.scrollHeight)");
    }


    // =====================================================
    // HOSPITAL SEARCH METHODS
    // =====================================================

    public void searchHospital(
            HomePage homePage,
            String location,
            String searchKeyword) {

        homePage.enterHospitalLocation(
                location);

        homePage.triggerHospitalLocationSuggestion(
                location);

        click(
                homePage.locationOption(
                        location));

        homePage.enterHospitalSearchKeyword(
                searchKeyword);

        click(
                homePage.searchOption(
                        searchKeyword));
    }

    public void searchHospitalUsingContains(
            HomePage homePage,
            String location,
            String searchKeyword) {

        homePage.enterHospitalLocation(
                location);

        homePage.triggerHospitalLocationSuggestion(
                location);

        click(
                homePage.locationOption(
                        location));

        homePage.enterHospitalSearchKeyword(
                searchKeyword);

        click(
                homePage.searchOptionContains(
                        searchKeyword));
    }

    public void waitForHospitalSearchResults(
            HospitalPage hospitalPage) {

        waitUntil(
                driver ->
                        hospitalPage
                                .getHospitalNamesForSearchResultsElements()
                                != null
                                &&
                                hospitalPage
                                        .getHospitalNamesForSearchResultsElements()
                                        .size() > 0
        );
    }

    public boolean waitForHospitalResultCountToIncrease(
            HospitalPage hospitalPage,
            int previousCount) {

        try {

            return waitUntil(
                    driver ->
                            hospitalPage
                                    .getHospitalNamesForSearchResultsElements()
                                    .size()
                                    > previousCount);

        } catch (
                TimeoutException e) {

            return false;
        }
    }

    // =====================================================
    // WINDOW METHODS
    // =====================================================

    public void switchToNewWindow(
            String parentWindow) {

        Set<String> windowHandles =
                driver.getWindowHandles();

        for (String window : windowHandles) {

            if (!window.equals(
                    parentWindow)) {

                driver.switchTo()
                        .window(window);

                break;
            }
        }
    }

    public boolean switchToNewWindowIfAvailable(
            String parentWindow) {

        try {

            waitForNumberOfWindows(2);

            switchToNewWindow(
                    parentWindow);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    // =====================================================
    // BROWSER INFO METHODS
    // =====================================================

    public String getPageTitle() {

        return driver.getTitle();
    }

    public String getCurrentUrl() {

        return driver.getCurrentUrl();
    }
}