package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends Page{

    private By header = By.cssSelector("h1");
    private By checkOut = By.linkText("PROCEED TO CHECKOUT");
    private By errorMessage = By.cssSelector("#notifications > div > article");
    private By nmbrOfItems = By.cssSelector(".js-subtotal");
    private By totalPrice = By.cssSelector(".cart-summary-totals > div > span.value");
    private By zeroItems = By.cssSelector(".no-items");
    private By increase = By.cssSelector(".touchspin-up");
    private By decrease = By.cssSelector(".touchspin-down");
    private By delete = By.cssSelector(".remove-from-cart > .material-icons");

    public CartPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    public double getTotalPrice() {
        String priceString = driver.findElement(totalPrice).getText();
        return Double.parseDouble(priceString.substring(1));
    }

    public void increaseQuantity(){
        int items = getTotalNmbrOfItems();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(increase)).click();
        wait.until(ExpectedConditions.textToBe(nmbrOfItems, items+1 + " items"));
    }

    public void decreaseQuantity(){
        int items = getTotalNmbrOfItems();
        wait.until(ExpectedConditions.presenceOfElementLocated(decrease)).click();
        if (items > 2) {
            wait.until(ExpectedConditions.textToBe(nmbrOfItems, items - 1 + " items"));
        }
        else {
            wait.until(ExpectedConditions.textToBe(nmbrOfItems,"1 item"));
        }
    }

    public boolean noMoreItemsVisible(){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(zeroItems));
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public void clickDelete() {
        driver.findElement(delete).click();
    }

    public CheckOutPersonalPage clickCheckOut() {
        driver.findElement(checkOut).click();
        return new CheckOutPersonalPage(driver);
    }

    public int getTotalNmbrOfItems(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(nmbrOfItems));
        String nmbrOfItemsText = driver.findElement(nmbrOfItems).getText().replaceAll("\\D+","");
        int nmbrOfItems = Integer.parseInt(nmbrOfItemsText);
        return nmbrOfItems;
    }

    public String getErrorMessage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return driver.findElement(errorMessage).getText();
    }
}
