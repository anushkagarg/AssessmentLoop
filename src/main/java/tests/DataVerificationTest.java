package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.Page_HistoryStore;
import pages.Page_Home;
import pages.Page_login;

import java.util.ArrayList;
import java.util.List;

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
            Thread.sleep(10000);
            pageLogin.button_skipPhoneNo.click();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void validateHistoryByStore() {
        Page_Home pageHome = new Page_Home(driver);
        Page_HistoryStore pageHistoryStore = new Page_HistoryStore(driver);
        String optionToSelect = "History By Store";
        String storeOption = "Reversals";
        try {
            //opening history by store
          pageHome.dropdown_3p.click();
          Thread.sleep(5000);
          for(WebElement eValue: pageHome.list_3p){
              if(eValue.getText().equalsIgnoreCase(optionToSelect)){
                  eValue.click();
                  break;
              }
          }

          //opening reversal
          try {
              Thread.sleep(10000);
              pageHistoryStore.dropdown_reversal.click();
              for(WebElement eValue2: pageHistoryStore.list_reversals){
                  if(eValue2.getText().equalsIgnoreCase(storeOption)){
                      eValue2.click();
                      break;
                  }
              }
              //opening table and calculate
             /* double sum =0;
              List<Double> values = new ArrayList<Double>();
              for(int i = 0;i<pageHistoryStore.table_augValues.size()-1;i++){
                  String element = pageHistoryStore.table_augValues.get(i).getText().substring(1);
                          values.add(Double.parseDouble(element));
              }
              for (double i: values) {
                  sum += i;
              }
              System.out.println("Sum: " + sum);*/
              double augSum = getMonthSum(pageHistoryStore.table_augValues);
              double sepSum = getMonthSum(pageHistoryStore.table_sepValues);
              double octSum = getMonthSum(pageHistoryStore.table_octValues);
              double novSum = getMonthSum(pageHistoryStore.table_novValues);
              double decSum = getMonthSum(pageHistoryStore.table_decValues);
              double janSum = getMonthSum(pageHistoryStore.table_janValues);

              String expectedVal = pageHistoryStore.text_augMonthSum.getText();
              System.out.println("ExpectedVal: " + expectedVal);
          }catch (Exception e){
              e.printStackTrace();
          }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static double getMonthSum(List<WebElement> monthData){
        double sum = 0;
        List<Double> values = new ArrayList<Double>();

        for(int i = 0;i<monthData.size()-1;i++){
            String element = monthData.get(i).getText().substring(1);
            values.add(Double.parseDouble(element));

        }
        for (double i: values) {
            sum += i;
        }
        System.out.println("Sum: " + sum);
        return sum;
    }
   }