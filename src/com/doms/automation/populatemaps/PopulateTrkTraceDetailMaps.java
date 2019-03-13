package com.doms.automation.populatemaps;


import java.util.HashMap;
import java.util.Map;

import com.doms.automation.bean.*;
import com.doms.automation.utils.GlobalStoreConstants;

public class PopulateTrkTraceDetailMaps {
	
	public Map<String,TrkTraceStatusDetails> getMap(){
		
		Map<String,TrkTraceStatusDetails> TrkTraceDetailsMap = new HashMap<String,TrkTraceStatusDetails>();
		
		TrkTraceDetailsMap.put(
				GlobalStoreConstants.SHIPPED, 
				setTrkTraceStatusDetails(GlobalStoreConstants.ESWSTATUS_SHIPPED,
						GlobalStoreConstants.SHIPPED,
						GlobalStoreConstants.BASE_SHIPPED,
						GlobalStoreConstants.STERSTATUS_SHIPPED));
	
		TrkTraceDetailsMap.put(
				GlobalStoreConstants.ARRIVEDINTERNATIONALHUB, 
				setTrkTraceStatusDetails(GlobalStoreConstants.ESWSTATUS_ARRIVEDINTERNATIONALHUB,
						GlobalStoreConstants.ARRIVEDINTERNATIONALHUB,
						GlobalStoreConstants.BASE_ARRIVEDINTERNATIONALHUB,
						GlobalStoreConstants.STERSTATUS_ARRIVEDINTERNATIONALHUB));
		TrkTraceDetailsMap.put(
				GlobalStoreConstants.INVALIDSTATUS, 
				setTrkTraceStatusDetails(GlobalStoreConstants.ESWSTATUS_INVALIDSTATUS,
						GlobalStoreConstants.INVALIDSTATUS,
						GlobalStoreConstants.BASE_INVALIDSTATUS,
						GlobalStoreConstants.STERSTATUS_INVALIDSTATUS));
		TrkTraceDetailsMap.put(
				GlobalStoreConstants.DEPARTEDINTERNATIONALHUB,
				setTrkTraceStatusDetails(GlobalStoreConstants.ESWSTATUS_DEPARTEDINTERNATIONALHUB,
						GlobalStoreConstants.DEPARTEDINTERNATIONALHUB,
						GlobalStoreConstants.BASE_DEPARTEDINTERNATIONALHUB,
						GlobalStoreConstants.STERSTATUS_DEPARTEDINTERNATIONALHUB));
		TrkTraceDetailsMap.put(
				GlobalStoreConstants.DEPARTEDORIGINCOUNTRY,
				setTrkTraceStatusDetails(GlobalStoreConstants.ESWSTATUS_DEPARTEDORIGINCOUNTRY,
						GlobalStoreConstants.DEPARTEDORIGINCOUNTRY,
						GlobalStoreConstants.BASE_DEPARTEDORIGINCOUNTRY,
						GlobalStoreConstants.STERSTATUS_DEPARTEDORIGINCOUNTRY));
		TrkTraceDetailsMap.put(
				GlobalStoreConstants.ARRIVEDDESTINATIONCOUNTRY,
				setTrkTraceStatusDetails(GlobalStoreConstants.ESWSTATUS_ARRIVEDDESTINATIONCOUNTRY,
						GlobalStoreConstants.ARRIVEDDESTINATIONCOUNTRY,
						GlobalStoreConstants.BASE_ARRIVEDDESTINATIONCOUNTRY,
						GlobalStoreConstants.STERSTATUS_ARRIVEDDESTINATIONCOUNTRY));
		TrkTraceDetailsMap.put(
				GlobalStoreConstants.CUSTOMSRELEASED,
				setTrkTraceStatusDetails(GlobalStoreConstants.ESWSTATUS_CUSTOMSRELEASED,
						GlobalStoreConstants.CUSTOMSRELEASED,
						GlobalStoreConstants.BASE_CUSTOMSRELEASED,
						GlobalStoreConstants.STERSTATUS_CUSTOMSRELEASED));
		TrkTraceDetailsMap.put(
				GlobalStoreConstants.OUTFORDELIVERY,
				setTrkTraceStatusDetails(GlobalStoreConstants.ESWSTATUS_OUTFORDELIVERY,
						GlobalStoreConstants.OUTFORDELIVERY,
						GlobalStoreConstants.BASE_OUTFORDELIVERY,
						GlobalStoreConstants.STERSTATUS_OUTFORDELIVERY));
		TrkTraceDetailsMap.put(
				GlobalStoreConstants.DELIVERED,
				setTrkTraceStatusDetails(GlobalStoreConstants.ESWSTATUS_DELIVERED,
						GlobalStoreConstants.DELIVERED,
						GlobalStoreConstants.BASE_DELIVERED,
						GlobalStoreConstants.STERSTATUS_DELIVERED));
		TrkTraceDetailsMap.put(
				GlobalStoreConstants.ATTEMPTEDDELIVERY,
				setTrkTraceStatusDetails(GlobalStoreConstants.ESWSTATUS_ATTEMPTEDDELIVERY,
						GlobalStoreConstants.ATTEMPTEDDELIVERY,
						GlobalStoreConstants.BASE_ATTEMPTEDDELIVERY,
						GlobalStoreConstants.STERSTATUS_ATTEMPTEDDELIVERY));
		
		return TrkTraceDetailsMap;		
	}
	
		
	
	private TrkTraceStatusDetails setTrkTraceStatusDetails(String eswStatus, String eswShortDescription,
			String sterlingBaseDropStatus,String sterlingStatusDescription) {
		TrkTraceStatusDetails trkTraceStatusDetails = new TrkTraceStatusDetails();
		trkTraceStatusDetails.setEswShortDescription(eswShortDescription);
		trkTraceStatusDetails.setEswStatus(eswStatus);
		trkTraceStatusDetails.setSterlingBaseDropStatus(sterlingBaseDropStatus);
		trkTraceStatusDetails.setSterlingStatusDescription(sterlingStatusDescription);
		return trkTraceStatusDetails;
		
	}

}
