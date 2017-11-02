package sebcel.inwentarz.gui.authorlist;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import sebcel.gui.list.ListTableModel;
import sebcel.inwentarz.dao.definition.IAuthorDao;
import sebcel.inwentarz.dao.dto.AuthorListElement;

public class AuthorListTableModel extends AbstractTableModel implements ListTableModel<AuthorListElement> {

    private static final long serialVersionUID = 1L;

    private IAuthorDao authorDao;

    private List<AuthorListElement> data = new ArrayList<AuthorListElement>();

    public AuthorListTableModel(IAuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return data.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        AuthorListElement row = data.get(rowIndex);
        switch (columnIndex) {
        case 0:
            return row.getId();
        case 1:
            return row.getImiona();
        case 2:
            return row.getNazwisko();
        }
        throw new RuntimeException("Invalid column index: " + columnIndex);
    }

    public void reload() {
        this.data = authorDao.getAuthorList();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return Integer.class;
        case 1:
        case 2:
            return String.class;
        }
        throw new RuntimeException("Invalid column index: " + columnIndex);
    }

    @Override
    public int getIndexOfId(int id) {
        return 0;
    }

    @Override
    public AuthorListElement getElementAt(int idx) {
        return data.get(idx);
    }
}