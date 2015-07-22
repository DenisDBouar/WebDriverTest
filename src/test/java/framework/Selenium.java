package framework;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

	public class Selenium {
	private String imagesFolderPath;
	WebDriver driver = null;
	 
	/**
	 * openWebBrowser("firefox"); firefox, ie, chrome
	 */
	public void openWebBrowser(String browser) {
		switch (browser) {
			case "firefox":
				driver = new FirefoxDriver();
				break;
			case "ie":
				driver = new InternetExplorerDriver();
				break;
			case "chrome":{
				System.setProperty("webdriver.chrome.driver","C:\\Users\\Boa\\workspace\\TestWebDriver\\common\\chromedriver.exe");
				driver = new ChromeDriver();
				break;
			}
			default:
				break;
		}
		driver.manage().deleteAllCookies();
	}

	public void driverQuit() {
		driver.quit();
	}
	
	
	public void populateField(String testName, String path, String value, String getType, boolean screenShot) {
		String[] action = path.split("##");
		switch (action[0]) {
		case "INPUT":{
			sendKeys(testName, action[1], getType, value);
			if(screenShot)doScreenshots(testName);
			break;
		}
		case "BUTTON":{
			findXPathAndClic(testName, action[1], getType);
			if(screenShot)doScreenshots(testName);
			break;
			}
		case "LINK":{
			findXPathAndClic(testName, action[1], getType);
			if(screenShot)doScreenshots(testName);
			break;
			}
		default:
			break;
	}
	}

	/**
	 * . 
	 * <p>
	 * @param utestName The name of you test name
	 * @param path xpath, id, class
	 * @param getType  xpath, id, class
	 * @param delay delay before starting method 10 = 1sec
	 * @param screenShot    true/false if you want to save screenshot.
	 */
	public void findXPathAndClic(String testName, String path, String getType) {
		Assert.assertTrue(isElementPresent(path, getType), testName);
		WebElement element = driver.findElement(getElementByType(path, getType));
		element.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void findXPathIframeAndClic(String testName, String iframe, String path, String getType) {
		WebElement element = driver.findElement(getElementByType(iframe, "classname"));
		driver.switchTo().frame(element);
		driver.findElement(getElementByType(path, getType)).click();
		
		delay(20);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.switchTo().defaultContent();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	
	/**
	 * . 
	 * <p>
	 * @param utestName The name of you test name
	 * @param path xpath, id, class
	 * @param getType  xpath, id, class
	 * @param key  String will be typed in field
	 * @param screenShot    true/false if you want to save screenshot.
	 */
	public void sendKeys(String testName, String path, String getType, String key) {
		Assert.assertTrue(isElementPresent(path, getType), testName);
		clear(path);
		WebElement element = driver.findElement(getElementByType(path, getType));
		element.sendKeys(key);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	
	public void sendKeysIframe(String testName, String iframe, String path, String getType, String key) {
		Assert.assertTrue(isElementPresent(iframe, "classname"), testName);
		WebElement element = driver.findElement(getElementByType(iframe, "classname"));
		driver.switchTo().frame(element);
		driver.findElement(getElementByType(path, getType)).sendKeys(key);
		
		delay(20);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.switchTo().defaultContent();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void clear(String xPath) {
		WebElement element = driver.findElement(By.xpath(xPath));
		element.clear();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	/**
	 * . 
	 * <p>
	 * @param utestName The name of you test name
	 * @param path xpath, id, class
	 * @param getType  xpath, id, class
	 * @param getSelectType  byvalue, byindex, byvisibletext
	 * @param visibleText  String will be typed in field
	 * @param delay delay before starting method 10 = 1sec
	 * @param screenShot    true/false if you want to save screenshot.
	 */
	public void select(String testName, String path, String getType, String visibleText, String getSelectType) {
		Select sel = new Select(driver.findElement(getElementByType(path, getType)));
		switch (getSelectType) {
		case "byvalue": sel.selectByValue(visibleText);
			break;
		case "byindex":  sel.selectByIndex(1);
		break;
		case "byvisibletext":  sel.selectByVisibleText(visibleText);
			break;
		default:
			break;
		}
	}

	public By getElementByType(String path, String getType){
		By by = null;
		switch (getType) {
		case "xpath": by = By.xpath(path);
			break;
		case "id":  by = By.id(path);
		break;
		case "classname":  by = By.className(path);
			break;
		default:
			break;
		}
		return by;
	}
	
	public boolean isElementPresent(String path, String getType){
		try {
		      WebDriverWait wait = new WebDriverWait(driver, 30);
		      wait.until(ExpectedConditions.visibilityOfElementLocated(getElementByType(path, getType)));
		      return true;
		    } 
		catch (Exception e) {
			  System.out.println("Path [" + path + "] was not found");
		      return false;
		    }
	}
	
	public ArrayList<String> getListString(String path, String getType){
		ArrayList<String> elem = null;
		try {
			List<WebElement> allElements = driver.findElements(getElementByType(path, getType)); 
				for (WebElement element: allElements) {
					 elem.add(element.getText());
				}
		    } 
		catch (Exception e) {
			  System.out.println("Path [" + path + "] was not found");
		    }
		return elem;
	}
	
	public ArrayList<String> getOutputListString(String path){
		ArrayList<String> elem = null;
		try {
			String[] array = path.split("##");
			for (String element: array) {
				elem.add(element);
			}
		} 
		catch (Exception e) {
			System.out.println("Path [" + path + "] was not found");
		}
		return elem;
	}
	
	public void isElementPresent(String testName, String path, String getType) {
		Assert.assertTrue(isElementPresent(path, getType), testName);
	}

	public String drivergetTitle() {
		return driver.getTitle();
	}

	/**
	 * 10 = 1sec
	 */
	public void delay(int time) {
		try {
			Thread.sleep(time * 100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param testName  comment
	 * @param path 		xpath, id, class
	 * @param getType   xpath, id, class
	 * @param delay     delay before starting method 10 = 1sec
	 */
	public String getText(String testName, String path, String getType) {
		Assert.assertTrue(isElementPresent(path, getType), testName);
		return driver.findElement(getElementByType(path, getType)).getText();
	}
	
	public boolean compareValue(String testName, String expected, String path, String getType){
		try{
			Assert.assertEquals(getText(testName, path, getType), expected);
			return true;
		}
		catch (AssertionError e){
			 return false;
		}
	}

	public void openURL(String testName, String url, boolean screenShot) {
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//if(screenShot)doScreenshots(testName);
	}
	public String getURL(){
		return driver.getCurrentUrl();
	}
	
	public void doScreenshots(String name){
		//WebDriver augmentedDriver = new Augmenter().augment(driver);
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
        	name = name.replaceAll("[<>@#$%^&*()]", "");
			FileUtils.copyFile(screenshot, new File(imagesFolderPath + "\\"+ name +".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void refreshPage(){
		driver.navigate().refresh();
	}
}
