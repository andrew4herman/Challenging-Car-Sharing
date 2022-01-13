package carsharing.activities;

import carsharing.database.dao.DBManager;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.model.Customer;
import carsharing.util.ChooserUtils;

import java.util.List;
import java.util.Scanner;

public class CustomerActivity extends Activity {

    private final Customer currentCustomer;
    private final DBManager dbManager;

    public CustomerActivity(Scanner scanner, DBManager dbManager, Customer customer) {
        super(scanner);
        this.dbManager = dbManager;
        this.currentCustomer = customer;
    }

    @Override
    protected void showMenu() {
        System.out.println("""
                                
                1. Rent a car
                2. Return a rented car
                3. My rented car
                0. Back
                """);
    }

    @Override
    protected void processOption(String option) {
        switch (option) {
            case "0" -> System.out.printf("Logged out from '%s'%n", currentCustomer.getName());
            case "1" -> rentCarOption();
            case "2" -> returnRentedCarOption();
            case "3" -> showRentedCarInfo();
            default -> System.out.println("Incorrect option. Try again!");
        }
    }

    private void rentCarOption() {
        if (hasRentedCar()) {
            System.out.println("You've already rented a car!");
        } else {
            List<Company> companies = dbManager.getCompanyDao().getAll();
            if (companies.isEmpty()) {
                System.out.println("\nThe company list is empty!");
            } else {
                System.out.println("Choose a company:");
                ChooserUtils.chooseEntityFrom(companies, scanner).ifPresent(this::chooseCar);
            }
        }
    }

    private void returnRentedCarOption() {
        if (hasRentedCar()) {
            dbManager.getCarDao().getById(currentCustomer.getRentedCarId()).ifPresent(this::returnCar);
        } else {
            System.out.println("You didn't rent a car!");
        }
    }

    private void showRentedCarInfo() {
        if (hasRentedCar()) {
            dbManager.getCarDao().getById(currentCustomer.getRentedCarId()).ifPresent(this::outputInfoAbout);
        } else {
            System.out.println("You didn't rent a car!");
        }
    }

    private void outputInfoAbout(Car car) {
        String companyName = dbManager.getCompanyDao().getById(car.getCompanyId()).orElseThrow().getName();
        System.out.printf("""
                                
                Your rented car:
                %s
                Company:
                %s
                """, car.getName(), companyName);
    }

    private void chooseCar(Company company) {
        List<Car> cars = dbManager.getCarDao().getRentedCarsFrom(company.getId());
        if (cars.isEmpty()) {
            System.out.println("\nThe car list is empty!");
        } else {
            System.out.println("Choose a car:");
            ChooserUtils.chooseEntityFrom(cars, scanner).ifPresent(this::rentCar);
        }
    }

    private void returnCar(Car car) {
        dbManager.makeTransaction(() -> {
            dbManager.getCustomerDao().updateCustomer(currentCustomer.getId(), 0);
            dbManager.getCarDao().updateCar(car.getId(), false);
        });
        currentCustomer.setRentedCarId(0);

        System.out.println("You've returned a rented car!");
    }

    private void rentCar(Car car) {
        dbManager.makeTransaction(() -> {
            dbManager.getCustomerDao().updateCustomer(currentCustomer.getId(), car.getId());
            dbManager.getCarDao().updateCar(car.getId(), true);
        });
        currentCustomer.setRentedCarId(car.getId());

        System.out.printf("%nYou rented '%s'%n", car.getName());
    }

    private boolean hasRentedCar() {
        return currentCustomer.getRentedCarId() != 0;
    }
}
