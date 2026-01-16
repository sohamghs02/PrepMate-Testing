package StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Signup {
    WebDriver driver;
    WebDriverWait wait;
    @Given("User is in the SignUpPage")
    public void user_is_in_the_sign_up_page() {
        driver = new EdgeDriver();
        driver.get("https://prep-mate-full-stack-alpha.vercel.app/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @When("User clicks on Signup Button")
    public void user_clicks_on_signup_button() {

    }

    @When("User enters Test User, testuser@gmail.com, XYZ, {int}, Test@{int} and Test@{int}")
    public void user_enters_test_user_testuser_gmail_com_xyz_test_and_test(Integer int1, Integer int2, Integer int3) {

    }

    @When("User clicks on Sign Up")
    public void user_clicks_on_sign_up() {

    }

    @Then("User is redirected to the Dashboard Section")
    public void user_is_redirected_to_the_dashboard_section() {

    }
}
