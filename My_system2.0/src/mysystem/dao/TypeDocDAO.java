package mysystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static mysystem.Log.*;
import mysystem.model.TypeDoc;

public class TypeDocDAO extends BaseDAO<TypeDoc> {
	/**
	 * Имя класса (с этим именем добавляются записи с лог)
	 */
	private static final String NAME_LOG_TYPEDOC_CLS = "TypeDocDAO";
	private static volatile TypeDocDAO instance;
	public static TypeDocDAO getInstance() {
		TypeDocDAO localInstance = instance;
		if (localInstance == null) {
			synchronized (TypeDocDAO.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new TypeDocDAO();
				}
			}
		}
		return localInstance;
	}
	protected TypeDocDAO() {
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
		logEntering(NAME_LOG_TYPEDOC_CLS,"getModelByID", rs);
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
			printSQLException(NAME_LOG_TYPEDOC_CLS,e, "getModelByID");
		}
		TypeDoc typeDoc=TypeDoc.getInstance(id,dirPath,shortName,longName,desc);
		logExiting(NAME_LOG_TYPEDOC_CLS, "getModelByID", typeDoc);
		return  typeDoc;
		
	}

	@Override
	protected void setDataForAddModel(PreparedStatement preparedStatement, TypeDoc model) {
		// TODO Auto-generated method stub
		logEntering(NAME_LOG_TYPEDOC_CLS,"setDataForAddModel", new Object[]{preparedStatement, model});
		//"INSERT INTO " + NameDBTableTypeDoc + " (" + NameFieldDirPath + ", "
		//	+ NameFieldShortName + ", " + NameFieldLongName + ", " + NameFieldDesc + ") VALUES (?, ?, ?, ?)";
        try {
        	preparedStatement.setString(1, model.getDirPath());
        	preparedStatement.setString(2, model.getShortName());
        	preparedStatement.setString(3, model.getLongName());
        	preparedStatement.setString(4, model.getDesc());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(NAME_LOG_TYPEDOC_CLS,e, "setDataForAddModel");
		}
        logExiting(NAME_LOG_TYPEDOC_CLS, "setDataForAddModel");
	}

	@Override
	protected void setDataForUpdateModel(PreparedStatement preparedStatement, TypeDoc model) {
		// TODO Auto-generated method stub
		logEntering(NAME_LOG_TYPEDOC_CLS,"setDataForUpdateModel", new Object[]{preparedStatement, model});
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
			printSQLException(NAME_LOG_TYPEDOC_CLS,e, "setDataForUpdateModel");
		}
		logExiting(NAME_LOG_TYPEDOC_CLS, "setDataForUpdateModel");
	}
	@Override
	protected void runTransactionsAddModel(TypeDoc model, boolean setId, Connection connection) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void runTransactionsUpdateModel(TypeDoc model, Connection connection) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void deleteModelDeleteModel(long id, Connection connection) {
		// TODO Auto-generated method stub
		
	}

}
