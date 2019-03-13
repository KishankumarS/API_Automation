package com.doms.automation.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.helper.impl.ReusedMethodsImpl;
import com.doms.automation.utils.DbValidationConstants;
import com.doms.automation.utils.NikeDOMSConstants;

public class FraudCheckService {

	public ReqResVO performFraudCheck(String orderNo, String enterprisecode,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO, TestCaseDataVO testCaseDataVO,
			Map<String, DbEnvConfig> dbConfigMap, Map<String, ReqResVO> responseMap) {

		ReusedMethodsImpl reusedMethodsImpl = new ReusedMethodsImpl();
		List<String> dateList = new ArrayList<>();
		ReqResVO reqResVO = new ReqResVO();

		// Fetching available date and create time stamp
		dateList = fetchAvailableDateAndCreateTimeStampFromAsyncQ(nikeDOMSConnectionDAO, orderNo, reusedMethodsImpl,
				testCaseDataVO, reqResVO, dbConfigMap);

		if (!dateList.contains("")) {

			if (checkIfOrderIsMpos(testCaseDataVO)) {
				reqResVO = performFraudCheckForMposOrder(dateList.get(0), reqResVO);

			} else {
				reqResVO = performFraudCheckForNonMposOrder(reqResVO, dateList.get(1), dateList.get(0), enterprisecode,
						orderNo, nikeDOMSConnectionDAO);
			}
		}

		return reqResVO;
	}

