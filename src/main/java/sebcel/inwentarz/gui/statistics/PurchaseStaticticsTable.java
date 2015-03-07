package sebcel.inwentarz.gui.statistics;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import sebcel.inwentarz.dao.dto.PurchaseStatistics;

public class PurchaseStaticticsTable extends JPanel {

    private static final long serialVersionUID = 1L;

    private JScrollPane scroll = new JScrollPane();
    private JTable table = new JTable();
    private PurchaseStatisticsTableModel tableModel = new PurchaseStatisticsTableModel();
    private PurchaseStatisticsTableColumnModel columnModel = new PurchaseStatisticsTableColumnModel();

    public PurchaseStaticticsTable() {
	this.setLayout(new BorderLayout());
	this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Statystyka zakupów"));
	this.add(scroll, BorderLayout.CENTER);
	this.scroll.setViewportView(table);
	this.table.setModel(tableModel);
	this.table.setColumnModel(columnModel);
    }

    public void setData(PurchaseStatistics purchaseStatistics) {
	tableModel.setData(purchaseStatistics.getEntries());
    }
}
