package seleniumTest.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumTest.base.BasePage;

import java.time.Duration;
import java.util.List;

public class SubjectPage extends BasePage {

    public SubjectPage(WebDriver driver) {
        super(driver);
    }

    // ---------- LOCATORS ----------

    // Page load indicator
    private static final By SUBJECT_CONTENT =
            By.xpath("//*[contains(text(),'Progress')]");

    // Chapter accordion
    private static final String CHAPTER_ACCORDION =
            "//button[.//h3[normalize-space()='%s']]";

    // Expanded panel
    private static final String EXPANDED_PANEL =
            CHAPTER_ACCORDION + "/following-sibling::*[1]";

    // Sub-topic buttons (appear after delay)
    private static final String SUB_TOPIC_BUTTONS =
            EXPANDED_PANEL + "//button[.//span]";

    // Mark as Read button (robust locator)
    private static final By MARK_AS_READ_BUTTON =
            By.xpath("//button[contains(.,'Mark as Read')]");

    // Learning Progress title
    private static final By LEARNING_PROGRESS_TITLE =
            By.xpath("//h2[normalize-space()='Learning Progress']");

    // ---------- ACTIONS ----------

    public void waitForSubjectToLoad() {
        waitForToast(SUBJECT_CONTENT, 15);
    }

    public void openChapter(String chapterName) {
        By chapter = By.xpath(String.format(CHAPTER_ACCORDION, chapterName));
        scrollIntoView(chapter);
        jsClick(chapter);

        // React-safe wait (presence, not visibility)
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(String.format(EXPANDED_PANEL, chapterName))
        ));
    }

    public void closeChapter(String chapterName) {
        By chapter = By.xpath(String.format(CHAPTER_ACCORDION, chapterName));
        scrollIntoView(chapter);
        jsClick(chapter);
    }

    /**
     * Wait up to 10 seconds for sub-topics to load
     */
    public List<WebElement> waitForSubTopics(String chapterName) {

        By subTopics = By.xpath(String.format(SUB_TOPIC_BUTTONS, chapterName));

        WebDriverWait tenSecondWait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        tenSecondWait.until(
                ExpectedConditions.presenceOfElementLocated(subTopics)
        );

        return driver.findElements(subTopics);
    }

    /**
     * Wait, scroll and mark sub-topic as read (React-safe)
     */
    public void waitAndMarkAsRead() {

        WebDriverWait tenSecondWait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement markBtn = tenSecondWait.until(
                ExpectedConditions.presenceOfElementLocated(MARK_AS_READ_BUTTON)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", markBtn);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", markBtn);
    }

    public boolean isLearningProgressVisible() {
        return waitForToast(LEARNING_PROGRESS_TITLE, 5);
    }
}
