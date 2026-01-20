package TestRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/feature/01_signup.feature", "src/test/resources/feature/02_login.feature","src/test/resources/feature/admin/03_subjectAdd.feature", "src/test/resources/feature/admin/04_subtopicAdd.feature"},
        glue = {"StepDefinition", "StepDefinition/admin"},
        plugin = {
                "pretty",
                "html:target/cucumber-report.html"
        }
)
public class StepRunner {
}
