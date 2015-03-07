package sebcel.inwentarz.gui.comparators;

import java.util.Comparator;

import sebcel.inwentarz.dao.dto.BookStatus;

public class StatusComparator implements Comparator<BookStatus> {

    @Override
    public int compare(BookStatus o1, BookStatus o2) {
	int i = 3;
	return o1.compareTo(o2);
    }
}