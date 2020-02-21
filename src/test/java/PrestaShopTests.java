import PageObjects.*;
import TestDataObjects.TestSearchString;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.Map;
import TestDataObjects.*;

public class PrestaShopTests {

    TopMenu topMenu;
    HomePage homePage;
    static Map<String, TestUser> testUsers;
    static Map<String, TestSearchString> testSearchStrings;
    static Tools driverTools;

    @BeforeClass
    public static void beforeClass() throws IOException {
        driverTools = new Tools(new ChromeDriver());
        testUsers = driverTools.loadTestUsers();
        testSearchStrings = driverTools.loadTestSerachStrings();
    }

    @Before
    public void before() {
        homePage = driverTools.toStartPage();
        topMenu = driverTools.getTopMenu();
    }


    /**
     * 1 As a customer I want to be able to register an account so I can keep track of my purchases.
     */
    @Test
    public void testCreateAccount() {
        //choosing test data
        TestUser testUser = testUsers.get("gubbe1");

        driverTools.doCreateAccount(testUser.firstName, testUser.lastName, testUser.email, testUser.password);

        //Check that login worked by sign out becoming visible
        Assert.assertTrue(topMenu.signOutVisible());

        //Check that logged in to the correct account
        Assert.assertEquals(testUser.firstName + " " + testUser.lastName, topMenu.getTextAccountName());

        //Reset for next test
        topMenu.clickSignOut();
    }


    /**
     * 2 As a customer I want to be able to buy products without creating an account so it’s simple.
     */
    @Test
    public void TestCheckoutWithoutAccount() {
        //choosing test data
        TestUser testUser = testUsers.get("gubbe2");
        int productNumber = 1;

        //Saving info about product name and price of the product that will be bought.
        String expectedProductName = homePage.getProductNameNumber(productNumber);
        double expectedPrice = homePage.getProductPriceNumber(productNumber);

        ProductPage productPage = homePage.clickProductNumber(productNumber);

        //Check that product on product page matches the product clicked.
        Assert.assertTrue(expectedProductName.equalsIgnoreCase(productPage.getProductName()));

        ProductAddedPage productAddedPage = productPage.clickAddToCartButton();

        //Check that right product is added, correct price and amount of products in cart.
        Assert.assertTrue(expectedProductName.equalsIgnoreCase(productAddedPage.getProductName()));
        Assert.assertEquals(1, productAddedPage.getAmountOfProducts());
        Assert.assertEquals(expectedPrice, productAddedPage.getPriceOfProducts(), 0.1);

        CartPage cartPage = productAddedPage.clickCheckout();

        //Check that price is correct in cart
        Assert.assertEquals(expectedPrice, cartPage.getTotalPrice(),0.1);

        CheckOutPersonalPage checkOutPersonalPage = cartPage.clickCheckOut();


        CheckOutAddressesPage checkOutAddressesPage
                = checkOutPersonalPage.setMandatoryFieldsAndContinue(testUser.firstName, testUser.lastName, testUser.email);


        //Check that name is filled with the name that was set in previous step
        Assert.assertEquals(testUser.firstName, checkOutAddressesPage.getFirstName());
        Assert.assertEquals(testUser.lastName, checkOutAddressesPage.getLastName());

        CheckOutShippingPage checkOutShippingPage
                = checkOutAddressesPage.setMandatoryFieldsAndContinue(testUser.address, testUser.postcode, testUser.city);

        CheckOutPaymentPage checkOutPaymentPage = checkOutShippingPage.clickContinue();

        checkOutPaymentPage.chooseBankWire();
        checkOutPaymentPage.setConditionsBox(true);
        OrderConfirmationPage orderConfirmationPage = checkOutPaymentPage.clickOrder();

        //Check that order confirmation has the correct price, product and email.
        Assert.assertEquals(expectedPrice, orderConfirmationPage.getTotalValue(),0.1);
        Assert.assertTrue(orderConfirmationPage.getEmailConformationText().contains(testUser.email));

        String orderConfirmationProductInfo = orderConfirmationPage.getProductInfo().toLowerCase();
        expectedProductName = expectedProductName.toLowerCase();
        Assert.assertTrue(orderConfirmationProductInfo.contains(expectedProductName));
    }


