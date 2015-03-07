package sebcel.inwentarz.logic.definition;

import java.util.List;

public interface ILifecycleManager<T> {

    public List<T> getAllowedTransitions(T status);

    public List<T> getAllStatuses();
}