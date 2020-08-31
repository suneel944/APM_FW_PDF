package keywords;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import base.baseClass;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import static io.appium.java_client.touch.TapOptions.tapOptions;

public class appiumKeywords extends baseClass 
{
	//********************************************************************
	//Declarations
	//********************************************************************
	static String ClassName;

	static String TestCaseName;

	static File FILE;

	static File imFILE;

	static StopWatch pageLoad = new StopWatch();
	StopWatch timer = new StopWatch();

	/**
	 * Document
	 */
	static Document document;

	/**
	 * PdfWriter
	 */
	static PdfWriter writer;

	/**
	 * PdfPTables
	 */
	static PdfPTable statusTable;

	/**
	 * PdfCell
	 */
	static PdfPCell cell;

	/**
	 * Passed Step Count
	 */
	private static int passStepCount = 0;

	/**
	 * Failed Step Count
	 */
	private static int failStepCount = 0;

	/**
	 * Error Step Count
	 */
	private static int errorStepCount = 0;

	/**
	 * Iteration Passed Step Count
	 */
	private static int iterationPassedStepCount = 0;

	/**
	 * Iteration Failed Step Count
	 */
	private static int iterationFailedStepCount = 0;

	/**
	 * Actual Execution Iteration Count
	 */
	private static int actualIterationCount = 0;

	/**
	 * Iteration Counter For Summary
	 */
	private static int itr = 1;

	/**
	 * Iteration Count
	 */
	private static int iteration;

	/**
	 * Initial Flag
	 */
	private static boolean initialFlag = false;

	/**
	 * Iterate Flag
	 */
	private static boolean iterateFlag = false;

	/**
	 * Overall Run Result Flag
	 */
	private static boolean overalRunResultFlag = false;

	/**
	 * Iteration Run Result Flag
	 */
	private static boolean iterationRunResultFlag = false;

	/**
	 * Document Closure Flag
	 */
	private static boolean documentClosed = false;

	/**
	 * Appium Service URL
	 */
	private static String appiumServiceUrl;

	/**
	 * Process
	 */
	private static java.lang.Process p;

	/**
	 * Desired Capabilities
	 */
	private static DesiredCapabilities caps;

