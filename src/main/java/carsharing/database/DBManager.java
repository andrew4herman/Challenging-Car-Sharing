package carsharing.database;

import carsharing.database.dao.CarDao;
import carsharing.database.dao.CompanyDao;
import carsharing.database.dao.CustomerDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class DBManager {

    private final DBConnector dbConnector;
    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;

    public DBManager(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
        this.companyDao = new CompanyDao(dbConnector);
        this.carDao = new CarDao(dbConnector);
        this.customerDao = new CustomerDao(dbConnector);
    }

    public void makeTransaction(Runnable runnable) {
        try {
            Connection connection = dbConnector.getConnection();
            Savepoint savepoint = connection.setSavepoint();

            try {
                runnable.run();
            } catch (RuntimeException e) {
                connection.rollback(savepoint);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot rollback to savepoint!", e);
        }
    }

    public CompanyDao getCompanyDao() {
        return companyDao;
    }

    public CarDao getCarDao() {
        return carDao;
    }

    public CustomerDao getCustomerDao() {
        return customerDao;
    }
}
