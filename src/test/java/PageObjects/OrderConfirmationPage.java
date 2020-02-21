package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrderConfirmationPage extends Page {

    //By header = By.cssSelector("#content-hook_order_confirmation > div > div > div > h3");
    By header = By.cssSelector("h3");
    By totalValue = By.cssSelector(".total-value > td:nth-child(2)");
    By productInfo = By.cssSelector(".col-sm-4 > span");
    By emailConfirmation = By.cssSelector("p");


    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    public double getTotalValue(){
        String valueString = driver.findElement(totalValue).getText();
        return Double.parseDouble(valueString.substring(1));
    }

    public String getProductInfo(){
        return driver.findElement(productInfo).getText();
    }

    public String getEmailConformationText() {
        return driver.findElement(emailConfirmation).getText();
    }
}
