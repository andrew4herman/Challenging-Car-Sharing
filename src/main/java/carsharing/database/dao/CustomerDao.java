package carsharing.database.dao;

import carsharing.database.DBManager;
import carsharing.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDao {

    private static final String GET_BY_ID = "SELECT * FROM customer WHERE id = ?;";
    private static final String GET_ALL = "SELECT * FROM customer";
    private static final String SAVE_CUSTOMER = "INSERT INTO customer(name) VALUES(?);";
    private static final String UPDATE_BY_ID = "UPDATE customer SET rented_car_id = ? WHERE id = ?;";
    private static final String GET_LAST_ID = "SELECT MAX(id) as last_id FROM customer;";

    private final DBManager manager;
    private int lastCustomerId;

    public CustomerDao(DBManager manager) {
        this.manager = manager;
        this.lastCustomerId = getLastCustomerId();
    }

    public Optional<Customer> getById(int id) {
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(GET_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int rentedCarId = resultSet.getInt("rented_car_id");

                return Optional.of(new Customer(id, name, rentedCarId));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get from database a customer with id" + id, e);
        }
        return Optional.empty();
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(GET_ALL)) {
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int rentedCarId = resultSet.getInt("rented_car_id");

                customers.add(new Customer(id, name, rentedCarId));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get customers from database", e);
        }

        return customers;
    }

    public int save(String name) {
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(SAVE_CUSTOMER)) {
            stmt.setString(1, name);
            stmt.executeUpdate();

            manager.getConnection().commit();
            return ++lastCustomerId;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save customer " + name, e);
        }
    }

    public void updateCustomer(int id, int rentedCarId) {
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(UPDATE_BY_ID)) {
            stmt.setInt(1, rentedCarId);
            stmt.setInt(2, id);
            stmt.executeUpdate();

            manager.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot update customer with id" + id, e);
        }
    }

    private int getLastCustomerId() {
        int lastId = 0;
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(GET_LAST_ID)) {
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                lastId = resultSet.getInt("last_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get id of the last customer", e);
        }

        return lastId;
    }

}
