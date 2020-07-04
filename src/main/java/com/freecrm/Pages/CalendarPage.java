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
import java.util.Calendar;

public class CalendarPage extends BasePage {

    //Class fields needed for functions
    Xls_Reader excel;
    LocalDate currentdate = LocalDate.now();
    private String CurrentMonthYear;


    //---------------------------------------------------Constructor----------------------------------------------------//
    public CalendarPage(WebDriver driver) {
        super(driver);

        this.excel = new Xls_Reader((System.getProperty("user.dir")+"/src/test/resources/TestData/CrmAppTestData.xlsx"));
    }


    //---------------------------------------------------Locators via By------------------------------------------------//
    private By calendarHeader = By.cssSelector("div.ui.header.item.mb5.light-black");
    private By calendarLink = By.xpath("//span[contains(text(),'Calendar')]");
    private By CalendarTableXPath = By.xpath("//div[@class='rbc-month-view']");

    public By WeeklyViewTextDisplayDateRange = By.cssSelector("span.rbc-toolbar-label:nth-child(2)");//Css
    public By MonthYearTextDisplayed = By.cssSelector("span.rbc-toolbar-label");//CSS
    public By CurrentDateHighlightedOnCalendar = By.cssSelector("div.rbc-day-bg.rbc-today");//CSS
    public By WeekButton= By.xpath("//span[contains(text(),'Week')]");//XPATH
    public By NewAgendaButton= By.cssSelector("button.ui.linkedin.button");//CSS
    public By CreateNewEventText= By.xpath("//div[@class='ui loader']");//XPATH


    //---------------------------------------------------Getters for By-------------------------------------------------//



    //-----------------------------------------------------Methods------------------------------------------------------//

    public void navigateToCalendarPage(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin(Constants.email,Constants.password);
        loginPage.clickOnCalendarMenu();
    }

    public boolean checkMonthYearText() {
        String MonthYearTextValueDisplayed = getDriver().findElement(MonthYearTextDisplayed).getText();
        //Getting the current month
        Month currentMonth = currentdate.getMonth();
        //getting the current year
        int currentYear = currentdate.getYear();
        //concat to format expected
        this.CurrentMonthYear = currentMonth + " " + currentYear;
        System.out.println("Text displayed is " + MonthYearTextValueDisplayed);

        if (MonthYearTextValueDisplayed.equalsIgnoreCase(CurrentMonthYear)) {
            return true;
        } else {
            return false;
        }
    }

    public String getCurrentWeekRange() {
        String BeginningOfWeekDate;
        String EndOfWeekDate;

        // Get calendar set to current date and time
        Calendar c = Calendar.getInstance();
        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        Format df = new SimpleDateFormat("MM/dd/yyyy");
        BeginningOfWeekDate = df.format(c.getTime());


        String[] tempDate1 = BeginningOfWeekDate.split("/");
        String FirstDayOfWeek = tempDate1[1];
        //Loop through the week and get last date. Probably could just add +7
        for (int i = 0; i <=6; i++) {
            c.add(Calendar.DATE, 1);
        }
        EndOfWeekDate = df.format(c.getTime());

        String[] tempDate2 = EndOfWeekDate.split("/");
        String LastDayOfWeek = tempDate2[1];

        Month currentMonth = currentdate.getMonth();

        String month = String.valueOf(currentMonth).toLowerCase();

        String newMonth = WordUtils.capitalize(month);//copy and pasted - from site
        String WeekRangeTextExpected =newMonth + " " + FirstDayOfWeek + " – " + LastDayOfWeek;


        return WeekRangeTextExpected;


    }

    public void clickOnWeek(){


    }

    public void clickOnNewAgendaButton(){

    }

    public void clickOnDay(){

    }


    public boolean checkIfWeekViewDateRangeisCorrect(){
        String Actual = driver.findElement(WeeklyViewTextDisplayDateRange).getText().trim();
        System.out.println("Displayed text is : "+ Actual);
        // Actual.replace("–","-");
        System.out.println("Expected text is  : "+ getCurrentWeekRange());
        String Expected = getCurrentWeekRange();
        if(Expected.equalsIgnoreCase(Actual)){
            System.out.println(" Expected and Actual is correct");
            return true;

        }else{
            return false;
        }
    }






}
