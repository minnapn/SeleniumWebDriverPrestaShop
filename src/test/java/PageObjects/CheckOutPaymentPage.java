package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckOutPaymentPage extends Page{

    By bankWire = By.id("payment-option-2");
    By conditionsBox = By.id("conditions_to_approve[terms-and-conditions]");
    By orderButton = By.cssSelector("#payment-confirmation > div.ps-shown-by-js > button");

    public CheckOutPaymentPage(WebDriver driver){
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderButton));
    }

    public void chooseBankWire() {
        driver.findElement(bankWire).click();
    }

    public void setConditionsBox(boolean check){
        if(!(driver.findElement(conditionsBox).isSelected() == check)) {
            driver.findElement(conditionsBox).click();
        }
    }

    public OrderConfirmationPage clickOrder(){
        driver.findElement(orderButton).click();
        return new OrderConfirmationPage(driver);
    }

}
