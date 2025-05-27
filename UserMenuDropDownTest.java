package salesfource.automationTest;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automation.contentpage.HomePage;
import automation.contentpage.LoginPage;
import automation.utilities.Constants;
import automation.utilities.ReadpropertyFileUtility;
import automation.utilities.excelUtils;

public class UserMenuDropDownTest extends BaseTest {
	private  Logger mylog= LogManager.getLogger(UserMenuDropDownTest.class);
	private LoginPage lp;
	private HomePage hp;
	
	@BeforeMethod
	public void homepagesetup() throws Exception
	{
		System.out.println("Inside UserMenuDropDownTest - Before Method");
		
		 Object[][] testObjArray =excelUtils.getTableArray(Constants.LOGINCREDENTIALS_EXCELPRO,Constants.LOGINCREDENTIALS_SHEETNAME,"valid");
		 String username =(String) testObjArray[0][0];
		 String password=(String) testObjArray[0][1];
		 lp = new LoginPage(webdriver);
		 lp.enterUserName(username);
		 lp.enterPassword(password);
		 hp = lp.clickLoginButton();

	}
	
	@Test 
	void  userMenuValidating()
	{
			extentReportsUtility.ReportlogTestInfo("UserDropdownmenu Test started");
			System.out.println("Inside UserMenuDropDownTest - userMenuValidating");

			hp.getHeader().clickUserMenuDropDown();
			List<String> actualList =hp.getHeader().getUserMenuList();
			System.out.println("Inside UserMenuDropDownTest - actualList: " + actualList);
			List<String> origianlInputList = Arrays.asList(ReadpropertyFileUtility.readDatdFromPropertyFile (Constants.PROPERTIESFILE_DIR,"USERMENU_DROPDOWN").split("/"));   //String into list  using Arrays.asList()	
			Collections.sort(actualList);
			Collections.sort(origianlInputList);
			Assert.assertEquals(actualList,origianlInputList,"List do not have the same content");
			mylog.info("User menu drop down menu are validated- Testcase Passed ");
			extentReportsUtility.ReportlogTestPassed("User menu drop down menu are validated- Testcase Passed ");
	}
}
