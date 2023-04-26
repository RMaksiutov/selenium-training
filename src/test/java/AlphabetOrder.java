import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlphabetOrder {

    static WebDriver driver;
    static WebDriverWait wait;
    static String urlCountries = "http://localhost/litecart/admin/?app=countries&doc=countries";
    static String urlGeoZones = "http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones";
    static String xPathCountryNames = "//table[@class]//tr/td[not(@style)]/a";
    static String xPathCountryRows = "//tr[@class='row']";
    static String xPathCountryGeoZones = "//table[@id='table-zones']//tr/td[3]";
    static String xPathGeoZoneContries = "//tr[@class='row']//a[not(@title)]";
    static String xPathGeoZones = "//option[@selected='selected' and not(@data-phone-code)]";

    @BeforeAll
    public static void start() {
        driver = BaseClass.createDriver("chrome");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    public static void stop() {
        driver.quit();
        driver = null;
    }

    @Test
    @Order(1)
    public void sortCountries() {
        BaseClass.loginAdmin(driver, wait);
        driver.navigate().to(urlCountries);
        List<WebElement> wECountryNames = driver.findElements(By.xpath(xPathCountryNames));
        ArrayList<String> countryNames = BaseClass.getStringNames(wECountryNames);
        assertTrue(BaseClass.checkAlphabetOrder(countryNames), "Alphabet order check failed!");
    }

    @Test
    @Order(2)
    public void sortCountryGeoZones() {
        ArrayList<String> linksWithGeoZones = new ArrayList<String>();
        //BaseClass.loginAdmin(driver, wait)
        driver.navigate().to(urlCountries);
        List<WebElement> wECountryRows = driver.findElements(By.xpath(xPathCountryRows));
        for (WebElement cRow : wECountryRows) {
            int zoneCol = Integer.parseInt(cRow.findElement(By.xpath("./td[6]")).getText());
            if (zoneCol > 0) {
                System.out.println("Zones count: " + zoneCol);
                linksWithGeoZones.add(cRow.findElement(By.xpath(".//a")).getAttribute("href"));
            }
        }
        for (String link : linksWithGeoZones) {
            driver.navigate().to(link);
            List<WebElement> wECountryGeoZoneNames = driver.findElements(By.xpath(xPathCountryGeoZones));
            int size = wECountryGeoZoneNames.size();
            if (wECountryGeoZoneNames.get(size-1).findElements(By.xpath(".//input[@type='text']")).size() > 0)
                wECountryGeoZoneNames.remove(size-1);
            ArrayList<String> countryGeoZoneNames = BaseClass.getStringNames(wECountryGeoZoneNames);
            assertTrue(BaseClass.checkAlphabetOrder(countryGeoZoneNames), "Alphabet order check failed!");
        }
    }

    @Test
    @Order(3)
    public void sortGeoZones() {
        //driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        //BaseClass.loginAdmin(driver, wait);
        driver.navigate().to(urlGeoZones);
        List<WebElement> wECountryNames = driver.findElements(By.xpath(xPathGeoZoneContries));
        ArrayList<String> countryNames = BaseClass.getStringNames(wECountryNames);
        for (String c : countryNames) {
            driver.findElement(By.xpath("//td/a[text()='" + c + "']")).click();
            List<WebElement> wEZones = driver.findElements(By.xpath(xPathGeoZones));
            ArrayList<String> zoneNames = BaseClass.getStringNames(wEZones);
            assertTrue(BaseClass.checkAlphabetOrder(zoneNames), "Alphabet order check failed!");
            driver.navigate().to("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        }
    }
}
