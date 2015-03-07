package sebcel.inwentarz.gui.scontrumlist;

import sebcel.inwentarz.gui.list.ListTableColumnModel;

public class ScontrumListTableColumnModel extends ListTableColumnModel {
    
    private static final long serialVersionUID = 1L;

    public ScontrumListTableColumnModel() {
	addColumn("Nr", 50);
	addColumn("Data rozpocz�cia", 50);
	addColumn("Data zako�czenia", 50);
	addColumn("Status", 50);
	addColumn("Liczba posiadanych ksi��ek", 50);
	addColumn("Liczba zweryfikowanych ksi��ek", 50);
	addColumn("Liczba niezweryfikowanych ksi��ek", 50);
    }
}