package carsharing.database.dao;

import carsharing.database.DBConnector;

public class DaoContainer {

    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;

    public DaoContainer(DBConnector dbConnector) {
        this.companyDao = new CompanyDao(dbConnector);
        this.carDao = new CarDao(dbConnector);
        this.customerDao = new CustomerDao(dbConnector);
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
