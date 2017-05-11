package swe.engine.traders;

import swe.engine.History;
import swe.engine.Market;
import swe.engine.Portfolio;
import swe.engine.TradeSlip;

public abstract class Trader {

    /**
     * Called before trading begins at the start of each new day
     */
    public void onNewDay() {}

    /**
     * Gets a TradeSlip of trades wanted for given portfolio
     * @param portfolio portfolio of requested trades
     * @param market market instance
     * @return TradeSlip containing requested trades
     */
    abstract public TradeSlip getRequestedTrades(Portfolio portfolio, Market market, History history);
}
