package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckOutAddressesPage extends Page{

    private By firstNameInput = By.name("firstname");
    private By lastNameInput = By.name("lastname");
    private By addressInput = By.name("address1");
    private By postcodeInput = By.name("postcode");
    private By cityInput = By.name("city");
    private By continueButton = By.cssSelector("#delivery-address > div > footer > button");

    public CheckOutAddressesPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(addressInput));
    }

    public void setAddress(String address) {
        driver.findElement(addressInput).sendKeys(address);
    }

    public void setCity(String city) {
        driver.findElement(cityInput).sendKeys(city);
    }

    public void setPostcode(String postcode){
        driver.findElement(postcodeInput).sendKeys(postcode);
    }

    public String getFirstName() {
        return driver.findElement(firstNameInput).getAttribute("value");
    }

    public String getLastName() {
        return driver.findElement(lastNameInput).getAttribute("value");
    }

    public CheckOutShippingPage setMandatoryFieldsAndContinue(String address, String postcode, String city){
        setAddress(address);
        setPostcode(postcode);
        setCity(city);
        return clickContinue();
    }

    public CheckOutShippingPage clickContinue() {
        driver.findElement(continueButton).click();
        return new CheckOutShippingPage(driver);
    }

}
