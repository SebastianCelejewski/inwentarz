package sebcel.inwentarz.gui.statistics;

import sebcel.inwentarz.gui.list.ListTableColumnModel;

public class PurchaseStatisticsTableColumnModel extends ListTableColumnModel {
    
    private static final long serialVersionUID = 1L;

    public PurchaseStatisticsTableColumnModel() {
	addColumn("Okres", 20);
	addColumn("Liczba dodanych ksi��ek", 40);
	addColumn("Cena dodanych ksi���k", 40);
	addColumn("Warto�� dodanych ksi���k", 40);
	addColumn("", 260);
    }
}