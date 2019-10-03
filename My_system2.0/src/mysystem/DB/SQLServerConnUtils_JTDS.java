package mysystem.DB;

import java.sql.*;
import static mysystem.Log.*;

public class SQLServerConnUtils_JTDS {
	private static final String NAME_LOG_CLS = "SQLServerConnUtils_JTDS";

	// Connect to SQLServer
	// (Using JDBC Driver of JTDS library)
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

	// Connect to SQLServer & using JTDS library
	public static Connection getSQLServerConnection(String hostName, String sqlInstanceName, String database,
			String userName, String password) throws ClassNotFoundException, SQLException {
		logEntering(NAME_LOG_CLS, "getSQLServerConnection",
				new Object[] { hostName, sqlInstanceName, database, userName, password });
		// Declare the class Driver for SQLServer DB
		// This is necessary with Java 5 (or older)
		// Java6 (or newer) automatically find the appropriate driver.
		// If you use Java> 5, then this line is not needed.
		Class.forName("net.sourceforge.jtds.jdbc.Driver");

		// Example:
		// jdbc:jtds:sqlserver://localhost:1433/simplehr;instance=SQLEXPRESS
		String connectionURL = "jdbc:jtds:sqlserver://" + hostName + ":1433/" + database + ";instance="
				+ sqlInstanceName;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		
		logExiting(NAME_LOG_CLS, "getSQLServerConnection", conn);
		
		return conn;
	}
}
