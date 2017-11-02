package sebcel.inwentarz.dao.dto;

public class PurchaseStatisticsEntry {

    private String period;
    private double expenses;
    private double value;
    private int number;

    public PurchaseStatisticsEntry(String period, int number, double expenses, double value) {
        this.period = period;
        this.number = number;
        this.expenses = expenses;
        this.value = value;
    }

    public String getPeriod() {
        return period;
    }

    public int getNumber() {
        return number;
    }

    public double getValue() {
        return value;
    }

    public double getExpenses() {
        return expenses;
    }
}