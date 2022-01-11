package carsharing.database.dao;

import carsharing.database.DBManager;
import carsharing.model.Company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyDao {

    private static final String GET_BY_ID = "SELECT * FROM company WHERE id = ?;";
    private static final String GET_All = "SELECT * FROM company;";
    private static final String SAVE_COMPANY = "INSERT INTO company(name) VALUES(?);";

    private final DBManager manager;

    public CompanyDao(DBManager manager) {
        this.manager = manager;
    }

    public Optional<Company> getById(int id) {
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(GET_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Company(
                        resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get from database a company with id " + id, e);
        }
        return Optional.empty();
    }

    public List<Company> getAll() {
        List<Company> companies = new ArrayList<>();
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(GET_All)) {
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                companies.add(new Company(
                        resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get companies from database", e);
        }

        return companies;
    }

    public void save(String name) {
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(SAVE_COMPANY)) {
            stmt.setString(1, name);
            stmt.executeUpdate();

            manager.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save company " + name, e);
        }
    }
}
