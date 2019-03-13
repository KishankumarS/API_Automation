package com.doms.automation.dbvalidations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.WarningQueryVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.utils.DbValidationConstants;
import com.doms.automation.utils.NikeDOMSConstants;


public class VerifyProcessHolds {
	
	public ReqResVO verifyFraudHoldResolvedInDB(String orderNo,
			NikeDOMSConnectionDAO nikedomsconnectiondao){
		ReqResVO reqResVO = new ReqResVO();
		String resultMsg="Error";
		String status="";
		try {
			nikedomsconnectiondao.getDBValues(orderNo,
					DbValidationConstants.GET_FRAUD_HOLD_STATUS.replace("${FraudStatuses}", "'FCI_CHO','FCI_CRO'"), NikeDOMSConstants.DOMS);
			CachedRowSet crs= RowSetProvider.newFactory().createCachedRowSet();
			crs = nikedomsconnectiondao.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if(size>0){
				if(size==1){
					if (crs.next()) {
						status = crs.getString(1);
					}
				}
				else{
					while (crs.next()) {
						status = crs.getString(1);
					}
				}
			}
			
			if(status.equalsIgnoreCase("1300")){
				resultMsg="Success";	
			}
		System.out.println("FraudHold Resolved VALIDATION-in DB:"+ resultMsg);
		reqResVO.setReqXML("FraudHold Resolved validation in DB");
		reqResVO.setResXML(resultMsg);	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return reqResVO;
		
	}
	
	public ReqResVO verifyRemorseHoldResolvedInDB(String orderNo,
			NikeDOMSConnectionDAO nikedomsconnectiondao,Map<String,DbEnvConfig> dbConfigMap){
		ReqResVO reqResVO = new ReqResVO();
		String resultMsg="Fail";
		String status="";
		String enterPriseCode = "";
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();
		try {
			nikedomsconnectiondao.getDBValues(orderNo,
					NikeDOMSConstants.GET_ORDER_DETAILS, NikeDOMSConstants.DOMS);
			CachedRowSet crs= RowSetProvider.newFactory().createCachedRowSet();
			crs = nikedomsconnectiondao.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if(size>0){
				if(size==1){
					if (crs.next()) {
						enterPriseCode = crs.getString("ENTERPRISE_KEY");
					}
				}
				else{
					while (crs.next()) {
						enterPriseCode = crs.getString("ENTERPRISE_KEY");
					}
				}
			}
			if(enterPriseCode.trim().equalsIgnoreCase("NIKEGS")){
				orderNo = automationHelperImpl.getFSOrderNo(orderNo, dbConfigMap);
			}
			
			nikedomsconnectiondao.getDBValues(orderNo,
					DbValidationConstants.GET_REMORSE_HOLD_STATUS, NikeDOMSConstants.DOMS);
			 crs= RowSetProvider.newFactory().createCachedRowSet();
			crs = nikedomsconnectiondao.getRowSet();
			int size1 = crs.size();
			crs.beforeFirst();
			if(size1>0){
				if(size1==1){
					if (crs.next()) {
						status = crs.getString(1);
					}
				}
				else{
					while (crs.next()) {
						status = crs.getString(1);
					}
				}
			}
			
			if(status.equalsIgnoreCase("1300")){
				resultMsg="Success";	
			}
		System.out.println("RemorseHold Resolved VALIDATION-in DB:"+ resultMsg);
		reqResVO.setReqXML("RemorseHold Resolved validation in DB");
		reqResVO.setResXML(resultMsg);	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return reqResVO;
		
	}
	
	public ReqResVO verifyInDOMSProcessQueue(String dataKey,String executeServicename,String query,
			NikeDOMSConnectionDAO nikedomsconnectiondao){
		ReqResVO reqResVO = new ReqResVO();
		String resultMsg="Fail";
	
		try {
			
			CachedRowSet crs= RowSetProvider.newFactory().createCachedRowSet();
			nikedomsconnectiondao.getDbValuesProcessQueue("%"+dataKey+"%", "%"+executeServicename+"%",query , NikeDOMSConstants.DOMS);
			crs = nikedomsconnectiondao.getRowSet();
			int size = crs.size();
			if(size>0){
				resultMsg="Success";
			}
			else{
				ArrayList<String> inputParameters = new ArrayList<String>();
				inputParameters.add(dataKey);
				inputParameters.add(executeServicename);
				WarningQueryVO warningQueryVO = new WarningQueryVO();
				warningQueryVO.setDataBase(NikeDOMSConstants.DOMS);
				warningQueryVO.setInputParameters(inputParameters);
				warningQueryVO.setQuery(query);
				ArrayList<WarningQueryVO> warningQueryVOList= null;
				if(reqResVO.getWarningQueryVOList()!=null && reqResVO.getWarningQueryVOList().size()>0){
					warningQueryVOList = reqResVO.getWarningQueryVOList();	
				}else{
					warningQueryVOList = new ArrayList<WarningQueryVO>();
				}				
				warningQueryVOList.add(warningQueryVO);
				reqResVO.setWarningQueryVOList(warningQueryVOList);
				resultMsg="Warning";
			}
			
			System.out.println("Order Acknowledge Message VALIDATION-process Q: in verifyInDOMSProcessQueue method "+ resultMsg);
			reqResVO.setReqXML("Order Acknowledge Message validation in Async Process Queue");
			reqResVO.setResXML(resultMsg);	
			if("Warning".equalsIgnoreCase(resultMsg)){
				reqResVO.setComment("The entry did not come in extn_async_process Q even after waiting for 20 seconds.So Please check manually.");
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
	
	public ReqResVO verifyFraudChallengeHoldCreated(String orderNo,
			NikeDOMSConnectionDAO nikedomsconnectiondao){
		ReqResVO reqResVO = new ReqResVO();
		String resultMsg="Error";
		String status="";
		try {
			nikedomsconnectiondao.getDBValues(orderNo,
					DbValidationConstants.GET_FRAUD_HOLD_STATUS.replace("${FraudStatuses}", "'CHALLENGE'"), NikeDOMSConstants.DOMS);
			CachedRowSet crs= RowSetProvider.newFactory().createCachedRowSet();
			crs = nikedomsconnectiondao.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if(size>0){
				if(size==1){
					if (crs.next()) {
						status = crs.getString(1);
					}
				}
				else{
					while (crs.next()) {
						status = crs.getString(1);
					}
				}
			}
			
			if(status.equalsIgnoreCase("1100")){
				resultMsg="Success";	
			}
		
		reqResVO.setReqXML("FraudHold Challenge validation in DB");
		reqResVO.setResXML(resultMsg);	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return reqResVO;
		
	}
	
	}
