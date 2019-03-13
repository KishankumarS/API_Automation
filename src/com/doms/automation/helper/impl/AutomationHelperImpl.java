package com.doms.automation.helper.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.OrderLineVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.bean.XmlTagAttributesVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.generatexml.impl.CreateAuthXml;
import com.doms.automation.helper.AutomationHelper;
import com.doms.automation.jaxbclasses.agenttrigger.AgentTrigger;
import com.doms.automation.marshaller.impl.AgentTriggerMarshaller;
import com.doms.automation.service.PostRequest;
import com.doms.automation.service.ProcessOrderService;
import com.doms.automation.utils.DbValidationConstants;
import com.doms.automation.utils.NikeDOMSApplicationUtils;
import com.doms.automation.utils.NikeDOMSConstants;

public class AutomationHelperImpl implements AutomationHelper {

	/*
	 * the method generates the po number (non-Javadoc)
	 * 
	 * @see
	 * com.doms.automation.helper.AutomationHelper#generatePONumber(com.doms.
	 * automation.bean.XmlTagLinesVO, java.lang.String)
	 */
	@Override
	public String generatePONumber(XmlTagLinesVO xmlTagLinesVO, String orderNumber) {
		if (isNewPoNeeded(xmlTagLinesVO.getXmlTagAttributesVO()))

			orderNumber = generatePoNumber(xmlTagLinesVO.getXmlTagAttributesVO(), orderNumber);

		return orderNumber;
	}

