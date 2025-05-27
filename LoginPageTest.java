package salesfource.automationTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import automation.contentpage.HomePage;
import automation.contentpage.LoginPage;
import automation.utilities.Constants;



public class LoginPageTest  extends BaseTest {

		private Logger mylog= LogManager.getLogger(LoginPageTest.class);

	 
		@Test (dataProvider = "validLogindata")
		public void LoginToSalesForce(String username, String password) throws Exception
		{
			LoginPage lp= new LoginPage(webdriver);
			lp.enterUserName(username);
			lp.enterPassword(password);
			lp.clickLoginButton();
			Thread.sleep(2000);
			Assert.assertEquals(webdriver.getCurrentUrl(),Constants.LOGIN_URL,"Valid Login");
			mylog.info("validlogin  method sucess");
			extentReportsUtility.ReportlogTestInfo("Valid user");
		}
		
		@Test (dataProvider = "validLogindata")
		public void LoginErrorMessage(String username,String password)
		{
			String orgErrorMessage;
			String ExpErrorMessage="Please enter your password.";
			LoginPage lp= new LoginPage(webdriver);
			lp.enterUserName(username);
			lp.clickLoginButton();
			orgErrorMessage=lp.getErrorMessage();
			Assert.assertEquals(orgErrorMessage, ExpErrorMessage);
			extentReportsUtility.ReportlogTestPassed("Error Message Displayed as Expected- Testcase Passed");
			mylog.info("Error Message Displayed as Expected- Testcase Passed");	
		}
		
		@Test (dataProvider = "validLogindata")
		public void RememberMe(String username,String password)
		{
			LoginPage lp = new LoginPage(webdriver);
			HomePage hp;
			
			
			lp.enterUserName(username);
			lp.enterPassword(password);
			lp.clickRememberMe();
			
			hp = lp.clickLoginButton();
			
			if (hp != null) {
				hp.getHeader().clickUserMenuDropDown();
				hp.getHeader().clickLogout();
				
				String user = lp.getUserName();
				Assert.assertEquals(user,username);
				
				extentReportsUtility.ReportlogTestPassed("RememberMe- Testcase Passed");
				mylog.info("RememberMe- Testcase Passed");	
			} else {
				extentReportsUtility.ReportlogTestFailed("RememberMe- Testcase Failed");
				mylog.info("RememberMe- Testcase Failed");	
			}
		
		}
		public void ForgotPassword()
		{
			
		}
		

}

