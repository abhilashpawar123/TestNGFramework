package com.qc.passFail;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qc.utils.ReadFile;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LoginTest extends BaseIntegration {

	// WebDriver driver;
	WebElement email;
	WebElement pass;
	WebElement signIn;
	static WebElement logout;
	// ExtentReports extent;
	// ExtentTest test;
	SoftAssert sa = new SoftAssert();
	ReadFile rfile = new ReadFile();
	Properties prop;

	// String UName;
	// String UPass;

	@BeforeSuite
	public void loadData() throws IOException {
		doSetup();
	}

	@BeforeTest
	public void reportGenerate() {
		setupReport();
	}

	@BeforeClass
	public void getXpath() {
		// this.driver=super.driver;
		email = driver.findElement(By.id("email"));
		pass = driver.findElement(By.id("password"));
		signIn = driver.findElement(By.id("submit"));
	}

	@AfterTest
	public void endReportGen() {
		endReport();

	}

	@BeforeMethod
	public void ClearData() {
		email.clear();
		pass.clear();
	}

	@Test(dataProvider = "getLoginData", priority = 0, alwaysRun = true, description = "QueueCodes")
	public void doLogin(String UName, String UPass) throws InterruptedException {
		test = extent.startTest("Queue Codes Login Test Case");
		email.sendKeys(UName);
		pass.sendKeys(UPass);
		signIn.click();
		test.log(LogStatus.PASS, "Login with Excel data");
		Thread.sleep(1000);
	}

	@AfterMethod
	public void doAssert() {
		String actual = driver.getTitle();
		String expResult = "Queue Codes | Log in";
		if (actual.equalsIgnoreCase(expResult)) {
			try {
				Assert.assertEquals(actual, expResult);
				test.log(LogStatus.PASS, "Login with Excel data");
			} catch (Exception e) {
				// TODO: handle exception
				test.log(LogStatus.FAIL, "Assertion Failed..");
			}

		} else {

			Assert.assertEquals(actual, "Queue Codes | Dashboard");
			test.log(LogStatus.FAIL, "Assertion Failed..");
			doLogout();

		}
		extent.endTest(test);
	}

	@AfterSuite
	public void tearDown1() {

		tearDown();
		// sa.assertAll();
	}

	// @AfterTest
	public void doLogout() {
		logout = driver.findElement(By.id("hlogout"));
		logout.click();
	}

}