	public ReqResVO setStatus(ReqResVO reqResVO, EnvConfigVO envConfigVO, String orderNo,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO, ProcessOrderService processOrderService) throws JAXBException {
		String paymentStatus = "";

		String orderStatus = processOrderService.getOrderStatus(orderNo, "Sales", envConfigVO);
		CachedRowSet crs;
		try {
			nikeDOMSConnectionDAO.getDBValues(orderNo, NikeDOMSConstants.getPaymentStatus, NikeDOMSConstants.DOMS);
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						paymentStatus = crs.getString(1);
					}
				} else {
					while (crs.next()) {
						paymentStatus = crs.getString(1);
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

		reqResVO.setOrderStatus(orderStatus);
		reqResVO.setPaymentStatus(paymentStatus);

		return reqResVO;
	}

	/*
	 * If the cardtype is any of the specified one then the required PO will be
	 * generated if else the order number itself
	 */
	private String generatePoNumber(ArrayList<XmlTagAttributesVO> xmlTagAttributesVO, String orderNumber) {

		for (XmlTagAttributesVO oxmlTagAttributesVO : xmlTagAttributesVO) {

			if ("CreditCardType".equalsIgnoreCase(oxmlTagAttributesVO.getAttributeName())) {
				if (AMERICAN_EXPRESS.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + AMERICAN_EXPRESS_PO + orderNumber;
				} else if (CARTASI.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + CARTASI_PO + orderNumber;
				} else if (CARTEBLUE.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + CARTEBLUE_PO + orderNumber;
				} else if (DANKORT.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + DANKORT_PO + orderNumber;
				} else if (DELTA.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + DELTA + orderNumber;
				} else if (DISCOVER.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + DISCOVER_PO + orderNumber;
				} else if (DISCOVER_CARD.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + DISCOVER_CARD_PO + orderNumber;
				} else if (DOMESTIC_MAESTRO.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + DOMESTIC_MAESTRO_PO + orderNumber;
				} else if (EURO_CARD.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + EURO_CARD_PO + orderNumber;
				} else if (LASER.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + LASER_PO + orderNumber;
				} else if (MASTER_CARD.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + MASTER_CARD_PO + orderNumber;
				} else if (PAYPAL.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + PAYPAL_PO + orderNumber;
				} else if (VISA.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + VISA_PO + orderNumber;
				} else if (VISA_DEBIT.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + VISA_DEBIT_PO + orderNumber;
				} else if (VISA_ELECTRON.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + VISA_ELECTRON_PO + orderNumber;
				} else if (OFFLINE_BANK.equalsIgnoreCase(oxmlTagAttributesVO.getAttributeValue())) {

					orderNumber = "N" + OFFLINE_BANK_PO + orderNumber;
				}

			}

		}
		return orderNumber;
	}

	/*
	 * if the the enterprise code is nikeeurope and the payment type is either
	 * credit card ,paypal,or offlinebank
	 */
	private boolean isNewPoNeeded(ArrayList<XmlTagAttributesVO> XmlTagAttributesVO) {
		boolean isNikeEurope = false;
		boolean isCreditCard = false;
		boolean ispayPal = false;
		boolean isOfflineBank = false;
		boolean isNewPoNeeded = false;
		for (XmlTagAttributesVO xmlTagAttributesVO : XmlTagAttributesVO) {
			if ("EnterpriseCode".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
				if ("NIKEEUROPE".equalsIgnoreCase(xmlTagAttributesVO.getAttributeValue())) {
					isNikeEurope = true;
				}
			}
			if ("PaymentType".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
				if ("CREDIT_CARD".equalsIgnoreCase(xmlTagAttributesVO.getAttributeValue())) {
					isCreditCard = true;
				}
			}
			if ("PaymentType".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
				if ("PAYPAL".equalsIgnoreCase(xmlTagAttributesVO.getAttributeValue())) {
					ispayPal = true;
				}
			}
			if ("PaymentType".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
				if ("OFFLINE_BANK".equalsIgnoreCase(xmlTagAttributesVO.getAttributeValue())) {
					isOfflineBank = true;
				}
			}
			if (isNikeEurope && (isCreditCard || ispayPal || isOfflineBank)) {
				isNewPoNeeded = true;
			}

		}
		return isNewPoNeeded;
	}

	public String verifyEmailDOMS(NikeDOMSConnectionDAO nikeDomsConnectionDao, String dataKey,
			String executeServicename, String messagePattern, String query) {
		String response = "";
		String responsexml ="";
		Clob msgClob = null;
		try {

			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			nikeDomsConnectionDao.getDbValues( dataKey , executeServicename,
					"%" + messagePattern + "%", query, NikeDOMSConstants.DOMS);
			crs = nikeDomsConnectionDao.getRowSet();
			int size = crs.size();
			if (size > 0) {
				response = "Success";
				
			} else {
				response = "Warning";
				
			}
			while (crs.next()) {
				msgClob = crs.getClob("message_xml");
			}
			
				if (null != msgClob) {
					responsexml = getStringFromClob(msgClob);
					System.out.println(responsexml);
				}
		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response+responsexml;
	}

	public String verifyEmailPAC(Map<String, DbEnvConfig> map, String searchReference, String messagePattern,
			String query) {
		String response = "";
		String responsexml="";
		Clob msgClob = null;
		try {

			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			NikeDOMSConnectionDAO nikeDomsConnectionDao = new NikeDOMSConnectionDAO(map);
			nikeDomsConnectionDao.getDBValuesPAC("%" + searchReference + "%", "%" + messagePattern + "%", query,
					NikeDOMSConstants.PAC);
			crs = nikeDomsConnectionDao.getRowSet();
			int size = crs.size();
			if (size > 0) {
				response = "Success";
				
				
			} else {
				response = "Warning";
				
			}
			while (crs.next()) {
				msgClob = crs.getClob("DESERIALIZED_MESSAGE");
			}
				if (null != msgClob) {
					responsexml = getStringFromClob(msgClob);
				}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response+responsexml;
	}

	public String verifyEmailCOMMS(Map<String, DbEnvConfig> map, String ref_1, String subCategory, String query) {
		String response = "";
		try {

			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			NikeDOMSConnectionDAO nikeDomsConnectionDao = new NikeDOMSConnectionDAO(map);
			nikeDomsConnectionDao.getDBValuesPAC(ref_1, subCategory, query, NikeDOMSConstants.COMMS);
			crs = nikeDomsConnectionDao.getRowSet();
			int size = crs.size();
			if (size > 0) {
				response = "Success";
			} else {
				response = "Warning";
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public String verifyInDOMSProcessQueue(NikeDOMSConnectionDAO nikeDomsConnectionDao, String dataKey,
			String executeServicename, String query) {
		String response = "Warning";
		try {

			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			nikeDomsConnectionDao.getDbValues("%" + dataKey + "%", "%" + executeServicename + "%", "", query,
					NikeDOMSConstants.DOMS);
			crs = nikeDomsConnectionDao.getRowSet();
			int size = crs.size();
			if (size > 0) {
				response = "Success";
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	public String checkEntryInExtnAsyncProcessQ(NikeDOMSConnectionDAO nikeDOMSConnectionDAO, String criteriaId) {

		String processingKey = "Warning";
		try {
			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			nikeDOMSConnectionDAO.getDBValues(criteriaId, NikeDOMSConstants.GET_PROCESSING_KEY_FROM_ASINC_PROCESS_Q,
					NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			if (size > 0) {
				processingKey = crs.getString(1);
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


	public String getEnterpriseCode(ArrayList<XmlTagLinesVO> xmlTagLinesVOList) {

		String enterpriseCode = "";
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {

			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO.getXmlTagAttributesVO()) {
				if ("EnterpriseCode".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
						enterpriseCode = xmlTagAttributesVO.getAttributeValue();
						break;
					}

				}
			}
		}

		return enterpriseCode;

	}

	public String getEmailId(ArrayList<XmlTagLinesVO> xmlTagLinesVOList) {

		String emailId = "";
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {

			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO.getXmlTagAttributesVO()) {
				if ("PersonInfoShipTo_EMailID".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
						emailId = xmlTagAttributesVO.getAttributeValue();
						break;
					}

				}
			}
		}

		return emailId;

	}

	public String getShipNode(ArrayList<XmlTagLinesVO> xmlTagLinesVOList) {

		String ShipNode = "";
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {

			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO.getXmlTagAttributesVO()) {
				if ("ShipNode".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
						ShipNode = xmlTagAttributesVO.getAttributeValue();
						break;
					}

				}
			}
		}

		return ShipNode;

	}

	public int getNikeIdItemsCount(String lineType, ArrayList<XmlTagLinesVO> xmlTagLinesVOList) {

		int counter = 0;
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {

			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO.getXmlTagAttributesVO()) {
				if ("LineType".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {

					if (xmlTagAttributesVO.getAttributeValue().equalsIgnoreCase(lineType)) {

						counter++;
					}

				}

			}

		}

		return counter;
	}

	public int getExpectedWOCount(ArrayList<XmlTagLinesVO> xmlTagLinesVOList) {

		int counter = 0;
		String orderLineType;
		String sub7lineType;
		boolean isSub7;
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {
			orderLineType = "";
			sub7lineType = "";
			isSub7 = false;
			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO.getXmlTagAttributesVO()) {
				if ("LineType".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					orderLineType = xmlTagAttributesVO.getAttributeValue();
				}
				if ("ItemType".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					sub7lineType = xmlTagAttributesVO.getAttributeValue();
					if (sub7lineType.equalsIgnoreCase("SOCKSID") || sub7lineType.equalsIgnoreCase("JERSEYID")
							|| sub7lineType.equalsIgnoreCase("DIGITALPID"))
						isSub7 = true;

				}
			}
			if (orderLineType.equals("NIKEID")) {
				counter++;
			} else if (isSub7 && orderLineType.equals("INLINE")) {
				counter++;
			}

		}

		return counter;
	}



	public String getAuthXml(ArrayList<XmlTagLinesVO> xmlTagLinesVOList, String orderNo) {
		CreateAuthXml createAuthXml = new CreateAuthXml();
		String authXml = "";
		try {
			authXml = createAuthXml.generateXml(xmlTagLinesVOList, orderNo);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authXml;
	}

	public String getItemId(ArrayList<XmlTagLinesVO> xmlTagLinesVOList, String lineNo) {

		String itemId = "";
		boolean isLineNo = false;

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
					if ("ItemID".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
						if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
							itemId = xmlTagAttributesVO.getAttributeValue();
							break;
						}

					}
				}
			}
		}

		return itemId;

	}

	public int getPOItemsCount(ArrayList<XmlTagLinesVO> xmlTagLinesVOList) {

		int counter = 0;
		boolean isInline = false;
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {

			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO.getXmlTagAttributesVO()) {
				if ("LineType".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {

					if (xmlTagAttributesVO.getAttributeValue().equalsIgnoreCase("NIKEID")) {

						counter++;
						break;
					}
					if (xmlTagAttributesVO.getAttributeValue().equalsIgnoreCase("INLINE")) {

						isInline = true;
					}

				}
				if (isInline) {
					if ("ShipNode".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
						if (!(xmlTagAttributesVO.getAttributeValue().equalsIgnoreCase("BRD")
								|| xmlTagAttributesVO.getAttributeValue().equalsIgnoreCase("MENLO"))) {

							counter++;
							isInline = false;
							break;
						}

					}

				}

			}

		}

		return counter;
	}

	public OrderLineVO getOrderLineAttr(String environment, String orderNo, NikeDOMSConnectionDAO nikedomsconnectiondao)
			throws SQLException, ClassNotFoundException {
		OrderLineVO orderLineVO = new OrderLineVO();
		CachedRowSet crs;
		crs = RowSetProvider.newFactory().createCachedRowSet();
		nikedomsconnectiondao.getDBValues(orderNo, NikeDOMSConstants.orderLineQuery, NikeDOMSConstants.DOMS);

		crs = nikedomsconnectiondao.getRowSet();
		int size = crs.size();
		crs.beforeFirst();
		if (size > 0) {
			if (size == 1) {
				if (crs.next()) {
					orderLineVO.setPONumber(crs.getString(1));
					orderLineVO.setOrderLineKey(crs.getString(2));
					orderLineVO.setOrderHeaderKey(crs.getString(3));
					orderLineVO.setOrderQuantity(crs.getString(4));
				}
			} else {
				while (crs.next()) {

					orderLineVO.setPONumber(crs.getString(1));
					orderLineVO.setOrderLineKey(crs.getString(2));
					orderLineVO.setOrderHeaderKey(crs.getString(3));
					orderLineVO.setOrderQuantity(crs.getString(4));
				}
			}
		}

		return orderLineVO;
	}

	public String getPONumber(String orderNo, String primeLineNo, NikeDOMSConnectionDAO nikedomsconnectiondao) {
		CachedRowSet crs;
		String orderlineKey = "";
		String poNumber = "";
		String query = NikeDOMSConstants.getLineKeyPrLnNo.replace("lineNo", primeLineNo);
		try {
			nikedomsconnectiondao.getDBValues(orderNo, query, NikeDOMSConstants.DOMS);
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikedomsconnectiondao.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						orderlineKey = crs.getString("ORDER_LINE_KEY").trim();
					}
				} else {
					while (crs.next()) {

						orderlineKey = crs.getString("ORDER_LINE_KEY").trim();
					}
				}
			}

			nikedomsconnectiondao.getDBValues(orderlineKey, NikeDOMSConstants.getPONolineKey, NikeDOMSConstants.DOMS);
			crs = nikedomsconnectiondao.getRowSet();
			size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						poNumber = crs.getString(1);
					}
				} else {
					while (crs.next()) {
						poNumber = crs.getString(1);
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
		return poNumber;
	}

	public List getOrderLinePrimeLine(String orderNo, String primeLineNo, NikeDOMSConnectionDAO nikedomsconnectiondao) {
		List orderLineVOList = new ArrayList();
		OrderLineVO orderLineVO = new OrderLineVO();
		String query = NikeDOMSConstants.getLineKeyPrLnNo.replace("lineNo", primeLineNo);
		String POOrderlineKey = null;
		String POOrderheaderKey = null;
		String POQuantity = null;
		String ItemID = null;
		String orderlineKey = "";
		String poNumber = "";
		CachedRowSet crs;
		try {
			nikedomsconnectiondao.getDBValues(orderNo, query, NikeDOMSConstants.DOMS);
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikedomsconnectiondao.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						orderlineKey = crs.getString(1);
						ItemID = crs.getString(7);
					}
				} else {
					while (crs.next()) {

						orderlineKey = crs.getString(1);
						ItemID = crs.getString(7);
					}
				}
			}

