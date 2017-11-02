package sebcel.inwentarz.gui.scontrumlist;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import sebcel.inwentarz.dao.dto.ScontrumStatus;

public class ScontrumListTableCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    private ScontrumListTableModel tableModel;
    private Map<ScontrumStatus, Color> colors = new HashMap<ScontrumStatus, Color>();

    public ScontrumListTableCellRenderer(ScontrumListTableModel tableModel) {
        this.tableModel = tableModel;
        colors.put(ScontrumStatus.ROZPOCZETE, Color.RED);
        colors.put(ScontrumStatus.ZAKONCZONE, Color.BLACK);
    }

    public Component getTableCellRendererComponent(JTable aTable, Object aNumberValue, boolean aIsSelected, boolean aHasFocus, int aRow, int aColumn) {
        if (aNumberValue == null) {
            this.setForeground(Color.BLACK);
            this.setValue(null);
        }
        Component renderer = super.getTableCellRendererComponent(aTable, aNumberValue, aIsSelected, aHasFocus, aRow, aColumn);

        ScontrumStatus bookStatus = tableModel.getElementAt(aTable.convertRowIndexToModel(aRow)).getStatus();
        Color color = colors.get(bookStatus);
        renderer.setForeground(color);
        return this;
    }
}