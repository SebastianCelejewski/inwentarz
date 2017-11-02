package sebcel.inwentarz.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sebcel.inwentarz.dao.definition.IScontrumDao;
import sebcel.inwentarz.dao.dto.ScontrumDetails;
import sebcel.inwentarz.dao.dto.ScontrumListElement;
import sebcel.inwentarz.dao.dto.ScontrumStatisticsData;
import sebcel.inwentarz.dao.dto.ScontrumStatus;
import sebcel.inwentarz.dao.utils.InwentarzException;

public class ScontrumDao implements IScontrumDao {

    private IConnectionFactory connectionFactory;
    private DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ScontrumDao(IConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public boolean isScontrumOpen() {
        System.out.println("[ScontrumDao][IsScontrumOpen]");

        Connection connection = connectionFactory.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "select count(*) from skontrum where status='ROZPOCZETE'";
            System.out.println(query);
            ResultSet result = statement.executeQuery(query);
            if (result == null) {
                throw new RuntimeException("Error while checking verification status: null result set");
            }
            result.next();

            int numberOfOpenVerifications = result.getInt(1);

            if (numberOfOpenVerifications == 0) {
                return false;
            }
            if (numberOfOpenVerifications == 1) {
                return true;
            }
            throw new InwentarzException("Rozpocz�te jest wi�cej, ni� jedno skontrum. Sprawd� sp�jno�� bazy danych.");

        } catch (Exception ex) {
            throw new RuntimeException("Error while checking verification status: " + ex.getMessage(), ex);
        }
    }

