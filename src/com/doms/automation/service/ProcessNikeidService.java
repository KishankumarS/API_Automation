package com.doms.automation.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.service.CreateOrderService;
import com.doms.automation.service.PostRequest;
import com.doms.automation.utils.NikeDOMSApplicationUtils;
import com.doms.automation.utils.NikeDOMSConstants;
import com.sun.rowset.CachedRowSetImpl;

public class ProcessNikeidService {

	String responseData = "Success";
	String environment, orderNo, orderHeaderKey, enterPriseCode, nikeidSGNo,
			WONumber, SGOrderlineKey,SGOrderedQty;
	CachedRowSet crs ;
	String trackingNo;
	PostRequest postReq = new PostRequest();
	NikeDOMSConnectionDAO nikedomsconnectiondao;
	
	public String getTrackingNo() {
		return trackingNo;
	}



	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}



	public void initialize(NikeDOMSConnectionDAO nikedomsconnectiondao,String environment,
			String orderNo,String orderLineKey,String enterpriseCode){
		this.environment = environment;
		this.orderNo = orderNo;
		this.enterPriseCode = enterpriseCode;
		try {
			this.nikedomsconnectiondao = nikedomsconnectiondao;
			nikedomsconnectiondao.getDBValuesNikeiD(orderNo, orderLineKey,
					NikeDOMSConstants.getNikeidSGno,NikeDOMSConstants.DOMS);
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikedomsconnectiondao.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if(size>0){
				if(size==1){
					if (crs.next()) {
						nikeidSGNo = crs.getString(1);
						SGOrderlineKey = crs.getString(2);
						SGOrderedQty = crs.getString(3); 
						}
				}
				else{
					while (crs.next()) {
						nikeidSGNo = crs.getString(1);
						SGOrderlineKey = crs.getString(2);
						SGOrderedQty = crs.getString(3);
						}
				}
				}
			
			nikedomsconnectiondao.getDBValuesNikeiD(orderNo, orderLineKey,
					NikeDOMSConstants.getNikeidWOno,NikeDOMSConstants.DOMS);
			crs = nikedomsconnectiondao.getRowSet();
			size = crs.size();
			crs.beforeFirst();
			if(size>0){
				if(size==1){
					if (crs.next()) {
						WONumber = crs.getString(1); 
						}
				}
				else{
					while (crs.next()) {
						WONumber = crs.getString(1);
						}
				}
				}
			
		}  catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public String paymentSettlement(EnvConfigVO envConfigVO) {
		String responseData = "Success";
		NikeDOMSApplicationUtils util = new NikeDOMSApplicationUtils();

		try {

			String inputXml = "<Order OrderHeaderKey=\"" + orderHeaderKey
					+ "\"/>";

			responseData = postReq.postXML(environment, inputXml,
					"requestCollection", "N", "<Order",envConfigVO);
			inputXml = "<<ExecuteCollection OrderHeaderKey=\"" + orderHeaderKey
					+ "\"/>";

			responseData = postReq.postXML(environment, inputXml,
					"executeCollection", "N", "<Order",envConfigVO);
			inputXml = "<Order OrderHeaderKey=\"" + orderHeaderKey + "\"/>";

			responseData = postReq.postXML(environment, inputXml,
					"requestCollection", "N", "<Order",envConfigVO);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseData;
	}
	

	public ReqResVO isSameTrackingId(NikeDOMSConnectionDAO nikedomsconnectiondao) {
	
		ReqResVO reqResVO = new ReqResVO();
		reqResVO.setResXML(getTrackingNo().equals(getTrackingNoFromDB(nikedomsconnectiondao))?
				"Success":"Error");
		return reqResVO;
	}
	public String getTrackingNoFromDB(NikeDOMSConnectionDAO nikedomsconnectiondao){
		String trackingNo="";
		try {

			
			nikedomsconnectiondao.getDBValues(nikeidSGNo, NikeDOMSConstants.getNikeiDTrackingNo,NikeDOMSConstants.DOMS);
			crs=nikedomsconnectiondao.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if(size>0){
				if(size==1){
					if (crs.next()) {
						trackingNo = crs.getString(1); 
						}
				}
				else{
					while (crs.next()) {
						trackingNo = crs.getString(1);
						}
				}
				}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trackingNo;
		
	}
	

}
