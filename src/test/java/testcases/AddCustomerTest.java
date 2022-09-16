package testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import base.TestBase;
import utils.CommonUtils;

public class AddCustomerTest extends TestBase {

	@Test(dataProviderClass = CommonUtils.class, dataProvider = "excelDP")
	public void addCustomerTest(Hashtable<String, String> data) throws InterruptedException {

		if (data.get("runmode").toUpperCase().charAt(0) == 'Y') {
			driver.findElement(By.cssSelector(OR.getProperty("ACBtn"))).click();
			driver.findElement(By.cssSelector(OR.getProperty("firstName"))).sendKeys(data.get("firstname"));
			driver.findElement(By.cssSelector(OR.getProperty("lastName"))).sendKeys(data.get("lastname"));
			driver.findElement(By.cssSelector(OR.getProperty("postCode"))).sendKeys(data.get("postcode"));
			Thread.sleep(1000);
			driver.findElement(By.cssSelector(OR.getProperty("addCustBtn"))).click();
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			String alertMsg = alert.getText();
			Assert.assertEquals(true, alertMsg.contains(OR.getProperty("alertMsg")));
			alert.accept();
		} else {
			Reporter.log("This test has to be skipped");
			throw new SkipException("runmode for the " + data.get("firstname").toUpperCase() + " "
					+ data.get("lastname").toUpperCase() + " is set to No");

		}
	}
}
