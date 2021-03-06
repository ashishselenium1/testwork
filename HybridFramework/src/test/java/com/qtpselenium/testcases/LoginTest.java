package com.qtpselenium.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.rediff.driver.DriverScript;
import com.qtpselenium.hybrid.rediff.reports.ExtentManager;
import com.qtpselenium.hybrid.rediff.util.DataUtil;
import com.qtpselenium.hybrid.rediff.util.Xls_Reader;

public class LoginTest {
	ExtentReports rep;
	ExtentTest test;
	DriverScript ds;
	Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//xls//Testcases.xlsx");

	@BeforeMethod
	public void init() {
		 rep = ExtentManager.getInstance("D:\\Whizdom-Trainings\\Online Training Workspace\\report\\");
		 test = rep.createTest("LoginTest");
		 ds = new DriverScript(test);
	}	
	
	@AfterMethod
	public void quit() {
		rep.flush();
		if(ds!=null)
			ds.quit();
	}
	
	@Test(dataProvider="getData")
	public void doLogin(Hashtable<String,String> data) {
		if(! DataUtil.isRunnable("LoginTest", xls) || data.get("Runmode").equals("N")) {
			test.log(Status.SKIP, "Skipping the test as runmode is NO");
			throw new SkipException("Skipping the test as runmode is NO");
		}
		test.log(Status.INFO, "Starting Login Test "+ data);

		
		ds.executeKeywords(xls,"LoginTest",data);
		test.log(Status.PASS, "Ending Login Test ");
	}
	
	@DataProvider
	public Object[][] getData(){
		return DataUtil.getTestData("LoginTest", xls);
	}
}
