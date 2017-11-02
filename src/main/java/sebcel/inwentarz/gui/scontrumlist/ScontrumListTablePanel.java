package sebcel.inwentarz.gui.scontrumlist;

import java.awt.BorderLayout;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.table.TableRowSorter;

import sebcel.gui.list.IListSelectionListener;
import sebcel.gui.list.ListTable;
import sebcel.gui.list.SelectionStatus;
import sebcel.inwentarz.dao.definition.IScontrumDao;
import sebcel.inwentarz.dao.dto.ScontrumListElement;
import sebcel.inwentarz.dao.dto.ScontrumStatus;
import sebcel.inwentarz.gui.booklist.ISelectionListener;
import sebcel.inwentarz.gui.comparators.IComparatorFactory;

public class ScontrumListTablePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private ListTable<ScontrumListElement> table;
    private ScontrumListTableModel tableModel;
    private ScontrumListTableColumnModel tableColumnModel;
    private TableRowSorter<ScontrumListTableModel> sorter;
    private ScontrumListTableCellRenderer cellRenderer;

    private Set<ISelectionListener<ScontrumStatus>> scontrumStatusListeners = new HashSet<ISelectionListener<ScontrumStatus>>();

    public ScontrumListTablePanel(IScontrumDao scontrumDao, IComparatorFactory comparatorFactory) {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("Lista skontrum"));

        tableModel = new ScontrumListTableModel(scontrumDao);
        tableColumnModel = new ScontrumListTableColumnModel();
        sorter = new TableRowSorter<ScontrumListTableModel>(tableModel);

        cellRenderer = new ScontrumListTableCellRenderer(tableModel);

        table = new ListTable<ScontrumListElement>(tableModel, tableColumnModel, sorter, cellRenderer);
        table.addListSelectionListener(new IListSelectionListener<ScontrumListElement>() {

            @Override
            public void elementsWasSelected(Collection<ScontrumListElement> elements) {
                if (elements != null) {
                    int numberOfSelectedElements = elements.size();
                    for (ScontrumListElement element : elements) {
                        fireScontrumStatusChanged(new SelectionStatus<ScontrumStatus>(numberOfSelectedElements, element.getStatus()));
                    }
                }
            }
        });

        this.add(table, BorderLayout.CENTER);
        reload();
    }

    public void reload() {
        table.reload();
    }

    public Collection<Integer> getSelectedScontrums() {
        return table.getSelectedElementIds();
    }

    public void focus(Integer id) {
        table.setSelectedElementId(id);
    }

    private void fireScontrumStatusChanged(SelectionStatus<ScontrumStatus> selectionStatus) {
        for (ISelectionListener<ScontrumStatus> listener : scontrumStatusListeners) {
            listener.statusChanged(selectionStatus);
        }
    }

    public void addScontrumStatusListener(ISelectionListener<ScontrumStatus> scontrumStatusListener) {
        scontrumStatusListeners.add(scontrumStatusListener);
    }
}
