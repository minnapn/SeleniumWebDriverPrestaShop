package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TopMenu extends Page{

    private By homeLink = By.id("_desktop_logo");
    private By signIn = By.cssSelector("[title=\"Log in to your customer account\"]");
    private By signOut = By.cssSelector(".logout.hidden-sm-down");
    private By customerAccount = By.cssSelector("a.account");
    private By searchInput = By.name("s");
    private final By searchButton = By.cssSelector("#search_widget > form > button");
    private By cartIcon = By.cssSelector(".block-cart > .materials-icon");


    public TopMenu(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(signIn));
    }

    public HomePage clickHome() {
        driver.findElement(homeLink).click();
        return new HomePage(driver);
    }

    public void clickSignOut() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signOut));
        driver.findElement(signOut).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(signIn));
    }

    public SignInPage clickSignIn() {
        driver.findElement(signIn).click();
        return new SignInPage(driver);
    }

    public SearchResultsPage searchCatalog(String searchString) {
        driver.findElement(searchInput).sendKeys(searchString);
        driver.findElement(searchButton).click();
        return new SearchResultsPage(driver);
    }

    public boolean cartClickable() {
        return driver.findElement(cartIcon).isEnabled();
    }

    public boolean signOutVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signOut));
        return driver.findElement(signOut).isDisplayed();
    }

    public String getTextAccountName(){
        return driver.findElement(customerAccount).getText();
    }
}
