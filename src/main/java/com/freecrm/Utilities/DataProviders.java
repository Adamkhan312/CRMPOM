package com.freecrm.Utilities;


import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Hashtable;


public class DataProviders {
    Xls_Reader excel = new Xls_Reader(System.getProperty("user.dir") + "/src/test/resources/TestData/CrmAppTestData.xlsx");


    @DataProvider(name = "data-provider")
    public Object[][] getData(Method m) {

        String sheetName = m.getName();
        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);
        Object[][] data = new Object[rows - 1][cols];
        for (int rowNum = 2; rowNum <= rows; rowNum++) {
            for (int colNum = 0; colNum < cols; colNum++) {
                data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
            }
        }
        return data;
    }

    @DataProvider(name = "Data")
    public Object[][] getData2(Method m) {

        String sheetName = m.getName();
        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);

        Object[][] data = new Object[rows - 1][1];
        Hashtable<String, String> table = null;
        for (int rowNum = 2; rowNum <= rows; rowNum++) { // 2
            table = new Hashtable<String, String>();
            for (int colNum = 0; colNum < cols; colNum++) {
                // data[0][0]
                table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
                data[rowNum - 2][0] = table;
            }

        }
        return data;
    }
}