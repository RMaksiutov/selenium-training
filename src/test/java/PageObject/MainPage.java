package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.List;

public class MainPage {
    private static WebDriver driver;
    private static String urlMain = "http://localhost/litecart/en/";
    private static String cssProducts = ".products > li";

    public MainPage(WebDriver d) {
        driver = d;
    }

    public void open() {
        driver.navigate().to(urlMain);
    }

    public void openProduct() {
        List<WebElement> products = driver.findElements(By.cssSelector(cssProducts));
        products.get(0).click();
    }

    public int getCartItemsCount() {
        return Integer.parseInt(driver.findElement(By.cssSelector(".quantity")).getText());
    }

    public void openCart() {
        driver.findElement(By.xpath("//a[contains(text(),'Checkout')]")).click();
    }
}
