package tests;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.Page_HistoryStore;
import pages.Page_Home;
import pages.Page_Transactions;
import pages.Page_login;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

            String expectedUrl = driver.getCurrentUrl();
            Assert.assertEquals(expectedUrl, homeUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 0)
    public void login() {
        Page_login pageLogin = new Page_login(driver);
        String actualUrl = "https://app.tryloop.ai/home";
        try {
            pageLogin.input_emailId.sendKeys("qa-engineer-assignment@test.com");
            pageLogin.input_password.sendKeys("QApassword123$");
            pageLogin.button_login.click();
            Thread.sleep(15000);
            pageLogin.button_skipPhoneNo.click();

            String expectedUrl = driver.getCurrentUrl();
            Assert.assertEquals(expectedUrl, actualUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1)
    public void generateCsvFileAndOrganize() {
        Page_Home pageHome = new Page_Home(driver);
        Page_HistoryStore pageHistoryStore = new Page_HistoryStore(driver);
        Page_Transactions oPageTransactions = new Page_Transactions(driver);
        String optionToSelect = "Transactions";
        try {
            //opening transactions
            Thread.sleep(5000);
            pageHome.dropdown_3p.click();
            Thread.sleep(5000);
            for (WebElement eValue : pageHome.list_3p) {
                if (eValue.getText().equalsIgnoreCase(optionToSelect)) {
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

                //selecting number of rows to be displayed
                pageHistoryStore.dropdown_rowsPagination.click();
                for (WebElement ePaginationOption : pageHistoryStore.dropdown_rowsPaginationOptions) {
                    if (ePaginationOption.getText().equalsIgnoreCase("30 rows")) {
                        ePaginationOption.click();
                        break;
                    }
                }
                // Extract column names from the header row
                List<WebElement> headerCells = oPageTransactions.table_transactions.findElements(By.xpath(".//th"));
                List<String> columnNames = new ArrayList<>();
                for (WebElement headerCell : headerCells) {
                    columnNames.add(headerCell.getText());
                }
                List<WebElement> rows = oPageTransactions.table_transactions.findElements(By.tagName("tr"));

                // store the extracted data
                List<String[]> data = new ArrayList<>();
                String[] previousRowData = new String[columnNames.size()]; //8

                for (int i = 1; i < rows.size(); i++) {
                    List<WebElement> columns = rows.get(i).findElements(By.xpath(".//td"));
                    String[] rowData = new String[columnNames.size()];
                    int x = columns.size();
                    int y = columnNames.size();
                    int z = y - x;
                    if (y >= x) {
                        for (int j = 0; j < z; j++) {
                            rowData[j] = previousRowData[j];
                        }
                    }
                    for (int k = 0; k < columns.size(); k++) {
                        rowData[k + z] = columns.get(k).getText();
                    }

                    previousRowData = Arrays.copyOf(rowData, rowData.length);
                    data.add(rowData);
                }
                // Sort the data array by order id
                data.sort(Comparator.comparing(o -> o.length > 0 ? o[0] : ""));

                // Write the sorted data to a CSV file
                try (CSVWriter writer = new CSVWriter(new FileWriter("output_sorted_1.csv"))) {
                    writer.writeNext(columnNames.toArray(new String[0]));
                    for (String[] rowData : data) {
                        writer.writeNext(rowData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 2)
    public void validateDownloadedExcel() {
            Page_Transactions oPageTransactions = new Page_Transactions(driver);
            try {
                //download excel
                oPageTransactions.button_download.isDisplayed();
                oPageTransactions.button_download.click();

                try {
                    Thread.sleep(10000);
                    String excelFilePath = "C:\\Users\\Anushka Garg\\Downloads\\chargebacks_payouts_overview.csv";
                    String csvFilePath = "C:\\Users\\Anushka Garg\\Desktop\\receipts\\Assignment_1\\output_sorted_1.csv";
                    int excelColumnToCompare = 0;
                    int csvColumnToCompare = 0;

                    double accuracy = compareCsvData(excelFilePath, csvFilePath, excelColumnToCompare, csvColumnToCompare);
                    DecimalFormat df = new DecimalFormat("#.##");
                    accuracy = Double.valueOf(df.format(accuracy));
                    System.out.println("Accuracy: " + (accuracy * 100) + "%");

                    driver.quit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    public static double  compareCsvData(String csvFilePath1, String csvFilePath2, int columnToCompare,int columnToCompare1) {
        try {
            CSVReader csvReader1 = new CSVReader(new FileReader(csvFilePath1));
            CSVReader csvReader2 = new CSVReader(new FileReader(csvFilePath2));

            String[] csvRow1;
            String[] csvRow2;
            int totalEntries = 0;
            int matchingEntries = 0;
            int rowNumber = 0;

            while ((csvRow1 = csvReader1.readNext()) != null && (csvRow2 = csvReader2.readNext()) != null) {
                rowNumber++;
                totalEntries++;

                String value1 = csvRow1[columnToCompare].trim();
              String value2 = csvRow2[columnToCompare1].trim().substring(1);
                // Compare values
                if (!value1.equals(value2)) {
                    System.out.println("Mismatch found in row " + rowNumber + ", column " + columnToCompare + ": "
                            + "CSV1 value - " + value1 + ", CSV2 value - " + value2);
                }else{
                    matchingEntries++;
                    System.out.println("Match found in row " + rowNumber + ", column " + columnToCompare + ": "
                            + "CSV1 value - " + value1 + ", CSV2 value - " + value2);
                }
            }
            csvReader1.close();
            csvReader2.close();
            // Calculate accuracy
            if (totalEntries > 0) {
                return (double) matchingEntries / totalEntries;
            } else {
                return 0.0; // Avoid division by zero
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
    @AfterTest
    public void quitDriver()
    {
        driver.quit();
    }
}