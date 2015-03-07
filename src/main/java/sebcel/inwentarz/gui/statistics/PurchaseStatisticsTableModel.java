package sebcel.inwentarz.gui.statistics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import sebcel.inwentarz.dao.dto.PurchaseStatisticsEntry;

public class PurchaseStatisticsTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private DecimalFormat moneyFormat = new DecimalFormat("0.00");

    private List<PurchaseStatisticsEntry> data = new ArrayList<PurchaseStatisticsEntry>();

    @Override
    public int getColumnCount() {
	return 5;
    }

    @Override
    public int getRowCount() {
	return data.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	PurchaseStatisticsEntry entry = data.get(rowIndex);
	switch (columnIndex) {
	case 0:
	    return entry.getPeriod() != null ? entry.getPeriod() : "nieznany";
	case 1:
	    return entry.getNumber();
	case 2:
	    return moneyFormat.format(entry.getExpenses()) + " z³";
	case 3:
	    return moneyFormat.format(entry.getValue()) + " z³";
	case 4:
	    return "";
	}
	throw new RuntimeException("Invalid column index: " + columnIndex);
    }

    public void setData(List<PurchaseStatisticsEntry> data) {
	this.data = data;
	fireTableChanged(new TableModelEvent(this));
    }
}