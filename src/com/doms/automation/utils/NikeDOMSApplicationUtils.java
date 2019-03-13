package com.doms.automation.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.doms.automation.bean.ReqResVO;
import com.doms.automation.dao.NikeDOMSConnectionDAO;

public class NikeDOMSApplicationUtils {
	
	
	
	public static List<String> xmlListForCSV = new ArrayList<String>();
	public static Map<String, ReqResVO> responseDataMap = new LinkedHashMap<String, ReqResVO>();
	public static Map<String, ReqResVO> responseMap = new LinkedHashMap<String, ReqResVO>();

	public static Map resultsMap = new LinkedHashMap<String, Map>();
	public static final String CLASS_NAME = "com.doms.automation.utils.NikeDOMSPropertyReader";
    

	public static void setResponseMap(Map ResponseDataMap){
		responseMap = ResponseDataMap;
	}
	
	public static Map addXMLsTOCSVMap(List selectedTestCases){
		Iterator entries = responseMap.entrySet().iterator();
	    String result = "";
	    String[] testList = new String[20];
	    List<Map<String,ReqResVO>> testCaseResults = new ArrayList<Map<String,ReqResVO>>();
	    for(int i=0;i < selectedTestCases.size();i++){
	    	String testCaseName = selectedTestCases.get(i).toString();
	    	while (entries.hasNext()) {
		    	Entry thisEntry = (Entry) entries.next();
		    	
		    	String testStep = thisEntry.getKey().toString();
		    	ReqResVO reqResVO = (ReqResVO)thisEntry.getValue();
		    	if(testStep.endsWith(testCaseName)){
		    		
		    		String key = testStep.substring(0,testStep.indexOf(testCaseName)-1);
		    		responseDataMap.put(key, reqResVO);
//		    		testCaseResults.add(responseDataMap);
		    		
		    		testCaseResults = (List)resultsMap.get(testCaseName);
		    		if(testCaseResults!=null){
		    			resultsMap.put(testCaseName, testCaseResults.add(responseDataMap));	 
		    		}
		    		   		
		    	}
		    }
	    }
	    
		
		return resultsMap;
		
	}
	public void writeLogPathToLog4jFile(String logPropertiesPath) {
		final String methodName = "writeLogPathToLog4jFile";
		Properties props = new Properties();
		InputStream is = null;
		// First try loading from the current directory
		try {
			File f = new File(logPropertiesPath);
			is = new FileInputStream(f);
			 NikeDOMSLogger.writeLog("NIKEDOMS_LOGGER_NAME", CLASS_NAME, NikeDOMSLogger.LEVEL_DEBUG, methodName,
	                    "Inside NikeDOMSPropertyReader Class, logger initialised");
		} catch (Exception e) {
			is = null;
		}
		try {
			if (is == null) {
				// Try loading from classpath
				is = getClass().getResourceAsStream(logPropertiesPath);
			}
			// Try loading properties from the file (if found)
			props.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getTimeStamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime()) + "-00:00";

	}
	
