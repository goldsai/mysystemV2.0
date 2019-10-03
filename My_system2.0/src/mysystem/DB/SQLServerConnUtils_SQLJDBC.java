package mysystem.DB;

import java.sql.*;
import static mysystem.Log.*;

public class SQLServerConnUtils_SQLJDBC {
	private static final String NAME_LOG_CLS = "SQLServerConnUtils_SQLJDBC";

	// Connect to SQLServer
	// (Using JDBC Driver: SQLJDBC)
	public static Connection getSQLServerConnection() throws SQLException, ClassNotFoundException {
		logEntering(NAME_LOG_CLS, "getSQLServerConnection");

		String hostName = "localhost";
		String sqlInstanceName = "SQLEXPRESS";
		String database = "simplehr";
		String userName = "sa";
		String password = "1234";

		Connection conn = getSQLServerConnection(hostName, sqlInstanceName, database, userName, password);

		logExiting(NAME_LOG_CLS, "getSQLServerConnection", conn);

		return conn;
	}

	//
	// Connect to SQLServer & using SQLJDBC Library.
	public static Connection getSQLServerConnection(String hostName, String sqlInstanceName, String database,
			String userName, String password) throws ClassNotFoundException, SQLException {
		logEntering(NAME_LOG_CLS, "getSQLServerConnection",
				new Object[] { hostName, sqlInstanceName, database, userName, password });
		// Declare the class Driver for SQLServer DB
		// This is necessary with Java 5 (or older)
		// Java6 (or newer) automatically find the appropriate driver.
		// If you use Java> 5, then this line is not needed.
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		// jdbc:sqlserver://ServerIp:1433/SQLEXPRESS;databaseName=simplehr
		String connectionURL = "jdbc:sqlserver://" + hostName + ":1433" + ";instance=" + sqlInstanceName
				+ ";databaseName=" + database;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);

		logExiting(NAME_LOG_CLS, "getSQLServerConnection", conn);

		return conn;
	}

}
