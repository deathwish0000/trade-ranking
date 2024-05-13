import java.util.Date;

public class TimeSeriesImp<T> implements TimeSeries<T> {
    private DLL<DataPoint<T>> dataPoints;

    public TimeSeriesImp() {
        this.dataPoints = new DLLImp<>();
    }

    @Override
    public int size() {
        return dataPoints.size();
    }

    @Override
    public boolean empty() {
        return dataPoints.empty();
    }

    @Override
    public T getDataPoint(Date date) {
        if (dataPoints.empty()) {
            return null;
        }

        dataPoints.findFirst();
        while (true) {
            DataPoint<T> dataPoint = dataPoints.retrieve();
            if (dataPoint.date.equals(date)) {
                return dataPoint.value;
            }
            if (dataPoints.last()) {
                break;
            }
            dataPoints.findNext();
        }
        return null;
    }


    @Override
    public DLL<Date> getAllDates() {
        DLL<Date> dates = new DLLImp<>();
        if (!dataPoints.empty()) {
            dataPoints.findFirst();
            while (true) {
                DataPoint<T> dataPoint = dataPoints.retrieve();
                dates.insert(dataPoint.date);
                if (dataPoints.last()) {
                    break;
                }
                dataPoints.findNext();
            }
        }
        return dates;
    }

    @Override
    public Date getMinDate() {
        if (dataPoints.empty()) {
            return null;
        }

        Date minDate = null;
        dataPoints.findFirst();
        while (true) {
            DataPoint<T> dataPoint = dataPoints.retrieve();
            if (minDate == null || dataPoint.date.before(minDate)) {
                minDate = dataPoint.date;
            }
            if (dataPoints.last()) {
                break;
            }
            dataPoints.findNext();
        }
        return minDate;
    }


    @Override
    public Date getMaxDate() {
        if (dataPoints.empty()) {
            return null;
        }

        Date maxDate = null;
        dataPoints.findFirst();
        while (true) {
            DataPoint<T> dataPoint = dataPoints.retrieve();
            if (maxDate == null || dataPoint.date.after(maxDate)) {
                maxDate = dataPoint.date;
            }
            if (dataPoints.last()) {
                break;
            }
            dataPoints.findNext();
        }
        return maxDate;
    }



    @Override
    public boolean addDataPoint(DataPoint<T> dataPoint) {
        dataPoints.insert(dataPoint);
        return true;
    }

    @Override
    public boolean updateDataPoint(DataPoint<T> dataPoint) {
        if (dataPoints.empty()) {
            return false;  // Early exit if the list is empty
        }

        dataPoints.findFirst();  // Set the current pointer to the head of the list
        while (true) {
            DataPoint<T> dp = dataPoints.retrieve();  // Retrieve the data point at the current position
            if (dp.date.equals(dataPoint.date)) {
                dp.value = dataPoint.value;  // Update the value of the data point
                return true;
            }
            if (dataPoints.last()) {
                break;  // Exit if we've checked the last element
            }
            dataPoints.findNext();  // Move to the next data point
        }

        return false;  // Return false if the date was not found and no data point was updated
    }


    @Override
    public boolean removeDataPoint(Date date) {
        if (dataPoints.empty()) {
            return false;  // Early exit if the list is empty
        }
        
        dataPoints.findFirst();  // Set the current pointer to the head of the list
        while (true) {
            DataPoint<T> dataPoint = dataPoints.retrieve();  // Retrieve the data point at the current position
            if (dataPoint.date.equals(date)) {
                dataPoints.remove();  // Removes the current data point
                return true;
            }
            if (dataPoints.last()) {
                break;  // Exit if we've checked the last element
            }
            dataPoints.findNext();  // Move to the next data point
        }
        
        return false;  // Return false if the date was not found and no element was removed
    }
    @Override
    public DLL<DataPoint<T>> getAllDataPoints() {
        return dataPoints;
    }

    @Override
    public DLL<DataPoint<T>> getDataPointsInRange(Date startDate, Date endDate) {
        DLL<DataPoint<T>> dataPointsInRange = new DLLImp<>();
        
        // Set the current position to the first element
        dataPoints.findFirst();
        
        // Iterate over the data points
        while (!dataPoints.last()) {
            DataPoint<T> dataPoint = dataPoints.retrieve();
            Date pointDate = dataPoint.date;
            if ((startDate == null || !pointDate.before(startDate)) && (endDate == null || !pointDate.after(endDate))) {
                dataPointsInRange.insert(dataPoint);
            }
            // Move to the next element
            dataPoints.findNext();
        }
        
        // Check the last element
        DataPoint<T> lastDataPoint = dataPoints.retrieve();
        if (lastDataPoint != null) {
            Date lastPointDate = lastDataPoint.date;
            if ((startDate == null || !lastPointDate.before(startDate)) && (endDate == null || !lastPointDate.after(endDate))) {
                dataPointsInRange.insert(lastDataPoint);
            }
        }

        return dataPointsInRange;
    }
}
