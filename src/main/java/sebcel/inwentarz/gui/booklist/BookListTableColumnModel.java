package sebcel.inwentarz.gui.booklist;

import sebcel.inwentarz.gui.list.ListTableColumnModel;

public class BookListTableColumnModel extends ListTableColumnModel {

    private static final long serialVersionUID = 1L;

    public BookListTableColumnModel() {
        addColumn("Nr inw.", 50);
        addColumn("Autorzy", 200);
        addColumn("Tytuł", 200);
        addColumn("Status", 80);
        addColumn("Data włączenia", 80);
        addColumn("Źródło", 80);
        addColumn("Cena", 50);
        addColumn("Wartość", 50);
    }
}