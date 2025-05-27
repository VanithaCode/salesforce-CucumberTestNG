package salesfource.automationTest;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.google.common.io.Files;

import automation.utilities.Constants;
import automation.utilities.ExtentReportsUtility;
import automation.utilities.Log4JUtility;
import automation.utilities.excelUtils;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
		protected static Logger mylog=LogManager.getLogger(BaseTest.class);
		protected Log4JUtility LogObject= Log4JUtility.getInstance();
		protected static final ExtentReportsUtility extentReportsUtility = ExtentReportsUtility.getInstance();
		protected static WebDriver webdriver = null;
		//private WebDriverWait wait=null;

		public void launchBrowser(String browserName) {
			switch (browserName.toLowerCase()) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				webdriver=new ChromeDriver();
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				webdriver=new FirefoxDriver();
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				webdriver=new EdgeDriver();
				break;

			default:
				break;
			}
		}

		public void goToUrl(String url){
			webdriver.get(url);
			mylog.info(url + "is entered");
		
		}
		
		public void quitDriver()
		{
			webdriver.quit();
		}
		
		public void closeDriver(){
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			webdriver.close();

		}
		
		@BeforeSuite
		public void setupBeforeSuite()
		{
			mylog=LogObject.getLogger();
			mylog.info("SetupBeforeSuite is started");	
		}
		
		
		@BeforeMethod
		@Parameters("browserName")
		public void setUpBeforeMethod(@Optional("chrome") String browserName)
		{
			System.out.println("Inside BaseTest - Before Method");

			launchBrowser(browserName);
			mylog.info("Browser Launched");
			webdriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);		// dynamic wait
			webdriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			goToUrl(Constants.SALESFORCE_URL);
			mylog.info("Application Launched");
			
			extentReportsUtility.startExtendReport();
		}
		
		@AfterMethod
		public void tearDownAfterMethod() throws Exception
		{
			closeDriver();
		}
		
		public void takeScreenshot(String filepath) {
			TakesScreenshot screenCapture=(TakesScreenshot)webdriver;
			File src= screenCapture.getScreenshotAs(OutputType.FILE);
			File destFile=new File(filepath);
			try {
				Files.copy(src, destFile);
				mylog.info("screen captured");
			} catch (IOException e) {
				e.printStackTrace();
				mylog.error("problem occured during screenshot taking");

			}
		}

		@DataProvider (name="validLogindata")
		public Object[][] Authentication() throws Exception
		{
			 Object[][] testObjArray =excelUtils.getTableArray(Constants.LOGINCREDENTIALS_EXCELPRO,Constants.LOGINCREDENTIALS_SHEETNAME,"valid");
			 
			return (testObjArray);
			
		}
		@DataProvider (name="invalidLogindata")
		public Object[][] UnAuthentication() throws Exception
		{
			
			 Object[][] testObjArray =excelUtils.getTableArray(Constants.LOGINCREDENTIALS_EXCELPRO,Constants.LOGINCREDENTIALS_SHEETNAME,"invalid");
			 
			return (testObjArray);
			
		}
	}
