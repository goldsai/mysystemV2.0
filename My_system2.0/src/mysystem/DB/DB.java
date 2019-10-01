package mysystem.DB;

//import java.io.IOException;
import java.sql.*;
//import java.util.logging.FileHandler;
//import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
	private static final Logger log = Logger.getLogger(DB.class.getName());
	private static final String NAME_LOG_CLS = "Class DB";
	static {
		log.setLevel(Level.ALL);
		log.setUseParentHandlers(false);
//		try {
//			Handler handler = new FileHandler("%h/server.log", 0, 10);
//
//			log.addHandler(handler);
//		
//		} catch (IOException e) {
//			log.log(Level.SEVERE, "Can't  create log file handler", e);
//		}
	}
	public static Connection getConnection() {
		log.entering(NAME_LOG_CLS, "getConnection");
		Connection conn = null;
		try {
			conn = MySQLConnUtils.getMySQLConnection("localhost", "mysystem2_0", "root", "ArMyTeKMySyStEm");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			log.throwing(NAME_LOG_CLS, "getConnection", e);
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.throwing(NAME_LOG_CLS, "getConnection", e);
			e.printStackTrace();
		}

		// log.logp(Level.SEVERE, NAME_LOG_CLS, "getConnection", "error");
		return conn;
	}
}
