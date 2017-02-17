package br.com.paulorobertomartins.tsar.repository.impl;

import br.com.paulorobertomartins.tsar.model.Profile;
import br.com.paulorobertomartins.tsar.repository.ProfileRepository;
import br.com.paulorobertomartins.tsar.repository.UserRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paulo.martins
 */
public class ProfileRepositoryJDBCImpl extends AbstractJDBCRepository implements ProfileRepository {

    private final UserRepository userRepository;

    public ProfileRepositoryJDBCImpl() {
        this.userRepository = new UserRepositoryJDBCImpl();
    }

    @Override
    public void create(Profile entity) throws SQLException {
        final String command = "INSERT INTO profile (user_id, first_name, middle_name, last_name, "
                + "\"position\", company, update_at, created_at ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setLong(1, entity.getUser().getUserId());
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getMiddleName());
            preparedStatement.setString(4, entity.getLastName());
            preparedStatement.setString(5, entity.getPosition());
            preparedStatement.setString(6, entity.getCompany());
            preparedStatement.setDate(7, new java.sql.Date(new Date().getTime()));
            preparedStatement.setDate(8, new java.sql.Date(new Date().getTime()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public void update(Profile entity) {
        final String command = "UPDATE profile SET first_name = ?, middle_name = ?, last_name = ?, "
                + "\"position\" = ?, company = ?, update_at = ?"
                + "WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getMiddleName());
            preparedStatement.setString(3, entity.getLastName());
            preparedStatement.setString(4, entity.getPosition());
            preparedStatement.setString(5, entity.getCompany());
            preparedStatement.setDate(6, new java.sql.Date(new Date().getTime()));
            preparedStatement.setLong(7, entity.getUser().getUserId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Profile entity) throws SQLException {
        this.deleteById(entity.getUser().getUserId());
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        final String command = "DELETE FROM profile WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public boolean exists(Profile entity) throws SQLException {
        return this.findById(entity.getUser().getUserId()) != null;
    }

    @Override
    public Profile findById(Long id) throws SQLException {
        final String command = "SELECT user_id, first_name, middle_name, last_name, \"position\", company, update_at, created_at  FROM profile WHERE user_id = ?";
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Profile profile = new Profile();
                profile.setUser(userRepository.findById(resultSet.getLong("user_id")));
                profile.setFirstName(resultSet.getString("first_name"));
                profile.setMiddleName(resultSet.getString("middle_name"));
                profile.setLastName(resultSet.getString("last_name"));
                profile.setPosition(resultSet.getString("position"));
                profile.setCompany(resultSet.getString("company"));
                profile.setUpdatedAt(resultSet.getDate("update_at"));
                profile.setCreatedAt(resultSet.getDate("created_at"));
                return profile;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfileRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return null;
    }

    @Override
    public List<Profile> listAll() throws SQLException {
        final String command = "SELECT user_id, first_name, middle_name, last_name, \"position\", company, update_at, created_at  FROM profile";

        List<Profile> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Profile profile = new Profile();
                profile.setUser(userRepository.findById(resultSet.getLong("user_id")));
                profile.setFirstName(resultSet.getString("first_name"));
                profile.setMiddleName(resultSet.getString("middle_name"));
                profile.setLastName(resultSet.getString("last_name"));
                profile.setPosition(resultSet.getString("position"));
                profile.setCompany(resultSet.getString("company"));
                profile.setUpdatedAt(resultSet.getDate("update_at"));
                profile.setCreatedAt(resultSet.getDate("created_at"));
                list.add(profile);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfileRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return list;
    }

    @Override
    public long count() throws SQLException {

        long result = 0;
        final String command = "SELECT COUNT(user_id) FROM profile";
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getLong(1);
        } catch (SQLException ex) {
            Logger.getLogger(ProfileRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return result;
    }
}
