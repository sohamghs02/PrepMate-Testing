package StepDefinition.admin;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


import java.time.Duration;

public class subjectAdd{
    WebDriver driver;
    WebDriverWait wait;

    @Given("User is in Home Page")
    public void user_is_in_home_page() {
        driver = new EdgeDriver();
        driver.get("https://prep-mate-full-stack-alpha.vercel.app/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @When("User clicks on Login")
    public void user_clicks_on_login() {
        driver.findElement(By.linkText("Login")).click();
    }

    @When("User enters admin email and admin password")
    public void user_enters_admin_email_and_admin_password() {
        driver.findElement(By.id("email"))
                .sendKeys("arunavabanerjee2018@gmail.com");

        driver.findElement(By.id("password"))
                .sendKeys("123456");

        driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/button")).click();

        // wait until dashboard appears
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Dashboard')]")
        ));
    }

    @When("User enters admin panel and clicks on Add Subject")
    public void user_enters_admin_panel_and_clicks_on_add_subject() {
        driver.findElement(By.linkText("Admin")).click();
//        System.out.println("1");
        WebElement addSubjectCard = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[5]/div[1]/div"));
        wait.until(ExpectedConditions.elementToBeClickable(addSubjectCard)).click();
    }

    @When("User enters Subject Name and Description and Choose Icon")
    public void user_enters_subject_name_and_description_and_choose_icon() {
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/div[1]/input")).sendKeys("Cucumeber");
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/div[2]/textarea")).sendKeys("This is a BDD Testing Framework");
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/div[3]/div/select")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/div[3]/div/select/option[11]")).click();
    }

    @When("User clicks on add subject")
    public void user_clicks_on_add_subject() {
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/button")).click();
    }

    @Then("User is displayed Success Message")
    public void user_is_displayed_success_message() {
        try {
            WebElement successMsg = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'Subject added successfully')]")
                    )
            );

            System.out.println("SUCCESS: " + successMsg.getText());

        } catch (Exception e) {

            WebElement errorMsg = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'Failed to add subject')]")
                    )
            );

            System.out.println("ERROR: " + errorMsg.getText());

            takeScreenshot("Add_Subject_Failure");

            throw new AssertionError("Subject was NOT added successfully");
        }
    }

    public void takeScreenshot(String fileName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);

            File dest = new File(
                    System.getProperty("user.dir") +
                            "/screenshots/" +
                            fileName +
                            "_" +
                            System.currentTimeMillis() +
                            ".png"
            );

            Files.createDirectories(dest.getParentFile().toPath());
            Files.copy(src.toPath(), dest.toPath());

            System.out.println("Screenshot saved at: " + dest.getAbsolutePath());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @AfterTest
    public void tearDown() throws InterruptedException {
        Thread.sleep(4000);
        driver.quit();
    }
}