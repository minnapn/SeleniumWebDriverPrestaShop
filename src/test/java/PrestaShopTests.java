import PageObjects.*;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.sql.Time;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import TestDataObjects.*;

public class PrestaShopTests {

    private static WebDriver driver;
    private static TopMenu topMenu;
    private static HomePage homePage;
    private static Map<String, TestUser> testUsers;
    private static Map<String, Integer> testSearchStrings;

    @BeforeClass
    public static void beforeClass(){
        driver = new ChromeDriver();
        testUsers = TestDataSetUp.loadTestUsers();
        testSearchStrings = TestDataSetUp.loadSearchStrings();
    }

    @Before
    public void setup() {
        homePage = HomePage.goToHomePage(driver);
        topMenu = new TopMenu(driver);
    }

    @After
    public void  tearDown() {
        //Teardown tests
    }

    /**
     * 1 As a customer I want to be able to register an account so I can keep track of my purchases.
     */
    @Test
    public void testCreateAccount() {
        //choosing test data
        TestUser testUser = testUsers.get("theSwede");

        createAccount(testUser.firstName, testUser.lastName, testUser.email, testUser.password);

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
        TestUser testUser = testUsers.get("theLongie");
        int productPosition = 1; //The position on the page of the product that will be bought

        //Saving info about product name and price of the product that will be bought.
        final String expectedProductName = homePage.getProductNameNumber(productPosition);
        final double expectedPrice = homePage.getProductPriceNumber(productPosition);

        CartPage cartPage
                = addProductAndProceedToCart(productPosition, expectedProductName, expectedPrice);

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
        // TODO Add comment
        String orderConfirmationProductInfo = orderConfirmationPage.getProductInfo().toLowerCase();
        Assert.assertTrue(orderConfirmationProductInfo.contains(expectedProductName.toLowerCase()));
    }


    /**
     * 3 As a customer I want to be able to login to my account at checkout so it’s fast and simple to complete a purchase.
     */
    @Test
    public void testLoginAtCheckOut() {
        //choosing test data
        TestUser testUser = testUsers.get("theShortie");
        int productPosition = 2; //The position on the page of the product that will be bought

        createAccount(testUser.firstName, testUser.lastName, testUser.email, testUser.password);
        topMenu.clickSignOut();

        //Saving info about product name and price of the product that will be bought.
        final String expectedProductName = homePage.getProductNameNumber(productPosition);
        final double expectedPrice = homePage.getProductPriceNumber(productPosition);

        CartPage cartPage
                = addProductAndProceedToCart(productPosition, expectedProductName, expectedPrice);

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
        Assert.assertTrue(orderConfirmationProductInfo.contains(expectedProductName.toLowerCase()));

        //Cleaning
        topMenu.clickSignOut();
    }



    /**
     * 4 As a customer I want to be able to change the quantity of a product at checkout so it's easy if I change my mind.
     */
    @Test
    public void testChangeQuantityAtCheckOut() {
        final int productPosition = 2; //The position on the page of the product that will be added to cart

        //Saving info about product name and price of the product that will be bought.
        final double productPrice = homePage.getProductPriceNumber(productPosition);
        final String productName = homePage.getProductNameNumber(productPosition);

        CartPage cartPage
                = addProductAndProceedToCart(productPosition, productName, productPrice);
        //Check that number of products and price changes correct while increasing amount of products
        for(int nbrOfItems = 1; nbrOfItems < 5; nbrOfItems++) {
            Assert.assertEquals(nbrOfItems, cartPage.getTotalNrOfItems());
            Assert.assertEquals(productPrice*nbrOfItems, cartPage.getTotalPrice(), 0.1);
            cartPage.increaseQuantity();
        }

        //Check that number of products and price changes correct while decreasing amount of products
        for(int nbrOfItems = 5; nbrOfItems > 0; nbrOfItems--) {
            Assert.assertEquals(nbrOfItems, cartPage.getTotalNrOfItems());
            Assert.assertEquals(productPrice*nbrOfItems, cartPage.getTotalPrice(), 0.1);
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
        final String searchString = "bear";
        final int expectedNbrOfHits = testSearchStrings.get("bear");

        SearchResultsPage searchResultsPage = topMenu.searchCatalog(searchString);

        int foundProducts = searchResultsPage.getNumberOfFoundProducts();

        //Check if number of found results is as expected
        Assert.assertEquals(expectedNbrOfHits, foundProducts);

        //Check if products found contains the searched string
        for(int productPosition = 1; productPosition <= foundProducts; productPosition++) {
            String foundProductName = searchResultsPage.getProductNameForResult(productPosition).toLowerCase();
            Assert.assertTrue(foundProductName.contains(searchString));
        }

    }


    @AfterClass
    public static void afterClass() {
        //driver.quit();
    }



    public void createAccount(String firstName, String lastName, String email, String password) {
        SignInPage signInPage = topMenu.clickSignIn();
        CreateAccountPage createAccountPage = signInPage.clickCreateAccount();
        createAccountPage.createAccount(firstName, lastName, email, password);
    }


    public CartPage addProductAndProceedToCart(int productNumber, String expectedProductName, double expectedPrice) {
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
        return cartPage;
    }
}