package com.doms.automation.generatexml.impl;

import javax.xml.bind.JAXBException;

import com.doms.automation.jaxbclasses.agenttrigger.AgentTrigger;
import com.doms.automation.marshaller.impl.AgentTriggerMarshaller;

public class CreateTriggerXml {
	public String generateXml(String criteriaId) throws JAXBException {
		AgentTrigger agentTrigger = new AgentTrigger();
		agentTrigger.setCriteriaId(criteriaId);
		AgentTriggerMarshaller agentTriggerMarshaller = new AgentTriggerMarshaller(
				agentTrigger);
		String triggerXml = agentTriggerMarshaller.convertJaxbObjectToXML();
		return triggerXml;
	}
}
