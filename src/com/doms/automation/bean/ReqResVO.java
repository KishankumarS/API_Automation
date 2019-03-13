package com.doms.automation.bean;

import java.util.ArrayList;

public class ReqResVO {
	public String reqXML;
	public String resXML;
	public String orderStatus;
	public String paymentStatus;
	public String authResponse;
	public String comment;
	public boolean isNotSuccess=false;
	public String retVariable;
	ArrayList<WarningQueryVO> warningQueryVOList;
	
	
	
	
	public String getRetVariable() {
		return retVariable;
	}
	public void setRetVariable(String retVariable) {
		this.retVariable = retVariable;
	}
	public ArrayList<WarningQueryVO> getWarningQueryVOList() {
		return warningQueryVOList;
	}
	public void setWarningQueryVOList(ArrayList<WarningQueryVO> warningQueryVOList) {
		this.warningQueryVOList = warningQueryVOList;
	}
	public boolean getIsNotSuccess() {
		return isNotSuccess;
	}
	public void setIsNotSuccess(boolean isSuccess) {
		this.isNotSuccess = isSuccess;
	}	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the authResponse
	 */
	public String getAuthResponse() {
		return authResponse;
	}
	/**
	 * @param authResponse the authResponse to set
	 */
	public void setAuthResponse(String authResponse) {
		this.authResponse = authResponse;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	
	public String getReqXML() {
		return reqXML;
	}
	public void setReqXML(String reqXML) {
		this.reqXML = reqXML;
	}
	public String getResXML() {
		return resXML;
	}
	public void setResXML(String resXML) {
		this.resXML = resXML;
	}

}
