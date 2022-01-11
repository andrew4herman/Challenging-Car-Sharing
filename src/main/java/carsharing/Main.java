package carsharing;

import carsharing.activities.MainActivity;
import carsharing.database.DBManager;
import carsharing.database.dao.CarDao;
import carsharing.database.dao.CompanyDao;
import carsharing.database.dao.CustomerDao;
import carsharing.util.CliParser;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Optional<String> fileName = new CliParser(args).optionOf("-fileName");

        if (fileName.isPresent()) {
            DBManager dbManager = new DBManager(fileName.get());
            CompanyDao companyDao = new CompanyDao(dbManager);
            CarDao carDao = new CarDao(dbManager);
            CustomerDao customerDao = new CustomerDao(dbManager);

            MainActivity mainActivity = new MainActivity(new Scanner(System.in), companyDao, carDao, customerDao);
            mainActivity.start();

            dbManager.closeConnection();
        } else {
            throw new IllegalArgumentException("Incorrect option for -fileName");
        }
    }
}
