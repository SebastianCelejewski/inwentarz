package sebcel.inwentarz.gui.utils;

import java.util.Collection;

public class ListUtils {

    public static Integer getSingleId(Collection<Integer> selectedIds) {
	if (selectedIds == null) {
	    return null;
	}
	if (selectedIds.size() > 1) {
	    throw new RuntimeException("Wywo�ano operacj� wymagaj�c� dok�adnie jednego zaznaczonego elementu, a zaznaczonych jest wiele element�w");
	}
	return selectedIds.iterator().next();
    }

}
