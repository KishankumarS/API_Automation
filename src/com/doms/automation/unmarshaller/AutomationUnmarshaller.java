package com.doms.automation.unmarshaller;

import javax.xml.bind.JAXBException;

public interface AutomationUnmarshaller {
	public Object convertXMLtoJaxbObject(String Xml) throws JAXBException;

}
