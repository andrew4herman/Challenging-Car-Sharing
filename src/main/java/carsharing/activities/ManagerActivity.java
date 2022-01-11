package carsharing.activities;

import carsharing.database.dao.CarDao;
import carsharing.database.dao.CompanyDao;

import java.util.Scanner;

public class ManagerActivity extends Activity{

    private final CompanyDao companyDao;
    private final CarDao carDao;

    public ManagerActivity(Scanner scanner, CompanyDao companyDao, CarDao carDao) {
        super(scanner);
        this.companyDao = companyDao;
        this.carDao = carDao;
    }

    @Override
    protected void showMenu() {
        System.out.println("""
                1. Company list
                2. Create a company
                0. Back
                """);
    }

    @Override
    protected void processOption(String option) {
        switch (option) {
            case "0" -> System.out.println("Logged out!");
            case "1" -> chooseCompanyOption();
            case "2" -> createCompanyOption();
            default -> System.out.println("Incorrect option. Try again!");
        }
    }

    private void chooseCompanyOption() {

    }

    private void createCompanyOption() {
        System.out.println("\nEnter the company name:");
        String companyName = scanner.nextLine();

        companyDao.save(companyName);
        System.out.println("The company was created!");
    }
}
