package mysystem.dao;

import java.sql.*;
import java.util.*;
import mysystem.model.BaseModel;
import static mysystem.DB.DB.*;
import static mysystem.Log.*;

/**
 * Это абстрактный, базовый класс в котором заложена реализация базовых CRUD
 * операций.
 * 
 * @author SAI
 *
 * @param <T> класс модели данных, для которого реализованы CRUD операции
 */
/**
 * @author SAI
 *
 * @param <T>
 */
public abstract class BaseDAO<T extends BaseModel> {
	// ================================================================
	// Константы
	// ================================================================

	/**
	 * Имя поля первичного ключа таблицы базы данных
	 */
	protected static final String NameFieldID = "id";
	/**
	 * Имя класса (с этим именем добавляются записи с лог)
	 */
	private static final String NAME_LOG_CLS = "BaseDAO";

	// ================================================================
	// Переменные класса
	// ================================================================

	/**
	 * Имя таблицы БД, с которой работает дынный класс. Записи этой таблицы хранят
	 * данные класса T extends BaseModel.
	 */
	private String NAME_DB_TABLE;

	/**
	 * Cтрока SQL запроса - получает запись по первичному ключу. <br>
	 * Строка вида: <br>
	 * <code>SELECT * FROM user WHERE id=?</code>
	 */
	private String SQL_GET_BY_ID;

	/**
	 * Строка SQL запроса - получает все записи из таблицы. <br>
	 * Строка вида: <br>
	 * <code>SELECT * FROM users</code>
	 */
	private String SQL_GET_ALL;

	/**
	 * Строка SQL запроса - добавляет новую запись в таблицу. <br>
	 * Строка вида: <br>
	 * <code>INSERT INTO users (name, email, country) VALUES (?, ?, ?)</code>
	 */
	private String SQL_ADD_MODEL;

	/**
	 * Строка SQL запроса - обновляет запись по первичному ключу. <br>
	 * Строка вида: <br>
	 * <code>UPDATE users SET name = ?,email= ?, country =? WHERE id = ?</code>
	 */
	private String SQL_UPDATE_MODEL;

	/**
	 * Строка SQL запроса - удаляет запись по первичному ключу. <br>
	 * Строка вида: <br>
	 * <code>DELETE FROM users WHERE id = ?</code>
	 */
	private String SQL_DELETE_MODEL;

	// ================================================================
	// Переменные и методы логирование
	// ================================================================

	// ================================================================
	// Создание и инициализация
	// ================================================================

	/**
	 * Инициализирует переменные SQL запросов. При необходимости можно
	 * переопределить в дочернем классе.
	 */
	protected void initSQLString() {
		SQL_GET_BY_ID = "SELECT * FROM " + NAME_DB_TABLE + " WHERE " + NameFieldID + "=?";
		SQL_GET_ALL = "SELECT * FROM " + NAME_DB_TABLE;
		SQL_DELETE_MODEL = "DELETE FROM " + NAME_DB_TABLE + " WHERE " + NameFieldID + " = ?";
	}

