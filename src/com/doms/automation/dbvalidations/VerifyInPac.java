package com.doms.automation.dbvalidations;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.bean.WarningQueryVO;
import com.doms.automation.bean.XmlTagAttributesVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.controller.TestExecuteController;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.utils.NikeDOMSConstants;

public class VerifyInPac {
	
	public ReqResVO verifyEmailConfirmationInPac(String eventType,String pacOrderconfirmEventName,String orderNo
			, Map<String,DbEnvConfig> dbConfigMap,ArrayList<XmlTagLinesVO> xmlTagLinesVOList,String lineno)
			throws JAXBException {
		AutomationHelperImpl autometionHelper = new AutomationHelperImpl();	
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();	
		String ocEmailPACEntry = autometionHelper.verifyEmailPAC(dbConfigMap,
				orderNo, pacOrderconfirmEventName,
				NikeDOMSConstants.GET_PAC_MESSAGE_DETAILS);
		String CarrierServiceCode =automationHelperImpl.getAttributeVlaue("CarrierServiceCode", xmlTagLinesVOList, lineno);
		ReqResVO reqResVOObj = new ReqResVO();
		String ResCarriercode= "";
		String expurl = "https://www.ups.com/lsw/invoke?&cburl=http://www.ups.com&oId=asdf&country=FR&isupId=True&uappuid=FR01716&loc=fr_FR_EUR&edit=false&target=_top";
		String acturl="";
		if(ocEmailPACEntry.contains("Success")){
			reqResVOObj.setResXML(ocEmailPACEntry);
			String xml = ocEmailPACEntry.replace("Success", "").replace("&lt;", "<").replace("&gt;", ">");
			String subxml =xml.substring(xml.indexOf("<Order"), xml.indexOf("</Data>")-1);
			System.out.println("EMAIL VALIDATION-PAC: " + subxml);
			if("OrderConfirm".equalsIgnoreCase(eventType)){
			 ResCarriercode = automationHelperImpl.validateXPathValue(subxml, "/Order/OrderLines/OrderLine/@CarrierServiceCode");
			 if(CarrierServiceCode.equalsIgnoreCase(ResCarriercode)){
					reqResVOObj.setComment("PAC, Kiala-Carrierservicecode in "+eventType+" email is found");}else{
						reqResVOObj.setComment("PAC, Kiala-Carrierservicecode in "+eventType+" email is not correct");
					}
			} else if("ShipmentConfirm".equalsIgnoreCase(eventType)||("NikeIdShipConfirmation".equalsIgnoreCase(eventType))){
				 ResCarriercode = automationHelperImpl.validateXPathValue(subxml, "/Order/ShipmentLines/ShipmentLine/OrderLine/@CarrierServiceCode");
				 if(CarrierServiceCode.equalsIgnoreCase(ResCarriercode)){
						reqResVOObj.setComment("PAC, Kiala-Carrierservicecode in "+eventType+" email is found");}else{
							reqResVOObj.setComment("PAC, Kiala-Carrierservicecode in "+eventType+" email is not correct");
						}
			} else if("DeliveryConfirm".equalsIgnoreCase(eventType)||("NikeIdDeliveryConfirm".equalsIgnoreCase(eventType))){
				 ResCarriercode = automationHelperImpl.validateXPathValue(subxml, "/Order/ShipmentLines/ShipmentLine/OrderLine/@CarrierServiceCode");
				 if(CarrierServiceCode.equalsIgnoreCase(ResCarriercode)){
						reqResVOObj.setComment("PAC, Kiala-Carrierservicecode in "+eventType+" email is found");}else{
							reqResVOObj.setComment("PAC, Kiala-Carrierservicecode in "+eventType+" email is not correct");
						}
			} else if("PickUp".equalsIgnoreCase(eventType)){
				reqResVOObj.setReqXML("Verify orderconfirmation email in PAC");
				 ResCarriercode = automationHelperImpl.validateXPathValue(subxml, "/Order/ShipmentLines/ShipmentLine/OrderLine/@CarrierServiceCode");
				 acturl = automationHelperImpl.validateXPathValue(subxml, "/Order/@CPPUrl");
				 if(CarrierServiceCode.equalsIgnoreCase(ResCarriercode)&&expurl.equalsIgnoreCase(acturl.replace("amp;", ""))){
						reqResVOObj.setComment("PAC, Kiala-Carrierservicecode in "+eventType+" email is found /n And Kiala Shipment URL is as expected");}else{
							reqResVOObj.setComment("PAC, Kiala-Carrierservicecode in "+eventType+" email is not correct /n or /n or Kiala Shipment URL is not as expected");
						}
			}
			System.out.println("ResCarriercode: " +ResCarriercode+" ReqCarriercode: "+CarrierServiceCode);
			System.out.println("Actual URL: "+acturl.replace("amp;", ""));
			System.out.println("Expted URL: "+expurl);
			
		}
	
	reqResVOObj.setReqXML("Verify orderconfirmation email in PAC");
		if("Warning".equalsIgnoreCase(ocEmailPACEntry)){
			ArrayList<String> inputParameters = new ArrayList<String>();
			inputParameters.add(orderNo);
			inputParameters.add(pacOrderconfirmEventName);
			WarningQueryVO warningQueryVO = new WarningQueryVO();
			warningQueryVO.setDataBase(NikeDOMSConstants.PAC);
			warningQueryVO.setInputParameters(inputParameters);
			warningQueryVO.setQuery(NikeDOMSConstants.GET_PAC_MESSAGE_DETAILS);
			ArrayList<WarningQueryVO> warningQueryVOList= null;
			if(reqResVOObj.getWarningQueryVOList()!=null && reqResVOObj.getWarningQueryVOList().size()>0){
				warningQueryVOList = reqResVOObj.getWarningQueryVOList();	
			}else{
				warningQueryVOList = new ArrayList<WarningQueryVO>();
			}				
			warningQueryVOList.add(warningQueryVO);
			reqResVOObj.setWarningQueryVOList(warningQueryVOList);
			reqResVOObj.setComment("The entry did not come in PAC even after waiting for 30 seconds.So Please check manually.");
		}
		return reqResVOObj;
	}
	

}
