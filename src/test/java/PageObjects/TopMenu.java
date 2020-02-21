package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TopMenu extends Page{

    private By home = By.cssSelector("#_desktop_logo > a > img");
    private By signIn = By.cssSelector("[title=\"Log in to your customer account\"]");
    private By signOut = By.cssSelector("#_desktop_user_info > div > a.logout.hidden-sm-down");
    private By customerAccount = By.cssSelector("#_desktop_user_info > div > a.account > span");
    private By searchInput = By.name("s");
    private By searchButton = By.cssSelector("#search_widget > form > button > i");
    private By cartIcon = By.cssSelector(".block-cart > .materials-icon");

    public TopMenu(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(signIn));
    }

    public HomePage clickHome() {
        driver.findElement(home).click();
        return new HomePage(driver);
    }

    public void clickSignOut() {
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
        return driver.findElement(signOut).isDisplayed();
    }

    public String getTextAccountName(){
        return driver.findElement(customerAccount).getText();
    }
}
