package database;

import java.sql.SQLException;

public interface DBConnection {
	java.sql.Connection getConnection() throws SQLException;
}
