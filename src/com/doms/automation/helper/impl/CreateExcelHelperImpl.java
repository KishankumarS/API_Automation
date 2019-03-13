package com.doms.automation.helper.impl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.doms.automation.bean.WarningQueryVO;
import com.doms.automation.helper.CreateExcelHelper;
import com.doms.automation.utils.NikeDOMSApplicationUtils;

public class CreateExcelHelperImpl implements CreateExcelHelper{

	public void createExcel(ArrayList<WarningQueryVO> sg,ArrayList<String> headers) {
		
			
		//Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook(); 
              
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Warning Queries");
                                   
        //header row        
        XSSFRow row1 = sheet.createRow(1);
        
        //header cell style
        XSSFCellStyle HeadercellStyle = workbook.createCellStyle();
        HeadercellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
        HeadercellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        HeadercellStyle.setBorderBottom((short) 1);
        HeadercellStyle.setBorderLeft((short) 1);
        HeadercellStyle.setBorderRight((short) 1);
        HeadercellStyle.setBorderTop((short) 1);
        HeadercellStyle.getWrapText();
        
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        HeadercellStyle.setFont(font);
        
        //data cell style
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom((short) 1);
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setBorderTop((short) 1);
        
          int headerCnt=1;
        //header row values 
          XSSFCell cell = null;
        for(String s :headers ){
        	cell = row1.createCell(headerCnt);
 		    cell.setCellValue(s);
 			cell.setCellStyle(HeadercellStyle);	 			
 			headerCnt++;
        }
		
// Data cell values
int rownum = 2;
int cellnum = 1;

XSSFRow row = sheet.createRow(rownum++);		

//get each TC details - from the list
Iterator<WarningQueryVO> itr=sg.iterator();  
while(itr.hasNext()){  
	WarningQueryVO warningQueryVO=(WarningQueryVO)itr.next();  
	String ip = "";
	int ipCnt=0;
	for(String s:warningQueryVO.getInputParameters()){
		if(ipCnt==0){
		ip=s;
		}
		else{
			ip=ip+","+s;
			
	}
		ipCnt++;
	}
		
	cellnum = 1;
	 cell = row.createCell(cellnum++);
	 cell.setCellValue(warningQueryVO.getDataBase());
	 cell.setCellStyle(cellStyle);
    cellnum = 2;
	 cell = row.createCell(cellnum++);
	 cell.setCellValue(ip);
	 cell.setCellStyle(cellStyle);
	 
	 cellnum = 3; 			 
	 cell = row.createCell(cellnum++);
	 cell.setCellValue(warningQueryVO.getQuery());
	 cell.setCellStyle(cellStyle);
		
	     //get TC name
	    
	 row = sheet.createRow(rownum++); 		 
	    }
 createExcelfile(workbook);	
  

	}
	//create an excel file
		public static String createExcelfile(XSSFWorkbook workbook)
		{

			String requestfilepath;	
	        try
	        {
	        	NikeDOMSApplicationUtils nikeDOMSApplicationUtils = new NikeDOMSApplicationUtils();
	        	requestfilepath ="C:/sample_"+ nikeDOMSApplicationUtils.getUniqueNo()+".xlsx" ; 
	        	requestfilepath = System.getProperty("user.dir")+"/resources/warnings/" +"warning_queries_"+ nikeDOMSApplicationUtils.getUniqueNo()+".xlsx" ;       	
	        	//Write the workbook in file system
	            FileOutputStream out = new FileOutputStream(requestfilepath);
	            workbook.write(out);
	            out.close(); 
	            //infoBox("Warning Queries.xlsx file created. refer path :"+requestfilepath,"File location");
	            
	            
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	            requestfilepath="";
	        }
				
			return requestfilepath;
		}
		public static void infoBox(String infoMessage, String titleBar)
	    {
	        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	    }

}
