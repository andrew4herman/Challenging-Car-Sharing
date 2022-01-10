package carsharing.database.dao;

import carsharing.database.DBManager;
import carsharing.model.Car;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDao {

    private static final String GET_BY_ID = "SELECT * FROM car WHERE id = ?;";
    private static final String GET_ALL = "SELECT * FROM car;";
    private static final String GET_CARS_BY_AVAILABILITY = "SELECT * FROM car WHERE is_rented = ?;";
    private static final String SAVE_CAR = "INSERT INTO car(name, company_id) VALUES(?, ?);";
    private static final String UPDATE_CAR_BY_ID = "UPDATE car SET is_rented = ? WHERE ID = ?;";
    private static final String GET_LAST_ID = "SELECT MAX(id) as last_id FROM car;";

    private final DBManager manager;
    private int lastCarId;

    public CarDao(DBManager manager) {
        this.manager = manager;
        this.lastCarId = getLastCarId();
    }

    public Optional<Car> getById(int id) {
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(GET_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Car(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("company_id"),
                        resultSet.getBoolean("is_rented")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get from database a car with id " + id, e);
        }
        return Optional.empty();
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(GET_ALL)) {
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                cars.add(new Car(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("company_id"),
                        resultSet.getBoolean("is_rented")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get cars from database", e);
        }

        return cars;
    }

    public List<Car> getCarsByAvailability(boolean rented) {
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(GET_CARS_BY_AVAILABILITY)) {
            stmt.setBoolean(1, rented);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                cars.add(new Car(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("company_id"),
                        resultSet.getBoolean("is_rented")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get all rented/unrented cars from database", e);
        }

        return cars;
    }

    public int save(String name, int companyId) {
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(SAVE_CAR)) {
            stmt.setString(1, name);
            stmt.setInt(2, companyId);
            stmt.executeUpdate();

            manager.getConnection().commit();
            return ++lastCarId;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save car %s from company %d".formatted(name, companyId), e);
        }
    }

    public void updateCar(int id, boolean rented) {
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(UPDATE_CAR_BY_ID)) {
            stmt.setBoolean(1, rented);
            stmt.setInt(2, id);
            stmt.executeUpdate();

            manager.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot update car with id " + id, e);
        }
    }

    private int getLastCarId() {
        int lastId = 0;
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(GET_LAST_ID)) {
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                lastId = resultSet.getInt("last_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get id of the last car", e);
        }

        return lastId;
    }
}
