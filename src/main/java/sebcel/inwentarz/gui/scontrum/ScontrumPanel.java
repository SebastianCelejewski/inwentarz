package sebcel.inwentarz.gui.scontrum;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import sebcel.inwentarz.dao.definition.IScontrumDao;
import sebcel.inwentarz.dao.dto.ScontrumStatisticsData;
import sebcel.inwentarz.event.IDataChangeListener;
import sebcel.inwentarz.gui.comparators.IComparatorFactory;
import sebcel.inwentarz.gui.scontrumlist.ScontrumListPanel;

public class ScontrumPanel extends JPanel implements IDataChangeListener {

    private static final long serialVersionUID = -2398654463702698404L;

    private IScontrumDao scontrumDao;

    private ScontrumInfoPanel infoPanel;
    private ScontrumListPanel listPanel;

    public ScontrumPanel(IScontrumDao scontrumDao, IComparatorFactory comparatorFactory, ScontrumListPanel listPanel) {
        this.scontrumDao = scontrumDao;
        this.infoPanel = new ScontrumInfoPanel();
        this.listPanel = listPanel;

        initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        this.add(infoPanel, BorderLayout.NORTH);
        this.add(listPanel, BorderLayout.CENTER);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refresh();
            }
        });
    }

    protected void refresh() {
        if (scontrumDao.isScontrumOpen()) {
            int scontrumId = scontrumDao.getOpenScontrumId();
            ScontrumStatisticsData data = scontrumDao.getScontrumStatistics(scontrumId);
            infoPanel.setData(data);
        } else {
            infoPanel.setData(null);
        }
    }

    @Override
    public void dataChanged() {
        refresh();
    }
}