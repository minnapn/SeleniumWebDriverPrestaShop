package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignInPage extends Page{

    private By header = By.cssSelector("h1");
    private By emailInput = By.name("email");
    private By passwordInput = By.name("password");
    private By signInButton = By.id("submit-login");
    private By createNewAccountLink = By.className("no-account");


    public SignInPage(WebDriver driver){
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    public void setEmail(String email){
        driver.findElement(emailInput).sendKeys(email);
    }

    public void setPassword(String password){
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickSignIn(){
        driver.findElement(signInButton).click();
    }

    public void preformLogin(String email, String password){
        setEmail(email);
        setPassword(password);
        clickSignIn();
    }

    public CreateAccountPage clickCreateAccount() {
        driver.findElement(createNewAccountLink).click();
        return new CreateAccountPage(driver);
    }

}
