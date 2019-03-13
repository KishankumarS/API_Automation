package com.doms.automation.generatexml.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import sun.font.CreatedFontTracker;
import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.XmlTagAttributesVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.jaxbclasses.order.Order;
import com.doms.automation.jaxbclasses.order.Order.Extn;
import com.doms.automation.jaxbclasses.order.Order.HeaderCharges;
import com.doms.automation.jaxbclasses.order.Order.HeaderTaxes;
import com.doms.automation.jaxbclasses.order.Order.Notes;
import com.doms.automation.jaxbclasses.order.Order.OrderLines;
import com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine;
import com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.Instructions;
import com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.Item;
import com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.LineCharges;
import com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.OrderDates;
import com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.PersonInfoShipTo;
import com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.Promotions;
import com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.Instructions.Instruction;
import com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.LineCharges.LineCharge;
import com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.LinePriceInfo;
import com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.LineTaxes;
import com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.LineTaxes.LineTax;
import com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.OrderDates.OrderDate;
import com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.Promotions.Promotion;
import com.doms.automation.jaxbclasses.order.Order.PaymentMethods;
import com.doms.automation.jaxbclasses.order.Order.PaymentMethods.PaymentMethod;
import com.doms.automation.jaxbclasses.order.Order.PaymentMethods.PaymentMethod.PaymentDetails;
import com.doms.automation.jaxbclasses.order.Order.PersonInfoBillTo;
import com.doms.automation.jaxbclasses.order.Order.PriceInfo;
import com.doms.automation.jaxbclasses.order.Order.Extn.ExtnStoreList;
import com.doms.automation.jaxbclasses.order.Order.Extn.ExtnUserAgentDetailsList;
import com.doms.automation.jaxbclasses.order.Order.Extn.ExtnStoreList.ExtnStore;
import com.doms.automation.jaxbclasses.order.Order.Extn.ExtnUserAgentDetailsList.ExtnUserAgentDetails;
import com.doms.automation.jaxbclasses.order.Order.HeaderCharges.HeaderCharge;
import com.doms.automation.jaxbclasses.order.Order.HeaderTaxes.HeaderTax;
import com.doms.automation.jaxbclasses.order.Order.Notes.Note;
import com.doms.automation.marshaller.impl.OrderMarshaller;
import com.doms.automation.service.CreateOrderService;
import com.doms.automation.utils.NikeDOMSApplicationUtils;

public class CreateOrderXMl {
	String orderNo;
	String orderXml;
	String authXml;
	ArrayList<XmlTagLinesVO> xmlTagLinesVOList;
	Order order;
	Map<String, String> resrvationIdsXml;
	NikeDOMSConnectionDAO nikeDOMSConnectionDAO;
	String environment;
	EnvConfigVO envConfigVO;
	public CreateOrderXMl(ArrayList<XmlTagLinesVO> xmlTagLinesVOList,
			String orderNo, String authXml,NikeDOMSConnectionDAO nikeDOMSConnectionDAO,
			String environment,EnvConfigVO envConfigVO) throws XPathExpressionException,
			ParserConfigurationException, SAXException, IOException, InterruptedException {
			this.orderNo = orderNo;
			this.nikeDOMSConnectionDAO=nikeDOMSConnectionDAO;
			this.environment=environment;
			this.envConfigVO=envConfigVO;
		this.xmlTagLinesVOList = xmlTagLinesVOList;
		this.authXml = authXml;
		generateXml();

	}

	public CreateOrderXMl(ArrayList<XmlTagLinesVO> xmlTagLinesVOList2,
			String orderNo2, String authResponse,
			Map<String, String> resrvationIdsXml,NikeDOMSConnectionDAO nikeDOMSConnectionDAO,
			String environment,EnvConfigVO envConfigVO)
			throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException, InterruptedException {
		this.orderNo = orderNo2;
		this.xmlTagLinesVOList = xmlTagLinesVOList2;
		this.authXml = authResponse;
		this.resrvationIdsXml = resrvationIdsXml;
		this.nikeDOMSConnectionDAO=nikeDOMSConnectionDAO;
		this.environment=environment;
		this.envConfigVO=envConfigVO;
		generateXml();
	}

	public String getOrderXml() throws JAXBException {

		OrderMarshaller orderMarshaller = new OrderMarshaller(order);
		orderXml = orderMarshaller.convertJaxbObjectToXML();
		return orderXml;
	}

