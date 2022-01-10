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

    public final String GET_BY_ID = "SELECT * FROM company WHERE id = ?;";
    public final String GET_All = "SELECT * FROM company;";
    public final String SAVE_COMPANY = "INSERT INTO company(name) VALUES(?);";
    public final String GET_LAST_ID = "SELECT MAX(id) as last_id FROM company;";

    private final DBManager manager;
    private int lastCompanyId;

    public CompanyDao(DBManager manager) {
        this.manager = manager;
        this.lastCompanyId = getLastCompanyId();
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

    public int save(String name) {
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(SAVE_COMPANY)) {
            stmt.setString(1, name);
            stmt.executeUpdate();

            manager.getConnection().commit();
            return ++lastCompanyId;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot save company " + name, e);
        }
    }

    private int getLastCompanyId() {
        int lastId = 0;
        try (PreparedStatement stmt =
                     manager.getConnection().prepareStatement(GET_LAST_ID)) {
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                lastId = resultSet.getInt("last_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get id of the last company", e);
        }

        return lastId;
    }
}
