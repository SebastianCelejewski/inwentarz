package sebcel.inwentarz.gui.scontrumlist;

import sebcel.inwentarz.gui.list.ListTableColumnModel;

public class ScontrumListTableColumnModel extends ListTableColumnModel {
    
    private static final long serialVersionUID = 1L;

    public ScontrumListTableColumnModel() {
	addColumn("Nr", 50);
	addColumn("Data rozpoczêcia", 50);
	addColumn("Data zakoñczenia", 50);
	addColumn("Status", 50);
	addColumn("Liczba posiadanych ksi¹¿ek", 50);
	addColumn("Liczba zweryfikowanych ksi¹¿ek", 50);
	addColumn("Liczba niezweryfikowanych ksi¹¿ek", 50);
    }
}