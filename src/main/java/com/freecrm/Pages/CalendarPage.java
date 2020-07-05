package com.freecrm.Pages;

import com.freecrm.Base.BasePage;
import com.freecrm.Config.Constants;
import com.freecrm.Utilities.Xls_Reader;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class CalendarPage extends BasePage {

    //Class fields needed for functions
    Xls_Reader excel;
    LocalDate currentdate = LocalDate.now();
    private String CurrentMonthYear;


    //---------------------------------------------------Constructor----------------------------------------------------//
    public CalendarPage(WebDriver driver) {
        super(driver);

        this.excel = new Xls_Reader((System.getProperty("user.dir") + "/src/test/resources/TestData/CrmAppTestData.xlsx"));
    }


    //---------------------------------------------------Locators via By------------------------------------------------//
    private By calendarHeader = By.cssSelector("div.ui.header.item.mb5.light-black");
    private By calendarLink = By.xpath("//span[contains(text(),'Calendar')]");
    private By CalendarTableXPath = By.xpath("//div[@class='rbc-month-view']");

    private By weeklyViewTextDisplayDateRange = By.xpath("//span[@class='rbc-toolbar-label']");
    private By monthYearTextDisplayed = By.cssSelector("span.rbc-toolbar-label");//CSS
    private By currentDateHighlightedOnCalendar = By.cssSelector("div.rbc-day-bg.rbc-today");//CSS
    private By weekButton = By.xpath("//span[contains(text(),'Week')]");//XPATH
    private By newAgendaButton = By.cssSelector("button.ui.linkedin.button");//CSS
    private By dayButton = By.xpath("//span[contains(text(),'Day')]");
    private By createNewEventText = By.xpath("//div[@class='ui loader']");//XPATH



    //---------------------------------------------------Getters for By-------------------------------------------------//

    public By getWeeklyViewTextDisplayDateRange() {
        return weeklyViewTextDisplayDateRange;
    }


    //-----------------------------------------------------Methods------------------------------------------------------//

    public void navigateToCalendarPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin(Constants.email, Constants.password);
        loginPage.clickOnCalendarMenu();
    }

    public boolean checkMonthYearText() {
        waitForElementPresent(weeklyViewTextDisplayDateRange);
        String MonthYearTextValueDisplayed = getDriver().findElement(monthYearTextDisplayed).getText();
        //Getting the current month
        Month currentMonth = currentdate.getMonth();
        //getting the current year
        int currentYear = currentdate.getYear();
        //concat to format expected
        this.CurrentMonthYear = currentMonth + " " + currentYear;
        System.out.println("Text displayed is " + MonthYearTextValueDisplayed);
        System.out.println("Expected test was " + CurrentMonthYear);

        if (MonthYearTextValueDisplayed.equalsIgnoreCase(CurrentMonthYear)) {
            return true;
        } else {
            return false;
        }
    }

    public String getDayFromDate(String date){
        String fullDate = date;
        String[] tempDate = fullDate.split("/");
        return tempDate[1].toString();
    }


    public String getMondayOfCurrentWeek(){
        String BeginningOfWeekDate;
        // Get calendar set to current date and time
        Calendar c = Calendar.getInstance();
        // Set the calendar week to start monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //Format date
        Format df = new SimpleDateFormat("MM/dd/yyyy");
        return  df.format(c.getTime());//current date is monday of the week

    }

    public String getSundayOfCurrentWeek(){
        Calendar c = Calendar.getInstance();
        // Set the calendar week to start monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //Format date
        Format df = new SimpleDateFormat("MM/dd/yyyy");
        //get the last day of the week
        for (int i = 1; i <= 6; i++) {
            c.add(Calendar.DATE, 1);
        }
       return df.format(c.getTime());

    }

//    public date getCustomDate(int numberOfClick , Date getsystem date ){
//        //-
//        //+
//    }
//
//    click previousWeek(){
//
//    }
//
//    click necx=
//
//    public String getCurrentWeekRange(Custom System Date) {
//
//
//    }


    public String getWeekRange() {
        String BeginningOfWeekDate;
        String EndOfWeekDate;
        String lastMonth;
        YearMonth thisMonth = YearMonth.now();
        // YearMonth lastMonth;
        Month currentMonth = currentdate.getMonth();
        Month last = currentMonth.minus(1l);

        // Get calendar set to current date and time
        Calendar c = Calendar.getInstance();
        // Set the calendar week to start monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //Format date
        Format df = new SimpleDateFormat("MM/dd/yyyy");
        BeginningOfWeekDate = df.format(c.getTime());//current date is monday of the week
        System.out.println("BeginningOfWeekDate" + BeginningOfWeekDate.toString());
        String[] tempDate1 = BeginningOfWeekDate.split("/");
        String FirstDayOfWeek = tempDate1[1];
        //Loop through the week and get last date. Probably could just add +7
        for (int i = 1; i <= 6; i++) {
            c.add(Calendar.DATE, 1);
        }
        EndOfWeekDate = df.format(c.getTime());

        String[] tempDate2 = EndOfWeekDate.split("/");
        String LastDayOfWeek = tempDate2[1];

        String month = String.valueOf(currentMonth).toLowerCase();
        String newMonth = WordUtils.capitalize(month);
        String WeekRangeTextExpected = newMonth + " " + FirstDayOfWeek + " – " + LastDayOfWeek;
        if (Integer.parseInt(LastDayOfWeek) < 7) {

            DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM");
            lastMonth = last.toString().toLowerCase();
            WeekRangeTextExpected = WordUtils.capitalize(lastMonth) + " " + FirstDayOfWeek + " – " + newMonth + " " + LastDayOfWeek;
        }
        return WeekRangeTextExpected;
    }

    public boolean checkIfWeekViewDateRangeisCorrect() {
        String Actual = driver.findElement(weeklyViewTextDisplayDateRange).getText().trim();
        System.out.println("Displayed text is : " + Actual);
        // Actual.replace("–","-");
        System.out.println("Expected text is  : " + getWeekRange());
        String Expected = getWeekRange();
        if (Expected.equalsIgnoreCase(Actual)) {
            System.out.println(" Expected and Actual is correct");
            return true;

        } else {
            return false;
        }
    }

    public void clickOnWeek() {
        clickOnElement(weekButton);


    }

    public void clickOnNewAgendaButton() {
        clickOnElement(newAgendaButton);
    }

    public void clickOnDay() {
        clickOnElement(dayButton);

    }

    public String weekRangeTextDisplayed() {
        String Actual = driver.findElement(weeklyViewTextDisplayDateRange).getText().trim();
        return Actual;
    }





}
