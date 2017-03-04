package swe.engine;

import swe.engine.traders.Trader;

import java.util.HashMap;

public class Portfolio {
    private String name;
    private Trader trader;
    private double cash;
    private Market market;

    public Portfolio(String name, double cash, Trader trader) {
        this.name = name;
        this.cash = cash;
        this.trader = trader;
    }

    public void setMarket(Market market) {
        this.market = market;
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

    public HashMap<Company, Share> getShares() {
        return this.market.getSharesForPortfolio(this);
    }

    public void addNewShare(Company c, int NoOfShares) {
        // this.market.deltaSharesForPortfolio(new Share(c, NoOfShares));
    }

    @Override
    public String toString() {
        String s = "Client{" + "Name=" + this.name + ", Cash=" + this.cash + ", Trader_Type=" + "tbi";
        String s2 = "\nShares:\n";

        //for (int i = 0; i < this.shares.size(); i++) {
        //    s2 = s2.concat("Company: "+this.shares.get(i).getCompany().getName()+"\tNo Of Shares: "+this.shares.get(i).getNoOfShares()+"\n");
        //}

        return s+s2;
    }

    public HashMap<Company, Integer> getRequestedTrades() {
        return this.trader.getRequestedTrades(this, this.market);
    }
}
