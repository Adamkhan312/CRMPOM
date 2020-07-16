package com.freecrm.TestCases;


import com.freecrm.Pages.DashboardPage;
import com.freecrm.Utilities.DataProviders;
import com.freecrm.Pages.LoginPage;
import com.freecrm.TestBase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardPageTests extends BaseTest {


    @Test
    public void verifySideBarOptions() {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.doLogin(config.getProperty("email"), config.getProperty("password"))
                .verifySideBarOptions("LeftSideBar"));
    }


    @Test(dataProvider = "data-provider", dataProviderClass = DataProviders.class)
    public void SideBarUrls(String link, String url) throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.doLogin(config.getProperty("email"), config.getProperty("password"))
                .checkAndClickUrlsExcel(link, url));
    }

    @Test
    public void verifyUserNameTest() {
        LoginPage loginPage = new LoginPage(driver);
        String userNameDisplayed = loginPage.doLogin(config.getProperty("email"), config.getProperty("password")).getDisplayedUserName();
        Assert.assertEquals(userNameDisplayed, config.getProperty("username"));
    }

    @Test
    public void verifyDealsSummaryCardIsPresent() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.doLogin(config.getProperty("email"), config.getProperty("password"));
        Assert.assertTrue(loginPage.isElementPresent(dashboardPage.getDealsSummaryContentCard()));
    }

    @Test
    public void verifyContactCardIsPresent() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.doLogin(config.getProperty("email"), config.getProperty("password"));
        Assert.assertTrue(loginPage.isElementPresent(dashboardPage.getContactsContentCard()));
    }

    @Test
    public void verifyDealsCardIsPresent() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.doLogin(config.getProperty("email"), config.getProperty("password"));
        Assert.assertTrue(loginPage.isElementPresent(dashboardPage.getDealsContentCard()));
    }


    @Test
    public void verifyFreeAccountLinkTest() {
        //TODO
    }

    @Test
    public void searchFunctionalityTest() {
        //TODO
    }

}




