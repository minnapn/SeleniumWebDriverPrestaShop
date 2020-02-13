package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignInPage {
    private WebDriver driver;
    private WebDriverWait wait;

    By header = By.cssSelector("#main > header > h1");
    By emailInput = By.name("email");
    By passwordInput = By.name("password");
    By signInButton = By.id("submit-login");
    By createNewAccountLink = By.className("no-account");


    public SignInPage(WebDriver driver){
        this.driver = driver;
        wait = wait = new WebDriverWait(driver, 20);
    }

    public void waitForHeaderVisible(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    public void populateEmail(String email){
        driver.findElement(emailInput).sendKeys(email);
    }

    public void populatePassword(String password){
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickSignIn(){
        driver.findElement(signInButton).click();
    }

    public void preformLogin(String email, String password){
        populateEmail(email);
        populatePassword(password);
        clickSignIn();
    }

    public CreateAccountPage clickCreateAccount() {
        driver.findElement(createNewAccountLink).click();
        CreateAccountPage createAccountPage = new CreateAccountPage(driver);
        createAccountPage.waitForHeaderVisible();
        return createAccountPage;
    }

}
