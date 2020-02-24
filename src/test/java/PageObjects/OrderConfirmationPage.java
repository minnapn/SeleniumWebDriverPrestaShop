package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrderConfirmationPage extends Page {

    private By header = By.cssSelector("h3");
    private By totalValue = By.cssSelector(".total-value > td:nth-child(2)");
    private By productInfo = By.cssSelector(".col-sm-4 > span");
    private By emailConfirmation = By.cssSelector("p");


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
