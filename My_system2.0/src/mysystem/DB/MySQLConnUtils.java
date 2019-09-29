package mysystem.DB;

import java.sql.*;
//import java.util.logging.Logger;

public class MySQLConnUtils {
	//private static final Logger log = Logger.getLogger(MySQLConnUtils.class.getName());
	// Connect to MySQL
	public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
		String hostName = "localhost";

		String dbName = "simplehr";
		String userName = "root";
		String password = "1234";

		return getMySQLConnection(hostName, dbName, userName, password);
	}

	public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password)
			throws SQLException, ClassNotFoundException {
		// Declare the class Driver for MySQL DB
		// This is necessary with Java 5 (or older)
		// Java6 (or newer) automatically find the appropriate driver.
		// If you use Java> 5, then this line is not needed.
		Class.forName("com.mysql.jdbc.Driver");

		String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName+"?useSSL=false";

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}
}
