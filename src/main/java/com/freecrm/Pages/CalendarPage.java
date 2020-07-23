package com.freecrm.Pages;

import com.freecrm.Base.BasePage;
import com.freecrm.Config.Constants;
import com.freecrm.Utilities.Xls_Reader;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

public class CalendarPage extends BasePage {

    //Class fields needed for functions
    Xls_Reader excel;
    LocalDate currentdate = LocalDate.now();
    private String CurrentMonthYear;
    String emailValue = Constants.email;

    //---------------------------------------------------Constructor----------------------------------------------------//
    public CalendarPage(WebDriver driver) {
        super(driver);
        this.excel = new Xls_Reader((System.getProperty("user.dir") + "/src/test/resources/TestData/CrmAppTestData.xlsx"));
    }

    //---------------------------------------------------Locators via By------------------------------------------------//
    private By calendarHeader = By.cssSelector("div.ui.header.item.mb5.light-black");
    private By calendarLink = By.xpath("//span[contains(text(),'Calendar')]");
    private By CalendarTableXPath = By.xpath("//div[@class='rbc-month-view']//a");
    private By todaysCalendarDate = By.xpath("//div[@class='rbc-day-bg rbc-today']");

    //Date element locators
    private By weeklyViewTextDisplayDateRange = By.xpath("//span[@class='rbc-toolbar-label']");
    private By monthYearTextDisplayed = By.cssSelector("span.rbc-toolbar-label");//CSS
    private By currentDateHighlightedOnCalendar = By.cssSelector("div.rbc-day-bg.rbc-today");//CSS
    private By weekButton = By.xpath("//span[contains(text(),'Week')]");//XPATH
    private By monthButton = By.xpath("//span[contains(text(),'Month')]");
    private By newAgendaButton = By.xpath("//button[@class='ui linkedin button']");
    private By dayButton = By.xpath("//span[contains(text(),'Day')]");
    private By previousButton = By.xpath(" //i[@class='chevron left icon']");
    private By nextButton = By.xpath("//i[@class='chevron right icon']");
    private By createNewEventText = By.xpath("//div[@class='ui loader']");//XPATH
    private By dayHeaderValue = By.className("rbc-toolbar-label");

    //New Event Locators
    private By newEventTitleBox = By.name("title");
    private By saveNewEventButton = By.xpath("//button[@class='ui linkedin button']");
    private By alertOk = By.cssSelector("div.actions > button.ui.red.button");
    private By errorHeader = By.cssSelector("div.ui.small.modal.transition.visible.active > div.header");
    private By calendarBox = By.xpath("//div[@name='calendar']");
    private By calendarBoxValue = By.xpath("//div[contains(text(),'" + emailValue + "')]");
    private By categoryBox = By.name("category");
    private By categoryBoxDropDownOptions = By.xpath("//div[@class='visible menu transition']//div");
    private By descriptionBox = By.name("description");
    private By tagsBox = By.xpath("//label//div[@class='ui fluid multiple search selection dropdown']");
    private By tagBoxVisible = By.xpath("//div[@class='ui active visible fluid multiple search selection dropdown']//input[@class='search']");
    private By LocationBox = By.name("location");


    //---------------------------------------------------Getters for By-------------------------------------------------//

    public By getNewEventTitleBox() {
        return newEventTitleBox;
    }

    public By getNewAgendaButton() {
        return newAgendaButton;
    }

    public By getDescriptionBox() {
        return descriptionBox;
    }

    public By getCalendarBox() {
        return calendarBox;
    }

    public By getTagBoxVisible() {
        return tagBoxVisible;
    }

    public By getLocationBox() {
        return LocationBox;
    }

    //-----------------------------------------------------Methods------------------------------------------------------//

