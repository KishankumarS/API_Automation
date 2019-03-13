package com.doms.automation.bean;

import java.util.ArrayList;

public class TestDataVO {
	private String testCaseName;
	private ArrayList <XmlTagAttributesVO> xmlTagAttributesVO=
		new ArrayList<XmlTagAttributesVO>();
	private String[] processStepsArr;
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	public ArrayList<XmlTagAttributesVO> getXmlTagAttributesVO() {
		return xmlTagAttributesVO;
	}
	public void setXmlTagAttributesVO(
			ArrayList<XmlTagAttributesVO> xmlTagAttributesVO) {
		this.xmlTagAttributesVO = xmlTagAttributesVO;
	}
	public String[] getProcessStepsArr() {
		return processStepsArr;
	}
	public void setProcessStepsArr(String[] processStepsArr) {
		this.processStepsArr = processStepsArr;
	}
	
	
}
