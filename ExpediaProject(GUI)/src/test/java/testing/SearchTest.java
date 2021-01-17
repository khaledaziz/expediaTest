package testing;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import base.TestBase;
import helper.ExcelRead;
import pages.HomePage;
import pages.SearchResultsPage;

public class SearchTest extends TestBase{
	
	HomePage HomeSearch;
	SearchResultsPage SearchResult;	
	
	@BeforeTest
	public void setup() throws IOException {

		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/target/ExpediaSearchTestReport.html");
		// Create an object of Extent Reports
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setDocumentTitle("Title of the Report Comes here ");
		// Name of the report
		htmlReporter.config().setReportName("Name of the Report Comes here ");
		// Dark Theme
		htmlReporter.config().setTheme(Theme.DARK);

	}
	
	
	
	@Test(dataProvider = "searchData")
	public void testSearch(String depCity, String arrCity) throws InterruptedException {
		logger = extent.createTest("Test round trip search in expedia");
		HomeSearch = new HomePage(driver);
		SearchResult = new SearchResultsPage(driver);
		driver.get("https://www.expedia.com");
		HomeSearch.wait.until(ExpectedConditions.visibilityOf(HomeSearch.flights_TAB));
		HomeSearch.flights_TAB.click();
		HomeSearch.dep_BTN.click();
		HomeSearch.dep_TXT.sendKeys(depCity);
		HomeSearch.dep_TXT.sendKeys(Keys.ENTER);
		HomeSearch.arr_BTN.click();
		HomeSearch.arr_TXT.sendKeys(arrCity);
		HomeSearch.arr_TXT.sendKeys(Keys.ENTER);
		HomeSearch.search_BTN.click();
		HomeSearch.wait.until(ExpectedConditions.visibilityOf(SearchResult.sortList_DRP));
		Select sortList = new Select(SearchResult.sortList_DRP);
		sortList.selectByVisibleText("Arrival (Earliest)");
		
		String Actual = driver.getTitle().toLowerCase();
        String Expected = depCity.toLowerCase() +" to " + arrCity.toLowerCase() + " flights";
        Assert.assertEquals(Expected,Actual);
	}
	
	@DataProvider (name = "searchData" )
    public String[][] dp() throws IOException {
		String path = System.getProperty("user.dir") + "/data/searchData.xlsx";		
	    String mySheet = "input";
		ExcelRead testData = new ExcelRead();
        String data[][] = testData.retrieveMyData(path, mySheet);
        return data;
   }
	
	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			logger.log(Status.FAIL,
					MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));	
			String screenshotPath = getScreenShot(driver, result.getName());
			logger.fail("Test Case Failed Snapshot is below " + logger.addScreenCaptureFromPath(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
		}
	}
	
	public static String getScreenShot(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}


}
