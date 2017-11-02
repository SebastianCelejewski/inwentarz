package sebcel.inwentarz.dao.definition;

import java.util.List;

import sebcel.inwentarz.dao.dto.ScontrumDetails;
import sebcel.inwentarz.dao.dto.ScontrumListElement;
import sebcel.inwentarz.dao.dto.ScontrumStatisticsData;

public interface IScontrumDao {

    public boolean isScontrumOpen();

    public int getOpenScontrumId();

    public ScontrumStatisticsData getScontrumStatistics(int scontrumId);

    public List<ScontrumListElement> getScontrumList();

    public ScontrumDetails getScontrumDetails(int scontrumId);

    public void closeScontrum(int scontrumId);

    public void startScontrum();
}
