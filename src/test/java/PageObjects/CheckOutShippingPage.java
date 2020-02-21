package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckOutShippingPage extends Page{

    By continueButton = By.name("confirmDeliveryOption");

    public CheckOutShippingPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueButton));
    }

    public CheckOutPaymentPage clickContinue() {
        driver.findElement(continueButton).click();
        return new CheckOutPaymentPage(driver);
    }
}
