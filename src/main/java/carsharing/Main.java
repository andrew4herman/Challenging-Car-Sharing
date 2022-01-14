package carsharing;

import carsharing.activities.MainActivity;
import carsharing.database.DBConnector;
import carsharing.database.DBManager;
import carsharing.database.dao.DaoContainer;
import carsharing.util.CliParser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DBConnector dbConnector = null;
        Scanner scanner = null;

        try {
            String fileName = new CliParser(args).optionOf("-fileName")
                    .orElseThrow(() -> new IllegalArgumentException("Incorrect option for -fileName"));

            dbConnector = new DBConnector(fileName);
            DBManager dbManager = new DBManager(dbConnector);
            DaoContainer daoContainer = new DaoContainer(dbConnector);
            scanner = new Scanner(System.in);

            dbManager.migrateApp();

            MainActivity mainActivity = new MainActivity(scanner, daoContainer, dbManager);
            mainActivity.start();

        } catch (RuntimeException e) {
            System.out.println("Execution of this application has stopped due to a runtime error: ");
            System.out.println(e.getMessage());

            e.printStackTrace();
        } finally {
            if (dbConnector != null) {
                dbConnector.closeConnection();
            }
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
