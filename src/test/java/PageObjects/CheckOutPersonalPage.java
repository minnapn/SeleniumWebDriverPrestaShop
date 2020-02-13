package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckOutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By firstNameInput = By.name("firstname");
    private By lastNameInput = By.name("lastname");
    private By emailInput = By.name("email");
    private By passwordInput = By.name("password");
    private By continueButton = By.name("continue");
    private By gdprCheckBox = By.name("psgdpr");

    private By header = By.cssSelector("#checkout-personal-information-step > h1");

    public CheckOutPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void waitForHeaderVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    public void setGdprCheckBox(boolean check) {
        if(!(driver.findElement(gdprCheckBox).isSelected() == check)){
            driver.findElement(gdprCheckBox).click();
        }
    }

    public void populateEmail(String email){
        driver.findElement(emailInput).sendKeys(email);
    }

    public void populatePassword(String password){
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void populatefirstName(String firstName){
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void populatelastName(String lastName){
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void clickContinue(){
        driver.findElement(continueButton).click();
    }


}
