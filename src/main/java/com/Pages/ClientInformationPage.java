package com.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.BaseClass.TestBase;

public class ClientInformationPage extends TestBase
{
	@FindBy(xpath = "//*[@class = 'app-navbar show-border client-detailed']")
	WebElement headerBar;

	@FindBy(xpath = "//*[@class = 'client-overview-container']")
	WebElement clientInfoView;

	public ClientInformationPage() {
		PageFactory.initElements(driver, this);
	}

	public boolean isDisplayed() {
		return headerBar.isDisplayed() && clientInfoView.isDisplayed();
	}

	public TasksPage selectHeaderBarByName(String headerBarName) {
		WebElement headerBar = driver.findElement(By.xpath("//a[@class = 'client-menu-item']/span[text() = '"
				+ headerBarName + "']"));

		headerBar.isDisplayed();
		headerBar.click();

		return new TasksPage();
	}

	public void selectContactByName(String name) {
		driver.findElement(By.xpath("//a[contains(text(),'"+name+"')]//parent::td[@class='datalistrow']"
				+ "//preceding-sibling::td[@class='datalistrow']//input[@name='contact_id']")).click();
	}

}
