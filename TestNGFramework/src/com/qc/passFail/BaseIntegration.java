package com.qc.passFail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import jxl.Sheet;
import jxl.Workbook;
import jxl.Cell;
import jxl.read.biff.BiffException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.qc.utils.ReadFile;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

//import com.google.common.collect.Table.Cell;

//import com.google.common.collect.Table.Cell;

public class BaseIntegration {
	Properties prop;
	WebDriver driver;
	ReadFile rfile = new ReadFile();
	ExtentReports extent;
	ExtentTest test;

	//@BeforeSuite
	public void doSetup() throws IOException {
		prop = rfile.getCommonData();
		if (prop.getProperty("browser").equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					prop.getProperty("firefoxDriverPath"));
			driver = new FirefoxDriver();
		} else {
			System.setProperty("webdriver.chrome.driver",
					prop.getProperty("chromeDriverPath"));
			driver = new ChromeDriver();
		}

		// driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get(prop.getProperty("launchURL"));
		//return driver;
	}

	//@BeforeTest
	public void setupReport() {
		extent = new ExtentReports("test-output/ExtentReport.html");
		extent.addSystemInfo("Host Name", "Queue Codes Technology")
				.addSystemInfo("Environment", "Automation")
				.addSystemInfo("Username", "queuecodes@gmail.com");
		extent.loadConfig(new File("config.xml"));
	}

	//@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();

	}

	//@AfterSuite
	public void tearDown() {

		driver.close();
		// sa.assertAll();
	}

	@DataProvider
	public Object[][] getLoginData() throws BiffException, IOException {
		Object[][] testData = rfile.getData("Sheet3");
		return testData;
	}

	@DataProvider
	public Object[][] getRegisterData() throws BiffException, IOException {
		Object[][] testData = rfile.getData("Sheet2");
		return testData;

	}
}