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

public class DeletePortfolioTest {

	
	ExtentReports rep;
	ExtentTest test;
	DriverScript ds;
	Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//xls//Testcases.xlsx");
	String testName="DeletePortfolioTest";

	@BeforeMethod
	public void init() {
		 rep = ExtentManager.getInstance("D:\\Whizdom-Trainings\\Online Training Workspace\\report\\");
		 test = rep.createTest(testName);
		 ds = new DriverScript(test);
	}	
	
	@AfterMethod
	public void quit() {
		rep.flush();
		if(ds!=null)
			ds.quit();
	}
	
	@Test(dataProvider="getData")
	public void deletePortFolioTest(Hashtable<String,String> data) {
		if(! DataUtil.isRunnable(testName, xls) || data.get("Runmode").equals("N")) {
			test.log(Status.SKIP, "Skipping the test as runmode is NO");
			throw new SkipException("Skipping the test as runmode is NO");
		}
		test.log(Status.INFO, "Starting "+testName+ " -Data -> "+ data);

		
		ds.executeKeywords(xls,testName,data);
		test.log(Status.PASS, "Ending  "+testName);
	}
	
	@DataProvider
	public Object[][] getData(){
		return DataUtil.getTestData(testName, xls);
	}
}
