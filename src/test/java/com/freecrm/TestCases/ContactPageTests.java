package com.freecrm.TestCases;

import com.freecrm.Pages.ContactsPage;
import com.freecrm.TestBase.BaseTest;
import com.freecrm.Utilities.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class ContactPageTests extends BaseTest {

    @Test(enabled = false)
    public void createNewContactTests() {

    }


    @Test//(dataProvider = "Data", dataProviderClass = DataProviders.class,enabled = false)
    public void verifyContactHeadersTEST() {
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsPage();
        contactsPage.getValuesOfHeaders();
        contactsPage.verifyColumnHeaders();
        //Assert.assertTrue(contactsPage.verifyColumnHeaders(data.get("ContactHeader")));
    }

    @Test(dataProvider = "Data", dataProviderClass = DataProviders.class, enabled = false)
    public void verifyContactHeaders(Hashtable<String, String> data) {
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsPage();
        //contactsPage.verifyColumnHeaders(contactsPage.getColumnHeaders(),data.get("ContactHeader"));
    }


    @Test
    public void viewContactTest() {
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsPage();
        contactsPage.getFirstNameContact();
        contactsPage.clickOnViewContactFirstRow();
        Assert.assertTrue(contactsPage.verifyThatViewedContactIsCorrect());
    }

    @Test
    public void selectAllRowsTest() throws InterruptedException {
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsPage();
        //click on select all checkbox
        contactsPage.clickOnSelectAllCheckBox();
        //verify all rows are selected
        Assert.assertTrue(contactsPage.verifyAllRowsAreSelected());
    }

    @Test
    public void verifyContactNameIsSortableTest() {
        ContactsPage contactsPage = new ContactsPage(driver);
        contactsPage.navigateToContactsPage();
        //first click does not sort in app
        contactsPage.clickOnNameHeader();
        //second click should sort Values
        contactsPage.clickOnNameHeader();
        Assert.assertTrue(contactsPage.verifyValuesAreSorted());
        //third click should desc values so values should not be sorted
        contactsPage.clickOnNameHeader();
        Assert.assertFalse(contactsPage.verifyValuesAreSorted());


    }


    @Test(enabled = false)
    public void verifyCategoryIsSortableTest() {

    }

    @Test(enabled = false)
    public void verifyStatusIsSortableTest() {

    }

    @Test(enabled = false)
    public void verifyEmailIsSortableTest() {

    }

}
