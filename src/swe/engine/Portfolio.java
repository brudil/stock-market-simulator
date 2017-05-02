package swe.engine;

import java.util.HashMap;
import swe.engine.traders.Trader;

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

    /**
     * Assigns the portfolio to a market.
     * Called automatically by Market when instantiated
     * Required for many market based methods
     * @param market the market the portfolio is within
     */
    public void setMarket(Market market) {
        this.market = market;
    }

    protected void onNewDay() {
        this.trader.onNewDay();
    }

    public String getName() {
        return this.name;
    }

    public Double getCash() {
        return this.cash;
    }

    public HashMap<Company, Share> getShares() {
        return this.market.getShares().getSharesForPortfolio(this);
    }

    public void getPercentageOfSharesRandom(float percentage) {

    }

    public double getWorth() {
        return getCash() + getShares().entrySet().stream()
                .map(entry -> entry.getKey().getPrice() * entry.getValue().getNumberOfShares())
                .reduce(0.0, Double::sum);
    }

    public Trader getTrader() {
        return trader;
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


    /**
     * Gets requested changes from the portfolios trader
     * @return a map of trades company:shares change
     */
    public TradeSlip getRequestedTrades() {
        return this.trader.getRequestedTrades(this, this.market);
    }
}
