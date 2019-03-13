package com.doms.automation.utils;

public class GlobalStoreConstants {

	
	public static final String ESWSTATUS_SHIPPED = "ESW0000005";
	public static final String ESWSTATUS_ARRIVEDINTERNATIONALHUB = "ESW0000006";
	public static final String ESWSTATUS_INVALIDSTATUS = "ESW0000026";
	public static final String ESWSTATUS_DEPARTEDINTERNATIONALHUB = "ESW0000007";
	public static final String ESWSTATUS_DEPARTEDORIGINCOUNTRY = "ESW0000008";
	public static final String ESWSTATUS_ARRIVEDDESTINATIONCOUNTRY = "ESW0000011";
	public static final String ESWSTATUS_CUSTOMSRELEASED = "ESW0000013";
	public static final String ESWSTATUS_OUTFORDELIVERY = "ESW0000014";
	public static final String ESWSTATUS_DELIVERED = "ESW0000015";
	public static final String ESWSTATUS_ATTEMPTEDDELIVERY = "ESW0000016";
	
	public static final String BASE_SHIPPED = "N";
	public static final String BASE_ARRIVEDINTERNATIONALHUB = "N";
	public static final String BASE_INVALIDSTATUS = "N";
	public static final String BASE_DEPARTEDINTERNATIONALHUB = "N";
	public static final String BASE_DEPARTEDORIGINCOUNTRY = "N";	
	public static final String BASE_ARRIVEDDESTINATIONCOUNTRY = "1400.10.20";
	public static final String BASE_CUSTOMSRELEASED = "1400.100";
	public static final String BASE_OUTFORDELIVERY = "1400.30.01";
	public static final String BASE_DELIVERED = "1400.70";
	public static final String BASE_ATTEMPTEDDELIVERY = "1400.40";
	
	public static final String STERSTATUS_SHIPPED = "Shipment Shipped";
	public static final String STERSTATUS_ARRIVEDINTERNATIONALHUB = "Shipment Shipped";
	public static final String STERSTATUS_INVALIDSTATUS = "Shipment Shipped";
	public static final String STERSTATUS_DEPARTEDINTERNATIONALHUB = "Shipment Shipped";
	public static final String STERSTATUS_DEPARTEDORIGINCOUNTRY = "Shipped";	
	public static final String STERSTATUS_ARRIVEDDESTINATIONCOUNTRY = "Arrived at Destination Country";
	public static final String STERSTATUS_CUSTOMSRELEASED = "Customs Cleared";
	public static final String STERSTATUS_OUTFORDELIVERY = "Out For Delivery";
	public static final String STERSTATUS_DELIVERED = "Delivered";
	public static final String STERSTATUS_ATTEMPTEDDELIVERY = "Delivery Attempt 1";

	public static final String SHIPPED = "Shipped";
	public static final String ARRIVEDINTERNATIONALHUB = "Arrived International Hub";
	public static final String INVALIDSTATUS = "INVALIDSTATUS";
	public static final String DEPARTEDINTERNATIONALHUB = "Departed International Hub";
	public static final String DEPARTEDORIGINCOUNTRY = "Departed Origin Country";
	public static final String ARRIVEDDESTINATIONCOUNTRY = "Arrived Destination Country";
	public static final String CUSTOMSRELEASED = "Customs Released";
	public static final String OUTFORDELIVERY = "Out for Delivery";
	public static final String DELIVERED = "Delivered";
	public static final String ATTEMPTEDDELIVERY = "Attempted Delivery";
	
	public static final String GET_BASEDROPSTATUS ="select BASE_DROP_STATUS from extn_delivery_status_details where delivery_tracker_key in "
			+ "(select delivery_tracker_key from extn_delivery_status_tracker where extn_order_no=?)";
	
	
	
	
	
	
	
	


}
