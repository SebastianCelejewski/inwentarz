package sebcel.inwentarz.gui.scontrum;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import sebcel.gui.DateViewField;
import sebcel.gui.IntegerViewField;
import sebcel.inwentarz.dao.definition.IScontrumDao;
import sebcel.inwentarz.dao.dto.ScontrumDetails;
import sebcel.inwentarz.gui.scontrumlist.IScontrumViewer;
import sebcel.inwentarz.gui.utils.GuiTools;

public class ScontrumViewFrame extends JDialog implements IScontrumViewer {

    private static final long serialVersionUID = 1L;

    private IScontrumDao scontrumDao;

    private int y = 0;

    private IntegerViewField id = new IntegerViewField();
    private JTextField status = new JTextField();
    private DateViewField dataRozpoczecia = new DateViewField();
    private DateViewField dataZakonczenia = new DateViewField();
    private IntegerViewField liczbaPosiadanychKsiazek = new IntegerViewField();
    private IntegerViewField liczbaZweryfikowanychKsiazek = new IntegerViewField();
    private IntegerViewField liczbaNiezweryfikowanychKsiazek = new IntegerViewField();

    private JPanel buttonPanel = new JPanel();
    private JButton closeButton = new JButton("Zamknij");

    public ScontrumViewFrame(IScontrumDao scontrumDao) {
	this.scontrumDao = scontrumDao;

	this.setLayout(new GridBagLayout());
	this.setSize(640, 480);
	GuiTools.centerWindow(this);

	addElement("Numer", id);
	addElement("Data rozpoczêcia", dataRozpoczecia);
	addElement("Data zakoñczenia", dataZakonczenia);
	addElement("Status", status);
	addElement("Liczba posiadanych ksi¹¿ek", liczbaPosiadanychKsiazek);
	addElement("Liczba zweryfikowanych ksi¹¿ek", liczbaZweryfikowanychKsiazek);
	addElement("Liczba niezweryfikowanych ksi¹¿ek", liczbaNiezweryfikowanychKsiazek);

	this.add(buttonPanel, new GridBagConstraints(0, y++, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 1, 1));
	buttonPanel.setLayout(new GridLayout());
	buttonPanel.add(closeButton);

	closeButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		setVisible(false);
	    }
	});
    }

    @Override
    public void viewScontrum(int scontrumId) {
	ScontrumDetails details = scontrumDao.getScontrumDetails(scontrumId);

	id.setValue(details.getId());
	dataRozpoczecia.setDate(details.getDataRozpoczecia());
	dataZakonczenia.setDate(details.getDataZakonczenia());
	status.setText(details.getStatus().getName());
	liczbaPosiadanychKsiazek.setValue(details.getLiczbaPosiadanychKsiazek());
	liczbaZweryfikowanychKsiazek.setValue(details.getLiczbaZweryfikowanychKsiazek());
	liczbaNiezweryfikowanychKsiazek.setValue(details.getLiczbaNiezweryfikowanychKsiazek());

	this.setTitle("Szczegó³y skontrum");
	this.setModal(true);
	this.pack();
	this.setVisible(true);
    }

    private void addElement(String label, JTextComponent component) {
	this.add(new JLabel(label), createLabelConstraints(y));
	this.add(component, createBoxConstraints(y, GridBagConstraints.HORIZONTAL, 0.0));
	component.setEditable(false);
	y++;
    }

    private GridBagConstraints createLabelConstraints(int y) {
	return new GridBagConstraints(0, y, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 2, 2);
    }

    private GridBagConstraints createBoxConstraints(int y, int fill, double weightY) {
	return new GridBagConstraints(1, y, 1, 1, 1.0, weightY, GridBagConstraints.CENTER, fill, new Insets(2, 2, 2, 2), 2, 2);
    }
}
