package listeners;

import java.io.IOException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import base.TestBase;
import utils.CommonUtils;

public class commonListener extends TestBase implements ITestListener, ISuiteListener {

	public String messageBody;

	public void onTestStart(ITestResult result) {
		test = rep.createTest(result.getName());
		System.out.println(excel.getColumnCount("test_suite"));
		if (!isRunnable(result.getName(), excel)) {
			throw new SkipException("Test case: " + result.getName().toUpperCase() + "  not runnable");
		}
	}

	/**
	 * Invoked each time a test succeeds.
	 *
	 * @param result <code>ITestResult</code> containing information about the run
	 *               test
	 * @see ITestResult#SUCCESS
	 */
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, result.getName() + "PASS");
		rep.flush();
	}

	/**
	 * Invoked each time a test fails.
	 *
	 * @param result <code>ITestResult</code> containing information about the run
	 *               test
	 * @see ITestResult#FAILURE
	 */
	public void onTestFailure(ITestResult result) {
		// In order to add any html tag to reportng use the flag:
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("Test failed!");
		try {
			String path = CommonUtils.getScreenshotPath(result.getName());
			Reporter.log("<a href = '" + path + "'" + ">screenshot</a>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(Status.FAIL, result.getName().toUpperCase() + "====>" + result.getThrowable().getMessage());
		try {
			test.fail(MediaEntityBuilder.createScreenCaptureFromPath(CommonUtils.getScreenshotPath(result.getName()))
					.build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// test.fail(MediaEntityBuilder.createScreenCaptureFromPath("img.png").build());
		// rep.flush();

	}

	/**
	 * Invoked each time a test is skipped.
	 *
	 * @param result <code>ITestResult</code> containing information about the run
	 *               test
	 * @see ITestResult#SKIP
	 */
	public void onTestSkipped(ITestResult result) {
		// not implemented
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("Test Skipped!");
		try {
			CommonUtils.getScreenshotPath(result.getName());
			System.out.println(CommonUtils.fullPath);
			Reporter.log("<a href = '" + CommonUtils.fullPath + "'" + ">screenshot</a>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Invoked each time a method fails but has been annotated with
	 * successPercentage and this failure still keeps it within the success
	 * percentage requested.
	 *
	 * @param result <code>ITestResult</code> containing information about the run
	 *               test
	 * @see ITestResult#SUCCESS_PERCENTAGE_FAILURE
	 */
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// not implemented
	}

	/**
	 * Invoked each time a test fails due to a timeout.
	 *
	 * @param result <code>ITestResult</code> containing information about the run
	 *               test
	 */
	public void onTestFailedWithTimeout(ITestResult result) {
		onTestFailure(result);
	}

	/**
	 * Invoked before running all the test methods belonging to the classes inside
	 * the &lt;test&gt; tag and calling all their Configuration methods.
	 */
	public void onStart(ITestContext context) {
		// not implemented
	}

	/**
	 * Invoked after all the test methods belonging to the classes inside the
	 * &lt;test&gt; tag have run and all their Configuration methods have been
	 * called.
	 */
	public void onFinish(ITestContext context) {
		// not implemented
	}

	public void onFinish(ISuite arg0) {

	}

}
