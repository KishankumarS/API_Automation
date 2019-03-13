package com.doms.automation.utils;

public class DbValidationConstants {
	public static final String PUBLISH_TO_INTERNAL_QUEUE_FOR_CNS_OUT_RDT = "PublishToInternalQueueForCNS_OUT_RDT";
	public static final String PUBLISH_TO_STERLING_X175_QUEUE_OUT_RDT = "PublishToSterlingX175Queue_OUT_RDT";
	public static final String EXECUTE_EXTN_ASYNC_REQ_SVC = "ExecuteExtnAsynsReq_svc";
	public static final String DOMS_EMAIL_EXECUTESERVICE_NAME = "PublishToSterlingX175Queue_OUT_svc";
	public static final String ORDER_CONFIRM = "OrderConfirm";
	public static final String PickUp = "PickUp";
	public static final String DOMS_PickUp_CONFIRM_EVENT_NAME = "/commerce/order/ReadyForPickup";
	public static final String COMMS_PickUp_CONFIRM_SUBCATEGORY = "PickUp";
	public static final String DOMS_REFUNDFAILUR_EVENT_NAME="Name=\"/commerce/order/RefundFailure1\"";
	public static final String DOMS_ORDERCONFIRM_EVENT_NAME = "/commerce/order/OrderConfirmation";
	public static final String COMMS_ORDERCONFIRM_SUBCATEGORY = "Confirmation";
	public static final String SHIPMENT_CONFIRM = "ShipmentConfirm";
	public static final String DOMS_SHIPMENT_CONFIRM_EVENT_NAME = "/commerce/order/ShipConfirmation";
	public static final String COMMS_SHIPMENT_CONFIRM_SUBCATEGORY = "ShipConfirmation";
	public static final String RET_ORDER_CONFIRM = "RetOrderConfirm";
	public static final String DOMS_RET_ORDER_CONFIRM_EVENT_NAME = "/commerce/order/ReturnOrderConfirmation";
	public static final String REFUND_CONFIRM = "RefundConfirm";
	public static final String DOMS_REFUND_CONFIRM_EVENT_NAME = "/csr/Refund";
	public static final String COMMS_REFUND_CONFIRM_SUBCATEGORY = "Cancellation";
	public static final String REFUND_NOTIFICATION = "RefundNotification";
	public static final String DOMS_REFUND_NOTIFICATION_EVENT_NAME = "/commerce/order/InvoiceReturn";
	public static final String COMMS_REFUND_NOTIFICATION_SUBCATEGORY = "Invoice";
	public static final String CANCELLATION = "Cancellation";
	public static final String DOMS_CANCELLATION_EVENT_NAME = "/csr/OrderCancellationPerCustomerRequest";
	public static final String COMMS_CANCELLATION_SUBCATEGORY = "Cancellation";
	public static final String FACTORY_COMPLETE_CONFIRM = "FactoryCompleteConfirm";
	public static final String DOMS_FACTORY_COMPLETE_CONFIRM_EVENT_NAME = "/commerce/NikeID/FactoryCompleted";
	public static final String COMMS_FACTORY_COMPLETE_SUBCATEGORY = "Factory Complete";
	public static final String DELiVERY_CONFIRM = "DeliveryConfirm";
	public static final String COMM_GS_REFUND_EMAIL="/commerce/order/RefundConfirmation";
	public static final String DOMS_DELiVERY_CONFIRM_EVENT_NAME = "/commerce/order/DeliveryConfirmation";
	public static final String COMMS_DELiVERY_CONFIRM_SUBCATEGORY = "Order Delivered";
	public static final String NIKEID_DELiVERY_CONFIRM = "NikeIdDeliveryConfirm";
	public static final String DOMS_NIKEID_DELiVERY_CONFIRM_EVENT_NAME = "/commerce/NikeID/DeliveryConfirmationToStore";
	public static final String COMMS_NIKEID_DELiVERY_CONFIRM_SUBCATEGORY = "Delivery";
	public static final String NIKEID_ORDER_STARTED_CONFIRM = "NikeIdOrderStartedConfirm";
	public static final String DOMS_NIKEID_ORDER_STARTED_CONFIRM_EVENT_NAME = "/commerce/NikeID/OrderStarted";
	public static final String COMMS_NIKEID_ORDER_STARTED_CONFIRM_SUBCATEGORY = "Order Started";
	public static final String NIKEID_SHIP_CONFIRMATION = "NikeIdShipConfirmation";
	public static final String DOMS_NIKEID_SHIP_CONFIRMATION_CONFIRM_EVENT_NAME = "/commerce/order/ShipConfirmation/NIKEiD";
	public static final String DOMS_RETURN_PICKED_UP_EVENT_NAME = "/commerce/order/ReturnPickedUp";
	public static final String COMMS_NIKEID_SHIP_CONFIRMATION_CONFIRM_SUBCATEGORY = "ShipConfirmation";
	
