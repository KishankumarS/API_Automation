package com.doms.automation.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.doms.automation.bean.EnvConfigVO;
import com.doms.automation.bean.ReqResVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.utils.NikeDOMSApplicationUtils;
import com.doms.automation.utils.NikeDOMSConstants;

public class CreateOrderService {
	
	PostRequest postReq = new PostRequest();
	String AuthorizationCode = "";
	String AuthTime = "";
	String AuthorizationExpirationDate = "";
	String RequestId = "";
	
	String TranReturnMessage = "";
	String RequestAmt = "";
	String quantity = "0";
	
	public ReqResVO inventoryAdjustment(String environment,String inventoryXML,EnvConfigVO envConfigVO){
		String responseData = "";
		ReqResVO reqresVO = new ReqResVO();
		try {
			
			responseData = postReq.postXML(environment, inventoryXML, "AdjustInventory_svc", "Y","<ApiSuccess/>",envConfigVO);
			reqresVO.setReqXML(inventoryXML);
			reqresVO.setResXML(responseData);
			System.out.println(inventoryXML);
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reqresVO;
	}
	public ReqResVO removeDirtyNode(String environment,String inventoryXML,EnvConfigVO envConfigVO){
		String responseData = "";
		ReqResVO reqresVO = new ReqResVO();
		try {
			responseData = postReq.postXML(environment, inventoryXML, "InventoryNodeControl_svc", "Y","<Inventory>",envConfigVO);
			reqresVO.setReqXML(inventoryXML);
			reqresVO.setResXML(responseData);
			System.out.println(inventoryXML);
			AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();
			if (!responseData.equalsIgnoreCase("Success")) {
				if(responseData.contains("ErrorDescription")){							
					
					reqresVO.setComment(automationHelperImpl.getErrorDescription(reqresVO.getResXML()));					
				}else{
					
					reqresVO.setComment(responseData);
				}
				reqresVO.setResXML("Error");
			}
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reqresVO;
	}
	
	public ReqResVO authXML(String environment,String authXML,EnvConfigVO envConfigVO,ReqResVO reqresVO){
		String responseData = "";		
		String strResponse = "";
		String bufferStr = "";
		AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();
		try {
			if(authXML != null && !authXML.equalsIgnoreCase("")){
				System.out.println(authXML);
				HttpResponse response = postReq.post(environment, authXML, "CallPG_svc","Y",envConfigVO);
				if(response != null){
					
 					HttpEntity r_entity = response.getEntity();
		
			        String xmlString = EntityUtils.toString(r_entity);
			        System.out.println("Rquest IS"+authXML);
			        System.out.println("RESPONSE IS"+xmlString);										
					if(xmlString.contains("Decision=\"ACCEPT\"")){
						responseData = "Success";
						reqresVO.setAuthResponse(xmlString);
					}else{
						responseData = xmlString;
						reqresVO.setComment(automationHelperImpl.getErrorDescription(xmlString));
					}
					
					
				}
			}
			
			reqresVO.setReqXML(authXML);
			reqresVO.setResXML(responseData);
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reqresVO;
	}
	
	
	public ReqResVO createOrder(String environment,String orderXML,EnvConfigVO envConfigVO) throws SQLException, FileNotFoundException, IOException{
		
		ReqResVO reqresVO = new ReqResVO();
		NikeDOMSApplicationUtils utils=new NikeDOMSApplicationUtils();
		try {			
			
			String responseData = "Success";
				responseData = postReq.postXML(environment, orderXML, "CreateOrder_svc", "Y","<Order",envConfigVO);
			//postReq.postQueue(environment, orderXML, "queue.SterlingX063Queue", "OrderCreationQueue", envConfigVO);
				reqresVO.setReqXML(orderXML);
				reqresVO.setResXML(responseData);
				System.out.println(orderXML);

			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} /*catch (IOException e) {
			e.printStackTrace();
		}*/
		
		return reqresVO;
	}
	
public ReqResVO createCloudOrder(String environment,String orderXML,EnvConfigVO envConfigVO) throws SQLException, FileNotFoundException, IOException{
		
		ReqResVO reqresVO = new ReqResVO();
		NikeDOMSApplicationUtils utils=new NikeDOMSApplicationUtils();
		try {			
			
			String responseData = "Success";
			String URL=envConfigVO.getcICOrderCreateURL();			
			System.out.println(URL);
			
			//responseData = postReq.postSOAPReq(environment, orderXML, URL);
			//String response = postReq.postSOAPReqSecured(environment, orderXML,URL);
				responseData = postReq.postXML(environment, orderXML, "CreateCloudOrder_svc", "Y","<Order",envConfigVO);
			//postReq.postQueue(environment, orderXML, "queue.SterlingX063Queue", "OrderCreationQueue", envConfigVO);
				reqresVO.setReqXML(orderXML);
				reqresVO.setResXML(responseData);
				System.out.println(orderXML);

			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} /*catch (IOException e) {
			e.printStackTrace();
		}*/
		
		return reqresVO;
	}
	
	
		
	public String checkInventory(NikeDOMSConnectionDAO nikedomsconnectiondao,String environment,String inventoryXML){
		
		
	
		try {
			
			
			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
			DocumentBuilderFactory factory = DocumentBuilderFactory
			.newInstance();
	DocumentBuilder db = factory.newDocumentBuilder();
	InputSource inStream = new InputSource(new StringReader(
			inventoryXML));

	Document doc = db.parse(inStream);

	XPathFactory xFactory = XPathFactory.newInstance();
	XPath xpath = xFactory.newXPath();

	String itemID = (String) xpath.evaluate(
			"/Items/Item/@ItemID",
			doc, XPathConstants.STRING);
	
	String shipNode = (String) xpath.evaluate(
			"/Items/Item/@ShipNode",
			doc, XPathConstants.STRING);
	String p="%";
	
			nikedomsconnectiondao.getDBValuesPAC(p+itemID+p,p+shipNode.toString()+p,
					NikeDOMSConstants.GET_INV_QTY,NikeDOMSConstants.DOMS);
	         crs = nikedomsconnectiondao.getRowSet();		         
	         int size = crs.size();
	 		crs.beforeFirst();
	 		if (size > 0) {
	 			if (size == 1) {
	 				if (crs.next()) {
	 					quantity=crs.getString(1);
	 				}
	 			} else {
	 				while (crs.next()) {

	 					quantity=crs.getString(1);
	 				}
	 			}
	 		}
	      System.out.println("Size:"+ size);
	      
	      
		}catch(Exception e){
			e.printStackTrace();
		}
		return quantity;
	}
	
	public ReqResVO checkOrderInDOMS(NikeDOMSConnectionDAO nikedomsconnectiondao,String environment,String orderNo){
		
		String orderNoDB = "";
		String responseData = "";
		ReqResVO reqresVO = new ReqResVO();
		try {
			
			CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();


			nikedomsconnectiondao.getDBValues(orderNo,
					NikeDOMSConstants.getOrderNo,NikeDOMSConstants.DOMS);
	         crs = nikedomsconnectiondao.getRowSet();		         
	         int size = crs.size();
				crs.beforeFirst();
				if(size>0){
					if(size==1){
						if (crs.next()) {
							 orderNoDB =  crs.getString(1); 
							}
					}
					else{
						while (crs.next()) {
							 orderNoDB =  crs.getString(1); 
							}
					}
					}  
	     
	      if(!orderNoDB.equalsIgnoreCase(orderNo)){
				responseData = "Error. Order "+orderNo+" is not present in DOMS";
				reqresVO.setComment( "Order "+orderNo+" is not present in DOMS");
			}else{
				responseData = "Success";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
      reqresVO.setReqXML(NikeDOMSConstants.getOrderNo);
      reqresVO.setResXML(responseData);
      return reqresVO;
	}
	
	protected String getElementByTag(Document doc,String tag){
		String tagValue = "";
		NodeList nl = doc.getElementsByTagName(tag);
        for(int j = 0; j < nl.getLength(); j++) {
            if (nl.item(j).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                 org.w3c.dom.Element nameElement = (org.w3c.dom.Element) nl.item(j);
                 tagValue = nameElement.getFirstChild().getNodeValue().trim();
             }
        }
        return tagValue;
	}
public ReqResVO createGSOrder(String environment,String orderXML,EnvConfigVO envConfigVO,Map<String, String> xlSheetAttributesMap ) throws SQLException, FileNotFoundException, IOException{
		
		ReqResVO reqresVO = new ReqResVO();
		try {			
			
			String responseData = "Success";
			
			/*
			 *Service name 'CreateGlobalStoreOrder_v1_svc' changed to 'CreateGlobalStoreOrder_svc'
			 */
			
			//responseData = postReq.postXML(environment, orderXML, "CreateGlobalStoreOrder_v1_svc", "Y","<Order",envConfigVO);
			
			responseData = postReq.postXML(environment, orderXML, "CreateGlobalStoreOrder_svc", "Y", "<Order",
						envConfigVO);
			//postReq.postQueue(environment, orderXML, "queue.SterlingX421Queue", "GSOrderCreationQueue",envConfigVO);
						
			reqresVO.setReqXML(orderXML);
			reqresVO.setResXML(responseData);
			System.out.println(orderXML);

			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} /*catch (IOException e) {
			e.printStackTrace();
		}*/
		
		return reqresVO;
	}
	
	
}
