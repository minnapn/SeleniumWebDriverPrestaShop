import PageObjects.*;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Map;

import TestDataObjects.*;

public class PrestaShopTests {

    private static WebDriver driver;
    private static TopMenu topMenu;
    private static HomePage homePage;
    private static Map<String, TestUser> testUsers;
    private static Map<String, TestSearch> testSearchStrings;

    @BeforeClass
    public static void beforeClass(){
        driver = new ChromeDriver();
        testUsers = TestDataSetUp.loadTestUsers();
        testSearchStrings = TestDataSetUp.loadSearchStrings();
    }

    @Before
    public void setUp() {
        homePage = HomePage.goToHomePage(driver);
        topMenu = new TopMenu(driver);
    }

    @After
    public void  tearDown() {
        if (topMenu.getNmbrOfItemsInCart() > 0) {
            CartPage cartPage = topMenu.clickCartIcon();
            cartPage.clickDelete();
        }
        if (topMenu.signOutVisible()) {
            topMenu.clickSignOut();
        }
    }

    @AfterClass
    public static void afterClass() {
        driver.quit();
    }

    /**
     * 1 As a customer I want to be able to register an account so I can keep track of my purchases.
     */
    @Test
    public void testCreateAccount() {
        //choosing test data
        TestUser testUser = testUsers.get("theLong");

        createAccountFromTopMenu(testUser.firstName, testUser.lastName, testUser.email, testUser.password);
        //Check that login worked by sign out becoming visible
        Assert.assertTrue(topMenu.signOutVisible());
        //Check that logged in to the correct account
        Assert.assertEquals(testUser.firstName + " " + testUser.lastName, topMenu.getTextAccountName());

    }


    /**
     * 2 As a customer I want to be able to buy products without creating an account so it’s simple.
     */
    @Test
    public void TestCheckoutWithoutAccount() {
        //choosing test data
        TestUser testUser = testUsers.get("theSwede");
        int productPosition = 1; //The position on the page of the product that will be bought (1-8)

        //Saving info about product name and price of the product that will be bought.
        final String expectedProductName = homePage.getProductNameForPosition(productPosition);
        final double expectedPrice = homePage.getProductPriceForPosition(productPosition);

        CartPage cartPage = addProductAndProceedToCart(productPosition, expectedProductName, expectedPrice); //This method also contains asserts.

        CheckOutAddressesPage checkOutAddressesPage = cartPage
                .clickCheckOut()
                .setMandatoryFieldsAndContinue(testUser.firstName, testUser.lastName, testUser.email);
        //Check that name is filled with the name that was set in previous step
        Assert.assertEquals(testUser.firstName, checkOutAddressesPage.getFirstName());
        Assert.assertEquals(testUser.lastName, checkOutAddressesPage.getLastName());

        OrderConfirmationPage orderConfirmationPage = checkOutAddressesPage
                .setMandatoryFieldsAndContinue(testUser.address, testUser.postcode, testUser.city)
                .clickContinue()
                .chooseBankWire()
                .setConditionsBox(true)
                .clickOrder();
        //Check that order confirmation has the correct price, product and email.
        Assert.assertEquals(expectedPrice, orderConfirmationPage.getTotalValue(),0.1);
        Assert.assertTrue(orderConfirmationPage.getEmailConformationText().contains(testUser.email));
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
        int productPosition = 2; //The position on the page of the product that will be bought (1-8)

        createAccountFromTopMenu(testUser.firstName, testUser.lastName, testUser.email, testUser.password); //Creating the account that will be logged in to at check out
        topMenu.clickSignOut();

        //Saving info about product name and price of the product that will be bought.
        final String expectedProductName = homePage.getProductNameForPosition(productPosition);
        final double expectedPrice = homePage.getProductPriceForPosition(productPosition);

        CartPage cartPage = addProductAndProceedToCart(productPosition, expectedProductName, expectedPrice); //This method also contains asserts.

        OrderConfirmationPage orderConfirmationPage = cartPage
                .clickCheckOut()
                .clickSignIn()
                .preformLogin(testUser.email, testUser.password)
                .setMandatoryFieldsAndContinue(testUser.address, testUser.postcode, testUser.city)
                .clickContinue()
                .chooseBankWire()
                .setConditionsBox(true)
                .clickOrder();
        //Check that order confirmation has the correct price, product and email.
        Assert.assertEquals(expectedPrice, orderConfirmationPage.getTotalValue(),0.1);
        Assert.assertTrue(orderConfirmationPage.getEmailConformationText().contains(testUser.email));
        String orderConfirmationProductInfo = orderConfirmationPage.getProductInfo().toLowerCase();
        Assert.assertTrue(orderConfirmationProductInfo.startsWith(expectedProductName.toLowerCase()));
    }


