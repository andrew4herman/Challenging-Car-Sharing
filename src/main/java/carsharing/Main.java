package carsharing;

import carsharing.activities.MainActivity;
import carsharing.database.DBConnector;
import carsharing.database.DBManager;
import carsharing.database.dao.DaoContainer;
import carsharing.util.CliParser;
import carsharing.util.DatabaseException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = new CliParser(args).optionOf("-fileName")
                .orElseThrow(() -> new IllegalArgumentException("Incorrect option for -fileName"));

        try (DBConnector dbConnector = new DBConnector(fileName);
             Scanner scanner = new Scanner(System.in)) {

            DBManager dbManager = new DBManager(dbConnector);
            DaoContainer daoContainer = new DaoContainer(dbConnector);

            dbManager.migrateUp();

            MainActivity mainActivity = new MainActivity(scanner, daoContainer, dbManager);
            mainActivity.start();

        } catch (DatabaseException e) {
            System.out.println("Execution of this application has stopped due to an error: ");
            System.out.println(e.getMessage());

            e.printStackTrace();
        }
    }
}
