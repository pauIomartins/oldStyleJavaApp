package br.com.paulorobertomartins.tsar.repository.impl;

import br.com.paulorobertomartins.tsar.model.Password;
import br.com.paulorobertomartins.tsar.repository.PasswordRepository;
import br.com.paulorobertomartins.tsar.repository.UserRepository;
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
public class PasswordRepositoryJDBCImpl extends AbstractJDBCRepository implements PasswordRepository {

    private Connection connection;
    private final UserRepository userRepository;

    public PasswordRepositoryJDBCImpl() {
        this.userRepository = new UserRepositoryJDBCImpl();
    }

    @Override
    public void create(Password entity) throws SQLException {
        final String command = "INSERT INTO \"password\" (password_id, \"password\", user_id, created_at, active) VALUES (?, ? , ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setLong(1, entity.getPasswordId());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setLong(3, entity.getUser().getUserId());
            preparedStatement.setDate(4, new java.sql.Date(new Date().getTime()));
            preparedStatement.setBoolean(5, entity.getActive());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PasswordRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public void update(Password entity) throws SQLException {
        final String command = "UPDATE \"password\" SET \"password\" = ?, user_id = ?, active = ? WHERE password_id = ?";
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setString(1, entity.getPassword());
            preparedStatement.setLong(2, entity.getUser().getUserId());
            preparedStatement.setBoolean(3, entity.getActive());
            preparedStatement.setLong(4, entity.getPasswordId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PasswordRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public void delete(Password entity) throws SQLException {
        this.deleteById(entity.getPasswordId());
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        final String command = "DELETE FROM \"password\" WHERE password_id = ?";
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PasswordRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public boolean exists(Password entity) throws SQLException {
        return this.findById(entity.getPasswordId()) != null;
    }

    @Override
    public Password findById(Long id) throws SQLException {
        final String command = "SELECT password_id, \"password\", user_id, created_at, active FROM \"password\" WHERE password_id = ?";
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
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
            throw ex;
        }
        return null;
    }

    @Override
    public List<Password> listAll() throws SQLException {
        final String command = "SELECT password_id, \"content\", user_id, created_at, update_at FROM \"password\"";

        List<Password> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Password password = new Password();
                password.setPasswordId(resultSet.getLong("password_id"));
                password.setPassword(resultSet.getString("\"password\""));
                password.setUser(userRepository.findById(resultSet.getLong("user_id")));
                password.setCreatedAt(resultSet.getDate("created_at"));
                password.setActive(resultSet.getBoolean("active"));
                list.add(password);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PasswordRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return list;
    }

    @Override
    public long count() throws SQLException {
        final String command = "SELECT COUNT(password_id) FROM \"password\"";
        try {
            PreparedStatement preparedStatement = this.connectionManager.getPreparedStatement(command);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException ex) {
            Logger.getLogger(PasswordRepositoryJDBCImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
}