    /**
     * 4 As a customer I want to be able to change the quantity of a product at checkout so it's easy if I change my mind.
     */
    @Test
    public void testChangeQuantityAtCheckOut() {
        final int productPosition = 4; //The position on the page of the product that will be added to cart (1-8).

        //Saving info about product name and price of the product that will be added to cart.
        final double productPrice = homePage.getProductPriceForPosition(productPosition);
        final String productName = homePage.getProductNameForPosition(productPosition);

        CartPage cartPage = addProductAndProceedToCart(productPosition, productName, productPrice); //This method also contains asserts.

        //Check that number of products and price changes correct while increasing amount of items
        for(int nbrOfItems = 1; nbrOfItems < 5; nbrOfItems++) {
            Assert.assertEquals(nbrOfItems, cartPage.getTotalNmbrOfItems());
            Assert.assertEquals(productPrice*nbrOfItems, cartPage.getTotalPrice(), 0.1);
            cartPage.increaseQuantity();
        }

        //Check that number of products and price changes correct while decreasing amount of items
        for(int nbrOfItems = 5; nbrOfItems > 0; nbrOfItems--) {
            Assert.assertEquals(nbrOfItems, cartPage.getTotalNmbrOfItems());
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
        //choosing test data
        final TestSearch search = testSearchStrings.get("bear");

        SearchResultsPage searchResultsPage = topMenu.searchCatalog(search.searchString);
        int foundProducts = searchResultsPage.getNumberOfFoundProducts();
        //Check if number of found results is as expected
        Assert.assertEquals(search.expectedNmbOfHits, foundProducts);

        //Check if products found contains the searched string
        for(int productPosition = 1; productPosition <= foundProducts; productPosition++) {
            String foundProductName = searchResultsPage.getProductNameForResult(productPosition).toLowerCase();
            Assert.assertTrue(foundProductName.contains(search.searchString));
        }
    }




    public void createAccountFromTopMenu(String firstName, String lastName, String email, String password) {
        topMenu.clickSignIn()
                .clickCreateAccount()
                .createAccount(firstName, lastName, email, password);
    }


    public CartPage addProductAndProceedToCart(int productNumber, String expectedProductName, double expectedPrice) {

        ProductPage productPage = homePage.clickProductInPosition(productNumber);
        //Check that product on product page matches the product clicked.
        Assert.assertTrue(productPage.getProductName().toLowerCase().startsWith(expectedProductName.toLowerCase()));

        ProductAddedPage productAddedPage = productPage.clickAddToCartButton();
        //Check that right product is added, correct price and amount of products in cart.
        Assert.assertTrue(productAddedPage.getProductName().toLowerCase().startsWith(expectedProductName.toLowerCase()));
        Assert.assertEquals(1, productAddedPage.getAmountOfProducts());
        Assert.assertEquals(expectedPrice, productAddedPage.getPriceOfProducts(), 0.1);

        CartPage cartPage = productAddedPage.clickCheckout();
        //Check that price is correct in cart
        Assert.assertEquals(expectedPrice, cartPage.getTotalPrice(),0.1);

        return cartPage;
    }
}