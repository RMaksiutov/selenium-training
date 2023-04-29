import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRegistration {
    static WebDriver driver;
    static WebDriverWait wait;

    static String urlMain = "http://localhost/litecart/en/";
    //static String xPathNewUserLink = "//form[@name='login_form']//tr[5]//a";
    static String xPathNewUserLink = "//a[contains(@href, 'create_account')]";
    static String xPathLogoutLink = "//a[contains(@href, 'logout')]";
    static String email = "";
    static String password = "";


    @BeforeAll
    public static void start() {
        //driver = BaseClass.createDriver("chrome");
        driver = BaseClass.createDriver("ff");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    public static void stop() {
        driver.quit();
        driver = null;
    }

    @Test
    public void userRegistration() {
        driver.navigate().to(urlMain);
        driver.findElement(By.xpath(xPathNewUserLink)).click();
        fillCreateAccountForm(driver);
        driver.findElement(By.cssSelector("button[name=\"create_account\"]")).click();
        driver.findElement(By.xpath(xPathLogoutLink)).click();
        driver.findElement(By.cssSelector("input[name=\"email\"]"))
                .sendKeys(email);
        driver.findElement(By.cssSelector("input[name=\"password\"]"))
                .sendKeys(password);
        driver.findElement(By.cssSelector("button[name=\"login\"]")).click();
        driver.findElement(By.xpath(xPathLogoutLink)).click();
        //assertTrue(BaseClass.checkAlphabetOrder(countryNames), "Alphabet order check failed!");
    }

    private void fillCreateAccountForm(WebDriver d) {
        email = "mail"
                + BaseClass.getRandomNumber(0)
                + "@mail.rr";
        password = "123456";

        d.findElement(By.cssSelector("input[name=\"firstname\"]"))
                .sendKeys("Name" + BaseClass.getRandomNumber(3));
        d.findElement(By.cssSelector("input[name=\"lastname\"]"))
                .sendKeys("Lastname" + BaseClass.getRandomNumber(3));
        d.findElement(By.cssSelector("input[name=\"address1\"]"))
                .sendKeys("Address 1, " + BaseClass.getRandomNumber(6));
        d.findElement(By.cssSelector("input[name=\"postcode\"]"))
                .sendKeys("12345");
        d.findElement(By.cssSelector("input[name=\"city\"]"))
                .sendKeys("New York");
        selectCountry(d);
        selectZoneCode(d);
        d.findElement(By.cssSelector("input[name=\"email\"]"))
                .sendKeys(email);
        d.findElement(By.cssSelector("input[name=\"phone\"]"))
                .sendKeys("+" + BaseClass.getRandomNumber(11));
        d.findElement(By.cssSelector("input[name=\"password\"]"))
                .sendKeys(password);
        d.findElement(By.cssSelector("input[name=\"confirmed_password\"]"))
                .sendKeys(password);
    }

    private void selectZoneCode(WebDriver d) {
        WebElement wEZoneCode = d.findElement(By.cssSelector("select[name=\"zone_code\"]"));
        Select selectZoneCode = new Select(wEZoneCode);
        selectZoneCode.selectByVisibleText("Minnesota");
    }

    private void selectCountry(WebDriver d) {
        //d.findElement(By.cssSelector(".selection")).click();
        //d.findElement(By.cssSelector("span[class=\"selection\"]")).click();
        WebElement e = d.findElement(By.cssSelector("span[role=\"presentation\"]"));
        e.click();
        d.findElement(By.cssSelector("input[type=\"search\"]")).sendKeys("United States");
        d.findElement(By.cssSelector("input[type=\"search\"]")).sendKeys(Keys.RETURN);
    }

}
