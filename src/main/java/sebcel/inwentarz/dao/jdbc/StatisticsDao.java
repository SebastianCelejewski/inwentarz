package sebcel.inwentarz.dao.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;

import sebcel.inwentarz.dao.definition.IStatisticsDao;
import sebcel.inwentarz.dao.dto.BookStatisticsData;
import sebcel.inwentarz.dao.dto.PurchaseStatistics;
import sebcel.inwentarz.dao.dto.PurchaseStatisticsEntry;

public class StatisticsDao implements IStatisticsDao {

    private IConnectionFactory connectionFactory;

    public StatisticsDao(IConnectionFactory connectionFactory) {
	this.connectionFactory = connectionFactory;
    }

    @Override
    public BookStatisticsData getBookStatisticsData() {
	System.out.println("[StatisticsDaoDao][GetBookStatisticsData]");
	try {

	    int totalBooks = -1;
	    int existingBooks = -1;
	    int notExistingBooks = -1;
	    int availableBooks = -1;
	    int nonAvailableBooks = -1;

	    double totalPrice = -1.0;
	    double totalValue = -1.0;

	    Statement statement = connectionFactory.getConnection().createStatement();
	    String query = "select count(id) from ksiazki where status != 99";
	    System.out.println("SQL: " + query);
	    ResultSet rs = statement.executeQuery(query);
	    if (rs.next()) {
		totalBooks = rs.getInt(1);
	    }

	    statement = connectionFactory.getConnection().createStatement();
	    query = "select count(id) from ksiazki where status != 99 and (status = 0 or status = 1)";
	    System.out.println("SQL: " + query);
	    rs = statement.executeQuery(query);
	    if (rs.next()) {
		existingBooks = rs.getInt(1);
	    }

	    statement = connectionFactory.getConnection().createStatement();
	    query = "select count(id) from ksiazki where status != 99 and status = 2";
	    System.out.println("SQL: " + query);
	    rs = statement.executeQuery(query);
	    if (rs.next()) {
		notExistingBooks = rs.getInt(1);
	    }

	    if (totalBooks != existingBooks + notExistingBooks) {
		throw new RuntimeException("Liczba wszystkich ksi¹¿ek (" + totalBooks + ") nie jest równa sumie liczby posiadanych ksi¹¿ek (" + existingBooks + ") i liczby wycofanych ksi¹¿ek (" + notExistingBooks + ").");
	    }

	    statement = connectionFactory.getConnection().createStatement();
	    query = "select count(id) from ksiazki where status = 0";
	    System.out.println("SQL: " + query);
	    rs = statement.executeQuery(query);
	    if (rs.next()) {
		availableBooks = rs.getInt(1);
	    }

	    statement = connectionFactory.getConnection().createStatement();
	    query = "select count(id) from ksiazki where status = 1";
	    System.out.println("SQL: " + query);
	    rs = statement.executeQuery(query);
	    if (rs.next()) {
		nonAvailableBooks = rs.getInt(1);
	    }

	    if (availableBooks + nonAvailableBooks != existingBooks) {
		throw new RuntimeException("Number of existing books (" + existingBooks + ") is not equal to the sum of available books (" + availableBooks + ") and non available books (" + nonAvailableBooks + ").");
	    }

	    statement = connectionFactory.getConnection().createStatement();
	    query = "SELECT sum(cena) FROM ksiazki k where status != 99";
	    System.out.println("SQL: " + query);
	    rs = statement.executeQuery(query);
	    if (rs.next()) {
		totalPrice = rs.getDouble(1);
	    }

	    statement = connectionFactory.getConnection().createStatement();
	    query = "SELECT sum(wartosc) FROM ksiazki k where status != 99 and status != 2";
	    System.out.println("SQL: " + query);
	    rs = statement.executeQuery(query);
	    if (rs.next()) {
		totalValue = rs.getDouble(1);
	    }

	    return new BookStatisticsData(totalBooks, existingBooks, notExistingBooks, availableBooks, nonAvailableBooks, totalPrice, totalValue);

	} catch (Exception ex) {
	    throw new RuntimeException("Error while loading book statistics data: " + ex.getMessage(), ex);
	}

    }

    @Override
    public PurchaseStatistics getPurchaseStatistics() {
	System.out.println("[StatisticsDaoDao][GetPurchaseStatisticsData]");
	try {
	    Statement statement = connectionFactory.getConnection().createStatement();
	    PurchaseStatistics result = new PurchaseStatistics();
	    String query = "select year(data_wlaczenia) as period, sum(cena) as expenses, sum(wartosc) as value, count(id) as number from ksiazki group by period";
	    System.out.println("SQL: " + query);
	    ResultSet rs = statement.executeQuery(query);
	    while (rs.next()) {
		String period = (String) rs.getString("period");
		int number = (int) rs.getInt("number");
		double expenses = (double) rs.getDouble("expenses");
		double value = (double) rs.getDouble("value");
		PurchaseStatisticsEntry entry = new PurchaseStatisticsEntry(period, number, expenses, value);
		result.add(entry);
	    }
	    return result;
	} catch (Exception ex) {
	    throw new RuntimeException("Error while loading book list: " + ex.getMessage(), ex);
	}
    }

}
