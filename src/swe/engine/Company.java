package swe.engine;

import java.io.Serializable;

public class Company implements Serializable {
    String name;
    StockType stockType;
    Float price;

    public Company(String name, StockType stockType, Float price) {
        this.name = name;
        this.stockType = stockType;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public Float getPrice() {
        return this.price;
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
