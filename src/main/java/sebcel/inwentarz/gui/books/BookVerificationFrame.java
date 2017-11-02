package sebcel.inwentarz.gui.books;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import sebcel.inwentarz.dao.definition.IBookDao;
import sebcel.inwentarz.gui.booklist.IBookVerifier;

public class BookVerificationFrame extends JDialog implements IBookVerifier {

    private static final long serialVersionUID = 1L;

    private IBookDao bookDao;

    public BookVerificationFrame(IBookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void verifyBookExistence(int id) {
        int result = JOptionPane.showConfirmDialog(null, "Czy potwierdzasz, że książka fizycznie istnieje w zbiorach?", "Weryfikacja", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            bookDao.verifyBookExistence(id);
        }
    }
}