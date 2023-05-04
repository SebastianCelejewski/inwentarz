package sebcel.inwentarz.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import sebcel.inwentarz.dao.definition.IAuthorDao;
import sebcel.inwentarz.dao.dto.AuthorCreationData;
import sebcel.inwentarz.dao.dto.AuthorEditData;
import sebcel.inwentarz.dao.dto.AuthorListElement;
import sebcel.inwentarz.dao.utils.InwentarzException;
import sebcel.inwentarz.dao.utils.ListElement;

public class AuthorDao implements IAuthorDao {

    private IConnectionFactory connectionFactory;

    public AuthorDao(IConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<ListElement> getAuthorList(String string) {
        try {
            List<ListElement> result = new ArrayList<ListElement>();
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "select * from autorzy where (imiona like '%" + string + "%') OR (nazwisko like '%" + string + "%')";
            query += " ORDER BY nazwisko";
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String imiona = rs.getString("imiona");
                String nazwisko = rs.getString("nazwisko");
                result.add(new ListElement(id, (imiona + " " + nazwisko).trim()));
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Error while getting author list: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<AuthorListElement> getAuthorList() {
        try {
            List<AuthorListElement> result = new ArrayList<AuthorListElement>();
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "select * from autorzy order by nazwisko";
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String imiona = rs.getString("imiona");
                String nazwisko = rs.getString("nazwisko");
                result.add(new AuthorListElement(id, imiona, nazwisko));
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Error while getting author list: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteAuthor(int id) {
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "select id_ksiazki from autorzy_ksiazki where id_autora = " + id;
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                throw new InwentarzException("Nie mo�na usun�� tego autora, poniewa� baza zawiera jego ksi��ki. Najpierw usu� ksi��ki tego autora, potem jego samego.");
            }
            statement = connectionFactory.getConnection().createStatement();
            query = "delete from autorzy where id = " + id;
            System.out.println("SQL: " + query);
            statement.execute(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error while deleting author: " + ex.getMessage(), ex);
        }
    }

    @Override
    public int createAuthor(AuthorCreationData authorCreationData) {
        try {
            String query = "insert into autorzy (imiona, nazwisko) values";
            query += "('" + authorCreationData.getImiona() + "', ";
            query += "'" + authorCreationData.getNazwisko() + "')";
            System.out.println("SQL: " + query);
            PreparedStatement statement = connectionFactory.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);
            return id;
        } catch (Exception ex) {
            throw new RuntimeException("Error while creating new author: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void updateAuthor(AuthorEditData modifiedEditData) {
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "update autorzy set ";
            query += "imiona = '" + modifiedEditData.getImiona() + "', ";
            query += "nazwisko = '" + modifiedEditData.getNazwisko() + "' ";
            query += "where id = " + modifiedEditData.getId();
            System.out.println("SQL: " + query);
            statement.execute(query);
        } catch (Exception ex) {
            throw new RuntimeException("Error while creating new author: " + ex.getMessage(), ex);
        }
    }

    @Override
    public AuthorEditData getAuthorEditData(int id) {
        try {
            AuthorEditData result = new AuthorEditData();
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "select id, imiona, nazwisko from autorzy where id=" + id;
            System.out.println("SQL: " + query);
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            String imiona = resultSet.getString("imiona");
            String nazwisko = resultSet.getString("nazwisko");

            result.setId(id);
            result.setImiona(imiona);
            result.setNazwisko(nazwisko);

            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Error while deleting author " + id + ": " + ex.getMessage(), ex);
        }
    }
}