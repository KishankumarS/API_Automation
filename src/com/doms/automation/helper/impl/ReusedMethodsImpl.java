package com.doms.automation.helper.impl;

import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.XmlTagAttributesVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.service.PostRequest;
import com.doms.automation.utils.DbValidationConstants;
import com.doms.automation.utils.NikeDOMSApplicationUtils;
import com.doms.automation.utils.NikeDOMSConstants;

public class ReusedMethodsImpl {

	private CachedRowSet crs = null;
	Clob msgClob = null;
	String dbValue = "";

	public ReqResVO compareStrings(String xmlOrderType, String orderType) {

		ReqResVO reqResVO = new ReqResVO();
		reqResVO.setResXML(xmlOrderType.equalsIgnoreCase(orderType) ? "Success" : "Error");
		return reqResVO;
	}

	public String getValueFromCrs(NikeDOMSConnectionDAO nikeDOMSConnectionDAO, String columnName,
			AutomationHelperImpl helperImpl) {

		CachedRowSet crs = null;
		Clob msgClob = null;
		String dbValue = "";

		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikeDOMSConnectionDAO.getRowSet();

			while (crs.next()) {
				msgClob = crs.getClob(columnName);
			}

			if (null != msgClob) {
				dbValue = helperImpl.getStringFromClob(msgClob);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbValue;
	}

	public String getCreateTimeStamp(NikeDOMSConnectionDAO nikeDOMSConnectionDAO, String orderNo) {
		String createTS = "";
		CachedRowSet crs = null;

		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();

			nikeDOMSConnectionDAO.getDBValues(orderNo, DbValidationConstants.GET_CREATETS_TIME, NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();

			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						createTS = crs.getString(1);

					}
				} else {
					while (crs.next()) {
						createTS = crs.getString(1);

					}
				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return createTS;
	}

	public ReqResVO validateAvailableDate(String createts, String availableDate, int minute) {
		ReqResVO reqResVO = new ReqResVO();
		NikeDOMSApplicationUtils nikeDOMSApplicationUtils = new NikeDOMSApplicationUtils();
		availableDate=nikeDOMSApplicationUtils.changeDateFormat(availableDate, "yyyy-MM-dd HH:mm");		
		reqResVO.setResXML(
				availableDate.equals(nikeDOMSApplicationUtils.addMinutesToTime(createts, minute, "yyyy-MM-dd HH:mm"))
						? "Success" : "Error");
		return reqResVO;
	}

	public String getAvailableDateFromYfsTasqQ(NikeDOMSConnectionDAO nikeDOMSConnectionDAO, String orderNo) {
		String availableDate = "";
		CachedRowSet crs = null;

		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();

			nikeDOMSConnectionDAO.getDBValues(orderNo, DbValidationConstants.GET_AVAILABLE_DATE_FROM_YFS_TASK_Q,
					NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();

			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						availableDate = crs.getString(1);

					}
				} else {
					while (crs.next()) {
						availableDate = crs.getString(1);

					}
				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return availableDate;
	}

	public String getAvailableDateFromDBWithProcessName(NikeDOMSConnectionDAO nikeDOMSConnectionDAO, String dataKey,
			String processName,String query) {
		String availableDate = "";
		CachedRowSet crs = null;

		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();

			nikeDOMSConnectionDAO.getDBValues2parametres(processName, dataKey,
					query, NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();

			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						availableDate = crs.getString(1);

					}
				} else {
					while (crs.next()) {
						availableDate = crs.getString(1);

					}
				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return availableDate;
	}

	public String getSingleValueFromCrs(NikeDOMSConnectionDAO nikeDOMSConnectionDAO) throws SQLException {

		crs = nikeDOMSConnectionDAO.getRowSet();
		int size = crs.size();
		crs.beforeFirst();
		if (size > 0) {
			if (size == 1) {
				if (crs.next()) {
					dbValue = crs.getString(1);
				}
			} else {
				while (crs.next()) {
					dbValue = crs.getString(1);
				}
			}
		}
		crs.close();
		return dbValue;

	}
	public Map<String, String> populateAttributeNameMap(ArrayList<XmlTagLinesVO> xmlTagLinesVOList, String lineNo) {
		Map<String,String> map = new LinkedHashMap<String,String>();
		boolean isLineNo;
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {
			isLineNo = false;
			if ("".equals(lineNo)) {
				isLineNo = true;
			}
			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO.getXmlTagAttributesVO()) {
				if (lineNo != null && !isLineNo) {
					if ("LineNo".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {

						isLineNo = (lineNo.equalsIgnoreCase(xmlTagAttributesVO.getAttributeValue())) ? true : false;
					}
				}
				if (isLineNo) {
					map.put(xmlTagAttributesVO.getAttributeName(), xmlTagAttributesVO.getAttributeValue());
				}
			}
		}
			
		return map;
	}
	
	public String getExchangeOrderNoWithOrderNo(String orderNo,NikeDOMSConnectionDAO nikeDOMSConnectionDAO){
		String exchangeOrderNo="";
		try {
			nikeDOMSConnectionDAO.getDBValues(orderNo, DbValidationConstants.GET_EXCHANGE_ORDER_NO_WITH_ORDER_NO, 
					NikeDOMSConstants.DOMS);
			exchangeOrderNo=getSingleValueFromCrs(nikeDOMSConnectionDAO);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return exchangeOrderNo;		
	}
	public List<String> getPrimeLineList(String primeLines) {
		List<String> primeLineList = new ArrayList<String>();
		if (primeLines.length() > 0) {
			for (int l = 0; l < primeLines.length(); l++) {
				primeLineList.add(String.valueOf(primeLines.charAt(l)));				
			}
		}
		return primeLineList;
	}
	public ReqResVO verifyXMLvalue(String xml, String validationInput,String xmlPath,String Successdescription,String Failuredescription,		
			AutomationHelperImpl automationHelperImpl){		
		ReqResVO reqResVO = new ReqResVO();			
		String xmlOrderType = automationHelperImpl 		
				.validateXPathValue("<order>"+xml+"</order>",  xmlPath);		
		ReusedMethodsImpl reusedMethodsImpl = new  ReusedMethodsImpl();				
		reqResVO = reusedMethodsImpl.compareStrings(xmlOrderType,validationInput);		
				
			reqResVO.setComment(reqResVO.getResXML().equalsIgnoreCase("Success")?Successdescription:Failuredescription);				
			return reqResVO;
	}
	public String getQuery(String tablename,String parameter1,String parameter2,String searchField){
		String query="";
		query= "select "+searchField+" from "+tablename+" where "+parameter1+" and "+parameter2;
		return query;
	}
	public void postXmlToApi(String environment, String xml,
			EnvConfigVO envConfigVO,String service,String yOrN,String assertValue) {
		PostRequest postRequest = new PostRequest();
		
		try {
			
			String responseData = postRequest.postXML(environment, 
					xml, service, yOrN,assertValue,envConfigVO);
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Use the following method if you have to fetch one or multiple attributes
	 * from a db table and validate those against some pre-defined values
	 * 
	 */
	public ReqResVO validateOrderCreationInDb(String orderNo, ArrayList<String> expectedAttributeValuesList,
			ArrayList<String> dbColumnNameList, String query, Map<String, String> xlSheetAttributesMap,
			ReusedMethodsImpl reImpl, Map<String, ReqResVO> responseMap, NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {

		ArrayList<String> dbColumnValueList = new ArrayList<>();
		ReqResVO reqResVoToReturn = new ReqResVO();
		int noOfValuesToValidate = 0;
		String expectedValue = "";
		String dbValue = "";

		try {
			noOfValuesToValidate = dbColumnNameList.size();
			nikeDOMSConnectionDAO.getDBValues(orderNo, query, NikeDOMSConstants.DOMS);
			dbColumnValueList = reImpl.getSingleOrMultipleValuesFromCrs(nikeDOMSConnectionDAO, dbColumnNameList,
					reqResVoToReturn, responseMap);

			for (int i = noOfValuesToValidate - 1; i >= 0; i--) {

				/*
				 * Assigning values from list to separate string since dynamic
				 * comparison is not possible
				 */

				dbValue = dbColumnValueList.get(i);
				expectedValue = expectedAttributeValuesList.get(i);

				if (dbValue.equalsIgnoreCase(expectedValue)) {
					reqResVoToReturn.setIsNotSuccess(false);
					reqResVoToReturn.setResXML("Success");
				} else {
					reqResVoToReturn.setResXML("Error");
					reqResVoToReturn.setIsNotSuccess(true);
					reqResVoToReturn.setComment("Exected " + dbColumnNameList.get(i) + " is " + expectedValue + " But "
							+ dbValue + " is present in DB");
					break;
				}
			}
			return reqResVoToReturn;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return reqResVoToReturn;
	}
	

	public ArrayList<String> getSingleOrMultipleValuesFromCrs(NikeDOMSConnectionDAO nikeDOMSConnectionDAO,
			ArrayList<String> columnNameList, ReqResVO reqResVO, Map<String, ReqResVO> responseMap)
			throws SQLException {

		crs = nikeDOMSConnectionDAO.getRowSet();
		int size = crs.size();
		crs.beforeFirst();
		ArrayList<String> columnValueList = new ArrayList<>();
		int i = columnNameList.size();
		int j = 0;
		if (size > 0) {
			crs.next();
			do {
				dbValue = crs.getString(columnNameList.get(j));
				columnValueList.add(j, dbValue.trim());
				i--;
				j++;
			} while (i != 0);

		} else {
			reqResVO.setResXML("Error");
		}

		crs.close();
		return columnValueList;

	}
	public boolean isFeedzai(NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		boolean isFeedzai=false;	
			
			try {
						
				nikeDOMSConnectionDAO.getDBValues(DbValidationConstants.GET_EXTN_CODE_2,
						NikeDOMSConstants.DOMS);				
				isFeedzai=("Feedzai".equalsIgnoreCase(getSingleValueFromCrs(
						nikeDOMSConnectionDAO)))?true:false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return isFeedzai;
		
	}
	public ReqResVO isEntryProcessed(String status,String entryType) {
		ReqResVO reqResVO= new ReqResVO();		
		if(status.trim().contains("New")){
			reqResVO.setResXML("Error");
			reqResVO.setComment(entryType+" did not get processed . Please restart the server and try again ");
		}else if(status.trim().contains("Retry")){
			reqResVO.setResXML("Error");
			reqResVO.setComment(entryType+" did not get processed . The status is retry ");
		}else if(status.trim().contains("Complete")){
			reqResVO.setResXML("Success");
			reqResVO.setComment(entryType+" got processed . The status is Complete ");
			}
		return reqResVO;
	}
		
	
}