package com.doms.automation.bean;

public class OrderLineVO {

	public String PONumber;
	public String orderLineKey;
	public String orderHeaderKey;
	public String orderQuantity;
	public String shipToKey;
	public String billToKey;
	public String primeLineNo;
	public String subLineNo;
	public String lineType;
	public String carrierServiceCode;
	public String itemID;
	private String sapOrderLineKey;

	
	
	public String getSapOrderLineKey() {
		return sapOrderLineKey;
	}
	public void setSapOrderLineKey(String sapOrderLineKey) {
		this.sapOrderLineKey = sapOrderLineKey;
	}
	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	
	
	public String getShipToKey() {
		return shipToKey;
	}
	public void setShipToKey(String shipToKey) {
		this.shipToKey = shipToKey;
	}
	public String getBillToKey() {
		return billToKey;
	}
	public void setBillToKey(String billToKey) {
		this.billToKey = billToKey;
	}
	public String getPrimeLineNo() {
		return primeLineNo;
	}
	public void setPrimeLineNo(String primeLineNo) {
		this.primeLineNo = primeLineNo;
	}
	public String getSubLineNo() {
		return subLineNo;
	}
	public void setSubLineNo(String subLineNo) {
		this.subLineNo = subLineNo;
	}
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	public String getCarrierServiceCode() {
		return carrierServiceCode;
	}
	public void setCarrierServiceCode(String carrierServiceCode) {
		this.carrierServiceCode = carrierServiceCode;
	}
	public String getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(String orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public String getPONumber() {
		return PONumber;
	}
	public void setPONumber(String number) {
		PONumber = number;
	}
	public String getOrderLineKey() {
		return orderLineKey;
	}
	public void setOrderLineKey(String orderLineKey) {
		this.orderLineKey = orderLineKey;
	}
	public String getOrderHeaderKey() {
		return orderHeaderKey;
	}
	public void setOrderHeaderKey(String orderHeaderKey) {
		this.orderHeaderKey = orderHeaderKey;
	}
}
