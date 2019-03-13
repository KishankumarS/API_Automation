package com.doms.automation.dbvalidations;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.xml.bind.JAXBException;

import org.apache.http.HttpResponse;
import org.apache.log4j.spi.TriggeringEventEvaluator;

import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.WarningQueryVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.jaxbclasses.agenttrigger.AgentTrigger;
import com.doms.automation.marshaller.impl.AgentTriggerMarshaller;
import com.doms.automation.service.PostRequest;
import com.doms.automation.utils.DbValidationConstants;
import com.doms.automation.utils.NikeDOMSConstants;

public class VerifyInvoicePublishStatus {

	public ReqResVO verifyInvoicePublish(String primeLineNo, String statusCode,
			String orderNo, NikeDOMSConnectionDAO nikedomsconnectiondao,
			String invoiceType, String environment, EnvConfigVO envConfigVO)
			throws JAXBException, InterruptedException {
		ReqResVO reqResVO = new ReqResVO();
		String orderLineKey = "";
		String query = DbValidationConstants.VERIFY_INVOICE_PUBLISH;

		try {
			Thread.sleep(10000);
			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();

			orderLineKey = getOrderLineKey(primeLineNo, orderNo,
					nikedomsconnectiondao);

			if (invoiceType.equalsIgnoreCase("Sales")) {
				query = query.replace("InvoiceTypes", "'SHIPMENT','ORDER'");
				nikedomsconnectiondao.getDBValuesPAC(statusCode, orderLineKey,
						query, NikeDOMSConstants.DOMS);

				crs = nikedomsconnectiondao.getRowSet();

			}
			if (invoiceType.equalsIgnoreCase("PO")) {
				query = query.replace("InvoiceTypes", "'PO'");
				nikedomsconnectiondao.getDBValuesPAC(statusCode, orderLineKey,
						query, NikeDOMSConstants.DOMS);

				crs = nikedomsconnectiondao.getRowSet();

			}
			if (invoiceType.equalsIgnoreCase("CreditMemo")) {
				query = query.replace("InvoiceTypes", "'CREDIT_MEMO'");
				nikedomsconnectiondao.getDBValuesPAC(statusCode, orderLineKey,
						query, NikeDOMSConstants.DOMS);

				crs = nikedomsconnectiondao.getRowSet();

			}
			if (invoiceType.equalsIgnoreCase("Return")) {
				String returnOrderLineKey=getReturnOrderLineKey(orderLineKey,nikedomsconnectiondao);
				query = query.replace("InvoiceTypes", "'RETURN'");
				nikedomsconnectiondao.getDBValuesPAC(statusCode,
						returnOrderLineKey, query, NikeDOMSConstants.DOMS);

				crs = nikedomsconnectiondao.getRowSet();

			}
			if (invoiceType.equalsIgnoreCase("Cancel")) {
				query = query.replace("InvoiceTypes", "'INFO'");
				nikedomsconnectiondao.getDBValuesPAC(statusCode, orderLineKey,
						query, NikeDOMSConstants.DOMS);

				crs = nikedomsconnectiondao.getRowSet();

			}

			int size = crs.size();
			reqResVO.setReqXML("Verifying invoice published properly in extn_invoice_state");
			if (size > 0) {

				reqResVO.setResXML("Success");
				PostRequest postRqst = new PostRequest();
				AgentTrigger agentTrigger = new AgentTrigger();
				agentTrigger.setCriteriaId("PUBLISH_SEND_INVOICE");
				AgentTriggerMarshaller agentTriggerMarshaller = new AgentTriggerMarshaller(
						agentTrigger);
				String triggerXml = agentTriggerMarshaller
						.convertJaxbObjectToXML();
				HttpResponse response = postRqst.post(environment, triggerXml,
						"triggerAgent", "N", envConfigVO);
				Thread.sleep(30000);
			} else {
				ArrayList<String> inputParameters = new ArrayList<String>();
				inputParameters.add(statusCode);
				inputParameters.add(orderLineKey);
				WarningQueryVO warningQueryVO = new WarningQueryVO();
				warningQueryVO.setDataBase(NikeDOMSConstants.DOMS);
				warningQueryVO.setInputParameters(inputParameters);
				warningQueryVO.setQuery(query);
				ArrayList<WarningQueryVO> warningQueryVOList= null;
				if(reqResVO.getWarningQueryVOList()!=null && reqResVO.getWarningQueryVOList().size()>0){
					warningQueryVOList = reqResVO.getWarningQueryVOList();	
				}else{
					warningQueryVOList = new ArrayList<WarningQueryVO>();
				}				
				warningQueryVOList.add(warningQueryVO);
				reqResVO.setWarningQueryVOList(warningQueryVOList);
				reqResVO.setResXML("Warning");
				reqResVO.setComment("The entry did not come in extn_invoice_publish even after waiting for 30 seconds.So Please check manually.");


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

	private String getReturnOrderLineKey(String orderLineKey,
			NikeDOMSConnectionDAO nikedomsconnectiondao) throws ClassNotFoundException {
		String returnOrderLineKey = "";
		try {
		nikedomsconnectiondao.getDBValues(orderLineKey,
				DbValidationConstants.GET_RETURN_ORDERLINEKEY,
				NikeDOMSConstants.DOMS);
		CachedRowSet crs;
		
			crs = RowSetProvider.newFactory().createCachedRowSet();
		
		crs = nikedomsconnectiondao.getRowSet();
		int size = crs.size();
		crs.beforeFirst();
		if (size > 0) {
			if (size == 1) {
				if (crs.next()) {
					returnOrderLineKey = crs
							.getString("ORDER_LINE_KEY");

				}
			} else {
				while (crs.next()) {

					returnOrderLineKey = crs
							.getString("ORDER_LINE_KEY");
				}
			}
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return returnOrderLineKey;
	}

	public String getOrderLineKey(String primeLineNo, String orderNo,
			NikeDOMSConnectionDAO nikedomsconnectiondao) {
		String orderLineKey = "";
		String query = NikeDOMSConstants.getLineKeyPrLnNo.replaceFirst(
				"lineNo", primeLineNo);

		try {
			nikedomsconnectiondao.getDBValues(orderNo, query,
					NikeDOMSConstants.DOMS);
			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikedomsconnectiondao.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						orderLineKey = crs.getString("ORDER_LINE_KEY").trim();
					}
				} else {
					while (crs.next()) {
						orderLineKey = crs.getString("ORDER_LINE_KEY").trim();
					}
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return orderLineKey;

	}

	public String getInvoiceNumber(String messageText, String orderLineKey,
			NikeDOMSConnectionDAO nikedomsconnectiondao) {
		String invoiceNo = "";
		String query = "";
		try {
			if (messageText.contains("CREDIT")) {
				query = DbValidationConstants.GET_INVOICE_NO.replace(
						"InvoiceType", "'CREDIT_MEMO'");
			}
			else if (messageText.contains("RETURN")) {
				query = DbValidationConstants.GET_INVOICE_NO.replace(
						"InvoiceType", "'RETURN'");
				orderLineKey=getReturnOrderLineKey(orderLineKey, nikedomsconnectiondao);
			}
			else if (messageText.contains("CANCEL")) {
				query = DbValidationConstants.GET_INVOICE_NO.replace(
						"InvoiceType", "'INFO'");
			}else if (messageText.contains("returnInvoice")) {
				query = DbValidationConstants.GET_returnINVOICE_NO;
			} else {
				query = DbValidationConstants.GET_INVOICE_NO.replace(
						"InvoiceType", "'SHIPMENT','ORDER'");
			}
			nikedomsconnectiondao.getDBValues(orderLineKey, query,
					NikeDOMSConstants.DOMS);
			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			crs = nikedomsconnectiondao.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						invoiceNo = crs.getString(1);
					}
				} else {
					while (crs.next()) {
						invoiceNo = crs.getString(1);
					}
				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return invoiceNo;

	}
	public String getInvoiceXml(String orderType, String primeLineNo,
			String statusCode, String orderNo,
			NikeDOMSConnectionDAO nikedomsconnectiondao, String invoiceType,
			String environment, EnvConfigVO envConfigVO) throws JAXBException,
			InterruptedException {
		ReqResVO reqResVO = new ReqResVO();
		String orderLineKey = "";
		Clob clobXml=null;
		String invoiceXml="";
		String query = DbValidationConstants.VERIFY_INVOICE_PUBLISH;

		try {

			Thread.sleep(10000);
			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			String transNo = "";
			if (orderType.equalsIgnoreCase("STORE") && statusCode.equals("O")) {

				nikedomsconnectiondao.getDBValues(orderNo,
						NikeDOMSConstants.GET_MPOS_TRANS_NO,
						NikeDOMSConstants.DOMS);

				crs = nikedomsconnectiondao.getRowSet();

				int size = crs.size();
				crs.beforeFirst();
				if (size > 0) {
					if (size == 1) {
						if (crs.next()) {
							transNo = crs.getString(1);

						}
					} else {
						while (crs.next()) {

							transNo = crs.getString(1);
						}
					}

					nikedomsconnectiondao.getDBValuesPAC(statusCode, transNo,
							NikeDOMSConstants.GET_INVOICEXML_FROM_PUBLISHTABLE,
							NikeDOMSConstants.DOMS);
					crs = nikedomsconnectiondao.getRowSet();
				}
			} else {

				orderLineKey = getOrderLineKey(primeLineNo, orderNo,
						nikedomsconnectiondao);

				if (invoiceType.equalsIgnoreCase("Sales")) {
					query = query.replace("InvoiceTypes", "'SHIPMENT','ORDER'");
					nikedomsconnectiondao.getDBValuesPAC(statusCode,
							orderLineKey, query, NikeDOMSConstants.DOMS);

					crs = nikedomsconnectiondao.getRowSet();

				}
				if (invoiceType.equalsIgnoreCase("CreditMemo")) {
					query = query.replace("InvoiceTypes", "'CREDIT_MEMO'");
					nikedomsconnectiondao.getDBValuesPAC(statusCode,
							orderLineKey, query, NikeDOMSConstants.DOMS);

					crs = nikedomsconnectiondao.getRowSet();

				}
				if (invoiceType.equalsIgnoreCase("Return")) {
					String returnOrderLineKey = getReturnOrderLineKey(
							orderLineKey, nikedomsconnectiondao);
					query = query.replace("InvoiceTypes", "'RETURN'");
					nikedomsconnectiondao.getDBValuesPAC(statusCode,
							returnOrderLineKey, query, NikeDOMSConstants.DOMS);

					crs = nikedomsconnectiondao.getRowSet();

				}
				if (invoiceType.equalsIgnoreCase("Cancel")) {
					query = query.replace("InvoiceTypes", "'INFO'");
					nikedomsconnectiondao.getDBValuesPAC(statusCode,
							orderLineKey, query, NikeDOMSConstants.DOMS);

					crs = nikedomsconnectiondao.getRowSet();

				}
			}

			int size = crs.size();
			
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						clobXml = crs
								.getClob("EXTN_INVOICE_XML");

					}
				} else {
					while (crs.next()) {
						clobXml = crs
								.getClob("EXTN_INVOICE_XML");

					}
				}
		}
			AutomationHelperImpl helper=new AutomationHelperImpl();
			invoiceXml=helper.getStringFromClob(clobXml);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return invoiceXml;
	}

}
