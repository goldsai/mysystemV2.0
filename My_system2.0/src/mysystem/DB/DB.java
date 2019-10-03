package mysystem.DB;

import java.sql.*;
import static mysystem.Log.*;
public class DB {

	private static final String NAME_LOG_CLS = "Class DB";

	public static Connection getConnection() {
		logEntering(NAME_LOG_CLS, "getConnection");
		Connection conn = null;
		try {
			conn = MySQLConnUtils.getMySQLConnection("localhost", "mysystem2_0", "root", "ArMyTeKMySyStEm2019");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logThrowing(NAME_LOG_CLS, "getConnection", e);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(NAME_LOG_CLS,e, "getConnection");
			
		}

		
		logExiting(NAME_LOG_CLS, "getConnection", conn);
		return conn;
	}
}
