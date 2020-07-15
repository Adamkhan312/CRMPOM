package com.freecrm.Pages;

import com.freecrm.Base.BasePage;
import com.freecrm.Config.Constants;
import com.freecrm.Utilities.Xls_Reader;
import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class ContactsPage extends BasePage {

    Xls_Reader excel;
    int countsMatched=0;

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

    @FindBy(xpath = "//th[contains(text(),'Name')]")
    WebElement NameHeader;

    @FindBy(xpath = "//tr[1]//td[2]")
    WebElement firstContactName;

    By nameCaptured = By.xpath("//tr[1]//td[2]");


    //---------------------------------------------------Getters for By-------------------------------------------------//


    //-----------------------------------------------------Methods------------------------------------------------------//

    public void navigateToContactsPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin(Constants.email, Constants.password);
        loginPage.clickOnContactsMenu();
    }


    public void clickOnNameHeader(){
        NameHeader.click();
    }

    public boolean compareSort(){
        ArrayList<String> names = new ArrayList<>();
        for(int i=0;i<2;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clickOnNameHeader();
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(firstContactName)));
            names.add(firstContactName.getText());
        }
        System.out.println(names.toString());
        boolean isSorted = Ordering.from(String.CASE_INSENSITIVE_ORDER).isOrdered(names);
        System.out.println(isSorted);
            return isSorted;


    }
    public ArrayList<String> getColumnHeaders() {
        ArrayList<String> list = new ArrayList<String>();
        List<WebElement> headerlist = contactHeader;
        try {
            for (int i = 0; i < contactHeader.size(); i++) {
              list.add(contactHeader.get(i).getAttribute("innerText"));
            }
        } catch (Exception e) {
            System.out.println("couldn't find element");
            e.printStackTrace();
        }
        return list;
    }


    public void verifyColumnHeaders() {
        wait.until(ExpectedConditions.visibilityOfAllElements(contactHeader));
                ArrayList<String> col = getColumnHeaders();
        System.out.println("Size of list is "+ col.size());
        for (int i = 0; i < col.size(); i++) {
            System.out.println("Header value is " + col.get(i));
        }

    }
    public boolean verifyColumnHeaders1( String value) {
        int countsMatched=0;
        wait.until(ExpectedConditions.visibilityOfAllElements(contactHeader));
        ArrayList<String> col = getColumnHeaders();
        System.out.println("Size of list is "+ col.size());
        for (int i = 0; i < col.size(); i++) {
            System.out.println("Header value is " + col.get(i));
            if(value.equals(col.get(i))){
                countsMatched++;
                System.out.println("Value found is "+ col.get(i)+ " value expected is "+ value);
            }else{
                countsMatched--;
                System.out.println("Value found is "+ col.get(i)+ "Value expected is "+ value);
            }

        }
        if(countsMatched==8){
            return true;
        }else{
            return false;
        }

    }

    public boolean verifyColumnHeaders(ArrayList<String> list, String value) {
        wait.until(ExpectedConditions.visibilityOfAllElements(contactHeader));
//        ArrayList<String> col = getColumnHeaders();
        list.toString();
       if(list.contains(value)){
           System.out.println("Value expected is "+ value);
           return true;
       }else{
           return false;
       }

    }
}