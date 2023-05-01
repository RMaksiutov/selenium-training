import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class WorkWithBasket {
    static WebDriver driver;
    static WebDriverWait wait;
    static String urlMain = "http://localhost/litecart/en/";

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
    public void workWithBasket() throws InterruptedException {
        int itemsQuantity = 3;

        addProductsToCart(driver, itemsQuantity);
        driver.findElement(By.xpath("//a[contains(text(),'Checkout')]")).click();
        removeAllItemsFromCart(driver, wait);
        System.out.println("Success!");
    }

    private void addProductsToCart(WebDriver d, int desiredItemsQ) throws InterruptedException {
        int currQuantity = 0;
        for (int i = 0; i < desiredItemsQ; i++){
            d.navigate().to(urlMain);
            List<WebElement> products = d.findElements(By.cssSelector(".products > li"));
            products.get(0).click();
            currQuantity = countCartItems(d);
            if (d.findElements(By.cssSelector("select[name=\"options[Size]\"]")).size() > 0) {
                selectSize(d);
            }
            d.findElement(By.cssSelector("button[name=\"add_cart_product\"]")).click();
            assertTrue(checkCartItemAdded(d, currQuantity),
                    "Items quantity in cart didn't change. Old quantity " + currQuantity
                            + ". Current quantity " + countCartItems(d));
        }
    }

    private void selectSize(WebDriver d) {
        WebElement wESelectSize = d.findElement(By.cssSelector("select[name=\"options[Size]\"]"));
        Select selectSize = new Select(wESelectSize);
        selectSize.selectByVisibleText("Small");
    }

    private void removeAllItemsFromCart(WebDriver d, WebDriverWait w) throws InterruptedException {
        int itemsQuantity = d.findElements(By.cssSelector(".items > li")).size();
        assertTrue(checkTable(d, itemsQuantity), "Quantity of items in order summary check failed!");
        for (int i = 0; i < itemsQuantity; i++) {
            removeItemFromCart(d, w);
            assertTrue(checkTable(d, itemsQuantity - i - 1), "Item removed from summary check failed!");
        }
    }

    private boolean checkTable(WebDriver d, int q) throws InterruptedException {
        int k = 10;
        do {
            int actualQ = d.findElements(By.xpath("//tr/td[@class='item']")).size();
            if (actualQ == q) return true;
            Thread.sleep(100);
            k--;
        } while (k > 0);
        return false;
    }

    private int countCartItems(WebDriver d) {
        return Integer.parseInt(d.findElement(By.cssSelector(".quantity")).getText());
    }

    private boolean removeItemFromCart(WebDriver d, WebDriverWait w) {
        List<WebElement> removeButtons = d.findElements(By.xpath("//button[@value='Remove']"));
        if (removeButtons.size() == 0) return false;
        WebElement removeButton = removeButtons.get(0);
        w.until(elementToBeClickable(removeButton));
        removeButton.click();
        return false;
    }

    private boolean checkCartItemAdded(WebDriver d, int currQuantity) throws InterruptedException {
        int newQuantity;
        int k = 10;
        do {
            Thread.sleep(100);
            newQuantity = Integer.parseInt(d.findElement(By.cssSelector(".quantity")).getText());
            if (newQuantity > currQuantity) return true;
            k--;
        } while (k > 0);
        return false;
    }

}
