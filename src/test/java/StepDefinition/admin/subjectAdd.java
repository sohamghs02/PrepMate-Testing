package StepDefinition.admin;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class subjectAdd {

    WebDriver driver;
    WebDriverWait wait;

    @Given("User is in Home Page")
    public void user_is_in_home_page() {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get("https://prep-mate-full-stack-alpha.vercel.app/");

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @When("User clicks on Login")
    public void user_clicks_on_login() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Login")
        )).click();
    }

    @When("User enters admin email and admin password")
    public void user_enters_admin_email_and_admin_password() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("email")
        )).sendKeys("arunavabanerjee2018@gmail.com");

        driver.findElement(By.id("password"))
                .sendKeys("123456");

        driver.findElement(
                By.xpath("//button[contains(text(),'Login')]")
        ).click();

        // Dashboard = login success signal
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Dashboard')]")
        ));
    }

    @When("User enters admin panel and clicks on Add Subject")
    public void user_enters_admin_panel_and_clicks_on_add_subject() {

        // Wait for loader to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[contains(@class,'fixed') and contains(@class,'inset-0')]")
        ));

        // Open Admin panel
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/admin']")
        )).click();

        // Click Add Subject card ONCE
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(text(),'Add Subject')]")
        )).click();
    }

    @When("User enters Subject Name and Description and Choose Icon")
    public void user_enters_subject_name_and_description_and_choose_icon() {

        // Wait for form (Subject Name input proves page loaded)
        WebElement subjectName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@placeholder='Subject Name']")
                )
        );
        subjectName.sendKeys("Cucumber");

        driver.findElement(
                By.xpath("//textarea[@placeholder='Description']")
        ).sendKeys("This is a BDD Testing Framework");

        WebElement iconDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//select")
                )
        );

        new Select(iconDropdown)
                .selectByVisibleText("Cyber Security (Lock)");
    }

    @When("User clicks on add subject")
    public void user_clicks_on_add_subject() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Add Subject')]")
        )).click();
    }

    @Then("User is displayed Success Message")
    public void user_is_displayed_success_message() {

        WebElement successMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Subject added successfully')]")
                )
        );

        System.out.println("SUCCESS: " + successMsg.getText());
    }

    public void takeScreenshot(String fileName) {
        try {
            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            File dest = new File(
                    System.getProperty("user.dir")
                            + "/screenshots/"
                            + fileName + "_" + System.currentTimeMillis() + ".png"
            );

            Files.createDirectories(dest.getParentFile().toPath());
            Files.copy(src.toPath(), dest.toPath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}
