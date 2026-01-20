package seleniumTest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import seleniumTest.base.BasePage;

public class SignupPage extends BasePage {

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    // ---------- HOME PAGE ----------
    private static final By SIGNUP_BUTTON =
            By.xpath("//button[normalize-space()='Sign Up']");

    // ---------- FORM FIELDS ----------
    private static final By FULL_NAME = By.name("name");
    private static final By EMAIL = By.name("email");
    private static final By COLLEGE = By.name("college");
    private static final By PASSWORD = By.name("password");
    private static final By CONFIRM_PASSWORD = By.name("confirmPassword");
    private static final By PASSOUT = By.name("passoutYear");

    // ---------- ACTIONS ----------
    private static final By SUBMIT =
            By.xpath("//button[@type='submit']");

    // ---------- FEEDBACK ----------
    private static final By SUCCESS_TOAST =
            By.xpath("//div[@data-slot='alert-title' and contains(text(),'Signup Successful')]");

    private static final By PASSWORD_ERROR =
            By.xpath("//p[contains(text(),'Passwords must match')]");

    // ---------- BUSINESS FLOW ----------
    public void register(String name, String email, String college,
                         String pass, String confirm, String year) {

        click(SIGNUP_BUTTON);

        type(FULL_NAME, name);
        type(EMAIL, email);
        type(COLLEGE, college);
        type(PASSWORD, pass);
        type(CONFIRM_PASSWORD, confirm);
        type(PASSOUT, year);

        click(SUBMIT);

        // toast is optional & short-lived
        waitForToast(SUCCESS_TOAST, 3);
    }

    public boolean isSignupSuccessToastVisible() {
        return waitForToast(SUCCESS_TOAST, 3);
    }

    public boolean isPasswordMismatchErrorVisible() {
        return waitForToast(PASSWORD_ERROR, 3);
    }
}
