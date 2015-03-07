package sebcel.inwentarz.gui.utils;

import java.util.Collection;

public class ListUtils {

    public static Integer getSingleId(Collection<Integer> selectedIds) {
	if (selectedIds == null) {
	    return null;
	}
	if (selectedIds.size() > 1) {
	    throw new RuntimeException("Wywo³ano operacjê wymagaj¹c¹ dok³adnie jednego zaznaczonego elementu, a zaznaczonych jest wiele elementów");
	}
	return selectedIds.iterator().next();
    }

}
