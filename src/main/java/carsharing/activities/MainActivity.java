package carsharing.activities;

import carsharing.database.dao.CarDao;
import carsharing.database.dao.CompanyDao;
import carsharing.database.dao.CustomerDao;

import java.util.Scanner;

public class MainActivity implements Activity {

    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;
    private final Scanner scanner;

    public MainActivity(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.customerDao = customerDao;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void start() {
        
    }
}
