package com.qtpselenium.hybrid.rediff.reports;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Temp {

	public static void main(String[] args) {
		Date d = new Date();
		System.out.println(d.toString().replaceAll(":", "_"));
		
		new File("D:\\a\\b").mkdirs();
		ExtentReports rep = ExtentManager.getInstance("D:\\Whizdom-Trainings\\Online Training Workspace\\report\\");
		ExtentTest test = rep.createTest("SampleTest");
		test.log(Status.INFO, "Starting sample test");
		test.log(Status.PASS, "Test Passed");
		rep.flush();
		
		
		

	}

}
