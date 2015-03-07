package sebcel.inwentarz.gui.authorlist;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AuthorListButtonPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private JButton newBookButton = new JButton("Dodaj");
    private JButton deleteBookButton = new JButton("Usuñ");
    private JButton editBookButton = new JButton("Edytuj");
    
    private JPanel operationButtons = new JPanel();
    private JPanel dummyPanel = new JPanel();
    
    private IAuthorListEventListener listener;
    
    public AuthorListButtonPanel() {
	this.setLayout(new GridLayout());
	
	operationButtons.setBorder(BorderFactory.createTitledBorder("Zarz¹dzanie"));
	operationButtons.setLayout(new GridLayout());
	
	dummyPanel.setLayout(new GridLayout());
	
	operationButtons.add(newBookButton);
	operationButtons.add(editBookButton);
	operationButtons.add(deleteBookButton);
	
	this.add(operationButtons);
	this.add(dummyPanel);
	
	newBookButton.setToolTipText("Dodanie nowego autora");
	deleteBookButton.setToolTipText("Usuniêcie autora");
	editBookButton.setToolTipText("Edycja danych autora");
	
	newBookButton.addActionListener(new ActionListener() {
	   @Override
	    public void actionPerformed(ActionEvent e) {
	       listener.addNewElement();
	    }
	});
	
	deleteBookButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		listener.deleteSelectedElement();
	    }	
	});
	
	editBookButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		listener.editSelectedElement();
	    }
	});
    }
    
    public void setListEventListener(IAuthorListEventListener listener) {
	this.listener = listener;
    }
}