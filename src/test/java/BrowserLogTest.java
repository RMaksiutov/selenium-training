import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BrowserLogTest {
    static WebDriver driver;
    static WebDriverWait wait;
    static List<LogEntry> currentLogEntries;

    @BeforeAll
    public static void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    public static void stop() {
        driver.quit();
        driver = null;
    }

    @Test
    public void testBrowserLog() {

        BaseClass.loginAdmin(driver, wait);
        printBrowserLog();
        driver.navigate().to("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        currentLogEntries = driver.manage().logs().get("browser").getAll();
        expandSubCategories();
        List<WebElement> products = driver.findElements(By.xpath("//td/a[not(@title) and not(contains(@href, 'doc=catalog'))]"));
        List<String> productLinks = BaseClass.getHRefs(products);
        for (String pL : productLinks) {
            driver.findElement(By.xpath("//td/a[not(@title) and @href=\"" + pL +"\"]")).click();
            checkNewLogEntry();
            driver.findElement(By.cssSelector("button[name='cancel']")).click();
            checkNewLogEntry();
        }
        printBrowserLog();
    }

    private void expandSubCategories() {
        int currentS;
        List<WebElement> subCategories = driver.findElements(By.xpath("//td/a[not(@title) and contains(@href,'doc=catalog')]"));
        currentS = subCategories.size();
        int i = 0;
        do {
            subCategories.get(i).click();
            subCategories = driver.findElements(By.xpath("//td/a[not(@title) and contains(@href,'doc=catalog')]"));
            currentS = subCategories.size();
            i++;
        } while (i < currentS);

    }

    private void printBrowserLog() {
        for (LogEntry l : driver.manage().logs().get("browser").getAll()) {
            System.out.println(l);
        }
    }

    private void checkNewLogEntry() {
        List<LogEntry> newLogEntries = driver.manage().logs().get("browser").getAll();
        if (newLogEntries.size() > currentLogEntries.size()) {
            for (int i = 0; i < newLogEntries.size(); i++) {
                if (i >= currentLogEntries.size()) System.out.println("New log entry: " + newLogEntries.get(i));
            }
            currentLogEntries = newLogEntries;
        }
    }
}
