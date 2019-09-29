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
	@Override
	protected void runTransactionsUpdateModel(Right model, Connection connection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void deleteModelDeleteModel(long id, Connection connection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runTransactionsAddModel(Right model, boolean setId, Connection connection) {
		// TODO Auto-generated method stub
		
	}

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
		logEntering("getRightForUser");
		List<User> list = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_IDRight)) {

			preparedStatement.setLong(1, idRight);
			logp("getRightForUser", "preparedStatement: '" + preparedStatement + "'");
			ResultSet rs = preparedStatement.executeQuery();
			UserDAO userDAO=UserDAO.getInstance();
			
			while (rs.next())
				list.add(userDAO.getModelByID(rs));

		} catch (SQLException e) {
			printSQLException(e, "getRightForUser");
		}
		return list;
	}
	public List<Right> getRightForUser(long idUser) {
		logEntering("getRightForUser");
		List<Right> list = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_IDUser)) {

			preparedStatement.setLong(1, idUser);
			logp("getRightForUser", "preparedStatement: '" + preparedStatement + "'");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
				list.add(getModelByID(rs));

		} catch (SQLException e) {
			printSQLException(e, "getRightForUser");
		}
		return list;
	}

	@Override
	protected Right getModelByID(ResultSet rs) {
		// TODO Auto-generated method stub
		logEntering("getModelByID", rs);
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
			printSQLException(e, "getModelByID");
		}

		return Right.getInstance(id, uri, shortName, longName, desc);
	}

	@Override
	protected void setDataForAddModel(PreparedStatement preparedStatement, Right model) {
		// TODO Auto-generated method stub
		logEntering("setDataForAddModel", new Object[] { preparedStatement, model });
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
			printSQLException(e, "setDataForAddModel");
		}
	}

	@Override
	protected void setDataForUpdateModel(PreparedStatement preparedStatement, Right model) {
		// TODO Auto-generated method stub
		logEntering("setDataForUpdateModel", new Object[] { preparedStatement, model });
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
			printSQLException(e, "setDataForUpdateModel");
		}
	}
}
