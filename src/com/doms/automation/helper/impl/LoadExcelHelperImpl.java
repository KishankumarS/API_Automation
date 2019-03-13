package com.doms.automation.helper.impl;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;











import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.TemplateMappingRulesVO;
import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.bean.UrlPosition;
import com.doms.automation.bean.XmlTagAttributesVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.bean.TestCasesVO;
import com.doms.automation.helper.LoadExcelHelper;
import com.doms.automation.utils.NikeDOMSConstants;
import com.doms.automation.utils.NikeDOMSLogger;

public class LoadExcelHelperImpl implements LoadExcelHelper {
	
	
	ArrayList<UrlPosition> urlPositionArray= new ArrayList<UrlPosition>();
	public  ArrayList<TestCasesVO> getLoadedTestCases() {			

			String xmlType = "";
			TestCasesVO testCasesVO = null;
			ArrayList<TemplateMappingRulesVO> templateMappingRulesVOList=null;
			TemplateMappingRulesVO templateMappingRulesVO = null;
			Boolean xmlTypeIsSet = false;		
			int cnt=2;
			String[] xmlTypeArr = null;
			ArrayList<TestCasesVO> testCasesVOList = new ArrayList<TestCasesVO>();
			FileInputStream file = null;
			try      
			{  
	    		String mappingsheetPath = System.getProperty("user.dir")+"/WebContent/resources/testCases/" +"Jmeter_Automation_Suite.xls" ;

				file = new FileInputStream(mappingsheetPath);
			 
				//Create Workbook instance holding reference to .xlsx file         
				HSSFWorkbook workbook = new HSSFWorkbook(file);      
				//Get first/desired sheet from the workbook     
				HSSFSheet sheet = workbook.getSheetAt(2);     
				//Iterate through each rows one by one       
				Iterator<Row> rowIterator = sheet.iterator();  
				
				
				while (rowIterator.hasNext())       
				{  
				
					Row row = rowIterator.next();				
				//For each row, iterate through all the columns 
					int rowNum = row.getRowNum();
				Iterator<Cell> cellIterator = row.cellIterator();  
				if(row.getRowNum()>3){
					testCasesVO = new TestCasesVO();
					templateMappingRulesVOList
					= new ArrayList<TemplateMappingRulesVO>();
				}
				
				int i=0;
				int cn=2;
				while (cellIterator.hasNext())         
				{     
					Cell cell = cellIterator.next(); 
					
					if(rowNum==3){
					if(cnt>1 && cnt<row.getLastCellNum()-1){
						if("".equalsIgnoreCase(xmlType)){
							xmlType=row.getCell(cnt).toString();
						}
						else{
							xmlType=xmlType+","+row.getCell(cnt).toString();
						}	
						 cnt = cnt + 3;
					}
					}
					
					if(row.getRowNum()>3){
						
						if(!xmlTypeIsSet){
							xmlTypeArr= xmlType.split(",");
							xmlTypeIsSet=true;
						}
						
						if(cell.getColumnIndex()==1&&(row.getCell(1)!=null)){
							testCasesVO.setTestCaseName(cell.getStringCellValue());
						}
						
						if(cell.getColumnIndex()==cn && cell.getColumnIndex()<row.getLastCellNum()-1){
							if(cn>1 && cn<row.getLastCellNum()){
								 templateMappingRulesVO 
								= new TemplateMappingRulesVO();
								 templateMappingRulesVO.setXmlType(xmlTypeArr[i]);
								 templateMappingRulesVO.setXmlName(row.getCell(cn).toString());
								 templateMappingRulesVO.setMappingSheet(row.getCell(cn+1).toString());
								 templateMappingRulesVO.setRules(row.getCell(cn+2).toString());
								 templateMappingRulesVOList.add(templateMappingRulesVO);
							}	
							 cn = cn + 3;
							 i++;
						}
						
						if(cell.getColumnIndex()==row.getLastCellNum()-1){
							String processSteps =cell.getStringCellValue();
							String[] processStepsArr = processSteps.split(",");
							testCasesVO.setProcessStepsArr(processStepsArr);
							testCasesVO.setTemplateMappingRulesVOList(templateMappingRulesVOList);
						}
						
					}
				
					
				
				         
				}              
				 if(testCasesVO!=null){
				testCasesVOList.add(testCasesVO);
				 }
				}         
				
				
				}    
			catch (Exception e)      
			{             e.printStackTrace();     
			}
			finally{
				if(file!=null){
					try {
						file.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		
			return testCasesVOList; 
		
	}
	
	public ArrayList<TestCaseDataVO> getLoadedTestCaseData(ArrayList<TestCaseDataVO> testCaseDataVOList) {
		String xmlAttributeName = "";
		//String  firstLine="";
		testCaseDataVOList=new ArrayList<TestCaseDataVO>();
		ArrayList<XmlTagLinesVO> xmlTagLinesVOList=null;
		XmlTagLinesVO xmlTagLinesVO= null;
		ArrayList<XmlTagAttributesVO> xmlTagAttributesVOList=null;
		
		TestCaseDataVO testCaseDataVO = null;    	
		XmlTagAttributesVO xmlTagAttributesVO = null;
		
		Boolean xmlAttributeNameIsSet = false;	
		String[] xmlAttributeNameArr = null;
		FileInputStream fis=null;
		try 
        {
    		String mappingsheetPath = System.getProperty("user.dir")+"/resources/testCases/" +"Automation_Test_Suite.xlsx" ;
			fis = new FileInputStream(mappingsheetPath);
			/*XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet dataSheet = workbook.getSheetAt(0);*/	
			
			Workbook workbook = WorkbookFactory.create(fis);

			// Fetch template xmls from testcases sheet
			Sheet dataSheet = workbook.getSheetAt(0);
			boolean isFirstLine= false;
			/*
			 * Iterating each row by row
			 */
			for(int j=1;j<dataSheet.getPhysicalNumberOfRows();j++){
				Row tstRow = dataSheet.getRow(j);
				String  testCase=tstRow.getCell(0).getStringCellValue();	
				/*
				 * checking whether the first line if there are number of order lines
				 * or just only one line
				 * Make the flag isFirstLine true to state that its is the first line.
				 */
				if(j>1) {
					testCase=tstRow.getCell(0).getStringCellValue();	
					if(!testCase.equals("")){
					isFirstLine=true;
					}
				}			
			
				/*
				 * If the row is greater than one and it is first line then the object of
				 * TestCaseDataVO is created.
				 * xmlTagLinesVO class is created which holds number of xmlTagAttribute list 
				 * according to the number of lines.
				 */
				if(j>1){
					//createTestCaseDataVOObject(testCaseDataVO,xmlTagLinesVOList,xmlTagAttributesVOList);
					if(isFirstLine){
					testCaseDataVO = new TestCaseDataVO();					
					xmlTagLinesVOList =
						new ArrayList<XmlTagLinesVO>();	
					xmlTagAttributesVOList
					/*
					 * setting the test case name.
					 */
					= new ArrayList<XmlTagAttributesVO>();	
					}else{
						xmlTagAttributesVOList
						/*
						 * setting the test case name.
						 */
						= new ArrayList<XmlTagAttributesVO>();	
					}
					
				}
				/*
				 * for assigning the tag name to the xmlTagAttributesVOList the variable is set to 0	
				 */
				int k=0;
				int lastCellNum = tstRow.getLastCellNum();
				/*
				 * Iterating column one by one till last column
				 * 
				 */
				
				for(int i=1;i<tstRow.getLastCellNum();i++){
				 tstRow.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
					
				/*
				 * Taking the first row to get the headers as attribute names excluding
				 *  last column as it is process steps heading.
				 */
				if(j==1&&i<lastCellNum-1){
					
						if("".equalsIgnoreCase(xmlAttributeName)){
							xmlAttributeName=tstRow.getCell(i).toString();
						}
						else{
							
							xmlAttributeName=xmlAttributeName+","+tstRow.getCell(i).toString();
						}	
						
					}
			/*
			 * else if row is greater than 1 the xmlAttributeName Array is set just once.
			 */
				else if(i<lastCellNum-1){
					if(!xmlAttributeNameIsSet){
						xmlAttributeNameArr= xmlAttributeName.split(",");
						xmlAttributeNameIsSet=true;
					}
					xmlTagAttributesVO = setXmlTagAttributesVO(xmlAttributeNameArr[k]
					                        ,tstRow.getCell(i).toString(),xmlTagAttributesVO);
	
					k++;
				
				/*
				 * if the xmlTagAttributesVO is not null it is added to the xmlTagAttributesVOlist
				 */
				if(xmlTagAttributesVO!=null){
					xmlTagAttributesVOList.add(xmlTagAttributesVO);
					}
					}
				/*
				 * Excluding the header row  and process steps column if i is last attribute value
				 * column 
				 * 
				 * If it is the end of test case
				 * 	1. If Only one order line is there then all the variables of TestCaseData is set.
				 *  2. else the excluding testcasename and process steps are set as this will be set earlier.
				 * The testcaseDataVO is added to the testCaseDataVOList for end of test case.
				 */
				
				if((j!=1)&&(i==lastCellNum-1)){					
					if(isEndOfTestCase(dataSheet,j)){
						
						testCaseDataVO=setTestCaseData(xmlTagLinesVO,xmlTagAttributesVOList,xmlTagLinesVOList
								,tstRow.getCell(i).toString(),testCase,testCaseDataVO,isFirstLine);
						
						if(testCaseDataVO!=null){	
							testCaseDataVOList.add(testCaseDataVO)	;
							}
						
					}else{
						
						testCaseDataVO=setTestCaseData(xmlTagLinesVO,xmlTagAttributesVOList,xmlTagLinesVOList
								,tstRow.getCell(i).toString(),testCase,testCaseDataVO,isFirstLine);
								
							}
					}	
						
				}
					
					
					
					if(isFirstLine){
						isFirstLine=false;	
					}
			}
				
			
			
				
        }
        catch (Throwable e) 
        {
            e.printStackTrace();
        }
		finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return testCaseDataVOList; 
	}
	private XmlTagAttributesVO setXmlTagAttributesVO(String attributeName, String attributeValue,XmlTagAttributesVO xmlTagAttributesVO) {
		/*
		 * creating an object of xmlTagAttributesVO class
		 */
		xmlTagAttributesVO = new XmlTagAttributesVO();
	/*
	 * The attribute name and value is set to xmlTagAttributesVO.
	 * If it first line then the flag isFirstLine is reset.
	 */
	
		xmlTagAttributesVO.setAttributeName(attributeName);
		//System.out.println(s);					
		xmlTagAttributesVO.setAttributeValue(attributeValue);
		return xmlTagAttributesVO;
		
	}

	private boolean isEndOfTestCase(Sheet dataSheet, int j) {
		boolean isEndOfTestCase=false;					
		if(j<dataSheet.getPhysicalNumberOfRows()-1){
			Row nextRow = dataSheet.getRow(j+1);
			isEndOfTestCase = !nextRow.getCell(0).getStringCellValue()
			.equalsIgnoreCase("")? true:false;						
		}
		else if(j==dataSheet.getPhysicalNumberOfRows()-1){
			isEndOfTestCase=true;
		}
		return isEndOfTestCase;
	}

	private TestCaseDataVO setTestCaseData(XmlTagLinesVO xmlTagLinesVO,ArrayList<XmlTagAttributesVO> xmlTagAttributesVOList
			, ArrayList<XmlTagLinesVO> xmlTagLinesVOList,String processSteps,String firstLine,TestCaseDataVO testCaseDataVO, boolean isFirstLine) {
		if(isFirstLine){
			testCaseDataVO.setProcessStepsArr(
					getProcessSteps(processSteps));
			testCaseDataVO.setTestCaseName(firstLine);	
			}
		xmlTagLinesVO
		= new XmlTagLinesVO();							
		xmlTagLinesVO.setXmlTagAttributesVO(xmlTagAttributesVOList);
		xmlTagLinesVOList.add(xmlTagLinesVO);
		testCaseDataVO.setXmlTagLinesVOList(xmlTagLinesVOList);
		return testCaseDataVO;
		
	}

	private void createTestCaseDataVOObject(TestCaseDataVO testCaseDataVO,ArrayList<XmlTagLinesVO> xmlTagLinesVOList
			,ArrayList<XmlTagAttributesVO> xmlTagAttributesVOList) {
		
		testCaseDataVO = new TestCaseDataVO();					
		xmlTagLinesVOList =
			new ArrayList<XmlTagLinesVO>();
		
		xmlTagAttributesVOList
		/*
		 * setting the test case name.
		 */
		= new ArrayList<XmlTagAttributesVO>();					
					
		
			
		}
	/*
	 * Method to get the array of process steps.
	 */
		private String[] getProcessSteps(String processSteps) {		
			String[] processStepsArr = processSteps.split(",");
			return processStepsArr;
			
			
		}
		/*
		 * setting the TestCaseDataVO
		 */
		private void setTestCaseDataVO(){
			
		}

		@Override
		public EnvConfigVO getEnvConfigVO(String env) {

			EnvConfigVO envConfigVO = new EnvConfigVO();

			String envConfigPath = System.getProperty("user.dir")+"/resources/jmeter/Env_Config.csv";

			BufferedReader br = null;

			String line = "";

			String cvsSplitBy = ","; 
			try {

			br = new BufferedReader(new FileReader(envConfigPath));

			while ((line = br.readLine()) != null) {



			// use comma as separator

			String[] envDetails = line.split(cvsSplitBy);

			
			envConfigVO = setUrl(envConfigVO,envDetails,env);

			if(envDetails[0].equalsIgnoreCase(env)){

			envConfigVO.setServletName( envDetails[1]);

			envConfigVO.setHost(envDetails[2]);
			
			envConfigVO.setDomsPort(envDetails[3]);

			envConfigVO.setProgId(envDetails[4]);

			envConfigVO.setUserID(envDetails[5]);

			envConfigVO.setPassword(envDetails[6]); 
			
			envConfigVO.setHostPac(envDetails[7]); 
			
			envConfigVO.setPortPac(envDetails[8]); 
			
			envConfigVO.setPathPac(envDetails[9]); 
			
			envConfigVO.setProtocol(envDetails[10]);

			break;

		}

		}

			} catch (FileNotFoundException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

			} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

			finally{
				if(br!=null){

			try {

			br.close();

			} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
				}

		}

			return envConfigVO;

		}

		private EnvConfigVO setUrl(EnvConfigVO envConfigVO, String[] envDetails, String env) {
			
			
			if("ENV".equalsIgnoreCase(envDetails[0])){
		
			for(int i = 0 ; i < envDetails.length;i++){

				UrlPosition urlPosition=null; 
				
				if(envDetails[i].equalsIgnoreCase("Order_Modify_CSR_URL")){
					urlPosition = new UrlPosition();
					urlPosition.setPosition(i);
					urlPosition.setUrl("Order_Modify_CSR_URL");
					
				

			}
				

				if(envDetails[i].equalsIgnoreCase("Return_Inspection_CSR_URL")){
					urlPosition = new UrlPosition();
					urlPosition.setPosition(i);
					urlPosition.setUrl("Return_Inspection_CSR_URL");
					

				
			}

				if(envDetails[i].equalsIgnoreCase("CSRcancelURL")){
					urlPosition = new UrlPosition();
					urlPosition.setPosition(i);
					urlPosition.setUrl("CSRcancelURL");

				

			}

				if(envDetails[i].equalsIgnoreCase("Return_CSR_URL")){
					urlPosition = new UrlPosition();
					urlPosition.setPosition(i);
					urlPosition.setUrl("Return_CSR_URL");

				

			}

				if(envDetails[i].equalsIgnoreCase("WarehouseAck_PAC_URL")){
					urlPosition = new UrlPosition();
					urlPosition.setPosition(i);
					urlPosition.setUrl("WarehouseAck_PAC_URL");

				

			}

				if(envDetails[i].equalsIgnoreCase("BRDShipmentURL")){
					urlPosition = new UrlPosition();
					urlPosition.setPosition(i);
					urlPosition.setUrl("BRDShipmentURL");

				

			}

				if(envDetails[i].equalsIgnoreCase("CICOrderCreateURL")){
					urlPosition = new UrlPosition();
					urlPosition.setPosition(i);
					urlPosition.setUrl("CICOrderCreateURL");

				

			}
				if(envDetails[i].equalsIgnoreCase("SelfServe")){
					urlPosition = new UrlPosition();
					urlPosition.setPosition(i);
					urlPosition.setUrl("SelfServe");

				

			}
				if(envDetails[i].equalsIgnoreCase("SelfServeReturn")){
					urlPosition = new UrlPosition();
					urlPosition.setPosition(i);
					urlPosition.setUrl("SelfServeReturn");

				

			}
				
				if(urlPosition!=null){
				urlPositionArray.add(urlPosition);
				}

			}}
			
			if(envDetails[0].equalsIgnoreCase(env)){
				for(UrlPosition urlPosition:urlPositionArray){
					if(urlPosition.getUrl().equalsIgnoreCase("Order_Modify_CSR_URL")){
						envConfigVO.setApplyDiscounturl(envDetails[urlPosition.getPosition()]);
					
					

				}

					if(urlPosition.getUrl().equalsIgnoreCase("Return_Inspection_CSR_URL")){
						envConfigVO.setInspectAndReturnOrderurl(envDetails[urlPosition.getPosition()]);
						envConfigVO.setInspectAndFapaioReversalurl(envDetails[urlPosition.getPosition()]);
						

					
				}

					if(urlPosition.getUrl().equalsIgnoreCase("CSRcancelURL")){
						envConfigVO.setOrderLineCancellationurl(envDetails[urlPosition.getPosition()]);

					

				}

					if(urlPosition.getUrl().equalsIgnoreCase("Return_CSR_URL")){
						envConfigVO.setReturnItemsurl(envDetails[urlPosition.getPosition()]);
					

				}

					if(urlPosition.getUrl().equalsIgnoreCase("WarehouseAck_PAC_URL")){
						envConfigVO.setWareHouseAcknowledgeurl(envDetails[urlPosition.getPosition()]);
						envConfigVO.setShipSAPOrderurl(envDetails[urlPosition.getPosition()]);

					

				}

					if(urlPosition.getUrl().equalsIgnoreCase("BRDShipmentURL")){
						envConfigVO.setShipInlineOrderurl(envDetails[urlPosition.getPosition()]);

					

				}
					if(urlPosition.getUrl().equalsIgnoreCase("CICOrderCreateURL")){
						envConfigVO.setcICOrderCreateURL(envDetails[urlPosition.getPosition()]);

					

				}
					if(urlPosition.getUrl().equalsIgnoreCase("SelfServe")){
						envConfigVO.setSelfServiceURL(envDetails[urlPosition.getPosition()]);
					
					

				}
					if(urlPosition.getUrl().equalsIgnoreCase("SelfServeReturn")){
						envConfigVO.setSelfServiceReturnURL(envDetails[urlPosition.getPosition()]);
					
					

				}
					
				}
				}
		
			return envConfigVO;
		}
		/*
		 * Loading a Map with key as the dbtype that is doms, comms or pac and the dbconfig 
		 * object with corresponding configuration details from the excel file for the selected
		 * environment.
		 * @see com.doms.automation.helper.LoadExcelHelper#getDbEnvConfig(java.lang.String)
		 */
		@Override
		public Map<String,DbEnvConfig> getDbEnvConfig(String environment) {
			Map<String,DbEnvConfig> dbEnvConfigMap = new LinkedHashMap<String,DbEnvConfig>();
			 String envConfigPath = System.getProperty("user.dir")+"/resources/db/EnvConfig.xlsx";
				FileInputStream fi=null;
				try {
					fi = new FileInputStream(envConfigPath);
				
				XSSFWorkbook workbk = new XSSFWorkbook(fi);
				XSSFSheet sheet = workbk.getSheetAt(0);
				Row row=null;
				Cell cell=null;
				
				for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
					row=sheet.getRow(i);
					cell=row.getCell(0);
					if(cell.getStringCellValue().equalsIgnoreCase(environment)){
						DbEnvConfig domsDbEnvConfig = new DbEnvConfig();
						domsDbEnvConfig.setHost(row.getCell(1).getStringCellValue());
						domsDbEnvConfig.setUserID(row.getCell(3).getStringCellValue());
						domsDbEnvConfig.setPassword(row.getCell(4).getStringCellValue());
						domsDbEnvConfig.setInstance(row.getCell(5).getStringCellValue());	
						dbEnvConfigMap.put(NikeDOMSConstants.DOMS,domsDbEnvConfig);
						DbEnvConfig pacDbEnvConfig = new DbEnvConfig();
						pacDbEnvConfig.setHost(row.getCell(1).getStringCellValue());
						pacDbEnvConfig.setUserID(row.getCell(6).getStringCellValue());
						pacDbEnvConfig.setPassword(row.getCell(7).getStringCellValue());
						pacDbEnvConfig.setInstance(row.getCell(8).getStringCellValue());	
						dbEnvConfigMap.put(NikeDOMSConstants.PAC,pacDbEnvConfig);
						DbEnvConfig commsDbEnvConfig = new DbEnvConfig();
						commsDbEnvConfig.setHost(row.getCell(1).getStringCellValue());
						commsDbEnvConfig.setUserID(row.getCell(9).getStringCellValue());
						commsDbEnvConfig.setPassword(row.getCell(10).getStringCellValue());
						commsDbEnvConfig.setInstance(row.getCell(11).getStringCellValue());	
						dbEnvConfigMap.put(NikeDOMSConstants.COMMS,commsDbEnvConfig);
						DbEnvConfig paymentDbEnvConfig = new DbEnvConfig();
						paymentDbEnvConfig.setHost(row.getCell(1).getStringCellValue());
						paymentDbEnvConfig.setUserID(row.getCell(12).getStringCellValue());
						paymentDbEnvConfig.setPassword(row.getCell(13).getStringCellValue());
						paymentDbEnvConfig.setInstance(row.getCell(14).getStringCellValue());	
						dbEnvConfigMap.put(NikeDOMSConstants.PAYMENT,paymentDbEnvConfig);
						break;
					}
					
				}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					if(fi!=null){
						try {
							fi.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			return dbEnvConfigMap;
		}
}
