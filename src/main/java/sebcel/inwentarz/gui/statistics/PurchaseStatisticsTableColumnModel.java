package sebcel.inwentarz.gui.statistics;

import sebcel.inwentarz.gui.list.ListTableColumnModel;

public class PurchaseStatisticsTableColumnModel extends ListTableColumnModel {
    
    private static final long serialVersionUID = 1L;

    public PurchaseStatisticsTableColumnModel() {
	addColumn("Okres", 20);
	addColumn("Liczba dodanych ksi¹¿ek", 40);
	addColumn("Cena dodanych ksi¹¿êk", 40);
	addColumn("Wartoœæ dodanych ksi¹¿êk", 40);
	addColumn("", 260);
    }
}