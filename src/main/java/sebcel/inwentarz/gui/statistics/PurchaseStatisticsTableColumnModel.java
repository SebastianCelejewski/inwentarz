package sebcel.inwentarz.gui.statistics;

import sebcel.inwentarz.gui.list.ListTableColumnModel;

public class PurchaseStatisticsTableColumnModel extends ListTableColumnModel {

    private static final long serialVersionUID = 1L;

    public PurchaseStatisticsTableColumnModel() {
        addColumn("Okres", 20);
        addColumn("Liczba dodanych książek", 40);
        addColumn("Cena dodanych książek", 40);
        addColumn("Wartość dodanych książek", 40);
        addColumn("", 260);
    }
}