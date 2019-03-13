package com.doms.automation.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import com.doms.automation.bean.DbEnvConfig;

public class DbConnection {
	public Connection getConection(Map<String,DbEnvConfig> dbConfigMap,String dataBase){
		
				DbEnvConfig dbEnvConfig = getDbConfig(dbConfigMap,dataBase);
				String host=dbEnvConfig.getHost();
				String userID=dbEnvConfig.getUserID();
				String password=dbEnvConfig.getPassword();
				String instance = dbEnvConfig.getInstance();
				Connection connection = null;
				try {
					Class.forName( "oracle.jdbc.driver.OracleDriver" ).newInstance();
					connection = DriverManager.getConnection(
					        "jdbc:oracle:thin:@"+host+":1521:"+instance,
					        userID,
					        password);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		return connection;
	}

	public DbEnvConfig getDbConfig(Map<String, DbEnvConfig> dbConfigMap,
			String dataBase) {
		DbEnvConfig dbEnvConfig = new DbEnvConfig();
		for (Map.Entry<String, DbEnvConfig> entry : dbConfigMap.entrySet()) {
			if(dataBase.equalsIgnoreCase(entry.getKey())){
				dbEnvConfig = entry.getValue();

			}

		}		
		
		return dbEnvConfig;
	}
	
	public void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
