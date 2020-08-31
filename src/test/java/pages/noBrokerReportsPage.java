package pages;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.baseClass;
import keywords.appiumKeywords;
import properties.noBrokerHome;
import properties.noBrokerLogin;
import properties.noBrokerReport;
import properties.noBrokerSearchResult;
import properties.noBrokerSuggestAnEdit;

public class noBrokerReportsPage extends baseClass
{
	appiumKeywords k = new appiumKeywords(this.getClass().getSimpleName());

	@BeforeMethod
	public void beforemethod(String TestCaseName, String TestCaseObjective, 
			String appPackage, String appActivity)
	{
		k.startTestReport(TestCaseName, TestCaseObjective, 
				appPackage, appActivity);
	}

	@Test
	public void test_abuse_report_001 (String deviceName, String platformName, String appPackage, String appActivity, String searchLocation1, String searchLocation2, String username, String password, String suggestionNote) throws Exception
	{
		try
		{
			//Launch Application
			k.launchApplication(deviceName, platformName, appPackage, appActivity);

			//Tap On Continue Button In The Permission Window
			k.tapOnElement(noBrokerHome.wePermissionContinue, "Continue");

			//Verify Home Page Is Displayed
			k.verifyPageIsDisplayed(noBrokerHome.weBuy, "Home");

			//Tap On The Buy Tab
			k.tapOnElement(noBrokerHome.weBuy, "Buy");

			//Click On The Search Related Box
			k.tapOnElement(noBrokerHome.weSearch, "Search Button");

			//Verify Related Search Section Is Displayed
			k.verifyPageIsDisplayed(noBrokerHome.lstBoxPlaces, "Search Related Section");

			//Input Place Name In The Edit Field
			k.inputText(noBrokerHome.edtSearchLocations, "Search Location", searchLocation1);

			//Select The First Option From The DropDown
			k.tapOnElement(noBrokerHome.lstFirstEntry, "Drop Down List");

			//Input Place Name In The Edit Field
			k.inputText(noBrokerHome.edtSearchLocations, "Search Location", searchLocation2);

			//Select The First Option From The DropDown
			k.tapOnElement(noBrokerHome.lstFirstEntry, "Drop Down List");

			//Tap On The Check Box "include nearby properties"
			k.tapOnElement(noBrokerHome.chkBoxIncludeNearByProperties, "Include Nearby Propertis Check Box");

			//Tap On The Search Button
			k.tapOnElement(noBrokerHome.btnSearch, "Search Button");

			//Verify Filter Section Is Displayed
			k.verifyPageIsDisplayed(noBrokerSearchResult.pgSearchResult, "Search Result");

			//Swipe Down Till The Fourth Tile
			for (int i = 0; i <=1;i++)
				k.swipeInVerticalUnits(0.5, 0.9, 0.5, 0.2, 2);
			
			//Tap On The 4th Image
			k.tapOnElement(noBrokerSearchResult.imgThumbnail, "Property Image");
			
			//After Tapping Fetch The BHK Details
			String actualBhk = k.getElementAttribute(noBrokerSearchResult.txtPropertyTitle, "text");

			//Verify Specific Property Search Page Is Displayed
			k.verifyPageIsDisplayed(noBrokerSearchResult.btnContactOwner, "Specific Propery Result");

			//Swipe Down Till The Wrong Info Button
			k.swipeToText(noBrokerSearchResult.weWrongInfo, "text", "Wrong Info", 0.5, 0.9, 0.5, 0.2, 2);

			//Verify Wrong Info Button Is Displayed
			k.verifyElementVisible(noBrokerSearchResult.weWrongInfo, "Wrong Info");

			//Tap On The Wrong Info Button
			k.tapOnElement(noBrokerSearchResult.weWrongInfo, "Wrong Info");

			//Verify Sign In Page Is Displayed
			k.verifyPageIsDisplayed(noBrokerLogin.edtEnterPhoneNumber, "No Broker Login");

			//Enter The Phone Number
			k.inputText(noBrokerLogin.edtEnterPhoneNumber, "Phone Number", username);

			//Verify Loading Message Is Not Displayed
			k.verifyElementIsNotVisible(noBrokerLogin.txtLoadingMessage, "Loading Message");

			//Tap On The I've Password Radio Button
			k.tapOnElement(noBrokerLogin.rbtnIHavePassword, "Password Radio Button");

			//Enter Password In The Password Field
			k.inputText(noBrokerLogin.edtPassword, "Password", password);
			
			//Swip Up
			k.swipeInVerticalUnits(0.125, 0.5, 0.125, 0.145, 2);

			//Tap On The Continue Button
			k.tapOnElement(noBrokerLogin.btnContinue, "Continue Button"); 

			//Verify Report Page Is Displayed
			k.verifyPageIsDisplayed(noBrokerReport.btnReport, "Report");

			//Tap On All The Check Boxes
			k.tapOnElement(noBrokerReport.cboxLocation, "Location Check Box");
			k.tapOnElement(noBrokerReport.cboxFakePhotos, "Fake Photos Check Box");
			k.tapOnElement(noBrokerReport.cboxBHKType, "BHK Type Check Box");
			k.tapOnElement(noBrokerReport.cboxAvailabilityDate, "Availability Date Check Box");
			k.tapOnElement(noBrokerReport.cboxPrice, "Price Check Box");
			k.tapOnElement(noBrokerReport.cboxOther, "Other Check Box");
			
			//Tap On The Report Button
			k.tapOnElement(noBrokerReport.btnReport, "Report Button");
			
			//Verify Thank You For Feedback Message Is Displayed
			k.verifyElementVisible(noBrokerSuggestAnEdit.imgGreenTick, "Thank You For Your Feeback Banner");
			
			//Change The Value Of The DropDown To Other Than The Existing One
			k.tapOnElement(noBrokerSuggestAnEdit.lstBhkType, "BHK Type Drop Down List");
			if(actualBhk.contains("4+"))
				k.tapOnElement(noBrokerSuggestAnEdit.ddl("3 BHK"), "3 BHK List");
			else if(actualBhk.contains("3"))
				k.tapOnElement(noBrokerSuggestAnEdit.ddl("4 BHK"), "4 BHK List");
			else if(actualBhk.contains("2"))
				k.tapOnElement(noBrokerSuggestAnEdit.ddl("4 BHK"), "4 BHK List");
			else if(actualBhk.contains("4 BHK"))
				k.tapOnElement(noBrokerSuggestAnEdit.ddl("3 BHK"), "4 BHK List");
			else
				k.tapOnElement(noBrokerSuggestAnEdit.ddl("4 BHK"), "4 BHK List");
			
			//Swipe Till Add A Note Edit Box Is Displayed
			k.swipeToText(noBrokerSuggestAnEdit.edtAddANote, "text", "Add a note", 0.5, 0.8, 0.5, 0.2, 2);
			
			//Add A Note
			k.inputText(noBrokerSuggestAnEdit.edtAddANote, "Note Edit Field", suggestionNote);
			
			//Tap On Save Changes Button
			k.tapOnElement(noBrokerSuggestAnEdit.btnSaveChanges, "Save Changes Button");
			
			//Verify Thank You Message Is Displayed
			k.verifyElementVisible(noBrokerSuggestAnEdit.txtThankYou, "Thank You Message Text");
			
		}
		catch (Exception e)
		{
			//Log the exception in the report and conclude
			appiumKeywords.logResultAndCaptureScreenshot("FAIL", "ERROR : Abrupt Exit", e.toString(), "NO");
		}
		finally
		{
			//Quit Driver
			try{driver.quit();}
			catch (Exception e) {appiumKeywords.abortOnException(e);}

			//End Report
			appiumKeywords.endTestReport();
			//Terminate Appium server (Destroy process)
			appiumKeywords.terminateAppiumServer();
		}
	}
}
