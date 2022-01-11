package carsharing.activities;

import carsharing.database.dao.CarDao;
import carsharing.database.dao.CompanyDao;
import carsharing.model.Company;
import carsharing.util.ChooserUtils;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ManagerActivity extends Activity {

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
        List<Company> companies = companyDao.getAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Choose the company:");
            chooseCompanyFrom(companyDao.getAll()).ifPresent(
                    company -> {
                        System.out.printf("'%s' company", company.getName());
                        new CompanyActivity(scanner, company, carDao).start();
                    });
        }
    }

    private void createCompanyOption() {
        System.out.println("\nEnter the company name:");
        String companyName = scanner.nextLine();

        companyDao.save(companyName);
        System.out.println("The company was created!");
    }

    private Optional<Company> chooseCompanyFrom(List<Company> companies) {
        do {
            ChooserUtils.outputEntities(companies);
            System.out.println("0. Back");
            try {
                int option = Integer.parseInt(scanner.nextLine());
                return ChooserUtils.chooseEntityFrom(companies, option);
            } catch (IllegalArgumentException e) {
                System.out.println("Incorrect input. Try again or return back.\n");
            }
        } while (true);
    }
}
