package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ProductPage {
    private static WebDriver driver;

    public ProductPage(WebDriver d) {
        driver = d;
    }

    public boolean isSizeSelectionAvailable() {
        return driver.findElements(By.cssSelector("select[name=\"options[Size]\"]")).size() > 0;
    }

    public void selectSize(String size) {
        WebElement wESelectSize = driver.findElement(By.cssSelector("select[name=\"options[Size]\"]"));
        Select selectSize = new Select(wESelectSize);
        selectSize.selectByVisibleText(size);
    }

    public void addProductToCart() {
        driver.findElement(By.cssSelector("button[name=\"add_cart_product\"]")).click();
    }
}
