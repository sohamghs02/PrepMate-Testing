package seleniumTest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import seleniumTest.base.BasePage;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // 🔹 Step 1: Open login page
    private static final By LOGIN_LINK =
            By.xpath("//a[@href='/auth/login' and normalize-space()='Login']");

    // 🔹 Step 2: Login form fields
    private static final By EMAIL =
            By.name("email");

    private static final By PASSWORD =
            By.name("password");

    // 🔹 Step 3: Submit login
    private static final By LOGIN_SUBMIT =
            By.xpath("//button[@type='submit' and normalize-space()='Login']");

    /**
     * Login using valid credentials
     */
    public void login(String email, String password) {

        // Open login page
        click(LOGIN_LINK);

        // Fill credentials
        type(EMAIL, email);
        type(PASSWORD, password);

        // Submit
        click(LOGIN_SUBMIT);
    }
}
