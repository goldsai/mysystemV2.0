package mysystem.DB;

import java.sql.*;

public class ODBCConnUtils {
	// Connect to ODBC Data Source named "simplehr-ds".
	public static Connection getJdbcOdbcConnection() throws SQLException, ClassNotFoundException {
		String odbcDataSourceName = "simplehr-ds";
		String userName = "simplehr";
		String password = "simplehr";
		return getJdbcOdbcConnection(odbcDataSourceName, userName, password);
	}

	public static Connection getJdbcOdbcConnection(String odbcDataSourceName, String userName, String password)
			throws SQLException, ClassNotFoundException {

		// Declare the class Driver for JDBC-ODBC Bridge
		// This is necessary with Java 5 (or older)
		// Java6 (or newer) automatically find the appropriate driver.
		// If you use Java> 5, then this line is not needed.
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

		String connectionURL = "jdbc:odbc:" + odbcDataSourceName;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}
}
