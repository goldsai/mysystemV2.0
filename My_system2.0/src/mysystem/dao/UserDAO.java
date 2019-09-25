package mysystem.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mysystem.model.User;

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
	private static final String NameDBTableUser = "users";
	private static final String NameFieldLogin = "login";
	private static final String NameFieldPass = "Pass";
	private static final String SQL_ADD_USER = "INSERT INTO " + NameDBTableUser + " (" + NameFieldLogin + ", "
			+ NameFieldPass + ") VALUES (?, ?)";
	private static final String SQL_UPDATE_USER = "UPDATE " + NameDBTableUser + " SET " + NameFieldLogin + " = ?,"
			+ NameFieldPass + "= ? WHERE id = ?";

	public UserDAO() {
		super(NameDBTableUser, SQL_ADD_USER, SQL_UPDATE_USER);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected User getModelByID(ResultSet rs) {
		// TODO Auto-generated method stub
		logEntering("getModelByID", rs);
		long id = 0;// = rs.getLong(NameFieldID);
		String login = "";
		String pass = "";
		try {
			id = rs.getLong(NameFieldID);
			login = rs.getString(NameFieldLogin);
			pass = rs.getString(NameFieldPass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e, "getModelByID");
		}
		User user = User.storageFind(id);
		if (user == null) {
			RightDAO dao = new RightDAO();

			user = User.getInstance(id, login, pass, dao.getRightForUser(id));
		}
		return user;
	}

	@Override
	protected void setDataForAddModel(PreparedStatement preparedStatement, User model) {
		// TODO Auto-generated method stub
		// "INSERT INTO " + NameDBTableUser + " (" + NameFieldLogin + ", "
		// + NameFieldPass + ") VALUES (?, ?)";
		logEntering("setDataForAddModel", new Object[] { preparedStatement, model });
		try {
			preparedStatement.setString(1, model.getLogin());
			preparedStatement.setString(2, model.getPass());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e, "setDataForAddModel");
		}

	}

	@Override
	protected void setDataForUpdateModel(PreparedStatement preparedStatement, User model) {
		// TODO Auto-generated method stub
//"UPDATE " + NameDBTableUser + " SET " + NameFieldLogin + " = ?,"
//+ NameFieldPass + "= ? WHERE id = ?";
		logEntering("setDataForUpdateModel", new Object[] { preparedStatement, model });
		try {
			preparedStatement.setString(1, model.getLogin());
			preparedStatement.setString(2, model.getPass());
			preparedStatement.setLong(3, model.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e, "setDataForUpdateModel");
		}
	}

}
