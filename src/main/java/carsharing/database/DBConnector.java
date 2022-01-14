package carsharing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String JDBC_DRIVER = "jdbc:h2:";
    private static final String FILE_PATH = "./src/main/java/carsharing/db/";

    private final String USER;
    private final String PASSWORD;
    private final String URL;

    private Connection connection;

    public DBConnector(String fileName) {
        USER = "root";
        PASSWORD = "hyperskill";
        URL = JDBC_DRIVER + FILE_PATH + fileName;

        tryToCreateConnection();
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Cannot close a connection with database!", e);
            }
        }
    }

    private void tryToCreateConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create a connection with database!", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
