package org.practo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SurgeryPage {

    private WebDriver driver;

    public SurgeryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /*
     * XPath given:
     * Treatments Offered heading:
     * //*[@id="surgery-list"]/section/h2
     */
    private static final String TREATMENTS_OFFERED_HEADING =
            "//*[@id='surgery-list']/section/h2";

    /*
     * XPath given:
     * Popular grid:
     * //*[@id="surgery-list"]/section/div/div[1]
     */
    private static final String POPULAR_GRID =
            "//*[@id='surgery-list']/section/div/div[1]";

    /*
     * Cards inside popular grid.
     * From screenshot, each treatment card is a div with role='button' / tabindex='0'
     */
    private static final String POPULAR_TREATMENT_CARDS =
            "//*[@id='surgery-list']/section/div/div[1]/div[@role='button' or @tabindex='0']";

    public boolean isTreatmentsOfferedDisplayed() {
        try {
            return driver.findElement(By.xpath(TREATMENTS_OFFERED_HEADING)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void scrollToTreatmentsOffered() {
        try {
            WebElement heading =
                    driver.findElement(By.xpath(TREATMENTS_OFFERED_HEADING));

            JavascriptExecutor js =
                    (JavascriptExecutor) driver;

            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    heading
            );

        } catch (Exception ignored) {
        }
    }

    public boolean isPopularGridDisplayed() {
        try {
            return driver.findElement(By.xpath(POPULAR_GRID)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getPopularTreatmentsCount() {
        try {
            return driver.findElements(By.xpath(POPULAR_TREATMENT_CARDS)).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<String> getPopularTreatments() {

        List<String> treatments = new ArrayList<>();

        List<WebElement> treatmentCards =
                driver.findElements(By.xpath(POPULAR_TREATMENT_CARDS));

        for (WebElement card : treatmentCards) {
            try {
                String text = card.getText().trim();

                if (!text.isEmpty()) {
                    treatments.add(text);
                }

            } catch (Exception ignored) {
                // handles stale element safely
            }
        }

        return treatments.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}