package sebcel.inwentarz.gui.books;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import sebcel.gui.CurrencyEditField;
import sebcel.gui.DateViewField;
import sebcel.inwentarz.dao.definition.IBookDao;
import sebcel.inwentarz.dao.definition.IDictionaryDao;
import sebcel.inwentarz.dao.dto.BookDetails;
import sebcel.inwentarz.dao.dto.BookStatus;
import sebcel.inwentarz.gui.booklist.IBookDetailsViewer;
import sebcel.inwentarz.gui.utils.GuiTools;
import sebcel.inwentarz.logic.definition.ILifecycleManager;

public class BookDetailsFrame extends JDialog implements IBookDetailsViewer {

    private static final long serialVersionUID = 1L;

    private IBookDao bookDao;

    private int y = 0;

    private JTextField idBox = new JTextField();
    private JTextField authorsBox = new JTextField();
    private JTextField titleBox = new JTextField();
    private JTextField statusBox = new JTextField();
    private DateViewField dataWlaczeniaBox = new DateViewField();
    private JTextField zrodloBox = new JTextField();
    private CurrencyEditField cenaBox = new CurrencyEditField();
    private CurrencyEditField wartoscBox = new CurrencyEditField();
    private JScrollPane uwagiScroll = new JScrollPane();
    private JTextArea uwagiBox = new JTextArea();
    private DateViewField dataOstatniejWeryfikacji = new DateViewField();

    private JPanel buttonPanel = new JPanel();
    private JButton closeButton = new JButton("Zamknij");

    public BookDetailsFrame(IBookDao bookDao, IDictionaryDao dictionaryDao) {
	this.bookDao = bookDao;

	uwagiBox.setLineWrap(true);
	uwagiBox.setBorder(BorderFactory.createEtchedBorder());
	uwagiBox.setEditable(false);

	uwagiScroll.setViewportView(uwagiBox);

	this.setLayout(new GridBagLayout());
	this.setSize(640, 480);
	GuiTools.centerWindow(this);

	addElement("Nr inw.", idBox);
	addElement("Tytu≥", titleBox);
	addElement("Autorzy", authorsBox);
	addElement("Status", statusBox);
	addElement("Data w≥πczenia", dataWlaczeniaBox);
	addElement("èrÛd≥o", zrodloBox);
	addElement("Cena", cenaBox);
	addElement("WartoúÊ", wartoscBox);
	addFillElement("Uwagi", uwagiScroll);
	addElement("Data ostatniej weryfikacji", dataOstatniejWeryfikacji);

	this.add(buttonPanel, new GridBagConstraints(0, y++, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 1, 1));
	buttonPanel.setLayout(new GridLayout());
	buttonPanel.add(closeButton);

	closeButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		setVisible(false);
	    }
	});
    }

    private void addElement(String label, JTextComponent component) {
	this.add(new JLabel(label), createLabelConstraints(y));
	this.add(component, createBoxConstraints(y, GridBagConstraints.HORIZONTAL, 0.0));
	component.setEditable(false);
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
    public void viewBookDetails(int bookId) {
	BookDetails bookDetails = bookDao.getBookDetails(bookId);

	idBox.setText(Integer.toString(bookDetails.getId()));
	authorsBox.setText(bookDetails.getAutorzy());
	titleBox.setText(bookDetails.getTytul());
	cenaBox.setValue(bookDetails.getCena());
	wartoscBox.setValue(bookDetails.getWartosc());
	dataWlaczeniaBox.setDate(bookDetails.getDataWlaczenia());
	zrodloBox.setText(bookDetails.getZrodlo());
	statusBox.setText(bookDetails.getStatus());
	uwagiBox.setText(bookDetails.getUwagi());
	uwagiBox.setCaretPosition(0);
	dataOstatniejWeryfikacji.setDate(bookDetails.getDataOstatniejWeryfikacji());

	authorsBox.invalidate();
	titleBox.grabFocus();
	this.setTitle("SzczegÛ≥y ksiπøki");
	this.setModal(true);
	this.setVisible(true);
    }
}