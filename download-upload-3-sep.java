package com.learningselenium.testscripts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class UploadDownload {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
			
		WebDriver driver=new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/upload-download-test/index.html");
		
		// variables
		String fileName="C:\\Users\\kaush\\Downloads\\download.xlsx";
		String fruit="Apple";
		String priceColumn=driver.findElement(By.xpath("//div[text()='Price']")).getAttribute("data-column-id");
		
		// original price
		String applePrice=driver.findElement(By.xpath("//div[text()='"+fruit+"']/parent::div/parent::div/div["+priceColumn+"]")).getText();
		
		//download
		driver.findElement(By.id("downloadButton")).click();
		
		
		//edit xlsx
		String updateVal="1000";
		int col=getColIdx(fileName,"price");
		int row=getRowIdx(fileName,"Apple");
		updateCell(fileName,row,col,updateVal);
		
		
		//upload updated xlsx
		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(fileName);
		
		
		//wait for success
		By toastLocator=By.cssSelector(".Toastify__toast-body div:nth-child(2)");
		WebDriverWait w=new WebDriverWait(driver,Duration.ofSeconds(10));
		String data=w.until(ExpectedConditions.visibilityOfElementLocated(toastLocator)).getText();
		System.out.println(data);
		Assert.assertEquals(data,"Updated Excel Data Successfully.");
		w.until(ExpectedConditions.invisibilityOfElementLocated(toastLocator));
		
		
		// validate original vs updated
		String applePriceUpdated=driver.findElement(By.xpath("//div[text()='"+fruit+"']/parent::div/parent::div/div["+priceColumn+"]")).getText();
		Assert.assertEquals(updateVal,applePriceUpdated);
		
		driver.quit();
	}

	private static void updateCell(String fileName, int row, int col, String updatedVal) throws IOException {

		FileInputStream fis=new FileInputStream(fileName);
		XSSFWorkbook workbook=new XSSFWorkbook(fis);
        XSSFSheet sheet=workbook.getSheet("sheet1");

        Row roww=sheet.getRow(row-1);
        Cell cel=roww.getCell(col-1);
        
        cel.setCellValue(updatedVal);
        
        FileOutputStream fos=new FileOutputStream(fileName);
        workbook.write(fos);
        workbook.close();
        fis.close();
	}

	private static int getRowIdx(String fileName, String fruit) throws IOException {
		FileInputStream fis=new FileInputStream(fileName);
		XSSFWorkbook workbook=new XSSFWorkbook(fis);
        XSSFSheet sheet=workbook.getSheet("sheet1");

        int row=0;
        Iterator<Row> rowIt=sheet.iterator();
        for (int i=0;rowIt.hasNext();++i) {
        	Iterator<Cell> colIt=rowIt.next().cellIterator();
            while (colIt.hasNext()) {
            	Cell cel=colIt.next();
            	if(cel.getCellType()==CellType.STRING && cel.getStringCellValue().equalsIgnoreCase(fruit)) {
            		row=i;
            		break;
            	}
            }
        }
        
        return row;
        
	}

	private static int getColIdx(String fileName, String colName) throws IOException {

		FileInputStream fis=new FileInputStream(fileName);
		XSSFWorkbook workbook=new XSSFWorkbook(fis);
        XSSFSheet sheet=workbook.getSheet("sheet1");

        Iterator<Row> rowIt=sheet.iterator();
        Row firstRow=rowIt.next();
        Iterator<Cell> colIt=firstRow.cellIterator();

        int column=0;
        for (int i=0;colIt.hasNext();++i) {
        	Cell cel=colIt.next();
        	if(cel.getStringCellValue().equalsIgnoreCase(colName)) {
        		column=i;
        		break;
        	}
        }
		return column;
	}

}
