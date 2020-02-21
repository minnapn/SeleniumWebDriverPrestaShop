package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductAddedPage extends Page{

    By checkoutButton = By.cssSelector(".cart-content-btn > .btn-primary");
    By amountOfProducts = By.cssSelector("p.cart-products-count");
    By priceOfProducts = By.cssSelector("span.value");
    By productName = By.cssSelector("h6");


    public ProductAddedPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutButton));
    }

    public CartPage clickCheckout() {
        driver.findElement(checkoutButton).click();
        return new CartPage(driver);
    }

    public Double getPriceOfProducts() {
        String priceString = driver.findElement(priceOfProducts).getText();
        return Double.parseDouble(priceString.substring(1));
    }

    public String getProductName() {
        return driver.findElement(productName).getText();
    }

    public int getAmountOfProducts() {
        String stringAmount = driver.findElement(amountOfProducts).getText().replaceAll("\\D+","");
        return Integer.parseInt(stringAmount);


    }
}
