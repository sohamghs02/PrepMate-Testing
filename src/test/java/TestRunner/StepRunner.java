package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/feature/admin",
        glue = "StepDefinition",
        plugin = {
                "pretty",
                "html:target/cucumber-report.html"
        }
)
public class StepRunner extends AbstractTestNGCucumberTests {
}
