package com.doms.automation.fetchExcelData;

import java.util.ArrayList;
import java.util.List;

import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.helper.CreateExcelHelper;

public class ReadTestCasesBean {

	CreateExcelHelper createExcelHelper;
	ArrayList<TestCaseDataVO> testCaseDataVO = new ArrayList<>();
	List<String> names0 = new ArrayList<>();
	List<String> names1 = new ArrayList<>();
	TestCaseDataVO testCaseDataVOObj;
	List<String> testCaseNames = new ArrayList<String>();
	Service service = new Service();
	List<String> tempList0 = new ArrayList<>();
	List<String> tempList1 = new ArrayList<>();
	// LoadExcelHelper loadExcelHelper;

	public ReadTestCasesBean() {
		// TODO Auto-generated constructor stub

		this.tempList0 = service.getLoadedTestCaseData(0);
		this.tempList1 = service.getLoadedTestCaseData(1);
		for (String var : tempList0) {
			if (!(var.equalsIgnoreCase(""))) {
				if (!(var.equalsIgnoreCase("Test Case"))) {
					this.names0.add(var);
				}
			}
		}
		for (String var : tempList1) {
			if (!(var.equalsIgnoreCase(""))) {
				if (!(var.equalsIgnoreCase("Test Case"))) {
					this.names1.add(var);
				}
			}
		}

	}
	
	public ArrayList<TestCaseDataVO> getTestCaseDataVO() {
		return testCaseDataVO;
	}

	public void setTestCaseDataVO(ArrayList<TestCaseDataVO> testCaseDataVO) {
		this.testCaseDataVO = testCaseDataVO;
	}

	public List<String> getNames0() {
		return names0;
	}

	public void setNames0(List<String> names0) {
		this.names0 = names0;
	}

	public List<String> getNames1() {
		return names1;
	}

	public void setNames1(List<String> names1) {
		this.names1 = names1;
	}
}
