package com.doms.automation.marshaller.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.doms.automation.jaxbclasses.order.Order;
import com.doms.automation.marshaller.AutomationMarshaller;
import com.doms.automation.utils.NikeDOMSConstants;

public class OrderMarshaller implements AutomationMarshaller{
	private Order order;
	private static JAXBContext context;
	private static Marshaller marshaller;
	
	
	
	/**
	 * @param order
	 */
	public OrderMarshaller(Order order) {
		super();
		this.order = order;
	}
	
	static{
		synchronized (Order.class) {
		try {
			context = JAXBContext.newInstance(Order.class);
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
		marshaller.marshal(order, outputStream);    
		return outputStream.toString();		
	}

}
