package newTest.TestWebDriver;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import framework.Constants;
import framework.Selenium;

public class testNgTest_2Test {
  private Constants constt;
  Selenium libAction;
  
  @Parameters({ "browser" })
  @BeforeClass
  public void beforeClass(String browser) {
	  constt = new Constants();
	  constt.setObjectsPaths("page_property\\Page_Home.properties");
	  constt.setOutputData("test_property\\Search_validation.properties");
	  
	  libAction = new Selenium();
	  libAction.openWebBrowser(browser);
  }
  
  @AfterClass
  public void afterTest() {
     libAction.driverQuit();
  }
  
  @Test
  public void startMainPage() {
	  libAction.openURL("Main Page --> Open URL", constt.mapObjectsPaths.getProperty("url"), true);
	  
	  libAction.populateField("AllDepartments", constt.mapObjectsPaths.getProperty("AllDepartments"), "",  "xpath", false);
	  libAction.populateField("ElectronicsOffice", constt.mapObjectsPaths.getProperty("ElectronicsOffice"), "",  "xpath", false);
	  libAction.populateField("iPad", constt.mapObjectsPaths.getProperty("iPad"), "",  "xpath", false);
	  Assert.assertEquals(libAction.drivergetTitle(), constt.mapOutputData.getProperty("iPadPageTitle"));
	  
	  ArrayList<String> listActual = libAction.getListString(constt.mapObjectsPaths.getProperty("iPadProductNames"), "xpath");
	  ArrayList<String> listExpected = libAction.getOutputListString(constt.mapOutputData.getProperty("ListAppleProducts"));
	  if(listActual !=null && listExpected !=null){
		  for (int i = 0; i < listExpected.size(); i++){
			  Assert.assertEquals(listActual.get(i), listExpected.get(i));
			}
	  }
  }
  
}
