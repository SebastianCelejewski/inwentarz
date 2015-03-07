package sebcel.inwentarz.gui.authorlist;

import java.awt.BorderLayout;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sebcel.inwentarz.dao.utils.InwentarzException;
import sebcel.inwentarz.gui.list.IFilterListener;
import sebcel.inwentarz.gui.utils.ListUtils;

public class AuthorListPanel extends JPanel {

    private static final long serialVersionUID = 8780701064368207552L;

    private final AuthorListFilterPanel filterPanel;
    private final AuthorListButtonPanel buttonPanel;
    private final AuthorListTablePanel tablePanel;

    private IAuthorCreator authorCreator;
    private IAuthorEditor authorEditor;
    private IAuthorDeletor authorDeletor;

    public AuthorListPanel(AuthorListFilterPanel filterPanel, AuthorListButtonPanel buttonPanel, AuthorListTablePanel listTablePanel, IAuthorCreator authorCreator, IAuthorEditor authorEditor, IAuthorDeletor authorDeletor) {
	this.filterPanel = filterPanel;
	this.buttonPanel = buttonPanel;
	this.tablePanel = listTablePanel;

	this.authorCreator = authorCreator;
	this.authorEditor = authorEditor;
	this.authorDeletor = authorDeletor;

	initialize();
    }

    private void initialize() {
	this.setLayout(new BorderLayout());
	this.add(filterPanel, BorderLayout.NORTH);
	this.add(tablePanel, BorderLayout.CENTER);
	this.add(buttonPanel, BorderLayout.SOUTH);

	filterPanel.addFilterListener(new IFilterListener() {
	    @Override
	    public void filterChanged() {
		tablePanel.setFilter(filterPanel.getFilter());
	    }
	});

	buttonPanel.setListEventListener(new IAuthorListEventListener() {
	    @Override
	    public void addNewElement() {
		authorCreator.createAuthor();
		tablePanel.reload();
	    }

	    @Override
	    public void deleteSelectedElement() {
		Collection<Integer> selectedAuthorIds = tablePanel.getSelectedAuthors();
		Integer selectedAuthorId = ListUtils.getSingleId(selectedAuthorIds);
		if (selectedAuthorId != null) {
		    try {
			authorDeletor.deleteAuthor(selectedAuthorId.intValue());
		    } catch (InwentarzException ex) {
			JOptionPane.showMessageDialog(AuthorListPanel.this, ex.getMessage());
		    }
		    tablePanel.reload();
		}
	    }

	    @Override
	    public void editSelectedElement() {
		Collection<Integer> selectedAuthorIds = tablePanel.getSelectedAuthors();
		Integer selectedAuthorId = ListUtils.getSingleId(selectedAuthorIds);
		if (selectedAuthorId != null) {
		    authorEditor.editAuthor(selectedAuthorId.intValue());
		    tablePanel.reload();
		}
	    }
	});
    }
}