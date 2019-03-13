package com.doms.automation.generatexml.impl;

import java.util.ArrayList;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.bean.OrderLineVO;
import com.doms.automation.bean.TestCaseDataVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.generatexml.GenerateXml;
import com.doms.automation.helper.impl.AutomationHelperImpl;
import com.doms.automation.jaxbclasses.taskqueue.TaskQueue;
import com.doms.automation.marshaller.impl.TaskQueueMarshaller;
import com.doms.automation.utils.NikeDOMSApplicationUtils;
import com.doms.automation.utils.NikeDOMSConstants;

public class CreateWOXml {

	
	public String generateXml(String processStep, String orderNo,
			NikeDOMSConnectionDAO nikedomsconnectiondao,
			ArrayList<XmlTagLinesVO> xmlTagLinesVOList,Map<String, DbEnvConfig> dbConfigMap,boolean isNikeGS,String fsoOrderNo)
			throws ClassNotFoundException {
		String task_q_key = null;
        String transaction_key = null;
        String data_key = null;
        String WOXml ="";
        try{
              Thread.sleep(15000);
              CachedRowSet crs;	
              crs = RowSetProvider.newFactory().createCachedRowSet();
              AutomationHelperImpl automationHelperImpl = new AutomationHelperImpl();
      		  String orderNum = automationHelperImpl.getOrderNoOrFsoOrderNo(isNikeGS, orderNo, dbConfigMap);
              nikedomsconnectiondao.getDBValues(orderNum,NikeDOMSConstants.GET_YFS_TASK_Q_DATA,NikeDOMSConstants.DOMS);
              crs = nikedomsconnectiondao.getRowSet();
              while (crs.next()) {
                    task_q_key = crs.getString(1);
                    transaction_key =  crs.getString(2);
                    data_key =  crs.getString(3);
              }
              NikeDOMSApplicationUtils nikeDomsAppUtils = new NikeDOMSApplicationUtils();
              TaskQueue taskQueue = new TaskQueue();
              taskQueue.setDataKey(data_key);
              taskQueue.setTransactionKey(transaction_key);
              taskQueue.setTaskQKey(task_q_key);
              taskQueue.setCreateprogid("CreateOrderJMSServer");
              taskQueue.setCreateuserid("CreateOrderJMSServer");
              taskQueue.setModifyprogid("CreateOrderJMSServer");
              taskQueue.setModifyuserid("CreateOrderJMSServer");
              taskQueue.setDataType("OrderHeaderKey");
              taskQueue.setHoldFlag("N");
              taskQueue.setLockid("0");
              taskQueue.setCreatets(nikeDomsAppUtils.sysdate()+"+00:00");
              taskQueue.setModifyts(nikeDomsAppUtils.sysdate()+"+00:00");
              taskQueue.setAvailableDate(nikeDomsAppUtils.sysdate()+"+00:00");
              TaskQueueMarshaller taskQueueMarshaller = new TaskQueueMarshaller(taskQueue);
              WOXml = taskQueueMarshaller.convertJaxbObjectToXML();
             
              
        }catch(Exception e){
              e.printStackTrace();
        }
        System.out.println(WOXml);
		return WOXml;
	}
	

}
