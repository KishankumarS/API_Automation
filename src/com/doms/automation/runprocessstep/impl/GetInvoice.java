package com.doms.automation.runprocessstep.impl;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.xml.bind.JAXBException;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.runprocessstep.RunProcessStep;
import com.doms.automation.service.ProcessOrderService;
import com.doms.automation.utils.NikeDOMSConstants;

public class GetInvoice implements RunProcessStep{

	@Override
	public Map<String, ReqResVO> execute(Map<String, ReqResVO> responseMap,
			ReqResVO reqResVO, NikeDOMSConnectionDAO nikeDOMSConnectionDAO,
			ProcessOrderService processOrderService, String environment,
			String orderNo, TestCaseDataVO testCaseDataVO,
			EnvConfigVO envConfigVO, Map<String, DbEnvConfig> dbConfigMap,
			String processStep, boolean isNikeGS, String exchangeOrderNo) {
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();		
		CachedRowSet crs = null;
		String invoiceNo="";

			
				try {
					crs = RowSetProvider.newFactory().createCachedRowSet();

					String[] validateInvoices = processStep.split("-");
					// String alertLevelValidation =
					// processStep.substring(processStep.indexOf("-") + 1,
					// processStep.indexOf("_"));

					nikeDOMSConnectionDAO.getDBValues(orderNo,
							NikeDOMSConstants.getInvoiceDetails,
							NikeDOMSConstants.DOMS);
					crs = nikeDOMSConnectionDAO.getRowSet();
					int size = crs.size();
					crs.beforeFirst();
					if (size > 0) {
						if (size == 1) {
							if (crs.next()) {
								invoiceNo = crs.getString("INVOICE_NO");
							}
						} else {
							while (crs.next()) {
								invoiceNo = crs.getString("INVOICE_NO");
							}
						}
					}

					for (int m = 0; m < validateInvoices.length; m++) {
						if (!invoiceNo.equalsIgnoreCase(validateInvoices[m])) {
							reqResVO.setReqXML(NikeDOMSConstants.getInvoiceDetails);
							reqResVO.setResXML("Success");
							break;

						} else {
							reqResVO.setReqXML(NikeDOMSConstants.getInvoiceDetails);
							reqResVO.setResXML("Error - invoice is not generated");

						}
					}

					reqResVO = automationHelperImpl.setStatus(reqResVO, envConfigVO, orderNo,
							nikeDOMSConnectionDAO, processOrderService);
					responseMap.put(processStep, reqResVO);
					if (!reqResVO.getResXML().equalsIgnoreCase("Success")) {
						reqResVO.setIsNotSuccess(true);
						return responseMap;
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();		
				}  finally{
					try {
						crs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				return responseMap;
	}
}
