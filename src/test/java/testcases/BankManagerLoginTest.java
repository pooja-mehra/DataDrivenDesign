package testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.TestBase;

public class BankManagerLoginTest extends TestBase {

	@Test
	public void bankManagerLoginTest() throws InterruptedException {
		// assert.equals being hard assert stops the rest of the execution too
		// so use try catch with reporting/logs etc in catch block to continue with the
		// rest of
		// the test
		click("BMLoginBtn");
		Thread.sleep(1000);
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("ACBtn"))), "Login successful");
		log.info("Login verified");
	}

}