	/* generate order xml */
	public void generateXml() throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException, InterruptedException {

		order = new Order();
		OrderLines orderLines = new OrderLines();
		OrderLine orderLine;
		PriceInfo priceInfo = new PriceInfo();
		PersonInfoBillTo personInfoBillTo = new PersonInfoBillTo();
		com.doms.automation.jaxbclasses.order.Order.PaymentMethods.PaymentMethod.PersonInfoBillTo paymentPersonInfoBillTo;
		Notes notes = new Notes();
		Extn extn = new Extn();
		HeaderCharges headerCharges = new HeaderCharges();
		HeaderTaxes headerTaxes = new HeaderTaxes();
		PaymentMethods paymentmethods = new PaymentMethods();

		ExtnStoreList extnStoreList = new ExtnStoreList();
		ExtnStore extnStore = new ExtnStore();
		HeaderCharge headerCharge;
		HeaderTax headerTax;
		PaymentMethod paymentMethod;
		PaymentDetails paymentDetails;
		com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.Extn orderLineExtn;
		Item item;
		LinePriceInfo linePriceInfo;
		LineCharges lineCharges;
		LineCharge lineCharge;
		LineTaxes lineTaxes;
		LineTax lineTax;
		OrderDates orderDates;
		OrderDate orderDateTag;
		PersonInfoShipTo personInfoShipTo;
		Instructions instructions;
		Instruction instruction;
		com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.Notes orderLineNotes;
		com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.LineTaxes.LineTax.Extn lineTaxExtn;
		String billToFirstName = "";
		String billToLastName = "";
		NikeDOMSApplicationUtils nikeUtils = new NikeDOMSApplicationUtils();
		AutomationHelperImpl automationHelper = new AutomationHelperImpl();
		String enterpriseCode = "";
		String orderType = "";
		String sgNo = "";
		String itemType = "";
		boolean isShipping = false;
		boolean isCOD = false;
		boolean isPaymentMethodCreated = false;
		boolean isOrderLineCreated = false;
		boolean isSwooshID = false;
		boolean isGCMsg = false;
		boolean isFeedzai = false;
		String reasonCode ="unknown";
		String paymentType = "";
		String lineType = "";
		List<Object> orderLineChildTags;
		// List <LineTax> lineTaxList;

		//String orderDate = nikeUtils.generateUniqueNo("yyyy-MM-dd'T'HH:mm:ss");
		String dbDate= nikeUtils.getDbDate(nikeDOMSConnectionDAO);
		String orderDate =dbDate.replace(" ", "T");
		if(orderNo.contains("Exc")){
		String[] x = orderNo.split("Exc");
		String ordNo = x[0];
		//int x1 = Integer.parseInt(x[1]);
		orderNo = ordNo;
		
		order.setEnteredBy("admin");
		order.setEntryType(22);
		order.setReturnOrderHeaderKeyForExchange(x[1]);
		order.setExchangeType("REGULAR");
		order.setOrderPurpose("EXCHANGE");
		}
		/* Setting attribute values in Order tag */
		order.setAllocationRuleID("SYSTEM");
		order.setBuyerUserId("763223");
		order.setDocumentType("0001");
		order.setOrderDate(orderDate);
		order.setOrderNo(orderNo);
		order.setSearchCriteria1("763223");
		order.setTaxExemptFlag("N");
		XmlTagLinesVO headerLine = new XmlTagLinesVO();
		headerLine = xmlTagLinesVOList.get(0);
		for (XmlTagAttributesVO xmlTagAttributesVO : headerLine
				.getXmlTagAttributesVO()) {
			if ("EnterpriseCode".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				order.setSellerOrganizationCode(xmlTagAttributesVO
						.getAttributeValue());
				order.setEnterpriseCode(xmlTagAttributesVO.getAttributeValue());
				enterpriseCode = xmlTagAttributesVO.getAttributeValue();
				sgNo = generateSgNumber(enterpriseCode);
			}
			if ("PersonInfoBillTo_EMailID".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				order.setCustomerEMailID(xmlTagAttributesVO.getAttributeValue());
			}
			if ("Division".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				order.setDivision(xmlTagAttributesVO.getAttributeValue());
			}
			if ("OrderType".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				order.setOrderType(xmlTagAttributesVO.getAttributeValue());
				orderType = xmlTagAttributesVO.getAttributeValue();
			}
			if ("Currency".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				priceInfo.setCurrency(xmlTagAttributesVO.getAttributeValue());
			}
			if ("PersonInfoBillTo_AddressLine1"
					.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
				personInfoBillTo.setAddressLine1(xmlTagAttributesVO
						.getAttributeValue());
			}
			 if("PersonInfoBillTo_AddressLine2".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())){
				 personInfoBillTo.setAddressLine2(xmlTagAttributesVO.getAttributeValue());
				 }
			if ("PersonInfoBillTo_City".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				personInfoBillTo
						.setCity(xmlTagAttributesVO.getAttributeValue());
			}
			if ("PersonInfoBillTo_Country".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				personInfoBillTo.setCountry(xmlTagAttributesVO
						.getAttributeValue());
			}
			if ("PersonInfoBillTo_DayPhone".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				personInfoBillTo.setDayPhone(xmlTagAttributesVO
						.getAttributeValue());
			}
			if ("PersonInfoBillTo_EMailID".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				personInfoBillTo.setEMailID(xmlTagAttributesVO
						.getAttributeValue());
			}
			if ("PersonInfoBillTo_FirstName"
					.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
				personInfoBillTo.setFirstName(xmlTagAttributesVO
						.getAttributeValue());
				billToFirstName=xmlTagAttributesVO
						.getAttributeValue();
			}
			if ("PersonInfoBillTo_LastName".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				personInfoBillTo.setLastName(xmlTagAttributesVO
						.getAttributeValue());
				billToLastName=xmlTagAttributesVO
						.getAttributeValue();
			}

			if ("PersonInfoBillTo_State".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				personInfoBillTo.setState(xmlTagAttributesVO
						.getAttributeValue());
			}
			if ("PersonInfoBillTo_ZipCode".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				personInfoBillTo.setZipCode(xmlTagAttributesVO
						.getAttributeValue());
			}
			if ("ExtnLocale".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				extn.setExtnLocale(xmlTagAttributesVO.getAttributeValue());
			}
			if ("FapiaoNeeded".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {
				if (xmlTagAttributesVO.getAttributeValue().equalsIgnoreCase(
						"Yes")) {
					personInfoBillTo.setCompany("Personal");
				}

			}

			/* Setting studio ID (Only for Studio orders */
			if (orderType.equalsIgnoreCase("POS")
					&& "Studio_Id".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
				extn.setExtnStudioID(xmlTagAttributesVO.getAttributeValue());
			}
			/* Setting store details (Only for MPOS orders */
			if (orderType.equalsIgnoreCase("STORE")
					&& "Mpos_StoreID".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
				extnStore
						.setExtnStoreID(xmlTagAttributesVO.getAttributeValue());
			}
			if (orderType.equalsIgnoreCase("STORE")
					&& "Mpos_RegisterNo".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
				extnStore.setExtnRegisterNo(xmlTagAttributesVO
						.getAttributeValue());
			}
			if ("ShippingCharge(Header)".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {

				if (!xmlTagAttributesVO.getAttributeValue()
						.equalsIgnoreCase("")) {
					headerCharge = new HeaderCharge();
					headerCharge
							.setChargeAmount(Float
									.parseFloat(xmlTagAttributesVO
											.getAttributeValue()));
					headerCharge.setChargeCategory("Shipping");
					headerCharge.setChargeName("0");
					headerCharge.setReference(sgNo);
					headerCharges.getHeaderCharge().add(headerCharge);

					isShipping = true;
				}
			}
			if (isShipping) {
				headerTax = new HeaderTax();
				com.doms.automation.jaxbclasses.order.Order.HeaderTaxes.HeaderTax.Extn taxExtn = new com.doms.automation.jaxbclasses.order.Order.HeaderTaxes.HeaderTax.Extn();
				taxExtn.setExtnReference4(nikeUtils
						.generateUniqueNo("yyyy-MM-dd'T'HH:mm:ss"));
				for (XmlTagAttributesVO xmlTagAttributesVO1 : headerLine
						.getXmlTagAttributesVO()) {

					if ("ShippingTaxPercentage(Header)Reference3"
							.equalsIgnoreCase(xmlTagAttributesVO1
									.getAttributeName())) {
						headerTax.setReference3(Float
								.parseFloat(xmlTagAttributesVO1
										.getAttributeValue()));
						headerTax.setChargeName(sgNo);
						headerTax.setReference1(sgNo);
						headerTax.setTaxName("SHIPPINGTAX");
						headerTax.setChargeCategory("Shipping");
					}
					if ("ShippingTaxPercentage(Header)"
							.equalsIgnoreCase(xmlTagAttributesVO1
									.getAttributeName())) {
						headerTax.setTaxPercentage(Float
								.parseFloat(xmlTagAttributesVO1
										.getAttributeValue()));
					}
					if ("ShippingTax(Header)Reference2"
							.equalsIgnoreCase(xmlTagAttributesVO1
									.getAttributeName())) {
						headerTax.setReference2(xmlTagAttributesVO1
								.getAttributeValue());
					}
					if ("ShippingTax(Header)"
							.equalsIgnoreCase(xmlTagAttributesVO1
									.getAttributeName())) {
						headerTax.setTax(Float.parseFloat(xmlTagAttributesVO1
								.getAttributeValue()));
						isShipping = false;
						com.doms.automation.jaxbclasses.order.Order.HeaderTaxes.HeaderTax.Extn HeaderTaxExtn = new com.doms.automation.jaxbclasses.order.Order.HeaderTaxes.HeaderTax.Extn();
						// HeaderTaxExtn.setExtnReference4(orderDate);
						// headerTax.setExtn(HeaderTaxExtn);
						headerTaxes.getHeaderTax().add(headerTax);
					}
					if ("ShippingDiscount".equalsIgnoreCase(xmlTagAttributesVO1
							.getAttributeName())) {

						if (!xmlTagAttributesVO1.getAttributeValue()
								.equalsIgnoreCase("")) {
							headerCharge = new HeaderCharge();
							headerCharge
									.setChargeAmount(Float
											.parseFloat(xmlTagAttributesVO1
													.getAttributeValue()));
							headerCharge.setChargeCategory("Discount");
							headerCharge.setChargeName("42-1442427816171-1");
							headerCharge.setReference(sgNo);
							headerCharges.getHeaderCharge().add(headerCharge);

						}
					}

				}
				
			}
			if ("COD Charge(Header)".equalsIgnoreCase(xmlTagAttributesVO
					.getAttributeName())) {

				if (!xmlTagAttributesVO.getAttributeValue()
						.equalsIgnoreCase("")) {
					headerCharge = new HeaderCharge();
					headerCharge
							.setChargeAmount(Float
									.parseFloat(xmlTagAttributesVO
											.getAttributeValue()));
					headerCharge.setChargeCategory("COD");
					headerCharge.setChargeName("COD_FEE0");
					headerCharge.setReference(sgNo);
					headerCharges.getHeaderCharge().add(headerCharge);

					isCOD = true;
				}
			}
			if ("ExtnCode"
					.equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
				isFeedzai=xmlTagAttributesVO
						.getAttributeValue().contains("Feedzai")?true:false;
				reasonCode=xmlTagAttributesVO
						.getAttributeValue().substring(xmlTagAttributesVO
								.getAttributeValue()
						.indexOf("_") + 1);
			}

		}

		/* Line level Updates */
		String parentLine = "";
		for (XmlTagLinesVO xmlTagLinesVO1 : xmlTagLinesVOList) {
			orderLine = new OrderLine();
			paymentPersonInfoBillTo = new com.doms.automation.jaxbclasses.order.Order.PaymentMethods.PaymentMethod.PersonInfoBillTo();
			paymentMethod = new PaymentMethod();
			paymentDetails = new PaymentDetails();
			orderLine = new OrderLine();
			orderLineExtn = new com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.Extn();
			item = new Item();
			linePriceInfo = new LinePriceInfo();
			lineCharges = new LineCharges();
			lineCharge = new LineCharge();
			lineTaxes = new LineTaxes();
			lineTax = new LineTax();
			lineTaxExtn = new com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.LineTaxes.LineTax.Extn();
			isPaymentMethodCreated = true;
			
			String reservationID = "";
			orderDates = new OrderDates();
			personInfoShipTo = new PersonInfoShipTo();
			orderLineNotes = new com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.Notes();
			instructions = new Instructions();
			instruction = new Instruction();
			isSwooshID = false;
			boolean isLineCharges = false;
			boolean isPaymentDetailsOptional = false;
			paymentType = "";
			boolean isIc = false;
			String giftMessage = "";
			boolean iscreditTypeFirstTime=true;
			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO1
					.getXmlTagAttributesVO()) {
				
				if ("PaymentType".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					paymentType = xmlTagAttributesVO.getAttributeValue();
				}

				if ("PersonInfoBillTo_AddressLine1"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					paymentPersonInfoBillTo.setAddressLine1(xmlTagAttributesVO
							.getAttributeValue());
				}
				if ("PersonInfoBillTo_City".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					paymentPersonInfoBillTo.setCity(xmlTagAttributesVO
							.getAttributeValue());
				}
				if ("PersonInfoBillTo_Country"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					paymentPersonInfoBillTo.setCountry(xmlTagAttributesVO
							.getAttributeValue());
				}
				if ("PersonInfoBillTo_DayPhone"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					paymentPersonInfoBillTo.setDayPhone(xmlTagAttributesVO
							.getAttributeValue());
				}
				if ("PersonInfoBillTo_EMailID"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					paymentPersonInfoBillTo.setEMailID(xmlTagAttributesVO
							.getAttributeValue());
				}
				if ("PersonInfoBillTo_FirstName"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					paymentPersonInfoBillTo.setFirstName(xmlTagAttributesVO
							.getAttributeValue());
					billToFirstName = xmlTagAttributesVO.getAttributeValue();
					
				}
				if ("PersonInfoBillTo_LastName"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					paymentPersonInfoBillTo.setLastName(xmlTagAttributesVO
							.getAttributeValue());
					billToLastName = xmlTagAttributesVO.getAttributeValue();
				}

				if ("PersonInfoBillTo_State"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					paymentPersonInfoBillTo.setState(xmlTagAttributesVO
							.getAttributeValue());
				}
				if ("PersonInfoBillTo_ZipCode"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					paymentPersonInfoBillTo.setZipCode(xmlTagAttributesVO
							.getAttributeValue());
				}

				if (paymentType.equalsIgnoreCase("CREDIT_CARD")) {
					if(iscreditTypeFirstTime){
						ReqResVO reqResVO = new ReqResVO();
						
						CreateOrderService createOrderservice = new CreateOrderService();
						reqResVO = createOrderservice.authXML(environment,
								automationHelper.getAuthXml(xmlTagLinesVOList, orderNo), envConfigVO,
								reqResVO);
						authXml=reqResVO.authResponse;	
						iscreditTypeFirstTime=false;
					}
					/* setting the static values */

					if (isPaymentMethodCreated) {
						
						/*
						 * get values from auth response xml
						 */
						DocumentBuilderFactory factory = DocumentBuilderFactory
								.newInstance();
						DocumentBuilder db = factory.newDocumentBuilder();
						InputSource inStream = new InputSource(
								new StringReader(authXml));

						Document doc = db.parse(inStream);

						XPathFactory xFactory = XPathFactory.newInstance();
						XPath xpath = xFactory.newXPath();

						String authTime = (String) xpath.evaluate(
								"/Payment/@AuthTime", doc,
								XPathConstants.STRING);
						String authorizationCode = (String) xpath.evaluate(
								"/Payment/@AuthorizationCode", doc,
								XPathConstants.STRING);
						String authExpDate = (String) xpath.evaluate(
								"/Payment/@AuthorizationExpirationDate", doc,
								XPathConstants.STRING);
						String requestId = (String) xpath.evaluate(
								"/Payment/@RequestId", doc,
								XPathConstants.STRING);
						String tranReturnMessage = (String) xpath.evaluate(
								"/Payment/@TranReturnMessage", doc,
								XPathConstants.STRING);
						String customerPONo = (String) xpath.evaluate(
								"/Payment/@CustomerPONo", doc,
								XPathConstants.STRING);

						paymentMethod.setChargeSequence("0");
						paymentMethod.setPaymentType(paymentType);
						paymentMethod.setPaymentReference1("0");
						paymentMethod.setPaymentReference2("1");
						paymentDetails.setAuthReturnCode("0");
						paymentDetails.setAuthAvs("U");
						paymentDetails.setCVVAuthCode("P");
						paymentDetails.setChargeType("AUTHORIZATION");
						paymentDetails.setHoldAgainstBook("Y");
						paymentDetails.setRequestProcessed("Y");
						paymentDetails.
						setAuthTime(authTime);
						paymentDetails.setAuthorizationID(authorizationCode);
						paymentDetails.setAuthCode(authorizationCode);
						paymentDetails
								.setAuthorizationExpirationDate(authExpDate);
						paymentDetails.setRequestId(requestId);
						paymentDetails.setTranReturnMessage(tranReturnMessage);
						paymentMethod.setCreditCardNo(tranReturnMessage);
						paymentDetails.setInternalReturnMessage(customerPONo);
						paymentMethod.setCustomerPONo(customerPONo);

						isPaymentMethodCreated = false;

					}
					if ("CreditCardExpDate".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCreditCardExpDate(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("CreditCardName".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCreditCardName(xmlTagAttributesVO
								.getAttributeValue());
					}

					/* to-do: add credit card no,customerPONo */
					if ("CreditCardType".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCreditCardType(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("NikeAccount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCustomerAccountNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("DisplayCCNumber".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setDisplayCreditCardNo(Short
								.parseShort(xmlTagAttributesVO
										.getAttributeValue()));

					}
					if ("PersonInfoBillTo_FirstName"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						paymentMethod.setFirstName(billToFirstName);

					}
					if ("PersonInfoBillTo_LastName"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						paymentMethod.setLastName(billToLastName);

					}
					if ("MaxChargeLimit".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setMaxChargeLimit(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));

					}

					if ("ProcessedAmount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentDetails.setProcessedAmount(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
					}
					if ("RequestAmount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentDetails.setRequestAmount(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
					}

				}
				/*
				 * To-Do: RTBT- how to get req id, trn ret msg
				 * if(paymentType.equalsIgnoreCase("REALTIME_BANK")){ }
				 */
				/* To-Do: PAYPAL */
				if (paymentType.equalsIgnoreCase("CUSTOMER_ACCOUNT")) {

					/* setting the static values */
					if (isPaymentMethodCreated) {
						paymentMethod.setChargeSequence("0");
						paymentMethod.setPaymentType(paymentType);
						paymentMethod.setUnlimitedCharges("N");
						paymentMethod.setPaymentReference1("0");
						paymentMethod.setCheckReference("0");
						paymentMethod.setCustomerPONo("0");
						paymentDetails.setAuthReturnCode("0");
						paymentDetails.setAuthTime(orderDate);
						paymentDetails.setChargeType("CHARGE");

						/* to-do: add requestId & TranReturnMessagefor GC */
						paymentDetails.setTranReturnMessage("1411473772054");
						isPaymentMethodCreated = false;

					}
					if ("NikeAccount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCustomerAccountNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("MaxChargeLimit".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setMaxChargeLimit(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));

					}

					if ("ProcessedAmount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentDetails.setProcessedAmount(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
					}
					if ("RequestAmount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentDetails.setRequestAmount(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
					}
				}
				if (paymentType.equalsIgnoreCase("GIFT_CERTIFICATE")) {

					/* setting the static values */
					if (isPaymentMethodCreated) {
						paymentMethod.setChargeSequence("0");
						paymentMethod.setPaymentType(paymentType);
						paymentMethod.setUnlimitedCharges("N");
						paymentMethod.setPaymentReference1("0");
						paymentMethod.setCustomerPONo(automationHelper
								.generatePONumber(xmlTagLinesVOList.get(0),
										orderNo));
						paymentDetails.setAuthTime(orderDate);
						paymentDetails.setChargeType("CHARGE");
						paymentDetails.setHoldAgainstBook("Y");

						/* to-do: add requestId & TranReturnMessagefor GC */
						paymentDetails.setRequestId("");
						paymentDetails.setTranReturnMessage("");

						paymentDetails.setInternalReturnMessage(orderNo);
						isPaymentMethodCreated = false;

					}
					if ("NikeAccount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCustomerAccountNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("MaxChargeLimit".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setMaxChargeLimit(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));

					}
					if ("DisplayCCNumber".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setDisplaySvcNo(xmlTagAttributesVO
								.getAttributeValue());

					}

					if ("CreditCardNum".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setSvcNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("ProcessedAmount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentDetails.setProcessedAmount(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
					}
					if ("RequestAmount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentDetails.setRequestAmount(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
					}
				}
				if (paymentType.equalsIgnoreCase("OFFLINE_BANK")) {

					/* setting the static values */
					if (isPaymentMethodCreated) {
						paymentMethod.setChargeSequence("0");
						paymentMethod.setPaymentType(paymentType);
						paymentMethod.setUnlimitedCharges("N");
						paymentMethod.setPaymentReference1("0");
						/* to-do: check payment ref2,3, CheckReference, checkno */
						paymentMethod.setPaymentReference2("00"
								+ nikeUtils.generateUniqueNo("ddHHmmss"));
						paymentMethod.setPaymentReference3("20-00-00");
						paymentMethod
								.setCheckReference("Global Collect BV|141453692527");
						paymentMethod.setCheckNo(Integer.parseInt(nikeUtils
								.generateUniqueNo("ddHHmmss")));
						paymentMethod.setCustomerPONo(automationHelper
								.generatePONumber(xmlTagLinesVOList.get(0),
										orderNo));
						paymentMethod.setFirstName(billToFirstName);
						paymentMethod.setLastName(billToLastName);
						isPaymentMethodCreated = false;
						isPaymentDetailsOptional = true;

					}
					if ("CreditCardName".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCreditCardName(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("CreditCardNum".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCreditCardNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("NikeAccount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCustomerAccountNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("MaxChargeLimit".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setMaxChargeLimit(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));

					}

				}
				if (paymentType.equalsIgnoreCase("SOFORT")) {

					/* setting the static values */
					if (isPaymentMethodCreated) {
						paymentMethod.setChargeSequence("0");
						paymentMethod.setPaymentType(paymentType);
						paymentMethod.setUnlimitedCharges("N");
						paymentMethod.setPaymentReference1("0");
						paymentMethod.setPaymentReference2(sgNo);
						paymentMethod.setCustomerPONo(automationHelper
								.generatePONumber(xmlTagLinesVOList.get(0),
										orderNo));
						paymentDetails.setChargeType("CHARGE");
						paymentDetails.setHoldAgainstBook("Y");

						/* to-do: add requestId & TranReturnMessagefor GC */
						paymentDetails.setRequestId("");
						paymentDetails.setTranReturnMessage("");

						isPaymentMethodCreated = false;

					}
					if ("NikeAccount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCustomerAccountNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("MaxChargeLimit".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setMaxChargeLimit(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));

					}
					if ("ProcessedAmount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentDetails.setProcessedAmount(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
					}
					if ("RequestAmount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentDetails.setRequestAmount(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
					}

				}
				if (paymentType.equalsIgnoreCase("CONV_STORE")) {

					/* setting the static values */
					if (isPaymentMethodCreated) {
						paymentMethod.setChargeSequence("0");
						paymentMethod.setPaymentType(paymentType);
						paymentMethod.setUnlimitedCharges("N");
						paymentMethod.setPaymentReference1("0");
						paymentMethod.setPaymentReference2(nikeUtils.generateUniqueNo("yymmddhhMMss"));
						paymentMethod.setCustomerPONo(orderNo);
						isPaymentDetailsOptional = true;
						isPaymentMethodCreated = false;

					}
					if ("NikeAccount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCustomerAccountNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("MaxChargeLimit".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setMaxChargeLimit(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));

					}

				}

				if (paymentType.equalsIgnoreCase("POS_PURCHASE")) {
					if (isPaymentMethodCreated) {
						paymentMethod.setChargeSequence("0");
						paymentMethod.setPaymentType(paymentType);
						paymentMethod.setUnlimitedCharges("N");
						paymentMethod.setPaymentReference1("0");
						isPaymentDetailsOptional = true;
						isPaymentMethodCreated = false;

					}
					if ("MaxChargeLimit".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setMaxChargeLimit(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));

					}

				}
				if (paymentType.equalsIgnoreCase("ALIPAY")) {

					/* setting the static values */
					if (isPaymentMethodCreated) {
						paymentMethod.setChargeSequence("0");
						paymentMethod.setPaymentType(paymentType);
						paymentMethod.setUnlimitedCharges("N");
						paymentMethod.setPaymentReference1("0");
						paymentMethod.setPaymentReference2(orderNo);
						paymentMethod.setCustomerPONo("0");
						paymentDetails.setChargeType("AUTHORIZATION");
						String reqId = nikeUtils
								.generateUniqueNo("yyyyMMddHHmmss");
						/* to-do: check these 2 values */
						paymentDetails.setRequestId(reqId);
						paymentDetails.setTranReturnMessage(reqId);
					}
					if ("NikeAccount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCustomerAccountNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("MaxChargeLimit".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setMaxChargeLimit(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));

					}
					if ("PersonInfoBillTo_FirstName"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						paymentMethod.setFirstName(xmlTagAttributesVO
								.getAttributeValue());

					}
				}
				if (paymentType.equalsIgnoreCase("TENPAY")) {

					/* setting the static values */
					if (isPaymentMethodCreated) {
						paymentMethod.setChargeSequence("0");
						paymentMethod.setPaymentType(paymentType);
						paymentMethod.setUnlimitedCharges("N");
						paymentMethod.setPaymentReference1("0");
						paymentMethod.setPaymentReference2(sgNo);
						paymentMethod.setCustomerPONo("0");
						paymentDetails.setChargeType("AUTHORIZATION");
						String reqId = nikeUtils
								.generateUniqueNo("yyyyMMddHHmmss");
						/* to-do: check these 2 values */
						paymentDetails.setRequestId(reqId);
						paymentDetails.setTranReturnMessage(reqId);
					}
					if ("NikeAccount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCustomerAccountNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("MaxChargeLimit".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setMaxChargeLimit(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));

					}
				}
				if (paymentType.equalsIgnoreCase("UNIONPAY")) {

					/* setting the static values */
					if (isPaymentMethodCreated) {
						paymentMethod.setChargeSequence("0");
						paymentMethod.setPaymentType(paymentType);
						paymentMethod.setUnlimitedCharges("N");
						paymentMethod.setPaymentReference1("0");
						paymentMethod.setPaymentReference2(sgNo);
						paymentMethod.setCustomerPONo("0");
						paymentDetails.setChargeType("AUTHORIZATION");
						String reqId = nikeUtils
								.generateUniqueNo("yyyyMMddHHmmss");
						/* to-do: check these 2 values */
						paymentDetails.setRequestId(reqId);
						paymentDetails.setTranReturnMessage(reqId);
					}
					if ("NikeAccount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCustomerAccountNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("MaxChargeLimit".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setMaxChargeLimit(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));

					}

				}
				if (paymentType.equalsIgnoreCase("COD")) {

					/* setting the static values */
					if (isPaymentMethodCreated) {
						paymentMethod.setChargeSequence("0");
						paymentMethod.setPaymentType(paymentType);
						paymentMethod.setUnlimitedCharges("N");
						paymentMethod.setPaymentReference1("0");
						paymentMethod.setCheckReference("0");
						paymentMethod.setCustomerPONo("0");
						paymentDetails.setChargeType("AUTHORIZATION");
						paymentDetails.setAuthTime(orderDate);
						paymentDetails.setTranReturnMessage("");
					}
					if ("NikeAccount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCustomerAccountNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("MaxChargeLimit".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setMaxChargeLimit(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));

					}
					if ("ProcessedAmount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentDetails.setProcessedAmount(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
					}
					if ("RequestAmount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentDetails.setRequestAmount(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
					}

				}
				
				//added
				if (paymentType.equalsIgnoreCase("WECHAT")) {

					/* setting the static values */
					if (isPaymentMethodCreated) {
						paymentMethod.setChargeSequence("0");
						paymentMethod.setPaymentType(paymentType);
						paymentMethod.setUnlimitedCharges("N");
						paymentMethod.setPaymentReference1("0");
						paymentMethod.setPaymentReference2(orderNo);
						paymentMethod.setCustomerAccountNo("cnew");
						paymentMethod.setCustomerPONo("0");
						paymentDetails.setChargeType("AUTHORIZATION");
						String reqId = nikeUtils
								.generateUniqueNo("yyyyMMddHHmmss");
						/* to-do: check these 2 values */
						//changed order num _m 
						paymentDetails.setRequestId(orderNo);
						paymentDetails.setTranReturnMessage(orderNo);
						//paymentDetails.setRequestId(reqId);
						//paymentDetails.setTranReturnMessage(reqId);
					}
					if ("NikeAccount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCustomerAccountNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("MaxChargeLimit".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setMaxChargeLimit(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
					}
					if ("PersonInfoBillTo_FirstName"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						paymentMethod.setFirstName(xmlTagAttributesVO
								.getAttributeValue());
					}
				}
				
				if (paymentType.equalsIgnoreCase("KLARNA")) {

					/* setting the static values */
					if (isPaymentMethodCreated) {
						paymentMethod.setChargeSequence("0");
						paymentMethod.setPaymentType(paymentType);
						paymentMethod.setUnlimitedCharges("N");
						paymentMethod.setPaymentReference1("0");
						paymentDetails.setAuthTime(orderDate);
						paymentDetails.setChargeType("AUTHORIZATION");
						paymentDetails.setHoldAgainstBook("Y");

						paymentDetails.setAuthReturnCode("0");
						paymentDetails.setAuthAvs("U");
						paymentDetails.setCVVAuthCode("P");
						paymentDetails.setChargeType("AUTHORIZATION");
						paymentDetails.setHoldAgainstBook("Y");
						paymentDetails.setRequestProcessed("Y");
						
						paymentDetails.setAuthorizationID("1");
						paymentDetails.setAuthCode("1");
						paymentDetails
								.setAuthorizationExpirationDate(orderDate);
						//paymentDetails.setRequestId("");
						String reqId = nikeUtils
								.generateUniqueNo("yyyyMMddHHmmss");
						paymentDetails.setRequestId(reqId);
						isPaymentMethodCreated = false;

					}
					if ("NikeAccount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCustomerAccountNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("MaxChargeLimit".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setMaxChargeLimit(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));

					}
					if ("CreditCardNum".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCreditCardNo(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("CreditCardType".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentMethod.setCreditCardType(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("ProcessedAmount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentDetails.setProcessedAmount(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
					}
					if ("RequestAmount".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeName())) {
						paymentDetails.setRequestAmount(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
					}
				}
				/*
				 * Adding OrderLine values
				 */
				if ("nParentLineNo".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {

					if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
						orderLine.setNParentLineNo(xmlTagAttributesVO
								.getAttributeValue());
						parentLine = xmlTagAttributesVO.getAttributeValue();
						isSwooshID = true;
					}

				}
				if ("LineType".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					itemType = xmlTagAttributesVO.getAttributeValue();
					lineType = xmlTagAttributesVO.getAttributeValue();
					orderLine.setLineType(lineType);
					isOrderLineCreated = true;
							
				}
				if ("OutfitID".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {

					if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
						String outfitID = xmlTagAttributesVO
								.getAttributeValue();
						if(!"NikeID".equalsIgnoreCase(itemType)){
							orderLine.setDepartmentCode("omega-gu-j6O0D8lL4qu33kpGujvO-"+outfitID);
						}else{
							orderLine.setDepartmentCode(outfitID);
						}
						
						
					}

				}
				if ("IC".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if ("Yes".equalsIgnoreCase(xmlTagAttributesVO
							.getAttributeValue())) {
						isIc = true;
					}

				}
				if ("LineNo".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					orderLine.setPrimeLineNo(xmlTagAttributesVO
							.getAttributeValue());

				}
				
				if ("CarrierServiceCode".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					orderLine.setCarrierServiceCode(xmlTagAttributesVO
							.getAttributeValue());
				}
				if ("GiftFlag".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					orderLine.setGiftFlag(xmlTagAttributesVO
							.getAttributeValue());
				}
				if ("OrderedQty".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					orderLine.setOrderedQty(xmlTagAttributesVO
							.getAttributeValue());
				}
				if ("LineNo".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					orderLine.setPrimeLineNo(xmlTagAttributesVO
							.getAttributeValue());
				}
				if ("ExtnCommodityCode".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					orderLineExtn.setExtnCommodityCode(xmlTagAttributesVO
							.getAttributeValue());
				}
				if ("ExtnProductId".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						orderLineExtn.setExtnProductId(xmlTagAttributesVO
								.getAttributeValue());
				}
				if ("ExtnProductId".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						orderLineExtn.setExtnProductId(xmlTagAttributesVO
								.getAttributeValue());
				}
				if ("ExtnColorNumber".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						orderLineExtn.setExtnColorNumber(xmlTagAttributesVO
								.getAttributeValue());
				}
				if ("ExtnColorDescription".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						orderLineExtn
								.setExtnColorDescription(xmlTagAttributesVO
										.getAttributeValue());
				}
				if ("ExtnDisplaySize".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						orderLineExtn.setExtnDisplaySize(xmlTagAttributesVO
								.getAttributeValue());
					orderLineExtn.setExtnSizeDescription(xmlTagAttributesVO
							.getAttributeValue());

				}
				if ("ExtnStyleNumber".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						orderLineExtn.setExtnStyleNumber(xmlTagAttributesVO
								.getAttributeValue());
				}
				if ("ItemDesc".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					item.setItemDesc(xmlTagAttributesVO.getAttributeValue());
				}
				if ("UPCCode".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					item.setUPCCode(xmlTagAttributesVO.getAttributeValue());
				}
				if ("ItemID".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					item.setItemID(xmlTagAttributesVO.getAttributeValue());
				}
				if ("ListPrice".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					linePriceInfo
							.setListPrice(Float.parseFloat(xmlTagAttributesVO
									.getAttributeValue()));
				}
				if ("RetailPrice".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					linePriceInfo
							.setRetailPrice(Float.parseFloat(xmlTagAttributesVO
									.getAttributeValue()));
					if(lineType.equals("EGC")){
					orderLineExtn.setExtnDenomination(Float.parseFloat(xmlTagAttributesVO
									.getAttributeValue()));
					}
				}
				if ("UnitPrice".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					linePriceInfo
							.setUnitPrice(Float.parseFloat(xmlTagAttributesVO
									.getAttributeValue()));
				}
				if ("ItemDiscountperunit(Linelevel)"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
						lineCharge.setChargePerUnit(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
						isLineCharges = true;
					}
				}
				if ("DiscountType(ChargeCategory)"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						lineCharge.setChargeCategory(xmlTagAttributesVO
								.getAttributeValue());
				}
				if ("DiscountReasonName(ChargeName)"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals(""))) {
						lineCharge.setChargeName(xmlTagAttributesVO
								.getAttributeValue());
						if (isLineCharges) {
							lineCharges.getLineCharge().add(lineCharge);
							com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.LineCharges.LineCharge.Extn lineChargeExtn = new com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.LineCharges.LineCharge.Extn();
							lineCharge.setExtn(lineChargeExtn);
							isLineCharges = false;
						}
					}
				}
				if ("DiscountReasonCode(Reference)"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						lineCharge.setReference(xmlTagAttributesVO
								.getAttributeValue());
				}
				if ("ItemTaxName".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						lineTax.setTaxName(xmlTagAttributesVO
								.getAttributeValue());
				}
				if ("ItemTax(Line Level)".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						lineTax.setTax(Float.parseFloat(xmlTagAttributesVO
								.getAttributeValue()));
				}
				if ("TaxChargeName".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						lineTax.setChargeName(xmlTagAttributesVO
								.getAttributeValue());
				}
				if ("TaxChargeCategory".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						lineTax.setChargeCategory(xmlTagAttributesVO
								.getAttributeValue());
				}
				if ("ItemTaxPercentage(LineLevel)Reference3"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						lineTax.setReference3(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
				}
				if ("ItemTax(LineLevel)Reference2"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						lineTax.setReference2(xmlTagAttributesVO
								.getAttributeValue());
				}
				if ("ItemTaxPercentage(LineLevel)"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						lineTax.setTaxPercentage(Float
								.parseFloat(xmlTagAttributesVO
										.getAttributeValue()));
					// lineTaxExtn.setExtnReference4(orderDate);
					// lineTax.setExtn(lineTaxExtn);
				}
				if ("GiftMessage".equalsIgnoreCase(xmlTagAttributesVO
						.getAttributeName())) {
					if (!(xmlTagAttributesVO.getAttributeValue().equals("")))
						isGCMsg = true;
					giftMessage = xmlTagAttributesVO.getAttributeValue();
				}
				if (lineType.equalsIgnoreCase("NIKEID") || isSwooshID) {
					if(resrvationIdsXml == null){
					orderLine
							.setReservationID(generateReservationID(enterpriseCode));
					}
					if (!lineType.equalsIgnoreCase("SERVICE")) {
						if ("ExtnMetricID".equalsIgnoreCase(xmlTagAttributesVO
								.getAttributeName())) {
							orderLineExtn.setExtnMetricID(xmlTagAttributesVO
									.getAttributeValue());
							if (isSwooshID && !(xmlTagAttributesVO
									.getAttributeValue().equals(""))) {
								instruction
										.setInstructionText(xmlTagAttributesVO
												.getAttributeValue());
								instruction.setInstructionType("METRICID");
								instructions.setInstruction(instruction);
								orderLine.setInstructions(instructions);
							}
						}
					}
				}
				if (isGCMsg) {
					instruction.setInstructionText(giftMessage);
					instruction.setInstructionType("GIFTMSG");
					instructions.setInstruction(instruction);
					orderLine.setInstructions(instructions);
					isGCMsg = false;
					orderLineExtn.setExtnGiftMsg("99162322");
				}
				if (!lineType.equals("EGC")) {
					if ("PersonInfoShipTo_AddressID"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						personInfoShipTo.setAddressID(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("PersonInfoShipTo_AddressLine1"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						personInfoShipTo.setAddressLine1(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("PersonInfoShipTo_AddressLine2"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						personInfoShipTo.setAddressLine2(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("PersonInfoShipTo_City"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						personInfoShipTo.setCity(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("PersonInfoShipTo_Country"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						personInfoShipTo.setCountry(xmlTagAttributesVO
								.getAttributeValue());
					}

					if ("PersonInfoShipTo_State"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						personInfoShipTo.setState(xmlTagAttributesVO
								.getAttributeValue());
					}
					if ("PersonInfoShipTo_ZipCode"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						personInfoShipTo.setZipCode(xmlTagAttributesVO
								.getAttributeValue());
					}
				}
				if ("PersonInfoShipTo_FirstName"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					personInfoShipTo.setFirstName(xmlTagAttributesVO
							.getAttributeValue());
				}
				if ("PersonInfoShipTo_LastName"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					personInfoShipTo.setLastName(xmlTagAttributesVO
							.getAttributeValue());
				}
				if ("PersonInfoShipTo_EMailID"
						.equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())) {
					personInfoShipTo.setEMailID(xmlTagAttributesVO
							.getAttributeValue());
				}

				/* assigning static values for orderLine tag */

				if (isOrderLineCreated) {
					if(lineType.equals("EGC")){
						orderLineExtn.setExtnSenderFirstName(billToFirstName);
						orderLineExtn.setExtnSenderLastName(billToLastName);
					}
					if (orderType.equalsIgnoreCase("STORE")) {
						orderLine.setAction("");
						orderLine.setItemNotExist("N");
						orderLineExtn.setExtnComputeTax("N");
						orderLineExtn.setExtnEntryMethod("KEYD");
						linePriceInfo.setTaxableFlag("Y");
						lineCharges.setReset("Y");

					}
					if (lineType.equalsIgnoreCase("NIKEID")) {
						reservationID = generateReservationID(enterpriseCode);
						orderLine.setReservationID(reservationID);
						orderLineExtn.setExtnFTCAddnlDays("30");
						orderLineExtn.setExtnIsPreOrder("N");
						orderLineExtn.setExtnClearance("N");
						orderDateTag = new OrderDate();
						orderDateTag.setDateTypeId("FTC_EVENT_DATE_1");
						orderDateTag.setExpectedDate(nikeUtils.dateFuture(30,
								"yyyy-MM-dd'T'HH:mm:ss"));
						orderDates.getOrderDate().add(orderDateTag);
						orderDateTag = new OrderDate();
						orderDateTag.setDateTypeId("EDD");
						orderDateTag.setExpectedDate(nikeUtils.dateFuture(35,
								"yyyy-MM-dd'T'HH:mm:ss"));
						orderDates.getOrderDate().add(orderDateTag);
						com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.Notes.Note orderLineNote = new com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.Notes.Note();
						orderLineNote.setNoteText(nikeUtils.dateFuture(35,
								"MM/dd/yyyy"));
						orderLineNote.setReasonCode("DELIVERY_PROMISE_TEXT");
						orderLineNotes.setNote(orderLineNote);
						if (orderType.equalsIgnoreCase("POS")) {
							orderLineExtn.setExtnCommerceItemID(reservationID);
							Thread.sleep(1000);
						}
					}
					
					if (lineType.equalsIgnoreCase("INLINE")
							|| lineType.equalsIgnoreCase("SERVICE")) {

						if (resrvationIdsXml != null && isIc) {
							if (lineType.equalsIgnoreCase("INLINE")) {
								orderLine.setReservationID(resrvationIdsXml
										.get("baseReservationID" + parentLine));
							} else {
								orderLine.setReservationID(resrvationIdsXml
										.get("serviceReservationID"
												+ parentLine));
							}
						}

						orderLineExtn.setExtnFTCAddnlDays("3");
						orderLineExtn.setExtnIsPreOrder("N");
						orderLineExtn.setExtnClearance("N");
						orderDateTag = new OrderDate();
						orderDateTag.setDateTypeId("FTC_EVENT_DATE_1");
						orderDateTag.setExpectedDate(nikeUtils.dateFuture(3,
								"yyyy-MM-dd'T'HH:mm:ss"));
						orderDates.getOrderDate().add(orderDateTag);
						orderDateTag = new OrderDate();
						orderDateTag.setDateTypeId("EDD");
						orderDateTag.setExpectedDate(nikeUtils.dateFuture(7,
								"yyyy-MM-dd'T'HH:mm:ss"));
						orderDates.getOrderDate().add(orderDateTag);
						com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.Notes.Note orderLineNote = new com.doms.automation.jaxbclasses.order.Order.OrderLines.OrderLine.Notes.Note();
						orderLineNote.setNoteText(nikeUtils.dateFuture(7,
								"MM/dd/yyyy"));
						orderLineNote.setReasonCode("DELIVERY_PROMISE_TEXT");
						orderLineNotes.setNote(orderLineNote);

					}
					if (lineType.equalsIgnoreCase("STORE")
							|| lineType.equalsIgnoreCase("NONMERCH")
							|| lineType.equalsIgnoreCase("EGC")) {
						orderLineExtn.setExtnFTCAddnlDays("0");
					}
					if(lineType.equals("EGC") || lineType.equals("GC")){
						orderDateTag = new OrderDate();
						orderDateTag.setDateTypeId("FTC_EVENT_DATE_1");
						orderDateTag.setExpectedDate(nikeUtils.dateFuture(0,
								"yyyy-MM-dd'T'HH:mm:ss"));
						orderDates.getOrderDate().add(orderDateTag);
					}

					orderLine.setSubLineNo("1");
					orderLineExtn.setExtnShipGroup(sgNo);
					item.setUnitOfMeasure("EACH");
					linePriceInfo.setIsPriceLocked("Y");
					isOrderLineCreated = false;
				}

			}

			if (!(paymentType.equals("")
					|| paymentType.equalsIgnoreCase("REALTIME_BANK")
					|| paymentType.equalsIgnoreCase("POS_PURCHASE") || paymentType
						.equalsIgnoreCase("SOFORT"))) {
				paymentMethod.setPersonInfoBillTo(paymentPersonInfoBillTo);

			}
			if (!paymentType.equals("")) {
				if (!isPaymentDetailsOptional) {
					paymentMethod.setPaymentDetails(paymentDetails);
				}
				paymentmethods.getPaymentMethod().add(paymentMethod);

			}
			/* to-do: update this */

			lineTaxes.getLineTax().add(lineTax);
			instructions.setInstruction(instruction);

			/* Adding child nodes to orderLine tag */
			orderLine.setExtn(orderLineExtn);
			orderLine.setItem(item);
			orderLine.setLinePriceInfo(linePriceInfo);
			orderLine.setLineCharges(lineCharges);
			orderLine.setLineTaxes(lineTaxes);
			orderLine.setOrderDates(orderDates);
			orderLine.setPersonInfoShipTo(personInfoShipTo);
			orderLine.setNotes(orderLineNotes);

			// orderLine.getExtnOrItemOrLinePriceInfo().add(orderLineChildTags);
			orderLines.getOrderLine().add(orderLine);

		}

		/* Setting //Order/Notes */
		Note note = new Note();
		note.setContactTime(orderDate);
		note.setNoteText(nikeUtils.generateUniqueNo("dd/MM/yyyy"));
		note.setReasonCode("DELIVERY_PROMISE_TEXT");
		
		notes.getNote().add(note);
		if(isFeedzai){
			Note note1 = new Note();
			note1.setContactTime(orderDate);
			note1.setNoteText("FeedzaiAuthResponse");	
			note1.setReasonCode(reasonCode);	
			notes.getNote().add(note1);	
			}
		/* Setting //Order/Extn */
		extn.setExtnGeoDetect("US");
		extn.setExtnIpAddress("72.37.244.124");
		ExtnUserAgentDetailsList userAgentList = new ExtnUserAgentDetailsList();
		ExtnUserAgentDetails userAgentDetails = new ExtnUserAgentDetails();
		userAgentDetails.setExtnOrderNo(orderNo);
		userAgentDetails
				.setExtnUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
		userAgentList.setExtnUserAgentDetails(userAgentDetails);
		extn.setExtnUserAgentDetailsList(userAgentList);

		/* Setting store details (Only for MPOS orders */
		if (orderType.equalsIgnoreCase("STORE")) {
			extnStore.setExtnTransEnd(nikeUtils
					.generateUniqueNo("yyyy-MM-dd'T'HH:mm:ss"));
			extnStore.setExtnTransStart(orderDate);
			extnStore.setExtnTransNo(String.valueOf(generateRandomNo()));
			extnStoreList.setExtnStore(extnStore);
			extn.setExtnStoreList(extnStoreList);

		}

		/* Adding nodes to Order Tag */
		order.setPriceInfo(priceInfo);
		order.setPersonInfoBillTo(personInfoBillTo);
		order.setNotes(notes);
		order.setExtn(extn);
		order.setHeaderCharges(headerCharges);
		order.setHeaderTaxes(headerTaxes);
		order.setPaymentMethods(paymentmethods);
		order.setOrderLines(orderLines);

	}

	public int generateRandomNo() {
		int random = 0;
		try {
			SecureRandom randomGenerator = SecureRandom.getInstance("SHA1PRNG");
			// HashSet<Integer> set = new HashSet<Integer>();

			random = randomGenerator.nextInt(9999);

		} catch (NoSuchAlgorithmException nsae) {
			// Forward to handler
		}
		return random;

	}

	public String generateSgNumber(String enterpriseCode) {
		NikeDOMSApplicationUtils nikeUtils = new NikeDOMSApplicationUtils();
		String sgNo = "";
		sgNo = nikeUtils.generateUniqueNo("ddMMHHmmss");
		if (enterpriseCode.equalsIgnoreCase("NIKEUS"))
			sgNo = "SG" + sgNo;
		else if (enterpriseCode.equalsIgnoreCase("NIKEEUROPE"))
			sgNo = "ES" + sgNo;
		else if (enterpriseCode.equalsIgnoreCase("NIKEJP")
				|| enterpriseCode.equalsIgnoreCase("NIKECN"))
			sgNo = "AS" + sgNo;
		return sgNo;

	}

	public String generateReservationID(String enterpriseCode) {
		NikeDOMSApplicationUtils nikeUtils = new NikeDOMSApplicationUtils();
		String reservationID = "";
		if (enterpriseCode.equalsIgnoreCase("NIKEEUROPE")) {
			reservationID = "EI" + nikeUtils.generateUniqueNo("MMddhhmmss");

		} else if (enterpriseCode.equalsIgnoreCase("NIKEUS")) {
			reservationID = "CI" + nikeUtils.generateUniqueNo("MMddhhmmss");

		} else if (enterpriseCode.equalsIgnoreCase("NIKECN")
				|| enterpriseCode.equalsIgnoreCase("NIKEJP") || enterpriseCode.equalsIgnoreCase("NIKEHK") ) {
			reservationID = "AI" + nikeUtils.generateUniqueNo("MMddhhmmss");

		}
		return reservationID;
	}

}
