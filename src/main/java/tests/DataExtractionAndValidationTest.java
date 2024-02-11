package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.Page_HistoryStore;
import pages.Page_Home;
import pages.Page_Transactions;
import pages.Page_login;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DataExtractionAndValidationTest {
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
            Thread.sleep(5000);

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
            Thread.sleep(15000);
            pageLogin.button_skipPhoneNo.click();

            String expectedUrl= driver.getCurrentUrl();
            Assert.assertEquals(expectedUrl,homeUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void validateCsvFile() {
        Page_Home pageHome = new Page_Home(driver);
        Page_HistoryStore pageHistoryStore = new Page_HistoryStore(driver);
        Page_Transactions oPageTransactions = new Page_Transactions(driver);
        String optionToSelect = "Transactions";
        String storeOption = "Reversals";
        try {
            //opening transactions
          pageHome.dropdown_3p.click();
          Thread.sleep(10000);
          for(WebElement eValue: pageHome.list_3p){
              if(eValue.getText().equalsIgnoreCase(optionToSelect)){
                  eValue.click();
                  break;
              }
          }


          try {
              Thread.sleep(10000);
              //Selecting locations
              oPageTransactions.dropdown_locations.click();
              oPageTransactions.button_clearOptionSelected.click();
              oPageTransactions.checkbox_firstLocation.click();
              oPageTransactions.checkbox_secondLocation.click();
              oPageTransactions.button_applyFilter.click();
              Thread.sleep(5000);

              //Selecting marketplace
              oPageTransactions.dropdown_marketplace.click();
              oPageTransactions.button_clearOptionSelected.click();
              oPageTransactions.checkbox_marketplaceOption.click();
              oPageTransactions.button_applyFilter.click();


          }catch (Exception e){
              e.printStackTrace();
          }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

   }