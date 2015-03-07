package sebcel.inwentarz.gui.booklist;

import sebcel.gui.list.SelectionStatus;

public interface ISelectionListener<T> {

    public void statusChanged(SelectionStatus<T> selectionStatus);

}
