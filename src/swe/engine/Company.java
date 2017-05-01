package swe.engine;

public class Company {
    String name;
    StockType stockType;
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

    void printName() {
        System.out.println(this.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
