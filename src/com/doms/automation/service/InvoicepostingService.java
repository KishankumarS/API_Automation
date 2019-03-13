package com.doms.automation.service;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.bean.XmlTagAttributesVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.helper.impl.ReusedMethodsImpl;
import com.doms.automation.utils.DbValidationConstants;
import com.doms.automation.utils.NikeDOMSConstants;

public class InvoicepostingService {
	
public ReqResVO verifyOrderType(String xmlFormat, String orderType,
		AutomationHelperImpl automationHelperImpl){
	ReqResVO reqResVO = new ReqResVO();	
	String xmlOrderType = automationHelperImpl 
			.validateXPathValue(xmlFormat,  "/order/orderType");
	ReusedMethodsImpl reusedMethodsImpl = new  ReusedMethodsImpl();		
	reqResVO = reusedMethodsImpl.compareStrings(xmlOrderType,orderType);
	reqResVO.setComment(reqResVO.getResXML().equalsIgnoreCase("Success")?
			"The ordertype is properly verified":"Error!!The ordertype in xml is "+xmlOrderType);
	return reqResVO;
}
public String getOrderType(ArrayList<XmlTagLinesVO> xmlTagLinesVOList) {
	String nOrderType="";
	String division="";
	String orderType="";
	for(XmlTagAttributesVO xmlTagAttributesVO:xmlTagLinesVOList.
			get(0).getXmlTagAttributesVO() ){
		if ("Division".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
			if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
				division = xmlTagAttributesVO.getAttributeValue();
				
			}
		}
			if ("OrderType".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
				if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
					nOrderType = xmlTagAttributesVO.getAttributeValue();
					
				}

		}
	}
	if(NikeDOMSConstants.STANDARD.equalsIgnoreCase(nOrderType)){			
		orderType = NikeDOMSConstants.doms;
	}
	if(NikeDOMSConstants.STORE.equalsIgnoreCase(nOrderType)&&
			"600".equalsIgnoreCase(division)){			
		orderType = NikeDOMSConstants.MPOS;
	}
	if(NikeDOMSConstants.STORE.equalsIgnoreCase(nOrderType)&&
			"700".equalsIgnoreCase(division)){			
		orderType = NikeDOMSConstants.POS;
	}
	if(NikeDOMSConstants.POS.equalsIgnoreCase(nOrderType)&&
			"600".equalsIgnoreCase(division)){			
		orderType = NikeDOMSConstants.MPOS;
	}
	if(NikeDOMSConstants.POS.equalsIgnoreCase(nOrderType)&&
			"700".equalsIgnoreCase(division)){			
		orderType = NikeDOMSConstants.POS;
	}
	
	return orderType;
}

public String getDataKey(NikeDOMSConnectionDAO nikeDOMSConnectionDAO, String financialSystem,
		String invNo, String messagePattern) {
	if (DbValidationConstants.FIN03.equalsIgnoreCase(financialSystem)) {
		invNo = getSeqNo(nikeDOMSConnectionDAO,invNo,messagePattern);
	}
	return invNo;
}

