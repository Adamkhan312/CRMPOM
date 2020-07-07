package com.freecrm.Base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import static org.openqa.selenium.support.PageFactory.initElements;

public class BasePage {

    private static final int TIMEOUT = 5;
    private static final int POLLING = 100;
    protected WebDriver driver;
    protected WebDriverWait wait;

    //constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEOUT, POLLING);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    //---------------------------------Common Functions using By Locator for each page--------------------------------------------------/

    public WebDriver getDriver() {
        return this.driver;
    }

    public void switchToiFrame(int index) {
        driver.switchTo().frame(index);
    }
    public void getAllFramesById(){
    List<WebElement> frames = driver.findElements(By.tagName("iframe"));
    System.out.println(frames.size());
    for(WebElement fr: frames){
        System.out.println(fr.getAttribute("id"));
    }
}
    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getPageHeader(By locator) {
        return getElement(locator).getText();
    }

    public void getUrlAndGo() {
        String url = driver.getCurrentUrl();
        driver.get(url);
    }

    public void clickOnElement(By locator) {
        driver.findElement(locator).click();
    }

    public void enterTextInField(By locator, String text) {
        driver.findElement(locator).sendKeys(text);
    }


    public WebElement getElement(By locator) {
        WebElement element = null;
        try {
            element = driver.findElement(locator);
            return element;
        } catch (Exception e) {
            System.out.println("could not locate the element using getElement() with " + locator.toString());
            e.printStackTrace();
        }
        return element;//will return null if not able to getELement
    }

    public boolean isElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            System.out.println("Element not present");
            e.printStackTrace();
            return false;
        }
    }

    public String getTextOfElement(By locator) {
        try {
            return driver.findElement(locator).getText();
        } catch (ElementNotVisibleException e) {
            System.out.println("Couldnt find Element while using getTextOfElement()");
            e.printStackTrace();
            return null;
        }
    }

    public void waitForElementPresent(By locator) {

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Exception occured while waiting for the element " + locator.toString());
            e.printStackTrace();
        }
    }

    public void waitForPageTitle(String title) {
        try {
            wait.until(ExpectedConditions.titleContains(title));
        } catch (Exception e) {
            System.out.println("Exception occurred while waiting for the title of " + title);
            e.printStackTrace();
        }
    }

    public ArrayList<String> getlist(By locator) {
        List<WebElement> list = getDriver().findElements(locator);
        ArrayList<String> linkList = new ArrayList<String>() {
        };
        for (int i = 0; i <= list.size() - 1; i++) {
            linkList.add(list.get(i).getText());
        }
        return linkList;
    }


    public static String getSystemDate(int dayOffset) {
        LocalDate currentdate = LocalDate.now();
        Month currentMonth = currentdate.getMonth();
        Calendar c = Calendar.getInstance();
        Format df = new SimpleDateFormat("MM/dd/yyyy");
        c.add(Calendar.DATE,dayOffset);
        String date = df.format(c.getTime());
        return date;


    }


}


