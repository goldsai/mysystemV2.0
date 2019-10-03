package mysystem.DB;

import java.sql.*;
import static mysystem.Log.*;

public class OracleConnUtils {
	private static final String NAME_LOG_CLS = "OracleConnUtils";

	// Connect to Oracle.
	public static Connection getOracleConnection() throws SQLException, ClassNotFoundException {
		logEntering(NAME_LOG_CLS, "getOracleConnection");

		String hostName = "localhost";
		String sid = "db11g";
		String userName = "simplehr";
		String password = "1234";

		Connection conn = getOracleConnection(hostName, sid, userName, password);

		logExiting(NAME_LOG_CLS, "getOracleConnection", conn);

		return conn;
	}

	public static Connection getOracleConnection(String hostName, String sid, String userName, String password)
			throws ClassNotFoundException, SQLException {
		logEntering(NAME_LOG_CLS, "getOracleConnection", new Object[] { hostName, sid, userName, password });
		// Declare the class Driver for ORACLE DB
		// This is necessary with Java 5 (or older)
		// Java6 (or newer) automatically find the appropriate driver.
		// If you use Java> 5, then this line is not needed.
		Class.forName("oracle.jdbc.driver.OracleDriver");

		String connectionURL = "jdbc:oracle:thin:@" + hostName + ":1521:" + sid;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);

		logExiting(NAME_LOG_CLS, "getOracleConnection", conn);

		return conn;
	}
}
