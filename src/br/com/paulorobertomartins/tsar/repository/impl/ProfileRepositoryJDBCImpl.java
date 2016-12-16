package br.com.paulorobertomartins.tsar.repository.impl;

import br.com.paulorobertomartins.tsar.model.Profile;
import br.com.paulorobertomartins.tsar.repository.ProfileRepository;
import br.com.paulorobertomartins.tsar.repository.UserRepository;
import br.com.paulorobertomartins.tsar.util.ConnectionFactory;
import java.sql.Connection;
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
public class ProfileRepositoryJDBCImpl implements ProfileRepository {

    private Connection connection;    
    private final UserRepository userRepository;

    public ProfileRepositoryJDBCImpl() {
        this.userRepository = new UserRepositoryJDBCImpl();
    }

    @Override
    public void create(Profile entity) {
        final String command = "INSERT INTO profile (user_id, first_name, middle_name, last_name, "
                + "\"position\", company, update_at, created_at ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            try {
                // Get JDBC connection
                connection = ConnectionFactory.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(command);
                preparedStatement.setLong(1, entity.getUser().getUserId());
                preparedStatement.setString(2, entity.getFirstName());
                preparedStatement.setString(3, entity.getMiddleName());
                preparedStatement.setString(4, entity.getLastName());
                preparedStatement.setString(5, entity.getPosition());
                preparedStatement.setString(6, entity.getCompany());
                preparedStatement.setDate(7, new java.sql.Date(new Date().getTime()));
                preparedStatement.setDate(8, new java.sql.Date(new Date().getTime()));
                preparedStatement.executeQuery();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfileRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Profile entity) {
        final String command = "UPDATE profile SET first_name = ?, middle_name = ?, last_name = ?, "
                + "\"position\" = ?, company = ?, update_at = ?"
                + "WHERE user_id = ?";
        try {
            try {
                // Get JDBC connection
                connection = ConnectionFactory.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(command);                
                preparedStatement.setString(1, entity.getFirstName());
                preparedStatement.setString(2, entity.getMiddleName());
                preparedStatement.setString(3, entity.getLastName());
                preparedStatement.setString(4, entity.getPosition());
                preparedStatement.setString(5, entity.getCompany());
                preparedStatement.setDate(6, new java.sql.Date(new Date().getTime()));                
                preparedStatement.setLong(7, entity.getUser().getUserId());
                preparedStatement.executeQuery();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfileRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Profile entity) {
        this.deleteById(entity.getUser().getUserId());
    }

    @Override
    public void deleteById(Long id) {
        final String command = "DELETE FROM profile WHERE user_id = ?";
        try {
            try {
                // Get JDBC connection
                connection = ConnectionFactory.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(command);
                preparedStatement.setLong(1, id);
                preparedStatement.executeQuery();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfileRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @Override
    public boolean exists(Profile entity) {
        return this.findById(entity.getUser().getUserId()) != null;
    }

    @Override
    public Profile findById(Long id) {
        final String command = "SELECT user_id, first_name, middle_name, last_name, \"position\", company, update_at, created_at  FROM profile WHERE user_id = ?";
        ResultSet resultSet;
        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            try {
                while (resultSet.next()) {
                    Profile profile = new Profile();
                    profile.setUser(userRepository.findById(resultSet.getLong("user_id")));
                    profile.setFirstName(resultSet.getString("first_name"));
                    profile.setMiddleName(resultSet.getString("middle_name"));
                    profile.setLastName(resultSet.getString("last_name"));
                    profile.setPosition(resultSet.getString("\"position\""));
                    profile.setCompany(resultSet.getString("company"));
                    profile.setUpdatedAt(resultSet.getDate("update_at"));
                    profile.setCreatedAt(resultSet.getDate("created_at"));
                    return profile;
                }
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfileRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Profile> listAll() {
        // Make SQL query to retrive all users
        final String command = "SELECT user_id, first_name, middle_name, last_name, \"position\", company, update_at, created_at  FROM profile";

        // Create a new list to return
        List<Profile> list = new ArrayList<>();

        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            // Get Connection and prepare SQL Query to execute
            PreparedStatement preparedStatement = connection.prepareStatement(command);

            // Execute SQL query and get ResultSet with all Profiles
            ResultSet resultSet = preparedStatement.executeQuery();
            try {
                // Loop all Profiles in ResultSet
                while (resultSet.next()) {

                    // For each line create a new Profile and populate it 
                    Profile profile = new Profile();
                    profile.setUser(userRepository.findById(resultSet.getLong("user_id")));
                    profile.setFirstName(resultSet.getString("first_name"));
                    profile.setMiddleName(resultSet.getString("middle_name"));
                    profile.setLastName(resultSet.getString("last_name"));
                    profile.setPosition(resultSet.getString("\"position\""));
                    profile.setCompany(resultSet.getString("company"));
                    profile.setUpdatedAt(resultSet.getDate("update_at"));
                    profile.setCreatedAt(resultSet.getDate("created_at"));                    

                    // Add user to the list that will be returned
                    list.add(profile);
                }
            } finally {
                // Close database connection
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfileRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public long count() {
        // Make SQL query to retrive users count
        final String command = "SELECT COUNT(user_id) FROM profile";

        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            // Get Connection and prepare SQL Query to execute
            PreparedStatement preparedStatement = connection.prepareStatement(command);

            // Execute SQL query and get ResultSet with all Profiles
            ResultSet resultSet = preparedStatement.executeQuery();
            try {
                resultSet.next();
                return resultSet.getLong(1);
            } finally {
                // Close database connection
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfileRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}