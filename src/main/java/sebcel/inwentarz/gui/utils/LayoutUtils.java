package sebcel.inwentarz.gui.utils;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class LayoutUtils {
    public static GridBagConstraints labelConstraints(int x, int y) {
	return new GridBagConstraints(x, y, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, 1);
    }

    public static GridBagConstraints textConstraints(int x, int y) {
	return new GridBagConstraints(x, y, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, 1);
    }
}
