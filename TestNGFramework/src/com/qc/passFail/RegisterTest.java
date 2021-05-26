package com.qc.passFail;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

public class RegisterTest extends BaseIntegration {

	WebElement registerLink;
	WebElement uName;
	WebElement uMobile;
	WebElement uemail;
	WebElement regBtn;
	WebElement uPass;
	String username, mobileno, email, pass;

	@BeforeClass
	public void getXpath() {
		registerLink = driver.findElement(By
				.partialLinkText("Register a new membership"));
		registerLink.click();
		uName = driver.findElement(By.id("name"));
		uMobile = driver.findElement(By.id("mobile"));
		uemail = driver.findElement(By.id("email"));
		uPass = driver.findElement(By.id("password"));
		regBtn = driver.findElement(By.className("btn"));
	}
	
	@AfterTest
	public void endReportGen() {
		endReport();

	}
	
	@BeforeSuite
	public void loadData() throws IOException{
		doSetup();
	}
	
	@BeforeTest
	public void reportGenerate(){
		setupReport();
	}
	

	@BeforeMethod
	public void clearTextBox() {
		uName.clear();
		uMobile.clear();
		uemail.clear();
		uPass.clear();
	}

	@Test(dataProvider = "getRegisterData", priority = 0, alwaysRun = true, description = "QueueCodes")
	public void doRegister(String name, String mobile, String email, String pass)
			throws InterruptedException {
		this.username = name;
		this.mobileno = mobile;
		this.email = email;
		this.pass = pass;
		uName.sendKeys(name);
		uMobile.sendKeys(mobile);
		uemail.sendKeys(email);
		uPass.sendKeys(pass);
		regBtn.click();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}

	@AfterMethod
	public void doAssert() {
		if (username.isEmpty() || mobileno.isEmpty() || email.isEmpty()
				|| pass.isEmpty()) {
			String actualTitle = driver.getTitle();
			Assert.assertEquals(actualTitle, "Queue Codes | Registration Page");
			//test.log(LogStatus.FAIL, "Login with Excel data");
		} else {
			Alert alt = driver.switchTo().alert();
			String actualResult = alt.getText();
			Assert.assertEquals(actualResult, "User registered successfully.");
			alt.accept();
			//test.log(LogStatus.PASS, "Login with Excel data");
		}
	}
	@AfterSuite
	public void tearDown1() {

		tearDown();
		// sa.assertAll();
	}

}