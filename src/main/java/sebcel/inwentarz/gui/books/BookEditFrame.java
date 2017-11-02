package sebcel.inwentarz.gui.books;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sebcel.gui.CurrencyEditField;
import sebcel.gui.DateEditField;
import sebcel.inwentarz.dao.definition.IBookDao;
import sebcel.inwentarz.dao.definition.IDictionaryDao;
import sebcel.inwentarz.dao.dto.BookCreationData;
import sebcel.inwentarz.dao.dto.BookEditData;
import sebcel.inwentarz.dao.dto.BookStatus;
import sebcel.inwentarz.dao.utils.ListElement;
import sebcel.inwentarz.gui.booklist.IBookCreator;
import sebcel.inwentarz.gui.booklist.IBookEditor;
import sebcel.inwentarz.gui.components.AuthorsBox;
import sebcel.inwentarz.gui.utils.AcceptCancelResult;
import sebcel.inwentarz.gui.utils.GuiTools;
import sebcel.inwentarz.logic.definition.ILifecycleManager;

public class BookEditFrame extends JDialog implements IBookEditor, IBookCreator {

    private static final long serialVersionUID = 1L;

    private IBookDao bookDao;

    private int y = 0;

    private JTextField idBox = new JTextField();
    private AuthorsBox authorsBox;
    private JTextField titleBox = new JTextField();
    private JComboBox statusBox = new JComboBox();
    private DateEditField dataWlaczeniaBox = new DateEditField();
    private JComboBox zrodloBox = new JComboBox();
    private CurrencyEditField cenaBox = new CurrencyEditField();
    private CurrencyEditField wartoscBox = new CurrencyEditField();
    private JScrollPane uwagiScroll = new JScrollPane();
    private JTextArea uwagiBox = new JTextArea();

    private JPanel buttonPanel = new JPanel();
    private JButton acceptButton = new JButton("Zatwierdź");
    private JButton cancelButton = new JButton("Anuluj");

    private AcceptCancelResult result = AcceptCancelResult.CANCEL;

    public BookEditFrame(IBookDao bookDao, IDictionaryDao dictionaryDao, ILifecycleManager<BookStatus> bookLifecycleManager, AuthorsBox authorsBox) {
        this.authorsBox = authorsBox;
        this.bookDao = bookDao;

        uwagiBox.setLineWrap(true);
        uwagiBox.setBorder(BorderFactory.createEtchedBorder());

        uwagiScroll.setViewportView(uwagiBox);

        this.setLayout(new GridBagLayout());
        this.setSize(640, 480);
        GuiTools.centerWindow(this);

        addElement("Nr inw.", idBox);
        addElement("Tytuł", titleBox);
        addElement("Autorzy", authorsBox);
        addElement("Status", statusBox);
        addElement("Data włączenia", dataWlaczeniaBox);
        addElement("Źródło", zrodloBox);
        addElement("Cena", cenaBox);
        addElement("Wartość", wartoscBox);
        addFillElement("Uwagi", uwagiScroll);

        this.add(buttonPanel, new GridBagConstraints(0, y++, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 1, 1));
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

        for (BookStatus bookStatus : bookLifecycleManager.getAllStatuses()) {
            statusBox.addItem(bookStatus);
        }

        for (String element : dictionaryDao.getBookSourceList()) {
            zrodloBox.addItem(element);
        }
    }

    private void addElement(String label, JComponent component) {
        this.add(new JLabel(label), createLabelConstraints(y));
        this.add(component, createBoxConstraints(y, GridBagConstraints.HORIZONTAL, 0.0));
        y++;
    }

    private void addFillElement(String label, JComponent component) {
        this.add(new JLabel(label), createLabelConstraints(y));
        this.add(component, createBoxConstraints(y, GridBagConstraints.BOTH, 1.0));
        y++;
    }

    private GridBagConstraints createLabelConstraints(int y) {
        return new GridBagConstraints(0, y, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 2, 2);
    }

    private GridBagConstraints createBoxConstraints(int y, int fill, double weightY) {
        return new GridBagConstraints(1, y, 1, 1, 1.0, weightY, GridBagConstraints.CENTER, fill, new Insets(2, 2, 2, 2), 2, 2);
    }

    @Override
    public void editBook(int bookId) {
        BookEditData bookEditData = bookDao.getBookEditData(bookId);

        idBox.setText(Integer.toString(bookEditData.getId()));
        authorsBox.setAuthors(bookEditData.getAutorzy());
        titleBox.setText(bookEditData.getTytul());
        cenaBox.setValue(bookEditData.getCena());
        wartoscBox.setValue(bookEditData.getWartosc());
        dataWlaczeniaBox.setValue(bookEditData.getDataWlaczenia());
        zrodloBox.setSelectedItem(bookEditData.getZrodloId());
        statusBox.setSelectedItem(bookEditData.getStatus());
        uwagiBox.setText(bookEditData.getUwagi());
        uwagiBox.setCaretPosition(0);

        authorsBox.invalidate();
        titleBox.grabFocus();
        this.setTitle("Edycja książki");
        this.setModal(true);
        this.setVisible(true);

        if (result == AcceptCancelResult.ACCEPT) {

            BookEditData modifiedEditData = new BookEditData();

            modifiedEditData.setId(bookEditData.getId());
            modifiedEditData.setAutorzy(authorsBox.getAuthors());
            modifiedEditData.setTytul(titleBox.getText());
            modifiedEditData.setCena(cenaBox.getValue());
            modifiedEditData.setWartosc(wartoscBox.getValue());
            modifiedEditData.setDataWlaczenia(dataWlaczeniaBox.getValue());
            modifiedEditData.setZrodlo((String) zrodloBox.getSelectedItem());
            modifiedEditData.setStatus((BookStatus) statusBox.getSelectedItem());
            modifiedEditData.setUwagi(uwagiBox.getText());

            if (modifiedEditData.getStatus() != bookEditData.getStatus()) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Zmieniasz status książki. Czy robisz to świadomie? Jeśli książka jest faktycznie wypożyczana, zwracana, ubytkowana lub całkowicie kasowana, należy użyć przycisków na liście książek.", "Zmiana statusu ksi¹¿ki",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result != JOptionPane.OK_OPTION) {
                    setVisible(true);
                    return;
                }
            }

            bookDao.updateBook(modifiedEditData);
        }
    }

    @Override
    public Integer createBook() {
        idBox.setText("");
        authorsBox.setAuthors(new HashSet<ListElement>());
        titleBox.setText("");
        cenaBox.setValue(null);
        wartoscBox.setValue(null);
        dataWlaczeniaBox.setValue(Calendar.getInstance().getTime());
        zrodloBox.setSelectedIndex(0);
        statusBox.setSelectedIndex(0);
        uwagiBox.setText("");

        this.setTitle("Dodawanie nowej książki");
        this.invalidate();
        this.setModal(true);
        this.setVisible(true);

        if (result == AcceptCancelResult.ACCEPT) {
            BookCreationData bookCreationData = new BookCreationData();

            bookCreationData.setAutorzy(authorsBox.getAuthors());
            bookCreationData.setTytul(titleBox.getText());
            bookCreationData.setCena(cenaBox.getValue());
            bookCreationData.setWartosc(wartoscBox.getValue());
            bookCreationData.setDataWlaczenia(dataWlaczeniaBox.getValue());
            bookCreationData.setZrodlo((String) zrodloBox.getSelectedItem());
            bookCreationData.setUwagi(uwagiBox.getText());

            return bookDao.createBook(bookCreationData);
        } else {
            return null;
        }
    }
}