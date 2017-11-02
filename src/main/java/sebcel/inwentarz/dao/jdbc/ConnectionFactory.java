package sebcel.inwentarz.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory implements IConnectionFactory {

    private Connection connection;

    private String driverClass;
    private String url;

    public ConnectionFactory(String driverClass, String url) {
        this.driverClass = driverClass;
        this.url = url;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(driverClass);
                connection = DriverManager.getConnection(url);
            } catch (Exception ex) {
                throw new RuntimeException("Could not create database connection: " + ex.getMessage(), ex);
            }
        }
        return connection;
    }
}