			nikedomsconnectiondao.getDBValues(orderlineKey, NikeDOMSConstants.getPONolineKey, NikeDOMSConstants.DOMS);
			crs = nikedomsconnectiondao.getRowSet();
			size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						poNumber = crs.getString(1);
					}
				} else {
					while (crs.next()) {
						poNumber = crs.getString(1);
					}
				}
			}

			nikedomsconnectiondao.getDBValues(poNumber, NikeDOMSConstants.getlineKeyPONo, NikeDOMSConstants.DOMS);
			crs = nikedomsconnectiondao.getRowSet();
			size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						orderLineVO = new OrderLineVO();
						POOrderlineKey = crs.getString(1).trim();
						POOrderheaderKey = crs.getString("ORDER_HEADER_KEY");
						POQuantity = crs.getString("ORDERED_QTY");
						orderLineVO.setItemID(ItemID);
						orderLineVO.setPONumber(poNumber);
						orderLineVO.setOrderLineKey(POOrderlineKey);
						orderLineVO.setOrderHeaderKey(POOrderheaderKey);
						orderLineVO.setOrderQuantity(POQuantity);
						orderLineVOList.add(orderLineVO);
					}
				} else {
					while (crs.next()) {
						orderLineVO = new OrderLineVO();
						POOrderlineKey = crs.getString(1).trim();
						POOrderheaderKey = crs.getString("ORDER_HEADER_KEY");
						POQuantity = crs.getString("ORDERED_QTY");
						orderLineVO.setItemID(ItemID);
						orderLineVO.setPONumber(poNumber);
						orderLineVO.setOrderLineKey(POOrderlineKey);
						orderLineVO.setOrderHeaderKey(POOrderheaderKey);
						orderLineVO.setOrderQuantity(POQuantity);
						orderLineVOList.add(orderLineVO);
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

		return orderLineVOList;
	}

	public OrderLineVO getOrderLineVO(String primeLineNo, String orderNo, NikeDOMSConnectionDAO nikeDOMSConnectionDAO)
			throws ClassNotFoundException, SQLException {

		String orderlineKey = "";

		CachedRowSet crs;
		OrderLineVO orderLineVO = new OrderLineVO();
		String query = NikeDOMSConstants.getLineKeyPrLnNo.replace("lineNo", primeLineNo);
		nikeDOMSConnectionDAO.getDBValues(orderNo, query, NikeDOMSConstants.DOMS);
		crs = RowSetProvider.newFactory().createCachedRowSet();
		crs = nikeDOMSConnectionDAO.getRowSet();

		if (crs.next()) {
			orderlineKey = crs.getString("ORDER_LINE_KEY").trim();
			orderLineVO.setOrderLineKey(orderlineKey);

			orderLineVO.setOrderQuantity(crs.getString("ORDERED_QTY"));
		}
		nikeDOMSConnectionDAO.getDBValues(orderlineKey, NikeDOMSConstants.GET_OLKEY_SAP, NikeDOMSConstants.DOMS);
		crs = nikeDOMSConnectionDAO.getRowSet();

		if (crs.next()) {

			orderLineVO.setSapOrderLineKey(crs.getString(1));
		}
		nikeDOMSConnectionDAO.getDBValues(orderlineKey, NikeDOMSConstants.getPONolineKey, NikeDOMSConstants.DOMS);
		crs = nikeDOMSConnectionDAO.getRowSet();

		if (crs.next()) {

			orderLineVO.setPONumber(crs.getString(1));
		}
		orderLineVO.setPrimeLineNo(primeLineNo);
		crs.close();
		return orderLineVO;
	}



	public String getTriggerCode(String enterPriseCode2) {
		String triggerCode = "";
		if ("NIKEUS".equalsIgnoreCase(enterPriseCode2)) {

			triggerCode = "BOMonitor_US";
		}
		if ("NIKEEUROPE".equalsIgnoreCase(enterPriseCode2)) {

			triggerCode = "BOMonitor_EU";
		}
		if ("NIKEJP".equalsIgnoreCase(enterPriseCode2)) {

			triggerCode = "BOMonitor_JP";
		}
		if ("NIKECN".equalsIgnoreCase(enterPriseCode2)) {

			triggerCode = "BOMonitor_CN";
		}
		return triggerCode;
	}

	public ArrayList<String> getEventNameSubCategory(String eventType) {
		ArrayList<String> list = new ArrayList<String>();
		String eventName = "";
		String subCategory = "";
		if (DbValidationConstants.ORDER_CONFIRM.equalsIgnoreCase(eventType)) {
			eventName = DbValidationConstants.DOMS_ORDERCONFIRM_EVENT_NAME;
			subCategory = DbValidationConstants.COMMS_ORDERCONFIRM_SUBCATEGORY;
		}
		if (DbValidationConstants.SHIPMENT_CONFIRM.equalsIgnoreCase(eventType)) {
			eventName = DbValidationConstants.DOMS_SHIPMENT_CONFIRM_EVENT_NAME;
			subCategory = DbValidationConstants.COMMS_SHIPMENT_CONFIRM_SUBCATEGORY;
		}
		if (DbValidationConstants.PickUp.equalsIgnoreCase(eventType)) {
			eventName = DbValidationConstants.DOMS_PickUp_CONFIRM_EVENT_NAME;
			subCategory = DbValidationConstants.COMMS_PickUp_CONFIRM_SUBCATEGORY;
		}
		if (DbValidationConstants.RET_ORDER_CONFIRM.equalsIgnoreCase(eventType)) {
			eventName = DbValidationConstants.DOMS_RET_ORDER_CONFIRM_EVENT_NAME;
			subCategory = DbValidationConstants.COMMS_ORDERCONFIRM_SUBCATEGORY;
		}
		if (DbValidationConstants.REFUND_CONFIRM.equalsIgnoreCase(eventType)) {
			eventName = DbValidationConstants.DOMS_REFUND_CONFIRM_EVENT_NAME;
			subCategory = DbValidationConstants.COMMS_REFUND_CONFIRM_SUBCATEGORY;
		}
		if (DbValidationConstants.REFUND_NOTIFICATION.equalsIgnoreCase(eventType)) {
			eventName = DbValidationConstants.DOMS_REFUND_NOTIFICATION_EVENT_NAME;
			subCategory = DbValidationConstants.COMMS_REFUND_NOTIFICATION_SUBCATEGORY;
		}
		if (DbValidationConstants.CANCELLATION.equalsIgnoreCase(eventType)) {
			eventName = DbValidationConstants.DOMS_CANCELLATION_EVENT_NAME;
			subCategory = DbValidationConstants.COMMS_CANCELLATION_SUBCATEGORY;
		}
		if (DbValidationConstants.FACTORY_COMPLETE_CONFIRM.equalsIgnoreCase(eventType)) {
			eventName = DbValidationConstants.DOMS_FACTORY_COMPLETE_CONFIRM_EVENT_NAME;
			subCategory = DbValidationConstants.COMMS_FACTORY_COMPLETE_SUBCATEGORY;
		}
		if (DbValidationConstants.DELiVERY_CONFIRM.equalsIgnoreCase(eventType)) {
			eventName = DbValidationConstants.DOMS_DELiVERY_CONFIRM_EVENT_NAME;
			subCategory = DbValidationConstants.COMMS_DELiVERY_CONFIRM_SUBCATEGORY;
		}
		if (DbValidationConstants.NIKEID_DELiVERY_CONFIRM.equalsIgnoreCase(eventType)) {
			eventName = DbValidationConstants.DOMS_NIKEID_DELiVERY_CONFIRM_EVENT_NAME;
			subCategory = DbValidationConstants.COMMS_NIKEID_DELiVERY_CONFIRM_SUBCATEGORY;
		}
		if (DbValidationConstants.NIKEID_ORDER_STARTED_CONFIRM.equalsIgnoreCase(eventType)) {
			eventName = DbValidationConstants.DOMS_NIKEID_ORDER_STARTED_CONFIRM_EVENT_NAME;
			subCategory = DbValidationConstants.COMMS_NIKEID_ORDER_STARTED_CONFIRM_SUBCATEGORY;
		}
		if (DbValidationConstants.NIKEID_SHIP_CONFIRMATION.equalsIgnoreCase(eventType)) {
			eventName = DbValidationConstants.DOMS_NIKEID_SHIP_CONFIRMATION_CONFIRM_EVENT_NAME;
			subCategory = DbValidationConstants.COMMS_NIKEID_SHIP_CONFIRMATION_CONFIRM_SUBCATEGORY;
		}
		list.add(eventName);
		list.add(subCategory);
		return list;
	}

	public ArrayList<String> getCriteriaIdExecServiceName(String financialSystem) {

		ArrayList<String> list = new ArrayList<String>();

		String criteriaId = "";
		String executeServiceName = "";
		if (DbValidationConstants.FIN03.equalsIgnoreCase(financialSystem)) {
			criteriaId = DbValidationConstants.CRITERIA_ID_FIN03;
			executeServiceName = DbValidationConstants.EXEC_SERVICE_NAME_FIN03;
		} else if (DbValidationConstants.FIN07.equalsIgnoreCase(financialSystem)) {
			criteriaId = DbValidationConstants.CRITERIA_ID_FIN07;
			executeServiceName = DbValidationConstants.EXEC_SERVICE_NAME_FIN07;
		} else if (DbValidationConstants.PO_Match.equalsIgnoreCase(financialSystem)) {
			criteriaId = DbValidationConstants.CRITERIA_ID_PO_Match;
			executeServiceName = DbValidationConstants.EXEC_SERVICE_NAME_PO_Match;
		} else if (DbValidationConstants.STO14.equalsIgnoreCase(financialSystem)) {
			criteriaId = DbValidationConstants.CRITERIA_ID_STO14;
			executeServiceName = DbValidationConstants.EXEC_SERVICE_NAME_STO14;
		}
		list.add(criteriaId);
		list.add(executeServiceName);
		return list;
	}

	public ArrayList<String> getCriteriaIdExecServiceNameNew(String financialSystem) {

		ArrayList<String> list = new ArrayList<String>();

		String criteriaId = "";
		String executeServiceName = "";
		if (DbValidationConstants.FIN03.equalsIgnoreCase(financialSystem)) {
			criteriaId = DbValidationConstants.CRITERIA_ID_FIN03;
			executeServiceName = DbValidationConstants.EXEC_SERVICE_NAME_FIN03_RFS;
		} else if (DbValidationConstants.FIN07.equalsIgnoreCase(financialSystem)) {
			criteriaId = DbValidationConstants.CRITERIA_ID_FIN07;
			executeServiceName = DbValidationConstants.EXEC_SERVICE_NAME_FIN07_NEW;
		} else if (DbValidationConstants.PO_Match.equalsIgnoreCase(financialSystem)) {
			criteriaId = DbValidationConstants.CRITERIA_ID_PO_Match;
			executeServiceName = DbValidationConstants.EXEC_SERVICE_NAME_PO_Match_NEW;
		} else if (DbValidationConstants.STO14.equalsIgnoreCase(financialSystem)) {
			criteriaId = DbValidationConstants.CRITERIA_ID_STO14;
			executeServiceName = DbValidationConstants.EXEC_SERVICE_NAME_STO14;
		}
		list.add(criteriaId);
		list.add(executeServiceName);
		return list;
	}

	public String getResponseToString(HttpResponse response) {

		HttpEntity r_entity = response.getEntity();
		String xmlString = null;
		try {
			xmlString = EntityUtils.toString(r_entity);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlString;

	}

	public String getErrorDescription(String xmlString) {
		String errorDescription = "Not traceable";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = factory.newDocumentBuilder();
			InputSource inStream = new InputSource(new StringReader(xmlString));

			Document doc = db.parse(inStream);

			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xpath = xFactory.newXPath();

			if (xmlString.contains("<Error")) {
				errorDescription = (String) xpath.evaluate("//Error/@ErrorDescription", doc, XPathConstants.STRING);
			}
			if (xmlString.contains("<error")) {
				errorDescription = (String) xpath.evaluate("//exceptions/@message", doc, XPathConstants.STRING);
			}

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
		return errorDescription;
	}

	public String generateReservationID(String enterpriseCode) throws InterruptedException {
		NikeDOMSApplicationUtils nikeUtils = new NikeDOMSApplicationUtils();
		String reservationID = "";
		Thread.sleep(1000);
		if (enterpriseCode.equalsIgnoreCase("NIKEEUROPE")) {
			reservationID = "EI" + nikeUtils.generateUniqueNo("MMddhhmmss");

		} else if (enterpriseCode.equalsIgnoreCase("NIKEUS")) {
			reservationID = "CI" + nikeUtils.generateUniqueNo("MMddhhmmss");

		} else if (enterpriseCode.equalsIgnoreCase("NIKECN") || enterpriseCode.equalsIgnoreCase("NIKEJP")) {
			reservationID = "AI" + nikeUtils.generateUniqueNo("MMddhhmmss");

		}
		return reservationID;
	}

	public String getOrderLineKey(String orderNo, String lineNo, NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		CachedRowSet crs = null;
		String orderLineKey = "";
		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();
			String query = "";
			query = NikeDOMSConstants.getOrderLineKey.replace("lineNo", lineNo);

			nikeDOMSConnectionDAO.getDBValues(orderNo, query, NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						orderLineKey = crs.getString(1);

					}
				} else {
					while (crs.next()) {
						orderLineKey = crs.getString(1);

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
		return orderLineKey;

	}
	public String getNikeIdOrderLineKeyForPinckUp(String orderID, String lineNo,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		CachedRowSet crs=null; 
		String ComponentOrderLineKey ="";
		String query = "";		
		query = NikeDOMSConstants.getOrderlinekeyfornikeid.replace(
				"lineNo", lineNo);
		
		try{
			crs = RowSetProvider.newFactory().createCachedRowSet();
			
			
			nikeDOMSConnectionDAO.getDBValues(orderID, query,
					NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						ComponentOrderLineKey =  crs.getString(1); 
						

					}
				} else {
					while (crs.next()) {
						ComponentOrderLineKey =  crs.getString(1); 
						

					}
				}
			}
			
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				crs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ComponentOrderLineKey;
	
	
	}
	public String getNikeIdOrderLineKey(String orderLineKey, NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		CachedRowSet crs = null;
		String ComponentOrderLineKey = "";
		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();

			nikeDOMSConnectionDAO.getDBValues(orderLineKey, NikeDOMSConstants.getComponentOrderLineKeyNikeId,
					NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						ComponentOrderLineKey = crs.getString(1);

					}
				} else {
					while (crs.next()) {
						ComponentOrderLineKey = crs.getString(1);

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
		return ComponentOrderLineKey;

	}

	public String getOrderHeaderKey(String orderNo, NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		CachedRowSet crs = null;
		String orderHeaderKey = "";
		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();

			nikeDOMSConnectionDAO.getDBValues(orderNo, NikeDOMSConstants.GET_orderHeaderKeyQuery,
					NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();

			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						orderHeaderKey = crs.getString(1);

					}
				} else {
					while (crs.next()) {
						orderHeaderKey = crs.getString(1);

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
		return orderHeaderKey;

	}
	public String getOrderHeaderKeyOfPoNumber(String sgNo, NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		CachedRowSet crs = null;
		String orderHeaderKey = "";
		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();

			nikeDOMSConnectionDAO.getDBValues(sgNo, DbValidationConstants.GET_ORDER_HEADER_KEY_OF_PO_NUMBER,
					NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();

			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						orderHeaderKey = crs.getString(1);

					}
				} else {
					while (crs.next()) {
						orderHeaderKey = crs.getString(1);

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
		return orderHeaderKey;

	}
	public String getStringFromClob(Clob msgClob) throws SQLException {

		String messageXML = "";
		if (msgClob != null) {
			if ((int) msgClob.length() > 0) {
				messageXML = msgClob.getSubString(1, (int) msgClob.length());
			}
		}

		return messageXML;
	}
	public String getStringFromClob(String msgClob) throws SQLException {

		String messageXML = "";
		if (msgClob != null) {
			if ((int) msgClob.length() > 0) {
				messageXML = msgClob.substring(1, (int) msgClob.length());
			}
		}

		return messageXML;
	}
	public String validateXPathValue(String xmlString, String xpathValue) {

		String Status = "";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		// System.out.println(xmlString);
		try {
			db = factory.newDocumentBuilder();
			InputSource inStream = new InputSource(new StringReader(xmlString));

			Document doc = db.parse(inStream);

			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xpath = xFactory.newXPath();

			Status = (String) xpath.evaluate(xpathValue, doc, XPathConstants.STRING);

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

	

	public String getMessageXmlFromDOMS(NikeDOMSConnectionDAO nikeDomsConnectionDao, String dataKey,
			String executeServicename, String messagePattern, String query) {

		Clob msgClob = null;
		String messageXml = "";
		try {

			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			nikeDomsConnectionDao.getDbValues("%" + dataKey + "%", "%" + executeServicename + "%",
					"%" + messagePattern + "%", query, NikeDOMSConstants.DOMS);
			crs = nikeDomsConnectionDao.getRowSet();
			while (crs.next()) {
				msgClob = crs.getClob("");
			}

			if (null != msgClob) {
				messageXml = getStringFromClob(msgClob);
			}
		}

		catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageXml;
	}
	public String getMessageXmlFromDOMSWOLike(NikeDOMSConnectionDAO nikeDomsConnectionDao, String dataKey,
			String executeServicename, String messagePattern, String query) {

		Clob msgClob = null;
		String messageXml = "";
		try {

			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			nikeDomsConnectionDao.getDbValues( dataKey ,  executeServicename,
					"%" + messagePattern + "%", query, NikeDOMSConstants.DOMS);
			crs = nikeDomsConnectionDao.getRowSet();
			while (crs.next()) {
				msgClob = crs.getClob("message_xml");
			}

			if (null != msgClob) {
				messageXml = getStringFromClob(msgClob);
			}
		}

		catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageXml;
	}
	public String getMessageXmlFromDOMSForFirstSalesFiles(NikeDOMSConnectionDAO nikeDomsConnectionDao, String dataKey,
			String executeServicename, String messagePattern, String query) {

		Clob msgClob = null;
		String messageXml = "";
		try {

			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			nikeDomsConnectionDao.getDbValues1("%" + executeServicename + "%",
					"%"+dataKey+"%"+messagePattern + "%", query, NikeDOMSConstants.DOMS);
			crs = nikeDomsConnectionDao.getRowSet();
			while (crs.next()) {
				msgClob = crs.getClob("message_xml");
			}

			if (null != msgClob) {
				messageXml = getStringFromClob(msgClob);
			}
		}

		catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageXml;
	}
	public boolean isIC(ArrayList<XmlTagLinesVO> xmlTagLinesVOList) {

		boolean isIC = false;
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {

			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO.getXmlTagAttributesVO()) {
				if ("IC".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (("Yes").equalsIgnoreCase(xmlTagAttributesVO.getAttributeValue())) {
						isIC = true;
						break;
					}

				}
			}
		}

		return isIC;

	}

	public String getMPOSTransNo(String orderNo, NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		CachedRowSet crs = null;
		String transNo = "";
		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();

			nikeDOMSConnectionDAO.getDBValues(orderNo, NikeDOMSConstants.GET_MPOS_TRANS_NO, NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						transNo = crs.getString(1);

					}
				} else {
					while (crs.next()) {
						transNo = crs.getString(1);

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
		return transNo;

	}

	public String getInvoiceNumberByOrderLineKey(String orderLineKey, String invoiceTypes,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		CachedRowSet crs = null;
		String invoiceNumber = "";
		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();
			String query = "";
			query = NikeDOMSConstants.getInvoiceNumberByOrderLineKey.replace("InvoiceTypes", invoiceTypes);

			nikeDOMSConnectionDAO.getDBValues(orderLineKey, query, NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						invoiceNumber = crs.getString(1);

					}
				} else {
					while (crs.next()) {
						invoiceNumber = crs.getString(1);

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
		return invoiceNumber;

	}

	public String updateAttribute(String xmlString, String xpath, String attrValue) {

		DocumentBuilderFactory dbf = null;
		DocumentBuilder db = null;
		InputSource source = null;
		Document document = null;
		StreamResult xmlOutput = null;
		XPathFactory factory = null;
		XPath xPath = null;
		NodeList nodes = null;
		Node node = null;
		Transformer transformer = null;
		DOMSource domSource = null;
		try {
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			source = new InputSource(new StringReader(xmlString));
			document = db.parse(source);
			factory = XPathFactory.newInstance();
			xPath = factory.newXPath();

			nodes = (NodeList) xPath.evaluate(xpath, document, XPathConstants.NODESET);

			for (int i = 0; i < nodes.getLength(); i++) {
				node = nodes.item(i);
				node.setTextContent(attrValue);
			}

			xmlOutput = new StreamResult(new StringWriter());
			transformer = TransformerFactory.newInstance().newTransformer();
			domSource = new DOMSource(document);

			transformer.transform(domSource, xmlOutput);

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
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlOutput.getWriter().toString();

	}

	public String addNode(String xmlString, String xpath, Node nodeToAdd) {
		DocumentBuilderFactory dbf = null;
		DocumentBuilder db = null;
		InputSource source = null;
		Document document = null;
		StreamResult xmlOutput = null;
		XPathFactory factory = null;
		XPath xPath = null;
		NodeList nodes = null;
		Node node = null;
		Transformer transformer = null;
		DOMSource domSource = null;
		Element element = null;
		try {
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			source = new InputSource(new StringReader(xmlString));
			document = db.parse(source);
			Node importedNode = document.importNode(nodeToAdd, true);
			factory = XPathFactory.newInstance();
			xPath = factory.newXPath();

			System.out.println("importedNode.toString()" + importedNode.toString());
			nodes = (NodeList) xPath.evaluate(xpath, document, XPathConstants.NODESET);
			String XMLop = "";
			for (int i = 0; i < nodes.getLength(); i++) {
				if (i > 0) {
					source = new InputSource(new StringReader(XMLop));
					document = db.parse(source);
					nodes = (NodeList) xPath.evaluate(xpath, document, XPathConstants.NODESET);
				}
				node = nodes.item(i);
				importedNode = document.importNode(nodeToAdd, true);
				node.appendChild(importedNode);
				xmlOutput = new StreamResult(new StringWriter());
				transformer = TransformerFactory.newInstance().newTransformer();
				domSource = new DOMSource(document);

				transformer.transform(domSource, xmlOutput);

				XMLop = xmlOutput.getWriter().toString();

			}

			/*
			 * xmlOutput = new StreamResult(new StringWriter()); transformer =
			 * TransformerFactory.newInstance() .newTransformer(); domSource =
			 * new DOMSource(document);
			 * 
			 * transformer.transform( domSource, xmlOutput);
			 * 
			 * XMLop = xmlOutput.getWriter().toString();
			 */
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
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlOutput.getWriter().toString();

	}

	

	public String getreturnOrderLineKey(String orderNo, String query,
			NikeDOMSConnectionDAO nikedomsconnectiondao) {
		String orderLineKey = "";

		try {
			nikedomsconnectiondao.getDBValues(orderNo, query,
					NikeDOMSConstants.DOMS);
			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikedomsconnectiondao.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						orderLineKey = crs.getString("ORDER_LINE_KEY").trim();
					}
				} else {
					while (crs.next()) {
						orderLineKey = crs.getString("ORDER_LINE_KEY").trim();
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

		return orderLineKey;

	}

	public ArrayList<String> getProductDetails(ArrayList<XmlTagLinesVO> xmlTagLinesVOList, String lineNo) {
		ArrayList<String> productDetails = new ArrayList<String>();
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {
			boolean isLine = false;
			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO.getXmlTagAttributesVO()) {
				if ("LineNo".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (lineNo.equals(xmlTagAttributesVO.getAttributeValue())) {
						isLine = true;
					}
				}
				if (isLine) {
					if ("ExtnProductId".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
						productDetails.add(xmlTagAttributesVO.getAttributeValue());

					}
					if ("ExtnColorNumber".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
						productDetails.add(xmlTagAttributesVO.getAttributeValue());

					}
					if ("ExtnDisplaySize".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
						productDetails.add(xmlTagAttributesVO.getAttributeValue());

					}
				}

			}
		}
		return productDetails;
	}

	public String getFSOOrderHeaderKeyFromOrderHeaderKey(String orderHeaderKey, Map<String, DbEnvConfig> dbConfigMap)
			throws ClassNotFoundException, SQLException {
		String gSOrderNo = "";
		NikeDOMSConnectionDAO nikedomsconnectiondao = new NikeDOMSConnectionDAO(dbConfigMap);
		nikedomsconnectiondao.getDBValues(orderHeaderKey,
				NikeDOMSConstants.GET_FSOORDER_HEADER_KEY_FROM_ORDER_HEADER_KEY, NikeDOMSConstants.DOMS);
		CachedRowSet crs = nikedomsconnectiondao.getRowSet();
		int size = crs.size();
		crs.beforeFirst();
		if (size > 0) {
			if (size == 1) {
				if (crs.next()) {
					gSOrderNo = crs.getString(1);
				}
			} else {
				while (crs.next()) {
					gSOrderNo = crs.getString(1);
				}
			}
		}

		return gSOrderNo;

	}

	public String getFSOrderNo(String orderNo, Map<String, DbEnvConfig> dbConfigMap)
			throws ClassNotFoundException, SQLException {
		String fSOrderNo = "";
		NikeDOMSConnectionDAO nikedomsconnectiondao = new NikeDOMSConnectionDAO(dbConfigMap);
		nikedomsconnectiondao.getDBValues(orderNo, NikeDOMSConstants.GET_FSOORDER_NO_QUERRY, NikeDOMSConstants.DOMS);
		CachedRowSet crs = nikedomsconnectiondao.getRowSet();
		int size = crs.size();
		crs.beforeFirst();
		if (size > 0) {
			if (size == 1) {
				if (crs.next()) {
					fSOrderNo = crs.getString(1);
				}
			} else {
				while (crs.next()) {
					fSOrderNo = crs.getString(1);
				}
			}
		}

		return fSOrderNo; // Map<String, DbEnvConfig> dbConfigMap

	}

	public String getFROOrderNo(String orderNo, Map<String, DbEnvConfig> dbConfigMap)
			throws ClassNotFoundException, SQLException {
		String fSOrderNo = "";
		NikeDOMSConnectionDAO nikedomsconnectiondao = new NikeDOMSConnectionDAO(dbConfigMap);
		nikedomsconnectiondao.getDBValues(orderNo, NikeDOMSConstants.GET_FROORDER_NO_QUERRY, NikeDOMSConstants.DOMS);
		CachedRowSet crs = nikedomsconnectiondao.getRowSet();
		int size = crs.size();
		crs.beforeFirst();
		if (size > 0) {
			if (size == 1) {
				if (crs.next()) {
					fSOrderNo = crs.getString(1);
				}
			} else {
				while (crs.next()) {
					fSOrderNo = crs.getString(1);
				}
			}
		}

		return fSOrderNo; // Map<String, DbEnvConfig> dbConfigMap

	}

	public String getOrderNoOrFsoOrderNo(boolean isNikeGS, String orderNo, Map<String, DbEnvConfig> dbConfigMap) {
		String orderNum = "";

		if (isNikeGS) {
			try {
				orderNum = getFSOrderNo(orderNo, dbConfigMap);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			orderNum = orderNo;
		}
		return orderNum;
	}
	public String getPONumberForInline(String orderNo, String primeLineNo,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		CachedRowSet crs = null;
		String poNumber = "";
		try {
			nikeDOMSConnectionDAO.getDBValues(orderNo, NikeDOMSConstants.getInlineSGno, NikeDOMSConstants.DOMS);

			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						poNumber = crs.getString(1);

					}
				} else {
					while (crs.next()) {
						poNumber = crs.getString(1);

					}
				}

			}
			crs.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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
		return poNumber;
	}
	public String getPONumberForNikeId(String orderNo, String primeLineNo,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		CachedRowSet crs = null;
		String poNumber = "";
		try {
			nikeDOMSConnectionDAO.getDBValuesNikeiD(orderNo,
					getOrderLineKey(orderNo, primeLineNo, nikeDOMSConnectionDAO), NikeDOMSConstants.getNikeidSGno,
					NikeDOMSConstants.DOMS);

			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						poNumber = crs.getString(1);

					}
				} else {
					while (crs.next()) {
						poNumber = crs.getString(1);

					}
				}

			}
			crs.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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
		return poNumber;
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

	public String getLineType(ArrayList<XmlTagLinesVO> xmlTagLinesVOList, String line) {
		String lineType = "";
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {
			boolean isLine = false;
			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO.getXmlTagAttributesVO()) {
				if ("LineNo".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (line.equals(xmlTagAttributesVO.getAttributeValue())) {
						isLine = true;
					}
				}
				if (isLine) {
					if ("LineType".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
						return xmlTagAttributesVO.getAttributeValue();

					}
				}
			}
		}

		return lineType;
	}

	

	public int getLineTypeCnt(ArrayList<XmlTagLinesVO> xmlTagLinesVOList) {
		Set<String> lineTypeSet = new HashSet<String>();
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {

			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO.getXmlTagAttributesVO()) {

				if ("LineType".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					lineTypeSet.add(xmlTagAttributesVO.getAttributeValue());

				}

			}
		}
		return lineTypeSet.size();
	}

	public String getOrderLineKeyForAnyLineType(String lineType, NikeDOMSConnectionDAO nikeDOMSConnectionDAO,
			String line, String orderNo) {

		String orderLineKey = getOrderLineKey(orderNo, line, nikeDOMSConnectionDAO);
		if ("NikeId".equalsIgnoreCase(lineType)) {

			orderLineKey = getNikeIdOrderLineKey(orderLineKey, nikeDOMSConnectionDAO);

		}
		return orderLineKey;
	}

	public void triggerAgent(String criteriaId, String environment, EnvConfigVO envConfigVO) {
		AgentTrigger agentTrigger = new AgentTrigger();
		agentTrigger.setCriteriaId(criteriaId);
		AgentTriggerMarshaller agentTriggerMarshaller = new AgentTriggerMarshaller(agentTrigger);
		String triggerXml;
		try {
			triggerXml = agentTriggerMarshaller.convertJaxbObjectToXML();

			PostRequest postReq = new PostRequest();
			HttpResponse response = postReq.post(environment, triggerXml, "triggerAgent", "N", envConfigVO);
			Thread.sleep(15000);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getAttributeVlaue(String attributeName, ArrayList<XmlTagLinesVO> xmlTagLinesVOList, String lineNo) {
		String attributeValue = "";
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
					if (attributeName.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
						if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
							attributeValue = xmlTagAttributesVO.getAttributeValue();
							break;
						}

					}
				}
			}
		}

		return attributeValue;
	}

	public String getReturnOrderNo(String primeLineNo, String orderNo, NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		String returnOrderNo = "";
		String query = NikeDOMSConstants.getReturnOrderNo.replace("${PrimeLineNo}", primeLineNo);
		CachedRowSet crs = null;

		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikeDOMSConnectionDAO.getRowSet();
			nikeDOMSConnectionDAO.getDBValues(orderNo, query, NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {

						returnOrderNo = crs.getString("ORDER_NO");
					}
				} else {
					while (crs.next()) {

						returnOrderNo = crs.getString("ORDER_NO");
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
		return returnOrderNo;

	}

	public String getCountry(ArrayList<XmlTagLinesVO> xmlTagLinesVOList) {

		String country = "";
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {

			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO.getXmlTagAttributesVO()) {
				if ("Country".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
						country = xmlTagAttributesVO.getAttributeValue();
						break;
					}

				}
			}
		}

		return country;

	}

	public void triggerBOMonitor(String enterpriseCode, String environment, EnvConfigVO envConfigVO) {

		if (enterpriseCode.equals("NIKEUS")) {
			triggerAgent("BOMonitor_US", environment, envConfigVO);
		}
		if (enterpriseCode.equals("NIKEEUROPE")) {
			triggerAgent("BOMonitor_EU", environment, envConfigVO);
		}
		if (enterpriseCode.equals("NIKEJP")) {
			triggerAgent("BOMonitor_JP", environment, envConfigVO);
		}
		if (enterpriseCode.equals("NIKECN")) {
			triggerAgent("BOMonitor_CN", environment, envConfigVO);
		}
		if (enterpriseCode.equals("NIKEGS")) {
			triggerAgent("BOMonitor_NIKEGS", environment, envConfigVO);
		}

	}

	public String getReturnInvoiceNo(String orderNo, NikeDOMSConnectionDAO nikeDOMSConnectionDAO, String primeLineNo) {
		String invoiceNo = "";
		CachedRowSet crs = null;

		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();
			nikeDOMSConnectionDAO.getDBValues(orderNo,
					NikeDOMSConstants.GET_RETURN_INVOICE_DETAILS_PRLNO.replace("{PrimeLineNo}", primeLineNo),
					NikeDOMSConstants.DOMS);

			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						invoiceNo = crs.getString("INVOICE_NO");

					}
				} else {
					while (crs.next()) {
						invoiceNo = crs.getString("INVOICE_NO");

					}
				}
			}

			crs.close();

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

		return invoiceNo;

	}
	//Method to convert json string to xml string
	public String convertJsonStringToXmlString(String jsonString) {
		String xml = "";
		JSONObject json;
		try {
			json = new JSONObject(jsonString);
			xml = XML.toString(json);
		} catch (JSONException e) {		
			e.printStackTrace();
		}
		return xml;
	}

	public String verifyEmailDOMSWOLike(NikeDOMSConnectionDAO nikeDomsConnectionDao, String dataKey, String executeServicename,String messagePattern,String query ){
		String response="";
		String responsexml="";
		Clob msgClob = null;
		try {
			
			CachedRowSet crs= RowSetProvider.newFactory().createCachedRowSet();
			nikeDomsConnectionDao.getDbValues(dataKey, executeServicename, "%"+messagePattern+"%",query , NikeDOMSConstants.DOMS);
			crs = nikeDomsConnectionDao.getRowSet();
			int size = crs.size();
			if(size>0){
				response="Success";
				msgClob = crs.getClob("message_xml");
				
			} else {
				response = "Warning";
				
			}
			
				if (null != msgClob) {
					responsexml = getStringFromClob(msgClob);
					System.out.println(responsexml);
				}
		
	}
		catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response+responsexml;
	}
	public String getOrderType(ArrayList<XmlTagLinesVO> xmlTagLinesVOList) {

		String orderType = "";
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {

			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO.getXmlTagAttributesVO()) {
				if ("OrderType".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
						orderType = xmlTagAttributesVO.getAttributeValue();
						break;
					}

				}
			}
		}

		return orderType;

	}	
	public String getShipNode(String orderNo,String primeLineNo,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO) throws SQLException, ClassNotFoundException {
		
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();
		String orderLineKey =  automationHelperImpl.getOrderLineKey(orderNo, primeLineNo, nikeDOMSConnectionDAO);
		String shipNode = "";
		CachedRowSet crs = null;
		
		
			crs = RowSetProvider.newFactory().createCachedRowSet();
			String query = "";
			query = NikeDOMSConstants.GET_SHIP_NODE;

			nikeDOMSConnectionDAO.getDBValues(orderLineKey, query, NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						shipNode = crs.getString("SHIP_NODE");

					}
				} else {
					while (crs.next()) {
						shipNode = crs.getString("SHIP_NODE");

					}
				}
			}

		return shipNode;
	}
	public String getCancelreqQty(String orderLineKey, NikeDOMSConnectionDAO nikedomsconnectiondao,String coulnmname)		
			throws ClassNotFoundException {		
		String returnOrderLineKey = "";		
		try {		
			nikedomsconnectiondao.getDBValues(orderLineKey, NikeDOMSConstants.getextnCancelrequestqty,		
					NikeDOMSConstants.DOMS);		
			CachedRowSet crs;		
			crs = RowSetProvider.newFactory().createCachedRowSet();		
			crs = nikedomsconnectiondao.getRowSet();		
			int size = crs.size();		
			crs.beforeFirst();		
			if (size > 0) {		
				if (size == 1) {		
					if (crs.next()) {		
						returnOrderLineKey = crs.getString(coulnmname);		
					}		
				} else {		
					while (crs.next()) {		
						returnOrderLineKey = crs.getString(coulnmname);		
					}		
				}		
			}		
		} catch (SQLException e) {		
			// TODO Auto-generated catch block		
			e.printStackTrace();		
		}		
		// TODO Auto-generated method stub		
		return returnOrderLineKey;		
	}
	public String geteachQTYStatus(String qty,String orderLineKey, NikeDOMSConnectionDAO nikedomsconnectiondao,String coulnmname)		
			throws ClassNotFoundException {		
		String returnOrderLineKey = "";		
		try {		
			nikedomsconnectiondao.getDBValues2parametres(qty,orderLineKey, NikeDOMSConstants.geteachQTYStatus,		
					NikeDOMSConstants.DOMS);		
			CachedRowSet crs;		
			crs = RowSetProvider.newFactory().createCachedRowSet();		
			crs = nikedomsconnectiondao.getRowSet();		
			int size = crs.size();		
			crs.beforeFirst();		
			if (size > 0) {		
				if (size == 1) {		
					if (crs.next()) {		
						returnOrderLineKey = crs.getString(coulnmname);		
					}		
				} else {		
					while (crs.next()) {		
						returnOrderLineKey = crs.getString(coulnmname);		
					}		
				}		
			}		
		} catch (SQLException e) {		
			// TODO Auto-generated catch block		
			e.printStackTrace();		
		}		
		// TODO Auto-generated method stub		
		return returnOrderLineKey;		
	}	
}
