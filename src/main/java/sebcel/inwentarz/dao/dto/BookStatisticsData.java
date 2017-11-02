package sebcel.inwentarz.dao.dto;

public class BookStatisticsData {

    private int totalBooks = 0;
    private int existingBooks = 0;
    private int notExistingBooks = 0;
    private int availableBooks = 0;
    private int nonAvailableBooks = 0;

    private double totalBooksPrice = 0.0;
    private double totalBooksValue = 0.0;

    public BookStatisticsData(int totalBooks, int existingBooks, int notExistingBooks, int availableBooks, int nonAvailableBooks, double totalBooksPrice, double totalBooksValue) {
        this.totalBooks = totalBooks;
        this.existingBooks = existingBooks;
        this.notExistingBooks = notExistingBooks;
        this.availableBooks = availableBooks;
        this.nonAvailableBooks = nonAvailableBooks;
        this.totalBooksPrice = totalBooksPrice;
        this.totalBooksValue = totalBooksValue;
    }

    public int getTotalBooks() {
        return totalBooks;
    }

    public int getExistingBooks() {
        return existingBooks;
    }

    public int getNotExistingBooks() {
        return notExistingBooks;
    }

    public int getAvailableBooks() {
        return availableBooks;
    }

    public int getNonAvailableBooks() {
        return nonAvailableBooks;
    }

    public double getTotalBooksPrice() {
        return totalBooksPrice;
    }

    public double getTotalBooksValue() {
        return totalBooksValue;
    }
}