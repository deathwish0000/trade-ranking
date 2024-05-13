import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NumericTimeSeriesImp implements NumericTimeSeries {
    private List<DataPoint<Double>> dataPoints;

    public NumericTimeSeriesImp() {
        this.dataPoints = new ArrayList<>();
    }

    @Override
    public NumericTimeSeries calculateMovingAverage(int period) {
        NumericTimeSeriesImp movingAverageSeries = new NumericTimeSeriesImp();
        if (period <= 0 || dataPoints.size() < period) {
            return movingAverageSeries;
        }

        double sum = 0;
        for (int i = 0; i < period; i++) {
            sum += dataPoints.get(i).value;
        }

        for (int i = period - 1; i < dataPoints.size(); i++) {
            if (i >= period) {
                sum -= dataPoints.get(i - period).value;
            }
            sum += dataPoints.get(i).value;
            movingAverageSeries.addDataPoint(new DataPoint<>(dataPoints.get(i).date, sum / period));
        }

        return movingAverageSeries;
    }

    @Override
    public DataPoint<Double> getMax() {
        if (dataPoints.isEmpty()) return null;

        DataPoint<Double> maxDataPoint = dataPoints.get(0);
        for (DataPoint<Double> dp : dataPoints) {
            if (dp.value.compareTo(maxDataPoint.value) > 0) {
                maxDataPoint = dp;
            }
        }
        return maxDataPoint;
    }

    @Override
    public DataPoint<Double> getMin() {
        if (dataPoints.isEmpty()) return null;

        DataPoint<Double> minDataPoint = dataPoints.get(0);
        for (DataPoint<Double> dp : dataPoints) {
            if (dp.value.compareTo(minDataPoint.value) < 0) {
                minDataPoint = dp;
            }
        }
        return minDataPoint;
    }

    @Override
    public int size() {
        return dataPoints.size();
    }

    @Override
    public boolean empty() {
        return dataPoints.isEmpty();
    }

    @Override
    public Double getDataPoint(Date date) {
        for (DataPoint<Double> dp : dataPoints) {
            if (dp.date.equals(date)) {
                return dp.value;
            }
        }
        return null;
    }

    @Override
    public DLL<Date> getAllDates() {
        DLL<Date> dates = new DLLImp<>();
        for (DataPoint<Double> dp : dataPoints) {
            dates.insert(dp.date);
        }
        return dates;
    }

    @Override
    public Date getMinDate() {
        Date minDate = null;
        for (DataPoint<Double> dp : dataPoints) {
            if (minDate == null || dp.date.before(minDate)) {
                minDate = dp.date;
            }
        }
        return minDate;
    }

    @Override
    public Date getMaxDate() {
        Date maxDate = null;
        for (DataPoint<Double> dp : dataPoints) {
            if (maxDate == null || dp.date.after(maxDate)) {
                maxDate = dp.date;
            }
        }
        return maxDate;
    }

    @Override
    public boolean addDataPoint(DataPoint<Double> dataPoint) {
        if (dataPoint == null) return false;
        dataPoints.add(dataPoint);
        return true;
    }

    @Override
    public boolean updateDataPoint(DataPoint<Double> dataPoint) {
        for (int i = 0; i < dataPoints.size(); i++) {
            if (dataPoints.get(i).date.equals(dataPoint.date)) {
                dataPoints.set(i, dataPoint);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeDataPoint(Date date) {
        return dataPoints.removeIf(dp -> dp.date.equals(date));
    }

    @Override
    public DLL<DataPoint<Double>> getAllDataPoints() {
        DLL<DataPoint<Double>> list = new DLLImp<>();
        for (DataPoint<Double> dp : dataPoints) {
            list.insert(dp);
        }
        return list;
    }

    @Override
    public DLL<DataPoint<Double>> getDataPointsInRange(Date startDate, Date endDate) {
        DLL<DataPoint<Double>> list = new DLLImp<>();
        for (DataPoint<Double> dp : dataPoints) {
            if ((startDate == null || !dp.date.before(startDate)) && (endDate == null || !dp.date.after(endDate))) {
                list.insert(dp);
            }
        }
        return list;
    }
}
