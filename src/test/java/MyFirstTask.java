import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Beta;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.naming.ldap.LdapReferralException;
import java.io.File;
import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTask {

    protected static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void start() {
        //CHROME
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //FIREFOX
        //FirefoxOptions options = new FirefoxOptions();
        //options.setBinary(new FirefoxBinary(new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe")));
        //driver = new FirefoxDriver(options);

        //IE
        //driver = new InternetExplorerDriver();
        //wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void loginAdmin() {
        driver.navigate().to("http://localhost/litecart/admin/");
        //driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
        System.out.println("Success!");
    }

    @AfterAll
    public static void stop() {
        driver.quit();
        driver = null;
    }
}
