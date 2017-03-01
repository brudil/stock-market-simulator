package swe.engine;

import swe.engine.traders.Trader;

public class Portfolio {
    Trader trader;

    public Portfolio(Trader trader) {
        this.trader =  trader;
    }

    public void onNewDay() {
        this.trader.onNewDay();
    }

}
