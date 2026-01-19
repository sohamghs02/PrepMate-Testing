package StepDefinition.admin;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

import java.time.Duration;

public class subtopicAdd {
    WebDriver driver;
    WebDriverWait wait;
    @Given("User is in the Home Page Section")
    public void user_is_in_the_home_page_section() {
        driver = new EdgeDriver();
        driver.get("https://prep-mate-full-stack-alpha.vercel.app/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @When("User Clicks on Login")
    public void user_clicks_on_login() {
        driver.findElement(By.linkText("Login")).click();
    }

    @When("User enters admin Email and admin password")
    public void user_enters_admin_email_and_admin_password() {
        driver.findElement(By.id("email"))
                .sendKeys("arunavabanerjee2018@gmail.com");

        driver.findElement(By.id("password"))
                .sendKeys("123456");
    }

    @When("User clicks on login button")
    public void user_clicks_on_login_button() {

        driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/button")).click();
        // wait until dashboard appears
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Dashboard')]")
        ));
    }

    @When("User clicks on admin panel and clicks on add subtopic")
    public void user_clicks_on_admin_panel_and_clicks_on_add_subtopic() {
        WebElement admin = driver.findElement(By.linkText("Admin"));
        wait.until(ExpectedConditions.elementToBeClickable(admin));
        admin.click();
//          System.out.println("1");
        WebElement addSubtopicCard = driver.findElement(By.xpath("//div[@data-slot='card-title' and normalize-space()='Add Subtopic']"));
        wait.until(ExpectedConditions.elementToBeClickable(addSubtopicCard)).click();
    }

    @When("User clicks on Select Subject and clicks on Add Subtopic")
    public void user_clicks_on_select_subject_and_clicks_on_add_subtopic() {
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/div[1]/select")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/div[1]/select/option[8]")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/div[2]/input")).sendKeys("This is Testing Subtopic");
    }

    @When("User clics on Add Subtopic Button")
    public void user_clics_on_add_subtopic_button() {
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/button")).click();

    }

    @Then("Success message displayed to user")
    public void success_message_displayed_to_user() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String actText = "Subtopic \"This is testing Subtopic section\" has been successfully added.";
        Assert.assertEquals(actText, driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/div[3]/div/div[2]")).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/div[3]/div/div[2]")).getText());
    }
    @AfterTest
    public void tearDown() throws InterruptedException {
        Thread.sleep(4000);
        driver.quit();
    }
}
