package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.Page_login;
public class DataVerificationTest {
    public static WebDriver driver;
    public static String homeUrl = "https://app.tryloop.ai/login/password";

    @BeforeTest
    public void launchHomePage() {
        try {
            ChromeOptions option = new ChromeOptions();
            option.addArguments("--remote-allow-origins=*");
            System.setProperty("webdriver.chrome.driver",
                    System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\chromedriver.exe");
            driver = new ChromeDriver(option);
            driver.manage().window().maximize();
            driver.get(homeUrl);
            driver.wait(5000);

            String expectedUrl= driver.getCurrentUrl();
            Assert.assertEquals(expectedUrl,homeUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void login() {
        Page_login pageLogin = new Page_login(driver);
        try {
            pageLogin.input_emailId.sendKeys("qa-engineer-assignment@test.com");
            pageLogin.input_password.sendKeys("QApassword123$");
            pageLogin.button_login.click();
            driver.wait(5000);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
   }