    @Override
    public int getOpenScontrumId() {
        System.out.println("[ScontrumDao][GetOpenScontrumId]");

        Connection connection = connectionFactory.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "select id from skontrum where status='ROZPOCZETE'";
            System.out.println(query);
            ResultSet result = statement.executeQuery(query);
            if (result == null) {
                throw new RuntimeException("Error while getting open verification id: null result set");
            }
            result.next();
            int verificationId = result.getInt(1);
            return verificationId;
        } catch (Exception ex) {
            throw new RuntimeException("Error while loading open scontrum id: " + ex.getMessage(), ex);
        }
    }

    @Override
    public ScontrumStatisticsData getScontrumStatistics(int scontrumId) {
        System.out.println("[ScontrumDao][GetScontrumStatistics]");
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            ScontrumStatisticsData result = new ScontrumStatisticsData();
            String query = "select id, data_rozpoczecia, data_zakonczenia from skontrum where id = " + scontrumId;
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = (int) rs.getInt("id");
                Date dataRozpoczecia = (Date) rs.getDate("data_rozpoczecia");
                Date dataZakonczenia = (Date) rs.getDate("data_zakonczenia");

                result.setId(id);
                result.setDataRozpoczecia(dataRozpoczecia);
                result.setDataZakonczenia(dataZakonczenia);
            }

            statement = connectionFactory.getConnection().createStatement();
            query = "select count(id) from ksiazki where status != 99 and (status = 0 or status = 1)";
            System.out.println("SQL: " + query);
            rs = statement.executeQuery(query);
            if (rs.next()) {
                result.setLiczbaPosiadanychKsiazek(rs.getInt(1));
            }

            statement = connectionFactory.getConnection().createStatement();
            query = "SELECT count(distinct id_ksiazki) FROM ksiazki_skontrum where id_skontrum = " + scontrumId;
            System.out.println("SQL: " + query);
            rs = statement.executeQuery(query);
            if (rs.next()) {
                result.setLiczbaZweryfikowanychKsiazek(rs.getInt(1));
            }

            result.setLiczbaNiezweryfikowanychKsiazek(result.getLiczbaPosiadanychKsiazek() - result.getLiczbaZweryfikowanychKsiazek());

            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Error while loading scontrum statistics: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<ScontrumListElement> getScontrumList() {
        List<ScontrumListElement> result = new ArrayList<ScontrumListElement>();

        System.out.println("[ScontrumDao][GetScontrumList]");
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "select id, data_rozpoczecia, data_zakonczenia, status, liczba_posiadanych_ksiazek, liczba_zweryfikowanych_ksiazek, liczba_niezweryfikowanych_ksiazek from skontrum";
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                ScontrumListElement element = new ScontrumListElement();

                element.setId((int) rs.getInt("id"));
                element.setDataRozpoczecia((Date) rs.getDate("data_rozpoczecia"));
                element.setDataZakonczenia((Date) rs.getDate("data_zakonczenia"));
                element.setStatus(ScontrumStatus.valueOf((String) rs.getString("status")));
                element.setLiczbaPosiadanychKsiazek((int) rs.getInt("liczba_posiadanych_ksiazek"));
                element.setLiczbaZweryfikowanychKsiazek((int) rs.getInt("liczba_zweryfikowanych_ksiazek"));
                element.setLiczbaNiezweryfikowanychKsiazek((int) rs.getInt("liczba_niezweryfikowanych_ksiazek"));

                result.add(element);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error while loading scontrum list: " + ex.getMessage(), ex);
        }

        return result;
    }

    @Override
    public ScontrumDetails getScontrumDetails(int scontrumId) {
        System.out.println("[ScontrumDao][GetScontrumDetails] " + scontrumId);
        try {
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "select id, data_rozpoczecia, data_zakonczenia, status, liczba_posiadanych_ksiazek, liczba_zweryfikowanych_ksiazek, liczba_niezweryfikowanych_ksiazek from skontrum where id = " + scontrumId;
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                ScontrumDetails result = new ScontrumDetails();
                result.setId((int) rs.getInt("id"));
                result.setDataRozpoczecia((Date) rs.getDate("data_rozpoczecia"));
                result.setDataZakonczenia((Date) rs.getDate("data_zakonczenia"));
                result.setStatus(ScontrumStatus.valueOf((String) rs.getString("status")));
                result.setLiczbaPosiadanychKsiazek((int) rs.getInt("liczba_posiadanych_ksiazek"));
                result.setLiczbaZweryfikowanychKsiazek((int) rs.getInt("liczba_zweryfikowanych_ksiazek"));
                result.setLiczbaNiezweryfikowanychKsiazek((int) rs.getInt("liczba_niezweryfikowanych_ksiazek"));

                return result;
            } else {
                throw new RuntimeException("No data found for scontrum " + scontrumId);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error while loading scontrum details: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void closeScontrum(int scontrumId) {
        System.out.println("[ScontrumDao][CloseScontrum] " + scontrumId);
        try {
            Date dataZakonczenia = Calendar.getInstance().getTime();
            ScontrumStatisticsData statistics = getScontrumStatistics(scontrumId);
            Statement statement = connectionFactory.getConnection().createStatement();
            String query = "UPDATE skontrum SET ";
            query += "data_zakonczenia = '" + dtf.format(dataZakonczenia) + "', ";
            query += "status = '" + ScontrumStatus.ZAKONCZONE + "', ";
            query += "liczba_posiadanych_ksiazek = " + statistics.getLiczbaPosiadanychKsiazek() + ", ";
            query += "liczba_zweryfikowanych_ksiazek = " + statistics.getLiczbaZweryfikowanychKsiazek() + ", ";
            query += "liczba_niezweryfikowanych_ksiazek = " + statistics.getLiczbaNiezweryfikowanychKsiazek() + " ";
            query += "where id = " + scontrumId;
            System.out.println("SQL: " + query);
            statement.execute(query);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to close scontrum " + scontrumId + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public void startScontrum() {
        System.out.println("[ScontrumDao][StartScontrum]");
        try {
            if (isScontrumOpen()) {
                throw new RuntimeException("Scontrum is already open.");
            }
            Statement statement = connectionFactory.getConnection().createStatement();
            Date dataRozpoczecia = Calendar.getInstance().getTime();
            String query = "INSERT INTO skontrum (data_rozpoczecia, status) VALUES ('" + dtf.format(dataRozpoczecia) + "', '" + ScontrumStatus.ROZPOCZETE + "')";
            System.out.println(query);
            statement.execute(query);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to start scontrum: " + ex.getMessage(), ex);
        }
    }
}