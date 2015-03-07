package sebcel.inwentarz.gui.bookregister;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import sebcel.inwentarz.dao.definition.IBookDao;

public class BookRegisterPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JScrollPane scrollPane = new JScrollPane();
    private JTable table = new JTable();
    private TableColumnModel columnModel = new RegisterTableColumnModel();
    private JTextArea details = new JTextArea();
    private BookRegisterTableModel tableModel;
    private JPanel detailsPanel = new JPanel();

    public BookRegisterPanel(IBookDao bookDao) {
	tableModel = new BookRegisterTableModel(bookDao);
	scrollPane.setViewportView(table);

	details.setEditable(false);
	details.setLineWrap(true);

	detailsPanel.setLayout(new BorderLayout());
	detailsPanel.add(details, BorderLayout.CENTER);
	detailsPanel.setBorder(BorderFactory.createTitledBorder("Szczegó³y operacji"));

	this.setLayout(new GridLayout());
	this.add(scrollPane);
	this.add(detailsPanel);

	table.setModel(tableModel);
	table.setColumnModel(columnModel);
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		int selectedRow = table.getSelectedRow();
		if (selectedRow > -1) {
		    details.setText(tableModel.getDescription(selectedRow));
		}
	    }
	});
    }

    public void reload(int bookId) {
	System.out.println("[BookRegisterPanel][Reload] id=" + bookId);
	tableModel.reload(bookId);
	table.getSelectionModel().clearSelection();
	details.setText("");
	table.invalidate();
    }
}