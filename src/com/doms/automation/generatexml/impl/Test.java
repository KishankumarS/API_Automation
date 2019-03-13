package com.doms.automation.generatexml.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.helper.LoadExcelHelper;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.helper.impl.LoadExcelHelperImpl;
import com.doms.automation.utils.NikeDOMSApplicationUtils;
import com.doms.automation.utils.NikeDOMSConstants;

public class Test {

	public static void main(String[] args) throws DatatypeConfigurationException {
		LoadExcelHelper loadExcelHelper = new LoadExcelHelperImpl();
		ArrayList<TestCaseDataVO> testCaseDataVO = new ArrayList<TestCaseDataVO>();
		testCaseDataVO = loadExcelHelper.getLoadedTestCaseData(testCaseDataVO);
		TestCaseDataVO testCaseDataVOObj=new TestCaseDataVO(); 
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();
		//String orderNum = automationHelperImpl.getOrderNoOrFsoOrderNo(isNikeGS,orderNo,fsoOrderNo);
		List<String> primeLineList=automationHelperImpl.getPrimeLineList("12");
		for(TestCaseDataVO oTestCaseDataVO:testCaseDataVO){		
			if("TC01_Regression_US_BRD_Inline_SL_Creditmemo_Return_GC_Payment_SmokeUS".
					equalsIgnoreCase(oTestCaseDataVO.getTestCaseName())){
				testCaseDataVOObj=oTestCaseDataVO;
				break;
				}	
		}
	/*	
		CreateModifyShipToAddressXml createModifyShipToAddressXml = new CreateModifyShipToAddressXml();
		String xml=createModifyShipToAddressXml.generateXml(primeLineList,"OR123456",testCaseDataVOObj.getXmlTagLinesVOList());
		System.out.println(xml);*/

	}

}
