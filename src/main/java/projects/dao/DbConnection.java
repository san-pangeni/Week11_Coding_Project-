package projects.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import projects.exception.DbException;

public class DbConnection {
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String USER = "root"; // Change to your MySQL username
    private static final String PASSWORD = "Mgfgtis321t"; // Change to your MySQL password
    private static final String SCHEMA = "projects";
    private static final String URI = String.format("jdbc:mysql://%s:%s/%s?useSSL=false", HOST, PORT, SCHEMA);

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URI, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}