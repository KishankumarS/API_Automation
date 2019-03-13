package com.doms.automation.marshaller.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.doms.automation.jaxbclasses.agenttrigger.AgentTrigger;
import com.doms.automation.marshaller.AutomationMarshaller;
import com.doms.automation.utils.NikeDOMSConstants;

public class AgentTriggerMarshaller implements AutomationMarshaller{
	private AgentTrigger agentTrigger;
	private static JAXBContext context;
	private static Marshaller marshaller;
	public AgentTriggerMarshaller(AgentTrigger agentTrigger) {
		super();
		this.agentTrigger = agentTrigger;
	}
	public AgentTriggerMarshaller() {
		// TODO Auto-generated constructor stub
	}
	static{	
		synchronized (AgentTrigger.class) {
		try {
			context = JAXBContext.newInstance(AgentTrigger.class);
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
		marshaller.marshal(agentTrigger, outputStream);   
		return outputStream.toString();
		
	}

}