	public static final String DOMS_ACKNOWLEDGEMESSAGE_EXECUTESERVICE_NAME = "AcknowledgeOrderCreation_OUT_svc";
	public static final String CN_DS20_EXECUTESERVICENAME = "Publish_Financials_SAP_svc";
	public static final String DETERMINEFTCNOTIFICATION_EXECUTESERVICE_NAME = "DetermineFTCNotification_svc";
	public static final String DOMS_REMORSE_EHOLD = "REMORSE";
	public static final String DOMS_FRAUD_EHOLD = "FCI_CHO";

	public static final String FIN03 = "FIN03";
	public static final String FIN07 = "FIN07";
	public static final String PO_Match = "PO Match";
	public static final String STO14 = "STO14";
	public static final String CARRIER_CODE_ORIGINSCAN = "AF";
	public static final String CARRIER_CODE_INTRANSIT = "X6";
	public static final String CARRIER_CODE_DELIVERED = "D1";

	public static final String CRITERIA_ID_FIN03 = "Publish_FinanceQueue_OUT_svc_RDT";
	public static final String CRITERIA_ID_FIN07 = "Publish_TAXQueue_OUT_svc_RDT";
	public static final String CRITERIA_ID_PO_Match = "Publish_POQueue_OUT_svc_RDT";
	public static final String CRITERIA_ID_STO14 = "Publish_StoreQueue_OUT_svc_RDT";

	public static final String EXEC_SERVICE_NAME_FIN03 = "Publish_FinanceQueue_OUT_svc";
	public static final String EXEC_SERVICE_NAME_FIN03_RFS = "Publish_FinanceQueue_OUT_svc_R1604A";
	public static final String EXEC_SERVICE_NAME_FIN07 = "Publish_TAXQueue_OUT_svc";
	public static final String EXEC_SERVICE_NAME_FIN07_NEW = "Publish_TaxQueue_OUT_svc_R1604A";
	public static final String EXEC_SERVICE_NAME_PO_Match = "Publish_POQueue_OUT_svc";
	public static final String EXEC_SERVICE_NAME_PO_Match_NEW = "Publish_POQueue_OUT_svc_R1604A";
	public static final String EXEC_SERVICE_NAME_STO14 = "Publish_StoreQueue_OUT_svc";
	public static final String RESOLVE_COC_RELEASE_HOLD_SVC = "Resolve_COC_RELEASE_Hold_svc";
	public static final String COC_RELEASE_HOLD = "COC_RELEASE_HOLD";
	public static final String ORGIN_SCAN = "Origin Scan";
	public static final String PARTIALLY_ORGIN_SCAN = "Partially Origin Scan";
	public static final String DELIVERED = "Delivered";
	public static final String PARTIALLY_DELIVERED = "Partially Delivered";
	public static final String OFBTRF = "OFBTRF";
	
