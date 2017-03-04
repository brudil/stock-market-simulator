package swe.engine.traders;

import swe.engine.Market;
import swe.engine.Portfolio;
import swe.engine.TradeSlip;

public abstract class Trader {

    /**
     * Called before trading begins at the start of each new day
     */
    public void onNewDay() {}

    abstract public TradeSlip getRequestedTrades(Portfolio portfolio, Market market);
}
