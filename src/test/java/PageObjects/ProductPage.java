package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ItemPage {
    WebDriverWait wait;
    WebDriver driver;

    By name = By.cssSelector("#main > div.row > div:nth-child(2) > h1");// By.cssSelector("[itemprop=\"name\"]");
    By price = By.cssSelector("#main > div.row > div:nth-child(2) > div.product-prices > div.product-price.h5.has-discount > div > span:nth-child(1)");//By.cssSelector("[itemprop=\"price\"]");
    By addToCartButton = By.cssSelector("#add-to-cart-or-refresh > div.product-add-to-cart > div > div.add > button");

    public ItemPage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void clickAddToCartButton(){
        driver.findElement(addToCartButton).click();
    }

    public String getItemName(){
        return driver.findElement(name).getText();
    }

    public String getItemPrice() {
        return driver.findElement(price).getText();
    }

    public void waitForItemDescriptionVisibile() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(name));
    }
}
