package sebcel.inwentarz.gui.authorlist;

import java.awt.BorderLayout;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import sebcel.gui.list.ListTable;
import sebcel.inwentarz.dao.definition.IAuthorDao;
import sebcel.inwentarz.dao.dto.AuthorListElement;

public class AuthorListTablePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private ListTable<AuthorListElement> table;
    private AuthorListTableModel tableModel;
    private AuthorListTableColumnModel tableColumnModel;
    private TableRowSorter<AuthorListTableModel> sorter;
    private TableCellRenderer cellRenderer = new DefaultTableCellRenderer();
    
    public AuthorListTablePanel(IAuthorDao authorDao) {
	this.setLayout(new BorderLayout());
	this.setBorder(BorderFactory.createTitledBorder("Lista ksi¹¿ek"));

	tableModel = new AuthorListTableModel(authorDao);
	tableColumnModel = new AuthorListTableColumnModel();
	sorter = new TableRowSorter<AuthorListTableModel>(tableModel);
	table = new ListTable<AuthorListElement>(tableModel, tableColumnModel, sorter, cellRenderer);
	
	this.add(table, BorderLayout.CENTER);
	reload();
    }

    public void reload() {
	table.reload();
    }
    
    public void setFilter(AuthorListFilterModel filter) {
	final String aNames = filter.getAuthorNameExpression();
	RowFilter<AuthorListTableModel, Object> rf = new RowFilter<AuthorListTableModel, Object>() {
	    @Override
	    public boolean include(javax.swing.RowFilter.Entry<? extends AuthorListTableModel, ? extends Object> entry) {
		if (aNames != null) {
		    String autorzy = (String) entry.getValue(2);
		    if (autorzy.toLowerCase().indexOf(aNames.toLowerCase())==-1) {
			return false;
		    }
		}
		return true;
	    }
	};
	sorter.setRowFilter(rf);
    }
    
    public Collection<Integer> getSelectedAuthors() {
	return table.getSelectedElementIds();
    }

}
