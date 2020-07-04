package com.freecrm.Base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BasePage {

    private static final int TIMEOUT = 5;
    private static final int POLLING = 100;

    protected WebDriver driver;
    private WebDriverWait wait;


    //constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEOUT, POLLING);
      // initElements(driver,this);
    }


    //---------------------------------Common Functions using By Locator for each page--------------------------------------------------/

    public  WebDriver getDriver(){
        return this.driver;
    }


    public String getPageTitle() {
       return driver.getTitle();
    }

    public String getPageHeader(By locator) {
    return getElement(locator).getText();
    }

    public void clickOnElement(By locator){
        driver.findElement(locator).click();


    }
    public WebElement getElement(By locator) {
        WebElement element = null;
        try {
          element =  driver.findElement(locator);
          return element;
        }catch (Exception e){
            System.out.println("could not locate the element using getElement() with "+ locator.toString());
            e.printStackTrace();
        }
        return element;//will return null if not able to getELement
    }

    public boolean isElementPresent(By locator){
        try{
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        }catch(Exception e){
            System.out.println("Element not present");
            e.printStackTrace();
            return false;
        }
    }



    public String getTextOfElement(By locator){
        try{
            return driver.findElement(locator).getText();
        }catch(ElementNotVisibleException e){
            System.out.println("Couldnt find Element while using getTextOfElement()");
            e.printStackTrace();
            return null;

        }
    }

    public void waitForElementPresent(By locator) {

        try{
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        }catch(Exception e){
            System.out.println("Exception occured while waiting for the element "+ locator.toString());
            e.printStackTrace();
        }
    }

    public void waitForPageTitle(String title) {
        try{
            wait.until(ExpectedConditions.titleContains(title));
        }catch(Exception e){
            System.out.println("Exception occurred while waiting for the title of "+ title);
            e.printStackTrace();
        }
    }


    public ArrayList<String> getlist(By locator) {
        List<WebElement> list = getDriver().findElements(locator);
        ArrayList<String> linkList = new ArrayList<String>(){
        };
        for (int i = 0; i <= list.size() - 1; i++) {
            linkList.add(list.get(i).getText());
        }
        return linkList;
    }




    }


