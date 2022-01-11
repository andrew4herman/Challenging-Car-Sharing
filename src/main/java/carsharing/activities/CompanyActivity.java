package carsharing.activities;

import carsharing.database.dao.CarDao;
import carsharing.model.Company;

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

    }

    @Override
    protected void processOption(String option) {

    }
}