	public static final String PUBLISH_STORE_QUEUE_OUT_SVC = "Publish_StoreQueue_OUT_svc";
	public static final String INVOICETYPE_RETURN = "InvoiceType=\"RETURN\"";
	public static final String COMMUNICATION_OUEUE_EXEC_SVC="PublishToSterlingX175Queue_OUT_svc";
	public static final String ESW_CANCEL_OUEUE_EXEC_SVC="PostCancelUpdateToeSW_svc";
	public static final String SAPCANCELLATION_REQ_RES_SVC="SAPCancellation_Req_Res_svc";	
	public static final String EXECUTEPOCONSOLIDATION_SVC ="ExecutePOConsolidation_svc";
	public static final String PUBLISH_ASN_SPARTAN_SVC="Publish_ASN_Spartan_svc";
	public static final String GS_ADDRESS_NAME1_DS20="ESHOPWORLD";
	public static final String GS_ADDRESS_NAME2_DS20="ARAMEX NETHERLANDS B.V.";
	public static final String OFBT_REFUND_FAILURE_SVC="OFBT_Refund_Failure_svc";
	public static final String RETURNED_AT_STORE="Returned At Store";
	public static final String ESM="ESM";
	public static final String HDX="HDX";
	public static final String LN="LN";
	public static final String QD="QD";
	public static final String QT="QT";
	public static final String VJ="VJ";
	public static final String VL="VL";
	public static final String VO="VO";
	public static final String VP="VP";
	public static final String VT="VT";
	public static final String VX="VX";
	public static final String XC="XC";
	public static final String XE="XE";
	public static final String Y3="Y3";
	public static final String MD="MD";
	public static final String ARV="ARV";
	public static final String FT="FT";
	public static final String C4="C4";
	
	public static final String CUSTOMERNOTIFICATIONROUTER_SVC="CustomerNotificationRouter_svc";
	public static final String POSTCANCELLATION_OUT_SVC="PostCancellation_OUT_svc";
	public static final String PAYMENTREQUESTCANCEL_SVC="PaymentRequestCancel_svc";
	public static final String PUBLISHPUSHMSGTOCDS_OUT_SVC="PublishPushMsgToCDS_OUT_svc";
	
	public static final String POSTCANCELLATION_OUT_RDT="PostCancellation_OUT_RDT";
	
	public static final String COLD_RETURN= "select * from yfs_order_header where trim(ORDER_TYPE)=? and trim(SEARCH_CRITERIA_1)=?";
			
	// query for shipnode verification
	public static final String VERIFY_SHIPNODE = "select count(*) from yfs_order_line where prime_line_no=? and shipnode_key=? and order_header_key in"
			+ " (select order_header_key from yfs_order_header where order_no=?)";
	// query for invoice verification
	public static final String VERIFY_INVOICE = "select * from yfs_order_invoice where order_invoice_key in"
			+ " (select order_invoice_key from  YFS_ORDER_INVOICE_DETAIL where trim(order_line_key)=?)";
	// query for invoice publish verification
	public static final String VERIFY_INVOICE_PUBLISH = "select * from extn_invoice_publish where extn_invoice_state =? and extn_invoice_no in (select invoice_no from yfs_order_invoice where invoice_type in (InvoiceTypes) and  order_invoice_key in (select order_invoice_key from  yfs_order_invoice_detail where order_line_key=?))";
	// query for invoice verification
	public static final String VERIFY_INVOICE_TYPE = "select count(*) from yfs_order_invoice where invoice_type=? and order_invoice_key in"
			+ " (select order_invoice_key from  YFS_ORDER_INVOICE_DETAIL where order_line_key=?)";
	// query to get invoice no
	public static final String GET_INVOICE_NO = "select invoice_no from yfs_order_invoice where invoice_type in (InvoiceType) and order_invoice_key in (select order_invoice_key from  yfs_order_invoice_detail where order_line_key=?) order by createts desc";
	// update
	public static final String UPDATE_REQ_CANCEL_DATE = "update yfs_order_line set REQ_CANCEL_DATE=sysdate-1 where trim(order_line_key)=?";
	public static final String GET_ASYNC_PROCESS_QUEUE_DETAILS_BY_SERVICE_NAME = "select * from extn_async_process_q where EXECUTESERVICENAME like ? and data_key like ?";
	public static final String GET_ASYNC_PROCESS_QUEUE_ARCHIVE_DETAILS = "select * from extn_async_process_q where EXECUTESERVICENAME like ? and data_key like ?";
	public static final String GET_ASYNC_PROCESS_QUEUE_DETAILS_COUNT = "select count(*) from extn_async_process_q where EXECUTESERVICENAME = ? and data_key = ?";
	public static final String GET_ASYNC_PROCESS_QUEUE_ARCHIVE_DETAILS_COUNT = "select count(*) from extn_async_process_q_archive where EXECUTESERVICENAME = ? and data_key = ?";
	

