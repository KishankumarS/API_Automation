package com.doms.automation.bean;

import java.util.ArrayList;

public class TestCaseDataVO {
	private String testCaseName;
	private ArrayList <XmlTagLinesVO> xmlTagLinesVOList=
		new ArrayList<XmlTagLinesVO>();
	private String[] processStepsArr;
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	
	public String[] getProcessStepsArr() {
		return processStepsArr;
	}
	public void setProcessStepsArr(String[] processStepsArr) {
		this.processStepsArr = processStepsArr;
	}
	public ArrayList<XmlTagLinesVO> getXmlTagLinesVOList() {
		return xmlTagLinesVOList;
	}
	public void setXmlTagLinesVOList(ArrayList<XmlTagLinesVO> xmlTagLinesVOList) {
		this.xmlTagLinesVOList = xmlTagLinesVOList;
	}
	
	
	
	
	
}
