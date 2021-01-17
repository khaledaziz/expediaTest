package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	
WebDriver driver;
public WebDriverWait wait;
	
	@FindBy(xpath="//span[text()='Flights']")
	public WebElement flights_TAB;
	
	@FindBy(xpath = "//button[@aria-label='Leaving from']")
	public WebElement dep_BTN;
	
	@FindBy(xpath = "//input[@id='location-field-leg1-origin']")
	public WebElement dep_TXT;
	
	@FindBy(xpath = "//button[@aria-label='Going to']")
	public WebElement arr_BTN;
	
	@FindBy(xpath = "//input[@id='location-field-leg1-destination']")
	public WebElement arr_TXT;
	
	@FindBy(xpath = "//button[text()='Search']")
	public WebElement search_BTN;
	
	public HomePage(WebDriver driver){

        this.driver = driver;
        wait = new WebDriverWait(driver,30);
        PageFactory.initElements(driver, this);

    }

}
