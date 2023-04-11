import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Beta;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.naming.ldap.LdapReferralException;
import java.time.Duration;

public class MyFirstTask {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void myFirstTask() {
        driver.get("http://www.google.com/");
    }

    @AfterAll
    public static void stop() {
        driver.quit();
        driver = null;
    }
}
