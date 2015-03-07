package sebcel.inwentarz.gui.scontrumlist;

import java.awt.BorderLayout;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sebcel.inwentarz.dao.definition.IScontrumDao;
import sebcel.inwentarz.dao.dto.ScontrumStatus;
import sebcel.inwentarz.event.IDataChangeListener;
import sebcel.inwentarz.gui.comparators.IComparatorFactory;
import sebcel.inwentarz.gui.utils.ListUtils;
import sebcel.inwentarz.logic.definition.ILifecycleManager;

public class ScontrumListPanel extends JPanel implements IScontrumListEventListener {

    private static final long serialVersionUID = 1L;

    private final ScontrumListButtonPanel buttonPanel;
    private final ScontrumListTablePanel tablePanel;

    private IScontrumViewer scontrumViewer;
    private IScontrumDao scontrumDao;
    private IDataChangeListener dataChangeListener;

    public ScontrumListPanel(IScontrumDao scontrumDao, IComparatorFactory comparatorFactory, IScontrumViewer scontrumViewer, ILifecycleManager<ScontrumStatus> lifecycleManager) {
	this.scontrumViewer = scontrumViewer;
	this.scontrumDao = scontrumDao;

	buttonPanel = new ScontrumListButtonPanel(lifecycleManager);
	tablePanel = new ScontrumListTablePanel(scontrumDao, comparatorFactory);

	initialize();
    }
    
    public void setDataChangeListener(IDataChangeListener dataChangeListener)
    {
	this.dataChangeListener = dataChangeListener;
    }

    private void initialize() {
	this.setLayout(new BorderLayout());
	this.add(tablePanel, BorderLayout.CENTER);
	this.add(buttonPanel, BorderLayout.SOUTH);

	tablePanel.addScontrumStatusListener(buttonPanel);
	buttonPanel.setScontrumListEventListener(this);
    }

    @Override
    public void finishCurrentScontrum() {
	if (scontrumDao.isScontrumOpen())
	{
	    int scontrumId = scontrumDao.getOpenScontrumId();
	    int liczbaNiezweryfikowanychKsiazek = scontrumDao.getScontrumStatistics(scontrumId).getLiczbaNiezweryfikowanychKsiazek();
	    int result = JOptionPane.showConfirmDialog(this, "Nie wszystkie ksi¹¿ki zosta³y zweryfikowane - pozosta³o "+liczbaNiezweryfikowanychKsiazek+" ksi¹¿ek.\nCzy mimo to chcesz zakoñczyæ skontrum?");
	    if (result == JOptionPane.YES_OPTION)
	    {
		scontrumDao.closeScontrum(scontrumId);
		tablePanel.reload();
		dataChangeListener.dataChanged();
	    }
	}
    }

    @Override
    public void startNewScontrum() {
	if (scontrumDao.isScontrumOpen())
	{
	    JOptionPane.showMessageDialog(this, "Skontrum jest rozpoczête. Nie mo¿na rozpocz¹æ nowego skontrum przed zakoñczeniem poprzedniego.", "B³¹d", JOptionPane.INFORMATION_MESSAGE);
	    return;
	}
	scontrumDao.startScontrum();
	tablePanel.reload();
	dataChangeListener.dataChanged();
    }

    @Override
    public void viewSelectedScontrumDetails() {
	Collection<Integer> selectedScontrumIds = tablePanel.getSelectedScontrums();
	Integer selectedScontrumId = ListUtils.getSingleId(selectedScontrumIds);
	if (selectedScontrumId != null) {
	    scontrumViewer.viewScontrum(selectedScontrumId);
	}
    }
}