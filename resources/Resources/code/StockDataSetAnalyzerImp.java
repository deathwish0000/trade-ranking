import java.util.Date;
import java.util.Set;

public class StockDataSetAnalyzerImp implements StockDataSetAnalyzer {
    private StockHistoryDataSet dataSet;

    @Override
    public StockHistoryDataSet getStockHistoryDataSet() {
        return dataSet;
    }

    @Override
    public void setStockHistoryDataSet(StockHistoryDataSet stockHistoryDataSet) {
        this.dataSet = stockHistoryDataSet;
    }

    @Override
    public DLLComp<CompPair<String, Double>> getSortedByPerformance(Date startDate, Date endDate) {
        DLLComp<CompPair<String, Double>> performanceList = new DLLCompImp<>();
        if (startDate == null || endDate == null) return performanceList;
        
        Set<String> keys = (Set<String>) dataSet.getAllCompanyCodes();
        for (String code : keys) {
            StockHistory history = dataSet.getStockHistory(code);
            if (history != null) {
                double performance = calculatePerformance(history, startDate, endDate);
                performanceList.insert(new CompPair<>(code, performance));
            }
        }
        performanceList.sort(true);
        return performanceList;
    }

    @Override
    public DLLComp<CompPair<String, Long>> getSortedByVolume(Date startDate, Date endDate) {
        DLLComp<CompPair<String, Long>> volumeList = new DLLCompImp<>();
        if (startDate == null || endDate == null) return volumeList;
        
        Set<String> keys = (Set<String>) dataSet.getAllCompanyCodes();
        for (String code : keys) {
            StockHistory history = dataSet.getStockHistory(code);
            if (history != null) {
                long totalVolume = calculateTotalVolume(history, startDate, endDate);
                volumeList.insert(new CompPair<>(code, totalVolume));
            }
        }
        volumeList.sort(true);
        return volumeList;
    }

    @Override
    public DLLComp<CompPair<Pair<String, Date>, Double>> getSortedByMSDPI(Date startDate, Date endDate) {
        DLLComp<CompPair<Pair<String, Date>, Double>> msdpiList = new DLLCompImp<>();
        if (startDate == null || endDate == null) return msdpiList;
        
        Set<String> keys = (Set<String>) dataSet.getAllCompanyCodes();
        for (String code : keys) {
            StockHistory history = dataSet.getStockHistory(code);
            if (history != null) {
                Pair<Date, Double> msdpi = calculateMSDPI(history, startDate, endDate);
                msdpiList.insert(new CompPair<>(new Pair<>(code, msdpi.first), msdpi.second));
            }
        }
        msdpiList.sort(true);
        return msdpiList;
    }

    // Actual implementations for calculating performance metrics
    private double calculatePerformance(StockHistory history, Date start, Date end) {
        // Mock implementation
        // Calculate percentage change between start and end dates
        StockData startData = history.getStockData(start);
        StockData endData = history.getStockData(end);
        if (startData == null || endData == null) return 0.0;
        return ((endData.close - startData.close) / startData.close) * 100.0;
    }

    private long calculateTotalVolume(StockHistory history, Date start, Date end) {
        // Aggregate volume over the specified date range
        long totalVolume = 0;
        TimeSeries<StockData> timeSeries = history.getTimeSeries();
        for (Date date = start; !date.after(end); date = new Date(date.getTime() + (1000 * 60 * 60 * 24))) {
            StockData data = timeSeries.getDataPoint(date);
            if (data != null) totalVolume += data.volume;
        }
        return totalVolume;
    }

    private Pair<Date, Double> calculateMSDPI(StockHistory history, Date start, Date end) {
        // Calculate maximum single-day price increase within the given range
        double maxIncrease = 0.0;
        Date maxDate = start;
        TimeSeries<StockData> timeSeries = history.getTimeSeries();
        for (Date date = start; date.before(end) || date.equals(end); date = new Date(date.getTime() + (1000 * 60 * 60 * 24))) {
            StockData data = timeSeries.getDataPoint(date);
            if (data != null) {
                double increase = data.high - data.low;
                if (increase > maxIncrease) {
                    maxIncrease = increase;
                    maxDate = date;
                }
            }
        }
        return new Pair<>(maxDate, maxIncrease);
    }
}
