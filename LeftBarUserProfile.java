package automation.leftbar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import automation.base.BaseLeftBar;

public class LeftBarUserProfile extends BaseLeftBar{
	public LeftBarUserProfile(WebDriver driver)
	{
		super(driver);
	}

@FindBy(xpath="//*[@id='tailBreadcrumbNode']") WebElement username;
	
	public String getusername()
	{
		return getText(username,"leftconner username");
		
	}
	public String getUserTitle()
	{
		return getTitleAttribute(username,"username");
	}
}
