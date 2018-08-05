package com.qtpselenium.hybrid.rediff.keywords;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.rediff.reports.ExtentManager;

public class GenericKeywords {
// webdriver layer
	WebDriver driver = null;
	Properties prop=null;
	ExtentTest test;
	
	
	public void openBrowser(String bName) {
		test.log(Status.INFO,"Opening browser "+bName);
		
		if(prop.getProperty("gridRun").equals("N")) {// run on local
			if(bName.equals("Mozilla"))
				driver = new FirefoxDriver();
			if(bName.equals("Chrome"))
				driver = new ChromeDriver();
			if(bName.equals("IE"))
				driver = new InternetExplorerDriver();
		}else {// run on grid
			
			DesiredCapabilities cap=null;
			if(bName.equals("Mozilla")){
				cap = DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setJavascriptEnabled(true);
				cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				
			}else if(bName.equals("Chrome")){
				 cap = DesiredCapabilities.chrome();
				 cap.setBrowserName("chrome");
				 cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			}
			
			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
	}
	
	public void quit() {
		driver.quit();
	}
	
	public void navigate(String urlKey) {
		test.log(Status.INFO,"Navigating to URL "+prop.getProperty(urlKey));
		driver.get(prop.getProperty(urlKey));
	}
	
	public void click(String locatorKey) {
		test.log(Status.INFO,"Clicking on object "+locatorKey);
		getObject(locatorKey).click();
	}
	
	public void input(String locatorKey, String data) {
		test.log(Status.INFO,"Typing in "+locatorKey+" value "+ data);
		getObject(locatorKey).sendKeys(data);
	}
	

	public void select(String locatorKey, String data) {
		test.log(Status.INFO,"Selecting from dropdown "+locatorKey+" value "+ locatorKey);

		Select s = new Select(getObject(locatorKey));
		s.selectByVisibleText(data);
		
	}
	

	
	


	
	public void clear(String locatorKey) {
		test.log(Status.INFO,"Clearing  "+locatorKey);
		getObject(locatorKey).clear();
		
	}
	
	public WebElement getObject(String locatorKey) {
		
		WebElement e = null;
		
		try {
			
			WebDriverWait wait = new WebDriverWait(driver, 20);
			if(locatorKey.endsWith("_xpath")) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(prop.getProperty(locatorKey))));
				e = driver.findElement(By.xpath(prop.getProperty(locatorKey))); // extract element if present
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prop.getProperty(locatorKey))));
			}else if(locatorKey.endsWith("_id")) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(prop.getProperty(locatorKey))));
				e = driver.findElement(By.id(prop.getProperty(locatorKey))); // extract element if present
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(prop.getProperty(locatorKey))));
			}else if(locatorKey.endsWith("_name")) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(prop.getProperty(locatorKey))));
				e = driver.findElement(By.name(prop.getProperty(locatorKey))); // extract element if present
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(prop.getProperty(locatorKey))));
			}else if(locatorKey.endsWith("_css")) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(prop.getProperty(locatorKey))));
				e = driver.findElement(By.cssSelector(prop.getProperty(locatorKey))); // extract element if present
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(prop.getProperty(locatorKey))));
			}
			
		}catch(Exception ex) {
			reportFailure("Object not extracted "+locatorKey );
		}
		
		return e;
		
	}
	
	public boolean isElementPresent(String locatorKey) {
		List<WebElement> e = null;
		
		if(locatorKey.endsWith("_xpath")) {
			e = driver.findElements(By.xpath(prop.getProperty(locatorKey))); // extract element if present
		}else if(locatorKey.endsWith("_id")) {
			e = driver.findElements(By.id(prop.getProperty(locatorKey))); // extract element if present
		}else if(locatorKey.endsWith("_name")) {
			e = driver.findElements(By.name(prop.getProperty(locatorKey))); // extract element if present
		}else if(locatorKey.endsWith("_css")) {
			e = driver.findElements(By.cssSelector(prop.getProperty(locatorKey))); // extract element if present
		}
		
		if(e.size()==0)
			return false;
		else 
			return true;
	}
	
	public void validateElementPresent(String locatorKey) {
		test.log(Status.INFO, "Validating element "+locatorKey);
		boolean result = isElementPresent(locatorKey);
		if(!result) {
			reportFailure("Element not found found "+locatorKey);
		}
	}
	
	public void validateTitle(String titleKey) {
		test.log(Status.INFO, "Validating title "+titleKey);
		String expectedTitle=prop.getProperty(titleKey);
		String actualTitle=driver.getTitle();
		if(!expectedTitle.equals(actualTitle)) {
			reportFailure("Titles did not match. Got title as "+actualTitle);
		}
	}
	
	public void reportFailure(String failureMsg) {
		// fail in extent reports
		test.log(Status.FAIL, failureMsg);
		// take screenshot and put in reports
		takeScreenShot();
		// fail in testng
		Assert.fail(failureMsg);
	}
	
	public void takeScreenShot(){
		// fileName of the screenshot
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_")+".png";
		// take screenshot
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			// get the dynamic folder name
			FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+screenshotFile));
			//put screenshot file in reports
			test.log(Status.FAIL,"Screenshot-> "+ test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void acceptAlertIfFound() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			test.log(Status.INFO, "Alert accepted");
		}catch(Exception e) {
			// chrome
			test.log(Status.INFO, "Alert not found");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
