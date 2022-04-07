package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class ExcelUtility {
	
	
	@DataProvider(name = "getApiEndPointData")
	public Iterator<Object[]> getApiTestData(Method method) throws IOException {
	    return excelAPIDataReader(1, "./src/test/resources/APIDetailsSheet.xlsx",method.getName());
	}
	
	/**
	 * 
	 * @param sheetNumber
	 * @param filepath
	 * @param testCaseName
	 * @return
	 * @throws IOException
	 */
	public static final Iterator<Object[]> excelAPIDataReader(Integer sheetNumber, String filepath,String testCaseName) throws IOException {
	    DataFormatter formatter = new DataFormatter();
	    File f = new File(filepath);
	    FileInputStream file = new FileInputStream(f);
	    Workbook workbook = new XSSFWorkbook(file);
	    String sheetName = (testCaseName.split("_"))[1].replace("Test", "");
	    Sheet selectedSheet =  workbook.getSheet(sheetName);
	    ArrayList<Object[]> apiRowData = new ArrayList<Object[]>();
	    //Getting row data for test case
	    for (int i=0; i <= selectedSheet.getLastRowNum(); i++) {
	    	String[] queryParamNameArray = null;
		    String[] pathParamNameArray = null;
		    String[] pathValueArray = null;
		    String[] queryValueArray = null;
		    String []  headerNameArray = null;
		    Map<String, String> headerMap = new LinkedHashMap<String, String>();
		    Map<String, String> queryParamMap = new LinkedHashMap<String, String>();
		    Map<String, Object> pathParamMap = new LinkedHashMap<String, Object>();
		    String [] headerValueArray = null;
		    String SERVICE_ENDPOINT;
		    String baseURI, endpoint;
		    
	        if(selectedSheet.getRow(i).getRowNum() == 0)
	            continue;
	        Cell testCaseNameCell = selectedSheet.getRow(i).getCell(1);
	        Cell baseURlCell = selectedSheet.getRow(i).getCell(2);
	        Cell uriCell = selectedSheet.getRow(i).getCell(3);
	        Cell methodName = selectedSheet.getRow(i).getCell(4);
	        Cell queryParamNameCell = selectedSheet.getRow(i).getCell(5);
	        Cell queryParamValueCell = selectedSheet.getRow(i).getCell(6);
	        Cell pathParamNameCell = selectedSheet.getRow(i).getCell(7);
	        Cell pathParamValueCell = selectedSheet.getRow(i).getCell(8);
	        Cell headerNameCell = selectedSheet.getRow(i).getCell(9);
	        Cell headerValueCell = selectedSheet.getRow(i).getCell(10);
	        Cell statusCodeCell = selectedSheet.getRow(i).getCell(11);
	        Cell messageCell = selectedSheet.getRow(i).getCell(12);
	        //Checking test case name and returning that data
	        if(testCaseName.equalsIgnoreCase(testCaseNameCell.getStringCellValue())) {
	        	//Set URL with URI
		        baseURI = baseURlCell.getStringCellValue();
		        endpoint = uriCell.getStringCellValue();
		
		        if (!("NA".equalsIgnoreCase(queryParamNameCell.getStringCellValue())) && !("NA".equalsIgnoreCase(queryParamValueCell.getStringCellValue()))) {
		            queryParamNameArray = formatter.formatCellValue(queryParamNameCell).split("\\|");
		            queryValueArray = formatter.formatCellValue(queryParamValueCell).split("\\|");
		            for(int j=0; j < queryParamNameArray.length; j++) {
		            	queryParamMap.put(queryParamNameArray[j],queryValueArray[j]);
		            }
		        }
		        if (!("NA".equalsIgnoreCase(pathParamNameCell.getStringCellValue())) && !("NA".equalsIgnoreCase(pathParamValueCell.getStringCellValue())) ) {
		        	pathParamNameArray = formatter.formatCellValue(pathParamNameCell).split("\\|");
		        	pathValueArray = formatter.formatCellValue(pathParamValueCell).split("\\|");
		            for(int l=0; l < pathParamNameArray.length; l++) {
		            	pathParamMap.put(pathParamNameArray[l],pathValueArray[l]);
		            }
		        }
		        if (!("NA".equalsIgnoreCase(headerNameCell.getStringCellValue())) && !("NA".equalsIgnoreCase(headerValueCell.getStringCellValue()))) {
		            headerNameArray = formatter.formatCellValue(headerNameCell).split("\\|");
		            headerValueArray = formatter.formatCellValue(headerValueCell).split("\\|");
		            for(int k=0; k < headerNameArray.length; k++) {
		            	headerMap.put(headerNameArray[k],headerValueArray[k]);
		            }
		        }
		        
		        SERVICE_ENDPOINT = baseURI+endpoint;
		        Object ob[] = {methodName.getStringCellValue(),SERVICE_ENDPOINT, headerMap, queryParamMap, pathParamMap, (int)statusCodeCell.getNumericCellValue(),messageCell.getStringCellValue()};
	            apiRowData.add(ob);
	        }
	    }
	    workbook.close();
	    return apiRowData.iterator();
	}

}
