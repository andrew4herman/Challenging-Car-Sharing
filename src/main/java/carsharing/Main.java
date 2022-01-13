package carsharing;

import carsharing.activities.MainActivity;
import carsharing.database.DBConnector;
import carsharing.database.dao.DBManager;
import carsharing.util.CliParser;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Optional<String> fileName = new CliParser(args).optionOf("-fileName");

        if (fileName.isPresent()) {
            DBConnector dbConnector = new DBConnector(fileName.get());
            DBManager dbManager = new DBManager(dbConnector);
            Scanner scanner = new Scanner(System.in);

            MainActivity mainActivity = new MainActivity(scanner, dbManager);
            mainActivity.start();

            dbConnector.closeConnection();
            scanner.close();
        } else {
            throw new IllegalArgumentException("Incorrect option for -fileName");
        }
    }
}
