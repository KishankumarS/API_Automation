package com.doms.automation.marshaller.impl;

import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import com.doms.automation.jaxbclasses.getorderdetails.GetOrderDetails;
import com.doms.automation.marshaller.AutomationMarshaller;

public class GetOrderDetailsMarshaller implements AutomationMarshaller{
	private GetOrderDetails getOrderDetails;
	private static JAXBContext context;
	private static Marshaller marshaller;
	
	public GetOrderDetailsMarshaller(GetOrderDetails getOrderDetails) {
		super();
		this.getOrderDetails = getOrderDetails;
	}
	
	static{
		synchronized (GetOrderDetails.class) {
		try {
			context = JAXBContext.newInstance(GetOrderDetails.class);
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
		marshaller.marshal(getOrderDetails, outputStream);   
		return outputStream.toString();		
	}

}
