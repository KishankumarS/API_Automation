package com.doms.automation.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.xml.xpath.XPathExpressionException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.bean.TestCaseResultVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.helper.impl.ReusedMethodsImpl;
import com.doms.automation.populatemaps.PopulateRunProcessStepMap;
import com.doms.automation.runprocessstep.RunProcessStep;
import com.doms.automation.service.PostRequest;
import com.doms.automation.service.ProcessOrderService;
import com.doms.automation.utils.NikeDOMSLogger;

public class TestExecuteController implements Callable<TestCaseResultVO> {
	NikeDOMSConnectionDAO nikedomsconnectiondao;
	ProcessOrderService processOrderService;

	String environment = "";
	String orderNo = "";
	String exchangeOrderNo=null;
	String[] processArr = new String[100];
	Map<String, ReqResVO> responseDataMap = new LinkedHashMap<String, ReqResVO>();
	TestCaseDataVO testCaseDataVO;
	EnvConfigVO envConfigVO;
	AutomationHelperImpl autometionHelper;
	private String threadName;
	public static Map ResultsMap = new LinkedHashMap();
	Map<String, DbEnvConfig> dbConfigMap;
	Map<String, RunProcessStep> runProcessStepMap;	
	PostRequest postRequest;
	ArrayList<XmlTagLinesVO> xmlTagLinesVOList;
	public static int threadNo = 1;
	String retVariable = "";
	boolean isNikeGS = false;
	PopulateRunProcessStepMap populateRunProcessStepMap;
	ReusedMethodsImpl reusedMethodsImpl;
	TestExecuteController(String environment, String orderNo, TestCaseDataVO testCaseDataVO, EnvConfigVO envConfigVO,
			Map<String, DbEnvConfig> dbConfigMap) {
		threadName = testCaseDataVO.getTestCaseName();
		this.environment = environment;
		this.orderNo = orderNo;
		this.processArr = testCaseDataVO.getProcessStepsArr();
		this.envConfigVO = envConfigVO;
		this.dbConfigMap = dbConfigMap;
		xmlTagLinesVOList = testCaseDataVO.getXmlTagLinesVOList();
		this.testCaseDataVO = testCaseDataVO;
		System.out.println("Creating " + threadName);
		NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME", "", NikeDOMSLogger.LEVEL_INFO, "", "Creating" + threadName);
		System.out.println("Thread Number----------------------->>>>>>>>>>>>>>>>>>>>>>" + threadNo);
		threadNo++;
	}

	public TestExecuteController() {
		// TODO Auto-generated constructor stub
	}

