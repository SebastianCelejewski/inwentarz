package sebcel.inwentarz.gui.list;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ListTableColumnModel extends DefaultTableColumnModel {
    
    private static final long serialVersionUID = 1L;

    private int modelIndex = 0;
    
    protected void addColumn(String header, int width) {
	TableColumn column = new TableColumn();
	column.setModelIndex(modelIndex++);
	column.setHeaderValue(header);
	column.setMinWidth(10);
	column.setPreferredWidth(width);
	addColumn(column);
    }
}