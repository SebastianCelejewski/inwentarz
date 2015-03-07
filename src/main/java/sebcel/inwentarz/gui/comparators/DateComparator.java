package sebcel.inwentarz.gui.comparators;

import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<Date> {

    @Override
    public int compare(Date o1, Date o2) {
	if (o1 == o2) {
	    return 0;
	}
	if (o1 == null) {
	    return -1;
	}
	return o1.compareTo(o2);
    }
}