	public String sysdate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}

	public String getUniqueNo() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMDDHHMMSS",Locale.US);
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}
	
	public String readFile( String file ) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }

	    return stringBuilder.toString();
	}
	// eg:2014-11-06T11:51:47
	public String getXMLGregorianCalendarwithoutimezone() throws DatatypeConfigurationException{
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		date2.setTimezone( DatatypeConstants.FIELD_UNDEFINED );
		date2.setFractionalSecond( null );
		return date2.toString();
		
	}
	
	//eg:2014-11-06T11:54:51+05:30
	public String getXMLGregorianCalendar() throws DatatypeConfigurationException{
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		
		date2.setFractionalSecond( null );
		return date2.toString();
		
	}
	public String generateUniqueNo(String dateFormat) {
		Date today;
        String output;
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(dateFormat);
        today = new Date();
        output = formatter.format(today);
        String DateString = output.toString();
        return(DateString);
	}
	public String generatepastdate(String format) {
		 DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        date.setTime(date.getTime()-24*60*60*1000);  // Subtract 24*60*60*1000 milliseconds
        return dateFormat.format(date);	     
	}
	public String generatefuturedate(String format) {
		 DateFormat dateFormat = new SimpleDateFormat(format);
       Date date = new Date();
       date.setTime(date.getTime()+24*60*60*1000);  // Subtract 24*60*60*1000 milliseconds
       return dateFormat.format(date);	     
	}
	/*public synchronized String GetURL(String env,String urlName) throws InvalidFormatException, IOException{
		
		String envConfigPath = System.getProperty("user.dir")+"/resources/jmeter/Env_Config.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int columnIndex = 0;
		String url ="";
		br = new BufferedReader(new FileReader(envConfigPath));
		while ((line = br.readLine()) != null) {
 
		        // use comma as separator
			String[] envDetails = line.split(cvsSplitBy);
			
			for(int i = 0 ; i < envDetails.length;i++){
				if(envDetails[i].equalsIgnoreCase(urlName)){
					columnIndex = i;
					break;
				}
			}
			
			if(envDetails[0].equalsIgnoreCase(env)){
				url = envDetails[columnIndex];
			}
		}
		br.close();
		return url;
	}*/
	
	public boolean isResponseSuccess(String response){
		boolean isSucess = false;
		if(response!=null){
			isSucess=(response.contains(NikeDOMSConstants.STATUS_OK)||
					response.equalsIgnoreCase("Success")||
					response.contains(NikeDOMSConstants.ORDER) ||
					response.contains(NikeDOMSConstants.STATUS_PUBLISHED)) ? true : false;		
		}
		return isSucess;
		
	}
	public String dateFuture(int daysToAdd,String formatString){
		SimpleDateFormat dtFormat = new SimpleDateFormat(formatString);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, daysToAdd);
		return(dtFormat.format(cal.getTime()));
	}
	
	public String sysdate(String format,int lessDays,int plusHours){
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, (-1*lessDays));
		cal.add(Calendar.HOUR, (plusHours));
				return dateFormat.format(cal.getTime());
	}
	
	public String generateUniqueNoWithLength(int length){
		Random rand = new Random(); 
		Long l;
		int i=0;	
		do{ 
			l = rand.nextLong();
			if(l>0){
				i++;				
		}}
		while(i==0);
		return (String.valueOf(l).substring(0, length));
	}
	public String generateRandom4DigitNo(){
		 Random r = new Random( System.currentTimeMillis() );
		return String.valueOf(1000 + r.nextInt(2000));
		
	}
	
	/*For Japan Konbini orders business needs to ignore the holiday calender during 
	 * new EDD calculation,so setting EDD as next coming Sunday
	 */
	public String getFutureSunday(int daysToAdd,String formatString){
		SimpleDateFormat dtFormat = new SimpleDateFormat(formatString);
		Calendar now = new GregorianCalendar();
		/*Calendar start = new GregorianCalendar(now.get(Calendar.YEAR), 
		        now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.HOUR), now.get(Calendar.MINUTE), now.get(Calendar.SECOND) );*/
		Calendar start = new GregorianCalendar(now.get(Calendar.YEAR), 
		        now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

		while (start.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
		    start.add(Calendar.DAY_OF_WEEK, -1);
		}

		Calendar end = (Calendar) start.clone();
		end.add(Calendar.DAY_OF_MONTH, daysToAdd);
		
		return(dtFormat.format(end.getTime()));
	}
	public String timeWithMillisecond(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();	  
	  
		return sdf.format(now);
		
	}
	public String timeWithMillisecondT(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		Date now = new Date();	  
	  
		return sdf.format(now);
		
	}
	 public String addMinutesToTime(String time,int minutes,String format){
		 	String newTime="";			
			SimpleDateFormat df = new SimpleDateFormat(format);
			Date d1;
			try {
				d1 = df.parse(time);
				Calendar cal = Calendar.getInstance();
				cal.setTime(d1);
				cal.add(Calendar.MINUTE, minutes);
				 newTime = df.format(cal.getTime());				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return newTime;
			
	 }
	 public Date convertStringToDate(String dateInString,String dateFormat){
		
		 DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
		 Date date = null;
		try {
			date = format.parse(dateInString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println(date);
		 return date;
	 }
	  public String convertDateToString(Date d,String dateFormat){
		  DateFormat df = new SimpleDateFormat(dateFormat);		
		  return df.format(d);
	  }
	  public String changeDateFormat(String date,String dateFormat){
			 DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
			 Date d = null;
			try {
				d = format.parse(date);
			} catch (ParseException e) {			
				e.printStackTrace();
			}
			 return format.format(d); 
			 
		 }
	 public int compareDates(String s1,String s2,String format){
		 /*if(date1.compareTo(date2)>0)
     		"Date1 is after Date2"
     	 if(date1.compareTo(date2)<0)
     		"Date1 is before Date2"
     	if(date1.compareTo(date2)==0)
     		"Date1 is equal to Date2"    	
		   
		  */
		 Date date1 = convertStringToDate(s1,format);
		 Date date2 =convertStringToDate(s2,format);
		 
		 return date1.compareTo(date2);
	 }

	public String getDbDate(NikeDOMSConnectionDAO nikeDOMSConnectionDAO) {
		String dbDate = "";
		CachedRowSet crs = null;

		try {
			crs = RowSetProvider.newFactory().createCachedRowSet();

			nikeDOMSConnectionDAO.getDBValues(DbValidationConstants.GET_DB_DATE,
					NikeDOMSConstants.DOMS);
			crs = nikeDOMSConnectionDAO.getRowSet();

			int size = crs.size();
			crs.beforeFirst();
			if (size > 0) {
				if (size == 1) {
					if (crs.next()) {
						dbDate = crs.getString(1);

					}
				} else {
					while (crs.next()) {
						dbDate = crs.getString(1);

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

		return dbDate;
	}
	
}
