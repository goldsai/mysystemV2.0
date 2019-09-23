package mysystem.DB;

import java.sql.*;
import java.util.logging.Logger;

public class DB {
	private static final Logger log = Logger.getLogger(DB.class.getName());
	private static final String NAME_LOG_CLS = "Class DB";
	
	public static Connection getConnection() throws SQLException {
		log.entering(NAME_LOG_CLS, "getConnection");
		try {
			return MySQLConnUtils.getMySQLConnection("localhost", "mysystem2_0", "root", "MCnhFyyBr");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			log.throwing(NAME_LOG_CLS, "getConnection", e);
			e.printStackTrace();
		}// catch (SQLException e) {
//			// TODO Auto-generated catch block
//			log.throwing(NAME_LOG_CLS, "getConnection", e);
//			e.printStackTrace();
//		}
		
		//log.logp(Level.SEVERE, NAME_LOG_CLS, "getConnection", "error");
		return null;
	}
}
