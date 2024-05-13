public class StockHistoryDataSetImp implements StockHistoryDataSet {
    private Map<String, StockHistory> stockHistories;

    public StockHistoryDataSetImp() {
        this.stockHistories = new BST<>();
    }

    @Override
    public int size() {
        return stockHistories.size();
    }

    @Override
    public boolean empty() {
        return stockHistories.empty();
    }

    @Override
    public void clear() {
        stockHistories.clear();
    }

    @Override
    public Map<String, StockHistory> getStockHistoryMap() {
        return stockHistories;
    }

    @Override
    public DLLComp<String> getAllCompanyCodes() {
        return stockHistories.getKeys();
    }

    @Override
    public StockHistory getStockHistory(String companyCode) {
        if (stockHistories.find(companyCode)) {
            return stockHistories.retrieve();
        }
        return null;
    }

    @Override
    public boolean addStockHistory(StockHistory stockHistory) {
        return stockHistories.insert(stockHistory.getCompanyCode(), stockHistory);
    }

    @Override
    public boolean removeStockHistory(String companyCode) {
        return stockHistories.remove(companyCode);
    }
}
