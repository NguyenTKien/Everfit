package com.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.BaseClass.TestBase;

public class TestUtility extends TestBase
{

	//Here we write all common methods which are available for all the Classes.

	public static long Page_Load_TimeOut = 15000;

	//Excel Sheet Path - Excel Utility.
	//Below Function is used for getting Data from Excel.
	//To be used with @DataProvider.
	static String projectPath = System.getProperty("user.dir");

	public static String TESTDATA_SHEET_PATH = projectPath
			+ "\\src\\main\\TestData\\TestData.xlsx";

	public static String getUrl() {
		return driver.getCurrentUrl();
	}

	public static WebElement getXpathLocatorByText(String text) {
		return driver.findElement(By.xpath("//*[string()='" + text + "']"));
	}

	static Workbook book;
	static Sheet sheet;

	public static Object[][] getTestData(String sheetName) {
		System.out.println(TESTDATA_SHEET_PATH);
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
//		System.out.println(sheet.getLastRowNum() + "--------" +
//		sheet.getRow(0).getLastCellNum());
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				System.out.println(data[i][k]);
			}
		}
		return data;
	}

	//Screenshot Utility.
	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/Screenshots/" + System.currentTimeMillis() + ".png"));
	}

	//Explicit Wait for Click on any Element.
	public static void clickOn(WebDriver driver, WebElement element, int timeout) {
		new WebDriverWait(driver, timeout).
				until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

	//Explicit Wait for Sending Data to any Element.
	public static void sendKeys(WebDriver driver, WebElement element, int timeout, String value) {
		new WebDriverWait(driver, timeout).
				until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(value);
	}

	//To Highlight the Element using JavaScript.
	public static void highLightElement(WebDriver driver,WebElement element) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: palegreen; border: 8px solid red:')", element);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white')", element);
	}

	//To Handle Frames.
	public void switchToFrame(int frame) {
		try {
			driver.switchTo().frame(frame);
			System.out.println("Navigated to frame with id " + frame);
		} catch (NoSuchFrameException e) {
			System.out.println("Unable to locate frame with id " + frame + e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Unable to navigate to frame with id " + frame + e.getStackTrace());
		}
	}

	//Function to Handle Multiple Windows Or Switch Between Multiple Windows.
	public void switchWindow(WebDriver driver, String firstWindow, String secondWindow) {
		Set<String> windowHandles = driver.getWindowHandles();
		for(String windows : windowHandles) {
			if(!windows.equals(firstWindow) && !windows.equals(secondWindow)) {
				driver.switchTo().window(windows);
			}
		}
	}

	//To Scroll to Particular Element.
	public static void scrollSpecificElement(WebDriver driver,WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}

	//Handle Alert in web base Pop-Up.
	public static void handleWebBaseAlert() {
		String alertMsg=driver.switchTo().alert().getText();// To Capture Alert text
		System.out.println("Alert msg is:"+alertMsg);
		Alert alert=driver.switchTo().alert();// In direct approach to handle alert
		alert.accept();

		driver.switchTo().alert().accept();

		Assert.assertEquals(alertMsg, "Field cannot be empty");//Verify Alert Message
	}

	//To Print all the Values from Dropdown and Select a Value from Dropdown.
	public static void selectDropDownValue(String xpathValue, String value) {
		List<WebElement> monthList = driver.findElements(By.xpath(xpathValue));
		System.out.println(monthList.size());

		for(int i=0; i<monthList.size(); i++) {
			System.out.println(monthList.get(i).getText());
			if(monthList.get(i).getText().equals(value)) {
				monthList.get(i).click();
				break;
			}
		}
	}

	//Function to Accept an Alert Popup.
	public static void acceptAlertPopup() throws InterruptedException {
		try {
			Alert alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			Thread.sleep(2000);
			alert.accept();
			System.out.println("Alert Accepted Successfully");
		} catch(Exception e) {
			System.out.println("Something Went Wrong -- Please Check" +e.getMessage());
		}
	}

	//Function to Dismiss an Alert Popup.
	public static void dismissAlertPopup() throws InterruptedException {
		try {
			Alert alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			Thread.sleep(2000);
			alert.dismiss();
			System.out.println("Alert Dismissed Successfully");
		} catch(Exception e) {
			System.out.println("Something Went Wrong -- Please Check" +e.getMessage());
		}
	}

	//To Select Calendar Date Or Data Picker UsingJava Script Executor.
	public static void selectDateByJS(WebDriver driver, WebElement element, String dateValue) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].setAttribute('value','"+dateValue+"');", element);
	}

	//Function to Mouse Hover and Click Or Select an Element using Actions Class.
	public static void moveToElement(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
	}

	//Function to perform Drag and Drop using Actions Class.
	public static void dragAndDrop(WebDriver driver, WebElement elementOne, WebElement elementTwo) {
		Actions actions = new Actions(driver);
		actions.dragAndDrop(elementOne, elementTwo).release().build().perform();
	}

	//To Click on Element Using JavaScript.
	public static void clickElementByJS(WebElement element, WebDriver driver)
    {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].click();", element);
	}

	//To Refresh Browser Using JavaScript.
	public static void refreshBrowserByJS(WebDriver driver)
    {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("history.go(0)");
    }

	//To Get Title Using JavaScript.
	public static String getTitleByJS(WebDriver driver)
    {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String title = js.executeScript("return document.title;").toString();
		return title;
    }

	//To Scroll Down the Page Using JavaScript.
	public static void scrollPageDown(WebDriver driver)
    {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }
}