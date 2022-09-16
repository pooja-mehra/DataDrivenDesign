package utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import base.TestBase;

public class CommonUtils extends TestBase {

	public static String fullPath;

	public static String getScreenshotPath(String filePath) throws IOException {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String regEx = "[\\s|:]";
		String shotDate = new Date().toString().replaceAll(regEx, "_") + ".png";
		String path = new File(System.getProperty("user.dir")).getAbsolutePath();
		fullPath = path + "/target/surefire-reports/html/" + filePath + shotDate;
		String testJenkins = new File(fullPath).getAbsolutePath();
		FileUtils.copyFile(screenshot, new File(testJenkins));
		return testJenkins;
	}

	@DataProvider(name = "excelDP")
	public Object[][] getData(Method m) {
		System.out.println("Sheet name: " + m.getName());
		String sheetName = m.getName();

		int rowCount = excel.getRowCount(sheetName);

		Object[][] excelObject = new Object[rowCount - 1][1];

		ArrayList<Hashtable<String, String>> excelData = excel.getDataHashTableFormat(sheetName, rowCount);

		int i = 0;
		for (Hashtable<String, String> data : excelData) {
			excelObject[i][0] = data;
			i++;
		}
		return excelObject;
	}

}
