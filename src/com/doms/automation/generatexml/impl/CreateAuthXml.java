package com.doms.automation.generatexml.impl;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import com.doms.automation.bean.XmlTagAttributesVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.jaxbclasses.authxmlpaymenttype.AuthXmlPaymentType;
import com.doms.automation.marshaller.impl.AuthXmlPaymentTypeMarshaller;

public class CreateAuthXml {
	public String generateXml(
			ArrayList<XmlTagLinesVO> xmlTagLinesVOList,String orderNo)
			throws ClassNotFoundException {
		String authXml="";
		AuthXmlPaymentType authXmlPaymentType = new AuthXmlPaymentType();
		boolean isCreditCard = false;
		boolean authXmlPaymentTypeIsSet = false;
		for (XmlTagLinesVO xmlTagLinesVO : xmlTagLinesVOList) {
			isCreditCard = false;

			for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO
					.getXmlTagAttributesVO()) {

				if ("PaymentType"
						.equalsIgnoreCase(xmlTagAttributesVO
								.getAttributeName())) {
					if ("CREDIT_CARD"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeValue())) {
						isCreditCard = true;
						authXmlPaymentType.setAction("AUTH");
						authXmlPaymentType.setOrderNo(orderNo);
						AutomationHelperImpl autoHelper = new AutomationHelperImpl();
						authXmlPaymentType
								.setCustomerPONo(autoHelper
										.generatePONumber(
												xmlTagLinesVO,
												orderNo));
						break;

					}
				}
			}

			if (isCreditCard) {
				

				for (XmlTagAttributesVO xmlTagAttributesVO : xmlTagLinesVO
						.getXmlTagAttributesVO()) {

					if ("PaymentType"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						if ("CREDIT_CARD"
								.equalsIgnoreCase(xmlTagAttributesVO
										.getAttributeValue())) {
							isCreditCard = true;
						}
					}
					if ("EnterpriseCode"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setEnterprisecode(xmlTagAttributesVO
										.getAttributeValue());
					}

					if ("PersonInfoBillTo_AddressLine1"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setBillToAddressLine1(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoBillTo_AddressLine2"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setBillToAddressLine2(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoBillTo_City"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setBillToCity(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoBillTo_Country"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setBillToCountry(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoBillTo_DayPhone"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setBillToDayPhone(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoBillTo_EMailID"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setBillToEmailId(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoBillTo_FirstName"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setBillToFirstName(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoBillTo_LastName"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setBillToLastName(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoBillTo_LastName"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setBillToLastName(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoBillTo_State"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setBillToState(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoBillTo_ZipCode"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setBillToZipCode(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("CreditCardExpDate"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setCreditCardExpirationDate(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("CreditCardName"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setCreditCardName(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("CreditCardNum"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setCreditCardNo(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("CreditCardType"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setCreditCardType(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("Currency"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setCurrency(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("NikeAccount"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setCustomerAccountNo(xmlTagAttributesVO
										.getAttributeValue());
					}

					if ("PaymentType"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setPaymentType(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("RequestAmount"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setRequestAmount(xmlTagAttributesVO
										.getAttributeValue());
					}

					if ("PersonInfoShipTo_FirstName"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setShipToFirstName(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoShipTo_LastName"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setShipToLastName(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoShipTo_AddressLine1"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setShipToAddressLine1(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoShipTo_AddressLine2"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setShipToAddressLine2(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoShipTo_City"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setShipToCity(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoShipTo_State"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setShipToState(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoShipTo_Country"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setShipToCountry(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoShipTo_ZipCode"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setShipToZipCode(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoBillTo_DayPhone"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setShipToDayPhone(xmlTagAttributesVO
										.getAttributeValue());
					}
					if ("PersonInfoShipTo_EMailID"
							.equalsIgnoreCase(xmlTagAttributesVO
									.getAttributeName())) {
						authXmlPaymentType
								.setShipToEmailId(xmlTagAttributesVO
										.getAttributeValue());
					}

				}
				authXmlPaymentTypeIsSet=true;
			}
		}
		if (authXmlPaymentTypeIsSet) {
		AuthXmlPaymentTypeMarshaller authXmlPaymentTypeMarshaller = new AuthXmlPaymentTypeMarshaller(
				authXmlPaymentType);
		try {
			authXml = authXmlPaymentTypeMarshaller
					.convertJaxbObjectToXML();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
				return authXml;
		
	}

}
