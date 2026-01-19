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

    // Chapter accordion button
    private static final String CHAPTER_ACCORDION =
            "//button[.//h3[normalize-space()='%s']]";

    /**
     * Sub-topic buttons:
     * - located AFTER the chapter button
     * - React-safe (ignores wrapper divs)
     */
    private static final String SUB_TOPIC_BUTTONS =
            "//button[.//h3[normalize-space()='%s']]" +
                    "/following::button[.//span]";

    // Mark as Read button
    private static final By MARK_AS_READ_BUTTON =
            By.xpath("//button[contains(.,'Mark as Read')]");

    // Learning Progress title
    private static final By LEARNING_PROGRESS_TITLE =
            By.xpath("//h2[normalize-space()='Learning Progress']");

    // Empty subject message
    private static final By NO_SUBTOPICS_MESSAGE =
            By.xpath("//*[contains(text(),'No subtopics available')]");

    // ---------- ACTIONS ----------

    /** Wait until subject page is loaded */
    public void waitForSubjectToLoad() {
        waitForToast(SUBJECT_CONTENT, 15);
    }

    /** Open chapter accordion */
    public void openChapter(String chapterName) {
        By chapter = By.xpath(String.format(CHAPTER_ACCORDION, chapterName));
        scrollIntoView(chapter);
        jsClick(chapter);
    }

    /** Close chapter accordion */
    public void closeChapter(String chapterName) {
        By chapter = By.xpath(String.format(CHAPTER_ACCORDION, chapterName));
        scrollIntoView(chapter);
        jsClick(chapter);
    }

    /**
     * Wait up to 15s for sub-topics to appear (React-safe)
     */
    public List<WebElement> waitForSubTopics(String chapterName) {

        By subTopics = By.xpath(String.format(SUB_TOPIC_BUTTONS, chapterName));

        WebDriverWait wait15 =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        // Count-based wait (best for React lists)
        wait15.until(driver -> driver.findElements(subTopics).size() > 0);

        return driver.findElements(subTopics);
    }

    /**
     * Wait, scroll and click "Mark as Read"
     */
    public void waitAndMarkAsRead() {

        WebDriverWait wait10 =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement markBtn = wait10.until(
                driver -> driver.findElement(MARK_AS_READ_BUTTON)
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        markBtn
                );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", markBtn);
    }

    /** Verify Learning Progress section */
    public boolean isLearningProgressVisible() {
        return waitForToast(LEARNING_PROGRESS_TITLE, 5);
    }

    /** Verify empty subject state */
    public boolean isNoSubTopicsMessageVisible() {
        return waitForToast(NO_SUBTOPICS_MESSAGE, 10);
    }
    public void openSubTopicByName(String subTopicName) {

        By subTopic =
                By.xpath("//button[.//span[normalize-space()='" + subTopicName + "']]");

        WebDriverWait wait10 =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement freshSubTopic = wait10.until(
                ExpectedConditions.presenceOfElementLocated(subTopic)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});",
                        freshSubTopic);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", freshSubTopic);
    }

}
