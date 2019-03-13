package com.doms.automation.unmarshaller.impl;


import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.doms.automation.jaxbclasses.taskqlistdetails.GetTaskQueueDataOutput;
import com.doms.automation.jaxbclasses.taskqlistdetails.GetTaskQueueDataOutput.TaskQueueList;
import com.doms.automation.unmarshaller.AutomationUnmarshaller;


public class TaskQListUnmarshaller implements AutomationUnmarshaller{
	private GetTaskQueueDataOutput taskQList;
	private static JAXBContext context;
	private static Unmarshaller unmarshaller;
	public TaskQListUnmarshaller(GetTaskQueueDataOutput taskQList) {
		super();
		this.taskQList = taskQList;
	}
	public TaskQListUnmarshaller() {
		// TODO Auto-generated constructor stub
	}
	static{	
		synchronized (GetTaskQueueDataOutput.class) {
		try {
			context = JAXBContext.newInstance(GetTaskQueueDataOutput.class);
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
		taskQList = (GetTaskQueueDataOutput) unmarshaller
				.unmarshal(strReader);
		return taskQList;
	}

}
