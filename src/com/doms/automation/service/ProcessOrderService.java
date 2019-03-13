package com.doms.automation.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.OrderLineVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.jaxbclasses.getorderdetails.GetOrderDetails;
import com.doms.automation.jaxbclasses.processorder.ProcessOrder;
import com.doms.automation.marshaller.impl.GetOrderDetailsMarshaller;
import com.doms.automation.marshaller.impl.ProcessOrderMarshaller;
import com.doms.automation.utils.DbValidationConstants;
import com.doms.automation.utils.NikeDOMSApplicationUtils;
import com.doms.automation.utils.NikeDOMSConstants;
import com.sun.rowset.CachedRowSetImpl;

public class ProcessOrderService {
	String responseData = "Success";

	PostRequest postReq = new PostRequest();
	NikeDOMSConnectionDAO nikedomsconnectiondao;
	CachedRowSet crs;
	String environment;
	String orderNo;
	String enterpriseCode;
	String orderHeaderKey;
	String exchangeOrderNo;
	/**
	 * @return the orderHeaderKey
	 */
	
	public String getOrderHeaderKey() {
		return orderHeaderKey;
	}

	public String getExchangeOrderNo() {
		return exchangeOrderNo;
	}

	public void setExchangeOrderNo(String exchangeOrderNo) {
		this.exchangeOrderNo = exchangeOrderNo;
	}

	boolean isNikeGS;
	NikeDOMSApplicationUtils nikeDOMSApplicationUtils;
	AutomationHelperImpl automationHelperImpl;
	Map<String, DbEnvConfig> dbConfigMap;
	public ProcessOrderService(String environment,
			NikeDOMSConnectionDAO nikedomsconnectiondao, String orderNo,
			ArrayList<XmlTagLinesVO> xmlTagLinesVOList, boolean isNikeGS,
			Map<String, DbEnvConfig> dbConfigMap,String exchangeOrderNo) {
		
		this.environment = environment;
		this.orderNo = orderNo;	
		this.isNikeGS=isNikeGS;
		this .dbConfigMap = dbConfigMap; 
		this.nikedomsconnectiondao = nikedomsconnectiondao;
		this.exchangeOrderNo = exchangeOrderNo;
		nikeDOMSApplicationUtils = new NikeDOMSApplicationUtils();
		automationHelperImpl = new AutomationHelperImpl();
		enterpriseCode = automationHelperImpl
				.getEnterpriseCode(xmlTagLinesVOList);
	}

