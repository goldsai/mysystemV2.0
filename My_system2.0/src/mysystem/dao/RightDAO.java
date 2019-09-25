package mysystem.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mysystem.model.Right;
import mysystem.model.TypeDoc;

/*
 * CREATE TABLE `rights` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uri` varchar(255) NOT NULL,
  `shortName` varchar(50) NOT NULL,
  `longName` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 */
public class RightDAO extends BaseDAO<Right> {
	public RightDAO() {
		super(NameDBTableRight, SQL_ADD_USER, SQL_UPDATE_USER);
		// TODO Auto-generated constructor stub
	}

	private static final String NameDBTableRight = "rights";
	private static final String NameFieldUri = "uri";
	private static final String NameFieldShortName = "shortName";
	private static final String NameFieldLongName = "longName";
	private static final String NameFieldDesc = "description";

	private static final String SQL_ADD_USER = "INSERT INTO " + NameDBTableRight + " (" + NameFieldUri + ", "
			+ NameFieldShortName + ", " + NameFieldLongName + ", " + NameFieldDesc + ") VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE_USER = "UPDATE " + NameDBTableRight + " SET " + NameFieldUri + " = ?,"
			+ NameFieldShortName + " = ?," + NameFieldLongName + " = ?," + NameFieldDesc + "= ? WHERE id = ?";

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

		return new Right(id, uri, shortName, longName, desc);
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
