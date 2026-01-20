package StepDefinition.Hooks;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import StepDefinition.admin.subjectAdd;

public class hooks {

    @After
    public void afterScenario(Scenario scenario) {

        if (subjectAdd.driver != null) {

            // TAKE SCREENSHOT IF SCENARIO FAILS
            if (scenario.isFailed()) {
                byte[] screenshot =
                        ((TakesScreenshot) subjectAdd.driver)
                                .getScreenshotAs(OutputType.BYTES);

                scenario.attach(
                        screenshot,
                        "image/png",
                        scenario.getName()
                );
            }

            // CLOSE BROWSER ALWAYS
            subjectAdd.driver.quit();
        }
    }
}
