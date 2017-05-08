package swe.engine;

/**
 * Represents a company listed on the stock market
 */
public class Company {
    /**
     * Display name of company
     */
    String name;

    /**
     * Type of company listed in markert
     */
    StockType stockType;

    /**
     * Latest stock price
     */
    Double price;

    public Company(String name, StockType stockType, Double price) {
        this.name = name;
        this.stockType = stockType;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public StockType getStockType() {
        return this.stockType;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
