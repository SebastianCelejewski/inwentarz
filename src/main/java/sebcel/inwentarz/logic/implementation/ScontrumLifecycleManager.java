package sebcel.inwentarz.logic.implementation;

import static sebcel.inwentarz.dao.dto.ScontrumStatus.ROZPOCZETE;
import static sebcel.inwentarz.dao.dto.ScontrumStatus.ZAKONCZONE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sebcel.inwentarz.dao.dto.ScontrumStatus;
import sebcel.inwentarz.logic.definition.ILifecycleManager;

public class ScontrumLifecycleManager implements ILifecycleManager<ScontrumStatus> {

    private Map<ScontrumStatus, List<ScontrumStatus>> allowedTransitions = new HashMap<ScontrumStatus, List<ScontrumStatus>>();

    public ScontrumLifecycleManager() {
	initialize();
    }

    private void initialize() {
	allowedTransitions.put(ROZPOCZETE, Arrays.asList(ZAKONCZONE));
	allowedTransitions.put(ZAKONCZONE, new ArrayList<ScontrumStatus>());
    }

    @Override
    public List<ScontrumStatus> getAllowedTransitions(ScontrumStatus status) {
	return allowedTransitions.get(status);
    }

    @Override
    public List<ScontrumStatus> getAllStatuses() {
	return Arrays.asList(ScontrumStatus.values());
    }
}