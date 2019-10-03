package mysystem.DB;

import java.sql.*;
import static mysystem.Log.*;

public class ODBCConnUtils {
	private static final String NAME_LOG_CLS = "ODBCConnUtils";

	// Connect to ODBC Data Source named "simplehr-ds".
	public static Connection getJdbcOdbcConnection() throws SQLException, ClassNotFoundException {
		logEntering(NAME_LOG_CLS, "getJdbcOdbcConnection");

		String odbcDataSourceName = "simplehr-ds";
		String userName = "simplehr";
		String password = "simplehr";

		Connection conn = getJdbcOdbcConnection(odbcDataSourceName, userName, password);

		logExiting(NAME_LOG_CLS, "getJdbcOdbcConnection", conn);

		return conn;
	}

	public static Connection getJdbcOdbcConnection(String odbcDataSourceName, String userName, String password)
			throws SQLException, ClassNotFoundException {
		logEntering(NAME_LOG_CLS, "getJdbcOdbcConnection", new Object[] { odbcDataSourceName, userName, password });
		// Declare the class Driver for JDBC-ODBC Bridge
		// This is necessary with Java 5 (or older)
		// Java6 (or newer) automatically find the appropriate driver.
		// If you use Java> 5, then this line is not needed.
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

		String connectionURL = "jdbc:odbc:" + odbcDataSourceName;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);

		logExiting(NAME_LOG_CLS, "getJdbcOdbcConnection", conn);

		return conn;
	}
}
