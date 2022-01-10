package carsharing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    public static final String CREATE_TABLE_COMPANY = """
            CREATE TABLE IF NOT EXISTS company(
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(255) UNIQUE NOT NULL
            );""";
    public static final String CREATE_TABLE_CAR = """
            CREATE TABLE IF NOT EXISTS car(
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(255) NOT NULL UNIQUE,
                company_id INT NOT NULL,
                is_rented BOOLEAN NOT NULL,
                FOREIGN KEY (company_id) REFERENCES company(id)
            );""";
    public static final String CREATE_TABLE_CUSTOMER = """
            CREATE TABLE IF NOT EXISTS customer(
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(255) NOT NULL UNIQUE,
                rented_car_id INT,
                FOREIGN KEY (rented_car_id) REFERENCES car(id)
            );""";

    private final String USER;
    private final String PASSWORD;
    private final String URL;
    private Connection connection;

    public DBManager(String fileName) {
        USER = "root";
        PASSWORD = "hyperskill";

        String JDBC_DRIVER = "jdbc:h2:";
        String FILE_PATH = "./src/main/java/carsharing/db/";
        URL = JDBC_DRIVER + FILE_PATH + fileName;

        tryToCreateConnection();
        createTables();
    }

    private void createTables() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(CREATE_TABLE_COMPANY);
            statement.executeUpdate(CREATE_TABLE_CAR);
            statement.executeUpdate(CREATE_TABLE_CUSTOMER);

            getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create table", e);
        }
    }

    private void tryToCreateConnection() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create a connection with database!", e);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Cannot close a connection with database!", e);
            }
        }
    }
}
