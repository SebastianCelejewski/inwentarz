package sebcel.inwentarz.gui.authorlist;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import sebcel.inwentarz.gui.components.FilterElement;
import sebcel.inwentarz.gui.list.IFilterListener;

public class AuthorListFilterPanel extends JPanel implements IAuthorListFilter, DocumentListener {

    private static final long serialVersionUID = 1L;

    private FilterElement nameFilterElement = new FilterElement("Autor", "Fragment imienia lub nazwiska autora");

    private Set<IFilterListener> listeners = new HashSet<IFilterListener>();

    public AuthorListFilterPanel() {
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createTitledBorder("Filtr listy autorów"));
        this.add(nameFilterElement, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, 1));

        nameFilterElement.addDocumentListener(this);
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
    public AuthorListFilterModel getFilter() {
        String name = nameFilterElement.getText();
        return new AuthorListFilterModel(name);
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
}