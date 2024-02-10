package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_login {
    WebDriver driver ;
    public Page_login(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//div[@data-testid='login-email']//input")
    public WebElement input_emailId;

    @FindBy(xpath = "//div[@data-testid='login-password']//input")
    public WebElement input_password;
    @FindBy(xpath = "//button[@data-testid='login-button']")
    public WebElement button_login;

    @FindBy(xpath = "//button[contains(text(),'Skip for now')]")
    public WebElement button_skipPhoneNo;
}
