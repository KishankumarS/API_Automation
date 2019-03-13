package com.doms.automation.runprocessstep.impl;

import java.util.Map;

import javax.xml.bind.JAXBException;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.runprocessstep.RunProcessStep;
import com.doms.automation.service.ProcessOrderService;
import com.doms.automation.utils.NikeDOMSLogger;

public class AuthorizeOrder implements RunProcessStep{

	@Override
	public Map<String, ReqResVO> execute(Map<String, ReqResVO> responseMap,
			ReqResVO reqResVO, NikeDOMSConnectionDAO nikeDOMSConnectionDAO,
			ProcessOrderService processOrderService, String environment,
			String orderNo, TestCaseDataVO testCaseDataVO,
			EnvConfigVO envConfigVO, Map<String, DbEnvConfig> dbConfigMap,
			String xml, boolean isNikeGS, String exchangeOrderNo) {
		
		if((null!=exchangeOrderNo)){
			System.out.println("Before exchange " +orderNo);
			orderNo =exchangeOrderNo;
			System.out.println("After exchange " +orderNo);
		}
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();		
		try {
			reqResVO = processOrderService
					.authorizePayment(envConfigVO);
			reqResVO = automationHelperImpl.setStatus(reqResVO, envConfigVO, orderNo,
					nikeDOMSConnectionDAO, processOrderService);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		} 
		NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME", "",
				NikeDOMSLogger.LEVEL_INFO, "",
				"REQ>>>>" + reqResVO.getReqXML() + "\nRES>>>"
						+ reqResVO.getResXML() + " -  "
						+ testCaseDataVO.getTestCaseName());
		responseMap.put("AuthorizeOrder-"+ orderNo, reqResVO);
		return responseMap;
	}

}
