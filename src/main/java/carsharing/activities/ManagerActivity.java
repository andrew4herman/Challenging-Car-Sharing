package carsharing.activities;

import carsharing.database.dao.DBManager;
import carsharing.model.Company;
import carsharing.util.ChooserUtils;

import java.util.List;
import java.util.Scanner;

public class ManagerActivity extends Activity {

    private final DBManager dbManager;

    public ManagerActivity(Scanner scanner, DBManager dbManager) {
        super(scanner);
        this.dbManager = dbManager;
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
        List<Company> companies = dbManager.getCompanyDao().getAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Choose the company:");
            ChooserUtils.chooseEntityFrom(companies, scanner).ifPresent(
                    company -> {
                        System.out.printf("'%s' company", company.getName());
                        new CompanyActivity(scanner, dbManager, company).start();
                    });
        }
    }

    private void createCompanyOption() {
        System.out.println("\nEnter the company name:");
        String companyName = scanner.nextLine();

        dbManager.getCompanyDao().save(companyName);
        System.out.println("The company was created!");
    }
}
