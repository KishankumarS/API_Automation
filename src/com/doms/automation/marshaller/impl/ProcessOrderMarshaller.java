package com.doms.automation.marshaller.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.doms.automation.jaxbclasses.processorder.ProcessOrder;
import com.doms.automation.marshaller.AutomationMarshaller;
import com.doms.automation.utils.NikeDOMSConstants;

public class ProcessOrderMarshaller implements AutomationMarshaller{
	private ProcessOrder processOrder;
	private static JAXBContext context;
	private static Marshaller marshaller;
	
	
	
	/**
	 * @param processOrder
	 */
	public ProcessOrderMarshaller(ProcessOrder processOrder) {
		super();
		this.processOrder = processOrder;
	}
	
	static{	
		synchronized (ProcessOrder.class) {
		try {
			context = JAXBContext.newInstance(ProcessOrder.class);
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
		marshaller.marshal(processOrder, outputStream);    
		return outputStream.toString();			
	}

}
