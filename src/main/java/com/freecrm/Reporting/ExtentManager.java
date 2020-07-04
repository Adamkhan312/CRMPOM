package com.freecrm.Reporting;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;


    public static ExtentReports createInstance() {
        String fileName= getReportName();
        String directory = System.getProperty("user.dir")+ "/report/";
        new File(directory).mkdirs();
        String path = directory + fileName;
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(path);
        // htmlReporter=new ExtentHtmlReporter("./reports/extent.html");
        htmlReporter.config().setDocumentTitle("FREE CRM AUTOMATION RESULTS");
        htmlReporter.config().setReportName("AUTOMATION TEST SUITE RESULTS");
        // htmlReporter.loadXMLConfig("/Users/adamkhan/IdeaProjects/FreeCRMAutomation/FreeCRMApp/src/test/resources/Properties/extent-config.xml");
        htmlReporter.config().setTheme(Theme.STANDARD);
        // Create an object of Extent Reports
        extent = new ExtentReports();
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User Name", "ADAM KHAN");
        extent.attachReporter(htmlReporter);


        return extent;

    }
    public static String getReportName(){
        Date d = new Date();
        String fileName="AutomationReport_"+d.toString().replace(":","_").replace(" ","_")+".html";
        return fileName;

    }
}
