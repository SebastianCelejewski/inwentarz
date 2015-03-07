package sebcel.inwentarz.gui.booklist;

import sebcel.inwentarz.gui.list.ListTableColumnModel;

public class BookListTableColumnModel extends ListTableColumnModel {
    
    private static final long serialVersionUID = 1L;

    public BookListTableColumnModel() {
	addColumn("Nr inw.", 50);
	addColumn("Autorzy", 200);
	addColumn("Tytu≥", 200);
	addColumn("Status", 80);
	addColumn("Data w≥πczenia", 80);
	addColumn("èrÛd≥o", 80);
	addColumn("Cena", 50);
	addColumn("WartoúÊ", 50);
    }
}