package com.qtpselenium.hybrid.rediff.keywords;

import java.io.FileInputStream;
import java.util.Properties;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
// validations
// data from xls
// reporting - Extent
public class AppKeywords extends GenericKeywords{

	public AppKeywords(ExtentTest t) {
		// init the properties file
		test=t;
		if(prop==null) {
			try {
			prop= new Properties();
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//project.properties");
			prop.load(fs);
			}catch(Exception e) {
				// report - file not found
			}
		}
	}
	
	public void validateLogin(String expectedResult) {
		test.log(Status.INFO, "Validating Login");
		boolean result = isElementPresent("portfolioSelect_id");
		String actualResult="";
		if(result)
			actualResult="success";
		else
			actualResult="failure";
		if(!actualResult.equals(expectedResult))
			reportFailure("Got actual result as " +actualResult);
	}

	public void login(String username, String password) {
		test.log(Status.INFO, "Logging in with "+username+" / "+password);
		validateTitle("loginPageTitle");
		validateElementPresent("username_id");
		input("username_id",username);
		click("usernameSubmit_xpath");
		input("password_id",password);
		click("passwordSubmit_xpath");
		acceptAlertIfFound();	

		
	}

	public void defaultLogin() {
		test.log(Status.INFO, "Logging in with default ID and password");
		login(prop.getProperty("defaultUsername"),prop.getProperty("defaultPassword"));
		
	}
	
	
}
