package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends Page{

    private By name = By.cssSelector("h1");
    private By price = By.cssSelector(".current-price");
    private By addToCartButton = By.cssSelector(".add-to-cart");

    public ProductPage(WebDriver driver){
       super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(name));
    }

    public ProductAddedPage clickAddToCartButton(){
        driver.findElement(addToCartButton).click();
        ProductAddedPage productAddedPage = new ProductAddedPage(driver);
        return productAddedPage;
    }

    public String getProductName(){
        return driver.findElement(name).getText();
    }

    public double getProductPrice() {
        String priceString = driver.findElement(price).getText();
        return Double.parseDouble(priceString.substring(1));
    }

}
