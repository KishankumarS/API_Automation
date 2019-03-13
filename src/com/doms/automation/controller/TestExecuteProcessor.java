package com.doms.automation.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.ResultVO;
import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.bean.TestCaseResultVO;
import com.doms.automation.bean.WarningQueryVO;
import com.doms.automation.bean.XmlTagAttributesVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.helper.CreateExcelHelper;
import com.doms.automation.helper.LoadExcelHelper;
import com.doms.automation.helper.impl.CreateExcelHelperImpl;
import com.doms.automation.helper.impl.LoadExcelHelperImpl;
import com.doms.automation.utils.NikeDOMSApplicationUtils;
import com.doms.automation.utils.NikeDOMSLogger;


public class TestExecuteProcessor {
	NikeDOMSApplicationUtils nikeDOMSApplicationUtils ;
	private long startTime;
	private String environment;	
	Integer totalNumberOfTestCases;
	Integer passCount=0, failCount=0;
	LoadExcelHelper loadExcelHelper;
	CreateExcelHelper createExcelHelper;
	ArrayList<TestCaseDataVO> testCaseDataVO;
	TestCaseDataVO testCaseDataVOObj;	
	EnvConfigVO envConfigVO;
	Map<String,DbEnvConfig> dbConfigMap;
	ArrayList<WarningQueryVO> warningQueryVOList;
	Integer warningQueryCnt=0;




	public ArrayList<WarningQueryVO> getWarningQueryVOList() {		
		return warningQueryVOList;
	}
	public void setWarningQueryVOList(ArrayList<WarningQueryVO> warningQueryVOList) {
		this.warningQueryVOList = warningQueryVOList;
	}
	public EnvConfigVO getEnvConfigVO() {
		return envConfigVO;
	}
	public void setEnvConfigVO(EnvConfigVO envConfigVO) {
		this.envConfigVO = envConfigVO;
	}
	public TestCaseDataVO getTestCaseDataVOObj() {
		return testCaseDataVOObj;
	}
	public void setTestCaseDataVOObj(TestCaseDataVO testCaseDataVOObj) {
		this.testCaseDataVOObj = new TestCaseDataVO();
		this.testCaseDataVOObj = testCaseDataVOObj;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public TestExecuteProcessor(String environment){
		loadExcelHelper = new LoadExcelHelperImpl();
		createExcelHelper = new CreateExcelHelperImpl();
		nikeDOMSApplicationUtils = new NikeDOMSApplicationUtils();
		testCaseDataVO = new ArrayList<TestCaseDataVO>();
		envConfigVO = new EnvConfigVO();
		dbConfigMap = new LinkedHashMap<String, DbEnvConfig>();

	}

	public Map<String, DbEnvConfig> getDbConfigMap() {
		return dbConfigMap;
	}
	public void setDbConfigMap(Map<String, DbEnvConfig> dbConfigMap) {
		this.dbConfigMap = dbConfigMap;
	}
	@SuppressWarnings("unchecked")
	public String runTestCase(String[] args,String env){
		//loading the excel sheet
		testCaseDataVO = loadExcelHelper.getLoadedTestCaseData(testCaseDataVO);
		envConfigVO = 	loadExcelHelper.getEnvConfigVO(env);
		dbConfigMap = loadExcelHelper.getDbEnvConfig(env);
		Map testResultsMap = new HashMap();
		Map resultsMap = new LinkedHashMap();
		String report = "";
		setEnvironment(env);

		TestExecuteController testExecuteController;

		List<ResultVO> resultArraylist = new ArrayList<ResultVO>();


		try {

			NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME",
					"TestExecuteProcessor", NikeDOMSLogger.LEVEL_DEBUG, "main",
					"Fetch testcases mapping");

			totalNumberOfTestCases =args.length ;
			//Multi-thread implementations
			ExecutorService taskExecutor = Executors.newFixedThreadPool(2);
			CompletionService<TestCaseResultVO> taskCompletionService =
					new ExecutorCompletionService<TestCaseResultVO>(taskExecutor);
			//The get the start time to know the time differance while using thread(Testing purpose)
			startTime = System.currentTimeMillis();
			for (int i = 0; i < args.length; i++) {
				ResultVO resultVO = new ResultVO();
				String selectedTestCase = args[i];			
				for(TestCaseDataVO oTestCaseDataVO:testCaseDataVO){
					testCaseDataVOObj=new TestCaseDataVO(); 
					if(selectedTestCase.equalsIgnoreCase(oTestCaseDataVO.getTestCaseName())){
						setTestCaseDataVOObj(oTestCaseDataVO);
						break;
					}		
				}	
				String orderNo = getOrderNo(getTestCaseDataVOObj());
				if("".equals(orderNo)){
					orderNo =  getOrderNumber(getTestCaseDataVOObj());
	
				}
				/* Generate new orderNo
				String orderNo =  getOrderNumber(getTestCaseDataVOObj());	

				 Take order No from sheet
				String orderNo = getOrderNo(getTestCaseDataVOObj());*/

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					// handle the exception...        
					// For example consider calling Thread.currentThread().interrupt(); here.
				}
				resultVO.setSelectedTestCase(selectedTestCase);
				resultVO.setOrderNo(orderNo);
				resultArraylist.add(resultVO);
				for(TestCaseDataVO oTestCaseDataVO:testCaseDataVO){
					testCaseDataVOObj=new TestCaseDataVO(); 
					if(selectedTestCase.equalsIgnoreCase(oTestCaseDataVO.getTestCaseName())){
						setTestCaseDataVOObj(oTestCaseDataVO);
						break;
					}		
				}			

				try {
					taskCompletionService.submit(new TestExecuteController(env, 
							orderNo, getTestCaseDataVOObj(),envConfigVO,dbConfigMap));
				} catch (Exception e) {

					e.printStackTrace();
				}


			}				
			if(resultArraylist.size()>0){
				for(ResultVO resultVO:resultArraylist){				
					// System.out.println("trying to take from Completion service");
					Future<TestCaseResultVO> result = taskCompletionService.take();
					// System.out.println("result for a task available in queue.Trying to get()"  );
					while(result.get()==null){
					}
					TestCaseResultVO testCaseResultVO = result.get();
					testResultsMap = testCaseResultVO.getResult();

					if(isSuccess(testResultsMap)){
						passCount++; 
					}
					else{
						failCount++; 
					}	
					// ResultsMap.put(resultVO.getSelectedTestCase(), testResultsMap);
					//ResultsMap.put("orderNo_"+resultVO.getSelectedTestCase(), resultVO.getOrderNo());
					// System.out.println("Task " + args[i] + "Completed - results obtained : " + String.valueOf(l.result));		
				}
			}


			long endTime = System.currentTimeMillis();
			long differenceTime = endTime - startTime;
			String s=String.format("%d min, %d sec", 
					TimeUnit.MILLISECONDS.toMinutes(differenceTime),
					TimeUnit.MILLISECONDS.toSeconds(differenceTime) - 
					TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(differenceTime))
					);

