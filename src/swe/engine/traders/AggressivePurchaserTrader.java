package swe.engine.traders;

import swe.engine.Company;
import swe.engine.Market;
import swe.engine.Portfolio;
import swe.engine.TradeSlip;

import java.util.Map;
import java.util.Random;

public class AggressivePurchaserTrader extends Trader {
    @Override
    public TradeSlip getRequestedTrades(Portfolio portfolio, Market market) {
        TradeSlip ts = new TradeSlip();
        Random r = new Random();
        Map<Company, Integer> purchase = market.getShares().getRandomSharePercentageOfPortfolio(portfolio, r.nextFloat() * 2);

        return ts;
    }
}
