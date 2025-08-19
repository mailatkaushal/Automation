package com.learningselenium.testscripts;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class SeleniumPractice {
	public static void main(String[] args) {
		
		WebDriver driver=new EdgeDriver();
		
		
		// Fluent Wait
		
		driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
		driver.findElement(By.cssSelector("div[id='start'] button")).click();
		
		Wait<WebDriver> w=new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class);
		
		WebElement el=w.until(new Function<WebDriver,WebElement>(){
			public WebElement apply(WebDriver driver) {
				if (driver.findElement(By.cssSelector("div[id='finish'] h4")).isDisplayed()) return driver.findElement(By.cssSelector("div[id='finish'] h4"));
				else return null;
				
			}
		});
		
		System.out.println(el.getText());
		
		
		// Actions class
		
		driver.get("https://www.amazon.in/");
		
		Actions a=new Actions(driver);
		
		WebElement ele=driver.findElement(By.xpath("//div[@id='nav-link-accountList']//a[contains(@class,'nav-progressive-attribute')]"));
		a.moveToElement(ele).build().perform();
		a.moveToElement(ele).contextClick().build().perform();
		a.moveToElement(driver.findElement(By.id("twotabsearchtextbox"))).click().keyDown(Keys.SHIFT).sendKeys("sony zv").keyUp(Keys.SHIFT).build().perform();
		driver.findElement(By.id("nav-search-submit-button")).click();
		

		// Switching Window

		driver.get("https://rahulshettyacademy.com/loginpagePractise/");
		driver.findElement(By.className("blinkingText")).click();
		
		Set<String> windows=driver.getWindowHandles();
		Iterator<String> it=windows.iterator();
		
		String par=it.next();
		String child=it.next();
		
		driver.switchTo().window(child);
		
		String email=driver.findElement(By.cssSelector("a[href='mailto:mentor@rahulshettyacademy.com']")).getText();
		
		driver.switchTo().window(par);
		driver.findElement(By.id("username")).sendKeys(email);
		
		
		// handling frames  <iFrame>
		
		driver.get("https://jqueryui.com/droppable/");
		
        // driver.switchTo().frame(0);
		driver.switchTo().frame(driver.findElement(By.tagName("iFrame")));
		
		WebElement srcEl=driver.findElement(By.id("draggable"));
		WebElement desEl=driver.findElement(By.id("droppable"));
		
		Actions aa=new Actions(driver);
		aa.dragAndDrop(srcEl,desEl).build().perform();
		
		
		// Limiting WebDriver Scope 

		driver.get("http://qaclickacademy.com/practice.php");
		
		System.out.println(driver.findElements(By.tagName("a")).size());  // complete window links count
		
		WebElement footerEl = driver.findElement(By.id("gf-BIG"));
		System.out.println(footerEl.findElements(By.tagName("a")).size());  // footer links count
		
		WebElement firstColFooterEl = driver.findElement(By.xpath("//table/tbody/tr/td[1]/ul"));
		System.out.println(firstColFooterEl.findElements(By.tagName("a")).size());  // footer 1st Col links count
		
		
		// Opening multiple links in different tabs
		
		for (int i=1; i<firstColFooterEl.findElements(By.tagName("a")).size(); ++i) {
			String linkNewTab=Keys.chord(Keys.CONTROL,Keys.ENTER);
			firstColFooterEl.findElements(By.tagName("a")).get(i).sendKeys(linkNewTab);
		}
		
		
		// Getting titles from Child Tabs
		
		Set<String> windowss=driver.getWindowHandles();
		Iterator<String> itt=windowss.iterator();

		while (itt.hasNext()) {
			driver.switchTo().window(itt.next());
			System.out.println(driver.getTitle());
		}
		
		
		// handling Calendar UI
		
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");
		
		int date=15;
		int month=10;
		int year=2028;
		
		driver.findElement(By.className("react-date-picker__calendar-button")).click();
		driver.findElement(By.className("react-calendar__navigation__label")).click();
		driver.findElement(By.className("react-calendar__navigation__label")).click();
		driver.findElement(By.xpath("//button[text()='"+year+"']")).click();
		driver.findElements(By.cssSelector(".react-calendar__tile")).get(month-1).click();
		driver.findElement(By.xpath("//abbr[text()='"+date+"']")).click();
		
		List<WebElement> lis=driver.findElements(By.cssSelector(".react-date-picker__inputGroup__input"));
		
		for (int i=0; i<lis.size(); ++i) {
			System.out.print(lis.get(i).getAttribute("value"));
			if(i!=lis.size()-1) System.out.print("/");
		}
		
		driver.quit();
	}
}
