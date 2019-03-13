package com.doms.automation.dao;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.doms.automation.utils.NikeDOMSApplicationUtils;
import com.doms.automation.utils.NikeDOMSConstants;
import com.doms.automation.utils.NikeDOMSLogger;

public class DataValueBean {
	 private final String CLASS_NAME = "com.doms.automation.dao.DataValueBean";
	 NikeDOMSApplicationUtils nikeDOMSApplicationUtils = new NikeDOMSApplicationUtils();
	public String getMappingType(String ruleValue) {

		String attributeType = "INVALID";
		final String methodName = "getMappingType";
		
		try {
			if (null != ruleValue && ruleValue.length() >= 2) {				
				ruleValue = ruleValue.trim();
				
					String[] tempSplitArr = ruleValue.split(" ", -1);
					if (null != tempSplitArr) {
						if (tempSplitArr[0].startsWith("DF")) {
							attributeType = "DEFAULTVALUE";
						} else if (tempSplitArr[0].startsWith("UN")) {
							attributeType = "UNIQUENUMBER";
						}else if (tempSplitArr[0].startsWith("ST")) {
							attributeType = "TIMESTAMP";
						}else if (tempSplitArr[0].startsWith("DS")) {
							attributeType = "DATASHEET";
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME", CLASS_NAME,
					NikeDOMSLogger.LEVEL_FATAL, methodName, e.getMessage());
		}
		return attributeType;
	}
	
	
	public String getValues(String valueMS,int rowNo,String dataRules) {
		final String methodName = "populateMappingData";
		try {
			
			
		
			String attributeType = getMappingType(valueMS);
						if (attributeType
								.equalsIgnoreCase(NikeDOMSConstants.ATTR_DEFAULT)) {
							String[] defaultValue = valueMS.split(" ");
							return defaultValue[1];

						} else if (attributeType
								.equalsIgnoreCase(NikeDOMSConstants.ATTR_TIME)) {
							return nikeDOMSApplicationUtils.getTimeStamp();

						}else if (attributeType
								.equalsIgnoreCase(NikeDOMSConstants.DATA_SHEET)) {
							String[] fieldName = valueMS.split(" ");
							return getFieldValue(fieldName[1],rowNo,dataRules);
							
						} else
						{
							return nikeDOMSApplicationUtils.getUniqueNo();
						}
						
		} 
		catch (Exception e) {
			e.printStackTrace();
			return "exception";
		} 
		
		
		
		
	}
	
	public String getFieldValue(String fieldName,int rowNo,String dataRules){

		String fieldOutput = "";
		
		try{
		

		String lineType = "";
		String paymentType = "";
		String ToBeReturned = "";
		String toBeGivenDiscount = "";
		
		if(dataRules.equalsIgnoreCase("LineType=INLINE")){
			lineType = fetchFieldValue("LineType", rowNo);
			if(lineType.equalsIgnoreCase("INLINE")){
				fieldOutput = fetchFieldValue(fieldName, rowNo);
			}
		}else if(dataRules.equalsIgnoreCase("LineType=NIKEiD")){
			lineType = fetchFieldValue("LineType", rowNo);
			if(lineType.equalsIgnoreCase("NIKEID")){
				fieldOutput = fetchFieldValue(fieldName, rowNo);
			}
		}else if(dataRules.equalsIgnoreCase("PaymentType=CREDIT_CARD")){
			paymentType = fetchFieldValue("PaymentType", rowNo);
			if(paymentType.equalsIgnoreCase("CREDIT_CARD")){
				fieldOutput = fetchFieldValue(fieldName, rowNo);
			}
		}else if(dataRules.equalsIgnoreCase("LineType=INLINE AND To Be Returned?=Yes")){
			lineType = fetchFieldValue("LineType", rowNo);
			ToBeReturned = fetchFieldValue("ToBeReturned?", rowNo);
			if(lineType.equalsIgnoreCase("INLINE") && ToBeReturned.equalsIgnoreCase("Yes")){
				fieldOutput = fetchFieldValue(fieldName, rowNo);
			}
			
		}else if(dataRules.equalsIgnoreCase("LineType=NIKEID AND To Be Returned?=Yes")){
			lineType = fetchFieldValue("LineType", rowNo);
			ToBeReturned = fetchFieldValue("ToBeReturned?", rowNo);
			if(lineType.equalsIgnoreCase("NIKEID") && ToBeReturned.equalsIgnoreCase("Yes")){
				if(fieldName.equalsIgnoreCase("Ship_Node"))
				{
					rowNo = rowNo - 1;
				}
				fieldOutput = fetchFieldValue(fieldName, rowNo);
			}
		}else if(dataRules.equalsIgnoreCase("LineType=GC")){
			lineType = fetchFieldValue("LineType", rowNo);
			if(lineType.equalsIgnoreCase("GC")){
				fieldOutput = fetchFieldValue(fieldName, rowNo);
			}
		}else if(dataRules.equalsIgnoreCase("LineType=GC AND To Be Returned?=Yes")){
			lineType = fetchFieldValue("LineType", rowNo);
			ToBeReturned = fetchFieldValue("To Be Returned?", rowNo);
			if(lineType.equalsIgnoreCase("GC") && ToBeReturned.equalsIgnoreCase("Yes")){
				fieldOutput = fetchFieldValue(fieldName, rowNo);
			}
		}else if(dataRules.equalsIgnoreCase("To be given discount? = Yes")){
			toBeGivenDiscount = fetchFieldValue("To be given discount?", rowNo);
			if(toBeGivenDiscount.equalsIgnoreCase("Yes")){
				fieldOutput = fetchFieldValue(fieldName, rowNo);
			}
		}else{
			fieldOutput = fetchFieldValue(fieldName, rowNo);
		}
		
		

		
		}catch(Exception e){
			e.printStackTrace();
		}
		return fieldOutput;

	}
	
	public String fetchFieldValue(String fieldName,int rowNo){
		String fieldValue = "";

		
		int testCaseRowNo = 0;
		int fieldColNum = 0;
		String cellValue = "";
		try{
			String mappingsheetPath = System.getProperty("user.dir")+"/resources/testCases/" +"Jmeter_Automation_Suite.xls" ;
			FileInputStream fis = new FileInputStream(mappingsheetPath);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet dataSheet = workbook.getSheetAt(3);
	        for(Row row : dataSheet) {

            for(Cell cell : row) {

            	switch(cell.getCellType()) {
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    cellValue = cell.getBooleanCellValue() +"";
                    
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    cellValue = cell.getNumericCellValue() + "";
                    
                    break;
                case HSSFCell.CELL_TYPE_STRING:               	
                    cellValue = cell.getStringCellValue();

                    	testCaseRowNo = cell.getRowIndex();
                    	if(rowNo==testCaseRowNo){
                    	if(fieldColNum != 0){
                    		Cell fieldValueCell = dataSheet.getRow(testCaseRowNo).getCell(fieldColNum);
                    		if(fieldValueCell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
                    			fieldValue = String.valueOf(fieldValueCell.getBooleanCellValue());
                    		}else if(fieldValueCell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
                    			fieldValue = String.valueOf((int)fieldValueCell.getNumericCellValue());
                    		}else if(fieldValueCell.getCellType()==HSSFCell.CELL_TYPE_STRING){
                    			fieldValue = fieldValueCell.getStringCellValue();
                    		}else if(fieldValueCell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
                    			fieldValue = fieldValueCell.getStringCellValue();
                    		}else if(fieldValueCell.getCellType()==HSSFCell.CELL_TYPE_BLANK){
                    			fieldValue = "";
                    		}
                    		
                    		if(!fieldValue.equalsIgnoreCase("")){
                    			return fieldValue;                    			
                    		}
                    	}}
                    
                    if(cellValue.equalsIgnoreCase(fieldName)){
                    	fieldColNum = cell.getColumnIndex();
                    	
                    }
                    
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    break;
                }
           
            }
        } }catch(Exception e){
			e.printStackTrace();
		}
        return fieldValue;
	}
}
