package com.doms.automation.marshaller;

import javax.xml.bind.JAXBException;

public interface AutomationMarshaller {
	public String convertJaxbObjectToXML() throws JAXBException;

}
