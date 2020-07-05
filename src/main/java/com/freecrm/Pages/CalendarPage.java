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
import java.util.*;

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
    private By previousButton = By.xpath(" //i[@class='chevron left icon']");
    private By nextButton = By.xpath("//i[@class='chevron right icon']");
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
    public void clickOnWeek() {
        clickOnElement(weekButton);

    }
    public void clickOnPreviousButton(){
        clickOnElement(previousButton);
    }

    public void clickOnNextButton(){
        clickOnElement(nextButton);
    }

    public void clickOnNewAgendaButton() {
        clickOnElement(newAgendaButton);
    }

    public void clickOnDay() {
        clickOnElement(dayButton);

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


    public  String getDayFromDate(String date) {
        String fullDate = date;
        String[] tempDate = fullDate.split("/");
        return tempDate[1].toString();
    }

    public  String getMonthFromDate(String date) {
        String fullDate = date;
        String[] tempDate = fullDate.split("/");
        return tempDate[0];
    }
    public  Calendar getCustomSundayDateCalendar(int weekDifference) {
        LocalDate currentdate = LocalDate.now();
        Month currentMonth = currentdate.getMonth();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.WEEK_OF_YEAR, weekDifference);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return c;
    }

    public  Date getDateCustomSunday(Calendar c) {
        return c.getTime();
    }

    public  Date getCustomSaturdayDate(int weekDifference) {
        Calendar c = getCustomSundayDateCalendar(weekDifference);
        for (int i = 1; i <= 6; i++) {
            c.add(Calendar.DATE, 1);
        }
        return c.getTime();
    }

    public static Map MonthHashMap() {
        Map<String, String> monthHash = new HashMap<String, String>(12);
        monthHash.put("01", "January");
        monthHash.put("02","February");
        monthHash.put("03","March");
        monthHash.put("04","April");
        monthHash.put("05","May");
        monthHash.put("06","June");
        monthHash.put("07","July");
        monthHash.put("08","August");
        monthHash.put("09","September");
        monthHash.put("10","October");
        monthHash.put("11","November");
        monthHash.put("12","December");
        return monthHash;
    }

    public  String customExpectedDateRange(int weekDifference) {
        Map monthHash = MonthHashMap();
        Format df = new SimpleDateFormat("MM/dd/yyyy");
        String sundayDate = df.format(getDateCustomSunday(getCustomSundayDateCalendar(weekDifference)));
        String saturdayDate = df.format(getCustomSaturdayDate(weekDifference));
        if (Integer.parseInt(getDayFromDate(sundayDate)) > 27) {
            String firstDate = monthHash.get(getMonthFromDate(sundayDate)).toString() + " " + getDayFromDate(sundayDate);
            String secondDate = monthHash.get(getMonthFromDate(saturdayDate)).toString() + " " + getDayFromDate(saturdayDate);
            String finalDateRange = firstDate + " – " + secondDate;
            System.out.println("Expected Week Range is " + finalDateRange);
            return finalDateRange;
        } else {
            String firstDate = monthHash.get(getMonthFromDate(sundayDate)).toString() + " " + getDayFromDate(sundayDate);
            String secondDate = getDayFromDate(saturdayDate);
            String finalDateRange = firstDate + " – " + secondDate;
            System.out.println("Expected Week Range is "+ finalDateRange);
            return finalDateRange;
        }
    }



    public String getWeekRange() {
        Locale locale = new Locale("en", "GB");
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
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
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



    public String weekRangeTextDisplayed() {
        String Actual = driver.findElement(weeklyViewTextDisplayDateRange).getText().trim();
        System.out.println("Displayed week range is : "+ Actual);
        return Actual;
    }





}
