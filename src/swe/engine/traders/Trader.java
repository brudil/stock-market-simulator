package swe.engine.traders;

import swe.engine.Company;
import swe.engine.Market;
import swe.engine.Portfolio;

import java.util.HashMap;

public abstract class Trader {

    /**
     * Called before trading begins at the start of each new day
     */
    public void onNewDay() {}

    abstract public HashMap<Company, Integer> getRequestedTrades(Portfolio portfolio, Market market);
}
