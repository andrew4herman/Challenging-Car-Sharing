package carsharing.activities;

import carsharing.database.dao.CarDao;
import carsharing.database.dao.CompanyDao;
import carsharing.database.dao.CustomerDao;
import carsharing.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

public class MainActivity extends Activity {

    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;

    public MainActivity(Scanner scanner, CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        super(scanner);
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.customerDao = customerDao;
    }

    @Override
    public void showMenu() {
        System.out.println("""
                1. Log in as a manager
                2. Log in as a customer
                3. Create a customer
                0. Exit
                """);
    }

    @Override
    public void processOption(String option) {
        switch (option) {
            case "0" -> System.out.println("Bye!");
            case "1" -> logInAsManager();
            case "2" -> logInAsCustomer();
            case "3" -> createCustomerOption();
            default -> System.out.println("Incorrect option. Try again.");
        }
    }

    private void createCustomerOption() {
        System.out.println("\nEnter the customer name:");
        String name = scanner.nextLine();

        customerDao.save(name);
        System.out.println("The customer was added!");
    }

    private void logInAsCustomer() {
        List<Customer> customers = customerDao.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
        } else {
            chooseCustomerFrom(customers).ifPresent(
                    customer -> new CustomerActivity(scanner, customer, companyDao, carDao).start());
        }
    }

    private void logInAsManager() {
        new ManagerActivity(scanner, companyDao, carDao, customerDao).start();
    }

    private Optional<Customer> chooseCustomerFrom(List<Customer> customers) {
        do {
            IntStream.iterate(0, i -> i + 1)
                    .limit(customers.size())
                    .forEach(i -> System.out.printf("%d. %s%n", i + 1, customers.get(i).getName()));
            System.out.println("0. Back");

            try {
                int option = Integer.parseInt(scanner.nextLine());

                if (option > 0 && option <= customers.size()) {
                    return Optional.of(customers.get(option - 1));
                } else if (option == 0) {
                    return Optional.empty();
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Incorrect input. Try again or return back.");
            }
        } while (true);
    }
}
