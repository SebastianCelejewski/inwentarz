package sebcel.inwentarz.dao.jdbc;

import java.sql.Connection;

public interface IConnectionFactory {

    public Connection getConnection();
}