	public static final String GET_REMORSE_HOLD_STATUS = "select Trim(status) from yfs_order_hold_type where order_header_key in (select order_header_key from yfs_order_header where order_no=?) and HOLD_TYPE='REMORSE'";
	
	public static final String GET_FRAUD_HOLD_STATUS = "select Trim(status) from yfs_order_hold_type where order_header_key in (select order_header_key from yfs_order_header where order_no=?) and trim(HOLD_TYPE) in(${FraudStatuses})";
	public static final String GET_NIKEID_LINE_COUNT = "select count(*) from yfs_order_line where line_type='NIKEID' and order_header_key in (select order_header_key from yfs_order_header where order_no =?";
	public static final String GET_NIKEID_WO_COUNT = "Select work_order_no from yfs_work_order where order_header_key in (Select order_header_key from yfs_order_header where order_no=?)";
	public static final String GET_SCHEDULED_SHIP_NODE = "select * from yfs_order_line_schedule where createts in (select max(createts) from yfs_order_line_schedule where order_line_key=? and ship_node is not null)";
	public static final String GET_PO_COUNT = "select order_no from yfs_order_header where order_header_key in (select order_header_key from yfs_order_line where CHAINED_FROM_ORDER_HEADER_KEY in (select order_header_key from yfs_order_header where order_no =?))";
	public static final String GET_RETURN_ORDERLINEKEY = "select * from yfs_order_line where DERIVED_FROM_ORDER_LINE_KEY=? and ordered_qty>0";
	public static final String UPDATE_COLLECTION_DATE = "update yfs_charge_transaction set COLLECTION_DATE=sysdate where CHARGE_TYPE='CHARGE' and order_header_key in (select order_header_key from yfs_order_header where order_no=?)";
	public static final String GET_INV_NO_LINE_NO = "select invoice_no from yfs_order_invoice where invoice_type in (InvoiceType) and order_invoice_key in (select order_invoice_key from  yfs_order_invoice_detail where trim(order_line_key) in(select trim(order_line_key) from yfs_order_line where prime_line_no=? and order_header_key in(select order_header_key from yfs_order_header where order_no=?)))";
	public static final String GET_DIG_PID_ASN_REQ_Q= "select MESSAGE_XML,PROCESSING_Q_KEY from extn_async_process_q where executeservicename = ? and MESSAGE_XML like ? order by createts desc";
	public static final String GET_DIG_PID_ASN_REQ_ARCHIVE= "select MESSAGE_XML from extn_async_process_q_archive where executeservicename =? and MESSAGE_XML like ? order by createts desc";
	public static final String GET_MESSAGE_XML_ASYNC_Q= "select MESSAGE_XML from extn_async_process_q where executeservicename =? and data_key=? and MESSAGE_XML like ? order by createts desc";
	public static final String GET_MESSAGE_XML_ASYNC_Q_ARCHIVE= "select MESSAGE_XML from extn_async_process_q_archive where process_name =? and data_key=? and MESSAGE_XML like ? order by createts desc";
	public static final String VERIFY_STAMPNOTE = "select count(*) from yfs_notes where table_key=?";
	public static final String UPDATE_SUSPEND_ANY_MORE_CHARGES ="update yfs_payment set suspend_any_more_charges='B' where order_header_key in (select order_header_key from yfs_order_header where order_no=?)";
	public static final String UPDATE_FTC_DATE ="update extn_async_process_q set available_date = SYSDATE  where executeservicename = ? and data_key=? ";
	public static final String UPDATE_FTC_DATE_3_Parameters = "update extn_async_process_q set available_date = SYSDATE where data_key = ? and EXECUTESERVICENAME = ? and  MESSAGE_XML like ?";
	public static final String GET_YFS_NOTES_ENTRY_COUNT = "select * from yfs_notes where table_key=? and reason_code=?";
	public static final String GET_YFS_REFERENCE_ENTRY_COUNT = "select * from yfs_reference_table where table_key=? and field_name1=?";
	public static final String GET_EXECUTESERVICENAME="select EXECUTESERVICENAME from DOM.EXTN_ASYNC_PROCESS_Q where EXECUTESERVICENAME like '%iD_Process_Pickup_Reminder%' and data_key in (select ORDER_LINE_KEY from DOM.YFS_ORDER_LINE where ORDER_HEADER_KEY in(select ORDER_HEADER_KEY from DOM.YFS_ORDER_HEADER where ORDER_NO=? and UOM='EACH'))";
	public static final String GET_MESSAGE_XML_ASYNC_Q_WITHOUT_DATAKEY= "select MESSAGE_XML from extn_async_process_q where executeservicename =? and MESSAGE_XML like ? order by createts desc";
	public static final String GET_MESSAGE_XML_ASYNC_Q_ARCHIVE_WITHOUT_DATAKEY= "select MESSAGE_XML from extn_async_process_q_archive where process_name =? and MESSAGE_XML like ? order by createts desc";
	public static final String GET_EXTN_SEQUENCE_NUMBER = "select EXTN_SEQUENCE_NUMBER from extn_invoice_publish where extn_invoice_no=? and EXTN_FINANCIAL_PUBLISH_TYPE = ?";
	public static final String GET_MESSAGE_XML_ASYNC_Q_WITH_PROCESSNAME= "select MESSAGE_XML from extn_async_process_q where data_key=? and process_name =?";
	public static final String GET_MESSAGE_XML_ASYNC_Q_ARCHIVE_WITH_PROCESSNAME= "select MESSAGE_XML from extn_async_process_q_archive where data_key=? and process_name =?";
	public static final String Publish_FinanceQueue_OUT_svc_R1604A = "Publish_FinanceQueue_OUT_svc_R1604A";
	public static final String DetermineOrderDelayNotification_svc = "DetermineOrderDelayNotification_svc";
	public static final String ProcessOrderDelayNotification_JP_svc = "ProcessOrderDelayNotification_JP_svc";
	public static final String GET_ASYNC_PROCESS_QUEUE_DETAILS_BY_SERVICE_NAME1 = "select * from extn_async_process_q where EXECUTESERVICENAME = ? and data_key = ? and MESSAGE_XML like ?";
	public static final String GET_ASYNC_PROCESS_QUEUE_ARCHIVE_DETAILS_BY_SERVICE_NAME = "select * from extn_async_process_q_archive where EXECUTESERVICENAME = ? and data_key = ? and MESSAGE_XML like ?";
	
