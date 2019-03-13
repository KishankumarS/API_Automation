package com.doms.automation.dbvalidations;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.service.PostRequest;
import com.doms.automation.utils.DbValidationConstants;
import com.doms.automation.utils.NikeDOMSConstants;

public class VerifyAsnService {
	
	
	public ArrayList<String> getAsnRequestXml(String orderNo,
			NikeDOMSConnectionDAO nikeDOMSConnectionDAO,AutomationHelperImpl automationHelperImpl, List<String> primeLineList) {
		CachedRowSet crs = null;
		 Clob msgClobDoms = null;
		 ArrayList<String> asnRequestXmlList = new ArrayList<String>();
	for(String lineNo:primeLineList){
		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();
			nikeDOMSConnectionDAO.getDBValues2parametres(orderNo,"%<OrderItemLineId>"+lineNo+"%", NikeDOMSConstants.getAsnRequestFromQ,					
					NikeDOMSConstants.DOMS);
		
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						msgClobDoms =  crs.getClob(1);						
						asnRequestXmlList.add(automationHelperImpl.getStringFromClob(msgClobDoms));
						

					}
				} else {
					while (crs.next()) {
						msgClobDoms =  crs.getClob(1);
						asnRequestXmlList.add(automationHelperImpl.getStringFromClob(msgClobDoms));
						

					}
				}
			}
			else{
				crs.close();
				crs = RowSetProvider.newFactory().createCachedRowSet();
				nikeDOMSConnectionDAO.getDBValues2parametres(orderNo,"%<OrderItemLineId>"+lineNo+"%",  NikeDOMSConstants.getAsnRequestFromArchive,					
						NikeDOMSConstants.DOMS);			
				crs = nikeDOMSConnectionDAO.getRowSet();
				size = crs.size();
				crs.beforeFirst();
				if(size > 0) {
					if (size == 1) {
						if (crs.next()) {
							msgClobDoms =  crs.getClob(1);
							asnRequestXmlList.add(automationHelperImpl.getStringFromClob(msgClobDoms));
							

						}
					} else {
						while (crs.next()) {
							msgClobDoms =  crs.getClob(1);
							asnRequestXmlList.add(automationHelperImpl.getStringFromClob(msgClobDoms));
							

						}
					}
				}
			}
			crs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(crs!=null){
				try {
					crs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
		return asnRequestXmlList;
	}
}
