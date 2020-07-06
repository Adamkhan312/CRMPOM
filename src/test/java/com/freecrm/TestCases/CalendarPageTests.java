package com.freecrm.TestCases;

import com.freecrm.Pages.CalendarPage;
import com.freecrm.Pages.DashboardPage;
import com.freecrm.TestBase.BaseTest;
import com.freecrm.Utilities.DataProviders;
import com.sun.xml.internal.bind.v2.TODO;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class CalendarPageTests extends BaseTest {

    @Test
    public void monthYearDisplayedTest(){
        CalendarPage calendarPage = new CalendarPage(driver);
        //login functionality is embodied into this function
        calendarPage.navigateToCalendarPage();
        //verify if the default text of calender month and year is correct
        Assert.assertTrue(calendarPage.checkMonthYearText());
    }

    @Test
    public void clickWeekTest(){
        CalendarPage calendarPage = new CalendarPage(driver);
        //login functionality is embodied into this function
        calendarPage.navigateToCalendarPage();
        //click On Week button
        calendarPage.clickOnWeek();
        //Check that the displayed week range is equal to expected week Range
        Assert.assertEquals(calendarPage.weekRangeTextDisplayed(),calendarPage.customExpectedDateRange(0));

    }

    @Test(enabled = false)
    public void verifyTodayButtonWhenInWeekViewTest(){
    }

    @Test(enabled = false)
    public void verifyTodayButtonWhenInDayViewTest(){
    }

    @Test(enabled = false)
    public void clickMonthTest(){

    }

    @Test
    public void verifyNavigatingThroughWeeksTest(){
        CalendarPage calendarPage = new CalendarPage(driver);
        //login functionality is embodied into this function
        calendarPage.navigateToCalendarPage();
        //click On Week button
        calendarPage.clickOnWeek();
        Assert.assertEquals(calendarPage.weekRangeTextDisplayed(),calendarPage.customExpectedDateRange(0));
        calendarPage.clickOnPreviousButton();
        //Navigating through the weeks and verifying that the Week Range is correct
        Assert.assertEquals(calendarPage.weekRangeTextDisplayed(),calendarPage.customExpectedDateRange(-1));
        calendarPage.clickOnPreviousButton();
        Assert.assertEquals(calendarPage.weekRangeTextDisplayed(),calendarPage.customExpectedDateRange(-2));
        calendarPage.clickOnNextButton();
        Assert.assertEquals(calendarPage.weekRangeTextDisplayed(),calendarPage.customExpectedDateRange(-1));
        calendarPage.clickOnNextButton();
        Assert.assertEquals(calendarPage.weekRangeTextDisplayed(),calendarPage.customExpectedDateRange(0));
        calendarPage.clickOnNextButton();
        Assert.assertEquals(calendarPage.weekRangeTextDisplayed(),calendarPage.customExpectedDateRange(1));
    }

    @Test
    public void saveNewEventWithoutRequiredFields() throws InterruptedException {
        CalendarPage calendarPage = new CalendarPage(driver);
        //login functionality is embodied into this function
        calendarPage.navigateToCalendarPage();
        calendarPage.waitForElementPresent(calendarPage.getNewAgendaButton());
        calendarPage.clickOnNewAgendaButton();
        //TODO NEED TO FIX WHY PAGE IS LOADING BLANK
        calendarPage.getUrlAndGo();
        calendarPage.clickOnSave();
        Assert.assertEquals(calendarPage.getErrorText(),"Error");
        calendarPage.clickOnOkinAlert();

    }

    @Test(dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void NewEvent(Hashtable<String,String> data) throws InterruptedException {
        CalendarPage calendarPage = new CalendarPage(driver);
        //login functionality is embodied into this function
        calendarPage.navigateToCalendarPage();
        calendarPage.waitForElementPresent(calendarPage.getNewAgendaButton());
        calendarPage.clickOnNewAgendaButton();
        //TODO NEED TO FIX WHY PAGE IS LOADING BLANK
        calendarPage.getUrlAndGo();
        calendarPage.waitForElementPresent(calendarPage.getNewEventTitleBox());
        //Enter value on Title
        calendarPage.enterTextInField(calendarPage.getNewEventTitleBox(),data.get("Title"));
        //Click On Calendar Box
        calendarPage.clickOnElement(calendarPage.getCalendarBox());
       //Select category
        calendarPage.selectCategory(data.get("Category"));
        Thread.sleep(5000);
        //fill in description
        calendarPage.enterTextInField(calendarPage.getDescriptionBox(),data.get("Description"));
        //Save
       calendarPage.clickOnSave();
    }

    @Test(enabled= false)
    public void verifySystemDateHighlighted(){

    }


    @Test(enabled = false)
    public void CreateEventAndVerifyDisplayedInAgendaTest(){

    }







}



