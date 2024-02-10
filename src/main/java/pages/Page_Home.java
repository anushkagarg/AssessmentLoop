package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page_Home {
    WebDriver driver ;
    public Page_Home(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//ul[@class='MuiList-root MuiList-padding css-uoijyg']/li/div")
    public WebElement dropdown_3p;

    @FindBy(xpath = "//ul[@class='MuiList-root MuiList-padding css-uoijyg']/div//a/div/span")
    public List<WebElement> list_3p;
}
