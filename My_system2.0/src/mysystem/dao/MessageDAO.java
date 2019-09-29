package mysystem.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Date;

import mysystem.model.Message;
//import mysystem.model.TypeDoc;
import mysystem.model.User;

/*
 * CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_create` datetime NOT NULL,
  `txt` longtext NOT NULL,
  `id_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `message_id_user_idx` (`id_user`),
  CONSTRAINT `message_id_user` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
public class MessageDAO extends BaseDAO<Message> {
	private static volatile MessageDAO instance;
	public static MessageDAO getInstance() {
		MessageDAO localInstance = instance;
		if (localInstance == null) {
			synchronized (MessageDAO.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new MessageDAO();
				}
			}
		}
		return localInstance;
	}
	protected MessageDAO() {
		super(NameDBTableMessage, SQL_ADD_USER, SQL_UPDATE_USER);
		// TODO Auto-generated constructor stub
	}

	private static final String NameDBTableMessage = "type_doc";
	private static final String NameFieldDateCreate = "date_create";
	private static final String NameFieldTxt = "txt";
	private static final String NameFieldIdUser = "id_user";
	private UserDAO userDAO = new UserDAO();

	private static final String SQL_ADD_USER = "INSERT INTO " + NameDBTableMessage + " (" + NameFieldDateCreate + ", "
			+ NameFieldTxt + ", " + NameFieldIdUser + ") VALUES (?, ?, ?)";
	private static final String SQL_UPDATE_USER = "UPDATE " + NameDBTableMessage + " SET " + NameFieldDateCreate
			+ " = ?," + NameFieldTxt + " = ?," + NameFieldIdUser + "= ? WHERE id = ?";

	@Override
	protected Message getModelByID(ResultSet rs) {
		// TODO Auto-generated method stub
		logEntering("getModelByID", rs);
		long id = 0;// = rs.getLong(NameFieldID);
		String txt = "";
		long id_user = 0;
		Date dateCreate = null;

		try {
			id = rs.getLong(NameFieldID);
			txt = rs.getString(NameFieldTxt);
			id_user = rs.getLong(NameFieldIdUser);
			dateCreate = rs.getDate(NameFieldDateCreate);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e, "getModelByID");
		}

		User user = userDAO.getByID(id_user);

		return new Message(id, dateCreate, txt, user);

	}

	@Override
	protected void setDataForAddModel(PreparedStatement preparedStatement, Message model) {
		// TODO Auto-generated method stub
		logEntering("setDataForAddModel", new Object[] { preparedStatement, model });
		// "INSERT INTO " + NameDBTableMessage + " (" + NameFieldDateCreate + ", "
		// + NameFieldTxt + ", " + NameFieldIdUser + ") VALUES (?, ?, ?)";
		try {
			preparedStatement.setDate(1, new Date(model.getCreateDate().getTime()));
			preparedStatement.setString(2, model.getTxt());
			preparedStatement.setLong(3, model.getAuthor().getId());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e, "setDataForAddModel");
		}
	}

	@Override
	protected void setDataForUpdateModel(PreparedStatement preparedStatement, Message model) {
		// TODO Auto-generated method stub
		logEntering("setDataForUpdateModel", new Object[] { preparedStatement, model });
		// "UPDATE " + NameDBTableMessage + " SET " + NameFieldDateCreate
		// + " = ?," + NameFieldTxt + " = ?," + NameFieldIdUser + "= ? WHERE id = ?";
		try {
			preparedStatement.setDate(1, new Date(model.getCreateDate().getTime()));
			preparedStatement.setString(2, model.getTxt());
			preparedStatement.setLong(3, model.getAuthor().getId());

			preparedStatement.setLong(4, model.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e, "setDataForUpdateModel");
		}
	}
	@Override
	protected void runTransactionsAddModel(Message model, boolean setId, Connection connection) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void runTransactionsUpdateModel(Message model, Connection connection) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void deleteModelDeleteModel(long id, Connection connection) {
		// TODO Auto-generated method stub
		
	}

}
