package br.com.paulorobertomartins.tsar.repository.impl;

import br.com.paulorobertomartins.tsar.model.User;
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
public class UserRepositoryJDBCImpl implements UserRepository {

    private Connection connection;

    public UserRepositoryJDBCImpl() {
    }

    @Override
    public void create(User entity) {
        final String command = "INSERT INTO \"user\" (user_id, email, created_at) VALUES (?, ? , ?)";
        try {
            try {
                // Get JDBC connection
                connection = ConnectionFactory.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(command);
                preparedStatement.setLong(1, entity.getUserId());
                preparedStatement.setString(2, entity.getEmail());
                preparedStatement.setDate(3, new java.sql.Date(entity.getCreatedAt().getTime()));
                preparedStatement.executeQuery();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(User entity) {
        final String command = "UPDATE \"user\" SET user_id = ?, email = ?, created_at = ? WHERE user_id = ?";
        try {
            try {
                // Get JDBC connection
                connection = ConnectionFactory.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement(command);
                preparedStatement.setLong(1, entity.getUserId());
                preparedStatement.setString(2, entity.getEmail());
                preparedStatement.setDate(3, new java.sql.Date(entity.getCreatedAt().getTime()));
                preparedStatement.setLong(4, entity.getUserId());
                preparedStatement.executeQuery();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(User entity) {
        this.deleteById(entity.getUserId());
    }

    @Override
    public void deleteById(Long id) {
        final String command = "DELETE FROM \"user\" WHERE user_id = ?";
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
            Logger.getLogger(UserRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @Override
    public boolean exists(User entity) {
        return this.findById(entity.getUserId()) != null;
    }

    @Override
    public User findById(Long id) {
        final String command = "SELECT user_id, email, created_at FROM \"user\" WHERE user_id = ?";
        ResultSet rs;
        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            try {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getLong("user_id"));
                    user.setEmail(rs.getString("email"));
                    user.setCreatedAt(new Date());
                    return user;
                }
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<User> listAll() {
        // Make SQL query to retrive all users
        final String command = "SELECT user_id, email, created_at FROM \"user\"";

        // Create a new list to return
        List<User> list = new ArrayList<>();

        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            // Get Connection and prepare SQL Query to execute
            PreparedStatement preparedStatement = connection.prepareStatement(command);

            // Execute SQL query and get ResultSet with all Users
            ResultSet resultSet = preparedStatement.executeQuery();
            try {
                // Loop all Users in ResultSet
                while (resultSet.next()) {

                    // For each line create a new User and populate it 
                    User user = new User();
                    user.setUserId(resultSet.getLong("user_id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setCreatedAt(new Date());

                    // Add user to the list that will be returned
                    list.add(user);
                }
            } finally {
                // Close database connection
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public long count() {
        // Make SQL query to retrive users count
        final String command = "SELECT COUNT(user_id) FROM \"user\"";

        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            // Get Connection and prepare SQL Query to execute
            PreparedStatement preparedStatement = connection.prepareStatement(command);

            // Execute SQL query and get ResultSet with all Users
            ResultSet resultSet = preparedStatement.executeQuery();
            try {
                resultSet.next();
                return resultSet.getLong(1);
            } finally {
                // Close database connection
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
