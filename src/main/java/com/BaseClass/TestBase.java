package com.BaseClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class TestBase
{
	public static WebDriver driver; 
	public static Properties property; //Making public So that we can use in all Child Classes.
	public static Logger Log;
		
	//Using Base Class we're achieving Inheritance Concept from Java.
	public TestBase() //Constructor to read data from property file.
	{
		Log = Logger.getLogger(this.getClass()); //Logger Implementation
		try 
		{
			String projectPath = System.getProperty("user.dir");
			property = new Properties();
			FileInputStream ip = new FileInputStream(projectPath + "\\src\\main\\java\\com\\Configuration\\Configuration.properties");
			property.load(ip);
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void initialization() //Read the properties from Configuration File
	{
		String browserName = property.getProperty("Browser");
		
		if(browserName.equals("chrome"))
		{
			System.setProperty("webdriver.chrome.driver","./Drivers/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		else if(browserName.equals("firefox"))
		{
			System.setProperty("webdriver.gecko.driver","./Drivers/geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(property.getProperty("URL"));
	}
}
