package sebcel.inwentarz.gui.authors;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import sebcel.inwentarz.dao.definition.IAuthorDao;
import sebcel.inwentarz.dao.utils.ListElement;
import sebcel.inwentarz.gui.authorlist.IAuthorCreator;
import sebcel.inwentarz.gui.utils.GuiTools;

public class AuthorPicker extends JDialog implements IAuthorPicker {

    private static final long serialVersionUID = 1L;

    private JTextField searchBox = new JTextField();
    private JScrollPane scrollPanel = new JScrollPane();
    private JList authorList = new JList();

    private JPanel buttonPanel = new JPanel();
    private JButton acceptButton = new JButton("ZatwierdŸ");
    private JButton cancelButton = new JButton("Anuluj");
    private JButton createButton = new JButton("Utwórz nowego");

    private IAuthorDao authorDao;

    public AuthorPicker(IAuthorDao authorDao, final IAuthorCreator authorCreator) {
	this.authorDao = authorDao;

	this.setLayout(new GridBagLayout());
	this.setSize(640, 480);
	this.setTitle("Wybierz autora");
	GuiTools.centerWindow(this);

	this.add(searchBox, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, 1));
	this.add(scrollPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
	this.add(buttonPanel, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 1, 1));
	buttonPanel.setLayout(new GridLayout());
	buttonPanel.add(acceptButton);
	buttonPanel.add(cancelButton);
	buttonPanel.add(createButton);
	scrollPanel.setViewportView(authorList);	

	acceptButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (authorList.getSelectedValue() != null) {
		    setVisible(false);
		}
	    }
	});

	cancelButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		setVisible(false);
	    }
	});
	
	createButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		Integer authorId = authorCreator.createAuthor();
		if (authorId != null) {
		    reloadAuthorList();
		    searchBox.setText(AuthorPicker.this.authorDao.getAuthorEditData(authorId).getNazwisko());
		}
	    }
	});
	
	searchBox.getDocument().addDocumentListener(new DocumentListener() {

	    @Override
	    public void changedUpdate(DocumentEvent e) {
		reloadAuthorList();
	    }

	    @Override
	    public void insertUpdate(DocumentEvent e) {
		reloadAuthorList();
	    }

	    @Override
	    public void removeUpdate(DocumentEvent e) {
		reloadAuthorList();
	    }
	});
	
	authorList.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mouseClicked(MouseEvent e) {
		if (e.getClickCount()>1) {
		    setVisible(false);
		}
	    }
	});
	
	reloadAuthorList();
    }

    private void reloadAuthorList() {
	List<ListElement> authors = authorDao.getAuthorList(searchBox.getText());
	authorList.setListData(authors.toArray(new ListElement[authors.size()]));
    }
    
    @Override
    public List<ListElement> selectAuthors() {
	this.setModal(true);
	this.setVisible(true);
	this.searchBox.grabFocus();
	List<ListElement> result = new ArrayList<ListElement>();
	for (Object o : authorList.getSelectedValues()) {
	    result.add((ListElement) o);
	}
	return result;
    }
}