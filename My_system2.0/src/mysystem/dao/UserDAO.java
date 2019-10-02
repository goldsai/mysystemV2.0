package mysystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import mysystem.model.Right;
import mysystem.model.User;
import static mysystem.Log.*;

/*
 * CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `pass` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 */
public class UserDAO extends BaseDAO<User> {
	/**
	 * Имя класса (с этим именем добавляются записи с лог)
	 */
	private static final String NAME_LOG_USER_CLS = "UserDAO";

	private static final String NameDBTableUser = "users";
	private static final String NameFieldLogin = "login";
	private static final String NameFieldPass = "Pass";
	private static final String SQL_ADD_USER = "INSERT INTO " + NameDBTableUser + " (" + NameFieldLogin + ", "
			+ NameFieldPass + ") VALUES (?, ?)";
	private static final String SQL_UPDATE_USER = "UPDATE " + NameDBTableUser + " SET " + NameFieldLogin + " = ?,"
			+ NameFieldPass + "= ? WHERE id = ?";

	private static volatile UserDAO instance;

	public static UserDAO getInstance() {
		UserDAO localInstance = instance;
		if (localInstance == null) {
			synchronized (UserDAO.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new UserDAO();
				}
			}
		}
		return localInstance;
	}

	protected UserDAO() {
		super(NameDBTableUser, SQL_ADD_USER, SQL_UPDATE_USER);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected User getModelByID(ResultSet rs) {
		// TODO Auto-generated method stub
		logEntering(NAME_LOG_USER_CLS, "getModelByID", rs);
		long id = 0;// = rs.getLong(NameFieldID);
		String login = "";
		String pass = "";
		try {
			id = rs.getLong(NameFieldID);
			login = rs.getString(NameFieldLogin);
			pass = rs.getString(NameFieldPass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(NAME_LOG_USER_CLS, e, "getModelByID");
		}
		User user = User.storageFind(id);
		if (user == null) {
			RightDAO dao = RightDAO.getInstance();

			user = User.getInstance(id, login, pass, dao.getRightForUser(id));
		}
		logExiting(NAME_LOG_USER_CLS, "getModelByID", user);
		return user;
	}

	@Override
	protected void afterAddModel(User model, boolean setId, Connection connection, boolean rowInserted) {
		// TODO Auto-generated method stub
		RightDAO dao = RightDAO.getInstance();
		List<Right> dbRight = dao.getRightForUser(model.getId());//список прав пользователя из БД
		
		dao.addNewRightForUser(connection, model.getRights(), dbRight, model.getId());
		dao.deleteOldRightForUser(connection, model.getRights(), dbRight, model.getId());
	}

	@Override
	protected void setDataForAddModel(PreparedStatement preparedStatement, User model) {
		// TODO Auto-generated method stub
		// "INSERT INTO " + NameDBTableUser + " (" + NameFieldLogin + ", "
		// + NameFieldPass + ") VALUES (?, ?)";
		logEntering(NAME_LOG_USER_CLS, "setDataForAddModel", new Object[] { preparedStatement, model });
		try {
			preparedStatement.setString(1, model.getLogin());
			preparedStatement.setString(2, model.getPass());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(NAME_LOG_USER_CLS, e, "setDataForAddModel");
		}
		logExiting(NAME_LOG_USER_CLS, "setDataForAddModel");
	}

	@Override
	protected void setDataForUpdateModel(PreparedStatement preparedStatement, User model) {
		// TODO Auto-generated method stub
//"UPDATE " + NameDBTableUser + " SET " + NameFieldLogin + " = ?,"
//+ NameFieldPass + "= ? WHERE id = ?";
		logEntering(NAME_LOG_USER_CLS, "setDataForUpdateModel", new Object[] { preparedStatement, model });
		try {
			preparedStatement.setString(1, model.getLogin());
			preparedStatement.setString(2, model.getPass());
			preparedStatement.setLong(3, model.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(NAME_LOG_USER_CLS, e, "setDataForUpdateModel");
		}
		logExiting(NAME_LOG_USER_CLS, "setDataForUpdateModel");
	}

}
