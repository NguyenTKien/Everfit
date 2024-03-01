package com.Pages;

import org.openqa.selenium.support.PageFactory;

import com.BaseClass.TestBase;

import static com.Utilities.TestUtility.getXpathLocatorByText;

public class HomePage extends TestBase
{
	public HomePage()
	{
		PageFactory.initElements(driver, this);
	}
	
	public ClientInformationPage clickClientByName(String clientName)
	{
		getXpathLocatorByText(clientName).isDisplayed();
		getXpathLocatorByText(clientName).click();

		return new ClientInformationPage();
	}
}
