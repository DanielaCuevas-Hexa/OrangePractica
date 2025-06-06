package Reports;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotClass {
    public static String captureScreenshot(WebDriver driver, String screenshotName) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String path = "test-output/screenshots/" + screenshotName + "_" + timestamp + ".png";
        File dest = new File(path);
        dest.getParentFile().mkdirs(); // crea la carpeta si no existe
        Files.copy(src.toPath(), dest.toPath());
        return dest.getAbsolutePath();
        //return "screenshots/" + screenshotName + "_" + timestamp + ".png";
    }
}
