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

    }

    @Override
    protected void processOption(String option) {

    }
}
