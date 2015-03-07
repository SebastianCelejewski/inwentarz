package sebcel.inwentarz.gui.scontrumlist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import sebcel.gui.list.ListTableModel;
import sebcel.inwentarz.dao.definition.IScontrumDao;
import sebcel.inwentarz.dao.dto.ScontrumListElement;

public class ScontrumListTableModel extends AbstractTableModel implements ListTableModel<ScontrumListElement> {

    private static final long serialVersionUID = 1L;

    private IScontrumDao scontrumDao;

    private List<ScontrumListElement> data = new ArrayList<ScontrumListElement>();

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public ScontrumListTableModel(IScontrumDao scontrumDao) {
	this.scontrumDao = scontrumDao;
    }

    public int getColumnCount() {
	return 7;
    }

    public int getRowCount() {
	return data.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
	ScontrumListElement row = data.get(rowIndex);
	switch (columnIndex) {
	case 0:
	    return row.getId();
	case 1:
	    return df.format(row.getDataRozpoczecia());
	case 2:
	    if (row.getDataZakonczenia() != null) {
		return df.format(row.getDataZakonczenia());
	    } else {
		return "";
	    }

	case 3:
	    return row.getStatus().getName();
	case 4:
	    return row.getLiczbaPosiadanychKsiazek();
	case 5:
	    return row.getLiczbaZweryfikowanychKsiazek();
	case 6:
	    return row.getLiczbaNiezweryfikowanychKsiazek();
	default:
	    return "";
	}
    }

    public void reload() {
	this.data = scontrumDao.getScontrumList();
	fireTableChanged(new TableModelEvent(this));
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
	    return String.class;
	case 2:
	    return String.class;
	case 3:
	    return String.class;
	case 4:
	    return Integer.class;
	case 5:
	    return Integer.class;
	case 6:
	    return Integer.class;
	default:
	    return Object.class;
	}
    }

    @Override
    public ScontrumListElement getElementAt(int idx) {
	return data.get(idx);
    }
}