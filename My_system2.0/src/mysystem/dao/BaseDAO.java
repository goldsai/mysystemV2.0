package mysystem.dao;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//import mysystem.DB.DB;
import mysystem.model.BaseModel;

import static mysystem.DB.DB.*;

public abstract class BaseDAO<T extends BaseModel> {
	private static final Logger log = Logger.getLogger(BaseDAO.class.getName());
	private static final String NAME_LOG_CLS = "BaseDB";
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
	/**
	 * Хранит наименование таблицы базы данных в которая хранит данные модели
	 */
	private String NAME_DB_TABLE;

	/**
	 * Инициализирует переменные запросов к базе данных
	 */
	protected void initSQLString() {
		SQL_GET_BY_ID = "SELECT * FROM " + NAME_DB_TABLE + "WHERE " + NameFieldID + "=?";
		SQL_GET_ALL = "SELECT * FROM " + NAME_DB_TABLE;
		SQL_DELETE_MODEL = "DELETE FROM " + NAME_DB_TABLE + " WHERE " + NameFieldID + " = ?";
	}

	/**
	 * @param nameDBTable    имя таблицы базы данных, в которой сохроняется модель
	 *                       данных
	 * @param sqlAddModel    SQL запрос на вставку данных в таблицу базы данных.
	 *                       Строка вида:
	 *                       {@code INSERT INTO users" + " (name, email, country) VALUES " + " (?, ?, ?)";}
	 * @param sqlUpdateModel SQL запрос на обновление данных в таблице базы данных.
	 *                       Строка вида:
	 *                       {@code update users set name = ?,email= ?, country =? where id = ?";}
	 */
	public BaseDAO(String nameDBTable, String sqlAddModel, String sqlUpdateModel) {
		NAME_DB_TABLE = nameDBTable;

		SQL_ADD_MODEL = sqlAddModel;
		SQL_UPDATE_MODEL = sqlUpdateModel;
		initSQLString();
	}

	protected static final String NameFieldID = "id";

	private String SQL_GET_BY_ID;

	// >>>>>>> "select * from users";
	private String SQL_GET_ALL;

	// !!!!!!!! "INSERT INTO users" + " (name, email, country) VALUES " + " (?, ?,
	// ?)";
	private String SQL_ADD_MODEL;

	// !!!!!!!! "update users set name = ?,email= ?, country =? where id = ?"
	private String SQL_UPDATE_MODEL;

	// >>>>>>>> "delete from users where id = ?"
	private String SQL_DELETE_MODEL;

	protected void logEntering(String sourceMethod) {
		log.entering(NAME_LOG_CLS, sourceMethod);
	}

	protected void logEntering(String sourceMethod, Object params) {
		log.entering(NAME_LOG_CLS, sourceMethod, params);
	}

	protected void logEntering(String sourceMethod, Object[] params) {
		log.entering(NAME_LOG_CLS, sourceMethod, params);
	}

	protected void logp(String sourceMethod, String msg) {
		log.logp(Level.SEVERE, NAME_LOG_CLS, sourceMethod, msg);
	}

	/**
	 * Метод обрабатывает ответ от базы данных и возвращает модель заполненую
	 * данными
	 * 
	 * @param rs
	 * @return
	 * 
	 */

	protected abstract T getModelByID(ResultSet rs);

	public T getByID(long id) {
		logEntering("getByID", id);
		T model = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID);) {
			preparedStatement.setLong(1, id);
			logp("getByID", "preparedStatement: '" + preparedStatement + "'");
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next())
				model = getModelByID(rs);

		} catch (SQLException e) {
			printSQLException(e, "getByID");
		}
		return model;
	}

	public List<T> getModelsAll() {
		logEntering("getAll");
		List<T> list = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL)) {
			logp("getAll", "preparedStatement: '" + preparedStatement + "'");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
				list.add(getModelByID(rs));

		} catch (SQLException e) {
			printSQLException(e, "getAll");
		}
		return list;
	}

	protected abstract void setDataForAddModel(PreparedStatement preparedStatement, T model);

	public boolean addModel(T model) {
		logEntering("addModel", model);
		boolean rowInserted = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_MODEL)) {
			setDataForAddModel(preparedStatement, model);
			logp("addModel", "preparedStatement: '" + preparedStatement + "'");
			rowInserted = preparedStatement.executeUpdate() > 0;

		} catch (SQLException e) {
			printSQLException(e, "addModel");
		}
		return rowInserted;
	}

	protected abstract void setDataForUpdateModel(PreparedStatement preparedStatement, T model);

	public boolean updateModel(T model) {
		logEntering("updateModel", model);
		boolean rowUpdated = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_MODEL)) {
			setDataForUpdateModel(preparedStatement, model);
			logp("updateModel", "preparedStatement: '" + preparedStatement + "'");
			rowUpdated = preparedStatement.executeUpdate() > 0;

		} catch (SQLException e) {
			printSQLException(e, "updateModel");
		}
		return rowUpdated;
	}

	public boolean deleteModel(T model) {
		logEntering("deleteModel", model);
		boolean rowDeleted = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_MODEL)) {

			preparedStatement.setLong(1, model.getId());
			logp("deleteModel", "preparedStatement: '" + preparedStatement + "'");
			rowDeleted = preparedStatement.executeUpdate() > 0;

		} catch (SQLException e) {
			printSQLException(e, "deleteModel");
		}
		return rowDeleted;
	}

	protected void printSQLException(SQLException ex, String sourceMethod) {
		StringBuffer buf = new StringBuffer();
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				buf.append("SQLState: " + ((SQLException) e).getSQLState() + "\nError Code: "
						+ ((SQLException) e).getErrorCode() + "\nMessage: " + e.getMessage() + "\n");
				Throwable t = ex.getCause();
				while (t != null) {

					buf.append("Cause: " + t + "\n");
					t = t.getCause();
				}
				buf.append("\n");
			}
		}
		log.logp(Level.SEVERE, NAME_LOG_CLS, sourceMethod, buf.toString(), ex);

	}

}
