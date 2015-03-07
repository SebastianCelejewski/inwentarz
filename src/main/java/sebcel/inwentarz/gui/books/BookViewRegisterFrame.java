package sebcel.inwentarz.gui.books;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sebcel.inwentarz.dao.definition.IBookDao;
import sebcel.inwentarz.gui.booklist.IBookRegisterViewer;
import sebcel.inwentarz.gui.bookregister.BookRegisterPanel;
import sebcel.inwentarz.gui.utils.GuiTools;

public class BookViewRegisterFrame extends JDialog implements IBookRegisterViewer {

    private static final long serialVersionUID = 1L;

    private int y = 0;

    private JTextField idBox = new JTextField();

    private BookRegisterPanel registerPanel;
    private JPanel buttonPanel = new JPanel();
    private JButton closeButton = new JButton("Zamknij");

    public BookViewRegisterFrame(IBookDao bookDao) {
	registerPanel = new BookRegisterPanel(bookDao);

	this.setTitle("Rejestr operacji ksi¹¿ki");
	this.setLayout(new GridBagLayout());
	this.setSize(640, 480);
	GuiTools.centerWindow(this);

	this.add(registerPanel, new GridBagConstraints(0, y++, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
	this.add(buttonPanel, new GridBagConstraints(0, y++, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 1, 1));
	buttonPanel.setLayout(new GridLayout());
	buttonPanel.add(closeButton);

	closeButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		setVisible(false);
	    }
	});

	idBox.setEditable(false);
    }

    @Override
    public void viewBookRegister(int id) {
	registerPanel.reload(id);
	registerPanel.invalidate();
	this.setModal(true);
	this.setVisible(true);
    }
}