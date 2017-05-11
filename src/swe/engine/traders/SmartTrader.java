package swe.engine.traders;

import swe.engine.*;

import java.util.Map;
import java.util.Random;

// using composition this directs out our calls to the trader of the day (being either balanced, aggressive purchaser and aggressive seller)

/**
 * Implements a Random style trader, as described in spec
 */
public class SmartTrader extends Trader {

    public SmartTrader() {
    }

    @Override
    public TradeSlip getRequestedTrades(Portfolio portfolio, Market market, History history) {
        Random r = new Random();
        if (history.getLastTick() != null && history.getLastTick().status == MarketStatus.BULL) {
            TradeSlip ts = new TradeSlip();
            Map<Company, Integer> purchase = market.getShares().getRandomSharePercentageOfPortfolio(portfolio, r.nextDouble() * 2);
            purchase.forEach((key, value) -> ts.put(key, ts.getOrDefault(key, 0) + value));

            Map<Company, Integer> sell = market.getShares().getRandomSharePercentageOfPortfolio(portfolio, r.nextDouble() * 0.5);
            sell.forEach((key, value) -> ts.put(key, ts.getOrDefault(key, 0) + -value));

            return ts;
        }
        return new TradeSlip();
    }

    @Override
    public String toString() {
        return "Smart";
    }
}
