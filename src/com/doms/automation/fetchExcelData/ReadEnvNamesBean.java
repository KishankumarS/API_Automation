package com.doms.automation.fetchExcelData;

import java.util.ArrayList;
import java.util.List;

import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.helper.CreateExcelHelper;

public class ReadEnvNamesBean {

	CreateExcelHelper createExcelHelper;
	ArrayList<TestCaseDataVO> testCaseDataVO = new ArrayList<>();
	List<String> names = new ArrayList<>();
	TestCaseDataVO testCaseDataVOObj;
	List<String> testCaseNames = new ArrayList<String>();
	Service service = new Service();
	String name;
	List<String> tempList = new ArrayList<>();
	// LoadExcelHelper loadExcelHelper;

	public ReadEnvNamesBean() {

		// TODO Auto-generated constructor stub
		this.tempList = service.getEnvNames();

		for (String var : tempList) {

			this.names.add(var);

		}

	}

	public ArrayList<TestCaseDataVO> getTestCaseDataVO() {
		return testCaseDataVO;
	}

	public void setTestCaseDataVO(ArrayList<TestCaseDataVO> testCaseDataVO) {
		this.testCaseDataVO = testCaseDataVO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

}
