package sebcel.inwentarz.gui.books;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import sebcel.inwentarz.dao.definition.IBookDao;
import sebcel.inwentarz.gui.booklist.IBookLender;

public class BookLenderFrame extends JDialog implements IBookLender {

    private static final long serialVersionUID = 1L;

    private IBookDao bookDao;

    public BookLenderFrame(IBookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void lendBook(int id) {
        int result = JOptionPane.showConfirmDialog(null, "Potwierdź operację wypożyczenia książki", "Wypożyczenie książki", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            bookDao.lendBook(id);
        }
    }
}