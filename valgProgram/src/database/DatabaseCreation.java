package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseCreation {
    private DatabaseCreation() {}

    public static void initialize() throws SQLException {
        try (Connection connection = new DatabaseConnection("").getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS studentDB");
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS voteDB");
        }
    }
}
