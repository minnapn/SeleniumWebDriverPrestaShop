package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TopMenu {
    WebDriver driver;
    WebDriverWait wait;

    private By signIn = By.cssSelector("[title=\"Log in to your customer account\"]");




    public TopMenu(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
    }

    public SignInPage clickSignIn() {
        driver.findElement(signIn).click();
        SignInPage signInPage = new SignInPage(driver);
        signInPage.waitForHeaderVisible();
        return signInPage;

    }


    public void waitForSignInVisible(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(signIn));
    }


}
