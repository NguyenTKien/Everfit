package com.Pages;

import com.BaseClass.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class TasksPage extends TestBase
{
    @FindBy(xpath = "//div[contains(@class, 'create-task edit-task')]")
    WebElement editTaskForm;

    @FindBy(xpath = "//div[contains(@class, 'custom-date-input form-control')]")
    WebElement dateField;

    @FindBy(xpath = "//button[contains(@class, 'actions__saveClose')]/following-sibling::button")
    WebElement saveAndCloseButton;

    public boolean isTaskFormIsDisplayed() {
        return editTaskForm.isDisplayed();
    }

    public void setDateOfTask(String dateInput) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", dateField, dateInput);
    }

    public boolean isDisplayed(String headerName) {
        return driver.findElement(By.xpath("//a[@class = 'client-menu-item active']/span[text() = '"
                + headerName + "']")).isDisplayed();
    }

    public void selectTaskTypeByName(String name) {
         driver.findElement(By.xpath("//div[@class='title' and text()='"
        + name +"']")).click();
    }

    public void clickSaveAndClose() {
        saveAndCloseButton.isDisplayed();
        saveAndCloseButton.click();
    }

    public String getDayFromTaskName(String taskName) {
        WebElement dayElement = driver.findElement(By.xpath("//div[text()='" +
                taskName + "']//ancestor::div[@id='task-dropable']/preceding-sibling::div/div[@class='ui label day-label']"));
        dayElement.isDisplayed();
        return dayElement.getText();
    }

    public void selectTaskToCustomizeByName(String taskName) {
        WebElement dropDownDot = driver.findElement(By.xpath("//div[@class='title' and text()='"
                + taskName +"']/parent::div/following-sibling::div[contains(@class, 'dropdown__trigger')]"));

        dropDownDot.isDisplayed();
        dropDownDot.click();
    }

    public void selectActionInTask(String option) {
        WebElement actionPopupButton = driver.findElement(By.xpath("//div[contains(@class, 'action-popup')]//div[text()='"
                + option + "']"));

        actionPopupButton.isDisplayed();
        actionPopupButton.click();
    }

    public void clickConfirmYesButton() {
        WebElement confirmYesButton = driver.findElement(By.xpath("//button[contains(@class, 'confirm-yes-button')]"));

        confirmYesButton.isDisplayed();
        confirmYesButton.click();
    }

    public void createTheTask(String createDate, String taskType, String taskName) throws InterruptedException {
        //Get day component from the Creating date
        String[] dateComponents = createDate.split("/");
        String day =  dateComponents[0];

        WebElement addNewButtonByDate = driver.findElement(By.xpath("//div[text()='"
                + day + "']/following-sibling::div/div[@class='add-new']"));
        addNewButtonByDate.isDisplayed();
        System.out.println(addNewButtonByDate);
        Actions actions = new Actions(driver);
        actions.moveToElement(addNewButtonByDate).click();
        actions.build().perform();

        WebElement taskTypButton = driver.findElement(By.xpath("//div[@class='item']//p[text()='"
                + taskType +"']"));
        taskTypButton.click();

        WebElement taskNameField = driver.findElement(By.xpath("//label/parent::div[@class='title__wrapper']/following-sibling::input"));
        taskNameField.click();
        taskNameField.clear();
        taskNameField.sendKeys(taskName);

        WebElement saveElement = driver.findElement(By.xpath("//button[contains(@class, 'actions__saveClose')]/following-sibling::button"));
        saveElement.click();
    }

    public boolean isTaskIsCreated(String taskName) {
        return driver.findElement(By.xpath("//div[@class = 'task-title']/div[text() = '" + taskName + "']")).isDisplayed();
    }

}