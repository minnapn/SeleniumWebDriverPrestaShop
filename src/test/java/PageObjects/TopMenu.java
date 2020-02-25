package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TopMenu extends Page{

    private By homeLink = By.id("_desktop_logo");
    private By signIn = By.cssSelector("[title=\"Log in to your customer account\"]");
    private By signOut = By.cssSelector(".logout.hidden-sm-down");
    private By customerAccount = By.cssSelector("a.account");
    private By searchInput = By.name("s");
    private final By searchButton = By.cssSelector("#search_widget > form > button");
    private By cartIcon = By.id("_desktop_cart");
    private By itemsInCart = By.cssSelector("span.cart-products-count");

    public TopMenu(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(signIn));
    }

    public HomePage clickHome() {
        driver.findElement(homeLink).click();
        return new HomePage(driver);
    }

    public CartPage clickCartIcon() {
        driver.findElement(cartIcon).click();
        return new CartPage(driver);
    }

    public int getNmbrOfItemsInCart(){
        String nmbrOfItemsInCart = driver.findElement(itemsInCart).getText().replaceAll("\\D+","");
        return Integer.parseInt(nmbrOfItemsInCart);
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

    public boolean signOutVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 1);
            wait.until(ExpectedConditions.visibilityOfElementLocated(signOut));
            return true;
        }
        catch(Exception e) { return false; }
    }

    public String getTextAccountName(){
        return driver.findElement(customerAccount).getText();
    }
}
