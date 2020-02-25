package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResultsPage extends Page {

    private By header = By.cssSelector(".h2");
    private By totalProducts = By.cssSelector(".total-products");

    public SearchResultsPage(WebDriver driver){
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    public int getNumberOfFoundProducts() {
        String numberOfProd = driver.findElement(totalProducts).getText().replaceAll("\\D+","");
        return Integer.parseInt(numberOfProd);
    }

    public String getProductNameForResult(int position){
        By productName = By.cssSelector("article:nth-child(" + position + ") > div > div.product-description > h2 > a");
        return driver.findElement(productName).getText();
    }
}
