package com.freecrm.TestCases;

import com.freecrm.Pages.CalendarPage;
import com.freecrm.Pages.DashboardPage;
import com.freecrm.TestBase.BaseTest;
import com.sun.xml.internal.bind.v2.TODO;
import org.testng.Assert;
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

    @Test//TODO:Implement loop functionality of calendar to validate correct date is highlighted
    public void verifySystemDateHighlighted(){



    }

    @Test
    public void clickWeekTest(){
        //TODO FIX CALENDAR METHODS
        //Login and click on Calender

        //Click on Week button

        //VerifyWeekCalender is displayed by checking if the Week range is displayed and correct

    }

    @Test
    public void clickDayTest(){
    }


    @Test
    public void clickMonthTest(){
        //navigate to week

        //click month button

    }


    @Test
    public void verifyNavigatingThroughWeeksTest(){
        //login click on calendar
        //click on week
        //get expected week range
        //click a week ahead
        //validate week range
        //click previous week(bringing us back to current)
        //validate current week range
        //click previous week again
        //validate week range


    }


    @Test//(dataProvider = "dp", dataProviderClass = Utilities.DataProviders.class)
    public void CreateNewEventTests(Hashtable<String,String> data) throws InterruptedException {


    }




    @Test
    public void CreateEventAndVerifyDisplayedInAgendaTest(){

    }







}



