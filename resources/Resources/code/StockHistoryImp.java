import java.util.Date;

public class StockHistoryImp implements StockHistory {
    private String companyCode;
    private TimeSeries<StockData> timeSeries;

    public StockHistoryImp(String companyCode) {
        this.companyCode = companyCode;
        this.timeSeries = new TimeSeriesImp<>();
    }

    @Override
    public int size() {
        return timeSeries.size();
    }

    @Override
    public boolean empty() {
        return timeSeries.empty();
    }

    @Override
    public void clear() {
        ((StockHistoryImp) timeSeries).clear();
    }

    @Override
    public String getCompanyCode() {
        return companyCode;
    }

    @Override
    public void SetCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Override
    public TimeSeries<StockData> getTimeSeries() {
        return timeSeries;
    }

    @Override
    public StockData getStockData(Date date) {
        return timeSeries.getDataPoint(date);
    }

    @Override
    public boolean addStockData(Date date, StockData stockData) {
        return timeSeries.addDataPoint(new DataPoint<>(date, stockData));
    }

    @Override
    public boolean removeStockData(Date date) {
        return timeSeries.removeDataPoint(date);
    }
}
