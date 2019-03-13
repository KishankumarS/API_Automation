package com.doms.automation.utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
/**
 *
 * @author dhanesh_raj
 */
public class NikeDOMSLogger {
	    
	    //The Logging Level is set to OFF
	    public final static int LEVEL_OFF = 1 ;
	    //The Logging Level is set to FATAL
	    public final static int LEVEL_FATAL = 2 ;
	    //The Logging Level is set to ERROR
	    public final static int LEVEL_ERROR = 3 ;
	    //The Logging Level is set to WARN
	    public final static int LEVEL_WARN = 4 ;
	    //The Logging Level is set to INFO
	    public final static int LEVEL_INFO = 5 ;
	    //The Logging Level is set to DEBUG
	    public final static int LEVEL_DEBUG = 6 ;
	    //The Logging Level is set to ALL
	    public final static int LEVEL_ALL = 7 ; 
	    
	    
	    private static final String APP_LOG_DELIMETER = " | "; 
	    
	
	    /**
	     * Method to provide the logging services. It uses the log4j log class in
	     * order to log messages. Writes logs to log file. This method logs the
	     * class name, severity and a descriptive message.
	     *
	     * @param loggerName
	     *            Logger name for logging  
	     * @param callerFQCN
	     *            class name from which this method is invoked
	     * @param level
	     *            integral log Severity level
	     * @param methodName
	     *            methodName
	     * @param message
	     *            log message
	     */
	    public static void writeLog (String loggerName,
	            String callerFQCN, int level, 
	            String methodName ,String message ){
	        
	        StringBuilder logMessage = new StringBuilder();
	        	logMessage.append(callerFQCN)
	        	.append(APP_LOG_DELIMETER)
	        	.append(methodName)
	        	.append(APP_LOG_DELIMETER)
	        	.append(message);        	
	        	
	        	logMessage( loggerName, callerFQCN, level, logMessage.toString(), null);

	    }  
	   
	    
	    /**
	     * Method to provide the logging services. It uses the log4j log class in
	     * order to log messages. Writes logs to log file. This method logs the
	     * class name, severity and a descriptive message.
	     *
	     * @param loggerName
	     *            Logger name for logging  
	     * @param callerFQCN
	     *            class name from which this method is invoked
	     * @param level
	     *            integral log Severity level
	     * @param message
	     *            log message
	     * @param throwable
	     *            the throwable exception object
	     */
	    private static void logMessage (String loggerName,
	            String callerFQCN, int level,
	            String message, Throwable throwable){
	        
	        Logger  logger = Logger.getLogger(loggerName);
	        
	        switch ( level ) {
	        
	        case LEVEL_OFF :
	            logger.log( callerFQCN, Level.OFF, message,throwable);
	            break;
	            
	        case LEVEL_FATAL :
	            logger.log( callerFQCN, Level.FATAL, message, throwable);
	            break;
	            
	        case LEVEL_ERROR:
	            logger.log( callerFQCN, Level.ERROR, message, throwable);
	            break;
	            
	        case LEVEL_WARN:
	            logger.log( callerFQCN, Level.WARN, message, throwable);
	            break;
	            
	        case LEVEL_INFO:
	            logger.log( callerFQCN, Level.INFO, message, throwable);
	            break;

	        case LEVEL_DEBUG:
	            logger.log( callerFQCN, Level.DEBUG, message, throwable);
	            break;
	            
	        case LEVEL_ALL:
	            logger.log( callerFQCN, Level.ALL, message, throwable);
	            break;
	            
	        default :
	            break;
	        
	        }
	    }

}


