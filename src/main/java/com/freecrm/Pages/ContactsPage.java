package com.freecrm.Pages;

import com.freecrm.Base.BasePage;
import com.freecrm.Config.Constants;
import com.freecrm.Utilities.Xls_Reader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class ContactsPage extends BasePage {

    Xls_Reader excel;

    //---------------------------------------------------Constructor----------------------------------------------------//
    public ContactsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.excel = new Xls_Reader((System.getProperty("user.dir") + "/src/test/resources/TestData/CrmAppTestData.xlsx"));
    }


    //---------------------------------------------------Locators via Find By------------------------------------------------//

    // By contactHeader = By.cssSelector("thead.full-width tr > *");
    @FindBy(css = "thead.full-width tr > *")
    List<WebElement> contactHeader;


    //---------------------------------------------------Getters for By-------------------------------------------------//


    //-----------------------------------------------------Methods------------------------------------------------------//

    public void navigateToContactsPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin(Constants.email, Constants.password);
        loginPage.clickOnContactsMenu();
    }

    public ArrayList<String> getColumnHeaders() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            for (int i = 0; i < contactHeader.size(); i++) {
              list.add(contactHeader.get(i).getText());
            }
        } catch (Exception e) {
            System.out.println("couldn't find element");
            e.printStackTrace();
        }
        return list;
    }


    public void verifyColumnHeaders() {
        ArrayList<String> col = getColumnHeaders();
        System.out.println("Size of list is "+ col.size());
        for (int i = 0; i < col.size(); i++) {
            System.out.println("Header value is " + getColumnHeaders().get(i));
        }

    }
}