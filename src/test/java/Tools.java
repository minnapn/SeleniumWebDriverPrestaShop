import PageObjects.*;
import TestDataObjects.TestSearchString;
import TestDataObjects.TestUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Tools {
    public WebDriver driver;

    public Tools(WebDriver driver) {
        this.driver = driver;
    }

    public Map<String, TestUser> loadTestUsers() throws IOException {
        String testUserJson = new String(Files.readAllBytes(Paths.get("TestUser.json")));
        Type mapType = new TypeToken<HashMap<String, TestUser>>(){}.getType();
        return new Gson().fromJson(testUserJson, mapType);
    }

    public HomePage toStartPage() {
        driver.get("http://demo.prestashop.com");
        driver.switchTo().frame("framelive");
        return new HomePage(driver);
    }

    public TopMenu getTopMenu() {
        return new TopMenu(driver);
    }

    public void doCreateAccount(String firstName, String lastName, String email, String password) {
        TopMenu topMenu = new TopMenu(driver);
        SignInPage signInPage = topMenu.clickSignIn();
        CreateAccountPage createAccountPage = signInPage.clickCreateAccount();
        createAccountPage.createAccount(firstName, lastName, email, password);
    }

    public CartPage doAddProductToCart(int nr) {
        HomePage homePage = new HomePage(driver);
        ProductPage productPage = homePage.clickProductNumber(nr);
        ProductAddedPage productAddedPage = productPage.clickAddToCartButton();
        return productAddedPage.clickCheckout();
    }

    public OrderConfirmationPage doCheckOutFromCart(CartPage cartPage, String firstName, String lastName, String email, String password, String address, String postcode, String city) {
        CheckOutPersonalPage checkOutPersonalPage = cartPage.clickCheckOut();

        CheckOutAddressesPage checkOutAddressesPage
                = checkOutPersonalPage.setMandatoryFieldsAndContinue(firstName, lastName, email);

        CheckOutShippingPage checkOutShippingPage
                = checkOutAddressesPage.setMandatoryFieldsAndContinue(address, postcode, city);

        CheckOutPaymentPage checkOutPaymentPage = checkOutShippingPage.clickContinue();

        checkOutPaymentPage.chooseBankWire();
        checkOutPaymentPage.setConditionsBox(true);
        return checkOutPaymentPage.clickOrder();

    }

    public Map<String, TestSearchString> loadTestSerachStrings() throws IOException {
        String testSearchStringsJson = new String(Files.readAllBytes(Paths.get("TestSearchStrings.json")));
        Type mapType2 = new TypeToken<HashMap<String, TestSearchString>>(){}.getType();
        return new Gson().fromJson(testSearchStringsJson, mapType2);

    }

}

