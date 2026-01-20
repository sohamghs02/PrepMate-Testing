package StepDefinition;

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

public class SignUp {

    WebDriver driver;
    WebDriverWait wait;

    @Given("User is in the SignUpPage")
    public void user_is_in_the_sign_up_page() {
        driver = new EdgeDriver();
        driver.get("https://prep-mate-full-stack-alpha.vercel.app/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @When("User clicks on Signup Button")
    public void user_clicks_on_signup_button() {
        driver.findElement(By.xpath("//*[@id='root']/div/nav/div/div/a[3]/button")).click();
    }

    @When("User enters {string}, {string}, {string}, {string}, {string} and {string}")
    public void user_enters_details(String name,
                                    String email,
                                    String college,
                                    String year,
                                    String pass,
                                    String confirmP) {

        driver.findElement(By.xpath("//*[@id='root']/div/main/div/div/div[2]/div/form/div[1]/div[1]/input"))
                .sendKeys(name);

        driver.findElement(By.xpath("//*[@id='root']/div/main/div/div/div[2]/div/form/div[1]/div[2]/input"))
                .sendKeys(email);

        driver.findElement(By.xpath("//*[@id='root']/div/main/div/div/div[2]/div/form/div[1]/div[3]/input"))
                .sendKeys(college);

        driver.findElement(By.xpath("//*[@id='root']/div/main/div/div/div[2]/div/form/div[1]/div[4]/input"))
                .sendKeys(year);

        driver.findElement(By.xpath("//*[@id='root']/div/main/div/div/div[2]/div/form/div[1]/div[5]/input"))
                .sendKeys(pass);

        driver.findElement(By.xpath("//*[@id='root']/div/main/div/div/div[2]/div/form/div[1]/div[6]/input"))
                .sendKeys(confirmP);
    }

    @When("User clicks on Sign Up")
    public void user_clicks_on_sign_up() {
        driver.findElement(By.xpath("//*[@id='root']/div/main/div/div/div[2]/div/form/div[2]/button")).click();
    }

    // POSITIVE ASSERTION
    @Then("User is redirected to the Dashboard Section")
    public void user_is_redirected_to_the_dashboard_section() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(driver ->
                driver.getCurrentUrl().contains("/dashboard")
        );

        Assert.assertEquals(
                "https://prep-mate-full-stack-alpha.vercel.app/dashboard",
                driver.getCurrentUrl()
        );
    }

    // NEGATIVE ASSERTION
    @Then("Error message should be displayed")
    public void error_message_should_be_displayed() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String actualMessage = wait.until(driver ->
                driver.findElement(By.xpath("//*[@id='root']/div/main/div/div/div[2]/div/form/p"))
                        .getText()
        );

        String expectedMessage = "User already exists";

        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}