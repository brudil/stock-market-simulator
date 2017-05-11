package swe.engine.traders;

import swe.engine.*;

import java.util.Map;
import java.util.Random;

/**
 * Handles the Balanced mode of RandomTrader
 */
public class BalancedTrader extends Trader {

    @Override
    public TradeSlip getRequestedTrades(Portfolio portfolio, Market market, History history) {
        TradeSlip ts = new TradeSlip();
        Random r = new Random();
        Map<Company, Integer> purchase = market.getShares().getRandomSharePercentageOfPortfolio(portfolio, r.nextDouble() * 1);
        purchase.forEach((key, value) -> ts.put(key, ts.getOrDefault(key, 0) + value));

        Map<Company, Integer> sell = market.getShares().getRandomSharePercentageOfPortfolio(portfolio, r.nextDouble() * 1);
        sell.forEach((key, value) -> ts.put(key, ts.getOrDefault(key, 0) + -value));

        return ts;
    }
}

