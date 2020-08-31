package testCases;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import helpers.excelHelper;
import pages.noBrokerReportsPage;

public class test_abuse_report_001
{
	@DataProvider
	public Object[][] data() throws IOException 
	{
		excelHelper xl = new excelHelper("Android", this.getClass().getSimpleName());
		return xl.getTestData();
	}

	//Test Case Objective
	/**
	 * Test Case Objective
	 */
	private static String TestCaseObjective = "Verify Abuse Report related functionality for the “Buy";

	@Test(dataProvider = "data")
	/*Note: While providing the arguments, please make sure 
	 * that the order of arguments passed inside run function 
	 * should be same as the order respected in data sheet, 
	 * otherwise script wont run
	 * OR In simple words, "the order of the arguments must be respected"*/
	public void run(String deviceName, String platformName, String appPackage, String appActivity, String searchLocation1, String searchLocation2, String username, String password, String suggestionNote) throws Exception
	{
		//Instantiate the page
		noBrokerReportsPage nbr = new noBrokerReportsPage();

		//Test case
		nbr.beforemethod(this.getClass().getSimpleName(), TestCaseObjective, appPackage, appActivity);
		nbr.test_abuse_report_001(deviceName, platformName, appPackage, appActivity, searchLocation1, searchLocation2, username, password, suggestionNote);
	}
}