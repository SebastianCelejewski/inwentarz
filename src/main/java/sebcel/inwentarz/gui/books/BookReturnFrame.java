package sebcel.inwentarz.gui.books;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import sebcel.inwentarz.dao.definition.IBookDao;
import sebcel.inwentarz.gui.booklist.IBookReturner;

public class BookReturnFrame extends JDialog implements IBookReturner {

    private static final long serialVersionUID = 1L;

    private IBookDao bookDao;

    public BookReturnFrame(IBookDao bookDao) {
	this.bookDao = bookDao;
    }

    @Override
    public void returnBook(int id) {
	int result = JOptionPane.showConfirmDialog(null, "PotwierdŸ operacjê zwrotu ksi¹¿ki", "Zwrot ksi¹¿ki", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	if (result == JOptionPane.OK_OPTION) {
	    bookDao.returnBook(id);
	}
    }
}