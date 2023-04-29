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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Products {
    static WebDriver driver;
    static WebDriverWait wait;
    static String productName;

    static final String productImage = "4qb4sny89gdu3o1cy1hqukxxbroahmrf.jpg";

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
    public void productAdd() {
        BaseClass.loginAdmin(driver, wait);
        driver.findElement(By.xpath("//span[contains(text(),'Catalog')]")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Add New Product')]")).click();
        fillGeneral(driver);
        driver.findElement(By.xpath("//a[contains(text(),'Information')]")).click();
        fillInformation(driver);
        driver.findElement(By.xpath("//a[contains(text(),'Prices')]")).click();
        fillPrices(driver);
        driver.findElement(By.cssSelector("button[name=\"save\"]")).click();
        List<WebElement> products = driver.findElements(By.xpath("//a[contains(text(),'" + productName + "')]"));
        assertTrue(products.size() == 1, "Product creation check failed. Products quantity: "
                + products.size());
    }

    private void fillGeneral(WebDriver d) {
        String pathToImage = new File(productImage).getAbsolutePath();
        productName = "ProductName" + BaseClass.getRandomNumber(5);

        d.findElement(By.xpath("//input[@type='radio' and @value='1']")).click();
        d.findElement(By.cssSelector("input[name=\"name[en]\"]"))
                .sendKeys(productName);
        d.findElement(By.cssSelector("input[name=\"code\"]"))
                .sendKeys("Code" + BaseClass.getRandomNumber(3));
        d.findElement(By.xpath("//input[@data-name='Rubber Ducks']")).click();
        selectDefCategory(d);
        d.findElement(By.xpath("//input[@value='1-1']")).click();
        d.findElement(By.cssSelector("input[name=\"quantity\"]"))
                .sendKeys(Keys.DELETE + "5");
        d.findElement(By.cssSelector("input[type=\"file\"]")).sendKeys(pathToImage);
        d.findElement(By.cssSelector("input[name=\"date_valid_from\"]"))
                .sendKeys("01022023");
        d.findElement(By.cssSelector("input[name=\"date_valid_to\"]"))
                .sendKeys("01022025");
        System.out.println("General Tab filled");
    }

    private void fillInformation(WebDriver d) {
        selectManufacturer(d);
        d.findElement(By.cssSelector("input[name=\"keywords\"]"))
                .sendKeys("new,product");
        d.findElement(By.cssSelector("input[name=\"short_description[en]\"]"))
                .sendKeys("Short Descr Product");
        d.findElement(By.cssSelector("div[class=\"trumbowyg-editor\"]"))
                .sendKeys("Full Description Product");
        d.findElement(By.cssSelector("input[name=\"head_title[en]\"]"))
                .sendKeys("HeadProduct");
        d.findElement(By.cssSelector("input[name=\"meta_description[en]\"]"))
                .sendKeys("Meta Descr");

        System.out.println("Information Tab filled!");
    }

    private void fillPrices(WebDriver d) {
        d.findElement(By.cssSelector("input[name=\"purchase_price\"]"))
                .sendKeys(Keys.DELETE
                        + "120"
                        + Keys.ARROW_RIGHT
                        + Keys.DELETE
                        + Keys.DELETE
                        + "99");
        selectCurrency(driver);
        //selectTaxClass(driver);
        d.findElement(By.cssSelector("input[name=\"gross_prices[EUR]\"]"))
                .sendKeys(Keys.DELETE
                        + "124"
                        + Keys.ARROW_RIGHT
                        + Keys.DELETE
                        + "5");

        System.out.println("Prices Tab filled!");
    }

    private void selectDefCategory(WebDriver d) {
        //WebElement wEDefCategory = d.findElement(By.cssSelector("select[name=\"default_category_id\"]"));
        //Select selectDefCategory = new Select(wEDefCategory);
        //selectDefCategory.selectByVisibleText("Rubber Ducks");
        selectByText(d, "select[name=\"default_category_id\"]", "Rubber Ducks");
    }

    private void selectManufacturer(WebDriver d) {
        //WebElement wEManufacturer = d.findElement(By.cssSelector("select[name=\"manufacturer_id\"]"));
        //Select selectManufacturer = new Select(wEManufacturer);
        //selectManufacturer.selectByVisibleText("ACME Corp.");
        selectByText(d, "select[name=\"manufacturer_id\"]", "ACME Corp.");
    }

    private void selectCurrency(WebDriver d) {
        selectByText(d, "select[name=\"purchase_price_currency_code\"]", "Euros");
    }

    private void selectTaxClass(WebDriver d) {
        selectByText(d, "select[name=\"tax_class_id\"]", "-- Select --");
    }

    private void selectByText(WebDriver d, String selector, String text) {
        WebElement wEManufacturer = d.findElement(By.cssSelector(selector));
        Select selectManufacturer = new Select(wEManufacturer);
        selectManufacturer.selectByVisibleText(text);
    }
}
