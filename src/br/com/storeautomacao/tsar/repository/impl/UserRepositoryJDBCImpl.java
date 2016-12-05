package br.com.storeautomacao.tsar.repository.impl;

import br.com.storeautomacao.tsar.model.User;
import br.com.storeautomacao.tsar.repository.UserRepository;
import br.com.storeautomacao.tsar.util.ConnectionFactory;
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

    }

    @Override
    public void update(User entity) {
    }

    @Override
    public void delete(User entity) {
        final String selectSql = "DELETE FROM \"user\" WHERE user_id = ?";

        try {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
                preparedStatement.setLong(0, entity.getUserId());
                preparedStatement.executeQuery();
            } finally {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void deleteById(Long id) {
        this.delete(this.findById(id));
    }    

    @Override
    public boolean exists(User entity) {
        return this.findById(entity.getUserId()) != null;
    }
    
    @Override
    public User findById(Long id) {
        final String selectSql = "SELECT user_id, email, created_at FROM \"user\" WHERE user_id = ?";

        ResultSet rs;
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setLong(0, id);
            rs = preparedStatement.executeQuery();
            try {
                while (rs.next()) {
                    User user = new User();
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
        final String selectSql = "SELECT user_id, email, created_at FROM \"user\"";

        // Create a new list to return
        List<User> list = new ArrayList<>();

        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            // Get Connection and prepare SQL Query to execute
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);

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
        final String selectSql = "SELECT COUNT(user_id) FROM \"user\"";

        try {
            // Get JDBC connection
            connection = ConnectionFactory.getConnection();

            // Get Connection and prepare SQL Query to execute
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);

            // Execute SQL query and get ResultSet with all Users
            ResultSet resultSet = preparedStatement.executeQuery();
            try {                
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