    public void navigateToCalendarPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin(Constants.email, Constants.password);
        loginPage.clickOnCalendarMenu();
    }
    //clicks on elements

    public void clickOnWeek() {
        clickOnElement(weekButton);
    }

    public void clickOnSave() {
        clickOnElement(saveNewEventButton);
    }

    public void clickOnOkinAlert() {
        clickOnElement(alertOk);
    }

    public void clickOnPreviousButton() {
        clickOnElement(previousButton);
    }

    public void clickOnNextButton() {
        clickOnElement(nextButton);
    }

    public void clickOnMonthButton() {
        clickOnElement(monthButton);
    }

    public void clickOnTagsBox() {
        clickOnElement(tagsBox);
    }


    public boolean checkDateValuesStyle() {
        List<WebElement> calendarElements = driver.findElements(CalendarTableXPath);
        boolean currentDayMatch = false;
        int falseCount = 0;
        boolean finalResult = false;
        String dayValue, fontWeight;
        try {

            for (int i = 0; i < calendarElements.size(); i++) {
                dayValue = calendarElements.get(i).getText();
                System.out.println("checking the date of " + dayValue);
                fontWeight = ((JavascriptExecutor) driver)
                        .executeScript("return window.getComputedStyle(arguments[0],'.rbc-date-cell.rbc-now').getPropertyValue('font-weight');", calendarElements.get(i)).toString();
                System.out.println("fontWeight is " + fontWeight);

                if (dayValue.equals(getDayFromDate(getSystemDate(0)))) {
                    if (fontWeight.equals(Constants.todaysFontWeight)) {
                        currentDayMatch = true;
                    } else {
                        currentDayMatch = false;
                    }
                } else if (!dayValue.equals(getDayFromDate(getSystemDate(0)))) {
                    if (fontWeight.equals(Constants.fontWeight)) {
                    } else {
                        falseCount++;
                    }
                }
                if ((currentDayMatch) && (falseCount == 0)) {
                    finalResult = true;
                } else if ((!currentDayMatch) && (falseCount == 0)) {
                    finalResult = false;
                } else if ((currentDayMatch) && (falseCount > 0)) {
                    finalResult = false;
                }
            }
        } catch (Exception e) {
            System.out.println("css style does not exist");
        }
        return finalResult;
    }

    //New events
    public void clickOnNewAgendaButton() {
        WebElement webElement = driver.findElement(newAgendaButton);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", webElement);
        getUrlAndGo();
    }

    public void clickOnDay() {
        clickOnElement(dayButton);
    }

    public String getErrorText() {
        return getTextOfElement(errorHeader);
    }

    public void selectCalendarEmail() {
        driver.findElement(calendarBoxValue).click();
        driver.findElement(calendarBoxValue).sendKeys(Keys.ENTER);
    }

    public void selectCategory(String value) {
        clickOnElement(categoryBox);
        List<WebElement> dropdown = driver.findElements(categoryBoxDropDownOptions);
        for (int i = 0; i < dropdown.size(); i++) {
            if (dropdown.get(i).getText().equals(value)) {
                dropdown.get(i).click();
            }
        }
    }

    //Date tests
    public String getDisplayedDayValue() {
        System.out.println("Day value displayed is " + getTextOfElement(dayHeaderValue));
        return getTextOfElement(dayHeaderValue);
    }

    public String todaysDayValueExpected(int dayOffset) {
        String firstValue = "00" + getDayFromDate(getSystemDate(dayOffset));
        String abreviatedMonth = MonthHashMap().get(getMonthFromDate(getSystemDate(dayOffset))).toString().substring(0, 3);
        String day = getDayFromDate(getSystemDate(dayOffset));
        String finalValue = firstValue + " " + abreviatedMonth + " " + day;
        System.out.println("Expected day value is " + finalValue);
        return finalValue;

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

    //Below are methods to figure out custom week ranges and functionality for date tests
    public String getDayFromDate(String date) {
        String fullDate = date;
        String[] tempDate = fullDate.split("/");
        return tempDate[1].toString();
    }

    public String getMonthFromDate(String date) {
        String fullDate = date;
        String[] tempDate = fullDate.split("/");
        return tempDate[0];
    }

    public Calendar getCustomSundayDateCalendar(int weekDifference) {
        LocalDate currentdate = LocalDate.now();
        Month currentMonth = currentdate.getMonth();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.WEEK_OF_YEAR, weekDifference);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return c;
    }

    public Date getDateCustomSunday(Calendar c) {
        return c.getTime();
    }

    public Date getCustomSaturdayDate(int weekDifference) {
        Calendar c = getCustomSundayDateCalendar(weekDifference);
        for (int i = 1; i <= 6; i++) {
            c.add(Calendar.DATE, 1);
        }
        return c.getTime();
    }

    public static Map MonthHashMap() {
        Map<String, String> monthHash = new HashMap<String, String>(12);
        monthHash.put("01", "January");
        monthHash.put("02", "February");
        monthHash.put("03", "March");
        monthHash.put("04", "April");
        monthHash.put("05", "May");
        monthHash.put("06", "June");
        monthHash.put("07", "July");
        monthHash.put("08", "August");
        monthHash.put("09", "September");
        monthHash.put("10", "October");
        monthHash.put("11", "November");
        monthHash.put("12", "December");
        return monthHash;
    }

    public String customExpectedDateRange(int weekDifference) {
        Map monthHash = MonthHashMap();
        Format df = new SimpleDateFormat("MM/dd/yyyy");
        String sundayDate = df.format(getDateCustomSunday(getCustomSundayDateCalendar(weekDifference)));
        String saturdayDate = df.format(getCustomSaturdayDate(weekDifference));
        if (Integer.parseInt(getDayFromDate(sundayDate)) > 27) {
            String firstDate = monthHash.get(getMonthFromDate(sundayDate)).toString() + " " + getDayFromDate(sundayDate);
            String secondDate = monthHash.get(getMonthFromDate(saturdayDate)).toString() + " " + getDayFromDate(saturdayDate);
            String finalDateRange = firstDate + " - " + secondDate;
            System.out.println("Expected Week Range is " + finalDateRange);
            return finalDateRange;
        } else {
            String firstDate = monthHash.get(getMonthFromDate(sundayDate)).toString() + " " + getDayFromDate(sundayDate);
            String secondDate = getDayFromDate(saturdayDate);
            String finalDateRange = firstDate + " – " + secondDate;
            System.out.println("Expected Week Range is " + finalDateRange);
            return finalDateRange;
        }
    }

    public String weekRangeTextDisplayed() {
        String Actual = driver.findElement(weeklyViewTextDisplayDateRange).getText().trim();
        System.out.println("Displayed week range is : " + Actual);
        return Actual;
    }


}
