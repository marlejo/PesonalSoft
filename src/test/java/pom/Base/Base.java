package pom.Base;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base {
	
	private WebDriver driver;

	public Base(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebDriver chromeDriverConnection() {
		System.setProperty("webdriver.com.webdriver", "./src/test/resources/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		return driver;
	}
	
	public WebElement findElement(By locator) {
		return driver.findElement(locator);
	}
	
	public List<WebElement> findElements(By locator){
		return driver.findElements(locator);
	}
	
	public String getText(WebElement element) {
		return element.getText();
	}
	
	public String getText (By locator) {
		waitForElementVisible(locator);
		return driver.findElement(locator).getText();		
	}
	
	public void type(String inputText, By locator) {
		waitForElementVisible(locator);
		driver.findElement(locator).sendKeys(inputText);
	}
	
	public void click(By locator) {
		waitForElementClickable(locator);
		driver.findElement(locator).click();
	}
	
	public void click(WebElement element) {
		element.click();
	}
	
	public Boolean isDisplayed(By locator) {
		try {
			waitForElementVisible(locator);
			return driver.findElement(locator).isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}
	
	public void visit(String url) {
		driver.get(url);
	}
	
	public void mouseOver(By locator) {
		waitForElementClickable(locator);
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(locator)).perform();
	}
	
	public void waitForElementClickable(By locator) {
		WebDriverWait ewait = new WebDriverWait(driver, Duration.ofSeconds(10));
		ewait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	public void waitForElementVisible(By locator) {
		WebDriverWait ewait = new WebDriverWait(driver, Duration.ofSeconds(10));
		ewait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void waitForElementNotVisible(By locator) {
		WebDriverWait ewait = new WebDriverWait(driver, Duration.ofSeconds(10));
		ewait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	
	public void waitForTitleContains(String titleText) {
		WebDriverWait ewait = new WebDriverWait(driver, Duration.ofSeconds(10));
		ewait.until(ExpectedConditions.titleContains(titleText));
	}
	
	public void waitPageToLoad() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(webDriver -> "complete".equals(((JavascriptExecutor) webDriver)
		    .executeScript("return document.readyState")));
	}
	
	public String getUrl() {
		return driver.getCurrentUrl();
	}
	
	public void goBack () {
		driver.navigate().back();
	}
	
	public void scrollToElement(By locator) throws InterruptedException {
		WebElement element = driver.findElement(locator);

		String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
		                                            + "var elementTop = arguments[0].getBoundingClientRect().top;"
		                                            + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

		((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
	}
	
	public void scrollToElement(WebElement element) throws InterruptedException {
		String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
		                                            + "var elementTop = arguments[0].getBoundingClientRect().top;"
		                                            + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

		((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
	}
	
	public void zoomInOrOut() {
		String zoomJS;
		 JavascriptExecutor js = (JavascriptExecutor) driver;
		 zoomJS = "document.body.style.zoom='0.6'";  
		 js.executeScript(zoomJS);
	}
	
	public void clickByJS(By locator) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", findElement(locator));
	}
	

}
