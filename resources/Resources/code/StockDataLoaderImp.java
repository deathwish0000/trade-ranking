import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.text.SimpleDateFormat;

public class StockDataLoaderImp implements StockDataLoader {

    @Override
    public StockHistory loadStockDataFile(String fileName) {
        StockHistory stockHistory = new StockHistoryImp(getBaseName(fileName));
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Date date = dateFormat.parse(parts[0]);
                StockData stockData = new StockData(
                    Double.parseDouble(parts[1]),
                    Double.parseDouble(parts[4]),
                    Double.parseDouble(parts[2]),
                    Double.parseDouble(parts[3]),
                    Long.parseLong(parts[5])
                );
                stockHistory.addStockData(date, stockData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return stockHistory;
    }

    @Override
    public StockHistoryDataSet loadStockDataDir(String directoryPath) {
        StockHistoryDataSet dataSet = new StockHistoryDataSetImp();
        try {
            Files.list(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .map(Path::toString)
                .forEach(file -> {
                    StockHistory history = loadStockDataFile(file);
                    if (history != null) dataSet.addStockHistory(history);
                });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return dataSet;
    }

    private String getBaseName(String fileName) {
        return Paths.get(fileName).getFileName().toString().replaceFirst("[.][^.]+$", "");
    }
}
