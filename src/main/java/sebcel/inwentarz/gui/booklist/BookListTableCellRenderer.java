package sebcel.inwentarz.gui.booklist;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import sebcel.inwentarz.dao.dto.BookStatus;

public class BookListTableCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    private BookListTableModel tableModel;
    private Map<BookStatus, Color> colors = new HashMap<BookStatus, Color>();

    public BookListTableCellRenderer(BookListTableModel tableModel) {
        this.tableModel = tableModel;
        colors.put(BookStatus.DOSTEPNA, Color.BLACK);
        colors.put(BookStatus.WYPOZYCZONA, Color.BLUE);
        colors.put(BookStatus.WYCOFANA, Color.LIGHT_GRAY);
        colors.put(BookStatus.USUNIETA, Color.LIGHT_GRAY);
    }

    public Component getTableCellRendererComponent(JTable aTable, Object aNumberValue, boolean aIsSelected, boolean aHasFocus, int aRow, int aColumn) {
        if (aNumberValue == null) {
            this.setForeground(Color.BLACK);
            this.setValue(null);
        }
        Component renderer = super.getTableCellRendererComponent(aTable, aNumberValue, aIsSelected, aHasFocus, aRow, aColumn);

        BookStatus bookStatus = tableModel.getElementAt(aTable.convertRowIndexToModel(aRow)).getStatus();
        Color color = colors.get(bookStatus);
        renderer.setForeground(color);
        return this;
    }
}