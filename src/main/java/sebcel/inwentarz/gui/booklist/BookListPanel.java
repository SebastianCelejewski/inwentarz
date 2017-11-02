package sebcel.inwentarz.gui.booklist;

import java.awt.BorderLayout;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sebcel.inwentarz.dao.utils.InwentarzException;
import sebcel.inwentarz.event.IDataChangeListener;
import sebcel.inwentarz.gui.list.IFilterListener;
import sebcel.inwentarz.gui.utils.ListUtils;

public class BookListPanel extends JPanel implements IFilterListener, IBookListEventListener, IDataChangeListener {

    private static final long serialVersionUID = 8780701064368207552L;

    private final BookListFilterPanel filterPanel;
    private final BookListButtonPanel buttonPanel;
    private final BookListTablePanel tablePanel;

    private IBookCreator bookCreator;
    private IBookEditor bookEditor;
    private IBookDeletor bookDeletor;
    private IBookRegisterViewer bookRegisterViewer;
    private IBookDetailsViewer bookDetailsViewer;
    private IBookPrinterFactory bookPrinterFactory;
    private IBookLender bookLender;
    private IBookReturner bookReturner;
    private IBookVerifier bookVerifier;

    public BookListPanel(BookListFilterPanel filterPanel, BookListButtonPanel buttonPanel, BookListTablePanel listTablePanel, IBookCreator elementCreator, IBookEditor elementEditor, IBookDeletor elementDeletor,
            IBookDetailsViewer elementDetailsViewer, IBookRegisterViewer elementRegisterViewer, IBookPrinterFactory elementPrinterFactory, IBookLender bookLender, IBookReturner bookReturner, IBookVerifier bookVerifier) {
        this.filterPanel = filterPanel;
        this.buttonPanel = buttonPanel;
        this.tablePanel = listTablePanel;

        this.bookCreator = elementCreator;
        this.bookEditor = elementEditor;
        this.bookDeletor = elementDeletor;
        this.bookDetailsViewer = elementDetailsViewer;
        this.bookRegisterViewer = elementRegisterViewer;
        this.bookPrinterFactory = elementPrinterFactory;
        this.bookLender = bookLender;
        this.bookReturner = bookReturner;
        this.bookVerifier = bookVerifier;

        initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        this.add(filterPanel, BorderLayout.NORTH);
        this.add(tablePanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        tablePanel.addBookStatusListener(buttonPanel);
        filterPanel.addFilterListener(this);
        buttonPanel.setBookListEventListener(this);
    }

    @Override
    public void filterChanged() {
        tablePanel.setFilter(filterPanel.getFilter());
    }

    @Override
    public void addNewElement() {
        Integer id = bookCreator.createBook();
        if (id != null) {
            tablePanel.reload();
            tablePanel.focus(id);
        }
    }

    @Override
    public void deleteSelectedElement() {
        Collection<Integer> selectedBookIds = tablePanel.getSelectedBooks();
        Integer selectedBookId = ListUtils.getSingleId(selectedBookIds);
        if (selectedBookId != null) {
            bookDeletor.deleteBook(selectedBookId.intValue());
            tablePanel.reload();
            tablePanel.focus(selectedBookId);
        }
    }

    @Override
    public void viewSelectedElementDetails() {
        Collection<Integer> selectedBookIds = tablePanel.getSelectedBooks();
        Integer selectedBookId = ListUtils.getSingleId(selectedBookIds);
        if (selectedBookId != null) {
            bookDetailsViewer.viewBookDetails(selectedBookId.intValue());
        }
    }

    @Override
    public void editSelectedElement() {
        Collection<Integer> selectedBookIds = tablePanel.getSelectedBooks();
        Integer selectedBookId = ListUtils.getSingleId(selectedBookIds);
        if (selectedBookId != null) {
            bookEditor.editBook(selectedBookId.intValue());
            tablePanel.reload();
            tablePanel.focus(selectedBookId);
        }
    }

    @Override
    public void printSelectedElement() {
        Collection<Integer> selectedBookIds = tablePanel.getSelectedBooks();
        if (selectedBookIds != null && selectedBookIds.size() > 0) {
            bookPrinterFactory.newSet();
            for (Integer selectedBookId : selectedBookIds) {
                if (selectedBookId != null) {
                    bookPrinterFactory.newBookPrinter().printBook(selectedBookId.intValue());
                }
            }
        }
    }

    @Override
    public void viewSelectedElementRegister() {
        Collection<Integer> selectedBookIds = tablePanel.getSelectedBooks();
        Integer selectedBookId = ListUtils.getSingleId(selectedBookIds);
        if (selectedBookId != null) {
            bookRegisterViewer.viewBookRegister(selectedBookId.intValue());
        }
    }

    @Override
    public void hardDeleteBook() {
        Collection<Integer> selectedBookIds = tablePanel.getSelectedBooks();
        Integer selectedBookId = ListUtils.getSingleId(selectedBookIds);
        if (selectedBookId != null) {
            bookDeletor.hardDeleteBook(selectedBookId.intValue());
            tablePanel.reload();
        }
    }

    @Override
    public void lendBook() {
        Collection<Integer> selectedBookIds = tablePanel.getSelectedBooks();
        Integer selectedBookId = ListUtils.getSingleId(selectedBookIds);
        if (selectedBookId != null) {
            bookLender.lendBook(selectedBookId.intValue());
            tablePanel.reload();
            tablePanel.focus(selectedBookId);
        }
    }

    @Override
    public void returnBook() {
        Collection<Integer> selectedBookIds = tablePanel.getSelectedBooks();
        Integer selectedBookId = ListUtils.getSingleId(selectedBookIds);
        if (selectedBookId != null) {
            bookReturner.returnBook(selectedBookId.intValue());
            tablePanel.reload();
            tablePanel.focus(selectedBookId);
        }
    }

    @Override
    public void verifyBook() {
        Collection<Integer> selectedBookIds = tablePanel.getSelectedBooks();
        Integer selectedBookId = ListUtils.getSingleId(selectedBookIds);
        if (selectedBookId != null) {
            try {
                bookVerifier.verifyBookExistence(selectedBookId.intValue());
                tablePanel.reload();
                tablePanel.focus(selectedBookId);
            } catch (InwentarzException ex) {
                JOptionPane.showMessageDialog(BookListPanel.this, ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(BookListPanel.this, "Wyst�pi� krytyczny b��d programu:\n" + ex.getMessage());
            }
        }
    }

    @Override
    public void dataChanged() {
        tablePanel.reload();
    }
}