package swe.engine.traders;

import swe.engine.Company;
import swe.engine.Market;
import swe.engine.Portfolio;
import swe.engine.TradeSlip;

import java.util.Map;
import java.util.Random;

/**
 * Handles the AggressiveSeller mode of RandomTrader
 */
public class AggressiveSellerTrader extends Trader {
    @Override
    public TradeSlip getRequestedTrades(Portfolio portfolio, Market market) {
        TradeSlip ts = new TradeSlip();
        Random r = new Random();
        Map<Company, Integer> purchase = market.getShares().getRandomSharePercentageOfPortfolio(portfolio, r.nextDouble() * 0.5);
        purchase.forEach((key, value) -> ts.put(key, ts.getOrDefault(key, 0) + value));

        Map<Company, Integer> sell = market.getShares().getRandomSharePercentageOfPortfolio(portfolio, r.nextDouble() * 2);
        sell.forEach((key, value) -> ts.put(key, ts.getOrDefault(key, 0) + -value));

        return ts;
    }
}

