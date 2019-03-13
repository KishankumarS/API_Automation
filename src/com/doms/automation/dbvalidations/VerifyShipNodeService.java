package com.doms.automation.dbvalidations;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import com.doms.automation.bean.ReqResVO;
import com.doms.automation.bean.XmlTagAttributesVO;
import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.utils.DbValidationConstants;
import com.doms.automation.utils.NikeDOMSConstants;

public class VerifyShipNodeService {

	public ReqResVO verifyShipNode(ArrayList<XmlTagLinesVO> xmlTagLinesVOList,
			NikeDOMSConnectionDAO nikedomsconnectiondao, String orderNo) {
		ReqResVO reqResVO = new ReqResVO();
		String lineNo="";		
		String shipNode="";
		String lineType="";		
		
		for(XmlTagLinesVO xmlTagLinesVO:xmlTagLinesVOList){
			boolean isInline=false;
			
			for(XmlTagAttributesVO xmlTagAttributesVO:xmlTagLinesVO.getXmlTagAttributesVO()){
				if("LineNo".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())){									
					lineNo=xmlTagAttributesVO.getAttributeValue();
					}
				if("LineType".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())){									
					lineType=xmlTagAttributesVO.getAttributeValue();
					}
				if("INLINE".equalsIgnoreCase(lineType)){
					isInline=true;
				if("ShipNode".equalsIgnoreCase(xmlTagAttributesVO.getAttributeName())){									
					shipNode=xmlTagAttributesVO.getAttributeValue();
					}				
				}
		}
			if(isInline){
			try {
				nikedomsconnectiondao.getDbValues(lineNo, shipNode, orderNo, 
						DbValidationConstants.VERIFY_SHIPNODE, NikeDOMSConstants.DOMS);
				CachedRowSet crs= RowSetProvider.newFactory().createCachedRowSet();
				crs = nikedomsconnectiondao.getRowSet();
				int size = crs.size();
				reqResVO.setReqXML("Verifying order scheduled to correct ship node");
				if(size>0){
					
					reqResVO.setResXML("Success");
				}
				else{
					reqResVO.setResXML("Scheduled to different shipnode. Error");
					
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		return reqResVO;
	}

}
