package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Transactions {
    WebDriver driver ;
    public Page_Transactions(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "(//button[@data-testid='selectBtn']//following-sibling::span)[1]")
    public WebElement dropdown_locations;
    @FindBy(xpath = "//div[@aria-label='Artisan Alchemy']/span")
    public WebElement checkbox_firstLocation;
    @FindBy(xpath = "//div[@aria-label='Blissful Buffet']/span")
    public WebElement checkbox_secondLocation;
    @FindBy(xpath = "(//button[@data-testid='selectBtn']//following-sibling::span/span[contains(text(),'All')])[1]")
    public WebElement dropdown_marketplace;
    @FindBy(xpath = "//div[@aria-label='Grubhub']/span")
    public WebElement checkbox_marketplaceOption;
    @FindBy(xpath = "//button[contains(text(),'Clear')]")
    public WebElement button_clearOptionSelected;
    @FindBy(xpath = "//button[@data-testid='applyBtn']")
    public WebElement button_applyFilter;
    @FindBy(xpath = "//table[@class='MuiTable-root css-15i8i05-MuiTable-root']")
    public WebElement table_transactions;
}
