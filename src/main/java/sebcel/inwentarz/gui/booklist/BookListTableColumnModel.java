package sebcel.inwentarz.gui.booklist;

import sebcel.inwentarz.gui.list.ListTableColumnModel;

public class BookListTableColumnModel extends ListTableColumnModel {
    
    private static final long serialVersionUID = 1L;

    public BookListTableColumnModel() {
	addColumn("Nr inw.", 50);
	addColumn("Autorzy", 200);
	addColumn("Tytu�", 200);
	addColumn("Status", 80);
	addColumn("Data w��czenia", 80);
	addColumn("�r�d�o", 80);
	addColumn("Cena", 50);
	addColumn("Warto��", 50);
    }
}