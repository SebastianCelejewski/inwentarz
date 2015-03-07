package sebcel.inwentarz.gui.bookregister;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import sebcel.inwentarz.dao.definition.IBookDao;
import sebcel.inwentarz.dao.dto.BookRegisterListElement;

public class BookRegisterTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private IBookDao bookDao;

    private List<BookRegisterListElement> data = new ArrayList<BookRegisterListElement>();

    private DateFormat df = SimpleDateFormat.getDateTimeInstance();

    public BookRegisterTableModel(IBookDao bookDao) {
	this.bookDao = bookDao;
    }

    public int getColumnCount() {
	return 2;
    }

    public int getRowCount() {
	return data.size();
    }

    public String getDescription(int rowIndex) {
	return data.get(rowIndex).getSzczegoly();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
	BookRegisterListElement row = data.get(rowIndex);
	switch (columnIndex) {
	case 0:
	    return df.format(row.getDataOperacji());
	case 1:
	    return row.getRodzajOperacji();
	}
	return "<b³¹d>";
    }

    public void reload(int bookId) {
	this.data = bookDao.getBookRegisterList(bookId);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
	switch (columnIndex) {
	case 1:
	    return String.class;
	case 2:
	    return String.class;
	default:
	    return Object.class;
	}
    }
}