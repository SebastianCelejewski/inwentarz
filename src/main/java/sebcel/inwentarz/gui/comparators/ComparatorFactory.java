package sebcel.inwentarz.gui.comparators;

import java.util.Comparator;
import java.util.Date;

import sebcel.inwentarz.dao.dto.BookStatus;

public class ComparatorFactory implements IComparatorFactory {

    private AuthorComparator authorComparator = new AuthorComparator();
    private DateComparator dateComparator = new DateComparator();
    private PriceComparator priceComparator = new PriceComparator();
    private StatusComparator statusComparator = new StatusComparator();

    @Override
    public Comparator<String> getAuthorComparator() {
        return authorComparator;
    }

    @Override
    public Comparator<BookStatus> getBookStatusComparator() {
        return statusComparator;
    }

    @Override
    public Comparator<Date> getDateComparator() {
        return dateComparator;
    }

    @Override
    public Comparator<Object> getPriceComparator() {
        return priceComparator;
    }
}