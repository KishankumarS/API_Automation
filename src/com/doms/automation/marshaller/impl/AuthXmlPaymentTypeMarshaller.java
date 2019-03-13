package com.doms.automation.marshaller.impl;

import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;



import com.doms.automation.jaxbclasses.authxmlpaymenttype.AuthXmlPaymentType;
import com.doms.automation.marshaller.AutomationMarshaller;

public class AuthXmlPaymentTypeMarshaller implements AutomationMarshaller{
	private AuthXmlPaymentType authXmlPaymentType;
	private static JAXBContext context;
	private static Marshaller marshaller;
	
	
	
	/**
	 * @param authXmlPaymentType
	 */
	public AuthXmlPaymentTypeMarshaller(AuthXmlPaymentType authXmlPaymentType) {
		super();
		this.authXmlPaymentType = authXmlPaymentType;
	}
	
	static{
		synchronized (AuthXmlPaymentType.class) {
		try {
			context = JAXBContext.newInstance(AuthXmlPaymentType.class);
			marshaller= context.createMarshaller();    
			  //for pretty-print XML in JAXB     
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		 
	}
	@Override
	public String convertJaxbObjectToXML() throws JAXBException {
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		marshaller.marshal(authXmlPaymentType, outputStream);    
		return outputStream.toString();		
	}

}
