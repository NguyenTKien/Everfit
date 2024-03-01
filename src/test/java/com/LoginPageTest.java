package com;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.BaseClass.TestBase;
import com.Pages.HomePage;
import com.Pages.LoginPage;

public class LoginPageTest extends TestBase
{	
	LoginPage loginPage;
	HomePage homePage;

	public LoginPageTest()
	{
		super();
	}
	
	@BeforeClass
	public void setUp()
	{
		initialization();
		Log.info("Browser Launched Successfully");
		
		loginPage = new LoginPage(); //Here we create objects to access methods from other Class.
	}
	
	@Test(priority=1, invocationCount = 1)
	public void loginTest() throws InterruptedException {
		homePage = loginPage.login(property.getProperty("Username"),property.getProperty("Password"));
		Log.info("Successfully Logged into CRM Application");
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
		Log.info("Browser Terminated");
	}
}
