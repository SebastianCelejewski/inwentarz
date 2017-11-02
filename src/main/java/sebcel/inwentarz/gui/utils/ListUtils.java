package sebcel.inwentarz.gui.utils;

import java.util.Collection;

public class ListUtils {

    public static Integer getSingleId(Collection<Integer> selectedIds) {
        if (selectedIds == null) {
            return null;
        }
        if (selectedIds.size() > 1) {
            throw new RuntimeException("Wywołano operację wymagającą dokładnie jednego zaznaczonego elementu, a zaznaczonych jest wiele elementów");
        }
        return selectedIds.iterator().next();
    }
}