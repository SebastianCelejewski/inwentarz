package sebcel.inwentarz.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sebcel.inwentarz.dao.definition.IBookDao;
import sebcel.inwentarz.dao.definition.IScontrumDao;
import sebcel.inwentarz.dao.dto.BookCreationData;
import sebcel.inwentarz.dao.dto.BookDetails;
import sebcel.inwentarz.dao.dto.BookEditData;
import sebcel.inwentarz.dao.dto.BookListElement;
import sebcel.inwentarz.dao.dto.BookPrintData;
import sebcel.inwentarz.dao.dto.BookRegisterListElement;
import sebcel.inwentarz.dao.dto.BookStatus;
import sebcel.inwentarz.dao.utils.InwentarzException;
import sebcel.inwentarz.dao.utils.ListElement;
import sebcel.inwentarz.dao.utils.TypOperacji;
import sebcel.inwentarz.event.IDataChangeListener;

public class BookDao implements IBookDao {

    private IConnectionFactory connectionFactory;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DecimalFormat cf = new DecimalFormat("0.00");

    private IScontrumDao scontrumDao;
    private IDataChangeListener dataChangeListener;

    public BookDao(IConnectionFactory connectionFactory, IScontrumDao scontrumDao, IDataChangeListener dataChangeListener) {
        this.connectionFactory = connectionFactory;
        this.scontrumDao = scontrumDao;
        this.dataChangeListener = dataChangeListener;
    }

