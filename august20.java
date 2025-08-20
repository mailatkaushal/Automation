package Ribbon;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SeleniumPractice {
	public static void main(String[] args) throws InterruptedException, IOException {
		
		// handling table grids
		WebDriver driver=new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		List<WebElement> arr=driver.findElements(By.cssSelector(".tableFixHead td:nth-child(4)"));
		int sum=0;
		for (int i=0; i<arr.size(); ++i) {
			sum+=Integer.parseInt(arr.get(i).getText());
		}
		System.out.println(driver.findElement(By.className("totalAmount")).getText().split(":")[1].trim());
		System.out.println(sum);
		
		// Browser Options
		ChromeOptions options=new ChromeOptions();
		options.setAcceptInsecureCerts(true);
		WebDriver driverr=new ChromeDriver(options);  // *pass options
		driverr.manage().window().maximize();
		driverr.manage().deleteAllCookies();
		driverr.manage().deleteCookieNamed("qwerty");;
		driverr.get("https://expired.badssl.com");
		System.out.println(driverr.getTitle());
	
		// Screenshot
		WebDriver driverrr=new ChromeDriver();
		driverrr.get("https://www.leetcode.com");
		File src=((TakesScreenshot)driverrr).getScreenshotAs(OutputType.FILE);
		File dest=new File("C:\\Users\\kaush\\Documents\\Screenshot7.png");
		Files.copy(src.toPath(),dest.toPath());
		
		// Checking if link is broken
		WebDriver driverrrr=new ChromeDriver();
        driverrrr.get("https://rahulshettyacademy.com/AutomationPractice/");
        String url=driverrrr.findElement(By.linkText("Broken Link")).getAttribute("href");
        System.out.println(url);
        HttpURLConnection conn=(HttpURLConnection)new URL(url).openConnection();
        conn.setRequestMethod("HEAD");
        conn.connect();
        System.out.println(conn.getResponseCode());
		
		// Check broken links on entire page, Hard Assert vs Soft Assert
		WebDriver driverrrrr=new ChromeDriver();
		driverrrrr.get("https://rahulshettyacademy.com/AutomationPractice/");
		List<WebElement> links=driverrrrr.findElements(By.cssSelector("li[class='gf-li'] a"));
		SoftAssert a=new SoftAssert();
		for (WebElement link:links) {
	        String urll=link.getAttribute("href");
	        System.out.println(urll);
	        HttpURLConnection conn=(HttpURLConnection)new URL(urll).openConnection();
	        conn.setRequestMethod("HEAD");
	        conn.connect();
	        int resCode=conn.getResponseCode();
	        System.out.println(resCode);
            // Assert.assertTrue(resCode<=400, "The link with text "+link.getText()+" and url "+link.getAttribute("href")+" is broken with code"+resCode);  //Hard Assertion
	        a.assertTrue(resCode<400  , "The link with text "+link.getText()+" and url "+link.getAttribute("href")+" is broken with code"+resCode);
		}
		a.assertAll();
		
		ArrayList<String> names=new ArrayList();
		names.add("Kaushal");
		names.add("Kumar");
		int ans=0;
		for (int i=0; i<names.size(); ++i) {
			if (names.get(i).startsWith("K")) ans++;
		}
		System.out.println(ans);
	}

  // Run as => testNg Test 
	@Test
	public void streamfilter() {
		// Streams in Java
		ArrayList<String> names=new ArrayList();
		names.add("Kaushal");
		names.add("KaushalKaushal");
		names.add("Ukmar");
		Long cnt=names.stream().filter(s->s.startsWith("K")).count();
		System.out.println(cnt);
		// Terminal operation executes only if intermediate operations return true
		Long x=Stream.of("Kaushal","Ukmar").filter(s->{s.startsWith("K"); return true; }).count(); 
		System.out.println(x);
		names.stream().filter(s->s.length()>6).forEach(s->System.out.println(s));
	}
}
