package swe.engine;

public class Company {
    String name;
    StockType stockType;
    int price;

    public Company(String name, StockType stockType, int price) {
        this.name = name;
        this.stockType = stockType;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public StockType getStockType() {
        return this.stockType;
    }

    void printName() {
        System.out.println(this.name);
    }
}
