package seleniumTest.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        if (driver != null) {
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        }
    }

    // ---------- CORE HELPERS ----------

    protected WebElement getElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void type(By locator, String text) {
        WebElement el = getElement(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // ---------- WAITS ----------

    protected boolean waitForToast(By locator, int seconds) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            return shortWait
                    .until(ExpectedConditions.visibilityOfElementLocated(locator))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void waitForUrlContains(String value, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.urlContains(value));
    }

    // ---------- SCREENSHOT ----------

    public void takeScreenshot(String testName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(
                "screenshots/" + testName + "_" + System.currentTimeMillis() + ".png"
        );

        try {
            Files.createDirectories(dest.getParentFile().toPath());
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------- SCROLL & JS CLICK (FIXES STICKY HEADER ISSUE) ----------

    protected void scrollIntoView(By locator) {
        WebElement element = getElement(locator);
        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'nearest'});",
                        element
                );
    }

    protected void jsClick(By locator) {
        WebElement element = getElement(locator);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }
}
