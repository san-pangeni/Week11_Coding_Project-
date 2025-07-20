package projects.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import projects.exception.DbException;

// Handles the connection to the MySQL database.
public class DbConnection {
    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String SCHEMA = "Project11";
    private static final String USER = "root";
    private static final String PASSWORD = "Mgfgtis321t";
    private static final String DB_URL = String.format("jdbc:mysql://%s:%d/%s?useSSL=false", HOST, PORT, SCHEMA);

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DbException("Unable to connect to database", e);
        }
    }
}