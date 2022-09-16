package testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.TestBase;
import utils.CommonUtils;

public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass = CommonUtils.class, dataProvider = "excelDP")
	public void openAccountTest(Hashtable<String, String> data) throws InterruptedException {
		driver.findElement(By.cssSelector(OR.getProperty("OACBtn"))).click();
		Select cust = new Select(driver.findElement(By.cssSelector(OR.getProperty("customer"))));
		cust.selectByVisibleText(data.get("customer"));
		Select currency = new Select(driver.findElement(By.cssSelector(OR.getProperty("currency"))));
		currency.selectByVisibleText(data.get("currency"));
		Thread.sleep(1000);
		driver.findElement(By.cssSelector(OR.getProperty("processBtn"))).click();
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		String alertMsg = alert.getText();
		Assert.assertEquals(true, alertMsg.contains(OR.getProperty("alertACT")));
		alert.accept();

	}
}
