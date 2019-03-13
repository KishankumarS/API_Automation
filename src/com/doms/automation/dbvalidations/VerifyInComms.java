package com.doms.automation.dbvalidations;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.WarningQueryVO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.utils.NikeDOMSConstants;

public class VerifyInComms {
	
	public ReqResVO verifyEmailConfirmationInComms(
			String commsOrderconfirmSubCategoryName, String orderNo
			, Map<String,DbEnvConfig> dbConfigMap)
			throws InterruptedException, JAXBException {
		AutomationHelperImpl autometionHelper = new AutomationHelperImpl();		
		Thread.sleep(10000);

		String ocEmailCommsEntry = autometionHelper.verifyEmailCOMMS(
				dbConfigMap, orderNo, commsOrderconfirmSubCategoryName,
				NikeDOMSConstants.GET_COMMS_EMAIL_DETAILS);
		System.out.println("EMAIL VALIDATION-COMMS: " + ocEmailCommsEntry);
		ReqResVO reqResVOObj = new ReqResVO();
		reqResVOObj.setReqXML("Order confirmation email in COMMS");
		reqResVOObj.setResXML(ocEmailCommsEntry);
		if("Warning".equalsIgnoreCase(ocEmailCommsEntry)){
			ArrayList<String> inputParameters = new ArrayList<String>();
			inputParameters.add(orderNo);
			inputParameters.add(commsOrderconfirmSubCategoryName);
			WarningQueryVO warningQueryVO = new WarningQueryVO();
			warningQueryVO.setDataBase(NikeDOMSConstants.COMMS);
			warningQueryVO.setInputParameters(inputParameters);
			warningQueryVO.setQuery(NikeDOMSConstants.GET_COMMS_EMAIL_DETAILS);
			ArrayList<WarningQueryVO> warningQueryVOList= null;
			if(reqResVOObj.getWarningQueryVOList()!=null && reqResVOObj.getWarningQueryVOList().size()>0){
				warningQueryVOList = reqResVOObj.getWarningQueryVOList();	
			}else{
				warningQueryVOList = new ArrayList<WarningQueryVO>();
			}				
			warningQueryVOList.add(warningQueryVO);
			reqResVOObj.setWarningQueryVOList(warningQueryVOList);
			reqResVOObj.setComment("The entry did not come in comms even after waiting for 10 seconds.So Please check manually.");
		}
		
		return reqResVOObj;
	}

}
