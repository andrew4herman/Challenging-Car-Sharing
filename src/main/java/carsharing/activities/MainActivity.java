package carsharing.activities;

import carsharing.database.dao.DBManager;
import carsharing.model.Customer;
import carsharing.util.ChooserUtils;

import java.util.List;
import java.util.Scanner;

public class MainActivity extends Activity {

    private final DBManager dbManager;

    public MainActivity(Scanner scanner, DBManager dbManager) {
        super(scanner);
        this.dbManager = dbManager;
    }

    @Override
    protected void showMenu() {
        System.out.println("""
                                
                1. Log in as a manager
                2. Log in as a customer
                3. Create a customer
                0. Exit
                """);
    }

    @Override
    protected void processOption(String option) {
        switch (option) {
            case "0" -> System.out.println("Bye!");
            case "1" -> logInAsManager();
            case "2" -> logInAsCustomer();
            case "3" -> createCustomerOption();
            default -> System.out.println("Incorrect option. Try again.");
        }
    }

    private void logInAsManager() {
        new ManagerActivity(scanner, dbManager).start();
    }

    private void logInAsCustomer() {
        List<Customer> customers = dbManager.getCustomerDao().getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
        } else {
            System.out.println("The customer list:");
            ChooserUtils.chooseEntityFrom(customers, scanner).ifPresent(
                    customer -> new CustomerActivity(scanner, dbManager, customer).start());
        }
    }

    private void createCustomerOption() {
        System.out.println("\nEnter the customer name:");
        String name = scanner.nextLine();

        dbManager.getCustomerDao().save(name);
        System.out.println("The customer was added!");
    }
}
