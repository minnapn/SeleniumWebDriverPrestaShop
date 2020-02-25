package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CreateAccountPage extends Page{

    private By header = By.cssSelector("#main > header > h1");
    private By firstNameInput = By.name("firstname");
    private By lastNameInput = By.name("lastname");
    private By emailInput = By.name("email");
    private By passwordInput = By.name("password");
    private By saveButton = By.cssSelector("#customer-form > footer > button");
    private By gdprCheckBox = By.name("psgdpr");


    public CreateAccountPage(WebDriver driver){
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    public void clickSaveButton(){
        driver.findElement(saveButton).click();
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

    public HomePage createAccount(String firsName, String lastName, String email, String password) {
        setEmail(email);
        setFirstName(firsName);
        setLastName(lastName);
        setPassword(password);
        setGdprCheckBox(true);
        clickSaveButton();
        return new HomePage(driver);
    }





}
