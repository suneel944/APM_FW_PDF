package helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

public class excelHelper
{
	private static String currentDir = System.getProperty("user.dir");
	private static Workbook workbook;
	private static Sheet worksheet;
	private static int rows;
	private String testCaseName;
	private int testCaseStartRow = 0;
	private int testCaseEndRow=0;
	private int usedColumnsCount = 0;
	private int iterationCount = 0;
	private static File wbFILE;
	private static boolean updateWorkbook = false;
	private static FileOutputStream out;
	/*Default Workbook Path*/
	private static String workbookPath = currentDir+"\\src\\test\\resources\\TestData\\Input_Data.xls";
	/*Custom Workbook Path*/
	private static String customWorkbookPath;

	//WB(Workbook) time Stamp
	private static java.util.Date wbTimeStamp = new java.util.Date();
	private static String[] wbDate1 = wbTimeStamp.toString().split(" ");
	private static String[] wbDate2 = wbDate1[3].split(":");
	@SuppressWarnings("unused")
	private static String wbDateVal = wbDate2[0] + wbDate2[1] + wbDate2[2];

	/**
	 * ExcelLibPoi - Constructor
	 * @param sheetName - Sheet Name From Which Data Has To Be Fetched
	 * @param testCaseName - Test Case Name To be Executed
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public excelHelper (String sheetName, String testCaseName) throws EncryptedDocumentException, IOException
	{
		workbook = WorkbookFactory.create(new File(workbookPath));
		worksheet = workbook.getSheet(sheetName);
		this.testCaseName = testCaseName;
		rows = getRowCount();
		testCaseStartRow = getTestCaseStartOrEndRow(0);
		testCaseEndRow = getTestCaseStartOrEndRow(1);
		usedColumnsCount = getTestDataUsedColumnsCount();
		iterationCount = getIterationCount();
	}

	/**
	 * ExcelLib - Empty Constructor
	 */
	public excelHelper () {}

	/**
	 * getTestData - Fetch Test Data
	 * @return TestData[][]
	 */
	public Object[][] getTestData()
	{
		int row = 0; int col = 0;
		String [][] data = new String[iterationCount][usedColumnsCount-1];

		//Get Test Data
		for (int i = testCaseStartRow; i <= testCaseEndRow; i++)
		{
			col = 0;
			boolean flag = false;
			if (getCellData(i, usedColumnsCount).equalsIgnoreCase("YES")) 
			{
				flag = true;
				for (int j = 1; j < usedColumnsCount; j++)
				{
					data[row][col] = getCellData(i, j);
					col++;
				}
			}
			if(flag)
				row++;
		}
		return data;
	}

	/**
	 * iterationCount - Output Iteration Count Of Test Case
	 * @return Iteration Count
	 */
	public int iterationCount()
	{
		return iterationCount;
	}