private String getSeqNo(NikeDOMSConnectionDAO nikeDOMSConnectionDAO, String invNo, String messagePattern) {
	CachedRowSet crs = null;
	String seqNo="";
	 try{
			crs= RowSetProvider.newFactory().createCachedRowSet();
			nikeDOMSConnectionDAO.getDBValues2parametres(invNo,messagePattern
					,DbValidationConstants.GET_EXTN_SEQUENCE_NUMBER , NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if(size > 0) {
				if (size == 1) {
					if (crs.next()) {
						seqNo = crs.getString(1);
				}
				}else{
					while (crs.next()) {
						seqNo = crs.getString(1);
					}
				}
			}
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
	return seqNo;
}
public String getStoreId(ArrayList<XmlTagLinesVO> xmlTagLinesVOList,String lineNo) {
	String storeId="";
	String lineType="";
	for(XmlTagAttributesVO xmlTagAttributesVO:xmlTagLinesVOList.
			get(Integer.parseInt(lineNo)-1).getXmlTagAttributesVO() ){
		if ("Mpos_StoreID".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
			if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
				storeId = xmlTagAttributesVO.getAttributeValue();
				
			}
		}
		if ("LineType".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
			if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
				lineType = xmlTagAttributesVO.getAttributeValue();
				
			}
		}
		
	}
	//inline-150 
		// nikeid-152 
		
	if(NikeDOMSConstants.INLINE.equalsIgnoreCase(lineType)){
		storeId="150";
	}
	if(NikeDOMSConstants.NIKEID.equalsIgnoreCase(lineType)){
		storeId="152";
	}
	
	return storeId;
}
public ReqResVO verifyStoreId(String xmlFormat, String storeId, AutomationHelperImpl automationHelperImpl) {
	ReqResVO reqResVO = new ReqResVO();
	String xmlStoreNo = automationHelperImpl 
			.validateXPathValue(xmlFormat,  "/order/store/storeNo");
	ReusedMethodsImpl reusedMethodsImpl = new  ReusedMethodsImpl();		
	reqResVO = reusedMethodsImpl.compareStrings(xmlStoreNo,storeId);
	reqResVO.setComment(reqResVO.getResXML().equalsIgnoreCase("Success")?
			"The ordertype is properly verified":"Error!!The ordertype in xml is "+xmlStoreNo);
	return reqResVO;
}
public ReqResVO verifyOrderlinesinXML(String xmlFormat, String validationInput,		
		AutomationHelperImpl automationHelperImpl){		
	ReqResVO reqResVO = new ReqResVO();			
	String xmlOrderType = automationHelperImpl 		
			.validateXPathValue("<order>"+xmlFormat+"</order>",  "/order/orderLines[2]/lineType");		
	ReusedMethodsImpl reusedMethodsImpl = new  ReusedMethodsImpl();				
	reqResVO = reusedMethodsImpl.compareStrings(xmlOrderType,validationInput);		
	reqResVO.setComment(reqResVO.getResXML().equalsIgnoreCase("Success")?		
			"Shipping charge sent as separate line item in Sales & Tax Message to RFS is verified":"Error!! Shipping charge sent as in single line item in Sales & Tax Message to RFS");		
	return reqResVO;		
}		
public ReqResVO verifyLastnameinXML(String xmlFormat, String validationInput,		
		AutomationHelperImpl automationHelperImpl){		
	ReqResVO reqResVO = new ReqResVO();			
			
	String g = xmlFormat.replace("&lt;", "<").replace("&gt;", ">");		
	String result = g.substring(g.indexOf("<Order"), g.indexOf("</Data>"));		
	System.out.println(result);		
	String xmlOrderType = automationHelperImpl 		
			.validateXPathValue(result,  "/Order/PersonInfoBillTo/@LastName");		
	System.out.println(xmlOrderType+"  -->xml output value");		
	ReusedMethodsImpl reusedMethodsImpl = new  ReusedMethodsImpl();				
	reqResVO = reusedMethodsImpl.compareStrings(xmlOrderType,validationInput);		
	reqResVO.setComment(reqResVO.getResXML().equalsIgnoreCase("Success")?		
			"Latname found in Message XML":"Error!! Latname not found in Message XML");		
	return reqResVO;		
}		
public ReqResVO verifyStoreIDXMLvalue(String xmlFormat, String validationInput,		
		AutomationHelperImpl automationHelperImpl){		
	ReqResVO reqResVO = new ReqResVO();			
	String xmlOrderType = automationHelperImpl 		
			.validateXPathValue("<order>"+xmlFormat+"</order>",  "/order/store/storeNo");		
	ReusedMethodsImpl reusedMethodsImpl = new  ReusedMethodsImpl();				
	reqResVO = reusedMethodsImpl.compareStrings(xmlOrderType,validationInput);		
			
		reqResVO.setComment(reqResVO.getResXML().equalsIgnoreCase("Success")?		
				"StoreID is verified and 0150 is getting in XML":"Error!! StoreID is not updated with 0150");				
		return reqResVO;
}
public ReqResVO verifyDeferredsaleentry(String xmlFormat, String validationInput,		
		AutomationHelperImpl automationHelperImpl){		
		ReqResVO reqResVO = new ReqResVO();	
			
		reqResVO.setComment(xmlFormat.contains("DEFERRED_SALE")?		
				"Entry is verified in both Async_Process_Q/ASYNC_PROCESS_QUEUE_Archive, Entry found":"Error!! Entry is verified in both Async_Process_Q/ASYNC_PROCESS_QUEUE_Archive, Entry not found");				
		return reqResVO;
}
public boolean isMessageType(String messagePattern,String messageType) {
	boolean isInfo = messageType.equalsIgnoreCase(messagePattern)?
			true:false;
	return isInfo;
}

	public ReqResVO verifyTenderTypeinXML(String xmlFormat,
			AutomationHelperImpl automationHelperImpl,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO,
			TestCaseDataVO testCaseDataVO,String type) {
		ReqResVO reqResVO = new ReqResVO();
		String validationInput = getTenderTypeByPaymentType(
				nikeDOMSConnectionDAO, testCaseDataVO,type);
		xmlFormat = "<order>" + xmlFormat + "</order>";
		System.out.println("xmlFormat-->" + xmlFormat);
		if ("".equalsIgnoreCase(validationInput)) {
			reqResVO.setResXML("Error");
			reqResVO.setComment("Check on the EXTN_MASTER_CODES table for EXTN_CODE_4 value of the corresponding payment type!");
			return reqResVO;
		} else {

			String xmlTenderType = automationHelperImpl.validateXPathValue(
					xmlFormat, "/order/paymentMethods/tenderType");
			System.out.println(xmlTenderType + "  -->xml output value");
			ReusedMethodsImpl reusedMethodsImpl = new ReusedMethodsImpl();
			reqResVO = reusedMethodsImpl.compareStrings(xmlTenderType,
					validationInput);
			reqResVO.setComment(reqResVO.getResXML()
					.equalsIgnoreCase("Success") ? "TenderType value in XML matched with the EXTN_MASTER_CODES table value"
					: "Error!! Mismatch in the TenderType value on the xml and EXTN_MASTER_CODES table value");
			return reqResVO;
		}

	}
	
	

	public String getTenderTypeByPaymentType(
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO,
			TestCaseDataVO testCaseDataVO, String type) {
		
		String paymentType = "";
		String creditCardType = "";
		ArrayList<XmlTagLinesVO> xmlTagLinesVOList = testCaseDataVO
				.getXmlTagLinesVOList();
		for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVOList.get(0)
				.getXmlTagAttributesVO()) {

			if ("PaymentType".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
					paymentType = xmlTagAttributesVO.getAttributeValue();

				}

			}
			if ("CreditCardType".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
					creditCardType = xmlTagAttributesVO.getAttributeValue();

				}

			}

		}
		if ("FINAL_SALE".equalsIgnoreCase(type)) {
			return "DLVRD";
		} else {
			return getTenderTypeFromDB(nikeDOMSConnectionDAO,
					paymentType, creditCardType);
		}
		 
	}

	private String getTenderTypeFromDB(
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO, String paymentType,
			String creditCardType) {

		CachedRowSet crs = null;
		String tenderType = "";
		String query = DbValidationConstants.GET_EXTN_CODE_4;
		String parm1 = "TenderType";
		String parm2 = paymentType;
		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();

			if ("CREDIT_CARD".equalsIgnoreCase(paymentType)) {
				parm1 = creditCardType;
				query = DbValidationConstants.GET_EXTN_CODE_4_CREDITCARD;
			} else if ("GIFT_CERTIFICATE".equalsIgnoreCase(paymentType)) {
				query = DbValidationConstants.GET_EXTN_CODE_4_GIFTCARD;
			}

			nikeDOMSConnectionDAO.getDBValues2parametres(parm1, parm2, query,
					NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						tenderType = crs.getString(1);
					}
				} else {
					while (crs.next()) {
						tenderType = crs.getString(1);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (crs != null) {
				try {
					crs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return tenderType;
	}
	
}
