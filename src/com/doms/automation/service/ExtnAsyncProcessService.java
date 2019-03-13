package com.doms.automation.service;

import java.sql.Clob;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.xml.bind.JAXBException;

import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.helper.impl.ReusedMethodsImpl;
import com.doms.automation.utils.DbValidationConstants;
import com.doms.automation.utils.NikeDOMSApplicationUtils;
import com.doms.automation.utils.NikeDOMSConstants;

public class ExtnAsyncProcessService {
	
	public int getEntryCountInExtnAsyncProcessQAndArchive(NikeDOMSConnectionDAO nikeDOMSConnectionDAO,String parameter1,
			String parameter2){
		int count=0;
	
			
			int size=getEntryCountInDomsTable(nikeDOMSConnectionDAO,parameter1,
					 parameter2, DbValidationConstants.GET_ASYNC_PROCESS_QUEUE_DETAILS_COUNT);		
		
			count=size;
			
			if(count<1){
			
			size =getEntryCountInDomsTable(nikeDOMSConnectionDAO,parameter1,
					 parameter2, DbValidationConstants.GET_ASYNC_PROCESS_QUEUE_ARCHIVE_DETAILS_COUNT);	
			if(size>0){
			count= count+size;}
			
			}
		
		return count;
	}
	
	public String getprocessingKey(String executiveServiceName,String datakey,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO){
		String processingKey="";
	
			try {
				CachedRowSet crs= RowSetProvider.newFactory().createCachedRowSet();
				nikeDOMSConnectionDAO.getDBValues2parametres(executiveServiceName,datakey,
						NikeDOMSConstants.GET_PROCESSING_KEY_FROM_ASYNC_PROCESS_Q, NikeDOMSConstants.DOMS);
				crs = nikeDOMSConnectionDAO.getRowSet();
				
				int size = crs.size();
				crs.beforeFirst();
				if (size > 0) {
					if (size == 1) {
						if (crs.next()) {
							processingKey=crs.getString(1);
							

						}
					} else {
						while (crs.next()) {
							processingKey=crs.getString(1);
							

						}
					}
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return processingKey;
	
		
	}

	

	public void postExtnAsyncProcessQXmlToApi(String environment,
			String processingKey, String executeExtnAsyncReqSvc,
			EnvConfigVO envConfigVO, String xml) {
		PostRequest postRequest = new PostRequest();
		postRequest.post(environment, xml, 
				executeExtnAsyncReqSvc, "Y",
				envConfigVO);
		
	}
	
	public String getMessageXmlFromQOrArchive(String executiveServiceName , String dataKey,
			String messagePattern,NikeDOMSConnectionDAO nikeDOMSConnectionDAO){
		String xml="";
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();
		CachedRowSet crs = null;
		 Clob msgClobDoms = null;
		 try{
		crs= RowSetProvider.newFactory().createCachedRowSet();
		nikeDOMSConnectionDAO.getDbValues(executiveServiceName,dataKey,"%"+messagePattern+"%"
				,DbValidationConstants.GET_MESSAGE_XML_ASYNC_Q , NikeDOMSConstants.DOMS);
		crs = nikeDOMSConnectionDAO.getRowSet();
		
		int size = crs.size();
		if(size>0){
			if (size == 1) {
				if (crs.next()) {
					msgClobDoms =  crs.getClob(1);
					xml = automationHelperImpl.getStringFromClob(msgClobDoms);
				
					

				}
			} else {
				while (crs.next()) {
					msgClobDoms =  crs.getClob(1);
					xml = automationHelperImpl.getStringFromClob(msgClobDoms);
					

				}
			}
		}else{
			crs.close();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			nikeDOMSConnectionDAO.getDbValues(executiveServiceName,dataKey,"%"+messagePattern+"%"
					,DbValidationConstants.GET_MESSAGE_XML_ASYNC_Q_ARCHIVE , NikeDOMSConstants.DOMS);		
			crs = nikeDOMSConnectionDAO.getRowSet();
			size = crs.size();
			crs.beforeFirst();
			if(size > 0) {
				if (size == 1) {
					if (crs.next()) {
						msgClobDoms =  crs.getClob(1);
						xml = automationHelperImpl.getStringFromClob(msgClobDoms);
						

					}
				} else {
					while (crs.next()) {
						msgClobDoms =  crs.getClob(1);
						xml = automationHelperImpl.getStringFromClob(msgClobDoms);
						

					}
				}
			}else{
				xml="Error";
			}
			
			
		}
		crs.close();
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(crs!=null){
					try {
						crs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		 
		return xml;
		
	}
	public String getMessageXmlFromQOrArchiveWithoutDataKey(String executiveServiceName , String dataKey,
			String messagePattern,NikeDOMSConnectionDAO nikeDOMSConnectionDAO){
		String xml="";
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();
		CachedRowSet crs = null;
		 Clob msgClobDoms = null;
		 try{
		crs= RowSetProvider.newFactory().createCachedRowSet();
		nikeDOMSConnectionDAO.getDBValues2parametres(executiveServiceName,"%"+dataKey+"%"+messagePattern+"%"
				,DbValidationConstants.GET_MESSAGE_XML_ASYNC_Q_WITHOUT_DATAKEY , NikeDOMSConstants.DOMS);
		crs = nikeDOMSConnectionDAO.getRowSet();
		
		int size = crs.size();
		if(size>0){
			if (size == 1) {
				if (crs.next()) {
					msgClobDoms =  crs.getClob(1);
					xml = automationHelperImpl.getStringFromClob(msgClobDoms);
				
					

				}
			} else {
				while (crs.next()) {
					msgClobDoms =  crs.getClob(1);
					xml = automationHelperImpl.getStringFromClob(msgClobDoms);
					

				}
			}
		}else{
			crs.close();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			nikeDOMSConnectionDAO.getDBValues2parametres(executiveServiceName,"%"+dataKey+"%"+messagePattern+"%"
					,DbValidationConstants.GET_MESSAGE_XML_ASYNC_Q_ARCHIVE_WITHOUT_DATAKEY , NikeDOMSConstants.DOMS);		
			crs = nikeDOMSConnectionDAO.getRowSet();
			size = crs.size();
			crs.beforeFirst();
			if(size > 0) {
				if (size == 1) {
					if (crs.next()) {
						msgClobDoms =  crs.getClob(1);
						xml = automationHelperImpl.getStringFromClob(msgClobDoms);
						

					}
				} else {
					while (crs.next()) {
						msgClobDoms =  crs.getClob(1);
						xml = automationHelperImpl.getStringFromClob(msgClobDoms);
						

					}
				}
			}else{
				xml="Error";
			}
			
			
		}
		crs.close();
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(crs!=null){
					try {
						crs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		 
		return xml;
		
	}
	public String getMessageXmlFromQOrArchiveWithProcessName(String processName , String dataKey,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO){
		String xml="Error";
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();
		CachedRowSet crs = null;
		 Clob msgClobDoms = null;
		 try{
		crs= RowSetProvider.newFactory().createCachedRowSet();
		nikeDOMSConnectionDAO.getDBValues2parametres(dataKey,processName
				,DbValidationConstants.GET_MESSAGE_XML_ASYNC_Q_WITH_PROCESSNAME , NikeDOMSConstants.DOMS);
		crs = nikeDOMSConnectionDAO.getRowSet();
		
		int size = crs.size();
		if(size>0){
			if (size == 1) {
				if (crs.next()) {
					msgClobDoms =  crs.getClob(1);
					xml = automationHelperImpl.getStringFromClob(msgClobDoms);
				
					

				}
			} else {
				while (crs.next()) {
					msgClobDoms =  crs.getClob(1);
					xml = automationHelperImpl.getStringFromClob(msgClobDoms);
					

				}
			}
		}else{
			crs.close();
			crs= RowSetProvider.newFactory().createCachedRowSet();
			nikeDOMSConnectionDAO.getDBValues2parametres(dataKey , processName
					,DbValidationConstants.GET_MESSAGE_XML_ASYNC_Q_ARCHIVE_WITH_PROCESSNAME , NikeDOMSConstants.DOMS);	
			crs = nikeDOMSConnectionDAO.getRowSet();
			size = crs.size();
			crs.beforeFirst();
			if(size > 0) {
				if (size == 1) {
					if (crs.next()) {
						msgClobDoms =  crs.getClob(1);
						xml = automationHelperImpl.getStringFromClob(msgClobDoms);
						

					}
				} else {
					while (crs.next()) {
						msgClobDoms =  crs.getClob(1);
						xml = automationHelperImpl.getStringFromClob(msgClobDoms);
						

					}
				}
			}else{
				xml="Error";
			}
			
			
		}
		crs.close();
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(crs!=null){
					try {
						crs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		 
		return xml;
		
	}

	public int getEntryCountInDomsTable(NikeDOMSConnectionDAO nikeDOMSConnectionDAO,String parameter1,
			String parameter2,String query){
		int count=0;
		try {
			nikeDOMSConnectionDAO.getDBValues2parametres(parameter1,parameter2,
					query, NikeDOMSConstants.DOMS); 
					
			CachedRowSet crs= RowSetProvider.newFactory().createCachedRowSet();
			crs = nikeDOMSConnectionDAO.getRowSet();
			count = crs.size();			
			crs.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public int updateFieldsInQTable3Parameters(NikeDOMSConnectionDAO nikeDOMSConnectionDAO, String query, String parameter1,
			String parameter2,String parameter3) {
		int result = nikeDOMSConnectionDAO.updateDB3Parameters(parameter1, parameter2,parameter3,
				query, NikeDOMSConstants.DOMS);
		return result;

	}

	public void updateFieldsInQTable(NikeDOMSConnectionDAO nikeDOMSConnectionDAO,
			String query, String parameter1, String parameter2) {
		nikeDOMSConnectionDAO.updateDB2Parameters(parameter1, parameter2,
				query, NikeDOMSConstants.DOMS);
		
	}
	public void updateFieldsInQTable(NikeDOMSConnectionDAO nikeDOMSConnectionDAO,
			String query, String parameter1) {
		nikeDOMSConnectionDAO.updateDB2Parameters(parameter1,
				query, NikeDOMSConstants.DOMS);
		
	}
	public ReqResVO checkFraudEntryInQorArchive(NikeDOMSConnectionDAO nikeDOMSConnectionDAO, String datakey,
			String executeServiceName,String searchParameter) {
		ReusedMethodsImpl reusedMethodsImpl= new ReusedMethodsImpl();
		ReqResVO reqResVO = new ReqResVO();
		CachedRowSet crs = null;
		int size=0;
		boolean isPresent=false;
		String date="";
		try {
			nikeDOMSConnectionDAO.getDBValues(reusedMethodsImpl.getQuery(NikeDOMSConstants.EXTN_ASYNC_PROCESS_Q,
							"executeservicename='"+executeServiceName+"'", "data_Key='"+datakey+"'", searchParameter),
					NikeDOMSConstants.DOMS);
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikeDOMSConnectionDAO.getRowSet();
			size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				isPresent=true;
				if (size == 1) {
					if (crs.next()) {
						date=crs.getString(1);
					}
					
				}else{
					while (crs.next()) {
						date=crs.getString(1);
					}
				}
			}else{
				nikeDOMSConnectionDAO.getDBValues(reusedMethodsImpl.getQuery(NikeDOMSConstants.EXTN_ASYNC_PROCESS_Q_ARCHIVE,
						"executeservicename='"+executeServiceName+"'", "data_Key='"+datakey+"'", searchParameter),
				NikeDOMSConstants.DOMS);
		crs = RowSetProvider.newFactory().createCachedRowSet();
		crs = nikeDOMSConnectionDAO.getRowSet();
		size = crs.size();
		crs.beforeFirst();
		if (size > 0) {
			isPresent=true;
			if (size == 1) {
				if (crs.next()) {
					date=crs.getString(1);
				}
				
			}else{
				while (crs.next()) {
					date=crs.getString(1);
				}
			}
		}	
			}
			
			reqResVO.setResXML(isPresent?"Success":"Error");
			reqResVO.setRetVariable(date);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reqResVO;
	}

	public boolean isSuccess(String date) {
		NikeDOMSApplicationUtils nikeDOMSApplicationUtils = new NikeDOMSApplicationUtils();
		int i=nikeDOMSApplicationUtils.compareDates(date, nikeDOMSApplicationUtils.
				generateUniqueNo("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
		return i<0?true:false;
	}
	
}
