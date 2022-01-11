package carsharing.activities;

import carsharing.database.dao.CarDao;

import java.util.Scanner;

public class CompanyActivity extends Activity {

    private final CarDao carDao;

    public CompanyActivity(Scanner scanner, CarDao carDao) {
        super(scanner);
        this.carDao = carDao;
    }

    @Override
    protected void showMenu() {

    }

    @Override
    protected void processOption(String option) {

    }
}
