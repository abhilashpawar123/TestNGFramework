package com.qc.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadFile {
	public Properties getCommonData() throws IOException {
		FileInputStream fis = new FileInputStream("config.properties");
		Properties prop = new Properties();
		prop.load(fis);
		return prop;
	}

	public Object[][] getData(String sheetNumber) throws BiffException,
			IOException {
		FileInputStream fis = new FileInputStream("ReadData.xls");
		Workbook book = Workbook.getWorkbook(fis);
		Sheet sheet = book.getSheet(sheetNumber);
		int rows = sheet.getRows();
		int columns = sheet.getColumns();

		String testData[][] = new String[rows - 1][columns];
		int temp = 0;
		for (int i = 1; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Cell cell = sheet.getCell(j, i);
				testData[temp][j] = cell.getContents();

			}
			temp++;
		}

		return testData;
	}

}