	/**
	 * Конструктор
	 * 
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
		logEntering(NAME_LOG_CLS, "getByID", id);// логирую вход в метод
		T model = null;
		// подключаюсь к БД
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID);) {

			preparedStatement.setLong(1, id);// заполняю SQL запрос недостающими значениями
			logp(NAME_LOG_CLS, "getByID", "preparedStatement: '" + preparedStatement + "'");// логирую SQL запрос
			ResultSet rs = preparedStatement.executeQuery();// по SQL запросу получаю набор записей

			// если ответ не пустой, то создаю объет класса данных
			if (rs.next())
				model = getModelByID(rs);

		} catch (SQLException e) {
			printSQLException(NAME_LOG_CLS, e, "getByID"); // логирую ошибки
		}
		logExiting(NAME_LOG_CLS, "getByID", model);
		return model;
	}

	/**
	 * Возвращает все записи из таблицы БД
	 * 
	 * @return
	 */
	public List<T> getModelsAll() {
		logEntering(NAME_LOG_CLS, "getAll");
		List<T> list = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL)) {
			logp(NAME_LOG_CLS, "getAll", "preparedStatement: '" + preparedStatement + "'");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
				list.add(getModelByID(rs));

		} catch (SQLException e) {
			printSQLException(NAME_LOG_CLS, e, "getAll");
		}
		logExiting(NAME_LOG_CLS, "getAll", list);
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
		logEntering(NAME_LOG_CLS, "addModel", model);
		boolean res = addModel(model, true);
		logExiting(NAME_LOG_CLS, "addModel", res);
		return res;
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
		logEntering(NAME_LOG_CLS, "addModel", new Object[] { model, setId });
		boolean res = addModel(model, setId, false);
		logExiting(NAME_LOG_CLS, "addModel", res);
		return res;
	}

	protected abstract void runTransactionsAddModel(T model, boolean setId, Connection connection);

	public boolean addModel(T model, boolean setId, boolean transactions) {
		logEntering(NAME_LOG_CLS, "addModel", new Object[] { model, setId, transactions });
		boolean rowInserted = false;

		try (Connection connection = getConnection()) {
			if (transactions)
				connection.setAutoCommit(false);

			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_MODEL)) {
				setDataForAddModel(preparedStatement, model);
				logp(NAME_LOG_CLS, "addModel", "preparedStatement: '" + preparedStatement + "'");
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
				printSQLException(NAME_LOG_CLS, e, "addModel");
				if (transactions && connection != null)
					try {
						connection.rollback();
					} catch (Exception ee) {
						ee.printStackTrace();
					}
			}
		} catch (SQLException e) {
			printSQLException(NAME_LOG_CLS, e, "addModel");
		}
		logExiting(NAME_LOG_CLS, "addModel", rowInserted);
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
		logEntering(NAME_LOG_CLS, "updateModel", model);
		boolean res = updateModel(model, false);
		logExiting(NAME_LOG_CLS, "updateModel", res);
		return res;
	}

	protected abstract void runTransactionsUpdateModel(T model, Connection connection);

	public boolean updateModel(T model, boolean transactions) {
		logEntering(NAME_LOG_CLS, "updateModel", new Object[] { model, transactions });
		boolean rowUpdated = false;
		try (Connection connection = getConnection()) {
			if (transactions)
				connection.setAutoCommit(false);

			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_MODEL)) {

				setDataForUpdateModel(preparedStatement, model);
				logp(NAME_LOG_CLS, "updateModel", "preparedStatement: '" + preparedStatement + "'");
				rowUpdated = preparedStatement.executeUpdate() > 0;
				if (transactions) {
					runTransactionsUpdateModel(model, connection);
					connection.commit();
				}
			} catch (SQLException e) {
				printSQLException(NAME_LOG_CLS, e, "updateModel");
				if (transactions && connection != null)
					try {
						connection.rollback();
					} catch (Exception ee) {
						ee.printStackTrace();
					}
			}

		} catch (SQLException e) {
			printSQLException(NAME_LOG_CLS, e, "updateModel");
		}
		logExiting(NAME_LOG_CLS, "updateModel", rowUpdated);
		return rowUpdated;
	}

	/**
	 * Удаляет модель из таблицы
	 * 
	 * @param model - ссылка на модель для удаления
	 * @return Истина при успешнном удалении
	 */
	public boolean deleteModel(T model) {
		logEntering(NAME_LOG_CLS, "deleteModel", model);
		boolean res = deleteModel(model.getId());
		logExiting(NAME_LOG_CLS, "deleteModel", res);
		return res;
	}

	/**
	 * Удаляет модель из таблицы
	 * 
	 * @param id - первичный ключ модели
	 * @return Истина при успешном удалении
	 */
	public boolean deleteModel(long id) {
		logEntering(NAME_LOG_CLS, "deleteModel", id);
		boolean rowDeleted = false;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_MODEL)) {

			preparedStatement.setLong(1, id);
			logp(NAME_LOG_CLS, "deleteModel", "preparedStatement: '" + preparedStatement + "'");
			rowDeleted = preparedStatement.executeUpdate() > 0;

		} catch (SQLException e) {
			printSQLException(NAME_LOG_CLS, e, "deleteModel");
		}
		logExiting(NAME_LOG_CLS, "deleteModel", rowDeleted);
		return rowDeleted;
	}

	protected abstract void deleteModelDeleteModel(long id, Connection connection);

	public boolean deleteModel(long id, boolean transactions) {
		logEntering(NAME_LOG_CLS, "deleteModel", new Object[] { id, transactions });
		boolean rowDeleted = false;
		try (Connection connection = getConnection()) {
			if (transactions)
				connection.setAutoCommit(false);

			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_MODEL)) {

				preparedStatement.setLong(1, id);
				logp(NAME_LOG_CLS, "deleteModel", "preparedStatement: '" + preparedStatement + "'");
				rowDeleted = preparedStatement.executeUpdate() > 0;

				if (transactions) {
					deleteModelDeleteModel(id, connection);
					connection.commit();
				}

			} catch (SQLException e) {
				printSQLException(NAME_LOG_CLS, e, "deleteModel");
				if (transactions && connection != null)
					try {
						connection.rollback();
					} catch (Exception ee) {
						ee.printStackTrace();
					}
			}

		} catch (SQLException e) {
			printSQLException(NAME_LOG_CLS, e, "deleteModel");
		}
		logExiting(NAME_LOG_CLS, "deleteModel", rowDeleted);
		return rowDeleted;
	}

}