			System.out.println("Total time***********************************************************"+s);

			report = this.generateReport(getResultMap(TestExecuteController.ResultsMap, resultArraylist));
		}catch (IllegalStateException e) {

			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return report;

	}

	/*private Map<String, Map<String, Object>> getLoadedTestCases() {
		Map<String, Map<String, Object>> testData = new HashMap<String, Map<String, Object>>();

		Map<String, Object> testCaseMap = new HashMap<String, Object>();
		List<TemplateMappingVO> templatesList = null;
		String fieldValue = "";
		String itemID = "";
		DataValueBean dataValueBean = new DataValueBean();
		Map testDataMap = new LinkedHashMap();

		try {

			String testSuiteXlsPath = System.getProperty("user.dir")+"/resources/testCases/" +"Jmeter_Automation_Suite.xls" ;

			FileInputStream fis = new FileInputStream(testSuiteXlsPath);
			Workbook workbook = WorkbookFactory.create(fis);

			// Fetch template xmls from testcases sheet
			Sheet sheet = workbook.getSheetAt(2);
			int lastRow = sheet.getLastRowNum();
			for (int rn = 4; rn <= lastRow; rn++) {
				HSSFRow row = (HSSFRow) sheet.getRow(rn);
				templatesList = new ArrayList<TemplateMappingVO>();
				if (row.getCell(1) != null) {
					String testCaseName = row.getCell(1).toString();
					TemplateMappingVO mappingVO = null;

					for (int cn = 2; cn < row.getLastCellNum(); cn = cn + 3) {
						if(cn!=row.getLastCellNum()-1){
						mappingVO = new TemplateMappingVO();
						mappingVO.setTemplateName(row.getCell(cn).toString());
						mappingVO.setMappingSheet(row.getCell(cn+1).toString());
						mappingVO.setDataRules(row.getCell(cn+2).toString());
						templatesList.add(mappingVO);
						}
					}
					String processSteps = row.getCell((int)row.getLastCellNum()-1).toString();

					String[] processStepsArr = processSteps.split(",");

					testCaseMap.put("process_"+testCaseName, processStepsArr);

					testCaseMap.put(testCaseName, templatesList);

				}
			}



		} catch (Exception e) {
			e.printStackTrace();
			NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME",
					"getLoadedTestCases", NikeDOMSLogger.LEVEL_ERROR, "main",
					"Exception in Application:" + e.getMessage());
		}

		testData.put("templates", testCaseMap);
		testData.put("testData", testDataMap);
		return testData;
	}*/

	private String generateReport(Map testResultsMap){

		StringBuilder sb = new StringBuilder();
		String failReqPath = "";
		String failResPath = "";		
		String result = "";	

		sb.append("<html>");
		sb.append("<head>");	    
		sb.append("<title>Test Report");
		sb.append("</title>");
		sb.append("</head>");
		sb.append("<body>");	    
		sb.append("	<h2 align='center'>Test Report</h2>");
		sb.append("<h2 align='center'>High Level Summary</h2><table table-layout='fixed' width='50%' align='center' border='1' cellpadding='1' cellspacing='1'>" +
				"<tbody><tr><td>Environment</td><td>"+getEnvironment()+"</td></tr>" +
				"<tr><td>Total Number Of testCases</td><td>"+totalNumberOfTestCases+"</td></tr>" +
				"<tr><td>Passed</td><td>"+passCount+"</td></tr>" +
				"<tr><td>Failed</td><td>"+failCount+"</td></tr>" +
				"<tr><td>Pass Percentage</td><td>"+calculatePassPercentage()+"%</td></tr>" +
				"</tbody></table>");
		sb.append("<h2 align='center'>Summary</h2><table table-layout='fixed' width='70%' align='center' border='1' cellpadding='1' cellspacing='1'><thead><tr><th scope='col'>Test case Name</th><th scope='col'>Result</th><th scope='col'>Order No</th></tr></thead><tbody>");


		Iterator entries = testResultsMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();


			if(!thisEntry.getKey().toString().contains("orderNo_")){
				String testCaseName = thisEntry.getKey().toString();
				Map testResults = (Map)thisEntry.getValue();
				Iterator results = testResults.entrySet().iterator();
				String response = "";
				boolean isFail=false;
				while (results.hasNext()) {
					Entry testSteps = (Entry) results.next();

					ReqResVO reqResVO = (ReqResVO)testSteps.getValue();
					response = reqResVO.getResXML();
					if(response !=null){
						if(response.contains("Success")){
							result = "Success";
						}else if(response.contains("Warning")){
							result = "Success";
						}else if(response.contains("<Exception")){
							result = "Exception";
						}else if(response.contains("Error")){
							result = "Fail";
							isFail= true;
						}else{
							result = response;
						}
					}

				}
				if(isFail){
					result = "Fail";  
				}
				String orderNo = testResultsMap.get("orderNo_"+testCaseName).toString();
				sb.append("<tr><td><a href=#"+testCaseName+">"+testCaseName+"</a></td><td>"+result+"</td><td>"+orderNo+"</td></tr>");




			}

		}
		sb.append("</tbody></table>");	     
		sb.append("<br>");
		sb.append("<h2 align='center'>Details</h2>");

		Iterator entries1 = testResultsMap.entrySet().iterator();
		while (entries1.hasNext()) {
			Entry thisEntry = (Entry) entries1.next();
			if(!thisEntry.getKey().toString().contains("orderNo_")){
				String testCaseName = thisEntry.getKey().toString();
				sb.append("<h3 align='center' style='font-style: italic; color: #aaa'><strong><span style='color: #000000'><a id="+testCaseName+">"+testCaseName+"</a></span></strong></h3>");
				sb.append("<table align='center' table-layout='fixed' width='70%' border='1' cellpadding='1' cellspacing='1' rules='all'><tbody><tr><td width='35%'><strong>Steps</strong></td><td><strong>Result</strong></td><td><strong>Request </strong></td><td><strong>Order Status in DOMS </strong></td><td><strong>Payment Status </strong></td><td><strong>Comments</strong></td></tr>");

				Map testResults = (Map)thisEntry.getValue();
				Iterator results = testResults.entrySet().iterator();

				while (results.hasNext()) {

					Entry testSteps = (Entry) results.next();
					String testStep = testSteps.getKey().toString();

					ReqResVO reqResVO = (ReqResVO)testSteps.getValue();
					if(reqResVO.getWarningQueryVOList()!=null){
						setWarninrQueryVOListFromRegResVO(reqResVO.getWarningQueryVOList());
					}
					String response = reqResVO.getResXML();

					if(response !=null){
						if(response.contains("Success")){
							result = "Success";
						}else if(response.contains("<Exception")){
							result = "Exception";
						}else if(response.contains("Error")){
							result = "Fail";

						}else{
							result = response;
						}
					}

					sb.append("<tr><td>"+testStep+"</td>");

					if(result.equalsIgnoreCase("Fail") || result.equalsIgnoreCase("Exception") || result.equalsIgnoreCase("")){
						StringBuilder sb1 = new StringBuilder();
						sb1.append(reqResVO.getReqXML());
						String time1 = nikeDOMSApplicationUtils.getUniqueNo();	
						failReqPath = System.getProperty("user.dir")+"/resources/applogs/failureResponse/request_"+testCaseName+time1+".txt";
						StringBuilder sb2 = new StringBuilder();
						sb2.append(reqResVO.getResXML());
						failResPath = System.getProperty("user.dir")+"/resources/applogs/failureResponse/response_"+testCaseName+time1+".txt";
						FileWriter fstream1;
						FileWriter fstream2;
						try {
							fstream1 = new FileWriter(failReqPath);
							BufferedWriter out1 = new BufferedWriter(fstream1);
							out1.write(sb1.toString());
							out1.close();

							fstream2 = new FileWriter(failResPath);
							BufferedWriter out2 = new BufferedWriter(fstream2);
							out2.write(sb2.toString());
							out2.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						sb.append("<td><a id="+testCaseName+" href="+failResPath+">"+result+"</a></td>");
						sb.append("<td><a id="+testCaseName+" href="+failReqPath+">ReqXML</a></td>");
						if(reqResVO.getOrderStatus() != null){
							sb.append("<td>"+reqResVO.getOrderStatus()+"</td>");
						}else{
							sb.append("<td></td>");
						}
						if(reqResVO.getPaymentStatus() != null){
							sb.append("<td>"+reqResVO.getPaymentStatus()+"</td>");
						}else{
							sb.append("<td></td>");
						}

						if(reqResVO.getComment() != null){
							sb.append("<td>"+reqResVO.getComment()+"</td></tr>");
						}else{
							reqResVO.setComment("");
							sb.append("<td>"+reqResVO.getComment()+"</td></tr>");
						}


					}else{
						sb.append("<td>"+result+"</td>");
						sb.append("<td></td>");
						if(reqResVO.getOrderStatus() != null){
							sb.append("<td>"+reqResVO.getOrderStatus()+"</td>");
						}else{
							sb.append("<td></td>");
						}
						if(reqResVO.getPaymentStatus() != null){
							sb.append("<td>"+reqResVO.getPaymentStatus()+"</td>");
						}else{
							sb.append("<td></td>");
						}
						if(reqResVO.getComment() != null){
							sb.append("<td>"+reqResVO.getComment()+"</td></tr>");
						}else{
							reqResVO.setComment("");
							sb.append("<td>"+reqResVO.getComment()+"</td></tr>");
						}

					}

				}}
			sb.append("</tbody></table>");
		}
		sb.append("</body>");
		sb.append("</html>");
		String time = nikeDOMSApplicationUtils.getUniqueNo();	    
		String htmlReportPath = System.getProperty("user.dir")+"/resources/applogs/report/report_"+time+".html";
		FileWriter fstream;
		try {
			fstream = new FileWriter(htmlReportPath);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(sb.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createExcelForWarnings();
		return sb.toString();
	}
	private void createExcelForWarnings() {
		if(warningQueryCnt>0 && getWarningQueryVOList()!=null){
			ArrayList<String> headings =new ArrayList<String>();
			headings.add("Database");
			headings.add("Input Parameter");
			headings.add("Query");
			createExcelHelper.createExcel(getWarningQueryVOList(), headings);	
		}
	}
	private void setWarninrQueryVOListFromRegResVO(
			ArrayList<WarningQueryVO> warningQueryVOList2) {
		ArrayList<WarningQueryVO> warningQueryVOTempList = null;
		if(warningQueryVOList2!=null && warningQueryVOList2.size()>0){
			if(warningQueryCnt>0){
				warningQueryVOTempList=getWarningQueryVOList();
			}else{
				warningQueryVOTempList= new ArrayList<WarningQueryVO>();
			}
			for(WarningQueryVO warningQueryVO:warningQueryVOList2){
				warningQueryVOTempList.add(warningQueryVO);
			}
		}
		setWarningQueryVOList(warningQueryVOTempList);
		warningQueryCnt++;	
	}
	private int calculatePassPercentage() {
		double result =((double)passCount/(double)totalNumberOfTestCases)*100;
		int i = (int)result;
		return i;

	}

	/*
	 * Returns success if the testcase is success
	 */
	public Boolean isSuccess(Map<String, ReqResVO> responseDataMap){
		Boolean isSucess = false;		
		String response;	
		int passCnt = 0,failCnt=0;
		for (Map.Entry<String, ReqResVO> entry : responseDataMap.entrySet())
		{

			ReqResVO reqResVO = (ReqResVO)entry.getValue();
			response = reqResVO.getResXML();
			if(response !=null){

				if(response.contains("Success")){		    		
					passCnt++;
				}else if(response.contains("<Exception")){		    		 
					failCnt++;
				}else if(response.contains("Error")){		    		 
					failCnt++;
				}else{		    		
					passCnt++;
				}
			}

		}
		isSucess =(passCnt>0&&failCnt==0) ? true : false;	

		return isSucess;

	}
	public Map getResultMap(Map resultMap,List<ResultVO> resultVoList){
		Map returnResultmap =  new LinkedHashMap();
		for(ResultVO resultVO : resultVoList){
			Iterator entries = resultMap.entrySet().iterator();
			while (entries.hasNext()) {
				Entry thisEntry = (Entry) entries.next();
				if(thisEntry.getKey().toString()!=null){			    		
					if(thisEntry.getKey().toString().equals(resultVO.getSelectedTestCase())){			    			
						returnResultmap.put(thisEntry.getKey().toString(), (Map)thisEntry.getValue());
						returnResultmap.put("orderNo_"+thisEntry.getKey().toString(), resultVO.getOrderNo());					      

					}
				}
			}

		}
		return returnResultmap;
	}
	public String getOrderNumber(TestCaseDataVO testCaseDataVO) {
		ArrayList<XmlTagLinesVO> xmlTagLinesVOList = new ArrayList<XmlTagLinesVO>();
		xmlTagLinesVOList = testCaseDataVO.getXmlTagLinesVOList();
		String orderN ="RG"+nikeDOMSApplicationUtils.generateUniqueNo("MddHHmmss");
		String enterpriseCode="";
		String orderType="";
		XmlTagLinesVO headerLine=new XmlTagLinesVO();
		headerLine=xmlTagLinesVOList.get(0);
		for(XmlTagAttributesVO xmlTagAttributesVO:headerLine.getXmlTagAttributesVO()){
			if("EnterpriseCode".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())){
				enterpriseCode=xmlTagAttributesVO.getAttributeValue();

			}
			if("OrderType".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())){
				orderType=xmlTagAttributesVO.getAttributeValue();
				break;

			}
		}
		if(orderType.equals("STORE")){
			orderN="S"+nikeDOMSApplicationUtils.generateUniqueNo("MMddHHmmss");
		}
		else{
			if("NIKEUS".equalsIgnoreCase(enterpriseCode)){
				orderN="O"+nikeDOMSApplicationUtils.generateUniqueNo("MMddHHmmss");

			}
			if("NIKEEUROPE".equalsIgnoreCase(enterpriseCode)){
				orderN="EO"+nikeDOMSApplicationUtils.generateUniqueNo("MddHHmmss");

			}
			if(("NIKEJP".equalsIgnoreCase(enterpriseCode))||
					("NIKECN".equalsIgnoreCase(enterpriseCode))){
				orderN="AO"+nikeDOMSApplicationUtils.generateUniqueNo("MddHHmmss");
			}
			if("NIKEGS".equalsIgnoreCase(enterpriseCode)){
				orderN="GT"+nikeDOMSApplicationUtils.generateUniqueNo("MddHHmmss");

			}


		}
		return orderN;
	}
	private String getOrderNo(TestCaseDataVO testCaseDataVOObj2) {
		String orderNo="";

		XmlTagLinesVO headerLine=new XmlTagLinesVO();
		headerLine=testCaseDataVOObj2.getXmlTagLinesVOList().get(0);
		for(XmlTagAttributesVO xmlTagAttributesVO:headerLine.getXmlTagAttributesVO()){
			if("OrderNo".equalsIgnoreCase(xmlTagAttributesVO.
					getAttributeName())){
				if(!(xmlTagAttributesVO.getAttributeValue().equals(""))){
					orderNo = xmlTagAttributesVO.getAttributeValue();
				}
				break;
			}
		}

		return orderNo;
	}

}
