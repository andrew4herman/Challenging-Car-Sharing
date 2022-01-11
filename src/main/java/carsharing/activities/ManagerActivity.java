package carsharing.activities;

import carsharing.database.dao.CarDao;
import carsharing.database.dao.CompanyDao;
import carsharing.database.dao.CustomerDao;

import java.util.Scanner;

public class ManagerActivity extends Activity{

    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;

    public ManagerActivity(Scanner scanner, CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        super(scanner);
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.customerDao = customerDao;
    }

    @Override
    public void showMenu() {

    }

    @Override
    public void processOption(String option) {

    }
}
