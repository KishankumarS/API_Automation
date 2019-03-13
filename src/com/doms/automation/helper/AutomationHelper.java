package com.doms.automation.helper;

import java.util.ArrayList;

import com.doms.automation.bean.XmlTagAttributesVO;
import com.doms.automation.bean.XmlTagLinesVO;

public interface AutomationHelper {
	public static final String AMERICAN_EXPRESS_PO = "AX00";
	public static final String CARTASI_PO 			= "CS00";
	public static final String CARTEBLUE_PO	    = "CB00";
	public static final String DANKORT_PO		    = "DK00";
	public static final String DELTA_PO 			= "DT00";
	public static final String DISCOVER_PO 		= "OT00";
	public static final String DISCOVER_CARD_PO 	= "OT00";
	public static final String DOMESTIC_MAESTRO_PO = "MD00";
	public static final String EURO_CARD_PO 		= "EC00";
	public static final String LASER_PO 			= "LS00";
	public static final String MASTER_CARD_PO		= "MC00";
	public static final String PAYPAL_PO 			= "PP01";
	public static final String VISA_PO 			= "VS00";
	public static final String VISA_DEBIT_PO 		= "VS00";
	public static final String VISA_ELECTRON_PO 	= "VE00";
	public static final String OFFLINE_BANK_PO		= "BO00";
	

	public static final String AMERICAN_EXPRESS = "AmericanExpress";
	public static final String CARTASI 			= "CartaSi";
	public static final String CARTEBLUE	    = "CarteBleue";
	public static final String DANKORT		    = "Dankort";
	public static final String DELTA 			= "Delta";
	public static final String DISCOVER 		= "Discover";
	public static final String DISCOVER_CARD 	= "Discover Card";
	public static final String DOMESTIC_MAESTRO = "DomesticMaestro";
	public static final String EURO_CARD 		= "EuroCard";
	public static final String LASER 			= "Laser";
	public static final String MASTER_CARD		= "MasterCard";
	public static final String PAYPAL 			= "PAYPAL";
	public static final String VISA 			= "Visa";
	public static final String VISA_DEBIT 		= "VisaDebit";
	public static final String VISA_ELECTRON 	= "VisaElectron";
	public static final String OFFLINE_BANK		= "OFFLINE_BANK";

	
 

	public String generatePONumber(XmlTagLinesVO xmlTagLinesVO,
			String orderNumber);

}