	public boolean checkIfOrderIsMpos(TestCaseDataVO testCaseDataVO) {

		AutomationHelperImpl helperImpl = new AutomationHelperImpl();

		/*
		 * Passing '1' as line number purposely since order type is to be
		 * checked which is not related to any order line.
		 */

		if (helperImpl.getAttributeVlaue("Mpos_StoreID", testCaseDataVO.getXmlTagLinesVOList(), "1").isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	public ReqResVO performFraudCheckForMposOrder(String availableDate, ReqResVO reqResVO) {

		if (availableDate.isEmpty()) {
			reqResVO.setIsNotSuccess(false);
			reqResVO.setResXML("Success");

		} else {
			reqResVO.setIsNotSuccess(true);
			reqResVO.setResXML("Error");
			reqResVO.setComment("Fraud Check unsuccessful. Available Date verification failed. For Mpos Order"
					+ " 'CheckOrderForFraudHold_RDT' entry should not be present in Async_process_Q");

		}
		return reqResVO;

	}

	public ReqResVO performFraudCheckForNonMposOrder(ReqResVO reqResVO, String createts, String availableDate,
			String enterprisecode, String orderNo, NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {

		ReusedMethodsImpl reusedMethodsImpl = new ReusedMethodsImpl();
		Integer timeDifference = Integer.parseInt(getMapWithTsForEnterpriseCode().get(enterprisecode));

		reqResVO = reusedMethodsImpl.validateAvailableDate(createts, availableDate, timeDifference);

		if (reqResVO.getResXML().equalsIgnoreCase("Success")) {

			reqResVO = updateFraudEntryAvailableDateToSystemDate(reqResVO, createts, availableDate, enterprisecode,
					orderNo, nikeDOMSConnectionDAO);
		} else {

			reqResVO.setIsNotSuccess(true);
			reqResVO.setResXML("Error");
			reqResVO.setComment("Fraud Check unsuccessful. Available Date should be " + timeDifference + " minutes "
					+ "more than create Time Stamp date");
		}
		return reqResVO;
	}

	public ReqResVO checkIfAvailableDateIsPresentInArchive(ReqResVO reqResVO, String createts, String availableDate,
			String enterprisecode, String orderNo, NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {

		try {

			// Performing Async_Process_q_Archive validation
			ReusedMethodsImpl reusedMethodsImpl = new ReusedMethodsImpl();

			Thread.sleep(15000);
			availableDate = reusedMethodsImpl.getAvailableDateFromDBWithProcessName(nikeDOMSConnectionDAO, orderNo,
					NikeDOMSConstants.InitiateFraudCheck_OUT_svc,
					DbValidationConstants.GET_AVAILABLE_DATE_FROM_ASYNC_PROCESS_Q_ARCHIVE);

			if (!(availableDate.isEmpty())) {

				reqResVO.setIsNotSuccess(false);
				reqResVO.setResXML("Success");
				reqResVO.setComment("Fraud Check Success. Available Date verified successfully");

			} else {

				reqResVO.setResXML("Warning");
				reqResVO.setComment("Fraud Check  partially successful. The entry for Available Date did"
						+ " not come in extn_async_process_Q_Archive even after waiting for 15 seconds."
						+ "So Please check manually");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return reqResVO;

	}

	public ReqResVO updateFraudEntryAvailableDateToSystemDate(ReqResVO reqResVO, String createts, String availableDate,
			String enterprisecode, String orderNo, NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {

		/*
		 * Updating available date to system date so that the entry gets
		 * consumed for further DB validation
		 */

		int count = 0;

		count = nikeDOMSConnectionDAO.updateDB2Parameters(NikeDOMSConstants.CheckOrderForFraudHold_RDT, orderNo,
				DbValidationConstants.UPDATE_AVAILABLE_DATE_IN_ASYNC_PROCESS_Q, NikeDOMSConstants.DOMS);

		if (count > 0) {

			checkIfAvailableDateIsPresentInArchive(reqResVO, createts, availableDate, enterprisecode, orderNo,
					nikeDOMSConnectionDAO);

		} else {

			reqResVO.setResXML("Warning");
			reqResVO.setComment("Update query for updating available date failed. "
					+ "Pls. update manually and check whether that entry comes in extn_process_q_archive");
		}

		return reqResVO;

	}

	private List<String> fetchAvailableDateAndCreateTimeStampFromAsyncQ(NikeDOMSConnectionDAO nikeDOMSConnectionDAO,
			String orderNo, ReusedMethodsImpl reusedMethodsImpl, TestCaseDataVO testCaseDataVO, ReqResVO reqResVO,
			Map<String, DbEnvConfig> dbConfigMap) {

		AutomationHelperImpl helperImpl = new AutomationHelperImpl();
		List<String> dateList = new ArrayList<>();
		String availableDate = "";
		String createts = "";

		try {

			if (helperImpl.getAttributeVlaue("EnterpriseCode", testCaseDataVO.getXmlTagLinesVOList(), "1")
					.equalsIgnoreCase("NIKEGS")) {

				availableDate = reusedMethodsImpl.getAvailableDateFromDBWithProcessName(nikeDOMSConnectionDAO,
						helperImpl.getFSOrderNo(orderNo, dbConfigMap), NikeDOMSConstants.CheckOrderForFraudHold_RDT,
						DbValidationConstants.GET_AVAILABLE_DATE_FROM_ASYNC_PROCESS_Q);
			} else {

				availableDate = reusedMethodsImpl.getAvailableDateFromDBWithProcessName(nikeDOMSConnectionDAO, orderNo,
						NikeDOMSConstants.CheckOrderForFraudHold_RDT,
						DbValidationConstants.GET_AVAILABLE_DATE_FROM_ASYNC_PROCESS_Q);
			}

			createts = reusedMethodsImpl.getCreateTimeStamp(nikeDOMSConnectionDAO, orderNo);

			if (!availableDate.isEmpty()) {

				if (!createts.isEmpty()) {

					reqResVO.setResXML("Success");

					/*
					 * Ignoring seconds i.e converting the time stamp from
					 * 'YYYY-MM-DD HH24:MI:SS' to 'YYYY-MM-DD HH24:MI:00'
					 */
					dateList.add(0, availableDate.substring(0, availableDate.length() - 3)+":00");
					dateList.add(1, createts.substring(0, createts.length() - 3)+":00");

				} else {

					reqResVO.setIsNotSuccess(true);
					reqResVO.setResXML("Error");
					reqResVO.setComment("Create Time Stamp Date not found in Async_process_Q");
				}
			} else {
				reqResVO.setIsNotSuccess(true);
				reqResVO.setResXML("Error");
				reqResVO.setComment("Available Date not found in Async_process_Q");

			}

			// To avoid array index out of bound exception
			if (dateList.isEmpty()) {
				dateList.add(0, "");
				dateList.add(1, "");
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return dateList;

	}

	private Map<String, String> getMapWithTsForEnterpriseCode() {
		Map<String, String> map = new LinkedHashMap<String, String>();

		map.put("NIKEUS", "16");
		map.put("NIKECN", "30");
		map.put("NIKEJP", "16");
		map.put("NIKEXA", "30");
		map.put("NIKEGS", "30");
		map.put("NIKEEUROPE", "16");
		return map;

	}

}
