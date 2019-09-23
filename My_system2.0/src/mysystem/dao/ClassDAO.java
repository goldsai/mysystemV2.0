package mysystem.dao;

import mysystem.model.Model;
import static mysystem.DB.DB.*;
import java.sql.*;

public class ClassDAO<T extends Model> {
	private String SELECT_ALL_BY_ID;
//	private T model;
	@SuppressWarnings("unchecked")
	public T find(long id) throws SQLException {
		try(Connection conn=getConnection()){
			PreparedStatement statement = conn.prepareStatement(SELECT_ALL_BY_ID);
			statement.setLong(1, id);
		//	ResultSet resultSet = statement.executeQuery();
		//	resultSet.findColumn(columnLabel)
			return (T) new Model();
		}
	}
}
