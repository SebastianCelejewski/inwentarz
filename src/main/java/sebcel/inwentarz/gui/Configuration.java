package sebcel.inwentarz.gui;

public class Configuration {

    private String databaseDriver;
    private String connectionString;

    public String getDatabaseDriver() {
	return databaseDriver;
    }

    public void setDatabaseDriver(String databaseDriver) {
	this.databaseDriver = databaseDriver;
    }

    public String getConnectionString() {
	return connectionString;
    }

    public void setConnectionString(String connectionString) {
	this.connectionString = connectionString;
    }
}
