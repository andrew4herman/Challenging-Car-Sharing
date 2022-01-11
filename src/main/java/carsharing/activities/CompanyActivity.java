package carsharing.activities;

import carsharing.database.dao.CarDao;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.util.ChooserUtils;

import java.util.List;
import java.util.Scanner;

public class CompanyActivity extends Activity {

    private final Company currentCompany;
    private final CarDao carDao;

    public CompanyActivity(Scanner scanner, Company company, CarDao carDao) {
        super(scanner);
        this.currentCompany = company;
        this.carDao = carDao;
    }

    @Override
    protected void showMenu() {
        System.out.println("""
                
                1. Car list
                2. Create a car
                0. Back
                """);
    }

    @Override
    protected void processOption(String option) {
        switch (option) {
            case "0" -> System.out.println("Exited from company.");
            case "1" -> showCarListOption();
            case "2" -> createCarOption();
            default -> System.out.println("Incorrect option. Try again.");
        }
    }

    private void showCarListOption() {
        List<Car> cars = carDao.getAllCars();
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println("Car list:");
            ChooserUtils.outputEntities(cars);
        }
    }

    private void createCarOption() {
        System.out.println("\nEnter the car name:");
        String carName = scanner.nextLine();

        carDao.save(carName, currentCompany.getId());
        System.out.println("The car was added!");
    }
}
