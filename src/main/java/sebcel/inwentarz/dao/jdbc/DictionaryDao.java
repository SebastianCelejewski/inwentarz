package sebcel.inwentarz.dao.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sebcel.inwentarz.dao.definition.IDictionaryDao;

public class DictionaryDao implements IDictionaryDao {

    private IConnectionFactory connectionFactory;

    public DictionaryDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<String> getBookSourceList() {
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "select * from zrodla";
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);
            List<String> result = new ArrayList<String>();
            while (rs.next()) {
                String nazwa = rs.getString("nazwa");
                result.add(nazwa);
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Exception while reading book source list: " + ex.getMessage(), ex);
        }
    }
}
