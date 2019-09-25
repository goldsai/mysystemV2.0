package mysystem.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mysystem.model.TypeDoc;

public class TypeDocDAO extends BaseDAO<TypeDoc> {
	public TypeDocDAO() {
		super(NameDBTableTypeDoc, SQL_ADD_USER, SQL_UPDATE_USER);
		// TODO Auto-generated constructor stub
	}

	private static final String NameDBTableTypeDoc = "type_doc";
	private static final String NameFieldDirPath = "dirPath";
	private static final String NameFieldShortName = "shortName";
	private static final String NameFieldLongName = "longName";
	private static final String NameFieldDesc = "description";

	private static final String SQL_ADD_USER = "INSERT INTO " + NameDBTableTypeDoc + " (" + NameFieldDirPath + ", "
			+ NameFieldShortName + ", " + NameFieldLongName + ", " + NameFieldDesc + ") VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE_USER = "UPDATE " + NameDBTableTypeDoc + " SET " + NameFieldDirPath + " = ?,"
			+ NameFieldShortName + " = ?," + NameFieldLongName + " = ?," + NameFieldDesc + "= ? WHERE id = ?";

	@Override
	protected TypeDoc getModelByID(ResultSet rs) {
		// TODO Auto-generated method stub
		logEntering("getModelByID", rs);
		long id = 0;// = rs.getLong(NameFieldID);
		String shortName = "";
		String longName = "";
		String dirPath = "";
		String desc = "";
		try {
			id = rs.getLong(NameFieldID);
			shortName = rs.getString(NameFieldShortName);
			longName = rs.getString(NameFieldLongName);
			dirPath=rs.getString(NameFieldDirPath);
			desc=rs.getString(NameFieldDesc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e, "getModelByID");
		}

		return new TypeDoc(id,dirPath,shortName,longName,desc);
		
	}

	@Override
	protected void setDataForAddModel(PreparedStatement preparedStatement, TypeDoc model) {
		// TODO Auto-generated method stub
		logEntering("setDataForAddModel", new Object[]{preparedStatement, model});
		//"INSERT INTO " + NameDBTableTypeDoc + " (" + NameFieldDirPath + ", "
		//	+ NameFieldShortName + ", " + NameFieldLongName + ", " + NameFieldDesc + ") VALUES (?, ?, ?, ?)";
        try {
        	preparedStatement.setString(1, model.getDirPath());
        	preparedStatement.setString(2, model.getShortName());
        	preparedStatement.setString(3, model.getLongName());
        	preparedStatement.setString(4, model.getDesc());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e, "setDataForAddModel");
		}
	}

	@Override
	protected void setDataForUpdateModel(PreparedStatement preparedStatement, TypeDoc model) {
		// TODO Auto-generated method stub
		logEntering("setDataForUpdateModel", new Object[]{preparedStatement, model});
		//"UPDATE " + NameDBTableTypeDoc + " SET " + NameFieldDirPath + " = ?,"
		//	+ NameFieldShortName + " = ?," + NameFieldLongName + " = ?," + NameFieldDesc + "= ? WHERE id = ?";
        try {
        	preparedStatement.setString(1, model.getDirPath());
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
