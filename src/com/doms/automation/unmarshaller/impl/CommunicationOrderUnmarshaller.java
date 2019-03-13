package com.doms.automation.unmarshaller.impl;


import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.doms.automation.jaxbclasses.communication.Order;
import com.doms.automation.unmarshaller.AutomationUnmarshaller;


public class CommunicationOrderUnmarshaller implements AutomationUnmarshaller{
	private Order order;
	private static JAXBContext context;
	private static Unmarshaller unmarshaller;
	public CommunicationOrderUnmarshaller(Order orderSummary) {
		super();
		this.order = orderSummary;
	}
	public CommunicationOrderUnmarshaller() {
		// TODO Auto-generated constructor stub
	}
	static{	
		synchronized (Order.class) {
		try {
			context = JAXBContext.newInstance(Order.class);
			unmarshaller = context.createUnmarshaller();

			  //for pretty-print XML in JAXB     
		//	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		}
		 
	}
	
	
	@Override
	public Object convertXMLtoJaxbObject(String xml) throws JAXBException {
		StringReader strReader = new StringReader(xml);
		order = (Order) unmarshaller
				.unmarshal(strReader);
		return order;
	}

}
