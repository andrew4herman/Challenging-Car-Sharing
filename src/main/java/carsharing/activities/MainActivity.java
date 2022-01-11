package carsharing.activities;

import carsharing.database.dao.CarDao;
import carsharing.database.dao.CompanyDao;
import carsharing.database.dao.CustomerDao;

import java.util.Scanner;

public class MainActivity implements Activity {

    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;
    private final Scanner scanner;

    public MainActivity(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.customerDao = customerDao;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void start() {
        String option;
        do {
            showMenu();
            option = scanner.nextLine();
            processOption(option);
        } while (!"0".equals(option));
    }

    private void showMenu() {
        System.out.println("""
                1. Log in as a manager
                2. Log in as a customer
                3. Create a customer
                0. Exit
                """);
    }

    private void processOption(String option) {
        switch (option) {
            case "0" -> System.out.println("Bye!");
            case "1" -> logInAsManager();
            case "2" -> logInAsCustomer();
            case "3" -> createCustomerOption();
        }
    }

    private void createCustomerOption() {

    }

    private void logInAsCustomer() {

    }

    private void logInAsManager() {

    }
}
