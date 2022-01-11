package carsharing.activities;

import carsharing.database.dao.CarDao;
import carsharing.database.dao.CompanyDao;
import carsharing.model.Customer;

import java.util.Scanner;

public class CustomerActivity extends Activity {

    private final Customer currentCustomer;
    private final CompanyDao companyDao;
    private final CarDao carDao;

    public CustomerActivity(Scanner scanner, Customer customer, CompanyDao companyDao, CarDao carDao) {
        super(scanner);
        this.currentCustomer = customer;
        this.companyDao = companyDao;
        this.carDao = carDao;
    }

    @Override
    public void showMenu() {

    }

    @Override
    public void processOption(String option) {

    }
}