	//*******************************
	//Font Declarations
	//*******************************
	private static Font blackTimes = new Font(FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLACK);
	private static Font blackTimesNormal = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
	private static Font blackTimesBold = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
	private static Font redTimesBold = new Font(FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(231, 76, 60));
	private static Font redTimesNormal = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(231, 76, 60));
	private static Font blackTimesDefaultSize = new Font(FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD, BaseColor.BLACK);
	private static Font greenResult = new Font(FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(39, 174, 96));
	private static Font redResult = new Font(FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(231, 76, 60));

	//*******************************
	//Date Declarations For Run Summary
	//*******************************
	static java.util.Date runStartTimeStamp = new java.util.Date();
	static String[] date1 = runStartTimeStamp.toString().split(" ");
	static String[] date2 = date1[3].split(":");
	static String dateval = date2[0] + date2[1] + date2[2];

	//Current Directory
	private static String currentDir = System.getProperty("user.dir");

	//Dynamic Report Folder Path
	private static String dateFolder;

	public appiumKeywords(String testCaseName)
	{
		ClassName = testCaseName;
	}

	/**
	 * takeScreenshot - Take Screenshot Of A Active Web Page With The Help Of WebDriver 
	 * @return Screenshot Destination Path
	 */
	protected static String takeScreenshot()
	{
		//****************************************************************************

		//Image Time Stamp
		java.util.Date imgTimeStamp = new java.util.Date();
		String[] imgdate1 = imgTimeStamp.toString().split(" ");
		String[] imgdate2 = imgdate1[3].split(":");
		String imgdateval = imgdate2[0] + imgdate2[1] + imgdate2[2]; 

		//ImagePath
		String imgPath = currentDir +"\\images\\ScreenShots\\page_"+imgdate1[1] + imgdate1[2] + imgdateval+".png";

		//****************************************************************************
		try
		{
			//****************************************************************************
			//Folder path creation
			//****************************************************************************
			//If Images folder is not present create an image folder in current directory
			imFILE = new File(currentDir +"\\images");
			if (!imFILE.exists())
				imFILE.mkdir();

			//If Screenshots folder is not present, then create a screenshot folder in current directory
			imFILE = new File(currentDir + "\\images\\Screenshots");
			if (!imFILE.exists())
				imFILE.mkdir();

			//GetScreenShot Method Directory and Image File
			File getSreenShotMethodImageFile = new File (imgPath);

			//Take Screenshot of viewable area
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			//Write Screenshot to a file
			try {FileUtils.copyFile(scrFile, getSreenShotMethodImageFile);} 
			catch (IOException e) {/* TODO Auto-generated catch block*/ e.printStackTrace();}
		}
		catch(Exception e)
		{
			try {abortOnException(e);}
			catch (Exception e1){e1.printStackTrace();}
			e.printStackTrace();
		}
		return imgPath;
	}

	//******************************************************************************************************************************
	//******************************************************************************************************************************
	//******************************************************************************************************************************
	//Reporting Keywords
	//******************************************************************************************************************************
	//******************************************************************************************************************************
	//******************************************************************************************************************************

	//******************************************************************************************************************************
	//Start Report
	//******************************************************************************************************************************

	/**
	 * startTestReport - Initiates The PDF Test Report
	 * @param TestCaseName - Provide Test Case Name
	 * @param TestCaseObjective - Provide Test Case Objective
	 * @param testPlatformApplicationPackage - Provide the Test Platform Name
	 * @param testPlatformApplicationActivity - Provide the Test platform Application Activity
	 * @param varArgs - Provide Iteration Count, If Multiple Iteration Is Required
	 */
	public void startTestReport(String TestCaseName, String TestCaseObjective, String testPlatformApplicationPackage, String testPlatformApplicationActivity, String...varArgs)
	{	
		appiumKeywords.TestCaseName = TestCaseName;
		pageLoad.reset();
		pageLoad.start();

		//****************************************************************************
		//Folder Path Creation
		//****************************************************************************

		//Create pdf_Reports folder if it is not created
		FILE = new File(currentDir+"\\pdf_Reports");
		if (!FILE.exists())
			FILE.mkdir();

		//Create Folder Structure
		dateFolder = currentDir+"\\pdf_Reports\\"+date1[1]+"_"+date1[2]+"_"+date1[5];

		FILE = new File(dateFolder);
		if (!FILE.exists())
			FILE.mkdir();

		//Create page specific folder 
		FILE = new File(dateFolder+"\\"+ClassName);
		if (!FILE.exists())
			FILE.mkdir();

		try
		{
			if (initialFlag == false)
			{
				if(varArgs.length==1)
				{
					//Setting the count for internal purpose
					iteration = Integer.parseInt(varArgs[0]);
					actualIterationCount = Integer.parseInt(varArgs[0]);

					//Set iterationFlag to true
					iterateFlag = true;
				}

				//Create pdf instance
				document = new Document(PageSize.A4);
				writer = PdfWriter.getInstance(document, new FileOutputStream(new File(dateFolder+"\\"+ClassName+"\\"+appiumKeywords.TestCaseName+ "_"+ date1[1] + date1[2] + dateval +".pdf")));
				document.open();

				//***************************************************************************************************************************
				//Main Heading
				//***************************************************************************************************************************
				//Test Report Name

				//Add a line separator
				document.add(new LineSeparator(1f, 100, null, 0, -5));

				//Add Main Heading
				Font blackTimes = new Font(FontFamily.HELVETICA, 15, Font.BOLD, BaseColor.BLACK);
				Chunk mainHeading = new Chunk(TestCaseName, blackTimes);
				Paragraph p = new Paragraph(mainHeading);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(p);

				//Add a line separator
				document.add(new LineSeparator(1f, 100, null, 0, -5));

				//Add a dummy line
				document.add(new Paragraph("\n"));

				//***************************************************************************************************************************
				//Test Case Details
				//***************************************************************************************************************************
				//Test case Name
				document.add(new Paragraph("Testcase Name : " +TestCaseName, new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

				//Test Objective
				document.add(new Paragraph("Test Objective : " +TestCaseObjective, new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

				//Test platform Application Package
				document.add(new Paragraph("Test Platform AppLication Package: " +testPlatformApplicationPackage, new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

				//Test platform Application Activity
				document.add(new Paragraph("Test Platform AppLication Activity: " +testPlatformApplicationActivity, new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

				//Java Version
				document.add(new Paragraph("Java Version : " +System.getProperty("java.version"), new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

				//Host Name
				document.add(new Paragraph("Host Name : " +InetAddress.getLocalHost().getHostName(), new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

				//Operating System
				document.add(new Paragraph("Operating System : " +System.getProperty("os.name"), new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

				//Add a dummy line
				document.add(new Paragraph("\n"));

				//Add a line separator
				document.add(new LineSeparator(0.5f, 100, null, 0, -5));

				Image reportLogo = Image.getInstance(currentDir+"\\hybrid_logo.png");
				//If image size exceeds a threshold value decrease it to below size
				if ((reportLogo.getWidth()>525.00) | (reportLogo.getHeight()>500.00))
				{
					reportLogo.scaleToFit(500, 600);
					reportLogo.setAlignment(Element.ALIGN_CENTER);
				}

				//Add Logo
				document.add(reportLogo);

				//Add a new page/ page break
				document.newPage();

				if(actualIterationCount > 1)
				{
					//Add a line separator
					document.add(new LineSeparator(0.8f, 100,BaseColor.RED, 0, -5));

					//Iteration Heading
					Chunk itrHeading = new Chunk("Iteration : "+itr, blackTimes);
					Paragraph pItr = new Paragraph(itrHeading);
					pItr.setAlignment(Paragraph.ALIGN_CENTER);
					document.add(pItr);

					//Add a line separator
					document.add(new LineSeparator(0.8f, 100, BaseColor.RED, 0, -5));

					//Add a dummy line
					document.add(new Paragraph("\n"));
				}

				//Set flag to true
				initialFlag = true;
			}
			else
			{
				//Initiate the iteration constants if kwargs length is > 1
				iterationPassedStepCount = 0;
				iterationFailedStepCount = 0;

				//Add a new page
				document.newPage();

				//Add a line separator
				document.add(new LineSeparator(0.8f, 100, BaseColor.RED, 0, -5));

				//Iteration Heading
				Font blackTimes = new Font(FontFamily.HELVETICA, 15, Font.BOLD, BaseColor.BLACK);

				Chunk mainHeading = new Chunk("Iteration : "+itr, blackTimes);
				Paragraph p = new Paragraph(mainHeading);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				document.add(p);

				//Add a line separator
				document.add(new LineSeparator(0.8f, 100, BaseColor.RED, 0, -5));

				//Add a dummy line
				document.add(new Paragraph("\n"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * logResultAndCaptureScreenshot - Capture Screenshot And Log Test Results To Test Report
	 * @param Status - <b>PASS/FAIL/ERROR</b>
	 * @param StepName - Step name
	 * @param StepDescription - A brief description about Step action
	 * @param screenCapture - Provide <b>"YES"/"NO"</b>
	 * @param kwargs - Provide <b>time</b> or provide <b>custom image path</b> which needs to be added to report or if providing both provide <b>time</b> and <b>custom image path</b> respectively
	 * @throws DocumentException
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static void logResultAndCaptureScreenshot(String Status, String StepName, String StepDescription, String screenCapture, String ...kwargs) 
			throws DocumentException, MalformedURLException, Exception
	{
		//**************************************
		//Exit Criteria
		//If Document Is Closed, Exit Function
		//**************************************
		if (documentClosed) return;

		//**************************************

		java.util.Date date1 = new java.util.Date();
		//******************************************************************************************************************************
		//Basic Table format
		//***************************************************************************************************************************
		try
		{
			statusTable = new PdfPTable(new float[]{.5f, .5f, .2f, .6f});
			Chunk stepDetails =null;
			if(Status.equalsIgnoreCase("ERROR"))
				stepDetails = new Chunk("Error", redTimesBold);
			else
				stepDetails = new Chunk("Step Details", blackTimesBold);

			Paragraph p = new Paragraph(stepDetails);
			p.setAlignment(Element.ALIGN_LEFT);
			cell = new PdfPCell(p);
			cell.setColspan(4);
			cell.setBackgroundColor(new BaseColor(208, 211, 212));
			statusTable.addCell(cell);

			Chunk stepNameHeading = null;
			if(Status.equalsIgnoreCase("ERROR"))
				stepNameHeading = new Chunk("Error Name", blackTimesBold);
			else
				stepNameHeading = new Chunk("Step Name", blackTimesBold);

			cell = new PdfPCell(new Paragraph(stepNameHeading));
			cell.setBackgroundColor(new BaseColor(208, 211, 212));
			statusTable.addCell(cell);

			Chunk stepDescriptionHeading = null;
			if(Status.equalsIgnoreCase("ERROR"))
				stepDescriptionHeading = new Chunk("Error Description", blackTimesBold);
			else
				stepDescriptionHeading = new Chunk("Step Description", blackTimesBold);

			cell = new PdfPCell(new Paragraph(stepDescriptionHeading));
			cell.setBackgroundColor(new BaseColor(208, 211, 212));
			statusTable.addCell(cell);

			Chunk statusHeading = new Chunk("Status", blackTimesBold);
			cell = new PdfPCell(new Paragraph(statusHeading));
			cell.setBackgroundColor(new BaseColor(208, 211, 212));
			statusTable.addCell(cell);

			Chunk timeHeading = new Chunk("Time", blackTimesBold);
			cell = new PdfPCell(new Paragraph(timeHeading));
			cell.setBackgroundColor(new BaseColor(208, 211, 212));
			statusTable.addCell(cell);

			//******************************************************************************************************************************
			//Appending Data To Table
			//******************************************************************************************************************************

			//Step name
			Chunk stepName = new Chunk(StepName, blackTimesNormal);
			cell = new PdfPCell(new Paragraph(stepName));
			statusTable.addCell(cell);

			//Step description
			Chunk stepDescription = null;
			if(Status.equalsIgnoreCase("ERROR"))
				stepDescription = new Chunk(StepDescription, redTimesNormal);
			else
				stepDescription = new Chunk(StepDescription, blackTimesNormal);

			cell = new PdfPCell(new Paragraph(stepDescription));
			statusTable.addCell(cell);

			//Status
			if (Status.equalsIgnoreCase("PASS"))
			{
				Font green = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(39, 174, 96));
				Chunk greenStatus = new Chunk(Status, green);
				cell = new PdfPCell(new Paragraph(greenStatus));
				statusTable.addCell(cell);

				//Increment pass step count
				passStepCount++;
				iterationPassedStepCount++;
			}
			else if (Status.equalsIgnoreCase("Fail")|(Status.equalsIgnoreCase("ERROR")))
			{
				Font red = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(231, 76, 60));
				Chunk redStatus = new Chunk(Status, red);
				cell = new PdfPCell(new Paragraph(redStatus));
				statusTable.addCell(cell);

				if(actualIterationCount > 1)
					iterationRunResultFlag = true;

				//Change the result flag to "True"
				overalRunResultFlag = true;

				//Increment fail step count
				if(!(Status.equalsIgnoreCase("ERROR")))
				{
					failStepCount++;
					iterationFailedStepCount++;
				}
				else
					errorStepCount++;
			}

			//Time
			Chunk time = new Chunk(date1.toLocaleString(), blackTimesNormal);
			cell = new PdfPCell(new Paragraph(time));
			statusTable.addCell(cell);

			//Update Report
			updateTestReport();
			if (!(kwargs.length == 0)) 
				if (!kwargs[0].contains(currentDir)) 
					document.add(new Paragraph("Page Load Time : " +kwargs[0]+ "secs", new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

			//Add a dummy line
			document.add(new Paragraph("\n"));

			//Screen capture if needed
			//Test Step Details: Along With Image
			//Create a Dynamic table with respect to number of test logs
			if (screenCapture.equalsIgnoreCase("YES"))
			{
				//Add a dummy line
				document.add(new Paragraph("\n"));
				document.add(new Paragraph(new Paragraph("Screenshot : ", new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD))));
				//Image
				Image img = null;
				try
				{
					if(kwargs[0].contains(currentDir)||kwargs.length >1)
						img = Image.getInstance(kwargs[0]);
					else
						/*Suppose if the arg[0] does contain data and if it is not equal
						 * to the required condition
						 */
						throw new NullPointerException();
				}
				catch(NullPointerException | ArrayIndexOutOfBoundsException e)
				{
					img = Image.getInstance(takeScreenshot()); 
				}

				//If image size exceeds a threshold value decrease it to below size
				if (img.getHeight()>600)
				{
					img.scaleAbsoluteWidth(190);
					img.scaleAbsoluteHeight(250);
					img.setAlignment(Image.ALIGN_CENTER);
				}
				if (writer.getVerticalPosition(true) - document.bottom() < 100)
					document.newPage();

				document.add(img);

				//Add a dummy line
				document.add(new Paragraph("\n"));
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	//******************************************************************************************************************************
	//Update Report
	//******************************************************************************************************************************

	/**
	 * updateTestReport - Updates Test Report With Test Steps
	 */
	public static void updateTestReport()
	{
		if (statusTable != null)
		{
			statusTable.setSpacingBefore(15f);
			try 
			{
				//If in case the page space is less add a new page
				if (writer.getVerticalPosition(true)- document.bottom() < 160)
					document.newPage();

				//Add a dummy line
				document.add(new Paragraph("\n"));

				//Add a line separator
				document.add(new LineSeparator(0.5f, 100, null, 0, -5));

				//Add a dummy line
				document.add(new Paragraph("\n"));

				//Add the table
				document.add(statusTable);
			}
			catch (DocumentException e) 
			{
				e.printStackTrace();
			}
			statusTable.setSpacingAfter(15f);
		}
	}

	//******************************************************************************************************************************
	//End Report
	//******************************************************************************************************************************
	/**
	 * endTestReport - Ends The Test Report
	 */
	public static void endTestReport()
	{
		try
		{		
			//Add Iteration Summary
			if(iterateFlag==true)
				iterationSummary();
			else
				//Add Run Summary
				runSummary();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * runSummary - Appends Run Summary To Test Report
	 */
	@SuppressWarnings("deprecation")
	private static void runSummary()
	{
		//Add a new page
		document.newPage();

		java.util.Date runEndTimeStamp = new java.util.Date();
		//Stop Timer
		pageLoad.stop();

		//*********************************************************************
		//Result String
		String runResult;
		//Validate run result flag
		if(overalRunResultFlag == true)
			//Set Run result to FAIL
			runResult = "FAIL";
		else
			//Set Run result to PASS
			runResult = "PASS";

		//*********************************************************************
		try
		{	
			//*****************************************************************
			//Test Summary
			//*****************************************************************
			//Add a line separator
			document.add(new LineSeparator(1f, 100, null, 0, -5));

			//Add run summary
			Chunk summaryHeading = new Chunk("Run Summary", blackTimes);
			Paragraph p = new Paragraph(summaryHeading);
			p.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(p);

			//Add a line separator
			document.add(new LineSeparator(1f, 100, null, 0, -5));

			//Add a dummy line
			document.add(new Paragraph("\n"));

			//***************************************************************************************************************************
			//Test Run Details
			//***************************************************************************************************************************

			//Add a line separator
			document.add(new LineSeparator(0.5f, 100, null, 0, -5));

			//Add a dummy line
			document.add(new Paragraph("\n"));

			//Overall Status
			Chunk beginning = new Chunk("Result : ", blackTimesDefaultSize);
			Phrase p1 = new Phrase(beginning);
			if (runResult.equalsIgnoreCase("PASS"))
			{
				Chunk runresult = new Chunk(runResult, greenResult);
				p1.add(runresult);
				Paragraph p8 = new Paragraph();
				p8.add(p1);
				document.add(p8);
			}
			else
			{
				Chunk runresult = new Chunk(runResult, redResult);
				p1.add(runresult);
				Paragraph p8 = new Paragraph();
				p8.add(p1);
				document.add(p8);
			}

			//Overall Passed Steps
			document.add(new Paragraph("Overall Steps Passed : " +passStepCount, new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

			//Overall Failed Steps
			document.add(new Paragraph("Overall Steps Failed : " +failStepCount, new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

			//Error Count
			document.add(new Paragraph("Errors : " +errorStepCount, new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

			//Run Started
			document.add(new Paragraph("Run Started : " +runStartTimeStamp.toLocaleString(), new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

			//Run Ended
			document.add(new Paragraph("Run Ended : " +runEndTimeStamp.toLocaleString(), new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

			//Run Duration
			document.add(new Paragraph("Run Duration : "+(pageLoad.getTime()/1000)+ " seconds", new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

			//Add a dummy line
			document.add(new Paragraph("\n"));

			//Add a line separator
			document.add(new LineSeparator(0.5f, 100, null, 0, -5));

			//Close PDF Document
			document.close();

			//Change The Flag To True
			documentClosed = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * iterationSummary - Appends The Iteration Summary To Test Report Per Iteration
	 */
	private static void iterationSummary()
	{
		try
		{
			//Add a new page
			document.newPage();

			//Check whether it is single iteration or multiple iteration and then proceed
			//Provide miniature summary
			//Add a line separator
			document.add(new LineSeparator(1f, 100, null, 0, -5));

			//Add iteration summary
			Chunk summaryHeading = new Chunk("Iteration Summary", blackTimes);
			Paragraph p = new Paragraph(summaryHeading);
			p.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(p);

			//Add a line separator
			document.add(new LineSeparator(1f, 100, null, 0, -5));

			//Add a dummy line
			document.add(new Paragraph("\n"));

			//*********************************************************************
			//Iteration Result
			String iterationResult;

			//Validate iteration result flag
			if(iterationRunResultFlag == true)
				//Set Run result to FAIL
				iterationResult = "FAIL";
			else
				//Set Run result to PASS
				iterationResult = "PASS";

			//*********************************************************************

			//Iteration Status
			Chunk itrResult = new Chunk("Iteration "+itr+ " Result : ", blackTimesDefaultSize);
			itr++;
			Phrase p1 = new Phrase(itrResult);
			if (iterationResult.equalsIgnoreCase("PASS"))
			{
				Chunk iterationresult = new Chunk(iterationResult, greenResult);
				p1.add(iterationresult);
				Paragraph p8 = new Paragraph();
				p8.add(p1);
				document.add(p8);
			}
			else
			{
				Chunk iterationresult = new Chunk(iterationResult, redResult);
				p1.add(iterationresult);
				Paragraph p8 = new Paragraph();
				p8.add(p1);
				document.add(p8);
			}

			//*********************************************************************

			//Iteration Passed Steps
			document.add(new Paragraph("Iteration Steps Passed : " +iterationPassedStepCount, new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

			//Iteration Failed Steps
			document.add(new Paragraph("Iteration Steps Failed : " +iterationFailedStepCount, new Font(Font.FontFamily.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));

			//Add a dummy page
			document.newPage();

			//decrement the iterator
			iteration--;
			if (iteration==0)
				//Add run summary and close the pdf
				runSummary();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	//******************************************************************************************************************************
	//******************************************************************************************************************************
	//******************************************************************************************************************************
	//Keyword Library
	//******************************************************************************************************************************
	//******************************************************************************************************************************
	//******************************************************************************************************************************
	/**
	 * abortOnException - Logs Appropriate Exceptions To Report And Terminates The Execution
	 * @param exception Provide Exception As Argument
	 * @throws Exception
	 */
	public static void abortOnException(Exception exception) throws Exception
	{
		overalRunResultFlag = true;
		try
		{	
			//WebDriverException
			if(exception.toString().contains("InvalidArgumentException"))
				logResultAndCaptureScreenshot("ERROR", "Argument Error : Invalid Browser Name", exception.toString(), "YES");
			else if(exception.toString().contains("WebDriverException")) 
				logResultAndCaptureScreenshot("ERROR", "Fatal Error : Browser Has Been Closed/Failed Due To Network Failure", exception.toString(), "NO");
			//NullPointerException
			else if(exception.toString().contains("NullPointerException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : Runtime Exception (Null)", exception.toString(), "YES");
			//ConnectionClosedException
			else if(exception.toString().contains("ConnectionClosedException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : Disconnection In Driver", exception.toString(), "YES");
			//ElementNotVisibleException
			else if(exception.toString().contains("ElementNotVisibleException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : Element Not Visible (May Have Hidden Property)", exception.toString(), "YES");
			//UnknownServerException
			else if(exception.toString().contains("UnknownServerException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : Server Error", exception.toString(), "YES");
			//ErrorInResponseException
			else if(exception.toString().contains("ErrorInResponseException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : Fault On Server Side", exception.toString(), "YES");
			//ImeActivationFailedException
			else if(exception.toString().contains("ImeActivationFailedException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : IME Engine Activation Failure", exception.toString(), "YES");
			//ImeNotAvailableException
			else if(exception.toString().contains("ImeNotAvailableException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : IME Support Unavailable", exception.toString(), "YES");
			//JavascriptException
			else if(exception.toString().contains("JavascriptException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : javascript Exception", exception.toString(), "YES");
			//JsonException
			else if(exception.toString().contains("JsonException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : Json Exception", exception.toString(), "YES");
			//NoSuchFrameException
			else if(exception.toString().contains("NoSuchFrameException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : Frame Not Available", exception.toString(), "YES");
			//NotFoundException
			else if(exception.toString().contains("NotFoundException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : Element Does Not Exist In DOM", exception.toString(), "YES");
			//RemoteDriverServerException
			else if(exception.toString().contains("RemoteDriverServerException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : Server Response Failure", exception.toString(), "YES");
			//StaleElementReferenceException
			else if(exception.toString().contains("StaleElementReferenceException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : Element Is Detached From Current DOM", exception.toString(), "YES");
			//TimeoutException
			else if(exception.toString().contains("TimeoutException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : Time Out", exception.toString(), "YES");
			//NoSuchElementException
			else if(exception.toString().contains("NoSuchElementException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : Cannot Locate Element", exception.toString(), "YES");
			//ArrayIndexOutOfBoundsException
			else if(exception.toString().contains("ArrayIndexOutOfBoundsException")) 
				logResultAndCaptureScreenshot("ERROR", "Error : Illegal Index", "An Array Has Been Accessed With An Illegal Index", "NO");
			//NoSuchWindowException
			else if(exception.toString().contains("NoSuchWindowException")) 
				logResultAndCaptureScreenshot("Error", "Error : Target Window Does Not Exist", exception.toString(), "YES");
			//Other Exceptions
			else 
				logResultAndCaptureScreenshot("ERROR", "Error : Something Went Wrong!", exception.toString(), "YES");
		}
		finally
		{
			//Iteration Summary
			if(actualIterationCount > 1) 
				iterationSummary();
			//End Report
			runSummary();

			//Print The Below Statement To Make The User Aware
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			System.err.println("***********!!!EXCEPTION OCCURED!!!***********");
			System.out.println("************Terminated Execution*************");
			System.out.println("*******!!!Report Has Been Generated!!!*******");
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

			//Quit Driver
			try {driver.quit();}
			//To Catch InvocationTargetException Used Throwable
			catch (Throwable e) {System.err.println("Driver Termination Failed, As Driver Is Not Invoked Yet");}
			//Terminate Appium server (Destroy process)
			appiumKeywords.terminateAppiumServer();
			//Exit Execution
			System.exit(1);
		}
	}

	/**
	 * getDeviceID
	 */
	private String getDeviceID()
	{
		String deviceID="";
		try
		{
			java.lang.Runtime rt = java.lang.Runtime.getRuntime();
			// Start a new process: UNIX command ls
			p = rt.exec("adb devices");

			// Get process’ output: its InputStream
			java.io.InputStream is = p.getInputStream();
			java.io.BufferedReader reader = new java.io.BufferedReader(new InputStreamReader(is));

			//dummy read to skip the unwanted string
			reader.readLine();

			deviceID = reader.readLine().split("\t")[0];
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return deviceID;
	}

	/**
	 * Get Platform Version - Returns Platform Version
	 * @return String Platform Version
	 */
	private String getPlatformVersion()
	{
		String platformVersion = "";
		java.lang.Runtime rt = java.lang.Runtime.getRuntime();
		try 
		{
			// Start a new process: UNIX command ls
			p = rt.exec("adb shell getprop ro.build.version.release");
			// Get process’ output: its InputStream
			java.io.InputStream is = p.getInputStream();
			java.io.BufferedReader reader = new java.io.BufferedReader(new InputStreamReader(is));
			platformVersion = reader.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return platformVersion;
	}

	/**
	 * setDesiredCapabilities - Sets The Desired Capabilities
	 * @param deviceName - Provide Device Name
	 * @param platformName - Provide Platform Name (Android/IOS)
	 * @param appPackage - Provide The Application Package Name
	 * @param appActivity - Provide The Application Activity
	 */
	private void setDesiredCapabilities(String deviceName, String platformName, String appPackage, String appActivity)
	{
		caps = new DesiredCapabilities();
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		caps.setCapability(MobileCapabilityType.UDID, getDeviceID());
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, getPlatformVersion());
		caps.setCapability("appPackage", appPackage);
		caps.setCapability("appActivity", appActivity);
		caps.setCapability("autoGrantPermissions", true);
		caps.setCapability("automationName", "UiAutomator2");
		caps.setCapability("autoAcceptAlerts", true);
		caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 100);
	}

	/**
	 * launchAppiumServer - Launches Appium Service Using Default Appium Port
	 */
	public static void launchAppiumServer()
	{
		server = AppiumDriverLocalService.buildDefaultService();
		server.start();
		appiumServiceUrl = server.getUrl().toString();
	}

	/**
	 * terminateAppiumServer - Terminates the appium server and frees up the space
	 */
	public static void terminateAppiumServer()
	{
		//kill Appium server process
		server.stop();
		//Log For User Awareness
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("!!!!!!......Terminated Appium Server......!!!!!!");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	}

	/**
	 * startWatch - Start Timer HH:MM:SS
	 * @throws IOException
	 */
	public void startWatch() throws IOException 
	{
		timer.reset();
		timer.start();
	}

	/**
	 * stopWatch - Stop Timer HH:MM:SS
	 * @return - Stop Time
	 * @throws IOException
	 */
	public String stopWatch() throws IOException 
	{
		timer.stop();
		return Integer.toString((int) (timer.getTime()/1000));
	}

	/**
	 * launchApplication
	 * @param deviceName
	 * @param platformName
	 * @param appPackage
	 * @param appActivity
	 */
	public void launchApplication(String deviceName, String platformName, String appPackage, String appActivity)
	{

		/*Launch Appium Service*/
		launchAppiumServer();

		/*Set Desired Capabilities*/
		setDesiredCapabilities(deviceName, platformName, appPackage, appActivity);

		/*Instantiate Driver*/
		try
		{
			driver = new AppiumDriver<MobileElement>(new URL(appiumServiceUrl),caps);
			logResultAndCaptureScreenshot("PASS", "Launch Application", "Launched Application Successfully", "NO");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * tapOnElement - Performs Tap Action On The Target Element 
	 * @param by -  Element Locator Address (Xpath, CSS, ID, Classname..etc)
	 * @param elementName
	 */
	public void tapOnElement(By by, String elementName) throws Exception
	{
		try
		{
			wait = new WebDriverWait(driver, 30);
			MobileElement e1 = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(by));
			if (e1.isDisplayed())
			{
				startWatch();
				e1.click();
				logResultAndCaptureScreenshot("PASS", "Tap On Element", "Tapped On "+elementName+ " Element", "NO");
			}
		}
		catch (Exception e)
		{
			//Log
			logResultAndCaptureScreenshot("FAIL", "Tap Object", "Failed To Tap On " + elementName + " Element", "YES");
			// TODO: handle exception
			abortOnException(e);
		}
	}

	/**
	 * verifyPageDisplayed - Verify Page Is Displayed With The Help Of Unique Object In That Page
	 * @param by - Element Locator Address (Xpath, CSS, ID, Classname..etc)
	 * @param pageName - Page Name To Be Verified
	 * @throws Exception
	 */
	public void verifyPageIsDisplayed(By by, String pageName) throws Exception
	{
		try
		{
			wait = new WebDriverWait(driver, 30);
			MobileElement e1 = (MobileElement)wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			if(e1.isDisplayed())
				logResultAndCaptureScreenshot("PASS", "Verify "+pageName+ " Page Is Displayed", pageName+" Page Is Displayed", "YES", stopWatch());
			else
				logResultAndCaptureScreenshot("FAIL", "Verify Page Is Displayed", pageName + " page is not Displayed", "YES");
		}
		catch(Exception e)
		{
			//Log
			logResultAndCaptureScreenshot("FAIL", "Error : Verify Page Is Displayed", "Failed As " + pageName + " Page Could Not Be Located", "YES");
			// TODO: handle exception
			abortOnException(e);
		}
	}

	/**
	 * inputText - Enter Text In The Edit Field
	 * @param by - Element Locator Address (Xpath, CSS, ID, Classname..etc)
	 * @param elementname - Element Name Where In Text Has To Be Entered
	 * @param data - Text To Be Entered
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void inputText(By by, String elementname, String data) throws Exception 
	{
		try 
		{
			wait = new WebDriverWait(driver, 30);
			MobileElement e1 = (MobileElement)wait.until(ExpectedConditions.elementToBeClickable(by));
			if (e1.isDisplayed()) 
			{
				new TouchAction(driver)
				.tap(tapOptions()
						.withElement(ElementOption.element(e1)))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(250)))
				.perform();
				e1.clear();
				e1.sendKeys(data);
				logResultAndCaptureScreenshot("PASS", "Input Text", "'"+data+"'"+" Entered In " + elementname + " Text Field Successfully.", "NO");
			}
		} 
		catch (Exception e) 
		{
			//Log
			logResultAndCaptureScreenshot("FAIL", "Input", "Failed To Locate " +elementname+ " Text field.", "YES");
			// TODO: handle exception
			abortOnException(e);
		}
	}

	/**
	 * verifyElementIsNotVisible - Verify Element Is Not Displayed
	 * @param by - Element Locator Address (Xpath, CSS, ID, Classname..etc)
	 * @param elementName - Element Name
	 * @return - Boolean(True/False)
	 * @throws Exception
	 */
	public boolean verifyElementIsNotVisible(By by, String elementName) throws Exception
	{
		boolean result = false;
		try 
		{
			wait = new WebDriverWait(driver, 120);
			if(!wait.until(ExpectedConditions.invisibilityOfElementLocated(by)))
				logResultAndCaptureScreenshot("PASS", "Verify Element Is Not Visible", elementName+ " Element Is Not Visible", "NO");
			result = true;
		}
		catch(Exception e)
		{
			//Log
			logResultAndCaptureScreenshot("FAIL", "Verify Element Is Not Visible", elementName+ " Element Is Visible", "YES");
			// TODO: handle exception
			abortOnException(e);
		}
		return result;
	}

	/**
	 * verifyElementVisible - Verify Element Is Visible
	 * @param by - Element Locator Address (Xpath, CSS, ID, Classname..etc)
	 * @param elementName - Element Name
	 * @throws Exception 
	 */
	public boolean verifyElementVisible(By by, String elementName) throws Exception
	{
		boolean result = false;
		try
		{
			wait = new WebDriverWait(driver, 30);
			MobileElement e1 = (MobileElement)wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			if (e1.isDisplayed())
				logResultAndCaptureScreenshot("PASS", "Verify Element Is Visibile", elementName+" Element Is Visible", "NO");
			result = true;
		}
		catch (Exception e)
		{
			//Log
			logResultAndCaptureScreenshot("FAIL", "Verify Element Is Visible", "Failed To Identify "+elementName+" Element Presence", "YES");
			// TODO: handle exception
			abortOnException(e);
		}
		return result;
	}

	/**
	 * swipeVertical - Swipe Vertically Up Or Down Until the Element Is Displayed
	 * @param by - Element Locator Address (Xpath, CSS, ID, Classname..etc)
	 * @param start_xd - Start X Position
	 * @param start_yd - Start Y Position
	 * @param end_xd   - End X Position
	 * @param end_yd   - End Y Position
	 * @param duration - Duration In Seconds To Wait Before Performing Action
	 * @example - example : swipe up : 0.5, 0.8, 0.5, 0.2 and swipe down: 0.5, 0.2, 0.5, 0.2
	 */
	public void swipeVertical(By by, double start_xd, double start_yd, double end_xd, double end_yd, int duration) 
	{ 
		Boolean isFoundElement; isFoundElement = driver.findElements(by).size() > 0;
		while(!isFoundElement) 
		{ 
			swipeInVerticalUnits(start_xd, start_yd, end_xd, end_yd, duration); 
			isFoundElement = driver.findElements(by).size() > 0; 
		} 
	}

	/**
	 * swipeInVerticalUnits - Swipe In Vertical Units Up or Down
	 * @param start_xd - Start X Position
	 * @param start_yd - Start Y Position
	 * @param end_xd   - End X Position
	 * @param end_yd   - End Y Position
	 * @param duration - Duration In Seconds To Wait Before Performing Action
	 * @example - example : swipe up : 0.5, 0.8, 0.5, 0.2 and swipe down: 0.5, 0.2, 0.5, 0.2
	 */
	@SuppressWarnings("rawtypes")
	public void swipeInVerticalUnits(double start_xd, double start_yd, double end_xd, double end_yd, int duration) 
	{
		Dimension size = driver.manage().window().getSize();
		int start_x = (int)(size.width*start_xd);
		int start_y = (int)(size.height*start_yd);
		int end_x = (int)(size.width*end_xd);
		int end_y = (int)(size.height*end_yd);
		new TouchAction((AppiumDriver<MobileElement>)driver)
		.press(PointOption.point(start_x, start_y))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(duration)))
		.moveTo(PointOption.point(end_x, end_y)).release().perform();
	}

	/**
	 * swipeToText - Swipe To Text, Whether The Text Is Located In The Top Section Or Bottom Section
	 * @param by - Element Locator Address (Xpath, CSS, ID, Classname..etc)
	 * @param attribute - Attribute Of The Element From Which Text Has To Be Fetched
	 * @param text - Element Text Till Which Swiping Has To Be Performed
	 * @param start_x - Start X Position
	 * @param start_y - Start Y Position
	 * @param end_x   - End X Position
	 * @param end_y   - End Y Position
	 * @param duration - Duration In Seconds To Wait Before Performing Action
	 * @example - example : swipe up : 0.5, 0.8, 0.5, 0.2 and swipe down: 0.5, 0.2, 0.5, 0.2
	 * @return - Boolean (True/False)
	 * @throws InterruptedException
	 */
	public boolean swipeToText(By by, String attribute, String text, double start_x, double start_y, double end_x, double end_y, int duration) throws InterruptedException
	{
		int a = 0;
		boolean flag = false;
		do
		{
			List<MobileElement> lists = driver.findElements(by);
			for(MobileElement e : lists)
			{
				if(e.getAttribute(attribute).equalsIgnoreCase(text))
				{
					flag = true;
					break;
				}
			}
			if (!flag)
				swipeInVerticalUnits(start_x, start_y, end_x, end_y, duration);
			a++;
		}
		while(!flag || a==10);
		return flag;
	}

	public String getElementAttribute(By by, String attribute)
	{
		wait = new WebDriverWait(driver, 30);
		MobileElement e1 = (MobileElement)wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		return e1.getAttribute(attribute);
	}
}