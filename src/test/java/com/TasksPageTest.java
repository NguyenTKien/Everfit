package com;

import com.Pages.*;
import org.testng.Assert;
import org.testng.annotations.*;

import com.BaseClass.TestBase;
import com.Utilities.TestUtility;

public class TasksPageTest extends TestBase
{
	LoginPage loginPage;
	HomePage homePage;
	TestUtility testUtil;
	ClientInformationPage clientInfoPage;
	TasksPage tasksPage;
	
	String sheetName = "Tasks"; //Passing Excel Sheet Name

	public TasksPageTest()
	{
		super();
	}
	
	@BeforeClass
	public void setUp() throws InterruptedException {
		initialization();
		testUtil = new TestUtility();
		Log.info("Browser Launched Successfully");
		
		loginPage = new LoginPage();
		clientInfoPage = new ClientInformationPage();

		homePage = loginPage.login(property.getProperty("Username"),property.getProperty("Password"));
		Assert.assertEquals(testUtil.getUrl(), property.get("HomePageUrl"));

		clientInfoPage = homePage.clickClientByName("Client Test");
		Assert.assertTrue(clientInfoPage.isDisplayed());

		tasksPage = clientInfoPage.selectHeaderBarByName("Tasks");
		Assert.assertTrue(tasksPage.isDisplayed("Tasks"));
	}

	//We are using Data Provider here to Access Data from Excel Sheet
	@DataProvider()
	public Object[][] getTasksTestData() //To Access Sheet from Test Data Sheet
	{
		return TestUtility.getTestData(sheetName);
	}

	@Test(priority = 1, dataProvider = "getTasksTestData")
	public void createTheTasksFromTestData(String createDate, String taskType, String taskName) throws InterruptedException
	//We Must Pass Parameters as it is like we have given in Excel Column Names to Access Data
	{
		tasksPage.createTheTask(createDate, taskType, taskName);
		Assert.assertTrue(tasksPage.isTaskIsCreated(taskName));

		Log.info("New Task Created Successfully");
	}

	@Test(priority=2)
	public void deleteTheTasks()
	{
		tasksPage.selectTaskToCustomizeByName("Automation Photo");
		tasksPage.selectActionInTask("Delete");
		tasksPage.clickConfirmYesButton();

		Log.info("Verified Delete Task");
	}

	@Test(priority=3)
	public void theTaskHasModifiedWithComponent()
	{
		tasksPage.selectTaskTypeByName("Automation General");
		Assert.assertTrue(tasksPage.isTaskFormIsDisplayed());

		tasksPage.setDateOfTask("Feb 26, 2024 (Tue)");
		tasksPage.clickSaveAndClose();
		Assert.assertEquals(tasksPage.getDayFromTaskName("Automation General"), "26");

		Log.info("Verified Updating Task");
	}

	@AfterClass
	public void tearDown()
	{
		driver.quit();
		Log.info("Browser Terminated");
	}
}