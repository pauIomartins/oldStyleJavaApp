package br.com.paulorobertomartins.tsar.repository.impl;

import br.com.paulorobertomartins.tsar.model.User;
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
public class UserRepositoryJDBCImpl extends AbstractJDBCRepository implements UserRepository {

    public UserRepositoryJDBCImpl() {
    }

    @Override
    public void create(User entity) {
        final String command = "INSERT INTO \"user\" (user_id, email, created_at) VALUES (?, ? , ?)";
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(new Date().getTime()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(User entity) {
        final String command = "UPDATE \"user\" SET user_id = ?, email = ?, created_at = ? WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(entity.getCreatedAt().getTime()));
            preparedStatement.setLong(4, entity.getUserId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
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
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
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
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("user_id"));
                user.setEmail(rs.getString("email"));
                user.setCreatedAt(new Date());
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public List<User> listAll() {
        final String command = "SELECT user_id, email, created_at FROM \"user\"";

        List<User> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getLong("user_id"));
                user.setEmail(resultSet.getString("email"));
                user.setCreatedAt(new Date());
                list.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
        return list;
    }

    @Override
    public long count() {
        final String command = "SELECT COUNT(user_id) FROM \"user\"";

        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException ex) {
            Logger.getLogger(UserRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }
}
