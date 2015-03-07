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
		// Class.forName("net.sourceforge.jtds.jdbc.Driver");
		// connection = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1177/Inwentarz");

		// Class.forName("com.mysql.jdbc.Driver");
		// connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inwentarz?user=inwentarz&password=inwentarz");

		Class.forName(driverClass);
		connection = DriverManager.getConnection(url);
	    } catch (Exception ex) {
		throw new RuntimeException("Could not create database connection: " + ex.getMessage(), ex);
	    }
	}
	return connection;
    }
}