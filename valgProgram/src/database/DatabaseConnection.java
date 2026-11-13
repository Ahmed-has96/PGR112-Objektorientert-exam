package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements DBConnection {
    private final String databaseName;

    public DatabaseConnection(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public Connection getConnection() throws SQLException {
        String host = System.getenv().getOrDefault("VOTE_DB_HOST", "localhost");
        String port = System.getenv().getOrDefault("VOTE_DB_PORT", "3306");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
        return DriverManager.getConnection(url, required("VOTE_DB_USER"), required("VOTE_DB_PASSWORD"));
    }

    private static String required(String key) {
        String value = System.getenv(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException("Sett " + key + " før du starter programmet.");
        }
        return value;
    }
}
