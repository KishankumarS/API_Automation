package com.doms.automation.service;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import com.doms.automation.bean.ReqResVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;
import com.doms.automation.utils.DbValidationConstants;
import com.doms.automation.utils.NikeDOMSConstants;

public class PeelAndStickUsService {
	public int getDbEntryCount(String orderNo, NikeDOMSConnectionDAO nikeDOMSConnectionDAO){

		CachedRowSet crs = null;
		int count = 0;
		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();

			nikeDOMSConnectionDAO.getDBValues(orderNo,DbValidationConstants.VERIFY_RROENTRY,
					NikeDOMSConstants.doms);
			crs = nikeDOMSConnectionDAO.getRowSet();

			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						count = crs.getInt(1);

					}
				} else {
					while (crs.next()) {
						count = crs.getInt(1);

					}
				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return count;
	}
	public ReqResVO checkEntriesInDb(int count, String orderNo, NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		ReqResVO reqResVO = new ReqResVO();
		CachedRowSet crs = null;
		int dbCount = 0;
		
		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();
			
			nikeDOMSConnectionDAO.getDBValues(orderNo,DbValidationConstants.VERIFY_RROENTRY,
					NikeDOMSConstants.doms);
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						dbCount = crs.getInt(1);
					}
				} else {
					while (crs.next()) {
						dbCount = crs.getInt(1);
					}
				}
			}
			reqResVO.setResXML(dbCount==count ? "Success" : "Error");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(crs!=null){
				try {
					crs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return reqResVO;
	}
	public ReqResVO checkTimeStampInDeliveryStatusDetails(String trackingNo, String time,
			String carrier_status_code, NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		ReqResVO reqResVO = new ReqResVO();
		CachedRowSet crs = null;
		String timeStamp = null;
		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();
			nikeDOMSConnectionDAO.getDBValues2parametres(trackingNo,carrier_status_code, DbValidationConstants.GETTIME_STATUSDETAILS,
					NikeDOMSConstants.doms);
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						timeStamp = crs.getString(1);
					}
				} else {
					while (crs.next()) {
						timeStamp = crs.getString(1);
					}
				}
			}
			time = time.replaceFirst("T", " ");
			reqResVO.setResXML(timeStamp.equalsIgnoreCase(time) ? "Success" : "Error");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(crs!=null){
				try {
					crs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return reqResVO;
	}
	
	public ReqResVO checkTimeStampInDeliveryStatusTRACKER(String trackingNo, String time,String status,
			 NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		ReqResVO reqResVO = new ReqResVO();
		CachedRowSet crs = null;
		String timeStamp = null;
		String query =  DbValidationConstants.GETTIME_STATUSTRACKER.replace("timestamp", status);
		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();
			nikeDOMSConnectionDAO.getDBValues(trackingNo, query,
					NikeDOMSConstants.doms);
			crs = nikeDOMSConnectionDAO.getRowSet();
			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						timeStamp = crs.getString(1);
					}
				} else {
					while (crs.next()) {
						timeStamp = crs.getString(1);
					}
				}
			}
			time = time.replaceFirst("T", " ");
			reqResVO.setResXML(timeStamp.equalsIgnoreCase(time) ? "Success" : "Error");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(crs!=null){
				try {
					crs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return reqResVO;
	}
}
