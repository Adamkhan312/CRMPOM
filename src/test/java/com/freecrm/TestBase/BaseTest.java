package com.freecrm.TestBase;

import com.freecrm.Base.BasePage;
import com.freecrm.Reporting.ExtentManager;
import com.freecrm.Reporting.WebEventListener;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected static Properties config = new Properties();
    protected static FileInputStream fis = null;
    protected static EventFiringWebDriver e_driver;
    public static WebDriverEventListener eventListener;
    protected WebDriver driver;
    public BasePage page;


    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        //Properties File Setup
        try {
            fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/freecrm/Config/config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            config.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //WebDriver Setup
        if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equals("chrome")) {

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        //EventFiringWebDriver Register
        e_driver = new EventFiringWebDriver(driver);
        // Now create object of EventListerHandler to register it with EventFiringWebDriver
        eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;


        driver.get(config.getProperty("urlApp"));
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);

       page = new BasePage(driver);
    }

    @AfterMethod
    public void tearDown() {
       driver.quit();
       // ExtentManager.createInstance().flush();
    }

    public  WebDriver getDriver() {
        return this.driver;

    }

    public String takeScreenshot(WebDriver driver, String methodName){
        String fileName= getScreenshotName(methodName);
        String directory = System.getProperty("user.dir")+ "/screenshots/";
        new File(directory).mkdirs();
        String path = directory+fileName;

        try{
            File screenshot =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot,new File(path));
            System.out.println("*****************");
            System.out.println("Screenshot stored at:" + path);
            System.out.println("******************");
        }catch (Exception e){
            e.printStackTrace();
        }
        return path;
    }

    public static String getScreenshotName(String methodName){
        Date d = new Date();
        String fileName = methodName + "_" + d.toString().replace(":","_").replace(" ","_")+".png";
        return fileName;
    }
}
