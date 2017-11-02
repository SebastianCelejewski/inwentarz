package sebcel.inwentarz.gui.authors;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sebcel.inwentarz.dao.definition.IAuthorDao;
import sebcel.inwentarz.dao.dto.AuthorCreationData;
import sebcel.inwentarz.dao.dto.AuthorEditData;
import sebcel.inwentarz.gui.authorlist.IAuthorCreator;
import sebcel.inwentarz.gui.authorlist.IAuthorEditor;
import sebcel.inwentarz.gui.utils.AcceptCancelResult;
import sebcel.inwentarz.gui.utils.GuiTools;

public class AuthorEditFrame extends JDialog implements IAuthorEditor, IAuthorCreator {

    private static final long serialVersionUID = 1L;

    private IAuthorDao authorDao;

    private int y = 0;

    private JTextField idBox = new JTextField();
    private JTextField imionaBox = new JTextField();
    private JTextField nazwiskoBox = new JTextField();

    private JPanel buttonPanel = new JPanel();
    private JButton acceptButton = new JButton("Zatwierdź");
    private JButton cancelButton = new JButton("Anuluj");

    private AcceptCancelResult result = AcceptCancelResult.CANCEL;

    public AuthorEditFrame(IAuthorDao authorDao) {
        this.authorDao = authorDao;

        this.setLayout(new GridBagLayout());
        this.setSize(640, 480);
        GuiTools.centerWindow(this);

        addElement("Identyfikator", idBox);
        addElement("Imiona", imionaBox);
        addElement("Nazwisko", nazwiskoBox);

        this.add(buttonPanel, new GridBagConstraints(0, y++, 2, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 1, 1));
        buttonPanel.setLayout(new GridLayout());
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result = AcceptCancelResult.ACCEPT;
                setVisible(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result = AcceptCancelResult.CANCEL;
                setVisible(false);
            }
        });

        idBox.setEditable(false);
    }

    private void addElement(String label, JComponent component) {
        this.add(new JLabel(label), createLabelConstraints(y));
        this.add(component, createBoxConstraints(y));
        y++;
    }

    private GridBagConstraints createLabelConstraints(int y) {
        return new GridBagConstraints(0, y, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, 1);
    }

    private GridBagConstraints createBoxConstraints(int y) {
        return new GridBagConstraints(1, y, 1, 1, 2.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, 1);
    }

    @Override
    public void editAuthor(int id) {
        AuthorEditData authorEditData = authorDao.getAuthorEditData(id);

        idBox.setText(Integer.toString(authorEditData.getId()));
        imionaBox.setText(authorEditData.getImiona());
        nazwiskoBox.setText(authorEditData.getNazwisko());

        this.setTitle("Edycja danych autora");
        this.setModal(true);
        this.setVisible(true);

        if (result == AcceptCancelResult.ACCEPT) {
            AuthorEditData modifiedEditData = new AuthorEditData();

            modifiedEditData.setId(authorEditData.getId());
            modifiedEditData.setImiona(imionaBox.getText());
            modifiedEditData.setNazwisko(nazwiskoBox.getText());

            authorDao.updateAuthor(modifiedEditData);
        }
    }

    @Override
    public Integer createAuthor() {
        idBox.setText("");
        imionaBox.setText("");
        nazwiskoBox.setText("");

        this.setTitle("Dodawanie nowego autora");
        this.setModal(true);
        this.setVisible(true);

        if (result == AcceptCancelResult.ACCEPT) {
            AuthorCreationData authorCreationData = new AuthorCreationData();

            authorCreationData.setImiona(imionaBox.getText());
            authorCreationData.setNazwisko(nazwiskoBox.getText());

            int id = authorDao.createAuthor(authorCreationData);
            return id;
        } else {
            return null;
        }
    }
}