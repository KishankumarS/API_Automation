package com.doms.automation.helper;

import java.util.ArrayList;
import java.util.Map;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.bean.TestCasesVO;

public interface LoadExcelHelper {
	public  ArrayList<TestCasesVO> getLoadedTestCases();
	public ArrayList<TestCaseDataVO> getLoadedTestCaseData(
			ArrayList<TestCaseDataVO> testCaseDataVOList);
	public EnvConfigVO getEnvConfigVO(String env);
	public Map<String,DbEnvConfig> getDbEnvConfig(String environment);
}
