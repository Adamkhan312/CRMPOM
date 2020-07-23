package com.freecrm.Pages;

import com.freecrm.Base.BasePage;
import com.freecrm.Config.Constants;
import com.google.common.collect.Ordering;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class ContactsPage extends BasePage {

    String tempValue;

    //---------------------------------------------------Constructor using Page Factory Init----------------------------------------------------//

    public ContactsPage(WebDriver driver) {
        super(driver);
        //set up framework to call Page Factory init on Base Page (super) or i can use below For specific page
        PageFactory.initElements(driver, this);
        // this.excel = new Xls_Reader((System.getProperty("user.dir") + "/src/test/resources/TestData/CrmAppTestData.xlsx"));
    }

    //---------------------------------------------------Locators via Find By Page Factory------------------------------------------------//

    @FindBy(css = ".custom-grid.table-scroll")
    private WebElement table;

    // By contactHeader = By.cssSelector("thead.full-width tr > *");
    @FindBy(css = "thead.full-width tr > *")
    private List<WebElement> contactHeader;

    @FindBy(css = "th.collapsing:nth-child(1) div.ui.fitted.checkbox")
    private WebElement selectAllCheckBox;

    @FindBy(xpath = "//div[@class='ui fitted checkbox']//input")
    private WebElement selectAllCheckBoxXpath;

    @FindBy(xpath = "//th[contains(text(),'Name')]")
    private WebElement NameHeader;

    @FindBy(css = "tbody:nth-child(2) tr:nth-child(1) > td:nth-child(2)")
    private WebElement firstContactName;

    @FindBy(css = "a:nth-child(1) button.ui.icon.button > i.unhide.icon")
    private WebElement viewContactButton;


    By contactHeaderLocator = By.cssSelector("thead.full-width tr > *");


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

    private void refreshPageIfNotLoaded() {
        if (!(getTotalColsInTable() == 8)) {
            driver.navigate().refresh();
        }
    }

    public void clickOnSelectAllCheckBox() {
        refreshPageIfNotLoaded();
        //TODO use of Thread.sleep is discouraged..need to implement wait condition
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        selectAllCheckBox.click();
    }

    public boolean verifyAllRowsAreSelected() {
        int rowCount = getTotalRowsInTable();
        int countCheck = 0;
        for (int i = 0; i < rowCount; i++) {
            //TODO use of Thread.sleep is discouraged..need to implement wait condition
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean selected = driver.findElement(By.cssSelector("div.ui.checked.fitted.read-only.checkbox")).getAttribute("class").contains("ui checked fitted read-only checkbox");
            if (selected) {
                countCheck++;
            }
        }
        if (countCheck == rowCount) {
            return true;
        } else {
            return false;
        }

    }

    private int getTotalRowsInTable() {
        wait.until(ExpectedConditions.visibilityOfAllElements(table.findElements(By.tagName("tr"))));
//      List<WebElement> totalRows = table.findElements(By.tagName("tr"));
        List<WebElement> totalRows = driver.findElements(By.cssSelector("div.ui.fitted.read-only.checkbox > label:nth-child(2)"));
        System.out.println("There are " + totalRows.size() + " rows in the table");
        return totalRows.size();
    }

    private int getTotalColsInTable() {
        int col = 0;
        try {
            Thread.sleep(1000);
            // List<WebElement> totalCols = contactHeader.findElements(By.tagName("td"));
            col = contactHeader.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return col;
    }

    public ArrayList<String> getValuesOfHeaders() {
        ArrayList<String> headerValues = new ArrayList<>();
        try {
            wait.until(ExpectedConditions.numberOfElementsToBe(contactHeaderLocator, getTotalColsInTable()));
            refreshPageIfNotLoaded();
            for (WebElement e : contactHeader) {
                // System.out.println(e.getAttribute("innerText"));
                headerValues.add(e.getAttribute("innerText"));
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(headerValues.toString());
        return headerValues;


    }

    public boolean verifyValuesAreSorted() {
        ArrayList<String> names = new ArrayList<>();
        int value = 1;
        int rowCount = getTotalRowsInTable();
        try {
            if (!(getTotalColsInTable() == 8)) {
                driver.navigate().refresh();
            }
            while (value <= rowCount) {
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("tr:nth-child(" + value + ") > td:nth-child(2)"))));
                Thread.sleep(1500);
                //wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("tr:nth-child(" + value + ") > td:nth-child(2)")));
                String nameValue = driver.findElement(By.cssSelector("tr:nth-child(" + value + ") > td:nth-child(2)")).getText();
                System.out.println("Name captured was " + nameValue);
                names.add(nameValue);
                value++;
            }
            value = 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("couldn't find element");
        }
        System.out.println(names.toString());
        boolean isSorted = Ordering.from(String.CASE_INSENSITIVE_ORDER).isOrdered(names);
        System.out.println("Values are sorted: " + isSorted);
        return isSorted;
    }

    public void verifyColumnHeaders() {
        ArrayList<String> list = getValuesOfHeaders();
        Object[][] data = excel.getData("verifyContactHeaders");

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(data[i][0].toString())) {
                System.out.println("column name in app is " + list.get(i) + " matches expected column name of " + data[i][0].toString());
            } else {
                System.out.println("column name in app is " + list.get(i) + " does NOT matches expected column name of " + data[i][0].toString());
            }
        }
    }

    public String getFirstNameContact() {
        refreshPageIfNotLoaded();
        //TODO use of Thread.sleep is discouraged..need to implement wait condition
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tempValue = firstContactName.getText();
        return tempValue;
    }

    public void clickOnViewContactFirstRow() {
        viewContactButton.click();
    }

    public boolean verifyThatViewedContactIsCorrect() {
        String ContactName = driver.findElement(By.cssSelector("div.ui.header.item.mb5.light-black:nth-child(1)")).getText();
        System.out.println(tempValue);
        if (tempValue.equals(ContactName)) {
            return true;
        } else {
            return false;
        }
    }


}