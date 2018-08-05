// Grid
// ANT
// Email the reports
// Jenkins

package com.qtpselenium.hybrid.rediff.driver;

import java.util.Hashtable;

import com.aventstack.extentreports.ExtentTest;
import com.qtpselenium.hybrid.rediff.keywords.AppKeywords;
import com.qtpselenium.hybrid.rediff.keywords.GenericKeywords;
import com.qtpselenium.hybrid.rediff.util.Xls_Reader;

public class DriverScript {
	ExtentTest test;
	AppKeywords app ;
	public DriverScript(ExtentTest t) {
		test = t;
	}

	//public static void main(String[] args) {
	public void executeKeywords(Xls_Reader xls, String testName,Hashtable<String,String> table) {
		
		int rows = xls.getRowCount("Keywords");
		//GenericKeywords app = new GenericKeywords();
		 app = new AppKeywords(test);// init the properties file
		
		for(int rNum=2;rNum<=rows;rNum++) {
			String tcid = xls.getCellData("Keywords", "TCID", rNum);
			if(tcid.equals(testName)) {
				String keyword = xls.getCellData("Keywords", "Keyword", rNum);
				String object = xls.getCellData("Keywords", "Object", rNum);
				String key = xls.getCellData("Keywords", "Data", rNum);
				String data = table.get(key);
				//System.out.println(tcid +" ---- "+ keyword+" ---- "+object+"----"+data );
				
				if(keyword.equals("openBrowser"))
					app.openBrowser(data);
				else if(keyword.equals("navigate"))
					app.navigate(object);
				else if(keyword.equals("click"))
					app.click(object);
				else if(keyword.equals("input"))
					app.input(object,data);
				else if(keyword.equals("validateLogin"))
					app.validateLogin(data);
				else if(keyword.equals("validateElementPresent"))
					app.validateElementPresent(object);
				else if(keyword.equals("validateTitle"))
					app.validateTitle(object);
				else if(keyword.equals("acceptAlertIfFound"))
					app.acceptAlertIfFound();
				else if(keyword.equals("login"))
					app.login(table.get("Username"),table.get("Password"));
				else if(keyword.equals("defaultLogin"))
					app.defaultLogin();
				else if(keyword.equals("clear"))
					app.clear(object);
				else if(keyword.equals("select"))
					app.select(object,data);
				
			}
			
			
		}

	}

	public void quit() {
		app.quit();
		
	}

}
