package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckOutPersonalPage extends Page{

    private By firstNameInput = By.name("firstname");
    private By lastNameInput = By.name("lastname");
    private By emailInput = By.name("email");
    private By passwordInput = By.name("password");
    private By continueButton = By.name("continue");
    private By gdprCheckBox = By.name("psgdpr");
    private By signIn = By.linkText("Sign in");
    private By header = By.cssSelector("#checkout-personal-information-step > h1");

    public CheckOutPersonalPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    public void setGdprCheckBox(boolean check) {
        if(!(driver.findElement(gdprCheckBox).isSelected() == check)){
            driver.findElement(gdprCheckBox).click();
        }
    }

    public void setEmail(String email){
        driver.findElement(emailInput).sendKeys(email);
    }

    public void setPassword(String password){
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void setFirstName(String firstName){
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void setLastName(String lastName){
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public CheckOutAddressesPage setMandatoryFieldsAndContinue(String firstName, String lastName, String email){
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setGdprCheckBox(true);
        return clickContinue();
    }

    public CheckOutAddressesPage clickContinue(){
        driver.findElement(continueButton).click();
        return new CheckOutAddressesPage(driver);
    }

    public SignInAtCheckOutPage clickSignIn() {
        driver.findElement(signIn).click();
        return new SignInAtCheckOutPage(driver);
    }


}