	public void setEnterpriseCode() {
		try {
			nikedomsconnectiondao.getDBValues(orderNo,
					NikeDOMSConstants.orderHeaderKeyQuerry,
					NikeDOMSConstants.DOMS);
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikedomsconnectiondao.getRowSet();

			if (crs.next()) {
				enterpriseCode = crs.getString(1);
				orderHeaderKey = crs.getString(2);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String getOrderNo() {
		String orderNum="";
			if(isNikeGS){
				AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();
				try {
					orderNum = automationHelperImpl.getFSOrderNo(orderNo, dbConfigMap);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{			
				orderNum=exchangeOrderNo!=null?
						exchangeOrderNo:orderNo;			
			}
			return orderNum;
		}

	
	
	public String getOrderDetails(String ordNo, String orderType,
			EnvConfigVO envConfigVO) throws JAXBException {
		GetOrderDetails order = new GetOrderDetails();
		String headerKey = "";
		String xmlString = "";
		if (orderType.equals("Sales")) {
			order.setDocumentType("0001");
		} else if (orderType.equals("Return")) {
			order.setDocumentType("0003");
		}
		try {
			nikedomsconnectiondao.getDBValues(ordNo,
					NikeDOMSConstants.orderHeaderKeyQuerry,
					NikeDOMSConstants.DOMS);
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikedomsconnectiondao.getRowSet();

			if (crs.next()) {
				headerKey = crs.getString(2);
			}
			order.setOrderHeaderKey(headerKey);
			GetOrderDetailsMarshaller orderMarshaller = new GetOrderDetailsMarshaller(
					order);

			String orderDetailsXml = orderMarshaller.convertJaxbObjectToXML();
System.out.println(orderDetailsXml);
			HttpResponse response = postReq.post(environment, orderDetailsXml,
					"getOrderDetails", "N", envConfigVO);
			HttpEntity r_entity = response.getEntity();
			xmlString = EntityUtils.toString(r_entity);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlString;
	}
	public String getOrderStatus(String ordNo, String orderType,
			EnvConfigVO envConfigVO) throws JAXBException {
		String xmlString = getOrderDetails(ordNo, orderType, envConfigVO);
		String Status = "";
		System.out.println(xmlString);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = factory.newDocumentBuilder();
			InputSource inStream = new InputSource(new StringReader(xmlString));

			Document doc = db.parse(inStream);

			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xpath = xFactory.newXPath();

			Status = (String) xpath.evaluate("/Order/@Status", doc,
					XPathConstants.STRING);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Status;
	}
	
	public boolean checkIfTotalAmountEqualToCollectedAmount(NikeDOMSConnectionDAO nikeDOMSConnectionDAO2,
			String orderNo2, AutomationHelperImpl automationHelperImpl2) {
		String amountCollected="",totalAmount="";
		try {
			nikeDOMSConnectionDAO2.getDBValues(orderNo2,
					DbValidationConstants.GET_YFS_ORDER_INVOICE_AMOUNTS,
					NikeDOMSConstants.DOMS);
		
		crs = RowSetProvider.newFactory().createCachedRowSet();
		crs = nikeDOMSConnectionDAO2.getRowSet();
		int size = crs.size();
		crs.beforeFirst();
		if (size > 0) {
			if (size == 1) {
				if (crs.next()) {
					amountCollected = crs.getString(1);
					totalAmount= crs.getString(2);
				}
			} else {
				while (crs.next()) {
					amountCollected = crs.getString(1);
					totalAmount= crs.getString(2);

				}
			}

		}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return amountCollected.equals(totalAmount)?true:false;
	}



	public ReqResVO postData(String xml, String apiName, String isFlow,
			String assertion, EnvConfigVO envConfigVO) {
		ReqResVO reqResVO = new ReqResVO();
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();

		try {
			responseData = postReq.postXML(environment, xml, apiName, isFlow,
					assertion, envConfigVO);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		reqResVO.setReqXML(xml);
		reqResVO.setResXML(responseData);
		if (responseData.contains("ErrorDescription")) {
			reqResVO.setComment(automationHelperImpl
					.getErrorDescription(responseData));
		}

		return reqResVO;
	}


	public ReqResVO shipPGC(String shipmentXml, EnvConfigVO envConfigVO)
			throws JAXBException {

		ReqResVO reqResVO = new ReqResVO();
		reqResVO = this.postData(shipmentXml, "SplitGCResponseMessage_svc",
				"Y", "<Order", envConfigVO);
		return reqResVO;
	}
	


	public ReqResVO POSentToWarehouseNew(String POSentToWarehouseXM,
			EnvConfigVO envConfigVO) {
		ReqResVO reqResVO = new ReqResVO();

		reqResVO = this.postData(POSentToWarehouseXM,
				"ChangeOrderStatus_DSV_svc", "Y", "<OrderStatusChange",
				envConfigVO);
		return reqResVO;
	}
	
			public ReqResVO CreateJapanInvoice(String createJapaninvoicexml,
				EnvConfigVO envConfigVO) {
			
		ReqResVO reqResVO = new ReqResVO();

		reqResVO = this.postData(createJapaninvoicexml,
				"Create_Info_Order_Invoice_svc", "Y", "<OrderInvoice",
				envConfigVO);
		return reqResVO;
	}


	public ReqResVO POAcknowledge(OrderLineVO orderLineVO,
			EnvConfigVO envConfigVO) {
		ReqResVO reqResVO = new ReqResVO();
		String POAcknowledgeXML = "<OrderStatusChange  BaseDropStatus=\"1100.100\" DocumentType=\"0005\" EnterpriseCode='"
				+ enterpriseCode
				+ "' "
				+ "OrderNo='"
				+ orderLineVO.getPONumber()
				+ "' TransactionId=\"PO_INTERMEDIATE_UPDATES.0005.ex\" ><OrderLines><OrderLine BaseDropStatus=\"1100.100\" "
				+ "Quantity='"
				+ orderLineVO.getOrderQuantity()
				+ "' ReleaseNo=\"\" OrderLineKey='"
				+ orderLineVO.getOrderLineKey()
				+ "'/></OrderLines></OrderStatusChange> ";
		System.out.println("The request xml is ...." + POAcknowledgeXML);
		reqResVO = this.postData(POAcknowledgeXML,
				"ChangeOrderStatus_DSVPO_svc", "Y",
				"BaseDropStatus=\"1100.100\"", envConfigVO);
		return reqResVO;
	}

	

	public ReqResVO authorizePayment(EnvConfigVO envConfigVO)
			throws JAXBException {
		ProcessOrder processOrder = new ProcessOrder();
		if(orderHeaderKey!=null){
			
		}else{
			setEnterpriseCode();
		}
		processOrder.setOrderHeaderKey(orderHeaderKey);
		ProcessOrderMarshaller processOrderMarshaller = new ProcessOrderMarshaller(
				processOrder);
		String authorizeXml = processOrderMarshaller.convertJaxbObjectToXML();
		ReqResVO reqResVO = new ReqResVO();

		reqResVO = this.postData(authorizeXml, "requestCollection", "N",
				"<Order", envConfigVO);
		return reqResVO;
	}
	public ReqResVO PaymentSettlementWithTriggeringAgents(EnvConfigVO envConfigVO)
			throws JAXBException, InterruptedException {
		
		ReqResVO reqResVO = new ReqResVO();
		automationHelperImpl.triggerAgent(environment, "PAYMENT_COLLECTION.0001."+enterpriseCode, envConfigVO);
		Thread.sleep(20000);
		automationHelperImpl.triggerAgent(environment, "PAYMENT_EXECUTION", envConfigVO);
		Thread.sleep(20000);
		automationHelperImpl.triggerAgent(environment, "PAYMENT_COLLECTION.0001."+enterpriseCode, envConfigVO);
		Thread.sleep(20000);
		reqResVO.setResXML("Success");
		return reqResVO;
	}


	public ReqResVO applyDiscount(String discountXMl, EnvConfigVO envConfigVO)
			throws InvalidFormatException, IOException {
		ReqResVO reqResVO = new ReqResVO();
		NikeDOMSApplicationUtils utils = new NikeDOMSApplicationUtils();
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();
		String orderModifyCSRURL = envConfigVO.getApplyDiscounturl();

		/*
		 * String response = postReq.postSOAPReq(environment, discountXMl,
		 * orderModifyCSRURL);
		 */
		String response = postReq.postSOAPReqSecured(environment, discountXMl,
				orderModifyCSRURL);
		if (utils.isResponseSuccess(response)) {
			reqResVO.setReqXML(discountXMl);
			reqResVO.setResXML("Success");
		} else {
			reqResVO.setReqXML(discountXMl);
			reqResVO.setResXML("Error");
			reqResVO.setComment(automationHelperImpl
					.getErrorDescription(response));
		}

		return reqResVO;
	}

	


	public ReqResVO getStatusCount(NikeDOMSConnectionDAO nikeDOMSConnectionDAO,
			String env, String key, String statusCOde)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ReqResVO reqResVO = new ReqResVO();
		String response = "Success";
		int count = 0;
		try {
			CachedRowSet crs = new CachedRowSetImpl();

			String query = NikeDOMSConstants.getStatusLine.replace("statusCD",
					statusCOde);
			nikeDOMSConnectionDAO.getDBValues(key, query,
					NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						count = crs.getInt(1);
					}
				} else {
					while (crs.next()) {
						count = crs.getInt(1);
					}
				}

			}

			if (count == 0) {
				response = "Error! dbcount is " + count;

			}
			reqResVO.setReqXML(query);
			reqResVO.setResXML(response);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reqResVO;
	}

	public List<OrderLineVO> getOrderLineAttrList(String environment,
			String orderNo) throws SQLException, ClassNotFoundException {
		List<OrderLineVO> orderLineList = new ArrayList<OrderLineVO>();

		OrderLineVO orderLineVO;

		nikedomsconnectiondao.getDBValues(orderNo,
				NikeDOMSConstants.orderLineQuery, NikeDOMSConstants.DOMS);
		crs = nikedomsconnectiondao.getRowSet();
		int size = crs.size();
		crs.beforeFirst();
		if (size > 0) {
			if (size == 1) {
				if (crs.next()) {
					orderLineVO = new OrderLineVO();
					orderLineVO.setPONumber(crs.getString(1));
					orderLineVO.setOrderLineKey(crs.getString(2));
					orderLineVO.setOrderHeaderKey(crs.getString(3));
					orderLineVO.setOrderQuantity(crs.getString(4));
					orderLineList.add(orderLineVO);
				}
			} else {
				while (crs.next()) {
					orderLineVO = new OrderLineVO();
					orderLineVO.setPONumber(crs.getString(1));
					orderLineVO.setOrderLineKey(crs.getString(2));
					orderLineVO.setOrderHeaderKey(crs.getString(3));
					orderLineVO.setOrderQuantity(crs.getString(4));
					orderLineList.add(orderLineVO);
				}
			}

		}

		return orderLineList;
	}

	
	public ReqResVO processPayment(String paymentXML, EnvConfigVO envConfigVO) {
		ReqResVO reqResVO = new ReqResVO();
		// String responseData = "";

		try {
			responseData = postReq.postXML(environment, paymentXML,
					"ProcessPayments_svc", "Y", "", envConfigVO);

			responseData = postReq.postXML(environment, paymentXML,
					"ProcessConvStorePayments_svc", "Y", "", envConfigVO);

			reqResVO.setReqXML(paymentXML);
			reqResVO.setResXML(responseData);
			AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();

			if (!responseData.equalsIgnoreCase("Success")) {
				if (responseData.contains("ErrorDescription")) {

					reqResVO.setComment(automationHelperImpl
							.getErrorDescription(reqResVO.getResXML()));
				} else {

					reqResVO.setComment(responseData);
				}
				reqResVO.setResXML("Error");
			}

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reqResVO;

	}
}