	public static final String UPDATE_Expiry_DATE ="update yfs_inventory_reservation set EXPIRATION_DATE = SYSDATE-1  where trim(reservation_id)=? ";
	public static final String PUBLISHINVOICEPOSTVOID_SVC="PublishInvoicePostVoid_svc";
	
	public static final String UPDATE_Expiry_DATE_Past ="update yfs_inventory_reservation set EXPIRATION_DATE = TO_DATE('xdate', 'DD-MM-YYYY HH24:MI:SS','NLS_DATE_LANGUAGE = American')  where trim(reservation_id)=? ";
	public static final String UPDATE_Expiry_DATE_Future ="update yfs_inventory_reservation set EXPIRATION_DATE = TO_DATE('xdate', 'DD-MM-YYYY HH24:MI:SS','NLS_DATE_LANGUAGE = American')  where trim(reservation_id)=? ";
	public static final String UPDATE_Ship_DATEAs_Past ="update yfs_inventory_reservation set SHIP_DATE = TO_DATE('xdate', 'DD-MM-YYYY HH24:MI:SS','NLS_DATE_LANGUAGE = American')  where trim(reservation_id)=? ";
	public static final String GET_AVAILABLE_DATE_from_ASYNC_PROCESS_QUEUE="select AVAILABLE_DATE from extn_async_process_q where EXECUTESERVICENAME = 'TAX' and data_key like ?";
	public static final String GET_AVAILABLE_DATE_from_ASYNC_PROCESS_QUEUE_Archive="select AVAILABLE_DATE from extn_async_process_q_archive where EXECUTESERVICENAME = 'TAX' and data_key like ?";

