package sebcel.inwentarz.dao.definition;

import sebcel.inwentarz.dao.dto.BookStatisticsData;
import sebcel.inwentarz.dao.dto.PurchaseStatistics;

public interface IStatisticsDao {

    public BookStatisticsData getBookStatisticsData();

    public PurchaseStatistics getPurchaseStatistics();

}
