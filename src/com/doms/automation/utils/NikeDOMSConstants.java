package com.doms.automation.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class NikeDOMSConstants {

	// MappingSheet AttributeTypes
	//public static final String PATH ="C:\\git\\trunkWsDynamicJsp\\RegressionBase\\WebContent";
	public static final String PATH = "C:\\Program Files\\apache-tomcat-8.0.29";
	
	// public static final String PATH ="CATALINA_HOME";
	public static final String ATTR_ORDERNO = "ORDERNO";
	public static final String ATTR_DEFAULT = "DEFAULTVALUE";
	public static final String ATTR_UNIQUE = "UNIQUENUMBER";
	public static final String ATTR_NOTREQD = "SKIP";
	public static final String ATTR_TIME = "TIMESTAMP";
	public static final String ATTR_CUSTOM = "CUSTOM";
	public static final String ATTR_COLUMN = "COLUMN";
	public static final String DATA_SHEET = "DATASHEET";
	public static final String ORDER = "<Order";
	public static final String STATUS_OK = "Status='OK'";
	public static final String STATUS_PUBLISHED = "Status=\"Published\"";
	public static final String doms = "DOMS";
	public static final String DOMS = "Doms";
	public static final String PAC = "Pac";
	public static final String COMMS = "Comms";
	public static final String PAYMENT = "Payment";
	public static final String CSR = "CSR";
	public static final String ATGEU = "ATGEU";
	public static final String ATGUS = "ATGUS";
	public static final String us = "us";
	public static final String US = "us";
	public static final String eu = "eu";
	public static final String GB = "GB";
	public static final String ap = "ap";
	public static final String CN = "CN";
	public static final String JP = "JP";
	public static final String NIKEUS = "NIKEUS";
	public static final String NIKEID = "NIKEID";
	public static final String INLINE = "INLINE";
	public static final String NIKEEUROPE = "NIKEEUROPE";
	public static final String NIKECN = "NIKECN";
	public static final String NIKEJP = "NIKEJP";
	public static final String STORE = "STORE";
	public static final String STANDARD = "STANDARD";
	public static final String MPOS = "MPOS";
	public static final String POS = "POS";
	public static final String CREDIT_MEMO = "CREDIT_MEMO";
	public static final String INFO = "INFO";
	public static final String COMMUNICATION_RAW = "communication.raw";
	public static final String DESERIALIZED_MESSAGE = "DESERIALIZED_MESSAGE";
	public static final String EXTN_ASYNC_PROCESS_Q_ARCHIVE = "EXTN_ASYNC_PROCESS_Q_ARCHIVE";
	public static final String EXTN_ASYNC_PROCESS_Q = "EXTN_ASYNC_PROCESS_Q";
	public static final String CheckOrderForFraudHold_RDT = "CheckOrderForFraudHold_RDT";
	public static final String InitiateFraudCheck_OUT_svc = "InitiateFraudCheck_OUT_svc";
	public static final String InitiateFraudCheck_svc = "InitiateFraudCheck_svc";
	public static final String CallFeedzaiUpdate_svc = "CallFeedzaiUpdate_svc";
	public static final String FeedzaiPostAuth_0001 = "FeedzaiPostAuth_0001";
	public static final String ORDER_STATUS_CREATED = "Created";
	public static final String ORDER_STATUS_AWAITING_CHAINED_ORDER_CREATION = "Awaiting Chained Order Creation";
	public static final String ORDER_CREATED = "OrderCreated";
	public static final String DELIVERED = "Delivered";
	public static final String SHIPPED = "Shipped";
	public static final String CANCELED = "CANCELED";
	public static final String BRD = "BRD";
	public static final String MENLO = "MENLO";
	public static final String BUYER_USERID = "BuyerUserId";
	public static final String SHIP_NODE = "ShipNode";
	public static final String ITEM_ID = "ItemID";
	public static final String LINE_TYPE = "LineType";
	public static final String NIKEXA = "NIKEXA";
	public static final String CurrencyCode_USD = "USD";
	public static final String PaymentRuleIdForNIKEXA = "NIKEXA_PAYMENTRULE";
	public static final String STATUS_CODE_1100 = "1100";
	public static final String STATUS_CODE_1300 = "1300";
	public static final String Error= "error";
	
	public static final String SHIPMENT_KEY = "SHIPMENT_KEY";
	public static final String PUBLISH_PUSH_MSG_TO_CDS_OUT_SVC_RDT = "PublishPushMsgToCDS_OUT_svc_RDT";
	
	public static final ArrayList<String> FSO_BILL_TO_ADDRESS = new ArrayList<String>(Arrays.asList("INLINE",
			"GLOBAL STORE EUROPE", "UNIT 701 NorthWest Business Park", "Kilshane Way", "DUBLIN", "DB", "D15", "IE"));
	public static final ArrayList<String> NIKEID_STATUS = new ArrayList<String>(
			Arrays.asList("1100", "1600", "2100.30", "2100", "1100.400", "1100.500", "1100.600", "1300", "1310"));
	public static final ArrayList<String> INLINE_STATUS = new ArrayList<String>(
			Arrays.asList("1100", "1500", "1600", "2100", "1300", "1310"));
	public static final String getOrderNo = "select order_no from yfs_order_header where order_no=?";
	public static final String orderLineQuery = "Select yh.order_no,trim(yl.order_line_key),yh.order_header_key,yl.ordered_qty from yfs_order_header yh,"
			+ "yfs_order_line yl where yh.order_header_key = yl.order_header_key and yl.UOM ='EACH' and yh.order_header_key IN "
			+ "(Select order_header_key from yfs_order_line where chained_from_order_header_key IN "
			+ "(SELECT ORDER_HEADER_KEY FROM YFS_ORDER_HEADER WHERE ORDER_NO = ?)and line_type = 'INLINE')";
	public static final String PIDHoldCountQuery = "select count(*) from yfs_order_hold_type where order_header_key in (select order_header_key from yfs_order_header where order_no= ?) AND HOLD_TYPE ='PID' ";
	public static final String orderLineKeyPID_Holds = "select trim(order_line_key) from yfs_order_hold_type where order_header_key in (select order_header_key from yfs_order_header where order_no= ?)AND TRIM(HOLD_TYPE) in ('PID', 'PIDCHALLENGE') AND STATUS='1100'";
	public static final String orderLineKeyPIDQuery = "select trim(order_line_key) from yfs_order_hold_type where order_header_key in (select order_header_key from yfs_order_header where order_no= ?)AND HOLD_TYPE = 'PID'";

	public static final String OEDERLINEKEY_PID = "select * from yfs_order_line where PACKLIST_TYPE in ('NIKEID','SOCKSID', 'DIGITALPID', 'JERSEYID')  and prime_line_no <100 and line_type <> 'SERVICE' and  order_header_key in (SELECT ORDER_HEADER_KEY FROM YFS_ORDER_HEADER WHERE ORDER_NO = ?)";
	public static final String ORDERLINEKEY_PID_GS = "select * from yfs_order_line where line_type='NIKEID'  and prime_line_no <100 and  order_header_key in (SELECT ORDER_HEADER_KEY FROM YFS_ORDER_HEADER WHERE ORDER_NO = ?)";
	public static final String orderHeaderKeyQuerry = "select trim(enterprise_key),order_header_key from yfs_order_header where order_no= ?";

	public static final String getLinesQuery = "select order_no from yfs_order_header where order_header_key "
			+ "in(select order_header_key from yfs_order_line where chained_from_order_line_key in "
			+ "(SELECT trim(yl.order_line_key) FROM yfs_order_line yl,yfs_order_header yh "
			+ "where yh.order_header_key = yl.order_header_key AND yl.line_type= 'INLINE' "
			+ "AND yh.order_header_key in " + "(select order_header_key from yfs_order_header where order_no=?)))";

	public static final String getOrderLineQuery = "SELECT trim(yl.order_line_key) FROM yfs_order_line yl,yfs_order_header yh "
			+ "where yh.order_header_key = yl.order_header_key AND yl.line_type= 'INLINE' "
			+ "AND yh.order_header_key in (select order_header_key from yfs_order_header where order_no=?)";

	public static final String getDateTypeIdQuery = "select distinct date_type_id from yfs_order_date where order_header_key in "
			+ "(select order_header_key from yfs_order_header where order_no = ?)";

	public static final String getActualDateQuery = "select distinct actual_date from yfs_order_date where order_header_key in "
			+ "(select order_header_key from yfs_order_header where order_no = ?)";

	public static final String getSalesLineKey = "select trim(order_line_key) from yfs_order_line where order_header_key in (select order_header_key "
			+ "from yfs_order_header where order_no=?)";

	public static final String getTrackingNo = "select tracking_no from yfs_shipment_container where shipment_container_key in "
			+ "(select shipment_container_key from YFS_CONTAINER_DETAILS where order_line_key=?)";

	public static final String getNikeidSGno = "Select yh.order_no,trim(yl.order_line_key),yl.ordered_qty from yfs_order_header yh,yfs_order_line yl where yh.order_header_key = yl.order_header_key and yl.UOM ='EACH' and yh.order_header_key IN  (Select order_header_key from yfs_order_line where chained_from_order_header_key IN (SELECT ORDER_HEADER_KEY FROM YFS_ORDER_HEADER WHERE ORDER_NO = ?)and line_type = 'NIKEID') and yl.chained_from_order_line_key in"
			+ " (select order_line_key  from yfs_order_line where  order_header_key in (select order_header_key from yfs_order_header where order_no=?) and trim(bundle_parent_order_line_key)= ? and uom='EACH')";
	public static final String getInlineSGno = "select ohc.order_no from yfs_order_header oh inner join yfs_order_line ol  on (oh.order_header_key=ol.chained_from_order_header_key)"
			+ "inner join yfs_order_header ohc on (ohc.order_header_key = ol.order_header_key) where oh.order_no = ?";

	public static final String getNikeidWOno = "Select trim(work_order_no) from yfs_work_order where order_header_key in (Select order_header_key from yfs_order_header where order_no=?) and yfs_work_order.work_order_key in (select current_work_order_key  from yfs_order_line where  order_header_key in (select order_header_key from yfs_order_header where order_no=?) and trim(bundle_parent_order_line_key)=? and uom='UNIT')";
	public static final String getLineType = "Select * from  yfs_order_line where trim(order_line_key)=?";
	public static final String getNikeiDTrackingNo = "select tracking_no from yfs_shipment_container where shipment_key = (select shipment_key from yfs_shipment where order_no=?)";
	public static final String getLineKeyPrLnNo = "select * from yfs_order_line where prime_line_no=lineNo and order_header_key in (SELECT ORDER_HEADER_KEY FROM YFS_ORDER_HEADER WHERE ORDER_NO = ?)";
	public static final String getOrderLineKey = "select trim(order_line_key) from yfs_order_line where prime_line_no=lineNo and order_header_key in (SELECT ORDER_HEADER_KEY FROM YFS_ORDER_HEADER WHERE ORDER_NO = ?)";
	public static final String getComponentOrderLineKeyNikeId = "select trim(order_line_key) from yfs_order_line where bundle_parent_order_line_key=? and UOM='EACH'";

	public static final String getReleaseNo = "SELECT distinct(yr.release_no) FROM YFS_ORDER_RELEASE_STATUS ys, YFS_ORDER_RELEASE yr where ys.order_release_key=yr.order_release_key and trim(ys.order_line_key)=?";
	public static final String getShipAdviceNo = "select ship_advice_no from yfs_order_release where release_no = ? and order_header_key in (select order_header_key from yfs_order_header where order_no = ?)";

	public static final String getStatusLine = "select count(*) from YFS_ORDER_RELEASE_STATUS where status='statusCD' and trim(order_line_key)=?";
	public static final String getPaymentStatus = "select Trim(payment_status) from yfs_order_header where order_no=?";
	public static final String getCommerceID = "select distinct(EXTN_COMMERCE_ITEMID) from yfs_order_line where EXTN_COMMERCE_ITEMID <> ' ' and EXTN_COMMERCE_ITEMID IS NOT NULL  and order_header_key in (select order_header_key from yfs_order_header where order_no=?)";
	public static final String getPONolineKey = "select max(order_no) from yfs_order_header where  order_header_key in(select order_header_key from yfs_order_line where trim(chained_from_order_line_key)=?) order by createts desc";

	public static final String getlineKeyPONo = "select trim(order_line_key),order_header_key,ordered_qty from yfs_order_line where order_header_key in (SELECT ORDER_HEADER_KEY FROM YFS_ORDER_HEADER WHERE ORDER_NO = ?)";
	public static final String getReturnOrdernoQuerry = "Select order_no from yfs_order_header where order_header_key In (Select order_header_key from yfs_order_line where line_type = 'INLINE' AND derived_from_order_header_key in (select order_header_key from"
			+ " yfs_order_header where order_no=?))";
	public static final String returnConfirmationQuerry = "select note_text  from "
			+ " yfs_notes where table_key IN (select order_header_key from yfs_order_header where order_no=?) and contact_type = 'Email' and reason_code ='RC'";

	public static final String GET_INV_QTY = "select quantity from yfs_inventory_supply where inventory_item_key in (select inventory_item_key from yfs_inventory_item where item_id like ?) and shipnode_key like ?";

	public static final String getAttributesForOrderReturnQuerry = "select trim(order_line_key),ordered_qty from yfs_order_line where order_header_key in "
			+ "(select order_header_key from yfs_order_header where order_no=?) and prime_line_no<100 and ordered_qty>0";
	public static final String VarifyOrderIDInvoicedQuerry = "select payment_status from yfs_order_header where order_no=?";
	public static final String retriveTheItemsToBeReturnedQuerry = "Select distinct trim(yl.item_id) FROM yfs_order_line yl,yfs_order_header yh where yh.order_header_key = yl.order_header_key AND yl.line_type = 'GC'  AND yl.item_id IN  (${__eval(${ItemReturned})} ) AND yh.order_header_key in"
			+ " (select order_header_key from yfs_order_header where order_no=?)";
	public static final String retrieveOrderlineAttributesForTheGCItemToBeReturnedQuerry = "Select trim(order_line_key) from yfs_order_line where item_id IN  (${OutputID} ) AND order_header_key  IN (select order_header_key from yfs_order_header where order_no= ?) AND line_type ='GC'";
	public static final String verifyReturnOrderCreatedQuerry = "Select order_no from yfs_order_header where order_header_key In (Select order_header_key from yfs_order_line where Item_ID= '${OutputID}' AND derived_from_order_header_key in (select order_header_key from"
			+ " yfs_order_header where order_no='${Order_Id}'))";

	public static final String retfriveAttributesForReturnOrderQuerry = "select trim(order_line_key),trim(ordered_qty) from yfs_order_line where order_header_key in (select order_header_key from yfs_order_header where order_no='${ReturnOrderNumber_1}') ";
	public static final String orderLineAttributes = "Select trim(yl.order_line_key),Trim(yh.SHIP_TO_KEY),Trim(yh.BILL_TO_KEY),Trim(yl.Prime_Line_no),"
			+ "trim(yl.sub_line_no),trim(yl.line_type),Trim(yl.carrier_service_code), Trim(yl.upc_code),Trim(yl.ordered_qty),Trim(yh.order_header_key) FROM yfs_order_line yl,yfs_order_header yh"
			+ " where yh.order_header_key = yl.order_header_key  AND yh.order_header_key in"
			+ "(select order_header_key from yfs_order_header where order_no=?)";

	public static final String nikeIDorderLineAttributes = "Select trim(yl.order_line_key),Trim(yh.SHIP_TO_KEY),Trim(yh.BILL_TO_KEY),Trim(yl.Prime_Line_no),"
			+ "trim(yl.sub_line_no),trim(yl.line_type),Trim(yl.carrier_service_code) FROM yfs_order_line yl,yfs_order_header yh"
			+ " where yh.order_header_key = yl.order_header_key AND yl.line_type = 'NIKEID' AND yh.order_header_key in"
			+ "(select order_header_key from yfs_order_header where order_no=?)";

	public static final String getShipmentKey = "select * from yfs_shipment where order_no=?";
	public static final String getNIKEIDReturnOrdernoQuerry = "Select order_no from yfs_order_header where order_header_key In "
			+ "(Select order_header_key from yfs_order_line where line_type = 'NIKEID' AND derived_from_order_header_key in (select order_header_key from"
			+ " yfs_order_header where order_no=?))";

	public static final String deleteInventory = "delete from yfs_inventory_node_control where trim(item_id)=?";
	public static final String getReturnOrderNo = "select order_no,EXTN_TRACKING_NO from yfs_order_header where order_header_key in"
			+ "(select order_header_key from yfs_order_line where trim(DERIVED_FROM_ORDER_LINE_KEY) in"
			+ "(select trim(order_line_key) from yfs_order_line where prime_line_no=${PrimeLineNo} and order_header_key in "
			+ "(select order_header_key from yfs_order_header where order_no=?)))";

	public static final String getShipNode = "select ship_node from yfs_order_line_schedule where trim(order_line_key)=? and ship_node is not null order by createts desc";
	public static final String getSalesLineKey1 = "select trim(order_line_key) from yfs_order_line where order_header_key in (select order_header_key "
			+ "from yfs_order_header where order_no=?)";
	public static final String getTrackingNoSAP = "select tracking_no from yfs_shipment_container where shipment_container_key in "
			+ "(select shipment_container_key from YFS_CONTAINER_DETAILS where order_line_key in"
			+ "(select order_line_key from yfs_order_line where chained_from_order_line_key in "
			+ "(select order_line_key from YFS_order_line where PRIME_LINE_NO=${primeLineNo} and order_header_key in "
			+ "(select order_header_key from yfs_order_header where order_no =?))))";
	public static final String getLineTypePrNo = "Select line_type,trim(order_line_key) from yfs_order_line where prime_line_no=${PrimeLineNo} and order_header_key in (SELECT ORDER_HEADER_KEY FROM YFS_ORDER_HEADER WHERE ORDER_NO = ?)";
	public static final String getInvoiceDetails = "Select * from yfs_order_invoice where order_no=?";
	public static final String GET_DIRTY_NODE = "Select * from yfs_inventory_node_control where item_id=? and node_key =?";
	public static final String getAlertLevel = "Select alert_level from yfs_inventory_alerts where inventory_item_key in (select inventory_item_key from yfs_inventory_item where Trim(item_id) = ?)";
	public static final String getInvoiceAmount = "select amount_collected from yfs_order_invoice where order_invoice_key in (select order_invoice_key from  YFS_ORDER_INVOICE_DETAIL where order_line_key=?)";
	public static final String getVatReceiptKey = "select extn_vat_receipt_KEY,extn_vat_receipt_No,extn_vat_status,extn_vat_receipt_amount from extn_vat_receipt where extn_chained_order_no=?";
	public static final String GET_OLKEY_SAP = "select trim(order_line_key) from yfs_order_line where trim(chained_from_order_line_key)=? and ordered_qty>0 order by createts desc";
	public static final String TEST_SUITE_XLS_PATH = System.getProperty("user.dir") + "/resources/testCases/"
			+ "Jmeter_Automation_Suite.xls";
	public static final String TEST_SUITE_XML_PATH = System.getProperty("user.dir") + "/resources/xmlFiles/";
	public static final String GET_SHIPGROUP_NO = "select reference from YFS_HEADER_CHARGES where header_key in( select order_header_key from yfs_order_header where order_no=?)";
	public static final String GET_SHIPMENT_INVOICE_COUNT = "select count(*) from yfs_order_invoice where invoice_type='SHIPMENT' and ORDER_INVOICE_KEY in (select ORDER_INVOICE_KEY from YFS_ORDER_INVOICE_DETAIL  where order_line_key=?)";
	public static final String GET_SHIPMENT_KEY_ORDERLINE = "select trim(shipment_key) from YFS_CONTAINER_DETAILS where trim(order_line_key)=?";
	public static final String GET_DISCOUNT_TAX = "select CHARGE_CATEGORY,TAX_NAME,TAX,TAX_PERCENTAGE from yfs_tax_breakup where charge_category='Discount' and header_key=?  and line_key=?";
	public static final String GET_ASYNC_PROCESS_QUEUE_DETAILS = "select * from extn_async_process_q where data_key = ? and EXECUTESERVICENAME = ? and  MESSAGE_XML like ?";
	public static final String GET_ASYNC_PROCESS_QUEUE_ARCHIVE_DETAILS = "select * from extn_async_process_q_archive where data_key = ? and EXECUTESERVICENAME = ? and MESSAGE_XML like ?";
	public static final String GET_ASYNC_PROCESS_QUEUE_DETAILS_With_Process_Name = "select * from extn_async_process_q where data_key = ? and process_name = ? and  MESSAGE_XML like ?";
	public static final String GET_ASYNC_PROCESS_QUEUE_ARCHIVE_DETAILS_With_Process_Name = "select * from extn_async_process_q_archive where data_key =  ? and process_name = ? and MESSAGE_XML like ?";
	public static final String GET_COMMS_EMAIL_DETAILS = "select * from comm_archive where ref_1=? and SUBCATEGORY=?";
	public static final String GET_PAC_MESSAGE_DETAILS = "select * from nike_audit_message where SEARCH_REFERENCE like ? and DESERIALIZED_MESSAGE like ?";
	public static final String GET_MPOS_TRANS_NO = "select extn_trans_no from extn_store where extn_order_header_key in (select order_header_key from yfs_order_header where order_no =?)";
	public static final String GET_NIKEID_COMPONENT_LINE_KEY = "select trim(order_line_key) from yfs_order_line where UOM='EACH' and trim(BUNDLE_PARENT_ORDER_LINE_KEY)=?";
	public static final String GET_PROCESSING_KEY_FROM_ASINC_PROCESS_Q = "SELECT PROCESSING_Q_KEY FROM EXTN_ASYNC_PROCESS_Q WHERE EXECUTESERVICENAME=?";
	public static final String GET_YFS_TASK_Q_DATA = "select task_q_key,transaction_key,data_key from yfs_task_q where data_key  in (select order_header_key from yfs_order_header where order_no=?) and transaction_key in (select transaction_key from yfs_transaction where trim(tranid)='GENERATE_WO.7001.ex')";
	public static final String GET_INVENTORYRESERVATION_COUNT = "select count(*) from yfs_inventory_reservation where trim(reservation_id) = ?";
	public static final String CHECK_IF_LINE_INSPECTED = "select count(*) from yfs_order_release_status  where order_line_key in (select trim(order_line_key) from yfs_order_line where prime_line_no = '{PrimeLineNo}' and order_header_key in (select order_header_key from yfs_order_header where order_no= ? )) and status = '3700.05'";
	public static final String RESERVATION_DURATION_IN_SECC = "600";
	public static final String GET_SICD_PUBLISH_STATUS = "select  EXTN_PUBLISH_STATUS from extn_invoice_publish where EXTN_INVOICE_STATE = 'SICD' and EXTN_INVOICE_NO in (select invoice_no from yfs_order_invoice where order_no = ?)";
	public static final String GET_AMOUNTCOLLECTED_FOR_RETURN_FROM_PRIMELINENO = "select amount_collected from yfs_order_invoice where order_no in "
			+ "(select order_no from yfs_order_header where order_header_key in (select order_header_key from yfs_order_line where DERIVED_FROM_ORDER_LINE_KEY in "
			+ "(select trim(order_line_key) from yfs_order_line where prime_line_no='{PrimeLineNo}' and order_header_key in "
			+ "(select order_header_key from yfs_order_header where order_no= ? ))))";
	public static final String GET_AMOUNTCOLLECTED_FOR_SHIPMENT_FROM_PRIMELINENO = "select amount_collected from yfs_order_invoice where order_invoice_key in "
			+ "(select order_invoice_key from yfs_order_invoice_detail where order_line_key in "
			+ "(select trim(order_line_key) from yfs_order_line where prime_line_no = '{PrimeLineNo}'and order_header_key = "
			+ "(select order_header_key from yfs_order_header where order_no= ? )))";
	public static final String CHECK_IF_LINE_RETURNED = "select count(*) from yfs_order_release_status  where order_line_key in "
			+ "(select trim(order_line_key) from yfs_order_line where prime_line_no = '{PrimeLineNo}' and order_header_key in "
			+ "(select order_header_key from yfs_order_header where order_no= ? )) and status = '3700.01'";
	public static final String GET_RETURN_INVOICE_DETAILS_PRLNO = "select * from yfs_order_invoice where order_no in "
			+ "(select order_no from yfs_order_header where order_header_key in (select order_header_key from yfs_order_line where DERIVED_FROM_ORDER_LINE_KEY in "
			+ "(select trim(order_line_key) from yfs_order_line where prime_line_no='{PrimeLineNo}' and order_header_key in "
			+ "(select order_header_key from yfs_order_header where order_no= ? ))))";
	public static final String GET_CANCELLATION_MAIL_Q = "select message_xml from extn_async_process_q where message_xml like '%SubCategory=\"Cancellation\"%{order_line_key}%'";
	public static final String GET_CANCELLATION_MAIL_Q_ARCHIVE = "select message_xml from extn_async_process_q_archive where message_xml like '%SubCategory=\"Cancellation\"%{order_line_key}%'";
	public static final String GET_SHIPMENT_CONFIRMATION_MAIL_Q = "select message_xml from extn_async_process_q where message_xml like '%SubCategory=\"ShipConfirmation\"%{order_line_key}%'";
	public static final String GET_SHIPMENT_CONFIRMATION_MAIL_ARCHIVE = "select message_xml from extn_async_process_q_archive where message_xml like '%SubCategory=\"ShipConfirmation\"%{order_line_key}%'";
	public static final String GET_ORDER_CONFIRMATIONINVOICE_XML = "select count(*) from extn_async_process_q where EXECUTESERVICENAME = 'Publish_FinanceQueue_OUT_svc' and message_xml like '%Action=\"OrderConfirm\"%{Order_No}%'";
	public static final String GET_ORDER_CONFIRMATIONINVOICE_DATA = "select PROCESSING_Q_KEY,EXECUTESERVICENAME from extn_async_process_q where PROCESS_NAME = 'STORE_RECEIVE_ORDER_INVOICE' and message_xml like '%{Order_No}%'";
	public static final String GET_BOOK_AMOUNT = "select BOOK_AMOUNT from yfs_charge_transaction where CHARGE_TYPE = 'CREATE_ORDER' and  order_header_key in (select order_header_key from yfs_order_header where order_no = ?)";
	public static final String GET_RETURN_DEBIT_AMOUNT = "select DEBIT_AMOUNT from yfs_charge_transaction where CHARGE_TYPE = 'RETURN' and order_header_key in(select order_header_key from yfs_order_header where order_no = ? ) ";
	public static final String GET_INVOICEXML_FROM_PUBLISHTABLE = "select * from extn_invoice_publish where EXTN_INVOICE_STATE=? and  EXTN_INVOICE_NO=?";
	public static final String checkInvoiceDetails = "Select distinct INVOICE_NO from yfs_order_invoice where order_no=?";
	public static final String getMessageXMLForPOQueue = "select MESSAGE_XML from extn_async_process_q where data_key = ? and process_name='Publish_POQueue_OUT_svc' order by createts";
	public static final String GET_GENERATED_INVOICE_NOS = "Select distinct INVOICE_NO from yfs_order_invoice where order_no=? and Invoice_Type = ?";
	public static final String getMessageXML1 = "select MESSAGE_XML from extn_async_process_q where data_key = ? and process_name='Publish_FinanceQueue_OUT_svc' order by createts";
	public static final String GET_CREDIT_AMOUNT = "select CREDIT_AMOUNT from yfs_charge_transaction where order_header_key in (select order_header_key from yfs_order_header where CHARGE_TYPE = 'CHARGE' and STATUS = 'CHECKED' and order_no = ?)";
	public static final String GET_orderHeaderKeyQuery = "select order_header_key from yfs_order_header where order_no= ?";
	public static final String getInvoiceNumberByOrderLineKey = "select invoice_no from yfs_order_invoice where invoice_type in (InvoiceTypes) and  order_invoice_key in (select order_invoice_key from  yfs_order_invoice_detail where order_line_key=?)";
	public static final String GET_RETURN_HEADER_DETAILS_PRLNNO = "select * from yfs_order_header where order_header_key in (select order_header_key from yfs_order_line where DERIVED_FROM_ORDER_HEADER_KEY in (select order_header_key from  yfs_order_line where prime_line_no=? and   order_header_key in (select order_header_key from yfs_order_header where order_no=?)));";
	public static final String GET_TRACKINGNO_PRLNO = "select tracking_no from yfs_shipment_container where shipment_container_key in (select shipment_container_key from YFS_CONTAINER_DETAILS where order_line_key in (select order_line_key from yfs_order_line where PRIME_LINE_NO=${primeLineNo} and order_header_key in (select order_header_key from yfs_order_header where order_no =?)))";
	public static final String GET_FSOORDER_NO_QUERRY = "select  Order_No  from yfs_order_header where document_type='0001' and  order_header_key in (select order_header_key from yfs_order_line where derived_from_order_header_key in (select order_header_key from yfs_order_header where order_no=?))";
	public static final String GET_ORDER_DETAILS = "select * from yfs_order_header where order_no = ?";
	public static final String VERIFY_ACCERTIFY_CALL_Q = "select * from extn_async_process_q where data_key like ? and PROCESS_NAME like ?  and  MESSAGE_XML like ?";
	public static final String VERIFY_ACCERTIFY_CALL_ARCHIVE = "select * from extn_async_process_q_archive where data_key like ? and PROCESS_NAME like ?  and  MESSAGE_XML like ?";
	public static final String GET_TAX_DETAILS = "select * from yfs_tax_breakup where CHARGE_NAME = '{Charge_Name}' and line_key in (select  order_line_key from yfs_order_line where prime_line_no = ? and order_header_key in (select order_header_key from yfs_order_line where derived_from_order_header_key in (select order_header_key from yfs_order_header where order_no= ? )))";
	public static final String GET_RETURN_LINES = "select * from yfs_order_line where DERIVED_FROM_ORDER_LINE_KEY in( select order_line_key from yfs_order_line where prime_line_no in(${PrimeLines}) and   order_header_key in (select order_header_key from yfs_order_header where order_no=?))";
	public static final String GET_FSOORDER_HEADER_KEY_FROM_ORDER_HEADER_KEY = "select order_header_key from yfs_order_header where order_no in(select  Order_No  from yfs_order_header where order_header_key in(select order_header_key from yfs_order_line where derived_from_order_header_key = ?))";
	public static final String GET_SAP_DELIVERY_DETAILS = "select * from EXTN_SAP_DELIVERY_DETAILS where trim(extn_order_line_key)=?";
	public static final String GET_SAP_ORDER_NO = "select extn_sap_order_no from yfs_order_header where order_no=?";
	public static final String getAsnRequestFromQ = "select MESSAGE_XML from extn_async_process_q where executeservicename ='Post_ASN_Request_svc' and data_key=? and MESSAGE_XML like ? order by createts desc";

	public static final String getAsnRequestFromArchive = "select MESSAGE_XML from extn_async_process_q_archive where executeservicename ='Post_ASN_Request_svc' and data_key=? and MESSAGE_XML like ? order by createts desc";
	public static final String getCountFromYfsShipment = "select count(*) from yfs_shipment where order_no=?";
	public static final String VERIFY_YFS_NOTES_FOR_RETURNREFUND_GS = "select * from yfs_notes where table_key in (select order_header_key from yfs_order_header where note_text like 'Customer Refunded%' and order_no = ?)";
	public static final String VERIFY_YFS_REFERENCE_TABLE_FOR_RETURNREFUND_GS = "select * from yfs_reference_table where table_key in (select order_header_key from yfs_order_header where field_name3 like 'RefundAmount' and order_no = ?)";
	public static final String VERIFY_YFS_PAYMENT_FOR_RETURNREFUND_GS = "select * from yfs_payment where order_header_key in (select order_header_key from yfs_order_header where order_no = ?)";
	public static final String VERIFY_YFS_CHARGE_TRANSACTION_FOR_RETURNREFUND_GS = "select * from yfs_charge_transaction where order_header_key in (select order_header_key from yfs_order_header where order_no = ?)";
	public static final String GET_RETURN_FINANCIAL_XML_FROM_Q = "select * from extn_async_process_q where executeservicename ='Publish_Financials_SAP_svc' and message_xml like ? order by createts desc";
	public static final String GET_RETURN_FINANCIAL_XML_FROM_ARCHIVE = "select * from extn_async_process_q_archive where executeservicename ='Publish_Financials_SAP_svc' and message_xml like ? order by createts desc";
	public static final String GET_COUNT_OF_ENTRY_IN_Q = "select count(*) from extn_async_process_q where data_key=? and executeservicename =? and message_xml like ?";
	public static final String GET_COUNT_OF_ENTRY_IN_ARCHIVE = "select count(*) from extn_async_process_archive where data_key=? and executeservicename =? and message_xml like ?";
	public static final String GET_FACTORY_COUNTRY = "select country from yfs_person_info where person_info_key in(select ship_node_address_key from yfs_ship_node where ship_node in(select shipnode_key from yfs_inventory_supply where inventory_item_key in (select inventory_item_key from yfs_inventory_item where trim(item_id)=? and trim(organization_code)=?)))";
	public static final String GET_SHIPMENT_MESSAGE_XML_FROM_Q = "select message_xml from extn_async_process_q where executeservicename= 'IDM_ShipConfirm_Post_ASN_svc' and message_xml like ?";
	public static final String GET_SHIPMENT_MESSAGE_XML_FROM_ARCHIVE = "select MESSAGE_XML from extn_async_process_q_archive where executeservicename= 'IDM_ShipConfirm_Post_ASN_svc' and message_xml like ?";
	public static final String CHECK_DIGITAL_INVOICED_PUBLISHED = "select extn_invoice_id,extn_digital_invoice_sent,invoice_no from yfs_order_invoice where invoice_no = ?";
	public static final String GET_BILL_TO_ADDRESS = "select * from yfs_person_info where PERSON_INFO_KEY in(select bill_to_key from yfs_order_header where order_no=?)";
	public static final String GET_INV_RESERVATION = "select * from yfs_inventory_reservation where reservation_id in(${reservationIds}) and trim(owner_key)=?";
	public static final String GET_INV_SUPPLY_QTY = "select trim(shipnode_key), trim(quantity) from yfs_inventory_supply where (LENGTH(TRIM(shipNode_KEY))>3 or TRIM(shipNode_KEY)='BRD') and supply_type='ONHAND' and  inventory_item_key in(select inventory_item_key from yfs_inventory_item where trim(item_id)=?)";
	public static final String GET_INV_DEMAND_SHIPNODE = "select sum(quantity) from YFS_INVENTORY_DEMAND where trim(shipnode_key)=? and inventory_item_key in(select inventory_item_key from yfs_inventory_item where trim(item_id)=?)";
	public static final String GET_CREDIT_CARD_TRANSACTION_DATA = "select * from YFS_CREDIT_CARD_TRANSACTION where TRAN_TYPE='CHARGE' and  CHARGE_TRANSACTION_KEY in (select CHARGE_TRANSACTION_KEY from yfs_charge_transaction where order_header_key in (select order_header_key from yfs_order_header where order_no=?))";
	public static final String GET_PROCESSING_KEY_FROM_ASYNC_PROCESS_Q = "SELECT PROCESSING_Q_KEY FROM EXTN_ASYNC_PROCESS_Q WHERE EXECUTESERVICENAME=? and DATA_KEY=?";
	public static final String GET_ONHAND_QTY_FROM_INV_AUDIT = "select on_hand_qty from (select item, createts, on_hand_qty, row_number() over(partition by item order by createts desc) as rn  from yfs_inventory_audit where trim(ITEM)=? and trim(ship_node)=?) t where t.rn = 1";
	public static final String GET_STORE_INV_INOUT_STOCK = "select * from extn_ino_inv where trim(extn_ship_node)=? and TRIM(MODIFYPROGID)='${ProgId}' and extn_INVENTORY_ITEM_KEY in (select INVENTORY_ITEM_KEY from yfs_inventory_item where trim(item_id)=?)";
	public static final String GET_ORDER_LINE_VALUES_PLNO_LESSTHAN_100 = "select * from yfs_order_line where prime_line_no<100 and order_header_key in(select order_header_key from yfs_order_header where order_no = ?)";
	public static final String GET_ORDER_RELEASE_KEY_PLNO = "select max(order_release_key) from yfs_order_release_status where trim(order_line_key) in(select trim(order_line_key) from yfs_order_line where prime_line_no=? and order_header_key in (select order_header_key from yfs_order_header where order_no=?))";
	public static final String GET_TASK_Q_ENTRIES = "select * from yfs_task_q where data_type=? and trim(data_key)=?";
	public static final String GET_GW_COUNT_FROM_RET_ORDER = "select count(*) from yfs_order_line where item_id=? and order_header_key in(select order_header_key from yfs_order_header where order_no=?)";
	public static final String GET_SUM_TAX_OTHERCHARGES = "select sum(other_charges), sum(tax) from yfs_order_invoice_detail where order_line_key in (select order_line_key from yfs_order_line where prime_line_no in ({PrimeLineNo}) and order_header_key in (select order_header_key from yfs_order_header where order_no = ?))";
	public static final String GET_SUM_TAX = "select sum(tax) from yfs_tax_breakup where line_key in (select order_line_key from yfs_order_line where prime_line_no in ({PrimeLineNo}) and order_header_key in (select order_header_key from yfs_order_header where order_no = ?))";
	public static final String GET_ITEM_DETAILS = "select * from  yfs_item where trim(item_id)=?";
	public static final String UPDATE_REQ_CANCEL_DT = "update yfs_order_line set REQ_CANCEL_DATE=sysdate-1 where prime_line_no in (${primeLines}) and order_header_key in(select order_header_key from yfs_order_header where order_no=?)";
	public static final String UPDATE_AVAILABLEDATE_BOMONITOR_ASYNCQ = "update extn_async_process_q set  available_date=sysdate where EXECUTESERVICENAME='BOMonitor_ExecuteAsync_svc' and data_key in (select order_line_key from yfs_order_line where prime_line_no in (primeLines) and order_header_key in(select order_header_key from yfs_order_header where order_no=?))";
	public static final String GET_BOMONITOR_ENTRY_FROM_Q = "select * from extn_async_process_q where EXECUTESERVICENAME='BOMonitor_ExecuteAsync_svc' and data_key in (select order_line_key from yfs_order_line where prime_line_no in (primeLineNos) and order_header_key in(select order_header_key from yfs_order_header where order_no=?))";
	public static final String GET_SAP_LINEKEY_FROM_PRIMELINENO = "select * from  yfs_order_line where trim(chained_from_order_line_key) in (select trim(order_line_key) from yfs_order_line where PRIME_LINE_NO in (primeLines) and order_header_key in (SELECT ORDER_HEADER_KEY FROM YFS_ORDER_HEADER WHERE ORDER_NO = ?))";
	public static final String GET_INLINE_PONO_FROM_PRIMELINEKEY = "select trim(order_no) from yfs_order_header where order_header_key in (select order_header_key from yfs_order_line where trim(chained_from_order_line_key) in(select trim(order_line_key) from yfs_order_line where prime_line_no in (primeLines) and order_header_key in (SELECT ORDER_HEADER_KEY FROM YFS_ORDER_HEADER WHERE ORDER_NO = ?)))";

	public static final String GET_PAYMENT_REQUEST_DETAILS_CREDIT = "select * from payment_requests where request_type = 'CREDIT' AND order_id = ?";
	public static final String GET_UPSCCODES_OF_PONUMBER = "select distinct alias_value from yfs_item_alias where item_key in "
			+ "(select item_key from yfs_item where item_id in "
			+ "(select distinct item_id from yfs_order_line where order_header_key in "
			+ "(select order_header_key from yfs_order_header where order_no=?))and item_key not in(select item_key from yfs_item where trim(item_id)=?))";
	public static final String GET_UPSCCODES_OF_ITEMID = "select distinct alias_value from yfs_item_alias where item_key in"
			+ "(select item_key from yfs_item where item_id=?) ";

	public static final String GET_PAYMENT_REQUEST_DETAILS_DEBIT = "select * from payment_requests where request_type = 'DEBIT' AND order_id = ?";
	public static final String GET_ASYNC_PROCESS_QUEUE_DETAILS_PROCESS_NAME = "select * from extn_async_process_q where data_key like ? and PROCESS_NAME like ? and  MESSAGE_XML like ?";
	public static final String GET_PO_NOS_FROM_SO_NO = "select * from yfs_order_header where order_header_key in (select order_header_key from yfs_order_line where CHAINED_FROM_ORDER_HEADER_KEY in (select order_header_key from yfs_order_header where order_No=?))";
	public static final String GET_TRACKINGNO_SHIPMENTKEY_PRLNO = "select tracking_no,shipment_key from yfs_shipment_container where shipment_container_key in (select shipment_container_key from YFS_CONTAINER_DETAILS where order_line_key in (select order_line_key from yfs_order_line where PRIME_LINE_NO=${primeLineNo} and order_header_key in (select order_header_key from yfs_order_header where order_no =?)))";
	public static final String GET_TRACKINGNO_SHIPMENTKEY = "select tracking_no from yfs_shipment where shipment_key = ?";
	public static final String GET_FROORDER_NO_QUERRY = "select  Order_No  from yfs_order_header where document_type='0003' and  order_header_key in (select order_header_key from yfs_order_line where derived_from_order_header_key in (select order_header_key from yfs_order_header where order_no=?))";
	public static final String GET_YFS_INBOX_DATA = "select * from yfs_inbox where exception_type = ? and  order_no = ?";
	public static final String getSuspendAnyMoreCharges = "select suspend_any_more_charges from yfs_payment where order_header_key in (select order_header_key from yfs_order_header where order_no=?)";
	public static final String GET_SHIPMENT_DETAILS = "select * from yfs_shipment where shipment_key in (select shipment_key from YFS_CONTAINER_DETAILS where ORDER_LINE_KEY=?)";
	public static final String GET_NOTES_TABLE_ENTRY = "select * from yfs_notes where table_key = ? and reason_code = ?";
	public static final String getOrderlinekeyfornikeid = "select trim(ORDER_LINE_KEY) from YFS_ORDER_LINE where ORDER_HEADER_KEY =(select ORDER_HEADER_KEY from YFS_ORDER_HEADER where ORDER_NO=? and UOM='EACH')";
	public static final String GET_ASYNC_PROCESS_QUEUE_DETAILS_salefiles = "select * from extn_async_process_q where EXECUTESERVICENAME like ? and  MESSAGE_XML like ?";
	public static final String GET_ASYNC_PROCESS_QUEUE_Archive_DETAILS_salefiles = "select * from extn_async_process_q_archive where EXECUTESERVICENAME like ? and  MESSAGE_XML like ?";
	public static final String getInvoiceNO = "Select invoice_no from yfs_order_invoice where order_no=?";
	public static final String getPersoninfoKey = "select PERSON_INFO_KEY from Yfs_person_info where DAY_PHONE='0' and first_name='Auto1' order by createts desc";
	public static final String getBillToKey = "select ORDER_NO from Yfs_order_header where BILL_TO_KEY in (0)  order by CREATETS desc";
	public static final String GET_ASYNC_PROCESS_QUEUE_ARCHIVE_PROCESSNAME = "select * from extn_async_process_q_archive where data_key = ? and PROCESS_NAME = ? and MESSAGE_XML like ?";
	public static final String GET_SHIP_NODE = "select * from yfs_order_line_schedule where  order_line_key= ?";
	public static final String GET_AVAILABLEDATE_SYSDATE_Q = "select available_date,sysdate from extn_async_process_q where data_key = ? AND process_name like '%BO_MONITOR%' and enterprise_key=?";
	public static final String GET_AVAILABLEDATE_SYSDATE_ARCHIVE = "select available_date,sysdate from extn_async_process_q_archive where data_key = ? AND process_name like '%BO_MONITOR%' and enterprise_key=?";
	public static final String ALTER_SESSION = "alter session set nls_date_format='YYYY MM DD HH24:MI:SS'";
	public static final String GET_SHIPPINGCHARGEPERLINE = "select * from yfs_line_charges where charge_category = 'Shipping' and header_key in (select order_header_key from yfs_order_header where document_type = '0003' and order_no = ?)";
	public static final String GET_SHIPPINGTAX = "select * from yfs_tax_breakup where line_key in (select order_line_key from yfs_order_line where charge_category = 'Shipping' and  prime_line_no in ({PrimeLineNo}) and order_header_key in (select header_key from yfs_line_charges where charge_category = 'Shipping' and header_key in (select order_header_key from yfs_order_header where document_type = '0003' and order_no = ?))) and header_key in (select order_header_key from yfs_order_header where order_no = ?)";
	public static final String DELETEDELIVERYNOENTRYFROMEXTN_SAP_DELIVERY_DETAILS = "delete from extn_sap_delivery_details where extn_order_line_key in (select order_line_key from yfs_order_line where order_header_key in (select order_header_key from yfs_order_header where order_no = ?))";
	public static final String GET_ORDERDATE_DETAILS = "select  * from yfs_order_date where order_header_key in (select order_header_key from yfs_order_header where order_no = ?)and DATE_TYPE_ID = ?";
	public static final String get_UPCNumber = "select ALIAS_VALUE from yfs_item_alias where trim (item_key) in (select trim (item_key) from yfs_item where item_id=?)";
	public static final String getExpecteddate = "select TO_char(Expected_Date,'YYYY-DD-MM HH24:MI:SS') from DOM.YFS_ORDER_DATE where DATE_TYPE_ID='datetype' and ORDER_HEADER_KEY in(select order_header_key from yfs_order_header where order_no= ?)";
	public static final String GET_PAC_MESSAGE_3_PARAMETERS = "select DESERIALIZED_MESSAGE from nike_audit_message where SEARCH_REFERENCE = ? and classification = ? and  DESERIALIZED_MESSAGE like ?";
	public static final String GET_Tax_reference3value = "select REFERENCE3 from YFS_TAX_BREAKUP where TAX_NAME ='SALESTAX' and HEADER_KEY= ?";
	public static final String getShipmentcarriercode = "select CARRIER_SERVICE_CODE from YFS_SHIPMENT where SHIPMENT_KEY= (select shipment_key from yfs_shipment_container where TRACKING_NO='replace')";
	public static final String getextnCancelrequestqty="select extn_cancel_request_qty from yfs_order_line where order_line_key = ?";
	public static final String geteachQTYStatus="select * from yfs_order_release_status where status_quantity= ? and order_line_key = ?";
	public static final String getreturnLineKeyPrLnNo="select * from yfs_order_line where prime_line_no=lineNo and order_header_key in (SELECT ORDER_HEADER_KEY FROM YFS_ORDER_HEADER WHERE ORDER_NO = ? and yfs_order_line.ORDERED_QTY = lineQTY) order by createts desc";
	public static final String getBOLNo ="select * from yfs_shipment where ORDER_HEADER_KEY in (select ORDER_HEADER_KEY from YFS_ORDER_HEADER where ORDER_NO in (select ohc.order_no from yfs_order_header oh inner join yfs_order_line ol  on (oh.order_header_key=ol.chained_from_order_header_key) inner join yfs_order_header ohc on (ohc.order_header_key = ol.order_header_key) where oh.order_no = ?))";
	public static final String GET_PromisedApptDate = "select TO_char(DATE,'YYYY-DD-MM HH24:MI:SS') from DOM.YFS_ORDER_LINE where ORDER_HEADER_KEY=?";
	public static final String GET_CONTAINER_DETAILS_KEY = "select container_details_key from dom.yfs_container_details where order_line_key =?";
	public static final String GET_SHIPMENT_CONTAINER_KEY = "select shipment_container_key from dom.yfs_shipment_container where tracking_no = ?";
	public static final String GET_FULFILLMENT_TYPE = "select FULFILLMENT_TYPE from DOM.YFS_ORDER_LINE where PRIME_LINE_NO=? and ORDER_HEADER_KEY in(select ORDER_HEADER_KEY from DOM.YFS_ORDER_HEADER where ORDER_NO=?)";
	public static final String GET_ALLOCATION_RULE_ID = "select ALLOCATION_RULE_ID from yfs_order_header where ORDER_NO=?";
	public static final String check_entry_yfs_payment = "select * from yfs_payment where order_header_key in (select order_header_key from yfs_order_header where order_no = ?)";
	public static final String check_entry_yfs_charge_transaction = "select * from yfs_charge_transaction where order_header_key in (select order_header_key from yfs_order_header where order_no = ?)";
	public static final String check_entry_extn_payment_details = "select * from extn_payment_details where extn_order_header_key in (select order_header_key from yfs_order_header where order_no = ?)";
	public static final String statusPAYMENTQuery ="select trim(status) from yfs_order_hold_type where order_header_key in (select order_header_key from yfs_order_header where order_no= ?)AND HOLD_TYPE = 'PAYMENT'";

}