	public static final String GET_EXTN_CODE_4 = "select EXTN_CODE_4 from EXTN_MASTER_CODES where EXTN_CODE_DESC_1=? and EXTN_CODE_2=?";
	public static final String GET_EXTN_CODE_4_CREDITCARD= "select EXTN_CODE_4 from EXTN_MASTER_CODES where EXTN_CODE_DESC_1='TenderType' and EXTN_CODE_5=? and EXTN_CODE_2 =?";
	public static final String GET_EXTN_CODE_4_GIFTCARD= "select EXTN_CODE_4 from EXTN_MASTER_CODES where EXTN_CODE_DESC_1=? and EXTN_CODE_2=? and EXTN_CODE_3 ='GIFTCARD'";
	
	public static final String VERIFY_RROENTRY = "select count(*) from yfs_order_header where EXTN_PARENT_ORDER_NO = ?";
	public static final String GETTIME_STATUSDETAILS = "select TO_char(EXTN_STATUS_TIME,'YYYY-MM-DD HH24:MI:SS') from extn_delivery_status_details where EXTN_TRACKING_NO = ? and carrier_status_code=?" ;
	public static final String GETTIME_STATUSTRACKER = "select TO_char(timestamp,'YYYY-MM-DD HH24:MI:SS') from extn_delivery_status_tracker where EXTN_TRACKING_NO= ?";
	public static final String GET_CREATETS_TIME = "select TO_char(createts,'YYYY-MM-DD HH24:MI:SS') from yfs_order_header where order_no =?";
	public static final String GET_AVAILABLE_DATE_FROM_YFS_TASK_Q = "select TO_char(available_date,'YYYY-MM-DD HH24:MI:SS') from yfs_task_q where data_key in(select order_release_key from yfs_order_release where order_header_key in (select order_header_key from yfs_order_header where order_no=?))";
	public static final String UPDATE_AVAILABLE_DATE_IN_YFS_TASK_Q ="update yfs_task_q set available_date=SYSDATE  where data_key in("
			+ "select order_release_key from yfs_order_release where order_header_key in (select order_header_key from yfs_order_header where order_no=?))";
	public static final String GET_AVAILABLE_DATE_FROM_ASYNC_PROCESS_Q="select TO_char(available_date,'YYYY-MM-DD HH24:MI:SS') from extn_async_process_q where process_name=?"
			+ " and data_key=?";
	public static final String GET_AVAILABLE_DATE_FROM_ASYNC_PROCESS_Q_ARCHIVE = "select TO_char(available_date,'YYYY-MM-DD HH24:MI:SS') from extn_async_process_q_archive where process_name=?"
			+ " and data_key=?";
	public static final String GET_AVAILABLE_DATE_FROM_ASYNC_PROCESS_Q_FOR_LINE_NO = "select TO_char(available_date,'YYYY-MM-DD') from extn_async_process_q where data_key = (select order_line_key from yfs_order_line where PRIME_LINE_NO = ? and order_header_key in (select order_header_key from yfs_order_header where order_no= ?))";

