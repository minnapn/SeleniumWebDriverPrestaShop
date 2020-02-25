package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends Page{

    private By sectionTitle = By.cssSelector("#content > section > h2");

    public HomePage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(sectionTitle));
    }

    public static HomePage goToHomePage(WebDriver driver){
        driver.get("http://demo.prestashop.com");
        driver.switchTo().frame("framelive");
        return new HomePage(driver);
    }

    public String getProductNameForPosition(int position){
        String productName = driver.findElement(By.cssSelector("article:nth-child("+position+") > div > div.product-description > h3 > a")).getText();
        productName = productName.substring(0,productName.length()-3); //removing last 3 characters because if name is long the last three chars are dots.
        return productName;
    }

    public ProductPage clickProductInPosition(int position){
        driver.findElement(By.cssSelector("article:nth-child("+position+") > div > a > img")).click();
        return new ProductPage(driver);
    }

    public double getProductPriceForPosition(int position) {
        String priceString = driver.findElement(By.cssSelector("article:nth-child(" + position + ") > div > div.product-description > div > span.price")).getText();
        return Double.parseDouble(priceString.substring(1));
    }
}
