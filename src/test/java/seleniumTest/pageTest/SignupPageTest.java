package seleniumTest.pageTest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import seleniumTest.BaseTest;
import seleniumTest.listeners.TestListener;
import seleniumTest.pages.SignupPage;

@Listeners(TestListener.class)
public class SignupPageTest extends BaseTest {

    @Test
    public void verifyUserCanRegisterSuccessfully() {

        SignupPage page = new SignupPage(driver);
        String email = "user_" + System.currentTimeMillis() + "@gmail.com";

        page.register(
                "Debanjan Mukherjee",
                email,
                "IIT Kharagpur",
                "Password@123",
                "Password@123",
                "2024"
        );
        page.waitForUrlContains("dashboard", 20);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("dashboard"),
                "User not redirected to dashboard"
        );
    }

    @Test
    public void verifyErrorShownWhenPasswordsDoNotMatch() {

        SignupPage page = new SignupPage(driver);

        page.register(
                "Debanjan Mukherjee",
                "wrong_" + System.currentTimeMillis() + "@gmail.com",
                "IIT Kharagpur",
                "Password@123",
                "WrongPassword",
                "2024"
        );
        Assert.assertTrue(
                page.isPasswordMismatchErrorVisible(),
                "Password mismatch error not shown"
        );
        Assert.assertFalse(
                driver.getCurrentUrl().contains("dashboard"),
                "User should not be redirected"
        );

        System.out.println("Wrong password test completed");
    }
}
