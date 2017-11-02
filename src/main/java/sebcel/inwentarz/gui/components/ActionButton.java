package sebcel.inwentarz.gui.components;

import static sebcel.inwentarz.gui.booklist.SelectionRequirement.ANY;
import static sebcel.inwentarz.gui.booklist.SelectionRequirement.SINGLE;

import javax.swing.JButton;

import sebcel.gui.list.SelectionStatus;
import sebcel.inwentarz.gui.booklist.ISelectionListener;
import sebcel.inwentarz.gui.booklist.SelectionRequirement;

public class ActionButton<T> extends JButton implements ISelectionListener<T> {

    private static final long serialVersionUID = 1L;
    private SelectionRequirement selectionRequirement;

    public ActionButton(String text, String toolTip, SelectionRequirement selectionRequirement) {
        super(text);
        super.setToolTipText(toolTip);
        this.selectionRequirement = selectionRequirement;
    }

    @Override
    public void statusChanged(SelectionStatus<T> selectionStatus) {
        if (selectionRequirement == ANY) {
            this.setEnabled(true);
            return;
        }
        if (selectionRequirement == SINGLE && selectionStatus.getNumberOfSelectedElements() != 1) {
            this.setEnabled(false);
            return;
        }
        this.setEnabled(true);
    }
}