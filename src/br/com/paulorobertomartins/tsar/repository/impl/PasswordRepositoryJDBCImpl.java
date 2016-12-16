package br.com.paulorobertomartins.tsar.repository.impl;

import br.com.paulorobertomartins.tsar.model.Password;
import br.com.paulorobertomartins.tsar.repository.PasswordRepository;
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
public class PasswordRepositoryJDBCImpl implements PasswordRepository {

    private Connection connection;
    private final UserRepository userRepository;

    public PasswordRepositoryJDBCImpl() {
        this.userRepository = new UserRepositoryJDBCImpl();
    }

    @Override
    public void create(Password entity) {
        final String command = "INSERT INTO \"password\" (password_id, \"password\", user_id, created_at, active) VALUES (?, ? , ?, ?, ?)";
        try {
            try {
                // Get JDBC connection
                connection = ConnectionFactory.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(command);
                preparedStatement.setLong(1, entity.getPasswordId());
                preparedStatement.setString(2, entity.getPassword());
                preparedStatement.setLong(3, entity.getUser().getUserId());
                preparedStatement.setDate(4, new java.sql.Date(new Date().getTime()));
                preparedStatement.setBoolean(5, entity.getActive());
                preparedStatement.executeQuery();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PasswordRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Password entity) {
        final String command = "UPDATE \"password\" SET \"password\" = ?, user_id = ?, active = ? WHERE password_id = ?";
        try {
            try {
                // Get JDBC connection
                connection = ConnectionFactory.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(command);
                preparedStatement.setString(1, entity.getPassword());
                preparedStatement.setLong(2, entity.getUser().getUserId());
                preparedStatement.setBoolean(3, entity.getActive());
                preparedStatement.setLong(4, entity.getPasswordId());
                preparedStatement.executeQuery();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PasswordRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Password entity) {
        this.deleteById(entity.getPasswordId());
    }

    @Override
    public void deleteById(Long id) {
        final String command = "DELETE FROM \"password\" WHERE password_id = ?";
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
            Logger.getLogger(PasswordRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @Override
    public boolean exists(Password entity) {
        return this.findById(entity.getPasswordId()) != null;
    }

    @Override
    public Password findById(Long id) {
        final String command = "SELECT password_id, \"password\", user_id, created_at, active FROM \"password\" WHERE password_id = ?";
        ResultSet resultSet;
        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            try {
                while (resultSet.next()) {
                    Password password = new Password();
                    password.setPasswordId(resultSet.getLong("password_id"));
                    password.setPassword(resultSet.getString("\"password\""));
                    password.setUser(userRepository.findById(resultSet.getLong("user_id")));
                    password.setCreatedAt(resultSet.getDate("created_at"));                    
                    password.setActive(resultSet.getBoolean("active"));
                    return password;
                }
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PasswordRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Password> listAll() {
        // Make SQL query to retrive all passwords
        final String command = "SELECT password_id, \"content\", user_id, created_at, update_at FROM \"password\"";

        // Create a new list to return
        List<Password> list = new ArrayList<>();

        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            // Get Connection and prepare SQL Query to execute
            PreparedStatement preparedStatement = connection.prepareStatement(command);

            // Execute SQL query and get ResultSet with all Passwords
            ResultSet resultSet = preparedStatement.executeQuery();
            try {
                // Loop all Passwords in ResultSet
                while (resultSet.next()) {

                    // For each line create a new Password and populate it 
                    Password password = new Password();
                    password.setPasswordId(resultSet.getLong("password_id"));
                    password.setPassword(resultSet.getString("\"password\""));
                    password.setUser(userRepository.findById(resultSet.getLong("user_id")));
                    password.setCreatedAt(resultSet.getDate("created_at"));                    
                    password.setActive(resultSet.getBoolean("active"));                 

                    // Add password to the list that will be returned
                    list.add(password);
                }
            } finally {
                // Close database connection
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PasswordRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public long count() {
        // Make SQL query to retrive passwords count
        final String command = "SELECT COUNT(password_id) FROM \"password\"";

        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            // Get Connection and prepare SQL Query to execute
            PreparedStatement preparedStatement = connection.prepareStatement(command);

            // Execute SQL query and get ResultSet with all Passwords
            ResultSet resultSet = preparedStatement.executeQuery();
            try {
                resultSet.next();
                return resultSet.getLong(1);
            } finally {
                // Close database connection
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PasswordRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
