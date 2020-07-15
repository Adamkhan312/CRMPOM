package com.freecrm.TestCases;

import com.freecrm.Config.Constants;
import com.freecrm.Pages.ContactsPage;
import com.freecrm.TestBase.BaseTest;
import com.freecrm.Utilities.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class ContactPageTests extends BaseTest {

    @Test(enabled = false)
    public void createNewContactTests(){

    }


    @Test(dataProvider = "Data", dataProviderClass = DataProviders.class,enabled = false)
    public void verifyContactHeadersTEST(Hashtable<String,String> data){
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsPage();
        //Assert.assertTrue(contactsPage.verifyColumnHeaders(data.get("ContactHeader")));
    }

    @Test(dataProvider = "Data", dataProviderClass = DataProviders.class,enabled=false)
    public void verifyContactHeaders(Hashtable<String,String> data){
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsPage();
        contactsPage.verifyColumnHeaders(contactsPage.getColumnHeaders(),data.get("ContactHeader"));
    }


    @Test(enabled = false)
    public void viewContactTest(){

    }

    @Test(enabled = false)
    public void deleteContactTest(){

    }

    @Test
    public void verifyContactNameIsSortableTest(){
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsPage();
        contactsPage.clickOnNameHeader();
        contactsPage.clickOnNameHeader();
        contactsPage.compareSort();


    }


    @Test(enabled = false)
    public void verifyCategoryIsSortableTest(){

    }

    @Test(enabled = false)
    public void verifyStatusIsSortableTest(){

    }

    @Test(enabled = false)
    public void verifyEmailIsSortableTest(){

    }

}
