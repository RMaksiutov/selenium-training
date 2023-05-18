import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

public class WorkWithBasket extends TestHelper{

    @BeforeAll
    public static void start() {
        initWebDriver("chrome");
        initWait();
    }

    @AfterAll
    public static void stop() {
        quitWebDriver();
    }

    @Test
    public void workWithBasket() throws InterruptedException {
        addProductsToCart(3);
        openCart();
        removeAllItemsFromCart();
    }
}
