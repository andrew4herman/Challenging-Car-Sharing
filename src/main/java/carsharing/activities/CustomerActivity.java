package carsharing.activities;

import carsharing.database.dao.CarDao;
import carsharing.database.dao.CompanyDao;

import java.util.Scanner;

public class CustomerActivity implements Activity {

    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final Scanner scanner;

    public CustomerActivity(CompanyDao companyDao, CarDao carDao, Scanner scanner) {
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.scanner = scanner;
    }

    @Override
    public void start() {

    }
}