	public static final String UPDATE_AVAILABLE_DATE_IN_ASYNC_PROCESS_Q="update extn_async_process_q set available_date=SYSDATE where process_name=? "
			+ "and data_key=?"; 
	public static final String GET_ORDER_HEADER_KEY_OF_PO_NUMBER="select order_header_key from yfs_order_line where order_header_key in (select order_header_key from"
			+ " yfs_order_header where order_no= ?)";
	

	public static final String GET_EXPECTED_DATE_TIME_FROM_ASYNC_PROCESS_Q = "select TO_char(EXPECTED_DATE,'YYYY-MM-DD HH24:MI:SS') from yfs_order_date where DATE_TYPE_ID = ? and order_line_key =(select order_line_key from yfs_order_line where PRIME_LINE_NO = ? and order_header_key in (select order_header_key from yfs_order_header where order_no = ?))";

	public static final String GET_returnINVOICE_NO = "select invoice_no from yfs_order_invoice where order_invoice_key in (select order_invoice_key from  yfs_order_invoice_detail where order_line_key=?) order by createts desc";
	public static final String GET_EXCHANGE_ORDER_NO_WITH_ORDER_NO ="select order_no from yfs_order_header where return_oh_key_for_exchange in (select order_header_key from yfs_order_line where derived_from_order_header_key"
							+" in(select order_header_key from yfs_order_header where order_no = ?))";
	public static final String GET_ORDER_NO_WITH_EXCHANGE_ORDER_NO="select order_no from yfs_order_header where order_header_key in (select derived_from_order_header_key from yfs_order_line where" 
							+" order_header_key in (select return_oh_key_for_exchange from yfs_order_header where "
							+"order_no = ?))";
	
	public static final String GET_YFS_ORDER_INVOICE_AMOUNTS = "select total_amount,amount_collected from ( select total_amount,amount_collected from yfs_order_invoice where order_no=? order by createts desc ) where ROWNUM <= 1";
	public static final String GET_All_HOLDTYPES = "select HOLD_TYPE from DOM.YFS_ORDER_HOLD_TYPE where order_line_key=(select order_line_key from DOM.YFS_ORDER_LINE where PRIME_LINE_NO=? and ORDER_HEADER_KEY in(select ORDER_HEADER_KEY from DOM.YFS_ORDER_HEADER where ORDER_NO=?))";
	public static final String  GET_RRO_EVENT_COUNT="select count(*) from extn_async_process_q where executeservicename in($list) AND (data_key=? or data_key=?)";
	public static final String UPDATE_EXTN_MASTER_CODE="update EXTN_MASTER_CODES set EXTN_CODE_2 = ?  where EXTN_CODE_1 = 'FraudMaster'";
	public static final String GET_CANCEL_REQUEST_QTY_FOR_ORDER_LINE = "select EXTN_CANCEL_REQUEST_QTY from yfs_order_line where order_header_key in (select order_header_key from yfs_order_header where order_no = ?) and PRIME_LINE_NO = ?";
	public static final String GET_POST_AUTH_MSG_ENTRY_IN_YFS_TASK_Q = "select * from yfs_task_q where data_key  in (select order_header_key from yfs_order_header where order_no=?) and TRANSACTION_KEY='201606171134302383764   '";
	public static final String GET_DB_DATE="select TO_char(sysdate,'YYYY-MM-DD HH24:MI:SS') from dual";
	public static final String GET_EXTN_CODE_2="select EXTN_CODE_2 from EXTN_MASTER_CODES where EXTN_CODE_1 = 'FraudMaster'";
	public static final String GET_YFS_TASQ_Q_DETAILS="select * from yfs_task_q where data_key  in (select order_header_key from yfs_order_header where order_no=?)";




}