    /**
     * 3 As a customer I want to be able to login to my account at checkout so it’s fast and simple to complete a purchase.
     */
    @Test
    public void testLoginAtCheckOut() {
        //choosing test data
        TestUser testUser = testUsers.get("gubbe5");
        int productNumber = 2;

        driverTools.doCreateAccount(testUser.firstName, testUser.lastName, testUser.email, testUser.password);
        topMenu.clickSignOut();

        //Saving info about product name and price of the product that will be bought.
        String expectedProductName = homePage.getProductNameNumber(productNumber);
        Double expectedPrice = homePage.getProductPriceNumber(productNumber);

        ProductPage productPage = homePage.clickProductNumber(productNumber);

        //Check that product on product page matches the product clicked.
        Assert.assertTrue(expectedProductName.equalsIgnoreCase(productPage.getProductName()));

        ProductAddedPage productAddedPage = productPage.clickAddToCartButton();

        //Check that right product is added, correct price and amount of products in cart.
        Assert.assertTrue(expectedProductName.equalsIgnoreCase(productAddedPage.getProductName()));
        Assert.assertEquals(1, productAddedPage.getAmountOfProducts());
        Assert.assertEquals(expectedPrice, productAddedPage.getPriceOfProducts());

        CartPage cartPage = productAddedPage.clickCheckout();

        //Check that price is correct in cart
        Assert.assertEquals(expectedPrice, cartPage.getTotalPrice(), 0.1);

        CheckOutPersonalPage checkOutPersonalPage = cartPage.clickCheckOut();

        SignInAtCheckOutPage signInAtCheckOutPage = checkOutPersonalPage.clickSignIn();
        CheckOutAddressesPage checkOutAddressesPage = signInAtCheckOutPage.preformLogin(testUser.email, testUser.password);

        CheckOutShippingPage checkOutShippingPage
                = checkOutAddressesPage.setMandatoryFieldsAndContinue(testUser.address, testUser.postcode, testUser.city);
        CheckOutPaymentPage checkOutPaymentPage = checkOutShippingPage.clickContinue();
        checkOutPaymentPage.chooseBankWire();
        checkOutPaymentPage.setConditionsBox(true);
        OrderConfirmationPage orderConfirmationPage = checkOutPaymentPage.clickOrder();

        //Check that order confirmation has the correct price, product and email.
        Assert.assertEquals(expectedPrice, orderConfirmationPage.getTotalValue(),0.1);
        Assert.assertTrue(orderConfirmationPage.getEmailConformationText().contains(testUser.email));

        String orderConfirmationProductInfo = orderConfirmationPage.getProductInfo().toLowerCase();
        expectedProductName = expectedProductName.toLowerCase();
        Assert.assertTrue(orderConfirmationProductInfo.contains(expectedProductName));

        //Cleaning
        topMenu.clickSignOut();
    }



    /**
     * 4 As a customer I want to be able to change the quantity of a product at checkout so it's easy if I change my mind.
     */
    @Test
    public void testChangeQuantityAtCheckOut() {
        int productNumber = 2;

        double productPrice = homePage.getProductPriceNumber(productNumber);
        CartPage cartPage = driverTools.doAddProductToCart(productNumber);

        //Check that number of products and price changes correct while increasing amount of products
        for(int i = 1; i < 5; i++) {
            Assert.assertEquals(i, cartPage.getTotalNrOfItems());
            Assert.assertEquals(productPrice*i, cartPage.getTotalPrice(), 0.1);
            cartPage.increaseQuantity();
        }

        //Check that number of products and price changes correct while decreasing amount of products
        for(int i = 5; i > 0; i--) {
            Assert.assertEquals(i, cartPage.getTotalNrOfItems());
            Assert.assertEquals(productPrice*i, cartPage.getTotalPrice(), 0.1);
            cartPage.decreaseQuantity();
        }

        //Check that error message occurs when trying to decrease from 1.
        Assert.assertTrue(cartPage.getErrorMessage().contains("The minimum purchase order quantity"));

        cartPage.clickDelete();

        //Check that product got deleted.
        Assert.assertTrue(cartPage.noMoreItemsVisible());
    }



    /**
     * 5 As a customer I want to be able to search for a product so it's easy to find what I'm looking for.
     */
    @Test
    public void testSearchProduct() {
        TestSearchString testData = testSearchStrings.get("bear");

        SearchResultsPage searchResultsPage = topMenu.searchCatalog(testData.searchString);
        String searchString = testData.searchString.toLowerCase();

        int foundProducts = searchResultsPage.getNumberOfFoundProducts();

        //Check if number of found results is as expected
        Assert.assertEquals(testData.expectedResults, foundProducts);

        //Check if products found contains the searched string
        for(int i = 1; i <= foundProducts; i++) {
            String foundProductName = searchResultsPage.getProductNameForResult(i).toLowerCase();
            Assert.assertTrue(foundProductName.contains(searchString));
        }

    }


    @AfterClass
    public static void afterClass() {
        //driver.quit();
    }

}