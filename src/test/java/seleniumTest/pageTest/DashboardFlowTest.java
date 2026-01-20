package seleniumTest.pageTest;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import seleniumTest.BaseTest;
import seleniumTest.listeners.TestListener;
import seleniumTest.pages.DashboardPage;
import seleniumTest.pages.LoginPage;
import seleniumTest.pages.SubjectPage;

import java.util.List;

@Listeners(TestListener.class)
public class DashboardFlowTest extends BaseTest {

    @Test
    public void verifyOperatingSystemFlow() {

        // 🔹 Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("test@test.com", "123456");
        loginPage.waitForUrlContains("dashboard", 20);

        // 🔹 Dashboard
        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(
                dashboardPage.isDashboardLoaded(),
                "Dashboard not loaded after login"
        );

        dashboardPage.clickSubject("Operating System");

        // 🔹 Subject Page
        SubjectPage subjectPage = new SubjectPage(driver);
        subjectPage.waitForSubjectToLoad();

        String chapter = "Introduction & Basics";

        // 🔹 Open chapter
        subjectPage.openChapter(chapter);

        // 🔹 Wait for sub-topics (up to 10s)
        List<WebElement> subTopics =
                subjectPage.waitForSubTopics(chapter);

        System.out.println("Total sub-topics found: " + subTopics.size());

        // 🔹 Mark ALL sub-topics
        for (int i = 0; i < subTopics.size(); i++) {

            // React-safe re-open
            subjectPage.openChapter(chapter);
            subTopics = subjectPage.waitForSubTopics(chapter);

            WebElement subTopic = subTopics.get(i);
            String subTopicName = subTopic.getText();

            System.out.println("Marking sub-topic: " + subTopicName);

            subTopic.click();

            // ✅ Correct marking logic
            subjectPage.waitAndMarkAsRead();

            subjectPage.closeChapter(chapter);
        }

        // 🔹 Final assertion
        Assert.assertTrue(
                subjectPage.isLearningProgressVisible(),
                "Learning Progress not visible after completing topic"
        );

        System.out.println("✅ All sub-topics marked and Learning Progress verified");
    }
}
