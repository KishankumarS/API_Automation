package com.doms.automation.generatexml;

import java.util.ArrayList;

import com.doms.automation.bean.XmlTagLinesVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;


public interface GenerateXml {
	
		public String generateXml(String processStep,String orderNo,
				NikeDOMSConnectionDAO nikedomsconnectiondao,ArrayList<XmlTagLinesVO> xmlTagLinesVOList) throws ClassNotFoundException;
}
