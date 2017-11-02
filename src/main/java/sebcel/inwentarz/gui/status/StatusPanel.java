package sebcel.inwentarz.gui.status;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sebcel.inwentarz.dao.definition.IStatisticsDao;
import sebcel.inwentarz.dao.dto.BookStatisticsData;
import sebcel.inwentarz.event.IDataChangeListener;

public class StatusPanel extends JPanel implements IDataChangeListener {

    private static final long serialVersionUID = 1L;

    private IStatisticsDao statisticsDao;

    private JTextField resourcesField;

    public StatusPanel(IStatisticsDao statisticsDao) {
        this.statisticsDao = statisticsDao;
        initialize();
    }

    private void initialize() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.resourcesField = new JTextField();
        this.add(resourcesField);
        updateZasobyField();
    }

    private void updateZasobyField() {
        BookStatisticsData bookStatistics = statisticsDao.getBookStatisticsData();
        String info = "Liczba książek ";
        info += "posiadanych: " + bookStatistics.getExistingBooks() + ", ";
        info += "dostępnych: " + bookStatistics.getAvailableBooks() + ", ";
        info += "wypożyczonych: " + bookStatistics.getNonAvailableBooks() + ".";

        resourcesField.setText(info);
    }

    @Override
    public void dataChanged() {
        updateZasobyField();
    }
}