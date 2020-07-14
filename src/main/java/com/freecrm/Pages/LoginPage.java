package com.freecrm.Pages;

import com.freecrm.Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class LoginPage extends BasePage {


    //---------------------------------------------------Constructor ------------------------------------//
    public LoginPage(WebDriver driver) {
        super(driver);
        Assert.assertTrue(isElementPresent(emailLocator));
    }

    //--------------------------------------Locators via By -------------------------------------//

    private By emailLocator = By.name("email");
    private By passwordBoxLocator = By.name("password");
    private By loginBtnLocator = By.xpath("//div[@id='ui']//div[text()='Login']");
    private By forgotPassWordLocator = By.xpath("//h2[contains(text(),'Forgot my password')]");
    private By forgotPasswordHeader = By.cssSelector("h2.ui.blue.header");
    private By forgotYourPassword = By.linkText("Forgot your password?");
    private By resetPasswordButton = By.xpath(" //button[@name='action']");
    private By invalidLoginMessage = By.xpath("//div[contains(text(),'Something went wrong...')]");

    private By calendarSideBar= By.xpath("//span[contains(text(),'Calendar')]");
    private By contactsSideBar= By.xpath("//span[contains(text(),'Contacts')]");


    //---------------------------------------------Getters----------------------------------------------//


    public WebElement getEmailLocator() {
        return getElement(emailLocator);
    }

    public WebElement getPasswordBoxLocator() {
        return getElement(passwordBoxLocator);
    }

    public WebElement getLoginBtnLocator() {
        return getElement(loginBtnLocator);
    }

    public WebElement getForgotPassWordLocator() {
        return getElement(forgotPassWordLocator);
    }


    //-----------------------------------Methods and Functionality-----------------------------------//


    public DashboardPage doLogin(String email, String pwd) {
        getEmailLocator().sendKeys(email);
        getPasswordBoxLocator().sendKeys(pwd);
        getLoginBtnLocator().click();
        return new DashboardPage(driver);
    }

    public void doInvalidLogin(String email, String pwd){
        getEmailLocator().sendKeys(email);
        getPasswordBoxLocator().sendKeys(pwd);
        getLoginBtnLocator().click();
    }

    public CalendarPage clickOnCalendarMenu(){
        clickOnElement(calendarSideBar);
        return new CalendarPage(driver);
    }

    public ContactsPage clickOnContactsMenu(){
        clickOnElement(contactsSideBar);
        return new ContactsPage(driver);
    }

    public boolean verifyInvalidLoginMessage() {
        boolean invalidMessageAppears = isElementPresent(invalidLoginMessage);
        System.out.println(driver.findElement(invalidLoginMessage).getText());
        return invalidMessageAppears;
    }

    public void clickOnForgotPassword() {
        clickOnElement(forgotYourPassword);
    }

    public boolean verifyForgotMyPasswordHeader() {
        boolean present;
        try {
            present = isElementPresent(forgotPasswordHeader);
            System.out.println(driver.findElement(forgotPasswordHeader).getText());
            return present;
        } catch (Exception e) {
            present = false;
            e.printStackTrace();
            return present;
        }
    }


    //click on Forgot password


}
