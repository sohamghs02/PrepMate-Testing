package StepDefinition.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReusableMethods {

    public String captureScreenshot(WebDriver driver, String testName) {
        String folderPath = System.getProperty("user.dir") + "/src/test/assets/" + testName + "/";
        File dir = new File(folderPath);
        if (!dir.exists()) dir.mkdirs();

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destPath = folderPath + testName + "_" + timestamp() + ".png";
        File destination = new File(destPath);
        try {
            FileHandler.copy(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destPath;
    }

    public String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
    }
}