    public List<BookListElement> getBookList() {
        System.out.println("[BookDao][GetBookList]");
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "SELECT id, tytul, status, data_wlaczenia, cena, wartosc, zrodlo FROM ksiazki WHERE status != 99";
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);
            List<BookListElement> result = new ArrayList<BookListElement>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String tytul = rs.getString("tytul");
                String autorzy = null;
                int statusId = rs.getInt("status");
                BookStatus status = BookStatus.parse(statusId);
                Date dataWlaczenia = rs.getDate("data_wlaczenia");
                Double cena = rs.getDouble("cena");
                Double wartosc = rs.getDouble("wartosc");
                String zrodlo = rs.getString("zrodlo");
                Boolean verified = null;
                BookListElement element = new BookListElement(id, autorzy, tytul, status, dataWlaczenia, zrodlo, cena, wartosc, verified);
                result.add(element);
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Error while loading book list: " + ex.getMessage(), ex);
        }
    }

    public String getAuthorNamesForBook(int bookId) {
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "SELECT autorzy.imiona as imiona, autorzy.nazwisko as nazwisko, autorzy.id as id FROM autorzy INNER JOIN autorzy_ksiazki ON autorzy.id = autorzy_ksiazki.id_autora INNER JOIN ksiazki ON autorzy_ksiazki.id_ksiazki = ksiazki.id WHERE ksiazki.id = "
                    + bookId;
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);
            StringBuffer result = new StringBuffer();
            while (rs.next()) {
                String imiona = rs.getString("imiona");
                String nazwisko = rs.getString("nazwisko");
                if (result.length() > 0) {
                    result.append(", ");
                }
                result.append(new String(imiona + " " + nazwisko).trim());
            }
            return result.toString();

        } catch (Exception ex) {
            throw new RuntimeException("Error while loading authors for book " + bookId + ": " + ex.getMessage(), ex);
        }
    }

    public BookEditData getBookEditData(int bookId) {
        try {
            BookEditData result = new BookEditData();
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "SELECT id, tytul, status, data_wlaczenia, cena, wartosc, zrodlo, uwagi FROM ksiazki WHERE id = " + bookId;
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);

            rs.next();
            int id = rs.getInt("id");
            String tytul = rs.getString("tytul");
            int statusId = rs.getInt("status");
            Date dataWlaczenia = rs.getDate("data_wlaczenia");
            Double cena = rs.getDouble("cena");
            Double wartosc = rs.getDouble("wartosc");
            String zrodlo = rs.getString("zrodlo");
            String uwagi = rs.getString("uwagi");

            result.setId(id);
            result.setTytul(tytul);
            result.setStatus(BookStatus.parse(statusId));
            result.setZrodlo(zrodlo);
            result.setDataWlaczenia(dataWlaczenia);
            result.setCena(cena);
            result.setWartosc(wartosc);
            result.setAutorzy(getAuthorsForBook(bookId));
            result.setUwagi(uwagi);

            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Error while loading data for book " + bookId + ": " + ex.getMessage(), ex);
        }
    }

    public BookDetails getBookDetails(int bookId) {
        try {
            BookDetails result = new BookDetails();
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "SELECT id, tytul, status, data_wlaczenia, data_weryfikacji, cena, wartosc, zrodlo, uwagi FROM ksiazki WHERE id = " + bookId;
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);

            rs.next();
            int id = rs.getInt("id");
            String tytul = rs.getString("tytul");
            int statusId = rs.getInt("status");
            Date dataWlaczenia = rs.getDate("data_wlaczenia");
            Double cena = rs.getDouble("cena");
            Double wartosc = rs.getDouble("wartosc");
            String zrodlo = rs.getString("zrodlo");
            String uwagi = rs.getString("uwagi");
            Date dataOstatniejWeryfikacji = rs.getDate("data_weryfikacji");

            result.setId(id);
            result.setTytul(tytul);
            result.setStatus(BookStatus.parse(statusId).getName());
            result.setZrodlo(zrodlo);
            result.setDataWlaczenia(dataWlaczenia);
            result.setCena(cena);
            result.setWartosc(wartosc);
            result.setAutorzy(getAuthorNamesForBook(bookId));
            result.setUwagi(uwagi);
            result.setDataOstatniejWeryfikacji(dataOstatniejWeryfikacji);

            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Error while loading data for book " + bookId + ": " + ex.getMessage(), ex);
        }
    }

    public Set<ListElement> getAuthorsForBook(int bookId) {
        System.out.println("Loading author names for book " + bookId);
        Set<ListElement> result = new HashSet<ListElement>();
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "SELECT autorzy.id as id, autorzy.imiona as imiona, autorzy.nazwisko as nazwisko FROM autorzy INNER JOIN autorzy_ksiazki ON autorzy.id = autorzy_ksiazki.id_autora INNER JOIN ksiazki ON autorzy_ksiazki.id_ksiazki = ksiazki.id WHERE ksiazki.id = "
                    + bookId;
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String imiona = rs.getString("imiona");
                String nazwisko = rs.getString("nazwisko");
                result.add(new ListElement(id, imiona + " " + nazwisko));
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Error while loading authors for book " + bookId + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public void updateBook(BookEditData modifiedEditData) {
        System.out.println("Updating book");

        Connection connection = connectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);

            Date modificationDate = Calendar.getInstance().getTime();

            Statement statement = connection.createStatement();
            String query = "UPDATE ksiazki SET ";
            query += "tytul = '" + modifiedEditData.getTytul() + "', ";
            query += "status = " + modifiedEditData.getStatus().getId() + ", ";
            if (modifiedEditData.getDataWlaczenia() != null) {
                query += "data_wlaczenia = '" + df.format(modifiedEditData.getDataWlaczenia()) + "', ";
            } else {
                query += "data_wlaczenia = null, ";
            }
            query += "data_aktualizacji = '" + dtf.format(modificationDate) + "', ";
            query += "cena = " + modifiedEditData.getCena() + ", ";
            query += "wartosc = " + modifiedEditData.getWartosc() + ", ";
            query += "zrodlo = '" + modifiedEditData.getZrodloId() + "', ";
            if (modifiedEditData.getUwagi() != null) {
                query += "uwagi = '" + modifiedEditData.getUwagi() + "' ";
            } else {
                query += "uwagi = null, ";
            }
            query += " WHERE id = " + modifiedEditData.getId();
            System.out.println("SQL: " + query);
            statement.execute(query);

            statement = connection.createStatement();
            query = "DELETE from autorzy_ksiazki where id_ksiazki = " + modifiedEditData.getId();
            System.out.println("SQL: " + query);
            statement.execute(query);

            if (modifiedEditData.getAutorzy() != null && modifiedEditData.getAutorzy().size() > 0) {
                for (ListElement author : modifiedEditData.getAutorzy()) {
                    statement = connection.createStatement();
                    query = "INSERT INTO autorzy_ksiazki (id_autora, id_ksiazki) VALUES (" + author.getId() + ", " + modifiedEditData.getId() + ")";
                    System.out.println("SQL: " + query);
                    statement.execute(query);
                }
            }

            String opis = createUpdateBookDescription(modifiedEditData);

            statement = connection.createStatement();
            query = "INSERT INTO rejestr_operacji (id_ksiazki, typ, data, opis) values (" + modifiedEditData.getId() + ", " + TypOperacji.ZmianaDanych.getId() + ", '" + dtf.format(modificationDate) + "', '" + opis + "')";
            statement.execute(query);

            connection.commit();

            dataChangeListener.dataChanged();
        } catch (Exception ex) {
            try {
                connection.rollback();
            } catch (Exception e) {
                throw new RuntimeException("Error while updating book: " + ex.getMessage(), ex);
            }
            throw new RuntimeException("Error while updating book: " + ex.getMessage(), ex);
        }
    }

    @Override
    public int createBook(BookCreationData bookData) {
        System.out.println("Creating book");

        Connection connection = connectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);

            Date creationDate = Calendar.getInstance().getTime();

            Statement statement = connection.createStatement();
            String query = "INSERT INTO ksiazki (id, tytul, status, data_wlaczenia, data_utworzenia, data_aktualizacji, cena, wartosc, zrodlo, uwagi) VALUES ";
            query += "( null, ";
            query += "'" + bookData.getTytul() + "', ";
            query += BookStatus.DOSTEPNA.getId() + ", ";
            if (bookData.getDataWlaczenia() != null) {
                query += "'" + df.format(bookData.getDataWlaczenia()) + "', ";
            } else {
                query += "null, ";
            }
            query += "'" + dtf.format(creationDate) + "', ";
            query += "'" + dtf.format(creationDate) + "', ";
            query += bookData.getCena() + ", ";
            query += bookData.getWartosc() + ", ";
            query += "'" + bookData.getZrodloId() + "',";
            query += "'" + bookData.getUwagi() + "') ";

            System.out.println("SQL: " + query);
            statement.execute(query);
            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int bookId = keys.getInt(1);

            if (bookData.getAutorzy() != null && bookData.getAutorzy().size() > 0) {
                for (ListElement author : bookData.getAutorzy()) {
                    statement = connection.createStatement();
                    query = "INSERT INTO autorzy_ksiazki (id_autora, id_ksiazki) VALUES (" + author.getId() + ", " + bookId + ")";
                    System.out.println("SQL: " + query);
                    statement.execute(query);
                }
            }

            String opis = createBookCreationDescription(bookData);

            statement = connection.createStatement();
            query = "INSERT INTO rejestr_operacji (id_ksiazki, typ, data, opis) values (" + bookId + ", " + TypOperacji.Utworzenie.getId() + ", '" + dtf.format(creationDate) + "', '" + opis + "')";
            statement.execute(query);

            connection.commit();

            dataChangeListener.dataChanged();
            return bookId;
        } catch (Exception ex) {
            try {
                connection.rollback();
            } catch (Exception e) {
                throw new RuntimeException("Error while updating book: " + ex.getMessage(), ex);
            }
            throw new RuntimeException("Error while updating book: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteBook(int bookId) {
        System.out.println("Deleting book");
        Connection connection = connectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);

            Date deleteDate = Calendar.getInstance().getTime();

            Statement statement = connection.createStatement();
            String query = "UPDATE ksiazki SET status = 2 WHERE id = " + bookId;
            System.out.println("SQL: " + query);
            statement.execute(query);

            statement = connection.createStatement();
            query = "INSERT INTO rejestr_operacji (id_ksiazki, typ, data) values (" + bookId + ", " + TypOperacji.Wycofanie.getId() + ", '" + dtf.format(deleteDate) + "')";
            statement.execute(query);

            connection.commit();

            dataChangeListener.dataChanged();
        } catch (Exception ex) {
            try {
                connection.rollback();
            } catch (Exception e) {
                throw new RuntimeException("Error while deleting book: " + ex.getMessage(), ex);
            }
            throw new RuntimeException("Error while deleting book: " + ex.getMessage(), ex);
        }
    }

    private String createBookCreationDescription(BookCreationData bookData) {
        StringBuilder result = new StringBuilder();
        if (bookData.getAutorzy() != null && bookData.getAutorzy().size() > 0) {
            StringBuilder a = new StringBuilder();
            for (ListElement autor : bookData.getAutorzy()) {
                if (a.length() > 0) {
                    a.append(", ");
                }
                a.append(autor.getDescription());
            }
            result.append("Autorzy: " + a.toString());
        }

        if (result.length() > 0) {
            result.append("\n");
        }
        result.append("Tytuł: " + bookData.getTytul() + "\n");

        if (bookData.getCena() != null) {
            result.append("Cena: " + cf.format(bookData.getCena()) + "\n");
        }

        if (bookData.getWartosc() != null) {
            result.append("Wartość: " + cf.format(bookData.getWartosc()) + "\n");
        }

        if (bookData.getDataWlaczenia() != null) {
            result.append("Data włączenia: " + df.format(bookData.getDataWlaczenia()) + "\n");
        }

        result.append("Źródło: " + bookData.getZrodloId() + "\n");
        result.append("Status: " + BookStatus.DOSTEPNA + "\n");
        result.append("Uwagi: " + bookData.getUwagi() + "\n");
        return result.toString();
    }

    private String createUpdateBookDescription(BookEditData bookData) {
        StringBuilder result = new StringBuilder();
        if (bookData.getAutorzy() != null && bookData.getAutorzy().size() > 0) {
            StringBuilder a = new StringBuilder();
            for (ListElement autor : bookData.getAutorzy()) {
                if (a.length() > 0) {
                    a.append(", ");
                }
                a.append(autor.getDescription());
            }
            result.append("Autorzy: " + a.toString());
        }

        if (result.length() > 0) {
            result.append("\n");
        }
        result.append("Tytuł: " + bookData.getTytul() + "\n");

        if (bookData.getCena() != null) {
            result.append("Cena: " + cf.format(bookData.getCena()) + "\n");
        }

        if (bookData.getWartosc() != null) {
            result.append("Wartość: " + cf.format(bookData.getWartosc()) + "\n");
        }

        if (bookData.getDataWlaczenia() != null) {
            result.append("Data włączenia: " + df.format(bookData.getDataWlaczenia()) + "\n");
        }

        result.append("Źródło: " + bookData.getZrodloId() + "\n");
        result.append("Status: " + bookData.getStatus() + "\n");
        result.append("Uwagi: " + bookData.getUwagi() + "\n");
        return result.toString();
    }

    @Override
    public List<BookRegisterListElement> getBookRegisterList(int bookId) {
        System.out.println("Loading book register");
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "SELECT id, typ, opis, data from rejestr_operacji where id_ksiazki = " + bookId;
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);
            List<BookRegisterListElement> result = new ArrayList<BookRegisterListElement>();
            while (rs.next()) {
                int id = rs.getInt("id");
                int typId = rs.getInt("typ");
                String opis = rs.getString("opis");
                Date data = rs.getTimestamp("data");
                BookRegisterListElement element = new BookRegisterListElement(id, data, TypOperacji.valueOf(typId).toString(), opis);
                result.add(element);
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Error while loading book register: " + ex.getMessage(), ex);
        }
    }

    public BookPrintData getBookPrintData(int bookId) {
        try {
            BookPrintData result = new BookPrintData();
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "SELECT id, tytul, data_wlaczenia FROM ksiazki WHERE id = " + bookId;
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);

            rs.next();
            int id = rs.getInt("id");
            String tytul = rs.getString("tytul");
            Date dataWlaczenia = rs.getDate("data_wlaczenia");

            result.setId(id);
            result.setTytul(tytul);
            result.setDataWlaczenia(dataWlaczenia);
            result.setAutorzy(getAuthorNamesForBook(bookId));

            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Error while loading data for book " + bookId + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public void hardDeleteBook(int bookId) {
        System.out.println("Hard deleting book");
        Connection connection = connectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);

            Date deleteDate = Calendar.getInstance().getTime();

            Statement statement = connection.createStatement();
            String query = "DELETE FROM autorzy_ksiazki where id_ksiazki = " + bookId;
            System.out.println("SQL: " + query);
            statement.execute(query);

            statement = connection.createStatement();
            query = "UPDATE ksiazki SET status = 99 WHERE id = " + bookId;
            System.out.println("SQL: " + query);
            statement.execute(query);

            statement = connection.createStatement();
            query = "INSERT INTO rejestr_operacji (id_ksiazki, typ, data) values (" + bookId + ", " + TypOperacji.Skasowanie.getId() + ", '" + dtf.format(deleteDate) + "')";
            statement.execute(query);

            dataChangeListener.dataChanged();
            connection.commit();
        } catch (Exception ex) {
            try {
                connection.rollback();
            } catch (Exception e) {
                throw new RuntimeException("Error while deleting book: " + ex.getMessage(), ex);
            }
            throw new RuntimeException("Error while deleting book: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void lendBook(int bookId) {
        System.out.println("Lending book");
        Connection connection = connectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);

            Date deleteDate = Calendar.getInstance().getTime();

            Statement statement = connection.createStatement();
            String query = "UPDATE ksiazki SET status = 1 WHERE id = " + bookId;
            System.out.println("SQL: " + query);
            statement.execute(query);

            statement = connection.createStatement();
            query = "INSERT INTO rejestr_operacji (id_ksiazki, typ, data) values (" + bookId + ", " + TypOperacji.Wypozyczenie.getId() + ", '" + dtf.format(deleteDate) + "')";
            statement.execute(query);

            connection.commit();

            dataChangeListener.dataChanged();
        } catch (Exception ex) {
            try {
                connection.rollback();
            } catch (Exception e) {
                throw new RuntimeException("Error while deleting book: " + ex.getMessage(), ex);
            }
            throw new RuntimeException("Error while deleting book: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void returnBook(int bookId) {
        System.out.println("Returning book");
        Connection connection = connectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);

            Date deleteDate = Calendar.getInstance().getTime();

            Statement statement = connection.createStatement();
            String query = "UPDATE ksiazki SET status = 0 WHERE id = " + bookId;
            System.out.println("SQL: " + query);
            statement.execute(query);

            statement = connection.createStatement();
            query = "INSERT INTO rejestr_operacji (id_ksiazki, typ, data) values (" + bookId + ", " + TypOperacji.Zwrot.getId() + ", '" + dtf.format(deleteDate) + "')";
            statement.execute(query);

            connection.commit();

            dataChangeListener.dataChanged();
        } catch (Exception ex) {
            try {
                connection.rollback();
            } catch (Exception e) {
                throw new RuntimeException("Error while deleting book: " + ex.getMessage(), ex);
            }
            throw new RuntimeException("Error while deleting book: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void verifyBookExistence(int bookId) {
        System.out.println("Verifying book existence");
        Connection connection = connectionFactory.getConnection();
        try {
            connection.setAutoCommit(false);

            Date verificationDate = Calendar.getInstance().getTime();

            Statement statement = connection.createStatement();
            String query = "UPDATE ksiazki SET data_weryfikacji = '" + dtf.format(verificationDate) + "' WHERE id = " + bookId;
            System.out.println("SQL: " + query);
            statement.execute(query);

            statement = connection.createStatement();
            query = "INSERT INTO rejestr_operacji (id_ksiazki, typ, data) values (" + bookId + ", " + TypOperacji.Weryfikacja.getId() + ", '" + dtf.format(verificationDate) + "')";
            System.out.println(query);
            statement.execute(query);

            if (!scontrumDao.isScontrumOpen()) {
                throw new InwentarzException("Skontrum nie zostało rozpoczête. Rozpocznij nowe skontrum aby weryfikować posiadane książki.");
            }

            int verificationId = scontrumDao.getOpenScontrumId();

            statement = connection.createStatement();
            query = "INSERT INTO ksiazki_skontrum (id_skontrum, id_ksiazki, data_weryfikacji) values (" + verificationId + ", " + bookId + ", '" + dtf.format(verificationDate) + "')";
            System.out.println(query);
            statement.execute(query);
            connection.commit();

            dataChangeListener.dataChanged();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (Exception e) {
                throw new RuntimeException("Error while verifying book: " + ex.getMessage(), ex);
            }
            throw new RuntimeException("Error while verifying book: " + ex.getMessage(), ex);
        }
    }

    @Override
    public boolean getBookVerification(int bookId) {
        System.out.println("Loading verification data for book " + bookId);
        try {
            if (!scontrumDao.isScontrumOpen()) {
                throw new RuntimeException("Scontrum nie jest rozpoczęte");
            }
            int verificationId = scontrumDao.getOpenScontrumId();
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "SELECT count(*) FROM ksiazki_skontrum where id_skontrum = " + verificationId + " and id_ksiazki = " + bookId;
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count == 1) {
                    return true;
                } else {
                    return false;
                }
            }
            throw new RuntimeException("Brak danych na temat weryfikacji książki " + bookId);
        } catch (Exception ex) {
            throw new RuntimeException("Error while loading verification information for book " + bookId + ": " + ex.getMessage(), ex);
        }
    }
}