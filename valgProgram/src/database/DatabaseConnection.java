package database;


import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

public class DatabaseConnection implements DBConnection {


	private final String userName;
	private final String password;
	private final String host;
	private final String name;
	private final String port;





	public DatabaseConnection(String dbHost, String dbPort, String dbName, String dbUserName, String dbPassword) {
		this.host = dbHost;
		this.port = dbPort;
		this.name = dbName;
		this.userName = dbUserName;
		this.password = dbPassword;
	}

	@Override
	public Connection getConnection() throws SQLException {
		Connection connection = null;
		String url = "jdbc:mysql://" + host + ":" + port + "/" + name;

		connection = DriverManager.getConnection(url, userName, password);
		return connection;
	}


}
