import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class WorkWithWindows {
    static WebDriver driver;
    static WebDriverWait wait;


    @BeforeAll
    public static void start() {
        driver = BaseClass.createDriver("chrome");
        //driver = BaseClass.createDriver("ff");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    public static void stop() {
        driver.quit();
        driver = null;
    }

    @Test
    public void workWithWindows() {
        BaseClass.loginAdmin(driver, wait);
        driver.findElement(By.xpath("//span[contains(text(),'Countries')]")).click();
        driver.findElement(By.xpath("//tr[@class='row']/td/a")).click();

        String mainWindowId = driver.getWindowHandle();

        List<WebElement> externalLinks = driver.findElements(By.cssSelector(".fa-external-link"));
        for (WebElement eLink : externalLinks) {
            Set<String> currentWindowIds = driver.getWindowHandles();
            eLink.click();
            String newWindowId =  wait.until(newWindowOpened(currentWindowIds));
            driver.switchTo().window(newWindowId);
            driver.close();
            driver.switchTo().window(mainWindowId);
        }
    }

    public static ExpectedCondition<String> newWindowOpened(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            @Override
            public String apply(WebDriver input) {
                Set<String> handles = input.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }
}
