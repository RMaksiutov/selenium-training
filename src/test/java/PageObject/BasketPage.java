package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class BasketPage {
    private static WebDriver driver;

    public BasketPage(WebDriver d) {
        driver = d;
    }

    public int getItemsCount() {
        return driver.findElements(By.cssSelector(".items > li")).size();
    }

    public int getItemsSummaryCount() {
        return driver.findElements(By.xpath("//tr/td[@class='item']")).size();
    }

    public boolean chechRemoveButtonPresence() {
        return driver.findElements(By.xpath("//button[@value='Remove']")).size() > 0;
    }

    public void removeProductFromCart() {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement removeButton = driver.findElements(By.xpath("//button[@value='Remove']")).get(0);
        w.until(elementToBeClickable(removeButton));
        removeButton.click();
    }
}
