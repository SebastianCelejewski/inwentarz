package sebcel.inwentarz.gui.scontrumlist;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import sebcel.gui.list.SelectionStatus;
import sebcel.inwentarz.dao.dto.ScontrumStatus;
import sebcel.inwentarz.gui.booklist.ISelectionListener;
import sebcel.inwentarz.gui.booklist.SelectionRequirement;
import sebcel.inwentarz.gui.components.ActionButton;
import sebcel.inwentarz.gui.components.LifecycleButton;
import sebcel.inwentarz.logic.definition.ILifecycleManager;

public class ScontrumListButtonPanel extends JPanel implements ISelectionListener<ScontrumStatus> {

    private static final long serialVersionUID = 1L;

    private IScontrumListEventListener listener;

    private ActionButton<ScontrumStatus> viewDetailsButton;
    private ActionButton<ScontrumStatus> startNewScontrumButton;
    private LifecycleButton<ScontrumStatus> finishCurrentScontrumButton;

    public void setScontrumListEventListener(IScontrumListEventListener listener) {
	this.listener = listener;
    }

    public ScontrumListButtonPanel(ILifecycleManager<ScontrumStatus> lifecycleManager) {

	viewDetailsButton = new ActionButton<ScontrumStatus>("Szczegó³y", "Szczegó³y skontrum", SelectionRequirement.SINGLE);
	startNewScontrumButton = new ActionButton<ScontrumStatus>("Rozpocznij", "Rozpoczêcie nowego skontrum", SelectionRequirement.ANY);
	finishCurrentScontrumButton = new LifecycleButton<ScontrumStatus>("Zakoñcz", "Zakoñczenie trwaj¹cego skontrum", ScontrumStatus.ZAKONCZONE, lifecycleManager);

	this.setLayout(new GridLayout());
	this.add(viewDetailsButton);
	this.add(startNewScontrumButton);
	this.add(finishCurrentScontrumButton);

	viewDetailsButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		listener.viewSelectedScontrumDetails();
	    }
	});

	startNewScontrumButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		listener.startNewScontrum();
	    }
	});

	finishCurrentScontrumButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		listener.finishCurrentScontrum();
	    }
	});
    }

    @Override
    public void statusChanged(SelectionStatus<ScontrumStatus> selectionStatus) {
	viewDetailsButton.statusChanged(selectionStatus);
	startNewScontrumButton.statusChanged(selectionStatus);
	finishCurrentScontrumButton.statusChanged(selectionStatus);
    }
}