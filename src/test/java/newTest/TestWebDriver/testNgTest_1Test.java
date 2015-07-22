package newTest.TestWebDriver;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import framework.Constants;
import framework.Selenium;

public class testNgTest_1Test {
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
  }
  
  @Test(dependsOnMethods={"startMainPage"}, dataProvider = "dp")
  public void TestsSetCloudCreditsQty(String inputStr) throws Exception {
    libAction.populateField("Main Page --> Set Search value='"+ inputStr +"'", constt.mapObjectsPaths.getProperty("searchbar-input"), inputStr, "xpath", false);
    libAction.populateField("Main Page --> Press button Search", constt.mapObjectsPaths.getProperty("searchbar-submit"), "",  "xpath", false);
  }
  
  @DataProvider
  public Object[][] dp() {
    return new Object[][] {
      new Object[] { "milk" },
      new Object[] { "jins" },
    };
  }
  
}
