package carsharing.activities;

import carsharing.database.dao.CarDao;
import carsharing.database.dao.CompanyDao;
import carsharing.database.dao.CustomerDao;

import java.util.Scanner;

public class ManagerActivity implements Activity{

    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;
    private final Scanner scanner;

    public ManagerActivity(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao, Scanner scanner) {
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.customerDao = customerDao;
        this.scanner = scanner;
    }

    @Override
    public void start() {
        
    }
}
