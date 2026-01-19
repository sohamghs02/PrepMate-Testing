package seleniumTest.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import seleniumTest.BaseTest;
import seleniumTest.base.BasePage;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object obj = result.getInstance();
        if (obj instanceof BaseTest) {
            BaseTest test = (BaseTest) obj;
            new BasePage(test.driver).takeScreenshot(result.getName());
        }
    }
}
