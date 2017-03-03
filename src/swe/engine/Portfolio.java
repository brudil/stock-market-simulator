package swe.engine;

import swe.engine.traders.Trader;

import java.util.ArrayList;

public class Portfolio {
    private String name;
    private Trader trader;
    private double cash;
    private ArrayList<Share> shares = new ArrayList<>();

    public Portfolio(String name, double cash, Trader trader) {
        this.name = name;
        this.cash = cash;
        this.trader = trader;
    }

    public void onNewDay() {
        this.trader.onNewDay();
    }

    public String getName() {
        return this.name;
    }

    public Double getCash() {
        return this.cash;
    }

    // TODO: probs not needed.
    public Trader getTrader() {
        return this.trader;
    }

    public ArrayList<Share> getShares() {
        return this.shares;
    }

    public void addNewShare(Company c, int NoOfShares) {
        this.shares.add(new Share(c, NoOfShares));
    }

    @Override
    public String toString() {
        String s = "Client{" + "Name=" + this.name + ", Cash=" + this.cash + ", Trader_Type=" + "tbi";
        String s2 = "\nShares:\n";

        for (int i = 0; i < this.shares.size(); i++) {
            s2 = s2.concat("Company: "+this.shares.get(i).getCompany().getName()+"\tNo Of Shares: "+this.shares.get(i).getNoOfShares()+"\n");
        }

        return s+s2;
    }
}
