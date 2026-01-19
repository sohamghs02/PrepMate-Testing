package seleniumTest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import seleniumTest.base.BasePage;

public class DashboardPage extends BasePage {

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    // Dashboard title
    private static final By DASHBOARD_TITLE =
            By.xpath("//h1[normalize-space()='Dashboard']");

    /**
     * Clickable SUBJECT CARD HEADER by visible title text
     * Example: "Operating Systems", "Cucumber"
     */
    private static final String SUBJECT_CARD_HEADER =
            "//div[@data-slot='card-header' and .//div[@data-slot='card-title' " +
                    "and contains(normalize-space(), '%s')]]";

    public boolean isDashboardLoaded() {
        return waitForToast(DASHBOARD_TITLE, 10);
    }

    /**
     * Click subject safely
     */
    public void clickSubject(String subjectName) {
        By subject = By.xpath(String.format(SUBJECT_CARD_HEADER, subjectName));
        click(subject);
    }
}
