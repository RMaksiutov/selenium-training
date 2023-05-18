import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
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

import static org.junit.jupiter.api.Assertions.*;

public class TestProductPage {


    static WebDriver driver;
    static WebDriverWait wait;
    static String urlMainPage = "http://localhost/litecart/en/";

    @AfterEach
    public void stop() {
        driver.quit();
        driver = null;
    }

    @Test
    public void sortCountriesChrome() {
        sortCountries("chrome");
    }

    @Test
    public void sortCountriesFF() {
        sortCountries("ff");
    }

    @Test
    public void sortCountriesIE() {
        sortCountries("ie");
    }

    private static WebDriver createDriver(String sDriver) {
        if ("chrome".equals(sDriver)) return new ChromeDriver();
        if ("ie".equals(sDriver)) return new InternetExplorerDriver();
        if ("ff".equals(sDriver)) {
            FirefoxOptions options = new FirefoxOptions();
            options.setBinary(new FirefoxBinary(new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe")));
            return new FirefoxDriver(options);
        }
        return null;
    }

    private void sortCountries(String sDriver) {
        String productNameMain = "";
        String productNameProductPage = "";
        String priceMain = "";
        String priceProductPage = "";
        String pricePromMain = "";
        String pricePromProductPage = "";
        driver = createDriver(sDriver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.navigate().to(urlMainPage);
        WebElement boxCampaigns = driver.findElement(By.cssSelector("#box-campaigns"));
        List<WebElement> ducks = boxCampaigns.findElements(By.cssSelector("li"));
        assertTrue(ducks.size() > 0, "Products not found!");
        WebElement firstDuck = ducks.get(0);
        productNameMain = firstDuck.findElement(By.cssSelector(".name")).getText();
        assertFalse("".equals(productNameMain), "Product name not found!");
        WebElement wEPriceMain = firstDuck.findElement(By.cssSelector(".regular-price"));
        WebElement wEPricePromMain = firstDuck.findElement(By.cssSelector(".campaign-price"));
        priceMain = wEPriceMain.getText();
        pricePromMain = wEPricePromMain.getText();
        String colorPrice = getColor(wEPriceMain.getCssValue("color"));
        String colorPromPrice = getColor(wEPricePromMain.getCssValue("color"));
        assertEquals("grey", colorPrice,
                "Old price color not grey!");
        assertTrue(wEPriceMain.getCssValue("text-decoration").contains("line-through"),
                "Old price not crossed out!");
        assertEquals("red", colorPromPrice,
                "Prom price color not red!");
        assertTrue(isBold(wEPricePromMain.getCssValue("font-weight")),
                "Prom price not bold!");
        float fSizeMain = Float.parseFloat(wEPriceMain.getCssValue("font-size")
                .replace("px", ""));
        float fSizePromMain = Float.parseFloat(wEPricePromMain.getCssValue("font-size")
                .replace("px", ""));
        assertTrue(fSizePromMain > fSizeMain, "Prom price not larger than regular price");

        firstDuck.click();

        assertTrue("Yellow Duck | Subcategory | Rubber Ducks | My Store".equals(driver.getTitle()),
                "Product page didn't open!");
        productNameProductPage = driver.findElement(By.cssSelector("h1.title")).getText();
        assertEquals(productNameMain, productNameProductPage, "Product names not equal!"
                + "Product name on main page: " + productNameMain + "|"
                + "Product name on product page: " + productNameProductPage);
        WebElement wEPriceProductPage = driver.findElement(By.cssSelector(".regular-price"));
        WebElement wEPricePromProductPage = driver.findElement(By.cssSelector(".campaign-price"));
        priceProductPage = wEPriceProductPage.getText();
        pricePromProductPage = wEPricePromProductPage.getText();
        assertEquals(priceMain, priceProductPage, "Regular prices not equal!"
                + "Price on main page: " + priceMain + "|"
                + "Price on product page: " + priceProductPage);
        assertEquals(pricePromMain, pricePromProductPage, "Prom prices not equal!"
                + "Price on main page: " + pricePromMain + "|"
                + "Price on product page: " + pricePromProductPage);
        String colorPriceProductPage = getColor(wEPriceProductPage.getCssValue("color"));
        String colorPromPriceProductPage = getColor(wEPricePromProductPage.getCssValue("color"));
        assertEquals("grey", colorPriceProductPage,
                "Old price on product page color not grey!");
        assertTrue(wEPriceProductPage.getCssValue("text-decoration").contains("line-through"),
                "Old price not crossed out!");
        assertEquals("red", colorPromPriceProductPage,
                "Prom price on product page color not red!");
        assertTrue(isBold(wEPricePromProductPage.getCssValue("font-weight")),
                "Prom price on product page not bold!");
        float fSizeProductPage = Float.parseFloat(wEPriceProductPage.getCssValue("font-size")
                .replace("px", ""));
        float fSizePromProductPage = Float.parseFloat(wEPricePromProductPage.getCssValue("font-size")
                .replace("px", ""));
        assertTrue(fSizePromProductPage > fSizeProductPage,
                "Prom price on product page not larger than regular price");

    }

    private String getColor(String cssColor) {
        String rgb = cssColor.substring(cssColor.indexOf("(") + 1, cssColor.indexOf(")"));
        int r = Integer.parseInt(rgb.split(",")[0].trim());
        int g = Integer.parseInt(rgb.split(",")[1].trim());
        int b = Integer.parseInt(rgb.split(",")[2].trim());
        if ((r == g) && (g == b)) return "grey";
        if ((r > 0) && (g == 0) && (b == 0)) return "red";
        return "undefined";
    }

    private boolean isBold(String fSize) {
        int iSize = Integer.parseInt(fSize);
        return iSize >= 700;
    }
}
