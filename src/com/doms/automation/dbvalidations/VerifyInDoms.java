package com.doms.automation.dbvalidations;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.xml.bind.JAXBException;

import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.WarningQueryVO;
import com.doms.automation.bean.XmlTagAttributesVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.generatexml.impl.CreateTriggerXml;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.service.PostRequest;
import com.doms.automation.utils.DbValidationConstants;
import com.doms.automation.utils.NikeDOMSConstants;

public class VerifyInDoms {

	public ReqResVO verifyInArchive(String criteriaId, String environment,
			EnvConfigVO envConfigVO, String string1, String string2,
			String string3, NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {

		ReqResVO reqResVOObj = new ReqResVO();
		AutomationHelperImpl autometionHelper = new AutomationHelperImpl();
		PostRequest postRequest = new PostRequest();
		
		String triggerXml;
		CreateTriggerXml createTriggerXml = new CreateTriggerXml();
		try {
			triggerXml = createTriggerXml.generateXml(criteriaId);

			postRequest.post(environment, triggerXml, "triggerAgent", "N",
					envConfigVO);
			Thread.sleep(10000);
			String ocEmailProcessqArchiveEntry = autometionHelper
					.verifyEmailDOMSWOLike(
							nikeDOMSConnectionDAO,
							string1,
							string2,
							string3,
							NikeDOMSConstants.GET_ASYNC_PROCESS_QUEUE_ARCHIVE_PROCESSNAME);
			System.out.println("VALIDATION-process Q Archive: "
					+ ocEmailProcessqArchiveEntry);

			reqResVOObj.setReqXML("Verification in Async_Process_Q_Archive");
			
				reqResVOObj.setResXML(ocEmailProcessqArchiveEntry);
				if("Warning".equalsIgnoreCase(ocEmailProcessqArchiveEntry)){
					ArrayList<String> inputParameters = new ArrayList<String>();
					inputParameters.add(string1);
					inputParameters.add(string2);
					inputParameters.add(string3);
					WarningQueryVO warningQueryVO = new WarningQueryVO();
					warningQueryVO.setDataBase(NikeDOMSConstants.DOMS);
					warningQueryVO.setInputParameters(inputParameters);
					warningQueryVO.setQuery(NikeDOMSConstants.GET_ASYNC_PROCESS_QUEUE_ARCHIVE_DETAILS);
					ArrayList<WarningQueryVO> warningQueryVOList= null;
					if(reqResVOObj.getWarningQueryVOList()!=null && reqResVOObj.getWarningQueryVOList().size()>0){
						warningQueryVOList = reqResVOObj.getWarningQueryVOList();	
					}else{
						warningQueryVOList = new ArrayList<WarningQueryVO>();
					}				
					warningQueryVOList.add(warningQueryVO);
					reqResVOObj.setWarningQueryVOList(warningQueryVOList);
					reqResVOObj.setComment("The entry did not come in extn_async_process_Q_archive after 10 seconds.So Please check manually.");
				}
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reqResVOObj;

	}

	public ReqResVO verifyInQueue(String criteriaId, String environment,
			EnvConfigVO envConfigVO, String string1, String string2,
			String string3, NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {

		ReqResVO reqResVOObj = new ReqResVO();
		
		
		try {
			Thread.sleep(20000);
			
			
			String ocEmailProcessqEntry = getMailConfirmationInQnArchWoLike(
					nikeDOMSConnectionDAO, string1, string2, string3);
			reqResVOObj.setReqXML("Verification in Async Process Queue");
			
				reqResVOObj.setResXML(ocEmailProcessqEntry);
				if("Warning".equalsIgnoreCase(ocEmailProcessqEntry)){
					ArrayList<String> inputParameters = new ArrayList<String>();
					inputParameters.add(string1);
					inputParameters.add(string2);
					inputParameters.add(string2);
					WarningQueryVO warningQueryVO = new WarningQueryVO();
					warningQueryVO.setDataBase(NikeDOMSConstants.DOMS);
					warningQueryVO.setInputParameters(inputParameters);
					warningQueryVO.setQuery(NikeDOMSConstants.GET_ASYNC_PROCESS_QUEUE_ARCHIVE_PROCESSNAME);
					ArrayList<WarningQueryVO> warningQueryVOList= null;
					if(reqResVOObj.getWarningQueryVOList()!=null && reqResVOObj.getWarningQueryVOList().size()>0){
						warningQueryVOList = reqResVOObj.getWarningQueryVOList();	
					}else{
						warningQueryVOList = new ArrayList<WarningQueryVO>();
					}				
					warningQueryVOList.add(warningQueryVO);
					reqResVOObj.setWarningQueryVOList(warningQueryVOList);
					reqResVOObj.setComment("The entry did not come in extn_async_process Q after 20 seconds.So Please check manually.");
					
				}
			
			
		}  catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reqResVOObj;

	}
	
	public ReqResVO verifyInQAndArchiveWithProcessName(NikeDOMSConnectionDAO nikeDomsConnectionDao, String dataKey,
			String processName, String messagePattern) {

		AutomationHelperImpl helperImpl = new AutomationHelperImpl();
		ReqResVO reqResVOObj = new ReqResVO();
		String response = "";

		response = helperImpl.verifyEmailDOMS(nikeDomsConnectionDao, dataKey, processName, messagePattern,
				NikeDOMSConstants.GET_ASYNC_PROCESS_QUEUE_DETAILS_With_Process_Name);

		System.out.println("Status of entry in Extn_Process_Q " + response);

		if (response.contains("Error") || response.contains("Warning")) {

			try {

				System.out.println("Status of entry in Extn_Process_Q");
				Thread.sleep(15);

				response = helperImpl.verifyEmailDOMS(nikeDomsConnectionDao, dataKey, processName, messagePattern,
						NikeDOMSConstants.GET_ASYNC_PROCESS_QUEUE_ARCHIVE_DETAILS_With_Process_Name);

				System.out.println("Status of entry in Extn_Process_Q_Archive " + response);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if ("Warning".equalsIgnoreCase(response)) {

			reqResVOObj.setResXML("Warning");
			reqResVOObj.setComment("The entry did not come in extn_async_process_Q or "
					+ "extn_async_process_Q_Archive even after waiting for 15 sec. " + " So Please check manually.");

		} else if (response.equalsIgnoreCase("Success")) {
			reqResVOObj.setResXML("Success");
		} else {
			reqResVOObj.setResXML("Error");
		}
		return reqResVOObj;
	}


	
	
	///////////////////////

	private String getMailConfirmationInQnArchWoLike(
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO, String string1,
			String string2, String string3) {
		AutomationHelperImpl autometionHelper = new AutomationHelperImpl();
		String ocEmailProcessqEntry = autometionHelper.verifyEmailDOMS(
				nikeDOMSConnectionDAO, string1, string2, string3,
				NikeDOMSConstants.GET_ASYNC_PROCESS_QUEUE_DETAILS);
		System.out.println("VALIDATION-process Q: " + ocEmailProcessqEntry);
		if (ocEmailProcessqEntry.contains("Error")||
				ocEmailProcessqEntry.contains("Warning")) {
			ocEmailProcessqEntry = autometionHelper
					.verifyEmailDOMSWOLike(
							nikeDOMSConnectionDAO,
							string1,
							string2,
							string3,
							NikeDOMSConstants.GET_ASYNC_PROCESS_QUEUE_ARCHIVE_PROCESSNAME);
			System.out.println("VALIDATION-process Q Archive: "
					+ ocEmailProcessqEntry);
		}
		return ocEmailProcessqEntry;
	}


	public String getMailConfirmationInQnArch(
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO, String string1,
			String string2, String string3) {
		AutomationHelperImpl autometionHelper = new AutomationHelperImpl();
		String ocEmailProcessqEntry = autometionHelper.verifyEmailDOMS(
				nikeDOMSConnectionDAO, string1, string2, string3,
				NikeDOMSConstants.GET_ASYNC_PROCESS_QUEUE_DETAILS);
		System.out.println("VALIDATION-process Q: " + ocEmailProcessqEntry);
		if (ocEmailProcessqEntry.contains("Error")||
				ocEmailProcessqEntry.contains("Warning")) {
			ocEmailProcessqEntry = autometionHelper
					.verifyEmailDOMS(
							nikeDOMSConnectionDAO,
							string1,
							string2,
							string3,
							NikeDOMSConstants.GET_ASYNC_PROCESS_QUEUE_ARCHIVE_DETAILS_With_Process_Name);
			System.out.println("VALIDATION-process Q Archive: "
					+ ocEmailProcessqEntry);
		}
		return ocEmailProcessqEntry;
	}

	
	public ReqResVO verifyWorkOrderCreatedtedForNikeId(String orderNo,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO, Integer nikeIdCnt) {
		ReqResVO reqResVO = new ReqResVO();
		try {

			nikeDOMSConnectionDAO.getDBValues(orderNo,
					DbValidationConstants.GET_NIKEID_WO_COUNT,
					NikeDOMSConstants.DOMS);
			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikeDOMSConnectionDAO.getRowSet();
			int woCount = crs.size();
			if (woCount == nikeIdCnt) {
				reqResVO.setResXML("Success");
			} else {
				
				reqResVO.setResXML("Error");
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reqResVO;

	}

	public ReqResVO verifyPOCreated(String orderNo,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO,
			Integer purchaceOrderCount) {
		ReqResVO reqResVO = new ReqResVO();
		try {

			nikeDOMSConnectionDAO.getDBValues(orderNo,
					DbValidationConstants.GET_PO_COUNT, NikeDOMSConstants.DOMS);
			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikeDOMSConnectionDAO.getRowSet();
			int poCount = crs.size();
			if (poCount >0) {
				reqResVO.setResXML("Success");
			} else {
				reqResVO.setResXML("Error");
				reqResVO.setComment("PO numbers are not generated");
			}
			System.out.println("PO Count:" + poCount);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reqResVO;

	}

	public int verifyDirtyNodeEntry(
			ArrayList<XmlTagLinesVO> xmlTagLinesVOList,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO, String LineNo) {
		ReqResVO reqResVO = new ReqResVO();
		int iterator = 1;
		int dirtyNodeCount=0;
		String shipNode="";
		String itemId="";
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {
			if (iterator == Integer.parseInt(LineNo)) {

				for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO
						.getXmlTagAttributesVO()) {
					if ("ItemID".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						itemId=xmlTagAttributesVO.getAttributeValue();
					}
					if ("ShipNode".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						shipNode=xmlTagAttributesVO.getAttributeValue();
					}
					

				}
			}
			iterator++;
		}

		try {

			nikeDOMSConnectionDAO.getDBValuesPAC(itemId, shipNode, NikeDOMSConstants.GET_DIRTY_NODE, NikeDOMSConstants.DOMS);
			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikeDOMSConnectionDAO.getRowSet();
			dirtyNodeCount = crs.size();
			

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dirtyNodeCount;

	}

	
	public int getXmlCountFromDOMS(
			NikeDOMSConnectionDAO nikeDomsConnectionDao, String dataKey,
			String executeServicename, String messagePattern, String query) {
		int size=0;
		CachedRowSet crs = null;
		try {

			 crs = RowSetProvider.newFactory().createCachedRowSet();
			nikeDomsConnectionDao.getDbValues( dataKey , executeServicename , "%" + messagePattern + "%",
					query, NikeDOMSConstants.DOMS);
			crs = nikeDomsConnectionDao.getRowSet();
			size = crs.size();
			crs.beforeFirst();
			if (size>0){
				return size;
			}
		}

		catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return size;
	}


}
