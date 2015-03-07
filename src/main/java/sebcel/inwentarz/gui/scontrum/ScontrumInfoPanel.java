package sebcel.inwentarz.gui.scontrum;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import sebcel.inwentarz.dao.dto.ScontrumStatisticsData;
import sebcel.inwentarz.gui.utils.LayoutUtils;

public class ScontrumInfoPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField status = new JTextField();
    private JTextField dataRozpoczecia = new JTextField();
    private JTextField liczbaZweryfikowanychKsiazek = new JTextField();
    private JTextField liczbaNiezweryfikowanychKsiazek = new JTextField();
    private JTextField liczbaPosiadanychKsiazek = new JTextField();

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private JPanel progressPanel = new JPanel();
    private JProgressBar progressBar = new JProgressBar();

    public ScontrumInfoPanel() {

	this.setLayout(new GridBagLayout());

	progressPanel.setLayout(new GridBagLayout());
	progressPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Informacje na temat bie¿¹cego skontrum"));

	progressPanel.add(new JLabel("Status: ", JLabel.RIGHT), LayoutUtils.labelConstraints(0, 0));
	progressPanel.add(status, LayoutUtils.textConstraints(1, 0));

	progressPanel.add(new JLabel("Data rozpoczêcia: ", JLabel.RIGHT), LayoutUtils.labelConstraints(0, 1));
	progressPanel.add(dataRozpoczecia, LayoutUtils.textConstraints(1, 1));

	progressPanel.add(new JLabel("Postêp: ", JLabel.RIGHT), LayoutUtils.labelConstraints(0, 2));
	progressPanel.add(progressBar, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, 1));


	progressPanel.add(new JLabel("Liczba zweryfikowanych ksi¹¿ek: ", JLabel.RIGHT), LayoutUtils.labelConstraints(2, 0));
	progressPanel.add(liczbaZweryfikowanychKsiazek, LayoutUtils.textConstraints(3, 0));

	progressPanel.add(new JLabel("Liczba niezweryfikowanych ksi¹¿ek: ", JLabel.RIGHT), LayoutUtils.labelConstraints(2, 1));
	progressPanel.add(liczbaNiezweryfikowanychKsiazek, LayoutUtils.textConstraints(3, 1));

	progressPanel.add(new JLabel("Ca³kowita liczba posiadanych ksi¹¿ek: ", JLabel.RIGHT), LayoutUtils.labelConstraints(2, 2));
	progressPanel.add(liczbaPosiadanychKsiazek, LayoutUtils.textConstraints(3, 2));

	this.add(progressPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, 1));
    }

    public void setData(ScontrumStatisticsData data) {
	if (data != null) {
	    set(data);
	} else {
	    reset();
	}
    }

    private void set(ScontrumStatisticsData data) {
	status.setText("Skontrum jest rozpoczête (id = " + Integer.toString(data.getId()) + ")");
	dataRozpoczecia.setText(df.format(data.getDataRozpoczecia()));
	liczbaZweryfikowanychKsiazek.setText(Integer.toString(data.getLiczbaZweryfikowanychKsiazek()));
	liczbaNiezweryfikowanychKsiazek.setText(Integer.toString(data.getLiczbaNiezweryfikowanychKsiazek()));
	liczbaPosiadanychKsiazek.setText(Integer.toString(data.getLiczbaPosiadanychKsiazek()));

	status.setEditable(true);
	dataRozpoczecia.setEditable(true);
	liczbaPosiadanychKsiazek.setEditable(true);
	liczbaZweryfikowanychKsiazek.setEditable(true);
	liczbaNiezweryfikowanychKsiazek.setEditable(true);

	setProgressBar(data);
    }

    private void setProgressBar(ScontrumStatisticsData data) {
	progressBar.setMaximum(data.getLiczbaPosiadanychKsiazek());
	progressBar.setMinimum(0);
	progressBar.setValue(data.getLiczbaZweryfikowanychKsiazek());
	float ratio = (float) data.getLiczbaZweryfikowanychKsiazek() / (float) data.getLiczbaPosiadanychKsiazek();
	progressBar.setForeground(new Color(1 - ratio, ratio, 0));
    }

    private void reset() {
	status.setText("Skontrum nie jest rozpoczête");
	dataRozpoczecia.setText("");
	liczbaZweryfikowanychKsiazek.setText("");
	liczbaNiezweryfikowanychKsiazek.setText("");
	liczbaPosiadanychKsiazek.setText("");
	progressBar.setEnabled(false);

	status.setEditable(false);
	dataRozpoczecia.setEditable(false);
	liczbaPosiadanychKsiazek.setEditable(false);
	liczbaZweryfikowanychKsiazek.setEditable(false);
	liczbaNiezweryfikowanychKsiazek.setEditable(false);
    }
}