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

    By contactHeaderLocator = By.cssSelector("thead.full-width tr > *");


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

    private int getTotalColsInTable() {
        int col = 0;
        try {
            Thread.sleep(2000);
            // List<WebElement> totalCols = contactHeader.findElements(By.tagName("td"));
            System.out.println("There are " + contactHeader.size() + " cols in the table");
            col = contactHeader.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return col;
    }

    public void getValuesOfHeaders() {
        wait.until(ExpectedConditions.numberOfElementsToBe(contactHeaderLocator, getTotalColsInTable()));
        for (WebElement e : contactHeader) {
            System.out.println(e.getAttribute("innerText"));
        }
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


}