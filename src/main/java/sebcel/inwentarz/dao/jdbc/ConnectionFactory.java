package sebcel.inwentarz.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory implements IConnectionFactory {

    private Connection connection;

    private String url;

    public ConnectionFactory(String url) {
        this.url = url;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url);
            } catch (Exception ex) {
                throw new RuntimeException("Could not create database connection: " + ex.getMessage(), ex);
            }
        }
        return connection;
    }
}