package com.freecrm.TestCases;

import com.freecrm.Pages.CalendarPage;
import com.freecrm.Pages.DashboardPage;
import com.freecrm.TestBase.BaseTest;
import com.sun.xml.internal.bind.v2.TODO;
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

    @Test(enabled= false)//TODO:Implement loop functionality of calendar to validate correct date is highlighted
    public void verifySystemDateHighlighted(){


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
        //Assert.assertTrue(calendarPage.checkIfWeekViewDateRangeisCorrect());

    }

    @Test(enabled = false)
    public void verifyTodayButtonWhenInWeekViewTest(){
    }

    @Test(enabled = false)
    public void verifyTodayButtonWhenInDayViewTest(){
    }


    @Test(enabled = false)
    public void clickMonthTest(){
        //navigate to week

        //click month button

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


    @Test(enabled = false)//(dataProvider = "dp", dataProviderClass = Utilities.DataProviders.class)
    public void CreateNewEventTests(Hashtable<String,String> data) throws InterruptedException {


    }




    @Test(enabled = false)
    public void CreateEventAndVerifyDisplayedInAgendaTest(){

    }







}



