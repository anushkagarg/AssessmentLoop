package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_HistoryStore {
    WebDriver driver ;
    public Page_HistoryStore(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//div[@id='drilldown-options-fc']")
    public WebElement dropdown_reversal;

    @FindBy(xpath = "//ul[@id=':rd:']/li/p")
    public List<WebElement> list_reversals;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr/td[1]")
    public List<WebElement> table_storeNames;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr/td[2]")
    public List<WebElement> table_augValues;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr/td[3]")
    public List<WebElement> table_sepValues;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr/td[4]")
    public List<WebElement> table_octValues;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr/td[5]")
    public List<WebElement> table_novValues;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr/td[6]")
    public List<WebElement> table_decValues;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr/td[7]")
    public List<WebElement> table_janValues;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr/td[8]")
    public List<WebElement> table_febValues;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr[8]/td[2]")
    public WebElement text_augMonthSum;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr[8]/td[3]")
    public WebElement text_septMonthSum;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr[8]/td[4]")
    public WebElement text_octMonthSum;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr[8]/td[5]")
    public WebElement text_novMonthSum;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr[8]/td[6]")
    public WebElement text_decMonthSum;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr[8]/td[7]")
    public WebElement text_janMonthSum;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr[8]/td[8]")
    public WebElement text_febMonthSum;
    @FindBy(xpath = "//table[@class='MuiTable-root css-l6sbfr-MuiTable-root']/tbody/tr[8]/td[10]")
    public WebElement text_grandTotalSum;
    @FindBy(xpath = "//div[@class='MuiFormControl-root css-1nrlq1o-MuiFormControl-root']")
    public WebElement dropdown_rowsPagination;
    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> dropdown_rowsPaginationOptions;
    @FindBy(xpath = "//button[@data-testid='pagination-next']")
    public WebElement button_nextData;

}
