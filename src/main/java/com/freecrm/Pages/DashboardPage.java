package com.freecrm.Pages;



import com.freecrm.Base.BasePage;
import com.freecrm.Utilities.Xls_Reader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class DashboardPage extends BasePage {
    Xls_Reader excel;

    //---------------------------------------------------Constructor----------------------------------------------------//
    public DashboardPage(WebDriver driver) {
        super(driver);
        Assert.assertTrue(isElementPresent(userName));
       this.excel = new Xls_Reader((System.getProperty("user.dir")+"/src/test/resources/TestData/CrmAppTestData.xlsx"));
    }

    //---------------------------------------------------Locators via By------------------------------------------------//
    private By userName = By.cssSelector("span.user-display");
    //SideBar
    private By leftSideBar = By.xpath("//div[@id='main-nav']//a");
    //Content Cards
    private By dealsSummaryContentCard = By.xpath(" //div[contains(text(),'Deals Summary')]");
    private By contactsContentCard = By.cssSelector("div.content.card-header");
    private By dealsContentCard = By.xpath("//div[@class='three column row']//div[@class='header'][contains(text(),'Deals')]");
    private By callQueueContentCard = By.xpath("//span[@class='header'][contains(text(),'Call Queue')]");
    private By upcomingCallsContentCard= By.xpath("//span[contains(text(),'Upcoming calls')]");
    private By twitterContentCard = By.xpath(" //div[contains(text(),'Twitter')]");
    private By exchangeRatesContentCard = By.xpath("//div[contains(text(),'Exchange Rates')]");


    //---------------------------------------------------Getters for By-------------------------------------------------//

    public By getUserName() {
        return userName;
    }

    public By getLeftSideBar() {
        return leftSideBar;
    }

    public By getDealsSummaryContentCard() {
        return dealsSummaryContentCard;
    }

    public By getContactsContentCard() {
        return contactsContentCard;
    }

    public By getDealsContentCard() {
        return dealsContentCard;
    }

    public By getCallQueueContentCard() {
        return callQueueContentCard;
    }

    public By getUpcomingCallsContentCard() {
        return upcomingCallsContentCard;
    }

    public By getTwitterContentCard() {
        return twitterContentCard;
    }

    public By getExchangeRatesContentCard() {
        return exchangeRatesContentCard;
    }


    //-----------------------------------------------------Methods------------------------------------------------------//
    public boolean verifyOnDashboardPage() {
        boolean present = isElementPresent(userName);
        return present;
    }

    public String getDisplayedUserName(){
       return  getTextOfElement(userName);

    }

    public boolean verifySideBarOptions(String sheetName){
       Object [][] data =  excel.getData(sheetName);
        int falseCount = 0;
        ArrayList list = getlist(leftSideBar);
            for(int i = 0; i <excel.getData(sheetName).length;i++) {
                    if (list.get(i).equals(data[i][0].toString())) {
                        System.out.println(list.get(i).toString() + " from webpage is equal to " + data[i][0].toString() + " from excel");
                    } else if (!list.get(i).equals(excel.getData(sheetName)[i][0].toString())) {
                        System.out.println(list.get(i).toString() + " from webpage is NOT equal to " + data[i][0].toString() + " from excel");
                        falseCount++;
                    }
                }

        if(falseCount>0){
            return false;
        }else{
            return true;
        }
    }



    public boolean checkAndClickUrlsExcel(String value, String Url) throws InterruptedException {
        try {
            List<WebElement> Elements = driver.findElements(leftSideBar);
            int count = 0;
            boolean result = true;
            for (int i = 0; i < Elements.size(); i++) {
                String valueAtIndex = Elements.get(i).getText();
                if (valueAtIndex.equals(value)) {
                    count++;
                    System.out.println(valueAtIndex + " is the same as excel value of " + value);
                    Elements.get(i).click();
                    //  Thread.sleep(1000);
                    String CapturedUrl = driver.getCurrentUrl();
                    System.out.println("Captured Url " + CapturedUrl + " is the same as expected URL" + Url);
                    if (!CapturedUrl.equals(Url)) {
                        count = 0;
                    }
                }
            }
            if (!(count == 1)) {
                result = false;
                Assert.fail("Check this link: " + value);
                System.out.println("count of correct tests is " + count);
            }

            return result;
        } catch (Exception e) {
            System.out.println("checkAndClickUrlsExcel() has error");
            e.printStackTrace();
            return false;
        }
    }
}


