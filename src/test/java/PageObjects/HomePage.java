package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends Page{

    By sectionTitle = By.cssSelector("#content > section > h2");

    public HomePage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(sectionTitle));
    }

    public By getProductNumber(int i){
        //return By.cssSelector(".product-miniature:nth-child(" + i + ")img");
        return By.cssSelector("#content > section > div > article:nth-child("+i+") > div > a > img");
    }

    public String getProductNameNumber(int i){
        return driver.findElement(By.cssSelector("#content > section > div > article:nth-child("+i+") > div > div.product-description > h3 > a")).getText();
        //return driver.findElement(By.cssSelector(".product-miniature:nth-child(" + i +").product-description a")).getText();
    }

    public ProductPage clickProductNumber(int i){
        driver.findElement(getProductNumber(i)).click();
        return new ProductPage(driver);
    }

    public double getProductPriceNumber(int i) {
        String priceString = driver.findElement(By.cssSelector("#content > section > div > article:nth-child(" + i + ") > div > div.product-description > div > span.price")).getText();
        //String priceString = driver.findElement(By.cssSelector(".product-miniature:nth-child(" + i +").price")).getText();
        return Double.parseDouble(priceString.substring(1));
    }
}
