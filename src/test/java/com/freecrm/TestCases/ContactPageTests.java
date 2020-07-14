package com.freecrm.TestCases;

import com.freecrm.Config.Constants;
import com.freecrm.Pages.ContactsPage;
import com.freecrm.TestBase.BaseTest;
import org.testng.annotations.Test;

public class ContactPageTests extends BaseTest {

    @Test(enabled = false)
    public void createNewContactTests(){

    }


    @Test
    public void verifyColumnHeadersTest(){
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsPage();
        contactsPage.getColumnHeaders();
        contactsPage.verifyColumnHeaders();
    }


    @Test(enabled = false)
    public void viewContactTest(){

    }

    @Test(enabled = false)
    public void deleteContactTest(){

    }

    @Test(enabled = false)
    public void verifyContactNameIsSortableTest(){

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
