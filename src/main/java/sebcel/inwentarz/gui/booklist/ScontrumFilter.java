package sebcel.inwentarz.gui.booklist;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import sebcel.inwentarz.gui.booklist.BookListFilterModel.ScontrumFilterValue;

public class ScontrumFilter extends JPanel {

    private static final long serialVersionUID = 1L;

    private JCheckBox scontrumFilter = new JCheckBox("Skontrum");
    private JRadioButton verified = new JRadioButton("Ksi¹¿ki zweryfikowane");
    private JRadioButton notVerified = new JRadioButton("Ksi¹¿ki niezweryfikowane");

    private Collection<ActionListener> listeners = new ArrayList<ActionListener>();

    public ScontrumFilter() {
	initialize();
	updateState();
    }

    private void initialize() {
	this.setLayout(new GridBagLayout());
	this.add(scontrumFilter, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
	this.add(verified, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
	this.add(notVerified, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

	scontrumFilter.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		updateState();
	    }
	});

	verified.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		notVerified.setSelected(!verified.isSelected());
		updateState();
	    }
	});

	notVerified.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		verified.setSelected(!notVerified.isSelected());
		updateState();
	    }
	});

	scontrumFilter.setSelected(false);
    }

    private void updateState() {
	verified.setEnabled(scontrumFilter.isSelected());
	notVerified.setEnabled(scontrumFilter.isSelected());
	if (!verified.isSelected() && !notVerified.isSelected()) {
	    verified.setSelected(true);
	}
	for (ActionListener listener : listeners) {
	    listener.actionPerformed(new ActionEvent(this, 0, null));
	}
    }

    public void addActionListener(ActionListener listener) {
	listeners.add(listener);
    }

    public boolean isSelected() {
	return scontrumFilter.isSelected();
    }

    public ScontrumFilterValue getValue() {
	if (!scontrumFilter.isSelected()) {
	    return ScontrumFilterValue.NONE;
	}
	if (verified.isSelected()) {
	    return ScontrumFilterValue.VERIFIED;
	} else {
	    return ScontrumFilterValue.NOT_VERIFIED;
	}
    }
}