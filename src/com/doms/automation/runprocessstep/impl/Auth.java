package com.doms.automation.runprocessstep.impl;

import java.util.Map;

import javax.xml.bind.JAXBException;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.bean.XmlTagAttributesVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.generatexml.impl.CreateAuthXml;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.jaxbclasses.authxmlpaymenttype.AuthXmlPaymentType;
import com.doms.automation.marshaller.impl.AuthXmlPaymentTypeMarshaller;
import com.doms.automation.runprocessstep.RunProcessStep;
import com.doms.automation.service.CreateOrderService;
import com.doms.automation.service.ProcessOrderService;
import com.doms.automation.utils.NikeDOMSLogger;

public class Auth implements RunProcessStep{

	@Override
	public Map<String, ReqResVO> execute(Map<String, ReqResVO> responseMap,
			ReqResVO reqResVO, NikeDOMSConnectionDAO nikeDOMSConnectionDAO,
			ProcessOrderService processOrderService, String environment,
			String orderNo, TestCaseDataVO testCaseDataVO,
			EnvConfigVO envConfigVO, Map<String, DbEnvConfig> dbConfigMap, String processStep, boolean isNikeGS, String exchangeOrderNo) {

		
		CreateOrderService createOrderservice = new CreateOrderService();
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();
		reqResVO = createOrderservice.authXML(environment, 
				automationHelperImpl.getAuthXml(testCaseDataVO.
						getXmlTagLinesVOList(), orderNo),
				envConfigVO,reqResVO);
		NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME", "",
				NikeDOMSLogger.LEVEL_INFO, "",
				"REQ>>>>" + reqResVO.getReqXML() + "\nRES>>>"
						+ reqResVO.getResXML() + " -  "
						+ testCaseDataVO.getTestCaseName());
		
		return responseMap;
	}

}
