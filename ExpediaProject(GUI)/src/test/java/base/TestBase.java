package base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	public static WebDriver driver;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public ExtentTest logger;
	
	@Parameters("browser")
	@BeforeTest
	public static void intialization(String browser) {
		if(browser.equalsIgnoreCase("chrome")) {
			Reporter.log("=====Chrome Browser Session Started=====", true);
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			
		}
		else if(browser.equalsIgnoreCase("firefox")){
	
			driver = new FirefoxDriver();
		}
		
		else if(browser.equalsIgnoreCase("Edge")){
			
			driver = new EdgeDriver();
		}
		
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}
	
	@AfterTest
	public void endReport() {
		extent.flush();
		driver.quit();
	}

}
