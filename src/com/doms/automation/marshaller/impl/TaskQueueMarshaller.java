package com.doms.automation.marshaller.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.doms.automation.jaxbclasses.agenttrigger.AgentTrigger;
import com.doms.automation.jaxbclasses.taskqueue.TaskQueue;
import com.doms.automation.marshaller.AutomationMarshaller;
import com.doms.automation.utils.NikeDOMSConstants;

public class TaskQueueMarshaller implements AutomationMarshaller{
	private TaskQueue taskQueue;
	private static JAXBContext context;
	private static Marshaller marshaller;
	public TaskQueueMarshaller(TaskQueue taskQueue) {
		super();
		this.taskQueue = taskQueue;
	}
	public TaskQueueMarshaller() {
		// TODO Auto-generated constructor stub
	}
	static{	
		synchronized (TaskQueue.class) {
		try {
			context = JAXBContext.newInstance(TaskQueue.class);
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
		marshaller.marshal(taskQueue, outputStream);   
		return outputStream.toString();
		
	}

}
