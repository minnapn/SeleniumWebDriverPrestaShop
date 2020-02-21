package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends Page{


    By header = By.cssSelector("h1");
    By checkOut = By.linkText("PROCEED TO CHECKOUT");
    By errorMessage = By.cssSelector("#notifications > div > article");
    By numberOfItems = By.cssSelector("#cart-subtotal-products > span.label.js-subtotal");
    By increase = By.cssSelector(".touchspin-up");
    By decrease = By.cssSelector(".touchspin-down");
    By delete = By.cssSelector(".remove-from-cart > .material-icons");
    By zeroItems = By.cssSelector(".no-items");
    By totalPrice = By.cssSelector(".cart-summary-totals > div > span.value");


    public CartPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    public double getTotalPrice() {
        String priceString = driver.findElement(totalPrice).getText();
        return Double.parseDouble(priceString.substring(1));
    }

    public void increaseQuantity(){
        int items = getTotalNrOfItems();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(increase)).click();
        wait.until(ExpectedConditions.textToBe(numberOfItems, items+1 + " items"));
    }

    public void decreaseQuantity(){
        int items = getTotalNrOfItems();
        wait.until(ExpectedConditions.presenceOfElementLocated(decrease)).click();
        if (items > 2) {
            wait.until(ExpectedConditions.textToBe(numberOfItems, items - 1 + " items"));
        }
        else {
            wait.until(ExpectedConditions.textToBe(numberOfItems,"1 item"));
        }
    }

    public boolean noMoreItemsVisible(){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(zeroItems));
            return true;
        }
        catch (Exception e) {
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

    public int getTotalNrOfItems(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(numberOfItems));
        String nrOfItemsText = driver.findElement(numberOfItems).getText();
        return subStringUntilFirstSpace(nrOfItemsText);
    }

    public int subStringUntilFirstSpace(String string){
        String newString = "";
        for (int i = 0; i < string.length(); i++){
            if (string.charAt(i) != ' ')
            {
                newString = newString + string.charAt(i);
            }
            else{
                return Integer.parseInt(newString);
            }
            }
        return Integer.parseInt(newString);
        }


    public String getErrorMessage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return driver.findElement(errorMessage).getText();
    }
}
