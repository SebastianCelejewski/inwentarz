package sebcel.inwentarz.gui.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractListModel;

import sebcel.inwentarz.dao.utils.ListElement;

public class AuthorsBoxListModel extends AbstractListModel {

    private static final long serialVersionUID = 1L;

    private List<ListElement> data = new ArrayList<ListElement>();

    @Override
    public Object getElementAt(int index) {
	return data.get(index);
    }

    @Override
    public int getSize() {
	return data.size();
    }

    public void setData(Set<ListElement> authors) {
	data.clear();
	data.addAll(authors);
	this.fireContentsChanged(this, 0, getSize());
    }

    public Set<ListElement> getData() {
	return new HashSet<ListElement>(data);
    }

    public void remove(ListElement element) {
	data.remove(element);
	this.fireIntervalRemoved(this, 0, getSize());
    }

    public void add(ListElement element) {
	if (!data.contains(element)) {
	    data.add(element);
	    this.fireIntervalAdded(this, 0, getSize());
	}
    }
}