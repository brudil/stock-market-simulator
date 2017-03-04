package swe.engine.traders;

import swe.engine.Company;
import swe.engine.Market;
import swe.engine.Portfolio;

import java.util.HashMap;

public class AggressivePurchaserTrader extends Trader {
    @Override
    public HashMap<Company, Integer> getRequestedTrades(Portfolio portfolio, Market market) {
        return null;
    }
}
