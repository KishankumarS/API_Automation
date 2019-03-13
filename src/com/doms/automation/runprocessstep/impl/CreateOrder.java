package com.doms.automation.runprocessstep.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.http.HttpResponse;
import org.xml.sax.SAXException;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.dbvalidations.VerifyInDoms;
import com.doms.automation.dbvalidations.VerifyProcessHolds;
import com.doms.automation.generatexml.impl.CreateOrderXMl;
import com.doms.automation.generatexml.impl.CreateWOXml;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.helper.impl.ReusedMethodsImpl;
import com.doms.automation.runprocessstep.RunProcessStep;
import com.doms.automation.service.CreateOrderService;
import com.doms.automation.service.FraudCheckService;
import com.doms.automation.service.ProcessOrderService;
import com.doms.automation.utils.DbValidationConstants;
import com.doms.automation.utils.NikeDOMSConstants;
import com.doms.automation.utils.NikeDOMSLogger;

public class CreateOrder implements RunProcessStep {

	@Override
	public Map<String, ReqResVO> execute(Map<String, ReqResVO> responseMap, ReqResVO reqResVO,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO, ProcessOrderService processOrderService, String environment,
			String orderNo, TestCaseDataVO testCaseDataVO, EnvConfigVO envConfigVO,
			Map<String, DbEnvConfig> dbConfigMap, String processStep, boolean isNikeGS, String exchangeOrderNo) {
		ReusedMethodsImpl reusedMethodsImpl= new ReusedMethodsImpl();
		Map<String,String> map=reusedMethodsImpl.populateAttributeNameMap(testCaseDataVO.getXmlTagLinesVOList(), "1");
		String orderXML = "";
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();
		CreateOrderService createOrderservice = new CreateOrderService();
		VerifyInDoms verifyInDoms = new VerifyInDoms();
		VerifyProcessHolds verifyProcessHolds = new VerifyProcessHolds();
		com.doms.automation.service.PostRequest postRqst = new com.doms.automation.service.PostRequest();
		String ordno;
		String xml;
		String paymentType=map.get("PaymentType");
		try {
			/*
			 * reqResVO = createOrderservice.authXML(environment,
			 * automationHelperImpl.getAuthXml(testCaseDataVO.
			 * getXmlTagLinesVOList(), orderNo), envConfigVO,reqResVO);
			 * CreateOrderXMl createOrderXml = new CreateOrderXMl(
			 * testCaseDataVO.getXmlTagLinesVOList(), orderNo,
			 * reqResVO.getAuthResponse());
			 */
			// --

			if (orderNo.contains("al")) {
				String[] x = orderNo.split("al");
				ordno = x[0];
				orderXML = this.getOrderXML(ordno, environment, envConfigVO, reqResVO, testCaseDataVO,nikeDOMSConnectionDAO);
				xml = orderXML.replace("Auto", "Auto" + x[1]);
				orderXML = xml;
				System.out.println("My XMl: " + x[1] + " ::" + orderXML);
				orderNo = ordno;
			
			} else {
				orderXML = this.getOrderXML(orderNo, environment, envConfigVO, reqResVO, testCaseDataVO,nikeDOMSConnectionDAO);

				

			}
			if (orderNo.contains("Exc")) {
				String[] x = orderNo.split("Exc");
				orderNo = x[0];
			}
			// --

			NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME", "", NikeDOMSLogger.LEVEL_INFO, "",
					"orderXml" + orderXML + " -  " + testCaseDataVO.getTestCaseName());
			System.out.println(orderXML);

			if (orderXML != null && !orderXML.equalsIgnoreCase("")) {
				
				reqResVO = createOrderservice.createOrder(environment, orderXML, envConfigVO);
				responseMap.put("Create Order-"+ orderNo, reqResVO);
				NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME", "", NikeDOMSLogger.LEVEL_INFO, "",
						"REQ>>>>" + reqResVO.getReqXML() + "\nRES>>>" + reqResVO.getResXML() + " -  "
								+ testCaseDataVO.getTestCaseName());

				NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME", "", NikeDOMSLogger.LEVEL_INFO, "",
						"REQ>>>>" + reqResVO.getReqXML() + "\nRES>>>" + reqResVO.getResXML() + " -  "
								+ testCaseDataVO.getTestCaseName());
			
				if (!reqResVO.resXML.equalsIgnoreCase("Success")) {
					reqResVO.setIsNotSuccess(true);
					return responseMap;
				}

				Thread.sleep(20000);

				reqResVO = createOrderservice.checkOrderInDOMS(nikeDOMSConnectionDAO, environment, orderNo);
				processOrderService.setEnterpriseCode();

				reqResVO = automationHelperImpl.setStatus(reqResVO, envConfigVO, orderNo, nikeDOMSConnectionDAO,
						processOrderService);

				NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME", "", NikeDOMSLogger.LEVEL_INFO, "",
						"REQ>>>>" + reqResVO.getReqXML() + "\nRES>>>" + reqResVO.getResXML() + " -  "
								+ testCaseDataVO.getTestCaseName());
				responseMap.put("Check_whether_Order_present_in_DOMS-"+ orderNo, reqResVO);
				if (!reqResVO.resXML.equalsIgnoreCase("Success")) {
					reqResVO.setIsNotSuccess(true);
					return responseMap;
				}

			}

			/*
			 * Verify whether the order acknowledgement message to ATG is
			 * getting posted in async_process_q table
			 */
			Thread.sleep(20000);
			reqResVO = verifyProcessHolds.verifyInDOMSProcessQueue(orderNo,
					DbValidationConstants.DOMS_ACKNOWLEDGEMESSAGE_EXECUTESERVICE_NAME,
					DbValidationConstants.GET_ASYNC_PROCESS_QUEUE_DETAILS_BY_SERVICE_NAME, nikeDOMSConnectionDAO);
			try {
				reqResVO = automationHelperImpl.setStatus(reqResVO, envConfigVO, orderNo, nikeDOMSConnectionDAO,
						processOrderService);
			} catch (JAXBException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			responseMap.put("Verify Order Acknowledge Message to ATG in Async_Process_Queue " + orderNo, reqResVO);

			/*
			 * verifying whether work order created for nike id items.
			 * getNikeIdItemsCount return the number of nikeid items if any. If
			 * the count is greater than 0 then the count is validated with the
			 * db entry.
			 */
			/*
			 * int serviceCnt = 0; int nikeIdItems =
			 * automationHelperImpl.getNikeIdItemsCount("NIKEID",
			 * testCaseDataVO.getXmlTagLinesVOList()); boolean isIC =
			 * automationHelperImpl.isIC(testCaseDataVO.getXmlTagLinesVOList());
			 * if(isIC){ serviceCnt =
			 * automationHelperImpl.getNikeIdItemsCount("SERVICE",
			 * testCaseDataVO.getXmlTagLinesVOList()); }
			 */
			int expectedWOCount = automationHelperImpl.getExpectedWOCount(testCaseDataVO.getXmlTagLinesVOList());
			if (expectedWOCount > 0) {

				Thread.sleep(10000);
				CreateWOXml createWOXml = new CreateWOXml();
				String fsoOrderNo = automationHelperImpl.getOrderNoOrFsoOrderNo(isNikeGS, orderNo, dbConfigMap);
				String woXml = createWOXml.generateXml(processStep, orderNo, nikeDOMSConnectionDAO,
						testCaseDataVO.getXmlTagLinesVOList(), dbConfigMap, isNikeGS, fsoOrderNo);
				HttpResponse response = postRqst.post(environment, woXml, "Neo_Execute_WorkOrder_svc", "Y",
						envConfigVO);

				if (response.toString().contains("ErrorDescription")) {
					automationHelperImpl = new AutomationHelperImpl();
					reqResVO.setResXML("Error");
					reqResVO.setComment(automationHelperImpl.getErrorDescription(response.toString()));
					reqResVO.setIsNotSuccess(true);

					reqResVO = automationHelperImpl.setStatus(reqResVO, envConfigVO, orderNo, nikeDOMSConnectionDAO,
							processOrderService);
					responseMap.put("Verify Work order created for NiKEID items in doms " + orderNo, reqResVO);

					return responseMap;
				}

				Thread.sleep(15000);

				reqResVO = verifyInDoms.verifyWorkOrderCreatedtedForNikeId(orderNo, nikeDOMSConnectionDAO,
						expectedWOCount);

				reqResVO = automationHelperImpl.setStatus(reqResVO, envConfigVO, orderNo, nikeDOMSConnectionDAO,
						processOrderService);
				responseMap.put("Verify Work order created for service items in doms " + orderNo, reqResVO);

				if (!reqResVO.resXML.equalsIgnoreCase("Success")) {

					reqResVO.setIsNotSuccess(true);
					return responseMap;

				}

			}

			/*
			 * Performing Fraud Check for the order
			 */
			
			if(!reusedMethodsImpl.isFeedzai(nikeDOMSConnectionDAO)){
			FraudCheckService service = new FraudCheckService();
			reqResVO = service.performFraudCheck(orderNo,
					automationHelperImpl.getEnterpriseCode(testCaseDataVO.getXmlTagLinesVOList()),
					nikeDOMSConnectionDAO, testCaseDataVO, dbConfigMap, responseMap);
			responseMap.put("Perform Fraud Check-"+ orderNo, reqResVO);

			if (!(reqResVO.resXML.equalsIgnoreCase("Success") || reqResVO.resXML.equalsIgnoreCase("Warning"))) {
				reqResVO.setIsNotSuccess(true);
				return responseMap;
			}
			}

		} catch (IOException | JAXBException | SQLException | InterruptedException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return responseMap;
	}

	

	public String getOrderXML(String orderNo, String environment, EnvConfigVO envConfigVO, ReqResVO reqResVO,
			TestCaseDataVO testCaseDataVO,NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		String orderXML = "";
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();
		CreateOrderService createOrderservice = new CreateOrderService();
		CreateOrderXMl createOrderXml;
		try {
			if (orderNo.contains("Exc")) {
				String[] x = orderNo.split("Exc");
				String ordNo = x[0];
				
				/*reqResVO = createOrderservice.authXML(environment,
						automationHelperImpl.getAuthXml(testCaseDataVO.getXmlTagLinesVOList(), ordNo), envConfigVO,
						reqResVO);*/
				createOrderXml = new CreateOrderXMl(testCaseDataVO.getXmlTagLinesVOList(), orderNo,
						"",null, nikeDOMSConnectionDAO, environment, envConfigVO);
			} else {
				System.out.println(orderNo);
				/*reqResVO = createOrderservice.authXML(environment,
						automationHelperImpl.getAuthXml(testCaseDataVO.getXmlTagLinesVOList(), orderNo), envConfigVO,
						reqResVO);*/
				createOrderXml = new CreateOrderXMl(testCaseDataVO.getXmlTagLinesVOList(), orderNo,
						"",nikeDOMSConnectionDAO,environment, envConfigVO);
			}

			orderXML = createOrderXml.getOrderXml();
		} catch (XPathExpressionException | SAXException | ParserConfigurationException | IOException | JAXBException
				| InterruptedException e) {
			e.printStackTrace();
		}
		return orderXML;

	}

}
