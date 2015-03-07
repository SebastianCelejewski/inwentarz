package sebcel.inwentarz.gui.components;

import javax.swing.JButton;

import sebcel.gui.list.SelectionStatus;
import sebcel.inwentarz.gui.booklist.ISelectionListener;
import sebcel.inwentarz.logic.definition.ILifecycleManager;

public class LifecycleButton<T> extends JButton implements ISelectionListener<T> {

    private static final long serialVersionUID = 1L;
    private T destinationStatus;
    private ILifecycleManager<T> lifecycleManager;

    public LifecycleButton(String text, String toolTip, T destinationStatus, ILifecycleManager<T> lifecycleManager) {
	super(text);
	super.setToolTipText(toolTip);
	this.destinationStatus = destinationStatus;
	this.lifecycleManager = lifecycleManager;
    }

    @Override
    public void statusChanged(SelectionStatus<T> selectionStatus) {
	if (selectionStatus.getNumberOfSelectedElements() != 1) {
	    this.setEnabled(false);
	} else {
	    this.setEnabled(lifecycleManager.getAllowedTransitions(selectionStatus.getStatus()).contains(destinationStatus));
	}
    }
}