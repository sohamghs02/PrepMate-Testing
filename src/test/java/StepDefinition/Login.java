package StepDefinition;

import StepDefinition.Hooks.Hooks;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Login {

    WebDriver driver;
    WebDriverWait wait;

    @Given("User is in the Sign-UpPage")
    public void user_is_in_the_sign_up_page() {
        driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://prep-mate-full-stack-alpha.vercel.app/");

    }

    @When("User clicks on Login Button")
    public void user_clicks_on_login_button() {
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/nav/div/div/a[2]")).click();
    }

    @When("User enters {string} and {string}")
    public void user_enters_and(String string, String string2) {
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(string);
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(string2);
    }

    @When("User Clicks on Submit button")
    public void user_clicks_on_submit_button() {
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/button")).click();
    }

    @Then("System shows {string} and user successfully enters the dashboard section")
    public void system_shows_and_user_successfully_enters_the_dashboard_section(String result) {



        if (result.equals("Dashboard")) {

            wait.until(driver ->
                    driver.getCurrentUrl().contains("/dashboard")
            );

            Assert.assertEquals(
                    "https://prep-mate-full-stack-alpha.vercel.app/dashboard",
                    driver.getCurrentUrl()
            );

        } else if (result.equals("Invalid credentials")) {

            String errorMessage = wait.until(driver ->
                    driver.findElement(By.xpath("//p[contains(text(),'Invalid')]"))
                            .getText()
            );

            Assert.assertEquals("Invalid credentials", errorMessage);
        }
    }



}