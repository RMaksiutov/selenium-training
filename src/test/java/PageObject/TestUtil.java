package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import java.io.File;

public class TestUtil {

    public static WebDriver createDriver(String sDriver) {
        if ("chrome".equals(sDriver)) return new ChromeDriver();
        if ("ie".equals(sDriver)) return new InternetExplorerDriver();
        if ("ff".equals(sDriver)) {
            FirefoxOptions options = new FirefoxOptions();
            options.setBinary(new FirefoxBinary(new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe")));
            return new FirefoxDriver(options);
        }
        return null;
    }

}
