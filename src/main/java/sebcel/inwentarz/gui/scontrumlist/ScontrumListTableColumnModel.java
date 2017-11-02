package sebcel.inwentarz.gui.scontrumlist;

import sebcel.inwentarz.gui.list.ListTableColumnModel;

public class ScontrumListTableColumnModel extends ListTableColumnModel {

    private static final long serialVersionUID = 1L;

    public ScontrumListTableColumnModel() {
        addColumn("Nr", 50);
        addColumn("Data rozpoczęcia", 50);
        addColumn("Data zakończenia", 50);
        addColumn("Status", 50);
        addColumn("Liczba posiadanych książek", 50);
        addColumn("Liczba zweryfikowanych książek", 50);
        addColumn("Liczba niezweryfikowanych książek", 50);
    }
}