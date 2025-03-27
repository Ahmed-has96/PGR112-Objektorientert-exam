package database;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseCreation {
	static {
		String jdbcUrl = "jdbc:mysql://localhost:3306/";
		String username = "Db_User";
		String password = "Db_pass";

		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE DATABASE IF NOT EXISTS  studentDB");
			statement.executeUpdate("CREATE DATABASE IF NOT EXISTS  voteDB");
			statement.executeUpdate("CREATE USER IF NOT EXISTS 'root' IDENTIFIED BY ''");
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
