package com.doms.automation.dbvalidations;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import com.doms.automation.bean.ReqResVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.utils.DbValidationConstants;
import com.doms.automation.utils.NikeDOMSConstants;


public class VerifyInvoiceInDoms {
	
	public ReqResVO verifyInvoiceInDoms(String orderLineKey,
			NikeDOMSConnectionDAO nikedomsconnectiondao){
		ReqResVO reqResVO = new ReqResVO();
		try {
			nikedomsconnectiondao.getDBValues(orderLineKey,
					DbValidationConstants.VERIFY_INVOICE, NikeDOMSConstants.DOMS);
			CachedRowSet crs= RowSetProvider.newFactory().createCachedRowSet();
			crs = nikedomsconnectiondao.getRowSet();
			int size = crs.size();
			reqResVO.setReqXML("Verifying invoice getting created in doms");
			if(size>0){
				
				reqResVO.setResXML("Success");
			}
			else{
				reqResVO.setResXML("Error");
				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return reqResVO;
		
	}
	
	public ReqResVO verifyInvoiceType(String invoiceType,String orderLineKey,
			NikeDOMSConnectionDAO nikedomsconnectiondao){
		ReqResVO reqResVO = new ReqResVO();
		try {			
			nikedomsconnectiondao.getDBValuesPAC(invoiceType, orderLineKey,
					DbValidationConstants.VERIFY_INVOICE_TYPE, NikeDOMSConstants.DOMS);
			CachedRowSet crs= RowSetProvider.newFactory().createCachedRowSet();
			crs = nikedomsconnectiondao.getRowSet();
			int size = crs.size();
			reqResVO.setReqXML("Verifying invoice type "+invoiceType+" getting created in doms");
			if(size>0){
				
				reqResVO.setResXML("Success");
			}
			else{
				reqResVO.setResXML("Error");
				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return reqResVO;
		
	}
	
	

}
