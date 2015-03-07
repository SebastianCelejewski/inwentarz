package sebcel.inwentarz.gui.comparators;

import java.util.Comparator;
import java.util.Date;

import sebcel.inwentarz.dao.dto.BookStatus;

public interface IComparatorFactory {
    
    public Comparator<Date> getDateComparator();
    
    public Comparator<String> getAuthorComparator();
    
    public Comparator<BookStatus> getBookStatusComparator();
    
    public Comparator<Object> getPriceComparator();

}
