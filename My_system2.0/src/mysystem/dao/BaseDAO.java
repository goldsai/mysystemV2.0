package mysystem.dao;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//import mysystem.DB.DB;
import mysystem.model.BaseModel;

import static mysystem.DB.DB.*;
import static mysystem.web.Log.*;

/**
 * Это абстрактный, базовый класс в котором заложена реализация базовых CRUD
 * операций.
 * 
 * @author SAI
 *
 * @param <T> класс модели данных, для которого реализованы CRUD операции
 */
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
	 * Хранит имя таблицы в базе данных, в которой хранится данные модели <T extends
	 * BaseModel>
	 */
	private String NAME_DB_TABLE;

	/**
	 * Инициализирует переменные запросов к базе данных
	 */
	protected void initSQLString() {
		SQL_GET_BY_ID = "SELECT * FROM " + NAME_DB_TABLE + " WHERE " + NameFieldID + "=?";
		SQL_GET_ALL = "SELECT * FROM " + NAME_DB_TABLE;
		SQL_DELETE_MODEL = "DELETE FROM " + NAME_DB_TABLE + " WHERE " + NameFieldID + " = ?";
	}

	/**
	 * @param nameDBTable    имя таблицы базы данных, в которой сохроняется модель
	 *                       данных
	 * @param sqlAddModel    SQL запрос на вставку данных в таблицу базы данных.
	 *                       Строка вида:
	 *                       {@code INSERT INTO users (name, email, country) VALUES (?, ?, ?);}
	 * @param sqlUpdateModel SQL запрос на обновление данных в таблице базы данных.
	 *                       Строка вида:
	 *                       {@code update users set name = ?,email= ?, country =? where id = ?;}
	 */
	protected BaseDAO(String nameDBTable, String sqlAddModel, String sqlUpdateModel) {
		NAME_DB_TABLE = nameDBTable;

		SQL_ADD_MODEL = sqlAddModel;
		SQL_UPDATE_MODEL = sqlUpdateModel;
		initSQLString();
	}

	/**
	 * Имя поля первичного ключа таблицы базы данных
	 */
	protected static final String NameFieldID = "id";

	/**
	 * Хранит строку SQL запроса для получения данных по первичному ключу
	 */
	private String SQL_GET_BY_ID;

	// >>>>>>> "select * from users";
	/**
	 * Хранит строку SQL запроса для получения всех записей хранящихся в таблице
	 */
	private String SQL_GET_ALL;

	// !!!!!!!! "INSERT INTO users" + " (name, email, country) VALUES " + " (?, ?,
	// ?)";
	/**
	 * Хранит строку SQL запроса для добавления новой записи в таблицу базы данных
	 */
	private String SQL_ADD_MODEL;

	// !!!!!!!! "update users set name = ?,email= ?, country =? where id = ?"
	/**
	 * Хранит строку SQL запроса для обновления данных по первичному ключу
	 */
	private String SQL_UPDATE_MODEL;

	// >>>>>>>> "delete from users where id = ?"
	/**
	 * Хранит строку SQL запроса для удаления данных по первичному ключу
	 */
	private String SQL_DELETE_MODEL;

	/**
	 * Логирует вход в метод
	 * 
	 * @param sourceMethod - наименование метода
	 */
	protected void logEntering(String sourceMethod) {
		log.entering(NAME_LOG_CLS, sourceMethod);
		logOut(NAME_LOG_CLS, sourceMethod, "Enter metod");
	}

	/**
	 * Логирует вход в метод с одним параметром
	 * 
	 * @param sourceMethod - наименование метода
	 * @param params       - значение параметра (который передали в метод)
	 */
	protected void logEntering(String sourceMethod, Object params) {
		log.entering(NAME_LOG_CLS, sourceMethod, params);
		logOut(NAME_LOG_CLS, sourceMethod, "Enter metod");
	}

	/**
	 * Логирует вход в метод с нескольькими параметрами
	 * 
	 * @param sourceMethod - наименование метода
	 * @param params       - массив значений параметров переданных в метод
	 */
	protected void logEntering(String sourceMethod, Object[] params) {
		log.entering(NAME_LOG_CLS, sourceMethod, params);
		logOut(NAME_LOG_CLS, sourceMethod, "Enter metod");
	}

	/**
	 * Логирует сообщение
	 * 
	 * @param sourceMethod - имя метода из которого переданно сообщение для записи в
	 *                     лог
	 * @param msg          - сообщение для логирования
	 */
	protected void logp(String sourceMethod, String msg) {
		log.logp(Level.SEVERE, NAME_LOG_CLS, sourceMethod, msg);
		logOut(NAME_LOG_CLS, sourceMethod, msg);
	}

	/**
	 * Метод обрабатывает одну запись из ответа на SQL запрос и возвращает модель
	 * заполненую данными. Набор записей должен содержать хотя бы одну запись.
	 * Данный метод вызывается из getByID и getModelsAll.
	 * 
	 * @param rs набор записей полученых от БД
	 * @return Объект класса модели с заполненными полями
	 * 
	 */
	protected abstract T getModelByID(ResultSet rs);

	/**
	 * Производит поиск модели в базе данных по первичному ключу. Если запись в БД
	 * существует, то возвращает объект с заполненными полями. Если записи с
	 * указанным первичным ключем не существует в БД, то возвращает пустую ссылку
	 * (Null)
	 * 
	 * @param id - первичный ключ
	 * @return - Объект класса модели или пустая ссылка (null)
	 */
	public T getByID(long id) {
		logEntering("getByID", id);// логирую вход в метод
		T model = null;
		// подключчаюсь к БД
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID);) {

			preparedStatement.setLong(1, id);// заполняю SQL запрос недостающими значениями
			logp("getByID", "preparedStatement: '" + preparedStatement + "'");// логирую SQL запрос
			ResultSet rs = preparedStatement.executeQuery();// по SQL запросу получаю набор записей

			// если ответ не пустой, то создаю объет класса данных
			if (rs.next())
				model = getModelByID(rs);

		} catch (SQLException e) {
			printSQLException(e, "getByID"); // логирую ошибки
		}
		return model;
	}

	/**
	 * Возвращает все записи из таблицы БД
	 * 
	 * @return
	 */
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

	/**
	 * Метод должен заполнить недостающие значения SQL запроса на вставку новой
	 * модели в таблицу БД.
	 * 
	 * @param preparedStatement
	 * @param model
	 */
	protected abstract void setDataForAddModel(PreparedStatement preparedStatement, T model);

	/**
	 * Добавляю модель в таблицу БД. При успешно добавлении сохраняет значение
	 * первичного ключа в объекте модели (model.id=значение из БД)
	 * 
	 * @param model - ссылка на объект класса данных
	 * @return Истина при успешном добавлении
	 */
	public boolean addModel(T model) {
		logEntering("addModel", model);

		return addModel(model, true);
	}

	/**
	 * Добавляю модель в таблицу БД.
	 * 
	 * @param model - ссылка на объект класса данных
	 * @param setId - истина - сохраняет, созданное при добавлении в БД, значение
	 *              первичного ключа (в объекте модели)
	 * @return Истина при успешном добавлении
	 */
	public boolean addModel(T model, boolean setId) {
		logEntering("addModel", new Object[] { model, setId });

		return addModel(model, setId, false);
	}

	protected abstract void runTransactionsAddModel(T model, boolean setId, Connection connection);

	public boolean addModel(T model, boolean setId, boolean transactions) {
		logEntering("addModel", new Object[] { model, setId, transactions });
		boolean rowInserted = false;

		try (Connection connection = getConnection()) {
			if (transactions)
				connection.setAutoCommit(false);

			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_MODEL)) {
				setDataForAddModel(preparedStatement, model);
				logp("addModel", "preparedStatement: '" + preparedStatement + "'");
				rowInserted = preparedStatement.executeUpdate() > 0;
				if (setId) {
					ResultSet rs = preparedStatement.getGeneratedKeys();
					if (rs.next()) {
						long newId = rs.getLong(1);
						model.setId(newId);
					}
				}
				if (transactions) {
					runTransactionsAddModel(model, setId, connection);
					connection.commit();
				}
			} catch (SQLException e) {
				printSQLException(e, "addModel");
				if (transactions && connection != null)
					try {
						connection.rollback();
					} catch (Exception ee) {
						ee.printStackTrace();
					}
			}
		} catch (SQLException e) {
			printSQLException(e, "addModel");
		}
		return rowInserted;
	}

	/**
	 * Метод должен заполнить недостающие значения SQL запроса на обновление данных
	 * модели в таблице БД.
	 * 
	 * @param preparedStatement
	 * @param model
	 */
	protected abstract void setDataForUpdateModel(PreparedStatement preparedStatement, T model);

	/**
	 * Обновляет данные в таблице
	 * 
	 * @param model - объект для обновления данных
	 * @return
	 */
	public boolean updateModel(T model) {
		logEntering("updateModel", model);

		return updateModel(model, false);
	}

	protected abstract void runTransactionsUpdateModel(T model, Connection connection);

	public boolean updateModel(T model, boolean transactions) {
		logEntering("updateModel", new Object[] { model, transactions });
		boolean rowUpdated = false;
		try (Connection connection = getConnection()) {
			if (transactions)
				connection.setAutoCommit(false);

			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_MODEL)) {

				setDataForUpdateModel(preparedStatement, model);
				logp("updateModel", "preparedStatement: '" + preparedStatement + "'");
				rowUpdated = preparedStatement.executeUpdate() > 0;
				if (transactions) {
					runTransactionsUpdateModel(model, connection);
					connection.commit();
				}
			} catch (SQLException e) {
				printSQLException(e, "updateModel");
				if (transactions && connection != null)
					try {
						connection.rollback();
					} catch (Exception ee) {
						ee.printStackTrace();
					}
			}

		} catch (SQLException e) {
			printSQLException(e, "updateModel");
		}
		return rowUpdated;
	}

	/**
	 * Удаляет модель из таблицы
	 * 
	 * @param model - ссылка на модель для удаления
	 * @return Истина при успешнном удалении
	 */
	public boolean deleteModel(T model) {
		logEntering("deleteModel", model);

		return deleteModel(model.getId());
	}

	/**
	 * Удаляет модель из таблицы
	 * 
	 * @param id - первичный ключ модели
	 * @return Истина при успешном удалении
	 */
	public boolean deleteModel(long id) {
		logEntering("deleteModel", id);
		boolean rowDeleted = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_MODEL)) {

			preparedStatement.setLong(1, id);
			logp("deleteModel", "preparedStatement: '" + preparedStatement + "'");
			rowDeleted = preparedStatement.executeUpdate() > 0;

		} catch (SQLException e) {
			printSQLException(e, "deleteModel");
		}
		return rowDeleted;
	}

	protected abstract void deleteModelDeleteModel(long id, Connection connection);

	public boolean deleteModel(long id, boolean transactions) {
		logEntering("deleteModel", new Object[] { id, transactions });
		boolean rowDeleted = false;
		try (Connection connection = getConnection()) {
			if (transactions)
				connection.setAutoCommit(false);

			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_MODEL)) {

				preparedStatement.setLong(1, id);
				logp("deleteModel", "preparedStatement: '" + preparedStatement + "'");
				rowDeleted = preparedStatement.executeUpdate() > 0;

				if (transactions) {
					deleteModelDeleteModel(id, connection);
					connection.commit();
				}

			} catch (SQLException e) {
				printSQLException(e, "deleteModel");
				if (transactions && connection != null)
					try {
						connection.rollback();
					} catch (Exception ee) {
						ee.printStackTrace();
					}
			}

		} catch (SQLException e) {
			printSQLException(e, "deleteModel");
		}
		return rowDeleted;
	}

	/**
	 * Логирует исключения
	 * 
	 * @param ex
	 * @param sourceMethod
	 */
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
