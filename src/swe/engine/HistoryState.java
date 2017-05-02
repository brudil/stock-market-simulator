package swe.engine;

import java.util.Calendar;
import java.util.HashMap;

final public class HistoryState {
    final public TickIdentifier tickIdentifier;
    final public MarketStatus status;
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
