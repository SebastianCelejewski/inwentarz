package sebcel.inwentarz.gui.authors;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import sebcel.inwentarz.dao.definition.IAuthorDao;
import sebcel.inwentarz.gui.authorlist.IAuthorDeletor;

public class AuthorDeleteFrame extends JDialog implements IAuthorDeletor {

    private static final long serialVersionUID = 1L;

    private IAuthorDao authorDao;

    public AuthorDeleteFrame(IAuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public void deleteAuthor(int id) {
        int result = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usunąć tego autora?", "Usuwanie autora", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            authorDao.deleteAuthor(id);
        }
    }
}