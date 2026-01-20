package StepDefinition.admin;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class subjectAdd {

    public static WebDriver driver;
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

        // Wait for any full-screen overlay to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[contains(@class,'fixed') and contains(@class,'inset-0')]")
        ));

        // Click Admin panel
        WebElement adminLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@href='/admin']")
                )
        );
        adminLink.click();

        // 3Wait for admin dashboard to fully load
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(text(),'Add Subject')]")
        ));

        // Locate Add Subject card
        WebElement addSubject = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[normalize-space()='Add Subject']")
                )
        );

        // Scroll into view (VERY IMPORTANT)
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", addSubject);

        // Small UI settle wait (React animation)
        wait.until(ExpectedConditions.elementToBeClickable(addSubject));

        // Click (fallback-safe)
        try {
            addSubject.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", addSubject);
        }
    }


    @When("User enters Subject Name and Description and Choose Icon")
    public void user_enters_subject_name_and_description_and_choose_icon() {

        // Subject Name
        WebElement subjectName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@name='name']")
                )
        );
        subjectName.clear();
        subjectName.sendKeys("Cucumber");

        // Description (FIXED)
        WebElement description = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//textarea[@name='description']")
                )
        );
        description.clear();
        description.sendKeys("This is a BDD Testing Framework");

        // Icon dropdown (FIXED)
        WebElement iconDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//select[@name='icon']")
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
}
