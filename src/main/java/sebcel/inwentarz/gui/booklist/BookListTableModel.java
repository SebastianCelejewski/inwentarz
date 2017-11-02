package sebcel.inwentarz.gui.booklist;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import sebcel.gui.list.ListTableModel;
import sebcel.inwentarz.dao.definition.IBookDao;
import sebcel.inwentarz.dao.dto.BookListElement;

public class BookListTableModel extends AbstractTableModel implements ListTableModel<BookListElement> {

    private static final long serialVersionUID = 1L;

    private IBookDao bookDao;

    private List<BookListElement> data = new ArrayList<BookListElement>();

    private DecimalFormat df = new DecimalFormat("0.00 zł");

    public BookListTableModel(IBookDao bookDao) {
        this.bookDao = bookDao;
    }

    public int getColumnCount() {
        return 9;
    }

    public int getRowCount() {
        return data.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        BookListElement row = data.get(rowIndex);
        switch (columnIndex) {
        case 0:
            return row.getId();
        case 1:
            if (row.getAutorzy() == null) {
                row.setAutorzy(bookDao.getAuthorNamesForBook(row.getId()));
            }
            return row.getAutorzy();
        case 2:
            return row.getTytul();
        case 3:
            return row.getStatus();
        case 4:
            return row.getDataWlaczenia();
        case 5:
            return row.getZrodlo();
        case 6:
            return getCurrency(row.getCena());
        case 7:
            return getCurrency(row.getWartosc());
        case 8:
            if (row.isVerified() == null) {
                row.setVerified(bookDao.getBookVerification(row.getId()));
            }
            return row.isVerified();
        }
        return rowIndex * columnIndex + rowIndex + columnIndex;
    }

    public void reload() {
        this.data = bookDao.getBookList();
        fireTableChanged(new TableModelEvent(this));
    }

    private String getCurrency(Double value) {
        if (value == null || value == 0) {
            return "-";
        } else {
            return df.format(value);
        }

    }

    public int getIndexOfId(int id) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == id) {
                return i;
            }
        }
        throw new RuntimeException("Element with id=" + id + " not found.");
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return Integer.class;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
            return String.class;
        case 7:
        case 8:
            return Object.class;
        default:
            return Object.class;
        }
    }

    @Override
    public BookListElement getElementAt(int idx) {
        return data.get(idx);
    }
}