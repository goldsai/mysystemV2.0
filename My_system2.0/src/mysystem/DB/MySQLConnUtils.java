package mysystem.DB;

import java.sql.*;
import static mysystem.Log.*;

public class MySQLConnUtils {
	private static final String NAME_LOG_CLS = "MySQLConnUtils";

	public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
		logEntering(NAME_LOG_CLS, "getMySQLConnection");

		String hostName = "localhost";

		String dbName = "simplehr";
		String userName = "root";
		String password = "1234";

		Connection conn = getMySQLConnection(hostName, dbName, userName, password);

		logExiting(NAME_LOG_CLS, "getMySQLConnection", conn);

		return conn;
	}

	public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password)
			throws SQLException, ClassNotFoundException {
		logEntering(NAME_LOG_CLS, "getMySQLConnection", new Object[] { hostName, dbName, userName, password });
		// Declare the class Driver for MySQL DB
		// This is necessary with Java 5 (or older)
		// Java6 (or newer) automatically find the appropriate driver.
		// If you use Java> 5, then this line is not needed.
		Class.forName("com.mysql.jdbc.Driver");

		String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?useSSL=false";

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);

		logExiting(NAME_LOG_CLS, "getMySQLConnection", conn);

		return conn;
	}
}
