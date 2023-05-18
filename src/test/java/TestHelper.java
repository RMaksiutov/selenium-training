import PageObject.BasketPage;
import PageObject.MainPage;
import PageObject.ProductPage;
import PageObject.TestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class TestHelper {
    static WebDriver driver;
    static WebDriverWait wait;
    static MainPage mainPage;
    static ProductPage productPage;
    static BasketPage basketPage;


    public static void initWebDriver(String desiredBrowser) {
        driver = TestUtil.createDriver(desiredBrowser);
        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        basketPage = new BasketPage(driver);
    }

    public static void initWait() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static void quitWebDriver() {
        driver.quit();
        driver = null;
    }

    public void addProductsToCart(int desiredItemsQ) throws InterruptedException {
        int currQuantity = 0;
        for (int i = 0; i < desiredItemsQ; i++){
            mainPage.open();
            mainPage.openProduct();
            currQuantity = mainPage.getCartItemsCount();
            if (productPage.isSizeSelectionAvailable()) {
                productPage.selectSize("Small");
            }
            productPage.addProductToCart();
            assertTrue(checkCartItemAdded(currQuantity),
                    "Items quantity in cart didn't change. Old quantity " + currQuantity
                            + ". Current quantity " + mainPage.getCartItemsCount());
        }
    }

    public void openCart() {
        mainPage.openCart();
    }

    void removeAllItemsFromCart() throws InterruptedException {
        int itemsQuantity = basketPage.getItemsCount();
        assertTrue(checkTable(itemsQuantity), "Quantity of items in order summary check failed!");
        for (int i = 0; i < itemsQuantity; i++) {
            removeItemFromCart();
            assertTrue(checkTable(itemsQuantity - i - 1), "Item removed from summary check failed!");
        }
    }

    private boolean checkTable(int q) throws InterruptedException {
        int k = 10;
        do {
            int actualQ = basketPage.getItemsSummaryCount();
            if (actualQ == q) return true;
            Thread.sleep(100);
            k--;
        } while (k > 0);
        return false;
    }

    private boolean removeItemFromCart() {
        if (!basketPage.chechRemoveButtonPresence()) return false;
        basketPage.removeProductFromCart();
        return false;
    }

    private boolean checkCartItemAdded(int currQuantity) throws InterruptedException {
        int newQuantity;
        int k = 10;
        do {
            Thread.sleep(100);
            newQuantity = mainPage.getCartItemsCount();
            if (newQuantity > currQuantity) return true;
            k--;
        } while (k > 0);
        return false;
    }
}
