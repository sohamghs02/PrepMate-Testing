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

import java.util.ArrayList;
import java.util.List;

@Listeners(TestListener.class)
public class DashboardFlowTest extends BaseTest {

    @Test
    public void verifyOperatingSystemFlowAndComputerArchitecture() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("test@test.com", "123456");
        loginPage.waitForUrlContains("dashboard", 20);

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(
                dashboardPage.isDashboardLoaded(),
                "Dashboard not loaded after login"
        );

        // 🔹 PART 1: OPERATING SYSTEM (HAS SUB-TOPICS)

        dashboardPage.clickSubject("Operating System");

        SubjectPage subjectPage = new SubjectPage(driver);
        subjectPage.waitForSubjectToLoad();

        String chapter = "Introduction & Basics";

        subjectPage.openChapter(chapter);

        List<WebElement> subTopicElements =
                subjectPage.waitForSubTopics(chapter);

        List<String> subTopicNames = new ArrayList<>();
        for (WebElement subTopicElement : subTopicElements) {
            String text = subTopicElement.getText();
            subTopicNames.add(text);
        }

        System.out.println("Total sub-topics found: " + subTopicNames.size());
        for (String subTopicName : subTopicNames) {

            subjectPage.openChapter(chapter);

            System.out.println("Marking sub-topic: " + subTopicName);

            subjectPage.openSubTopicByName(subTopicName);

            subjectPage.waitAndMarkAsRead();

            subjectPage.closeChapter(chapter);
        }

        Assert.assertTrue(
                subjectPage.isLearningProgressVisible(),
                "Learning Progress not visible after completing Operating System"
        );

        System.out.println("✅ Operating System completed successfully");
        // 🔹 PART 2: COMPUTER ARCHITECTURE (NO SUB-TOPICS)
        driver.navigate().back();

        Assert.assertTrue(
                dashboardPage.isDashboardLoaded(),
                "Dashboard not loaded after navigating back"
        );

        dashboardPage.clickSubject("Computer Architecture");

        SubjectPage computerArchPage = new SubjectPage(driver);
        computerArchPage.waitForSubjectToLoad();

        Assert.assertTrue(
                computerArchPage.isNoSubTopicsMessageVisible(),
                "Expected 'No subtopics available' message not shown"
        );

        System.out.println(
                "✅ Computer Architecture correctly shows no sub-topics message"
        );
    }
}
