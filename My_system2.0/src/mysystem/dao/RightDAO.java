package mysystem.dao;

import static mysystem.DB.DB.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysystem.model.Right;
import mysystem.model.User;
import static mysystem.Log.*;

/*
 * CREATE TABLE `rights` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uri` varchar(255) NOT NULL,
  `shortName` varchar(50) NOT NULL,
  `longName` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rights_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_right` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `rights_user_id_user_idx` (`id_user`),
  KEY `rights_user_id_right_idx` (`id_right`),
  CONSTRAINT `rights_user_id_right` FOREIGN KEY (`id_right`) REFERENCES `rights` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `rights_user_id_user` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
public class RightDAO extends BaseDAO<Right> {
	/**
	 * Имя класса (с этим именем добавляются записи с лог)
	 */
	private static final String NAME_LOG_RIGHT_CLS = "RightDAO";

	private static volatile RightDAO instance;

	protected RightDAO() {
		super(NameDBTableRight, SQL_ADD_Right, SQL_UPDATE_Right);
		// TODO Auto-generated constructor stub
	}

	public static RightDAO getInstance() {
		RightDAO localInstance = instance;
		if (localInstance == null) {
			synchronized (RightDAO.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new RightDAO();
				}
			}
		}
		return localInstance;
	}

	private static final String NameDBTableRight = "rights";
	private static final String NameFieldUri = "uri";
	private static final String NameFieldShortName = "shortName";
	private static final String NameFieldLongName = "longName";
	private static final String NameFieldDesc = "description";

	private static final String NameDBTableRightsUser = "rights_user";
	private static final String NameFieldIdUser = "id_user";
	private static final String NameFieldIdRight = "id_right";

	private static final String SQL_HAS_RIGTH_USER = "SELECT " + NameFieldID + " FROM " + NameDBTableRightsUser
			+ " WHERE " + NameFieldIdUser + " = ? AND " + NameFieldIdRight + " = ?";
	private static final String SQL_ADD_RIGHTS_USER = "INSERT INTO " + NameDBTableRightsUser + " (" + NameFieldIdUser
			+ ", " + NameFieldIdRight + ") VALUES (?, ?)";
	private static final String SQL_DELETE_RIGHTS_USER = "DELETE FROM " + NameDBTableRightsUser + " WHERE "
			+ NameFieldID + " = ?";;
	private static final String SQL_ADD_Right = "INSERT INTO " + NameDBTableRight + " (" + NameFieldUri + ", "
			+ NameFieldShortName + ", " + NameFieldLongName + ", " + NameFieldDesc + ") VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE_Right = "UPDATE " + NameDBTableRight + " SET " + NameFieldUri + " = ?,"
			+ NameFieldShortName + " = ?," + NameFieldLongName + " = ?," + NameFieldDesc + "= ? WHERE id = ?";

	private static final String SQL_GET_BY_IDUser = "SELECT " + NameDBTableRight + "." + NameFieldID + ", "
			+ NameDBTableRight + "." + NameFieldUri + ", " + NameDBTableRight + "." + NameFieldShortName + ", "
			+ NameDBTableRight + "." + NameFieldLongName + ", " + NameDBTableRight + "." + NameFieldDesc + " FROM "
			+ NameDBTableRight + ", " + NameDBTableRightsUser + " WHERE " + NameDBTableRightsUser + "."
			+ NameFieldIdRight + "=" + NameDBTableRight + "." + NameFieldID + " and " + NameDBTableRightsUser + "."
			+ NameFieldIdUser + "=?";

	private static final String SQL_GET_BY_IDRight = "SELECT " + NameDBTableRight + "." + NameFieldID + ", "
			+ NameDBTableRight + "." + NameFieldUri + ", " + NameDBTableRight + "." + NameFieldShortName + ", "
			+ NameDBTableRight + "." + NameFieldLongName + ", " + NameDBTableRight + "." + NameFieldDesc + " FROM "
			+ NameDBTableRight + ", " + NameDBTableRightsUser + " WHERE " + NameDBTableRightsUser + "."
			+ NameFieldIdRight + "=" + NameDBTableRight + "." + NameFieldID + " and " + NameDBTableRight + "."
			+ NameFieldID + "=?";

	public List<User> getUserForRight(long idRight) {
		logEntering(NAME_LOG_RIGHT_CLS, "getRightForUser");
		List<User> list = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_IDRight)) {

			preparedStatement.setLong(1, idRight);
			logp(NAME_LOG_RIGHT_CLS, "getRightForUser", "preparedStatement: '" + preparedStatement + "'");
			ResultSet rs = preparedStatement.executeQuery();
			UserDAO userDAO = UserDAO.getInstance();

			while (rs.next())
				list.add(userDAO.getModelByID(rs));

		} catch (SQLException e) {
			printSQLException(NAME_LOG_RIGHT_CLS, e, "getRightForUser");
		}
		logExiting(NAME_LOG_RIGHT_CLS, "getRightForUser", list);
		return list;
	}

	public boolean hasRightForUser(long id_user, long id_right) {
		logEntering(NAME_LOG_RIGHT_CLS, "hasRightForUser");
		boolean has = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_HAS_RIGTH_USER)) {

			preparedStatement.setLong(1, id_user);
			preparedStatement.setLong(2, id_right);
			logp(NAME_LOG_RIGHT_CLS, "hasRightForUser", "preparedStatement: '" + preparedStatement + "'");
			ResultSet rs = preparedStatement.executeQuery();

			has = rs.next();

		} catch (SQLException e) {
			printSQLException(NAME_LOG_RIGHT_CLS, e, "hasRightForUser");
		}
		logExiting(NAME_LOG_RIGHT_CLS, "hasRightForUser", has);
		return has;
	}

	public boolean addNewRightForUser(Connection conn, List<Right> newRight, List<Right> dbRight, long id_user) {
		logEntering(NAME_LOG_RIGHT_CLS, "addNewRightForUser");
		
		boolean ok = true;
		
		//добавляю в БД новые прова
		try (PreparedStatement preparedStatement = conn.prepareStatement(SQL_ADD_RIGHTS_USER)) {
			for (Right right : newRight)
				if (dbRight.indexOf(right) == -1) {

					preparedStatement.setLong(1, id_user);
					preparedStatement.setLong(2, right.getId());

					logp(NAME_LOG_RIGHT_CLS, "addNewRightForUser", "preparedStatement: '" + preparedStatement + "'");
					ok = preparedStatement.executeUpdate() > 0 && ok;
					logp(NAME_LOG_RIGHT_CLS, "addNewRightForUser", "Ok: " + ok);

				}

		} catch (SQLException e) {
			printSQLException(NAME_LOG_RIGHT_CLS, e, "addNewRightForUser");
		}

		logExiting(NAME_LOG_RIGHT_CLS, "addNewRightForUser", ok);
		return ok;
	}
	
	public boolean deleteOldRightForUser(Connection conn, List<Right> newRight, List<Right> dbRight, long id_user) {
		logEntering(NAME_LOG_RIGHT_CLS, "deleteOldRightForUser");
		
		boolean ok = true;
		
		// Удаляю из БД отсутствующие поля
		try (PreparedStatement prepStatement = conn.prepareStatement(SQL_DELETE_RIGHTS_USER)) {

			for (Right right : dbRight)
				if (newRight.indexOf(right) == -1) {
					prepStatement.setLong(1, right.getId());

					logp(NAME_LOG_RIGHT_CLS, "deleteOldRightForUser", "prepStatement: '" + prepStatement + "'");
					ok = prepStatement.executeUpdate() > 0 && ok;
					logp(NAME_LOG_RIGHT_CLS, "deleteOldRightForUser", "Ok: " + ok);
				}

		} catch (SQLException e) {
			printSQLException(NAME_LOG_RIGHT_CLS, e, "deleteOldRightForUser");
		}

		logExiting(NAME_LOG_RIGHT_CLS, "deleteOldRightForUser", ok);
		return ok;
	}

	public boolean updateRightForUser(long id_user, List<Right> lRight) {
		logEntering(NAME_LOG_RIGHT_CLS, "updateRightForUser");
		
		boolean ok = true;
		
		List<Right> dbRight = getRightForUser(id_user);//список прав пользователя из БД
		
		try (Connection connection = getConnection()) {
			
			ok= addNewRightForUser(connection, lRight, dbRight, id_user) && ok;
			ok= deleteOldRightForUser(connection, lRight, dbRight, id_user) && ok;

		} catch (SQLException e) {
			printSQLException(NAME_LOG_RIGHT_CLS, e, "updateRightForUser");
		}
	
		logExiting(NAME_LOG_RIGHT_CLS, "updateRightForUser", ok);
		return ok;
	}

	public List<Right> getRightForUser(long idUser) {
		logEntering(NAME_LOG_RIGHT_CLS, "getRightForUser");
		List<Right> list = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_IDUser)) {

			preparedStatement.setLong(1, idUser);
			logp(NAME_LOG_RIGHT_CLS, "getRightForUser", "preparedStatement: '" + preparedStatement + "'");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
				list.add(getModelByID(rs));

		} catch (SQLException e) {
			printSQLException(NAME_LOG_RIGHT_CLS, e, "getRightForUser");
		}
		logExiting(NAME_LOG_RIGHT_CLS, "getRightForUser", list);
		return list;
	}

	@Override
	protected Right getModelByID(ResultSet rs) {
		// TODO Auto-generated method stub
		logEntering(NAME_LOG_RIGHT_CLS, "getModelByID", rs);
		long id = 0;// = rs.getLong(NameFieldID);
		String shortName = "";
		String longName = "";
		String uri = "";
		String desc = "";
		try {
			id = rs.getLong(NameFieldID);
			shortName = rs.getString(NameFieldShortName);
			longName = rs.getString(NameFieldLongName);
			uri = rs.getString(NameFieldUri);
			desc = rs.getString(NameFieldDesc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(NAME_LOG_RIGHT_CLS, e, "getModelByID");
		}
		Right right = Right.getInstance(id, uri, shortName, longName, desc);
		logExiting(NAME_LOG_RIGHT_CLS, "getModelByID", right);
		return right;
	}

	@Override
	protected void setDataForAddModel(PreparedStatement preparedStatement, Right model) {
		// TODO Auto-generated method stub
		logEntering(NAME_LOG_RIGHT_CLS, "setDataForAddModel", new Object[] { preparedStatement, model });
		// "INSERT INTO " + NameDBTableRight + " (" + NameFieldUri + ", "
		// + NameFieldShortName + ", "+NameFieldLongName+ ", "+NameFieldDesc+ ") VALUES
		// (?, ?, ?, ?)";

		try {
			preparedStatement.setString(1, model.getUri());
			preparedStatement.setString(2, model.getShortName());
			preparedStatement.setString(3, model.getLongName());
			preparedStatement.setString(4, model.getDesc());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(NAME_LOG_RIGHT_CLS, e, "setDataForAddModel");
		}
		logExiting(NAME_LOG_RIGHT_CLS, "setDataForAddModel");
	}

	@Override
	protected void setDataForUpdateModel(PreparedStatement preparedStatement, Right model) {
		// TODO Auto-generated method stub
		logEntering(NAME_LOG_RIGHT_CLS, "setDataForUpdateModel", new Object[] { preparedStatement, model });
		// "UPDATE " + NameDBTableRight + " SET " + NameFieldUri + " = ?,"
		// + NameFieldShortName + " = ?,"+NameFieldLongName + " = ?,"+NameFieldDesc+ "=
		// ? WHERE id = ?";
		try {
			preparedStatement.setString(1, model.getUri());
			preparedStatement.setString(2, model.getShortName());
			preparedStatement.setString(3, model.getLongName());
			preparedStatement.setString(4, model.getDesc());
			preparedStatement.setLong(5, model.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(NAME_LOG_RIGHT_CLS, e, "setDataForUpdateModel");
		}
		logExiting(NAME_LOG_RIGHT_CLS, "setDataForUpdateModel");
	}
}
