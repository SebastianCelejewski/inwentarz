package sebcel.inwentarz.gui.bookregister;

import sebcel.inwentarz.gui.list.ListTableColumnModel;

public class RegisterTableColumnModel extends ListTableColumnModel {

    private static final long serialVersionUID = 1L;

    public RegisterTableColumnModel() {
        addColumn("Data operacji", 250);
        addColumn("Rodzaj operacji", 350);
    }
}