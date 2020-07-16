package com.freecrm.Pages;

import com.freecrm.Base.BasePage;
import com.freecrm.Config.Constants;
import com.freecrm.Utilities.Xls_Reader;
import com.google.common.collect.Ordering;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class ContactsPage extends BasePage {

    Xls_Reader excel;
    int value = 0;

    //---------------------------------------------------Constructor----------------------------------------------------//
    public ContactsPage(WebDriver driver) {
        super(driver);
        //set up framework to call Page Factory init on Base Page (super) or i can use below For specific page
        PageFactory.initElements(driver, this);
        this.excel = new Xls_Reader((System.getProperty("user.dir") + "/src/test/resources/TestData/CrmAppTestData.xlsx"));
    }


    //---------------------------------------------------Locators via Find By------------------------------------------------//

    @FindBy(css = ".custom-grid.table-scroll")
    private WebElement table;

    // By contactHeader = By.cssSelector("thead.full-width tr > *");
    @FindBy(css = "thead.full-width tr > *")
    private List<WebElement> contactHeader;

    @FindBy(xpath = "//th[contains(text(),'Name')]")
    private WebElement NameHeader;

    @FindBy(xpath = "//tr[1]//td[2]")
    private WebElement firstContactName;


    //---------------------------------------------------Getters for By-------------------------------------------------//


    //-----------------------------------------------------Methods------------------------------------------------------//

    public void navigateToContactsPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin(Constants.email, Constants.password);
        loginPage.clickOnContactsMenu();
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void clickOnNameHeader() {
        NameHeader.click();
    }

    private int getTotalRowsInTable() {
        wait.until(ExpectedConditions.visibilityOfAllElements(table.findElements(By.tagName("tr"))));
        List<WebElement> totalRows = table.findElements(By.tagName("tr"));
        System.out.println("There are " + totalRows.size() + " rows in the table");
        return totalRows.size();
    }

    public boolean verifyValuesAreSorted() {
        ArrayList<String> names = new ArrayList<>();
        int rowCount = getTotalRowsInTable();
        try {
            for (int i = 1; i < rowCount; i++) {
                value = i;
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("tr:nth-child(" + value + ") > td:nth-child(2)"))));
                Thread.sleep(1500);
                //wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("tr:nth-child(" + value + ") > td:nth-child(2)")));
                String nameValue = driver.findElement(By.cssSelector("tr:nth-child(" + value + ") > td:nth-child(2)")).getText();
                System.out.println("Name captured was " + nameValue);
                names.add(nameValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("couldnt find element");
        }
        System.out.println(names.toString());
        boolean isSorted = Ordering.from(String.CASE_INSENSITIVE_ORDER).isOrdered(names);
        System.out.println("Values are sorted: " + isSorted);
        return isSorted;
    }

    public ArrayList<String> getColumnHeaders() {
        ArrayList<String> list = new ArrayList<String>();
        List<WebElement> headerlist = contactHeader;
        try {
            for (int i = 1; i < contactHeader.size(); i++) {
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
        System.out.println("Size of list is " + col.size());
        for (int i = 0; i < col.size(); i++) {
            System.out.println("Header value is " + col.get(i));
        }

    }

    public boolean verifyColumnHeaders1(String value) {
        int countsMatched = 0;
        wait.until(ExpectedConditions.visibilityOfAllElements(contactHeader));
        ArrayList<String> col = getColumnHeaders();
        System.out.println("Size of list is " + col.size());
        for (int i = 0; i < col.size(); i++) {
            System.out.println("Header value is " + col.get(i));
            if (value.equals(col.get(i))) {
                countsMatched++;
                System.out.println("Value found is " + col.get(i) + " value expected is " + value);
            } else {
                countsMatched--;
                System.out.println("Value found is " + col.get(i) + "Value expected is " + value);
            }

        }
        if (countsMatched == 8) {
            return true;
        } else {
            return false;
        }

    }

    public boolean verifyColumnHeaders(ArrayList<String> list, String value) {
        wait.until(ExpectedConditions.visibilityOfAllElements(contactHeader));
//        ArrayList<String> col = getColumnHeaders();
        list.toString();
        if (list.contains(value)) {
            System.out.println("Value expected is " + value);
            return true;
        } else {
            return false;
        }

    }
}