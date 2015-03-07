package sebcel.inwentarz.gui.booklist;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import sebcel.inwentarz.gui.booklist.BookListFilterModel.ScontrumFilterValue;
import sebcel.inwentarz.gui.components.FilterElement;
import sebcel.inwentarz.gui.list.IFilterListener;

public class BookListFilterPanel extends JPanel implements IBookListFilter, DocumentListener, ActionListener {

    private static final long serialVersionUID = 1L;

    private FilterElement idFilterElement = new FilterElement("Nr inw.", "Numer inwentarzowy ksi¹¿ki");
    private FilterElement titleFilterElement = new FilterElement("Tytu³", "Fragment tytu³u ksi¹¿ki");
    private FilterElement nameFilterElement = new FilterElement("Autor", "Fragment imienia lub nazwiska autora");
    private ScontrumFilter scontrumFilter = new ScontrumFilter();

    private Set<IFilterListener> listeners = new HashSet<IFilterListener>();

    public BookListFilterPanel() {
		
	this.setLayout(new GridBagLayout());
	this.setBorder(BorderFactory.createTitledBorder("Filtr listy ksi¹¿ek"));
	this.add(idFilterElement, filterConstraints(0,0));
	this.add(titleFilterElement, filterConstraints(1,0));
	this.add(nameFilterElement, filterConstraints(2,0));
	this.add(scontrumFilter, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 1, 1));

	idFilterElement.addDocumentListener(this);
	titleFilterElement.addDocumentListener(this);
	nameFilterElement.addDocumentListener(this);
	scontrumFilter.addActionListener(this);
    }

    private GridBagConstraints filterConstraints(int x, int y) {
	return new GridBagConstraints(x, y, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, 1);
    }

    @Override
    public void addFilterListener(IFilterListener filterListener) {
	listeners.add(filterListener);
    }

    private void filterChanged() {
	for (IFilterListener listener : listeners) {
	    listener.filterChanged();
	}
    }

    @Override
    public BookListFilterModel getFilter() {
	Integer id = idFilterElement.getInt();
	String name = nameFilterElement.getText();
	String title = titleFilterElement.getText();
	ScontrumFilterValue scontrumFilterValue = scontrumFilter.getValue();
	return new BookListFilterModel(id, name, title, scontrumFilterValue);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
	filterChanged();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
	filterChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
	filterChanged();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	filterChanged();
    }

}