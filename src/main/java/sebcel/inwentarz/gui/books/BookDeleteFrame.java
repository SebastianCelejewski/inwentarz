package sebcel.inwentarz.gui.books;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import sebcel.inwentarz.dao.definition.IBookDao;
import sebcel.inwentarz.gui.booklist.IBookDeletor;

public class BookDeleteFrame extends JDialog implements IBookDeletor {

    private static final long serialVersionUID = 1L;

    private IBookDao bookDao;

    public BookDeleteFrame(IBookDao bookDao) {
	this.bookDao = bookDao;
    }

    @Override
    public void deleteBook(int id) {
	int result = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz zubytkowa� t� ksi��k�?", "Ubytkowanie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	if (result == JOptionPane.OK_OPTION) {
	    bookDao.deleteBook(id);
	}
    }

    @Override
    public void hardDeleteBook(int id) {
	int result = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz skasowa� t� ksi��k�?", "Kasowanie ksi��ki", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	if (result == JOptionPane.OK_OPTION) {
	    result = JOptionPane.showConfirmDialog(null, "Ponownie potwierd� kasowanie ksi��ki", "Kasowanie ksi��ki", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	    if (result == JOptionPane.OK_OPTION) {
		    bookDao.hardDeleteBook(id);
	    }
	}
    }
}