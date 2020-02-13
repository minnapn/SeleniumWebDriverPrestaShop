import PageObjects.CreateAccountPage;
import PageObjects.SignInPage;
import PageObjects.TopMenu;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PrestaShopTests {

    static WebDriver driver;
    static TopMenu topMenu;

    @BeforeClass
    public static void beforeClass(){
        driver = new ChromeDriver();
        topMenu = new TopMenu(driver);
    }

    @Before
    public void before() throws InterruptedException {
        driver.get("http://demo.prestashop.com/#/en/front");
        driver.switchTo().frame("framelive");
        topMenu.waitForSignInVisible();
    }

    @Test
    public void firstTest(){
        TopMenu topMenu = new TopMenu(driver);
        SignInPage signInPage = topMenu.clickSignIn();
        CreateAccountPage createAccountPage = signInPage.clickCreateAccount();
        createAccountPage.setGdprCheckBox(true);
        createAccountPage.clickSaveButton();
        //signInPage.preformLogin("test@test.se","testing");
    }

    @AfterClass
    public static void afterClass() {
        //driver.quit();
    }
}