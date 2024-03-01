package com.Pages;

import com.Utilities.TestUtility;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.BaseClass.TestBase;

public class LoginPage extends TestBase 
{
	//Page Factory or Object Repository Where we store all WebElements.
	//@FindBy will automatically do => driver.findElement(By.name(""));
	@FindBy(xpath = "//input [@type='text']")
	WebElement userNameField;
	
	@FindBy(xpath = "//input [@type='password']")
	WebElement passwordField;
	
	@FindBy(xpath="//button [@type='submit']") //Custom XPath We are Using here
	WebElement loginButton;
	

	//Initializing [Page Objects] all Object Repositories Elements with help of Page Factory in Constructor
	//We initialize Page Factory Using initElements(driver, this) //This refers to Current Class Object
	public LoginPage()
	{
		PageFactory.initElements(driver, this);
	}
	
	//Verify the Title of the Login Page
	public String validateLoginPageTitle()
	{
		return driver.getTitle();
	}
	
	public HomePage login(String username, String password) throws InterruptedException {
		userNameField.sendKeys(username);
		passwordField.sendKeys(password);
		//loginButton.click();
		TestUtility.clickElementByJS(loginButton, driver);
		Thread.sleep(TestUtility.Page_Load_TimeOut);

		return new HomePage(); //Since Login Page is landing on HomePage
	}
}

