package sebcel.inwentarz.gui.booklist;

import java.awt.BorderLayout;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import sebcel.gui.list.IListSelectionListener;
import sebcel.gui.list.ListTable;
import sebcel.gui.list.SelectionStatus;
import sebcel.inwentarz.dao.definition.IBookDao;
import sebcel.inwentarz.dao.dto.BookListElement;
import sebcel.inwentarz.dao.dto.BookStatus;
import sebcel.inwentarz.gui.booklist.BookListFilterModel.ScontrumFilterValue;
import sebcel.inwentarz.gui.comparators.IComparatorFactory;

public class BookListTablePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private ListTable<BookListElement> table;
    private BookListTableModel tableModel;
    private BookListTableColumnModel tableColumnModel;
    private TableRowSorter<BookListTableModel> sorter;
    private BookListTableCellRenderer cellRenderer;

    private Set<ISelectionListener<BookStatus>> bookStatusListeners = new HashSet<ISelectionListener<BookStatus>>();

    public BookListTablePanel(IBookDao bookDao, IComparatorFactory comparatorFactory) {
	this.setLayout(new BorderLayout());
	this.setBorder(BorderFactory.createTitledBorder("Lista ksi¹¿ek"));

	tableModel = new BookListTableModel(bookDao);
	tableColumnModel = new BookListTableColumnModel();
	sorter = new TableRowSorter<BookListTableModel>(tableModel);
	sorter.setComparator(1, comparatorFactory.getAuthorComparator());
	sorter.setComparator(3, comparatorFactory.getBookStatusComparator());
	sorter.setComparator(4, comparatorFactory.getDateComparator());
	sorter.setComparator(6, comparatorFactory.getPriceComparator());
	sorter.setComparator(7, comparatorFactory.getPriceComparator());

	cellRenderer = new BookListTableCellRenderer(tableModel);

	table = new ListTable<BookListElement>(tableModel, tableColumnModel, sorter, cellRenderer);
	table.addListSelectionListener(new IListSelectionListener<BookListElement>() {

	    @Override
	    public void elementsWasSelected(Collection<BookListElement> elements) {
		if (elements != null) {
		    int numberOfSelectedElements = elements.size();
		    for (BookListElement element : elements) {
			fireBookStatusChanged(new SelectionStatus<BookStatus>(numberOfSelectedElements, element.getStatus()));
		    }
		}
	    }
	});

	this.add(table, BorderLayout.CENTER);
	reload();
    }

    public void reload() {
	table.reload();
    }

    public void setFilter(BookListFilterModel filter) {
	final Integer expectedBookId = filter.getBookId();
	final String aNames = filter.getAuthorNameExpression();
	final String bTitle = filter.getBookTitleExpression();
	final ScontrumFilterValue scontrumFilterValue = filter.getScontrumFilter();
	RowFilter<BookListTableModel, Object> rf = new RowFilter<BookListTableModel, Object>() {
	    @Override
	    public boolean include(javax.swing.RowFilter.Entry<? extends BookListTableModel, ? extends Object> entry) {
		if (expectedBookId != null) {
		    int bookId = (Integer) entry.getValue(0);
		    if (bookId != expectedBookId) {
			return false;
		    }
		}
		if (aNames != null) {
		    String autorzy = (String) entry.getValue(1);
		    if (autorzy.toLowerCase().indexOf(aNames.toLowerCase()) == -1) {
			return false;
		    }
		}
		if (bTitle != null) {
		    String tytul = (String) entry.getValue(2);
		    if (tytul.toLowerCase().indexOf(bTitle.toLowerCase()) == -1) {
			return false;
		    }
		}
		if (scontrumFilterValue == ScontrumFilterValue.VERIFIED)
		{
		    Boolean verified = (Boolean) entry.getValue(8);
		    BookStatus status = (BookStatus) entry.getValue(3);
		    if (!verified)
		    {
			return false;
		    }
		    if (status == BookStatus.USUNIETA || status == BookStatus.WYCOFANA)
		    {
			return false;
		    }
		}
		if (scontrumFilterValue == ScontrumFilterValue.NOT_VERIFIED)
		{
		    Boolean verified = (Boolean) entry.getValue(8);
		    BookStatus status = (BookStatus) entry.getValue(3);
		    if (verified)
		    {
			return false;
		    }
		    if (status == BookStatus.USUNIETA || status == BookStatus.WYCOFANA)
		    {
			return false;
		    }
		}
		return true;
	    }
	};
	sorter.setRowFilter(rf);
    }

    public Collection<Integer> getSelectedBooks() {
	return table.getSelectedElementIds();
    }

    public void focus(Integer id) {
	table.setSelectedElementId(id);
    }

    private void fireBookStatusChanged(SelectionStatus<BookStatus> selectionStatus) {
	for (ISelectionListener<BookStatus> listener : bookStatusListeners) {
	    listener.statusChanged(selectionStatus);
	}
    }

    public void addBookStatusListener(ISelectionListener<BookStatus> bookStatusListener) {
	bookStatusListeners.add(bookStatusListener);
    }
}