package mysystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mysystem.model.Doc;
/*
 * CREATE TABLE `docs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `id_type` bigint(20) NOT NULL,
  `id_actual_version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `docs_id_type_idx` (`id_type`),
  CONSTRAINT `docs_id_type` FOREIGN KEY (`id_type`) REFERENCES `type_doc` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
public class DocDAO extends BaseDAO<Doc> {
	private static volatile DocDAO instance;
	public static DocDAO getInstance() {
		DocDAO localInstance = instance;
		if (localInstance == null) {
			synchronized (DocDAO.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new DocDAO();
				}
			}
		}
		return localInstance;
	}
	protected DocDAO() {
		super(NameDBTableDoc, SQL_ADD_USER, SQL_UPDATE_USER);
	}
	@Override
	protected Doc getModelByID(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void setDataForAddModel(PreparedStatement preparedStatement, Doc model) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void setDataForUpdateModel(PreparedStatement preparedStatement, Doc model) {
		// TODO Auto-generated method stub
		
	}
	private static final String NameDBTableDoc = "docs";
	private static final String NameFieldName = "name";
	private static final String NameFieldType = "id_type";
	private static final String NameFieldActualVersion = "id_actual_version";
	private UserDAO userDAO = new UserDAO();

	private static final String SQL_ADD_USER = "INSERT INTO " + NameDBTableDoc + " (" + NameFieldName + ", "
			+ NameFieldType + ", " + NameFieldActualVersion + ") VALUES (?, ?, ?)";
	private static final String SQL_UPDATE_USER = "UPDATE " + NameDBTableDoc + " SET " + NameFieldName
			+ " = ?," + NameFieldType + " = ?," + NameFieldActualVersion + "= ? WHERE id = ?";
	@Override
	protected void runTransactionsAddModel(Doc model, boolean setId, Connection connection) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void runTransactionsUpdateModel(Doc model, Connection connection) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void deleteModelDeleteModel(long id, Connection connection) {
		// TODO Auto-generated method stub
		
	}

}
