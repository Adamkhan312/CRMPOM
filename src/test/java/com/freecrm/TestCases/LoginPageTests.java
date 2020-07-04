package com.freecrm.TestCases;

import com.freecrm.Pages.LoginPage;
import com.freecrm.TestBase.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginPageTests extends BaseTest {


    @Test
    public void LoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.doLogin(config.getProperty("email"), config.getProperty("password"))
                .verifyOnDashboardPage());
    }

    @Test
    public void invalidLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doInvalidLogin(config.getProperty("email"), config.getProperty("invalidPassword"));
        Assert.assertTrue(loginPage.verifyInvalidLoginMessage());
    }

    @Test
    public void forgotPasswordTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickOnForgotPassword();
        Assert.assertTrue(loginPage.verifyForgotMyPasswordHeader());

    }


}
