package sebcel.inwentarz.event;

import java.util.HashSet;
import java.util.Set;

public class DataChangeManager implements IDataChangeListener {

    private Set<IDataChangeListener> listeners = new HashSet<IDataChangeListener>();

    public void addDataChangeListener(IDataChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void dataChanged() {
        for (IDataChangeListener listener : listeners) {
            listener.dataChanged();
        }
    }
}