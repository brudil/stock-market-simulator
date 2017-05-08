package swe.engine;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Stores an immutable record of a the state of the market
 */
final public class HistoryState {
    /**
     * Tick ID
     */
    final public TickIdentifier tickIdentifier;
    /**
     * Status of the market
     */
    final public MarketStatus status;
    /**
     * Total share index of market
     */
    final public double shareIndex;
    final public HashMap<Company, Double> companySharePrice;
    final public HashMap<Portfolio, Double> portfolioWorth;

    public HistoryState(TickIdentifier tickIdentifier, MarketStatus status, double shareIndex, HashMap<Company, Double> companySharePrice, HashMap<Portfolio, Double> portfolioWorth) {
        this.tickIdentifier = tickIdentifier;
        this.status = status;
        this.shareIndex = shareIndex;
        this.companySharePrice = companySharePrice;
        this.portfolioWorth = portfolioWorth;
    }
}