	public TestCaseResultVO call() throws Exception {
		if (threadName != null) {
			System.out.println("Running " + threadName);
			try {
				responseDataMap = this.runTestCases();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (XPathExpressionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvalidFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ResultsMap.put(threadName, responseDataMap);
			ResultsMap.put("orderNo_" + threadName, orderNo);
		}
		return new TestCaseResultVO(responseDataMap);
	}

	public Map getResponse() {
		return responseDataMap;
	}

	public Map runTestCases() throws FileNotFoundException, SQLException, IOException, ClassNotFoundException,
			XPathExpressionException, InvalidFormatException {

		Map<String, ReqResVO> responseMap = new LinkedHashMap<String, ReqResVO>();
		// populating runProcessStepMap
		populateRunProcessStepMap = new PopulateRunProcessStepMap();
		runProcessStepMap = populateRunProcessStepMap.
				getRunProcessStepMap();
		
		ReqResVO reqResVO = new ReqResVO();
		autometionHelper = new AutomationHelperImpl();
		nikedomsconnectiondao = new NikeDOMSConnectionDAO(dbConfigMap);
		reusedMethodsImpl = new ReusedMethodsImpl();
		
		/*
		 * Following if-loop was changed, as part of OMT-25301
		 *
		 * if ("NIKEGS".equalsIgnoreCase(
		 * autometionHelper.getEnterpriseCode(xmlTagLinesVOList))) { isNikeGS =
		 * true; }
		 */

		if ("NIKEGS".equalsIgnoreCase(autometionHelper.getEnterpriseCode(xmlTagLinesVOList))
				|| "NIKEXA".equalsIgnoreCase(autometionHelper.getEnterpriseCode(xmlTagLinesVOList))) {
			isNikeGS = true;
		}

		processOrderService = new ProcessOrderService(environment, nikedomsconnectiondao, orderNo, xmlTagLinesVOList,
				isNikeGS, dbConfigMap,exchangeOrderNo);
		postRequest = new PostRequest();
		try {
			for (int i = 0; i < processArr.length; i++) {
				reqResVO = new ReqResVO();
				NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME", "", NikeDOMSLogger.LEVEL_INFO, "",
						"processArr[i]" + processArr[i] + " -  " + threadName);
				System.out.println(testCaseDataVO.getTestCaseName() + "--Step--" + processArr[i] + "-----");

				/*
				 * if(responseMap.size()>0){
				 * reqResVO=getReqResVO(responseMap,reqResVO);
				 * 
				 * }
				 */
				/*
				 * Setting the return variable from previous step to current
				 * step if the class variable retVariable contains any value.
				 */
				if (retVariable != null && !(("").equals(retVariable))) {

					reqResVO.setRetVariable(retVariable);
				}
				responseMap = getResponseMap(runProcessStepMap, responseMap, reqResVO, nikedomsconnectiondao,
						processOrderService, environment, orderNo, testCaseDataVO, envConfigVO, dbConfigMap,
						processArr[i], isNikeGS,exchangeOrderNo);
				/*
				 * Getting the variables set from the current step which is
				 * neede for the next step in to a class variable
				 * 
				 */
				for (Map.Entry<String, ReqResVO> entry : responseMap.entrySet()) {

					ReqResVO reqResVOObj = (ReqResVO) entry.getValue();
					if (reqResVOObj.getRetVariable() != null && !(("").equals(reqResVOObj.getRetVariable()))) {

						retVariable = reqResVOObj.getRetVariable();
					}

				}
				if("CreateExchangeOrder".equalsIgnoreCase(processArr[i])){
					exchangeOrderNo = reusedMethodsImpl.getExchangeOrderNoWithOrderNo(orderNo, 
							nikedomsconnectiondao);
					if(exchangeOrderNo!=null||!exchangeOrderNo.equals("")){
					processOrderService.setExchangeOrderNo(exchangeOrderNo);
					}
				}
				if (!isResXMLSuccess(responseMap, processArr[i])) {

					return responseMap;
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
			reqResVO.setReqXML(e.getLocalizedMessage());
			reqResVO.setResXML("<Error");
			responseMap.put("Exception", reqResVO);
			return responseMap;
		}

		return responseMap;
	}

	private ReqResVO getReqResVO(Map<String, ReqResVO> responseMap, ReqResVO reqResVO) {
		Set set = responseMap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry me = (Map.Entry) iterator.next();
			reqResVO = (com.doms.automation.bean.ReqResVO) me.getValue();

		}
		return reqResVO;
	}

	private Map<String, ReqResVO> getResponseMap(Map<String, RunProcessStep> runProcessStepMap2,
			Map<String, ReqResVO> responseMap, ReqResVO reqResVO, NikeDOMSConnectionDAO nikedomsconnectiondao2,
			ProcessOrderService processOrderService2, String environment2, String orderNo2,
			TestCaseDataVO testCaseDataVO2, EnvConfigVO envConfigVO2, Map<String, DbEnvConfig> dbConfigMap2,
			String processArrStep, boolean isNikeGS, String exchangeOrderNo2) {
		if (!processArrStep.contains("_") && !processArrStep.contains("*") && !processArrStep.contains("-")) {
			responseMap = runProcessStepMap2.get(processArrStep).execute(responseMap, reqResVO, nikedomsconnectiondao2,
					processOrderService2, environment2, orderNo2, testCaseDataVO2, envConfigVO2, dbConfigMap2,
					processArrStep, isNikeGS, exchangeOrderNo2);

		} else if (!processArrStep.contains("*") && !processArrStep.contains("-")) {
			responseMap = runProcessStepMap2.get(processArrStep.substring(0, processArrStep.indexOf("_") + 1)).execute(
					responseMap, reqResVO, nikedomsconnectiondao2, processOrderService2, environment2, orderNo2,
					testCaseDataVO2, envConfigVO2, dbConfigMap2, processArrStep, isNikeGS, exchangeOrderNo2);

		} else if (processArrStep.contains("-") && !processArrStep.contains("*")) {

			responseMap = runProcessStepMap2.get(processArrStep.substring(0, processArrStep.indexOf("-"))).execute(
					responseMap, reqResVO, nikedomsconnectiondao2, processOrderService2, environment2, orderNo2,
					testCaseDataVO2, envConfigVO2, dbConfigMap2, processArrStep, isNikeGS, exchangeOrderNo2);

		} else if (processArrStep.contains("-") && processArrStep.contains("*")) {

			responseMap = runProcessStepMap2.get(processArrStep.substring(0, processArrStep.indexOf("-"))).execute(
					responseMap, reqResVO, nikedomsconnectiondao2, processOrderService2, environment2, orderNo2,
					testCaseDataVO2, envConfigVO2, dbConfigMap2, processArrStep, isNikeGS, exchangeOrderNo2);

		}
		return responseMap;
	}

	private boolean isResXMLSuccess(Map<String, ReqResVO> responseMap, String processArr) {
		boolean isSuccess = true;
		for (Map.Entry<String, ReqResVO> entry : responseMap.entrySet()) {
			ReqResVO reqResVO = (ReqResVO) entry.getValue();
			if (reqResVO.getIsNotSuccess()) {
				return false;
			}
		}

		return isSuccess;
	}

	

}
