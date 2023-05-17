import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class BaseClass {

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

    public static void loginAdmin(WebDriver d, WebDriverWait w) {
        d.navigate().to("http://localhost/litecart/admin/");
        d.findElement(By.name("username")).sendKeys("admin");
        d.findElement(By.name("password")).sendKeys("admin");
        d.findElement(By.name("login")).click();
        w.until(titleIs("My Store"));
        System.out.println("Login success!");
    }

    public static ArrayList<String> getStringNames(List<WebElement> wEList) {
        ArrayList<String> stringNames = new ArrayList<String>();
        for (WebElement c : wEList) {
            String name = "";
            name = c.getText();
            //if ("Anguilla".equals(country)) country = "Angilla"; // для проверки работы checkAlphabetOrder
            stringNames.add(name);
        }
        return stringNames;
    }

    public static ArrayList<String> getHRefs(List<WebElement> wEList) {
        ArrayList<String> stringNames = new ArrayList<String>();
        for (WebElement c : wEList) {
            String name = "";
            name = c.getAttribute("href");
            stringNames.add(name);
        }
        return stringNames;
    }

    public static boolean checkAlphabetOrder(ArrayList<String> strings) {
        for (int i = 0; i < strings.size() - 1; i++) {
            if (strings.get(i).compareTo(strings.get(i + 1)) >= 0) {
                System.out.println("Name 1: " + strings.get(i));
                System.out.println("Name 2: " + strings.get(i + 1));
                System.out.println("Not alphabet order!");
                return false;
                }
            }
        return true;
    }

    public static int getRandomNumber(int length) {
        if (length > 0) return new Random().nextInt(length);
        if (length == 0) return (int) System.currentTimeMillis();
        return 0;
    }

}
