package sebcel.inwentarz.gui.statistics;

import java.awt.GridBagLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sebcel.inwentarz.dao.dto.BookStatisticsData;
import sebcel.inwentarz.gui.utils.LayoutUtils;

public class BookStatisticsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField totalBooks = new JTextField();
    private JTextField existingBooks = new JTextField();
    private JTextField nonExistingBooks = new JTextField();

    private JTextField ownedBooks = new JTextField();
    private JTextField availableBooks = new JTextField();
    private JTextField lentBooks = new JTextField();

    private JTextField booksPrice = new JTextField();
    private JTextField booksValue = new JTextField();

    private DecimalFormat moneyFormat = new DecimalFormat("0.00 zł");

    public BookStatisticsPanel() {
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Statystyka zbiorów"));

        this.add(new JLabel("Całkowita liczba książek w bazie: ", JLabel.RIGHT), LayoutUtils.labelConstraints(0, 0));
        this.add(totalBooks, LayoutUtils.textConstraints(1, 0));

        this.add(new JLabel("w tym posiadane: ", JLabel.RIGHT), LayoutUtils.labelConstraints(0, 1));
        this.add(existingBooks, LayoutUtils.textConstraints(1, 1));

        this.add(new JLabel("wycofane: ", JLabel.RIGHT), LayoutUtils.labelConstraints(0, 2));
        this.add(nonExistingBooks, LayoutUtils.textConstraints(1, 2));

        this.add(new JLabel("Liczba posiadanych książek: ", JLabel.RIGHT), LayoutUtils.labelConstraints(2, 0));
        this.add(ownedBooks, LayoutUtils.textConstraints(3, 0));

        this.add(new JLabel("w tym dostępne: ", JLabel.RIGHT), LayoutUtils.labelConstraints(2, 1));
        this.add(availableBooks, LayoutUtils.textConstraints(3, 1));

        this.add(new JLabel("wypożyczone: ", JLabel.RIGHT), LayoutUtils.labelConstraints(2, 2));
        this.add(lentBooks, LayoutUtils.textConstraints(3, 2));

        this.add(new JLabel("Cena wszystkich książek: ", JLabel.RIGHT), LayoutUtils.labelConstraints(4, 0));
        this.add(booksPrice, LayoutUtils.textConstraints(5, 0));

        this.add(new JLabel("Wartość wszystkich książek: ", JLabel.RIGHT), LayoutUtils.labelConstraints(4, 1));
        this.add(booksValue, LayoutUtils.textConstraints(5, 1));

    }

    public void setData(BookStatisticsData bookStatisticsData) {
        totalBooks.setText(Integer.toString(bookStatisticsData.getTotalBooks()));
        existingBooks.setText(Integer.toString(bookStatisticsData.getExistingBooks()));
        nonExistingBooks.setText(Integer.toString(bookStatisticsData.getNotExistingBooks()));

        ownedBooks.setText(Integer.toString(bookStatisticsData.getExistingBooks()));
        availableBooks.setText(Integer.toString(bookStatisticsData.getAvailableBooks()));
        lentBooks.setText(Integer.toString(bookStatisticsData.getNonAvailableBooks()));

        booksPrice.setText(moneyFormat.format(bookStatisticsData.getTotalBooksPrice()));
        booksValue.setText(moneyFormat.format(bookStatisticsData.getTotalBooksValue()));
    }
}
