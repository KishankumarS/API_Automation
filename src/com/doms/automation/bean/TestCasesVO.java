package com.doms.automation.bean;

import java.util.ArrayList;


public class TestCasesVO {
	private String testCaseName;
	private ArrayList<TemplateMappingRulesVO> templateMappingRulesVOList;
	private String[] processStepsArr;
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	
	public ArrayList<TemplateMappingRulesVO> getTemplateMappingRulesVOList() {
		return templateMappingRulesVOList;
	}
	public void setTemplateMappingRulesVOList(
			ArrayList<TemplateMappingRulesVO> templateMappingRulesVOList) {
		this.templateMappingRulesVOList = templateMappingRulesVOList;
	}
	public String[] getProcessStepsArr() {
		return processStepsArr;
	}
	public void setProcessStepsArr(String[] processStepsArr) {
		this.processStepsArr = processStepsArr;
	}
	

}
