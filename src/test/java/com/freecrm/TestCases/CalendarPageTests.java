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

    @Test(enabled= false)
    public void monthYearDisplayedTest(){
        CalendarPage calendarPage = new CalendarPage(driver);//login functionality is embodied into this function
        calendarPage.navigateToCalendarPage();//verify if the default text of calender month and year is correct
        Assert.assertTrue(calendarPage.checkMonthYearText());
    }

    @Test(enabled= false)
    public void clickWeekTest(){
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.navigateToCalendarPage(); //login functionality is embodied into this function
        calendarPage.clickOnWeek();//click On Week button
        //Check that the displayed week range is equal to expected week Range
        Assert.assertEquals(calendarPage.weekRangeTextDisplayed(),calendarPage.customExpectedDateRange(0));

    }

    @Test
    public void verifyThatCurrentDateValueIsBoldandOthersAreNotTest(){
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.navigateToCalendarPage(); //login functionality is embodied into this function
        Assert.assertTrue(calendarPage.checkDateValuesStyle());
    }

    @Test(enabled = false)
    public void verifyTodayButtonWhenInWeekViewTest(){
    }

    @Test(enabled = false)
    public void verifyTodayButtonWhenInDayViewTest(){
    }

    @Test
    public void clickMonthTest(){
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.navigateToCalendarPage(); //login functionality is embodied into this function
        calendarPage.clickOnDay();
        Assert.assertEquals(calendarPage.getDisplayedDayValue(),calendarPage.todaysDayValueExpected(0));
        calendarPage.clickOnMonthButton();
        Assert.assertTrue(calendarPage.checkMonthYearText());
    }

    @Test(enabled= false)
    public void navigateThroughDaysTest(){
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.navigateToCalendarPage(); //login functionality is embodied into this function
        calendarPage.clickOnDay();
        Assert.assertEquals(calendarPage.getDisplayedDayValue(),calendarPage.todaysDayValueExpected(0));
        calendarPage.clickOnNextButton();
        Assert.assertEquals(calendarPage.getDisplayedDayValue(),calendarPage.todaysDayValueExpected(1));
        calendarPage.clickOnPreviousButton();
        Assert.assertEquals(calendarPage.getDisplayedDayValue(),calendarPage.todaysDayValueExpected(0));
        calendarPage.clickOnPreviousButton();
        Assert.assertEquals(calendarPage.getDisplayedDayValue(),calendarPage.todaysDayValueExpected(-1));

    }
    @Test(enabled= false)
    public void checkDayValueIsDisplayedCorrectlyTest(){
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.navigateToCalendarPage(); //login functionality is embodied into this function
        calendarPage.clickOnDay();
        Assert.assertEquals(calendarPage.getDisplayedDayValue(),calendarPage.todaysDayValueExpected(0));
    }

    @Test(enabled= false)
    public void verifyNavigatingThroughWeeksTest(){
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.navigateToCalendarPage();//login functionality is embodied into this function
        calendarPage.clickOnWeek(); //click On Week button
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

    @Test(enabled= false)
    public void saveNewEventWithoutRequiredFields() throws InterruptedException {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.navigateToCalendarPage();//login functionality is embodied into this function
        calendarPage.waitForElementPresent(calendarPage.getNewAgendaButton());
        calendarPage.clickOnNewAgendaButton();
        calendarPage.getUrlAndGo();//TODO NEED TO FIX WHY PAGE IS LOADING BLANK
        calendarPage.clickOnSave();
        Assert.assertEquals(calendarPage.getErrorText(),"Error");
        calendarPage.clickOnOkinAlert();

    }

    @Test(dataProvider = "Data", dataProviderClass = DataProviders.class,enabled = false)
    public void NewEvent(Hashtable<String,String> data) throws InterruptedException {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.navigateToCalendarPage();//login functionality is embodied into this function
        calendarPage.waitForElementPresent(calendarPage.getNewAgendaButton());
        calendarPage.clickOnNewAgendaButton();
        calendarPage.getUrlAndGo();//TODO NEED TO FIX WHY PAGE IS LOADING BLANK
        calendarPage.waitForElementPresent(calendarPage.getNewEventTitleBox());
        calendarPage.enterTextInField(calendarPage.getNewEventTitleBox(),data.get("Title"));//Enter value on Title
        calendarPage.clickOnElement(calendarPage.getCalendarBox());//Click On Calendar Box and select the calendar
        calendarPage.selectCategory(data.get("Category"));//Select category
        calendarPage.enterTextInField(calendarPage.getDescriptionBox(),data.get("Description"));//fill in description
        calendarPage.clickOnTagsBox();
        calendarPage.enterTextInField(calendarPage.getTagBoxVisible(),data.get("Tags"));
        calendarPage.enterTextInField(calendarPage.getLocationBox(),data.get("Location"));
       calendarPage.clickOnSave(); //Save
    }

    @Test(enabled= false)
    public void verifySystemDateHighlighted(){

    }

    @Test(enabled = false)
    public void CreateEventAndVerifyDisplayedInAgendaTest(){

    }

}



