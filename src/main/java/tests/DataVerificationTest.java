package tests;

import org.openqa.selenium.By;
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
import pages.Page_login;

import java.text.DecimalFormat;
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
            Thread.sleep(15000);
            pageLogin.button_skipPhoneNo.click();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void validateSumOfAllStoresPerMonth() {
        Page_Home pageHome = new Page_Home(driver);
        Page_HistoryStore pageHistoryStore = new Page_HistoryStore(driver);
        String optionToSelect = "History By Store";
        String storeOption = "Reversals";
        try {
            //opening history by store
          pageHome.dropdown_3p.click();
          Thread.sleep(10000);
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
              for(WebElement eOption: pageHistoryStore.list_reversals){
                  if(eOption.getText().equalsIgnoreCase(storeOption)){
                      eOption.click();
                      break;
                  }
              }
              scrollToElementByAction(driver,pageHistoryStore.dropdown_rowsPagination);
              Thread.sleep(5000);
              pageHistoryStore.dropdown_rowsPagination.click();
              for(WebElement ePaginationOption: pageHistoryStore.dropdown_rowsPaginationOptions){
                  if(ePaginationOption.getText().equalsIgnoreCase("50 rows")){
                      ePaginationOption.click();
                      break;
                  }
              }
              //opening table and calculate
              double fullSum = getMonthSum(pageHistoryStore.table_augValues,pageHistoryStore.table_sepValues,
                      pageHistoryStore.table_octValues,pageHistoryStore.table_novValues,
                      pageHistoryStore.table_decValues,pageHistoryStore.table_janValues,
                      pageHistoryStore.table_febValues);


              String expectedVal = pageHistoryStore.text_grandTotalSum.getText().substring(1).replace(",", "");;
              double dExpectedVal = Double.parseDouble(expectedVal);
              if(fullSum == dExpectedVal){
                  System.out.println("Grand total equals to UI sum. Calculated sum: "+fullSum+" UI sum: " + dExpectedVal);
              }
              else{
                  System.out.println("Grand total not equals to UI sum. Calculated sum: "+fullSum+" UI sum: " + dExpectedVal);
              }
              Assert.assertEquals(fullSum,dExpectedVal);
          }catch (Exception e){
              e.printStackTrace();
          }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static double getMonthSum(List<WebElement> augValues,List<WebElement> sepValues,
                                     List<WebElement> octValues,List<WebElement> novValues,
                                     List<WebElement> decValues,List<WebElement> janValues,
                                     List<WebElement> febValues){
        Page_HistoryStore pageHistoryStore = new Page_HistoryStore(driver);
        double augSum = 0,sepSum = 0,octSum = 0,novSum = 0,decSum = 0,janSum = 0,febSum = 0;
        double totalSum = 0;
        List<Double> augData = new ArrayList<Double>();
        List<Double> sepData = new ArrayList<Double>();
        List<Double> octData = new ArrayList<Double>();
        List<Double> novData = new ArrayList<Double>();
        List<Double> decData = new ArrayList<Double>();
        List<Double> janData = new ArrayList<Double>();
        List<Double> febData = new ArrayList<Double>();
        boolean flag = true;
        try{
            while(flag){
                for(int i = 0;i<augValues.size()-1;i++){
                    String augElement = augValues.get(i).getText().substring(1).replace(",", "");
                    String sepElement = sepValues.get(i).getText().substring(1).replace(",", "");
                    String octElement = octValues.get(i).getText().substring(1).replace(",", "");
                    String novElement = novValues.get(i).getText().substring(1).replace(",", "");
                    String decElement = decValues.get(i).getText().substring(1).replace(",", "");
                    String janElement = janValues.get(i).getText().substring(1).replace(",", "");
                    String febElement = febValues.get(i).getText().substring(1).replace(",", "");

                    augData.add(Double.parseDouble(augElement));
                    sepData.add(Double.parseDouble(sepElement));
                    octData.add(Double.parseDouble(octElement));
                    novData.add(Double.parseDouble(novElement));
                    decData.add(Double.parseDouble(decElement));
                    janData.add(Double.parseDouble(janElement));
                    febData.add(Double.parseDouble(febElement));
                }
                Thread.sleep(5000);
                if (!pageHistoryStore.button_nextData.getAttribute("class").contains("disabled")) {
                    pageHistoryStore.button_nextData.click();
                    Thread.sleep(1000);
                } else {
                    flag = false;
                    System.out.println("In the last page");
                }
            }

            for (double i: augData) {
                augSum += i;
                DecimalFormat df = new DecimalFormat("#.##");
                augSum = Double.valueOf(df.format(augSum));
            }
            for (double i: sepData) {
                sepSum += i;
                DecimalFormat df = new DecimalFormat("#.##");
                sepSum = Double.valueOf(df.format(sepSum));
            }
            for (double i: octData) {
                octSum += i;
                DecimalFormat df = new DecimalFormat("#.##");
                octSum = Double.valueOf(df.format(octSum));
            }
            for (double i: novData) {
                novSum += i;
                DecimalFormat df = new DecimalFormat("#.##");
                novSum = Double.valueOf(df.format(novSum));
            }
            for (double i: decData) {
                decSum += i;
                DecimalFormat df = new DecimalFormat("#.##");
                decSum = Double.valueOf(df.format(decSum));
            }
            for (double i: janData) {
                janSum += i;
                DecimalFormat df = new DecimalFormat("#.##");
                janSum = Double.valueOf(df.format(janSum));
            }
            for (double i: febData) {
                febSum += i;
                DecimalFormat df = new DecimalFormat("#.##");
                febSum = Double.valueOf(df.format(febSum));
            }
            System.out.println("Aug Sum: " + augSum);
            System.out.println("Sep Sum: " + sepSum);
            System.out.println("Oct Sum: " + octSum);
            System.out.println("Nov Sum: " + novSum);
            System.out.println("Dec Sum: " + decSum);
            System.out.println("Jan Sum: " + janSum);
            System.out.println("Feb Sum: " + febSum);

             totalSum= augSum + sepSum+octSum+novSum+decSum+janSum+febSum;
            System.out.println("total Sum calculated: " + totalSum);
            String expectedAugVal = pageHistoryStore.text_augMonthSum.getText();
            String expectedSepVal = pageHistoryStore.text_septMonthSum.getText();
            String expectedOctVal = pageHistoryStore.text_octMonthSum.getText();
            String expectedNovVal = pageHistoryStore.text_novMonthSum.getText();
            String expectedDecVal = pageHistoryStore.text_decMonthSum.getText();
            String expectedJanVal = pageHistoryStore.text_janMonthSum.getText();
            String expectedFebVal = pageHistoryStore.text_febMonthSum.getText();

            //assert here all month values


        }catch (Exception e){
            e.printStackTrace();
        }

        return totalSum;
    }
    public static boolean scrollToElementByAction(WebDriver driver, WebElement element) throws Exception {
        boolean scrollActionDone = false;
        try {

            Actions action = new Actions(driver);
            action.moveToElement(element);
            scrollActionDone = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while scrolling to element.");
        }

        return scrollActionDone;
    }
   }