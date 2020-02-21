package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignInAtCheckOutPage extends Page{

        By emailInput = By.cssSelector("#login-form > section > div:nth-child(2) > div.col-md-6 > input");
        By passwordInput = By.cssSelector("#login-form > section > div:nth-child(3) > div.col-md-6 > div > input");
        By signInButton = By.cssSelector("#login-form > footer > button");


        public SignInAtCheckOutPage(WebDriver driver) {
            super(driver);
            wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        }

        public void setEmail(String email){
            driver.findElement(emailInput).sendKeys(email);
        }

        public void setPassword(String password){
            driver.findElement(passwordInput).sendKeys(password);
        }

        public CheckOutAddressesPage clickSignIn(){
            driver.findElement(signInButton).click();
            return new CheckOutAddressesPage(driver);
        }

        public CheckOutAddressesPage preformLogin(String email, String password){
            setEmail(email);
            setPassword(password);
            return clickSignIn();
        }


    }



