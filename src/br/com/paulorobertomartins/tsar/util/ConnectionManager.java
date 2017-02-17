package br.com.paulorobertomartins.tsar.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author paulo.martins
 */
public final class ConnectionManager {

    private final String DB_URL = "jdbc:postgresql://localhost:5432/tsar";
    private final String DB_USER = "postgres";
    private final String DB_PASSWORD = "a";
    private static final ConnectionManager INSTANCE = new ConnectionManager();

    private Connection connection;

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        return INSTANCE;
    }
    
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return this.getConnection().prepareStatement(sql);
    }

    private Connection getPostgresConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.connection == null) {
            this.connection = this.getPostgresConnection();
        }
        return this.connection;
    }
}
