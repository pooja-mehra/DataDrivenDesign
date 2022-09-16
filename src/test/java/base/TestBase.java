package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ExcelReader;
import utils.ExtentUtils;

public class TestBase {

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	// devpinoyLogger is custom logger
	public static final Logger log = LogManager.getLogger(TestBase.class);
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "/src/test/resources/data/datadriven.xlsx");
	public static WebDriverWait wait;
	public ExtentReports rep = ExtentUtils.getInstance();
	public static ExtentTest test;
	public static String browser;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("deprecation")
	@BeforeSuite
	public void setUp() {

		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/resources/properties/config.properties");
			config.load(fis);
			int count = 0;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/resources/properties/OR.properties");
			OR.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// To configure parameters for jenkins
		if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {

			browser = System.getenv("browser");
		} else {

			browser = config.getProperty("browser");

		}
		config.setProperty("browser", browser);

		switch (config.getProperty("browser")) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;

		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;

		case "opera":
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();
			break;

		case "ie":
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			break;

		}

		driver.get(config.getProperty("testSiteURL"));
		log.debug("Navigated to: " + config.getProperty("testSiteURL"));
		driver.manage().window().maximize();
		driver.manage().timeouts()
				.implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
		wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (Throwable e) {
			log.error("login not valid: " + e);
			return false;
		}

	}

	// similar to click create other methods for type, select etc
	public void click(String locator) {

		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		test.log(Status.INFO, "clicked element: " + locator);

	}

	public static boolean isRunnable(String testName, ExcelReader excel) {
		String sheetName = "test_suite";
		System.out.println(testName);
		int rowCount = excel.getRowCount(sheetName);

		ArrayList<Hashtable<String, String>> excelData = excel.getDataHashTableFormat(sheetName, rowCount);

		for (Hashtable<String, String> row : excelData) {
			System.out.println(row);
			if (testName.equalsIgnoreCase(row.get("TCID")) && row.get("Runmode").toUpperCase().charAt(0) == 'Y') {
				return true;
			}
		}
		return false;

	}

	@AfterSuite
	public void tearDown() {
		driver.quit();
		log.debug("tests completed");
	}

}
