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

    private By WeeklyViewTextDisplayDateRange = By.xpath("//span[@class='rbc-toolbar-label']");
    public By MonthYearTextDisplayed = By.cssSelector("span.rbc-toolbar-label");//CSS
    public By CurrentDateHighlightedOnCalendar = By.cssSelector("div.rbc-day-bg.rbc-today");//CSS
    public By WeekButton = By.xpath("//span[contains(text(),'Week')]");//XPATH
    public By NewAgendaButton = By.cssSelector("button.ui.linkedin.button");//CSS
    public By CreateNewEventText = By.xpath("//div[@class='ui loader']");//XPATH


    //---------------------------------------------------Getters for By-------------------------------------------------//

    public By getWeeklyViewTextDisplayDateRange() {
        return WeeklyViewTextDisplayDateRange;
    }


    //-----------------------------------------------------Methods------------------------------------------------------//

    public void navigateToCalendarPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin(Constants.email, Constants.password);
        loginPage.clickOnCalendarMenu();
    }

    public boolean checkMonthYearText() {
        waitForElementPresent(WeeklyViewTextDisplayDateRange);
        String MonthYearTextValueDisplayed = getDriver().findElement(MonthYearTextDisplayed).getText();
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

    public String getCurrentWeekRange() {
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
        String Actual = driver.findElement(WeeklyViewTextDisplayDateRange).getText().trim();
        System.out.println("Displayed text is : " + Actual);
        // Actual.replace("–","-");
        System.out.println("Expected text is  : " + getCurrentWeekRange());
        String Expected = getCurrentWeekRange();
        if (Expected.equalsIgnoreCase(Actual)) {
            System.out.println(" Expected and Actual is correct");
            return true;

        } else {
            return false;
        }
    }

    public void clickOnWeek() {
        clickOnElement(WeekButton);


    }

    public void clickOnNewAgendaButton() {

    }

    public void clickOnDay() {

    }

    public String weekRangeTextDisplayed() {
        String Actual = driver.findElement(WeeklyViewTextDisplayDateRange).getText().trim();
        return Actual;
    }





}
