package com.doms.automation.runprocessstep;

import java.util.Map;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.service.ProcessOrderService;

public interface RunProcessStep {
	
	public Map<String, ReqResVO> execute(Map<String, ReqResVO> responseMap,ReqResVO reqResVO,NikeDOMSConnectionDAO nikeDOMSConnectionDAO
				,ProcessOrderService processOrderService,String environment, String orderNo,
				TestCaseDataVO testCaseDataVO, EnvConfigVO envConfigVO,
				Map<String, DbEnvConfig> dbConfigMap, String processStep, boolean isNikeGS, String exchangeOrderNo);

}
