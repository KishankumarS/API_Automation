package com.doms.automation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import com.doms.automation.bean.DbEnvConfig;
import com.doms.automation.utils.NikeDOMSConstants;
import com.sun.rowset.CachedRowSetImpl;

public class NikeDOMSConnectionDAO {
	private final String CLASS_NAME = "com.doms.automation.dao.NikeDOMSConnectionDAO";
	private Map<String, DbEnvConfig> dbConfigMap;
	private CachedRowSet crs = null;
	private DbConnection dbConnection;

	public NikeDOMSConnectionDAO(Map<String, DbEnvConfig> dbConfigMap) {

		this.dbConfigMap = dbConfigMap;
		dbConnection = new DbConnection();

	}

	public synchronized void getDBValues(String querry, String dataBase) throws ClassNotFoundException {

		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		ResultSet rs = null;

		try {

			connection = dbConnection.getConection(dbConfigMap, dataBase);
			preparedSatement = (PreparedStatement) connection.prepareStatement(querry);
			System.out.println("Query is >>>>>>>>>>>>>>>>>>>>>>>" + querry);
			// System.out.println("OrderID is >>>>>>>>>>>>>>>>>>>"+orderID);
			// preparedSatement.setString(1, orderID);

			rs = preparedSatement.executeQuery();

			crs = new CachedRowSetImpl();

			crs.populate(rs);

			rs.close();
			connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (preparedSatement != null) {
				try {
					preparedSatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public synchronized void getDBValues(String orderID, String querry, String dataBase)
			throws ClassNotFoundException, SQLException {
		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		ResultSet rs = null;

		try {
			preparedSatement = (PreparedStatement) connection.prepareStatement(querry);
			System.out.println("Query is >>>>>>>>>>>>>>>>>>>>>>>" + querry);
			System.out.println("OrderID is >>>>>>>>>>>>>>>>>>>" + orderID);
			preparedSatement.setString(1, orderID);

			rs = preparedSatement.executeQuery();

			// crs = new CachedRowSetImpl();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (rs != null) {
				rs.close();
			}
			if (preparedSatement != null) {
				preparedSatement.close();
			}

		}

	}

	public synchronized int updateDBValue(String value, String query, String dataBase) {
		int count = 0;
		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		try {
			preparedSatement = (PreparedStatement) connection.prepareStatement(query);
			preparedSatement.setString(1, value);
			count = preparedSatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (preparedSatement != null) {
				try {
					preparedSatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return count;

	}

	public synchronized int updateDB2Parameters(String value1, String value2, String query, String dataBase) {
		int count = 0;
		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		try {
			preparedSatement = (PreparedStatement) connection.prepareStatement(query);
			preparedSatement.setString(1, value1);
			preparedSatement.setString(2, value2);
			count = preparedSatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (preparedSatement != null) {
				try {
					preparedSatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return count;

	}

	public synchronized int updateDB2Parameters(String value1, String query, String dataBase) {
		int count = 0;
		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		try {
			preparedSatement = (PreparedStatement) connection.prepareStatement(query);
			preparedSatement.setString(1, value1);
			count = preparedSatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (preparedSatement != null) {
				try {
					preparedSatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return count;

	}
	

	public synchronized int updateDB3Parameters(String value1, String value2,String value3, 
			String query, String dataBase) {
		int count = 0;
		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		try {
			preparedSatement = (PreparedStatement) connection.prepareStatement(query);
			preparedSatement.setString(1, value1);
			preparedSatement.setString(2, value2);
			preparedSatement.setString(3, value3);
			System.out.println(query);
			System.out.println(value1);
			System.out.println(value2);
			System.out.println(value3);
			count = preparedSatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (preparedSatement != null) {
				try {
					preparedSatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return count;

	}


	public synchronized void getDBValues2parametres(String parameter1, String parameter2, String querry,
			String dataBase) throws ClassNotFoundException, SQLException {
		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);

		PreparedStatement preparedSatement = null;
		ResultSet rs = null;

		try {

			preparedSatement = (PreparedStatement) connection.prepareStatement(querry);
			System.out.println("Query is >>>>>>>>>>>>>>>>>>>>>>>" + querry);
			System.out.println("parameter1 is >>>>>>>>>>>>>>>>>>>" + parameter1);
			System.out.println("parameter2 is >>>>>>>>>>>>>>>>>>>" + parameter2);
			preparedSatement.setString(1, parameter1);
			preparedSatement.setString(2, parameter2);
			// System.out.println("Query is
			// >>>>>>>>>>>>>>>>>>>>>>>"+preparedSatement.toString());
			rs = preparedSatement.executeQuery();

			// crs = new CachedRowSetImpl();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (rs != null) {
				rs.close();
			}
			if (preparedSatement != null) {
				preparedSatement.close();
			}

		}

	}

	public synchronized void getDBValues3parametres(String parameter1, String parameter2, String parameter3,
			String querry, String dataBase) throws ClassNotFoundException, SQLException {

		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		ResultSet rs = null;

		try {

			preparedSatement = (PreparedStatement) connection.prepareStatement(querry);
			System.out.println("Query is >>>>>>>>>>>>>>>>>>>>>>>" + querry);
			System.out.println("parameter1 is >>>>>>>>>>>>>>>>>>>" + parameter1);
			System.out.println("parameter2 is >>>>>>>>>>>>>>>>>>>" + parameter2);
			System.out.println("parameter3 is >>>>>>>>>>>>>>>>>>>" + parameter3);
			preparedSatement.setString(1, parameter1);
			preparedSatement.setString(2, parameter2);
			preparedSatement.setString(3, parameter3);

			rs = preparedSatement.executeQuery();

			// crs = new CachedRowSetImpl();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (rs != null) {
				rs.close();
			}
			if (preparedSatement != null) {
				preparedSatement.close();
			}

		}
	}

	public synchronized void getDBValuesRS(String orderID, String querry, String dataBase)
			throws ClassNotFoundException {

		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		ResultSet rs = null;

		try {

			preparedSatement = (PreparedStatement) connection.prepareStatement(querry);
			System.out.println("Query is >>>>>>>>>>>>>>>>>>>>>>>" + querry);
			System.out.println("OrderID is >>>>>>>>>>>>>>>>>>>" + orderID);
			preparedSatement.setString(1, orderID);

			rs = preparedSatement.executeQuery();

			while (rs.next()) {

			}

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (preparedSatement != null) {
				try {
					preparedSatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public synchronized void getDBValuesNikeiD(String orderID, String orderlineKey, String querry, String dataBase)
			throws ClassNotFoundException, SQLException {

		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		ResultSet rs = null;

		try {

			preparedSatement = (PreparedStatement) connection.prepareStatement(querry);

			preparedSatement.setString(1, orderID);
			preparedSatement.setString(2, orderID);
			preparedSatement.setString(3, orderlineKey);
			System.out.println("Query is >>>>>>>>>>>>>>>>>>>>>>>" + querry);

			rs = preparedSatement.executeQuery();
			// crs = new CachedRowSetImpl();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (rs != null) {
				rs.close();
			}
			if (preparedSatement != null) {
				preparedSatement.close();
			}

		}

	}

	public synchronized void getDbValues(String dataKey, String serviceName, String messagePattern, String query,
			String dataBase) throws ClassNotFoundException, SQLException {

		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		ResultSet rs = null;

		try {

			preparedSatement = (PreparedStatement) connection.prepareStatement(query);

			preparedSatement.setString(1, dataKey);
			preparedSatement.setString(2, serviceName);
			preparedSatement.setString(3, messagePattern);

			System.out.println("Query is >>>>>>>>>>>>>>>>>>>>>>>" + query);
			System.out
					.println("dataKey" + dataKey + "\nserviceName" + serviceName + "\nmessagePattern" + messagePattern);

			rs = preparedSatement.executeQuery();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (rs != null) {
				rs.close();
			}
			if (preparedSatement != null) {
				preparedSatement.close();
			}

		}

	}

	public synchronized void getDbValuesWithQuery(String query, String dataBase)
			throws ClassNotFoundException, SQLException {

		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		ResultSet rs = null;
		Statement st = null;
		try {

			/*
			 * preparedSatement =
			 * (PreparedStatement)connection.prepareStatement(query);
			 * 
			 * preparedSatement.setString(1, dataKey);
			 */
			st = connection.createStatement();
			rs = st.executeQuery(query);
			System.out.println("Query is >>>>>>>>>>>>>>>>>>>>>>>" + query);

			// rs = preparedSatement.executeQuery();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (rs != null) {
				rs.close();
			}
			if (preparedSatement != null) {
				preparedSatement.close();
			}

		}

	}

	public synchronized void getDbValues1(String serviceName, String messagePattern, String query, String dataBase)
			throws ClassNotFoundException, SQLException {

		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		ResultSet rs = null;

		try {

			preparedSatement = (PreparedStatement) connection.prepareStatement(query);

			preparedSatement.setString(1, serviceName);
			preparedSatement.setString(2, messagePattern);

			System.out.println("Query is >>>>>>>>>>>>>>>>>>>>>>>" + query);
			System.out.println("dataKey" + "\nserviceName" + serviceName + "\nmessagePattern" + messagePattern);

			rs = preparedSatement.executeQuery();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (rs != null) {
				rs.close();
			}
			if (preparedSatement != null) {
				preparedSatement.close();
			}

		}

	}

	public synchronized void getDBValuesPAC(String searchReference, String MessagePattern, String query,
			String dataBase) throws ClassNotFoundException, SQLException {

		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		ResultSet rs = null;

		try {
			preparedSatement = (PreparedStatement) connection.prepareStatement(query);

			preparedSatement.setString(1, searchReference);
			preparedSatement.setString(2, MessagePattern);
			System.out.println("Query is >>>>>>>>>>>>>>>>>>>>>>>" + query + "\nsearch-Reference" + searchReference
					+ "\nMessagePattern" + MessagePattern);

			rs = preparedSatement.executeQuery();
			// crs = new CachedRowSetImpl();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (rs != null) {
				rs.close();
			}
			if (preparedSatement != null) {
				preparedSatement.close();
			}

		}

	}

	public CachedRowSet getRowSet() {
		return crs;
	}

	public synchronized void deleteInventory(String itemID, String dataBase) throws ClassNotFoundException {

		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		ResultSet rs = null;

		try {

			preparedSatement = (PreparedStatement) connection.prepareStatement(NikeDOMSConstants.deleteInventory);

			preparedSatement.setString(1, itemID.trim());

			System.out.println("Query is >>>>>>>>>>>>>>>>>>>>>>>" + itemID);

			int result = preparedSatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (preparedSatement != null) {
				try {
					preparedSatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public synchronized void getDbValuesProcessQueue(String dataKey, String serviceName, String query, String dataBase)
			throws ClassNotFoundException, SQLException {

		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		ResultSet rs = null;

		try {

			preparedSatement = (PreparedStatement) connection.prepareStatement(query);

			preparedSatement.setString(1, serviceName);
			preparedSatement.setString(2, dataKey);

			System.out.println("Query is >>>>>>>>>>>>>>>>>>>>>>>" + query);

			rs = preparedSatement.executeQuery();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (rs != null) {
				rs.close();
			}
			if (preparedSatement != null) {
				preparedSatement.close();
			}

		}

	}

	public synchronized void getDbValues2(String serviceName, String datakey, String messagePattern, String query,
			String dataBase) throws ClassNotFoundException, SQLException {

		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		ResultSet rs = null;

		try {

			preparedSatement = (PreparedStatement) connection.prepareStatement(query);

			preparedSatement.setString(1, serviceName);
			preparedSatement.setString(2, datakey);
			preparedSatement.setString(3, messagePattern);

			System.out.println("Query is >>>>>>>>>>>>>>>>>>>>>>>" + query);
			System.out
					.println("serviceName" + serviceName + "\ndataKey" + datakey + "\nmessagePattern" + messagePattern);

			rs = preparedSatement.executeQuery();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection(connection);
			if (rs != null) {
				rs.close();
			}
			if (preparedSatement != null) {
				preparedSatement.close();
			}

		}
	}

	public ResultSet getDBValues2parametresReturnRS(String parameter1, String parameter2, String querry,
			String dataBase) throws ClassNotFoundException, SQLException {
		Connection connection = dbConnection.getConection(dbConfigMap, dataBase);
		PreparedStatement preparedSatement = null;
		ResultSet rs = null;

		try {

			preparedSatement = (PreparedStatement) connection.prepareStatement(querry);
			System.out.println("Query is >>>>>>>>>>>>>>>>>>>>>>>" + querry);
			System.out.println("parameter1 is >>>>>>>>>>>>>>>>>>>" + parameter1);
			System.out.println("parameter2 is >>>>>>>>>>>>>>>>>>>" + parameter2);
			preparedSatement.setString(1, parameter1);
			preparedSatement.setString(2, parameter2);
			// System.out.println("Query is
			// >>>>>>>>>>>>>>>>>>>>>>>"+preparedSatement.toString());
			rs = preparedSatement.executeQuery();

			// crs = new CachedRowSetImpl();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return rs;

	}
}
