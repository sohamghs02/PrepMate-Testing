package StepDefinition.admin;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;

import java.time.Duration;

public class subtopicAdd {

    WebDriver driver;
    WebDriverWait wait;

    @Given("User is in the Home Page Section")
    public void user_is_in_the_home_page_section() {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get("https://prep-mate-full-stack-alpha.vercel.app/");

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @When("User Clicks on Login")
    public void user_clicks_on_login() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Login")
        )).click();
    }

    @When("User enters admin Email and admin password")
    public void user_enters_admin_email_and_admin_password() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("email")
        )).sendKeys("arunavabanerjee2018@gmail.com");

        driver.findElement(By.id("password"))
                .sendKeys("123456");
    }

    @When("User clicks on login button")
    public void user_clicks_on_login_button() {

        driver.findElement(
                By.xpath("//button[contains(text(),'Login')]")
        ).click();

        // Dashboard = login success indicator
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Dashboard')]")
        ));
    }

    @When("User clicks on admin panel and clicks on add subtopic")
    public void user_clicks_on_admin_panel_and_clicks_on_add_subtopic() {

        // Wait for loader to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[contains(@class,'fixed') and contains(@class,'inset-0')]")
        ));

        // Open admin panel
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/admin']")
        )).click();

        // Click Add Subtopic card ONCE
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(text(),'Add Subtopic')]")
        )).click();
    }

    @When("User clicks on Select Subject and clicks on Add Subtopic")
    public void user_clicks_on_select_subject_and_clicks_on_add_subtopic() {

        // Wait for Add Subtopic FORM (this confirms navigation)
        WebElement subtopicName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@placeholder='Subtopic Name']")
                )
        );

        subtopicName.sendKeys("This is Testing Subtopic");

        WebElement subjectDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//select")
                )
        );

        new Select(subjectDropdown)
                .selectByVisibleText("Computer Networks");
    }

    @When("User clics on Add Subtopic Button")
    public void user_clics_on_add_subtopic_button() {

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Add Subtopic')]")
        )).click();
    }

    @Then("Success message displayed to user")
    public void success_message_displayed_to_user() {

        WebElement successMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'successfully added')]")
                )
        );

        Assert.assertTrue(successMsg.getText().contains("successfully added"));
        System.out.println("SUCCESS: " + successMsg.getText());
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}
