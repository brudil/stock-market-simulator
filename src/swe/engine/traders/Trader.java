package swe.engine.traders;

import swe.engine.Market;
import swe.engine.Portfolio;
import swe.engine.TradeSlip;

import java.io.Serializable;

public abstract class Trader implements Serializable {

    /**
     * Called before trading begins at the start of each new day
     */
    public void onNewDay() {}

    abstract public TradeSlip getRequestedTrades(Portfolio portfolio, Market market);
}
