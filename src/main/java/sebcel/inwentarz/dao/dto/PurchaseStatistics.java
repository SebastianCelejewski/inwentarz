package sebcel.inwentarz.dao.dto;

import java.util.ArrayList;
import java.util.List;

public class PurchaseStatistics {

    private List<PurchaseStatisticsEntry> entries = new ArrayList<PurchaseStatisticsEntry>();

    public List<PurchaseStatisticsEntry> getEntries() {
	return entries;
    }

    public void add(PurchaseStatisticsEntry entry) {
	entries.add(entry);
    }
}