	/**
	 * getIterationCount - Outputs A Value Which Denotes The Number Of Occurrence Execution
	 * @return - Iteration Count
	 */
	private int getIterationCount()
	{
		try
		{
			for (int i = testCaseStartRow; i <= testCaseEndRow; i++)
			{
				if (getCellData(i, usedColumnsCount).equalsIgnoreCase("YES"))
					iterationCount++;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (iterationCount > 0)
		{
			System.out.println("--------------------------------------------------------------------------------------");
			System.out.println("Total Number Of Iterations Selected For Test Script: '"+testCaseName+"' Is"+" "+iterationCount);	
			System.out.println("--------------------------------------------------------------------------------------");
		}
		else
		{
			System.err.println("--------------------------------------------------------------------------------------");
			System.err.println("Total number of iterations selected is 0. Please Check Execute Column In TestData.xls file");
			System.err.println("--------------------------------------------------------------------------------------");
			System.err.println(".............XXXXXXX.............Terminated Execution.............XXXXXXX.............");
			System.err.println("--------------------------------------------------------------------------------------");
			/*Terminate Execution*/
			System.exit(1);
		}
		return iterationCount;
	}

	/**
	 * getTestCaseStartOrEndRow - Fetch Test Case Start/End Row
	 * @param delimiter - 0/1 --> Start/End
	 * @return Start/End Row Number
	 */
	private int getTestCaseStartOrEndRow(int delimiter)
	{
		int rowNum = 0;

		Iterator<Row> rowIterator = worksheet.iterator();
		while(rowIterator.hasNext())
		{
			Row row = rowIterator.next();
			if (row.getCell(0) == null)
			{
				continue;
			}
			if (row.getCell(0).getStringCellValue().equals(testCaseName.trim())) 
			{
				rowNum = row.getRowNum();
				/*Delimiter - 0/1 --> break/continue*/
				if (delimiter == 0)
					break;
			}
		}
		return rowNum;
	}

	/**
	 * getRowCount - Outputs Row Count
	 * @return Row Count
	 */
	public static int getRowCount()
	{
		rows = worksheet.getLastRowNum()+1;
		return rows;
	}

	/**
	 * getRowCount - Outputs Row Count From Default Workbook's Specific Work Sheet
	 * @param sheetName - Name Of The Sheet From Which Row Count Has To Be Fetched
	 * @return - Row Count
	 */
	public static int getRowCount(String sheetName)
	{
		return workbook.getSheet(sheetName).getLastRowNum()+1;
	}

	/**
	 * getRowCount - Outputs Row Count From Specified Workbook's Specific Work Sheet
	 * @param workbook - Workbook Object
	 * @param sheetName - Name Of The Sheet From Which Row Count Has To Be Fetched
	 * @return - Row Count
	 */
	public static int getRowCount(Workbook workbook, String sheetName)
	{
		return workbook.getSheet(sheetName).getLastRowNum()+1;
	}

	/**
	 * getColumnCount - Outputs Column Count From Default Workbook's Default Work Sheet 
	 * @param rowNum - Row Number From Which Column Count Has To Be Extracted <b>Note: </b>(Row Index Starts From 0)
	 * @return - Last Column Index
	 */
	public static int getColumnCount(int rowNum)
	{
		return worksheet.getRow(rowNum).getLastCellNum();
	}

	/**
	 * getColumnCount - Outputs Column Count From Default Workbook's Specific Work Sheet
	 * @param rowNum - Row Number From Which Column Count Has To Be Extracted <b>Note: </b>(Row Index Starts From 0)
	 * @param sheetName - Sheet Name From Which Column Count Has To Be Fetched
	 * @return - Last Column Index
	 */
	public static int getColumnCount(int rowNum, String sheetName)
	{
		return workbook.getSheet(sheetName).getRow(rowNum).getLastCellNum();
	}

	/**
	 * getColumnCount - Outputs Column Count From Specified Workbook's Specific Work Sheet
	 * @param rowNum - Row Number From Which Column Count Has To Be Extracted <b>Note: </b>(Row Index Starts From 0)
	 * @param sheetName - Sheet Name From Which Column Count Has To Be Fetched
	 * @param workbookPath - Absolute Path Of Workbook From Which Column Count Has To Be Fetched
	 * @return - Last Column Index
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public static int getColumnCount(int rowNum, String sheetName, Workbook workbook) throws EncryptedDocumentException, IOException
	{
		return workbook.getSheet(sheetName).getRow(rowNum).getLastCellNum();
	}

	/**
	 * getUsedColumnsCount - Outputs The Number Of Column Used In The Test Case Row
	 * @return Used Column Count
	 */
	public int getTestDataUsedColumnsCount()
	{
		int count = 0;
		while (!worksheet.getRow(testCaseStartRow-1).getCell(count).getStringCellValue().equals("Execute"))
			count++;
		return count;
	}

	/**
	 * getCellData - Outputs Particular Cell Data (String) From Default Workbook's Default Work Sheet
	 * @param rowNum - Row Number 
	 * @param columnNum - Column Number
	 * @return Cell Data
	 */
	public String getCellData(int rowNum, int columnNum)
	{
		String data = worksheet.getRow(rowNum).getCell(columnNum).getStringCellValue();
		return data;
	}

	/**
	 * getCellData - Outputs Particular Cell Data (String) From Default Workbook's Specified Work Sheet
	 * @param sheetName - Name Of The Sheet From Which Data Has To Be Fetched
	 * @param rowNum - Row Number
	 * @param columnNum - Column Number
	 * @return Cell Data
	 */
	public String getCellData(String sheetName, int rowNum, int columnNum)
	{
		String data = workbook.getSheet(sheetName).getRow(rowNum).getCell(columnNum).getStringCellValue();
		return data;
	}

	/**
	 * getCellData - Outputs Particular Cell Data (String) From Specified Workbook's Work Sheet 
	 * @param workbookPath - Absolute Path Of Workbook
	 * @param sheetName - Name Of Sheet From Which Data Has To Be Fetched
	 * @param row - Row Number
	 * @param column - Column Number
	 * @return Cell Data
	 */
	public String getCellData(Workbook workbook, String sheetName, int row, int column) 
	{
		String data = null;
		try 
		{
			data = workbook.getSheet(sheetName).getRow(row).getCell(column).getStringCellValue();
		} 
		catch (EncryptedDocumentException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * createWorkbook - Creates A New XLS/XLSX WorkbookFactory Instance
	 * @return Workbook Object / null
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public static Workbook createWorkbook(String workbookType) throws IOException
	{
		try
		{
			switch (workbookType.toUpperCase())
			{
			case "XLSX":
				workbook = new WorkbookFactory().create(true);
				break;
			case "XLS":
				workbook = new WorkbookFactory().create(false);
				break;
			default:
				throw new NullPointerException("No Such Extension");
			}
			return workbook;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * updateWorkbook - Pushes The Data From File Output Stream To Specified Workbook
	 * @param workbook - Workbook Object
	 * @param worbookpath - Absolute Path Of Workbook
	 * @throws IOException
	 */
	public static void updateWorkbook(Workbook workbook, String workbookPath) throws IOException
	{
		/*Create Output Stream For Workbook Folder Path*/
		if(!updateWorkbook)
			out = new FileOutputStream(workbookPath);
		else
			out = new FileOutputStream(workbookPath,true);
		/*Write Operation Workbook Using File Out Object*/
		workbook.write(out);
		/*Close OutputStream*/
		out.close();
	}

	/**
	 * createWorkbookFolderPath - Creates A Workbook Folder In Current Directory (Project Workspace) With Respect To Date
	 * @param folderName - Folder Name Of Workbook
	 * @return - Workbook Folder Path
	 */
	public static String createWorkbookFolderPath(String folderName)
	{
		String path = "";
		/*Create Execution Folder, If It Doesn't Exist*/
		wbFILE = new File(currentDir +"\\"+folderName);
		if (!wbFILE.exists())
			wbFILE.mkdir();

		/*Create Date Specific Folder, If It Doesn't Exist*/
		path = currentDir +"\\"+folderName+"\\"+wbDate1[1]+"_"+wbDate1[2]+"_"+wbDate1[5];
		wbFILE = new File(path);
		if (!wbFILE.exists())
			wbFILE.mkdir();
		return path;
	}

	/**
	 * openWorkbook - Opens The Appropriate HSSFWorkbook / XSSFWorkbook From The Given File Path
	 * @param workbookPath - Absolute Path Of Workbook (Workbook Should Exist And Be Readable)
	 * @return Opened Workbook
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public static Workbook openWorkbook(String workbookPath) throws EncryptedDocumentException, IOException
	{
		workbook = WorkbookFactory.create(new File(workbookPath));
		/*Set Update Workbook Flag*/
		updateWorkbook = true;
		return workbook;
	}

	/**
	 * createWorkSheet - Create A New Sheet In Default Workbook
	 * @param sheetName - Sheet Name To Be Created
	 * @return Sheet Object
	 */
	public static Sheet createWorkSheet(String sheetName)
	{
		worksheet = workbook.createSheet(sheetName);
		return worksheet;
	}

	/**
	 * createWorkSheet - Create A New Sheet In Specific Workbook
	 * @param workbook - Workbook Object
	 * @param sheetName - Sheet Name To Be Created
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @return Sheet Object
	 */
	public static Sheet createWorkSheet(Workbook workbook, String sheetName) throws EncryptedDocumentException, IOException
	{
		Sheet worksheet = workbook.createSheet(sheetName);	
		return worksheet;
	}

	/**
	 * writeWorkbook - Write Data To Specific Workbook's Specific Work Sheet
	 * @param data - Data To Be Written, provide 2D Array
	 * @param sheetName - Name Of The Sheet To Which Data Has To Be Written
	 * @param folderName - Name Of The Folder Where Excel File Is Present/To Be Created
	 * @param workbookName - Name Of The Workbook To Be Created/Which Already Exist, Provide Extension Also (.xlsx Or .xls)
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public static void writeWorkbook(String [][] data, String sheetName, String folderName, String workbookName) throws EncryptedDocumentException, IOException
	{
		//***********************************************
		/*Create Folder Path Based On The Input*/
		String workbookPath=null;
		//Create Workbook Folder, If It Doesn't Exist
		String path = createWorkbookFolderPath(folderName);
		workbookPath = path+"\\"+workbookName;

		//***********************************************

		/*If Spreadsheet Exists Use The Existing One, Otherwise Create A New One*/
		wbFILE = new File(workbookPath);
		if (!wbFILE.exists())
			/*Create Workbook*/
			workbook = createWorkbook("XLSX");
		else
			/*Open Workbook*/
			workbook = openWorkbook(workbookPath);

		/*Set The customWorkbookPath*/
		if(customWorkbookPath==null)
			customWorkbookPath = workbookPath;

		//***********************************************

		/*Create Sheet Or Ignore If Already Present*/
		boolean sheetHit = false;
		for (int i = 0 ; i < workbook.getNumberOfSheets(); i++)
		{
			if (workbook.getSheetName(i).equalsIgnoreCase(sheetName))
			{
				sheetHit = true;
				break;
			}
		}
		if (!sheetHit)
			worksheet = createWorkSheet(workbook, sheetName);
		else
			/*Get Sheet*/
			worksheet = workbook.getSheet(sheetName);

		/*Write to sheet
		Create column header if not present*/
		if (worksheet.getRow(0) == null)
		{
			/*Create Row*/
			worksheet.createRow(0);
			/*Add Column Header*/
			for (int clmn = 0; clmn < data[0].length; clmn++)
			{
				setCellData(workbook, sheetName, 0, clmn, data[0][clmn]);
				/*Resize The Column Width*/
				worksheet.setColumnWidth(clmn, ((int)(29*1.2123))*256);
				/*Update Workbook*/
				updateWorkbook(workbook, workbookPath);
			}
		}

		/*Add Rows*/
		int rowCount = getRowCount(workbook, sheetName);

		/*Row Loop*/
		for (int rw = 0; rw < data.length; rw++)
		{
			rowCount = getRowCount(workbook, sheetName);
			/*Skip The Column header*/
			if (rw==0)
				continue;
			/*Create Row*/
			worksheet.createRow(rowCount);
			/*Column Loop*/
			for (int clmn= 0; clmn < data[0].length; clmn++)
			{
				setCellData(workbook, sheetName, rowCount, clmn, data[rw][clmn]);
				/*Update Workbook*/
				updateWorkbook(workbook, workbookPath);
			}	
		}

		/*Close Workbook*/
		closeWorkbook(workbook);
	}

	/**
	 * editCellData - Edit/Append Data Into An Existing Cell In Default Workbook's Specific Sheet
	 * @param sheetName - Name Of The Sheet To Which Data Has To Be Edited
	 * @param rowNum - Row Number
	 * @param columnNum - Column Number
	 * @param data - Data To Be Entered
	 */
	public static void editCellData(String sheetName, int rowNum, int columnNum, String data)
	{
		workbook.getSheet(sheetName).getRow(rowNum).getCell(columnNum).setCellValue(data);
	}

	/**
	 * editCellData - Edit/Append Data Into An Existing Cell In Default Workbook's Default Sheet
	 * @param rowNum - Row Number 
	 * @param columnNum - Column Number
	 * @param data - Data To Be Entered
	 */
	public static void editCellData(int rowNum, int columnNum, String data)
	{
		worksheet.getRow(rowNum).getCell(columnNum).setCellValue(data);
	}

	/**
	 * editCellData - Edit/Append Data Into An Existing Cell In Specified Workbook's Specific Sheet 
	 * @param workbook - Workbook Object
	 * @param sheetName - Name Of Sheet To Which Data Has To Be Edited
	 * @param rowNum - Row Number
	 * @param columnNum - Column Number
	 * @param data - Data To Be Entered
	 */
	public static void editCellData(Workbook workbook, String sheetName, int rowNum, int columnNum, String data)
	{
		workbook.getSheet(sheetName).getRow(rowNum).getCell(columnNum).setCellValue(data);
	}

	/**
	 * setCellData - Set/Enter Data Into A Particular Cell In Default Workbook's Default Sheet
	 * @param rowNum - Row Number
	 * @param columnNum - Column Number
	 * @param data - Data To Be Entered
	 */
	public static void setCellData(int rowNum, int columnNum, String data)
	{
		worksheet.getRow(rowNum).createCell(columnNum).setCellValue(data);
	}

	/**
	 * setCellData - Set/Enter Data Into A Particular Cell In Default Workbook's Specific Work Sheet
	 * @param sheetName - Name Of Sheet To Which Data Has To Be Written
	 * @param rowNum - Row Number
	 * @param columnNum - Column Number
	 * @param data - Data To Be Entered
	 */
	public static void setCellData(String sheetName, int rowNum, int columnNum, String data)
	{
		workbook.getSheet(sheetName).getRow(rowNum).createCell(columnNum).setCellValue(data);
	}

	/**
	 * setCellData - Set/Enter Data Into A Particular Cell In Specified Workbook's Specific Work Sheet
	 * @param workbook - Workbook Object
	 * @param sheetName - Name Of Sheet To Which Data Has To Be Written
	 * @param rowNum - Row Number
	 * @param columnNum - Column Number
	 * @param data - Data To Be Entered
	 */
	public static void setCellData(Workbook workbook, String sheetName, int rowNum, int columnNum, String data)
	{
		workbook.getSheet(sheetName).getRow(rowNum).createCell(columnNum).setCellValue(data);
	}

	/**
	 * clearCellData - Clear Cell Data From Default Workbook's Default Work Sheet
	 * @param rowNum - Row Number
	 * @param columnNum - Column Number
	 */
	public void clearCellData(int rowNum, int columnNum)
	{
		worksheet.getRow(rowNum).getCell(columnNum).setBlank();
	}

	/**
	 * clearCellData - Clear Cell Data From Default Workbook's Specific Work Sheet
	 * @param sheetName - Name Of Sheet To Which Particular Cell Data Has To Be Cleared/Set Blank
	 * @param rowNum - Row Number
	 * @param columnNum - Column Number
	 */
	public void clearCellData(String sheetName, int rowNum, int columnNum)
	{
		workbook.getSheet(sheetName).getRow(rowNum).getCell(columnNum).setBlank();
	}

	/**
	 * clearCellData - Clear Cell Data From Specified Workbook's Specific Work Sheet
	 * @param workbook - Workbook Object
	 * @param sheetName - Name Of Sheet To Which Particular Cell Data Has To Be Cleared/Set Blank
	 * @param rowNum - Row Number
	 * @param columnNum - Column Number
	 */
	public void clearCellData(Workbook workbook, String sheetName, int rowNum, int columnNum)
	{
		workbook.getSheet(sheetName).getRow(rowNum).getCell(columnNum).setBlank();
	}

	/**
	 * closeWorkbook - Closes Specified Workbook
	 * @param workbookObj - Workbook's Object Which Has To Be Closed
	 */
	public static void closeWorkbook(Workbook workbookObj)
	{
		try 
		{
			workbookObj.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * closeWorkbook - Closes Default Workbook
	 */
	public static void closeWorkbook()
	{
		try
		{
			workbook.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}