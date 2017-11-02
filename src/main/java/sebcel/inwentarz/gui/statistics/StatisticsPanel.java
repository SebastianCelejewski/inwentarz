package sebcel.inwentarz.gui.statistics;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import sebcel.inwentarz.dao.definition.IStatisticsDao;
import sebcel.inwentarz.dao.dto.BookStatisticsData;
import sebcel.inwentarz.dao.dto.PurchaseStatistics;

public class StatisticsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private IStatisticsDao statisticsDao;

    private BookStatisticsPanel bookStatisticsPanel = new BookStatisticsPanel();
    private PurchaseStaticticsTable purchaseStatisticsTable = new PurchaseStaticticsTable();

    public StatisticsPanel(IStatisticsDao statisticsDao) {
        this.statisticsDao = statisticsDao;
        this.setLayout(new BorderLayout());
        this.add(bookStatisticsPanel, BorderLayout.NORTH);
        this.add(purchaseStatisticsTable, BorderLayout.CENTER);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refresh();
            }
        });
    }

    private void refresh() {
        BookStatisticsData statisticsData = statisticsDao.getBookStatisticsData();
        bookStatisticsPanel.setData(statisticsData);

        PurchaseStatistics purchaseStatistics = statisticsDao.getPurchaseStatistics();
        purchaseStatisticsTable.setData(purchaseStatistics);

    }
}