import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MySecondTask extends MyFirstTask{
    private final String subMenuXPath = "//li[@class='selected']/ul/li";

    @Test
    public void investigateAdminPage() {
        int menuSize = 0;
        int subMenuSize = 0;
        loginAdmin();
        menuSize = driver.findElements(By.id("app-")).size();
        for (int i = 0; i < menuSize; i++) {
            driver.findElements(By.id("app-")).get(i).click();
            System.out.println("Success!");
            subMenuSize = driver.findElements(By.xpath(subMenuXPath)).size();
            if (subMenuSize > 0) {
                for (int j = 0; j < subMenuSize; j++) {
                    driver.findElements(By.xpath(subMenuXPath)).get(j).click();
                    List<WebElement> tagName = driver.findElements(By.tagName("h1"));
                    assertTrue(tagName.size() > 0, "Tag h1 not exist on the page");
                    System.out.println(tagName.get(0).getText() + " menu sub item clicked");
                }
            }
        }
    }

    @Test
    public void stickersCheck() {
        driver.navigate().to("http://localhost/litecart/en/");
        wait.until(titleIs("Online Store | My Store"));
        //List<WebElement> ducks = driver.findElements(By.xpath("//li[@class='product column shadow hover-light']"));
        List<WebElement> ducks = driver.findElements(By.cssSelector("li[class*=\"product\"]"));
        System.out.println("Ducks found: " + ducks.size());
        for (WebElement duck : ducks) {
            //stickers += duck.findElements(By.xpath(".//div[@class='sticker new']")).size();
            //stickers += duck.findElements(By.xpath(".//div[@class='sticker sale']")).size();
            int stickers = duck.findElements(By.cssSelector(".sticker")).size();
            if (stickers == 1)
                System.out.println("Sticker found: " + duck.findElement(By.cssSelector(".sticker")).getAttribute("className"));
            assertTrue(stickers==1, "Sticker check failed. Stickers found: " + stickers);
        }
    }

}