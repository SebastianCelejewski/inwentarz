package sebcel.inwentarz.gui.authorlist;

import sebcel.inwentarz.gui.list.ListTableColumnModel;

public class AuthorListTableColumnModel extends ListTableColumnModel {
    
    private static final long serialVersionUID = 1L;

    public AuthorListTableColumnModel() {
	addColumn("Id", 50);
	addColumn("Imiona", 200);
	addColumn("Nazwisko", 400);
    }
}