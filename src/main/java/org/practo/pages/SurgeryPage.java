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
     * Surgery page DOM from screenshot:
     *
     * <div id="surgery-list">
     *     <section class="SurgeryList-module_wrapper...">
     *         <h2>Treatments offered</h2>
     *         <button>Popular</button>
     *         <div class="SurgeryList-module_grid...">
     *             treatment cards...
     *         </div>
     *     </section>
     * </div>
     */

    private static final String SURGERY_LIST_SECTION =
            "//div[@id='surgery-list']";

    private static final String TREATMENTS_OFFERED_HEADING =
            "//div[@id='surgery-list']//h2[contains(normalize-space(),'Treatments offered')]";

    private static final String POPULAR_TAB =
            "//div[@id='surgery-list']//button[.//span[contains(normalize-space(),'Popular')]]";

    /*
     * Treatment names are visible inside grid cards.
     * This locator captures text nodes from treatment cards under the grid.
     */
    private static final String POPULAR_TREATMENT_NAMES =
            "//div[@id='surgery-list']//div[contains(@class,'SurgeryList-module_grid')]//*[self::div or self::p or self::span][normalize-space()!='']";

    // ==========================
    // Actions
    // ==========================

    public void scrollToSurgeryListSection() {

        try {
            WebElement section =
                    driver.findElement(By.xpath(SURGERY_LIST_SECTION));

            JavascriptExecutor js = (JavascriptExecutor) driver;

            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    section
            );

        } catch (Exception ignored) {
        }
    }

    public void clickPopularTab() {

        try {
            WebElement popular =
                    driver.findElement(By.xpath(POPULAR_TAB));

            JavascriptExecutor js = (JavascriptExecutor) driver;

            js.executeScript(
                    "arguments[0].click();",
                    popular
            );

        } catch (Exception ignored) {
        }
    }

    // ==========================
    // Validation Methods
    // ==========================

    public boolean isSurgeryListSectionDisplayed() {

        try {
            return driver.findElement(
                    By.xpath(SURGERY_LIST_SECTION)
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTreatmentsOfferedDisplayed() {

        try {
            return driver.findElement(
                    By.xpath(TREATMENTS_OFFERED_HEADING)
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    // ==========================
    // Data Extraction Methods
    // ==========================

    public int getPopularTreatmentCount() {

        try {
            return driver.findElements(
                    By.xpath(POPULAR_TREATMENT_NAMES)
            ).size();

        } catch (Exception e) {
            return 0;
        }
    }

    public List<String> getPopularTreatments() {

        List<String> treatments = new ArrayList<>();

        List<WebElement> treatmentElements =
                driver.findElements(By.xpath(POPULAR_TREATMENT_NAMES));

        for (WebElement treatmentElement : treatmentElements) {

            try {
                String treatmentName =
                        treatmentElement.getText().trim();

                if (!treatmentName.isEmpty()
                        && treatmentName.length() <= 40
                        && !treatmentName.equalsIgnoreCase("Popular")
                        && !treatmentName.equalsIgnoreCase("Treatments offered")) {

                    treatments.add(treatmentName);
                }

            } catch (Exception ignored) {
            }
        }

        return treatments.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}